package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.entity.ai.goal.DestroyCropGoal;
import com.acuteterror233.mite.world.entity.ai.goal.ZombieDigGoal;
import com.acuteterror233.mite.world.entity.ai.goal.ZombieEatDroppedMeatGoal;
import com.acuteterror233.mite.world.entity.ai.pathing.ZombieDigNavigation;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.CamelHusk;
import net.minecraft.world.entity.animal.equine.SkeletonHorse;
import net.minecraft.world.entity.animal.equine.ZombieHorse;
import net.minecraft.world.entity.animal.nautilus.ZombieNautilus;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Zombie} — Extends zombie behavior: terrain digging, crop destruction,
 * dimension/Y-based weapon population, and full-armor buffs.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code registerGoals} injection (TAIL): adds a {@link ZombieDigGoal} (priority 1)
 *       so blocked zombies dig through terrain toward their target — blocks that require
 *       a correct tool for drops can only be dug while the zombie holds such a tool;
 *       also adds a {@link DestroyCropGoal} so zombies seek out and destroy crops, and a
 *       {@link ZombieEatDroppedMeatGoal} (priority 5) so zombies devour whole dropped
 *       {@code #minecraft:meat} stacks. The vanilla player-targeting goal (priority 2) is
 *       rebuilt as a sight-gated variant with a five-minute memory: a zombie still only
 *       acquires a player it can actually see, and it drops that player only after five
 *       minutes without sight (6000 ticks, i.e. 5:00) — vanilla's 60-tick expiry would
 *       lose the target halfway through every tunnel.
 *       The animal-hunting goal skips undead mounts (zombie horse, skeleton horse,
 *       zombie nautilus, camel husk).</li>
 *   <li>{@code hurtServer} redirects: the reinforcement roll always succeeds — the chance
 *       lookup returns {@code 1.0} and the difficulty gate always sees {@code HARD} — so
 *       every zombie hit summons reinforcements regardless of difficulty.</li>
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
    /**
     * Ticks a player target survives without line of sight before the mob gives up (5 minutes).
     *
     * <p>The stored value is <em>not</em> the wall-clock duration. {@code TargetGoal} forgets the
     * target once its unseen counter passes {@code reducedTickDelay(unseenMemoryTicks)}, and that
     * helper is {@code positiveCeilDiv(i, 2)} — it halves. The counter itself advances once per
     * tick, because {@code GoalSelector.tick} evaluates {@code canContinueToUse} for every running
     * goal each tick. Five minutes (6000 t) therefore needs 12000 here; the previous 6000 gave
     * only 2:30.</p>
     */
    @Unique
    private static final int TARGET_UNSEEN_MEMORY_TICKS = 12000;
    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Routes zombies through the dig-aware navigation. This is a merged override, not an
     * injection: {@code Zombie} itself does not declare {@code createNavigation} (only
     * {@code Mob} and {@code Drowned} do), and Mixin resolves {@code @Inject} targets only
     * against the target class's own methods — with {@code defaultRequire: 1} that would fail
     * at load time. {@code Drowned} keeps its own water-bound navigation by Java's
     * most-specific-method rule.
     */
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new ZombieDigNavigation(this, level);
    }

    /**
     * Adds the terrain-digging goal (priority 1 — preempts the vanilla spear-use goal at
     * 2 and the melee attack goal at 3 while a wall blocks the way), the crop-destroying
     * goal (priority 4 — the first slot after the melee attack goal at 3, so trampling only
     * yields to attacking a player) and the livestock-hunting target goal (priority 4 — any
     * entity extending {@link Animal}) after vanilla goal registration.
     *
     * <p>The vanilla player-targeting goal at priority 2 is replaced: the replacement still
     * requires line of sight to acquire a player (a zombie has to see a player before it
     * starts pursuing them) and keeps sight-checked continuation, but the unseen memory is
     * raised from vanilla's 60 ticks to five minutes ({@value #TARGET_UNSEEN_MEMORY_TICKS}
     * ticks) — enough for a tunnel, without making the pursuit literally endless.
     * Hunting animals sits below players/villagers/golems (priorities 2-3) and above the
     * vanilla baby-turtle goal (priority 5), excluding undead mounts ({@link #isUndeadMount}).</p>
     */
    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void registerGoals(CallbackInfo ci) {
        Zombie zombie = (Zombie) (Object) this;
        // The vanilla player goal (priority 2) already requires line of sight to both acquire
        // and keep a target, but its unseen memory is only 60 ticks — every tunnel would lose
        // the target halfway. Rebuilt with the same sight gates and a five-minute unseen
        // memory: the counter is reset on every tick the player is actually visible, so a
        // target is only abandoned after five continuous minutes out of sight.
        this.targetSelector.getAvailableGoals().removeIf(wrapped ->
                wrapped.getPriority() == 2 && wrapped.getGoal() instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(zombie, Player.class, true)
                .setUnseenMemoryTicks(TARGET_UNSEEN_MEMORY_TICKS));
        this.goalSelector.addGoal(1, new ZombieDigGoal(zombie));
        // Priority 4 is the first slot after the vanilla melee attack goal (ZombieAttackGoal
        // at 3), so trampling crops only ever yields to attacking a player; priority 1 stays the
        // dig goal, which is itself part of the chase and only runs when the player is walled off.
        this.goalSelector.addGoal(4, new DestroyCropGoal(zombie, 1.0F, 3));
        this.goalSelector.addGoal(5, new ZombieEatDroppedMeatGoal(zombie, 1.0D));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(zombie, Animal.class, true,
                (candidate, level) -> !isUndeadMount(candidate)));
    }

    /**
     * Forces the reinforcement roll to always succeed: vanilla only spawns reinforcements
     * when {@code random.nextFloat() < getAttributeValue(SPAWN_REINFORCEMENTS_CHANCE)}, so
     * returning {@code 1.0} makes every hit pass that check (the roll is in {@code [0, 1)}).
     * The callee charge modifier on spawned reinforcements is overridden too, but a
     * reinforcement still needs to be hit itself before it can summon another.
     */
    @Redirect(method = "hurtServer", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/monster/zombie/Zombie;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
    protected double mme$alwaysAllowReinforcements(Zombie zombie, Holder<Attribute> attribute) {
        return attribute == Attributes.SPAWN_REINFORCEMENTS_CHANCE ? 1.0D : zombie.getAttributeValue(attribute);
    }

    /**
     * Removes the vanilla {@code HARD}-difficulty gate on reinforcements (the only
     * {@code getDifficulty} use in {@code hurtServer} is that comparison), so the forced
     * roll in {@link #mme$alwaysAllowReinforcements} actually spawns reinforcements on every
     * difficulty. {@code ServerLevel.isSpawningMonsters()} is still honoured, so a server
     * that disabled mob spawning does not get reinforcements either.
     */
    @Redirect(method = "hurtServer", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    protected Difficulty mme$alwaysHardForReinforcements(ServerLevel level) {
        return Difficulty.HARD;
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