package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.entity.ai.goal.DestroyCropGoal;
import com.acuteterror233.mite.world.entity.ai.goal.MonsterFindPlayerGoal;
import com.acuteterror233.mite.world.entity.ai.goal.ZombieDigGoal;
import com.acuteterror233.mite.world.entity.ai.goal.ZombieEatDroppedMeatGoal;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.CamelHusk;
import net.minecraft.world.entity.animal.equine.SkeletonHorse;
import net.minecraft.world.entity.animal.equine.ZombieHorse;
import net.minecraft.world.entity.animal.nautilus.ZombieNautilus;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code Zombie} — Extends zombie behavior: terrain digging, crop destruction,
 * dimension/Y-based weapon population, and full-armor buffs.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code createAttributes} injection (RETURN): raises {@code FOLLOW_RANGE} to 48
 *       (vanilla 35), enlarging both target acquisition distance and the A* node budget
 *       ({@code maxVisitedNodes = FOLLOW_RANGE * 16}). Applies to every zombie variant
 *       since all of them delegate to {@code Zombie.createAttributes}.</li>
 *   <li>{@code registerGoals} injection (TAIL): adds a {@link ZombieDigGoal} (priority 1)
 *       so blocked zombies dig through terrain toward their target — blocks that require
 *       a correct tool for drops can only be dug while the zombie holds such a tool;
 *       also adds a {@link DestroyCropGoal} so zombies seek out and destroy crops, and a
 *       {@link ZombieEatDroppedMeatGoal} (priority 5) so zombies devour whole dropped
 *       {@code #minecraft:meat} stacks. The vanilla sight-gated player-targeting goal
 *       (priority 2) is replaced by {@link MonsterFindPlayerGoal} so zombies find and
 *       track players without line of sight; the animal-hunting goal skips undead
 *       mounts (zombie horse, skeleton horse, zombie nautilus, camel husk).</li>
 *   <li>{@code populateDefaultEquipmentSlots} overwrite: after the vanilla pass,
 *       fully-armored zombies gain Strength II and a +0.1 movement-speed modifier;
 *       zombies in the underground dimension or the overworld may additionally receive
 *       a tiered melee weapon chosen by Y-level (see the {@code set*Weapon} helpers).</li>
 * </ul>
 *
 * <p>Weapon drop rates: 0.25 below the dimension's Y threshold (underground 125,
 * overworld 0), 0.05 at or above it, doubled on HARD difficulty. Fully-armored
 * zombies always receive a weapon.</p>
 */
@Mixin(Zombie.class)
public abstract class ZombieMixin extends Monster {
    @Unique
    private static final float LOW_Y_THRESHOLD_UNDERGROUND = 125.0F;
    @Unique
    private static final float LOW_Y_THRESHOLD_OVERWORLD = 0.0F;
    @Unique
    private static final float LOW_Y_DROP_RATE = 0.25F;
    @Unique
    private static final float HIGH_Y_DROP_RATE = 0.05F;
    @Unique
    private static final float HARD_DIFFICULTY_MULTIPLIER = 2.0F;
    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Adds the terrain-digging goal (priority 1 — preempts the vanilla spear-use goal at
     * 2 and the melee attack goal at 3 while a wall blocks the way), the crop-destroying
     * goal (priority 4) and the livestock-hunting target goal (priority 4 — any entity
     * extending {@link Animal}) after vanilla goal registration. The vanilla sight-gated
     * player-targeting goal (the only {@code NearestAttackableTargetGoal} at priority 2)
     * is replaced by {@link MonsterFindPlayerGoal} so zombies acquire and track players
     * without line of sight. Hunting animals sits below players/villagers/golems
     * (priorities 2-3) and above the vanilla baby-turtle goal (priority 5), excluding
     * undead mounts ({@link #isUndeadMount}).
     */
    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void registerGoals(CallbackInfo ci) {
        Zombie zombie = (Zombie) (Object) this;
        this.goalSelector.addGoal(1, new ZombieDigGoal(zombie));
        this.goalSelector.addGoal(4, new DestroyCropGoal(zombie, 1.0F, 3));
        this.goalSelector.addGoal(5, new ZombieEatDroppedMeatGoal(zombie, 1.0D));
        this.targetSelector.getAvailableGoals().removeIf(wrapped ->
                wrapped.getPriority() == 2 && wrapped.getGoal() instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(2, new MonsterFindPlayerGoal(zombie));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(zombie, Animal.class, true,
                (candidate, level) -> !isUndeadMount(candidate)));
    }

    /**
     * Returns true for undead animal mobs zombies must never hunt: zombie horse,
     * skeleton horse, zombie nautilus and camel husk. Used as the selector of the
     * livestock-hunting target goal.
     */
    @Unique
    private static boolean isUndeadMount(LivingEntity candidate) {
        return candidate instanceof ZombieHorse
                || candidate instanceof SkeletonHorse
                || candidate instanceof ZombieNautilus
                || candidate instanceof CamelHusk;
    }

    /**
     * Raises {@code FOLLOW_RANGE} from the vanilla 35 to 48. This enlarges both the
     * target-acquisition distance ({@code TargetingConditions.range}) and the pathfinding
     * node budget ({@code maxVisitedNodes = FOLLOW_RANGE * 16}). All zombie variants
     * (husk, drowned, zombie villager, ...) build their attributes through
     * {@code Zombie.createAttributes}, so a single injection covers them all.
     */
    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void mme$extendFollowRange(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue().add(Attributes.FOLLOW_RANGE, 48.0D));
    }

    /**
     * @author AcuteTerror233
     * @reason Add held weapons
     */
    @Overwrite
    public void populateDefaultEquipmentSlots(@NonNull RandomSource randomSource, @NonNull DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        // Full-armor bonus: infinite Strength II and a permanent +0.1 movement speed
        boolean fullArmor = !this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()
                && !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty()
                && !this.getItemBySlot(EquipmentSlot.LEGS).isEmpty()
                && !this.getItemBySlot(EquipmentSlot.FEET).isEmpty();
        if (fullArmor){
            this.addEffect(new MobEffectInstance(MobEffects.STRENGTH, -1, 1));
            this.getAttribute(Attributes.MOVEMENT_SPEED).addOrReplacePermanentModifier(
                    new AttributeModifier(
                            Identifier.fromNamespaceAndPath(MME.MOD_ID, "speed_modifier"),
                            0.1F,
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        }
        Level level = this.level();
        // Drop rate: 0.25 below the Y threshold, 0.05 above; doubled on HARD difficulty
        if (level.dimension() == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) {
            float populateRate = (getY() < LOW_Y_THRESHOLD_UNDERGROUND ? LOW_Y_DROP_RATE : HIGH_Y_DROP_RATE) * (level.getDifficulty() == Difficulty.HARD ? HARD_DIFFICULTY_MULTIPLIER : 1.0F);
            if (randomSource.nextFloat() < populateRate || fullArmor) {
                int weaponIndex = randomSource.nextInt(6);
                if (getY() < LOW_Y_THRESHOLD_UNDERGROUND) {
                    setUndergroundLowYWeapon(weaponIndex);
                } else {
                    setUndergroundHighYWeapon(weaponIndex);
                }
            }
        } else if (level.dimension() == Level.OVERWORLD) {
            float populateRate = (getY() < LOW_Y_THRESHOLD_OVERWORLD ? LOW_Y_DROP_RATE : HIGH_Y_DROP_RATE) * (level.getDifficulty() == Difficulty.HARD ? HARD_DIFFICULTY_MULTIPLIER : 1.0F);
            if (randomSource.nextFloat() < populateRate || fullArmor) {
                int weaponIndex = randomSource.nextInt(4);
                if (getY() < LOW_Y_THRESHOLD_OVERWORLD) {
                    setOverworldLowYWeapon(weaponIndex);
                } else {
                    setOverworldHighYWeapon(weaponIndex);
                }
            }
        }
    }
    // Underground low-Y weapon assignment
    @Unique
    private void setUndergroundLowYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.ANCIENT_METAL_SWORD)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.ANCIENT_METAL_BATTLE_AXE)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.IRON_BATTLE_AXE)); break;
            case 4: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_SWORD)); break;
            case 5: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_BATTLE_AXE)); break;
        }
    }

    // Underground high-Y weapon assignment
    @Unique
    private void setUndergroundHighYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_SWORD)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_BATTLE_AXE)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.COPPER_SWORD)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.COPPER_BATTLE_AXE)); break;
            case 4: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.SILVER_SWORD)); break;
            case 5: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.SILVER_BATTLE_AXE)); break;
        }
    }

    // Overworld low-Y weapon assignment
    @Unique
    private void setOverworldLowYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.COPPER_SWORD)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.COPPER_BATTLE_AXE)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.SILVER_SWORD)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.SILVER_BATTLE_AXE)); break;
        }
    }

    // Overworld high-Y weapon assignment
    @Unique
    private void setOverworldHighYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.WOODEN_CLUB)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.WOODEN_CUDGEL)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.FLINT_SHOVEL)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.FLINT_AXE)); break;
        }
    }
}