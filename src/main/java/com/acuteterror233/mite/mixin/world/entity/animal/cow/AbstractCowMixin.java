package com.acuteterror233.mite.mixin.world.entity.animal.cow;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.acuteterror233.mite.world.entity.ai.goal.DrinkGoal;
import com.acuteterror233.mite.world.entity.ai.goal.GrazeGoal;
import com.acuteterror233.mite.world.entity.ai.goal.SeekLightGoal;
import com.acuteterror233.mite.world.entity.ai.goal.SicknessCheckGoal;
import com.acuteterror233.mite.world.entity.ai.sickness.AnimalSicknessLogic;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessCap;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessRules;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.AbstractCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

/**
 * Mixin for {@code AbstractCow} — Adds a milking quota with recovery, plus the animal
 * sickness system: thirst, light, crowding and grazing needs (eats short/tall grass
 * plants), milk refusal while sick, sickness persistence, and no drops while sick.
 * Cows and mooshrooms both inherit this behaviour.
 *
 * <p>A cow holds up to 4 milk units: only a full quota can be bucket-milked (yielding
 * the {@code milk_<cow>} item variant), while each bowl drawing consumes one unit.
 * The quota recovers by 1 every 12000 ticks and both counters persist through NBT.</p>
 */
@Mixin(AbstractCow.class)
public abstract class AbstractCowMixin extends Animal implements SicknessCap {
    /** Per-cow sickness bookkeeping (shared by cows and mooshrooms). */
    @Unique
    private final SicknessState mme$sickness = new SicknessState();

    /** Ticks accumulated toward the next milk unit recovery (reset at every 12000). */
    @Unique
    private int recoveryCounter = 0;
    /** Maximum milk units a cow can hold. */
    @Unique
    private static final int maxMilkCounter = 4;
    /** Current milk units available for milking. */
    @Unique
    private int milkCounter = maxMilkCounter;
    protected AbstractCowMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public SicknessState mme$sicknessState() {
        return mme$sickness;
    }

    /** Registers the graze, thirst, light-seeking and check goals after vanilla goals. */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void mme$registerSicknessGoals(EntityType<? extends AbstractCow> type, Level level, CallbackInfo ci) {
        SicknessRules rules = SicknessRules.get();
        this.goalSelector.addGoal(5, new GrazeGoal((Animal) (Object) this, rules, SicknessRules.GrassKind.GRASS_PLANT));
        this.goalSelector.addGoal(5, new DrinkGoal((Animal) (Object) this, rules));
        this.goalSelector.addGoal(6, new SeekLightGoal((Animal) (Object) this, rules));
        this.goalSelector.addGoal(9, new SicknessCheckGoal((Animal) (Object) this));
    }

    /**
     * Bucket milking (only at full quota) yields the {@code milk_<cow>} item variant
     * when one is registered; bowl milking consumes one quota unit per bowl. A sick
     * cow refuses both bucket and bowl. Falls back to vanilla behavior otherwise.
     *
     * @author AcuteError233
     * @reason Modify cow interaction
     */
    @Overwrite
    public @NotNull InteractionResult mobInteract(Player player, @NonNull InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (AnimalSicknessLogic.isSick(this)
                && (itemStack.is(MMEItemTags.BUCKET) || itemStack.is(Items.BOWL))) {
            return InteractionResult.FAIL;
        }
        if (!this.isBaby()) {
            if (itemStack.is(MMEItemTags.BUCKET) && this.milkCounter == 4){
                // Only a fully "charged" cow can be bucket-milked; the whole quota is spent at once
                this.milkCounter-=4;
                player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                Optional<Item> milk = BuiltInRegistries.ITEM.getOptional(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).withPrefix("milk_"));
                if (milk.isPresent()) {
                    ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, player, milk.get().getDefaultInstance());
                    player.setItemInHand(interactionHand, itemStack2);
                    return InteractionResult.SUCCESS;
                }else {
                    return super.mobInteract(player, interactionHand);
                }
            }if (itemStack.is(Items.BOWL) && this.milkCounter >= 1){
                // Bowl milking: one quota unit per bowl
                this.milkCounter-=1;
                player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                itemStack.consume(1, player);
                player.getInventory().add(MMEItems.BOWL_MILK.getDefaultInstance());
                return InteractionResult.SUCCESS;
            }else {
                return super.mobInteract(player, interactionHand);
            }
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }
    /** Recovers one milk unit per 12000 ticks until the quota is full. */
    @Override
    public void tick(){
        super.tick();
        if (this.milkCounter < maxMilkCounter){
            this.recoveryCounter++;
        }
        if (this.recoveryCounter > 12000){
            this.milkCounter++;
            this.recoveryCounter -= 12000;
        }
    }

    /** Persists the milk quota counters and sickness state to NBT. */
    @Override
    public void addAdditionalSaveData(@NonNull ValueOutput compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("RecoveryCounter", recoveryCounter);
        compoundTag.putInt("MilkCounter", milkCounter);
        mme$sickness.save(compoundTag);
    }

    /** Restores the milk quota counters and sickness state from NBT. */
    @Override
    public void readAdditionalSaveData(@NonNull ValueInput compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.recoveryCounter = compoundTag.getInt("RecoveryCounter").orElse(0);
        this.milkCounter = compoundTag.getInt("MilkCounter").orElse(recoveryCounter);
        mme$sickness.load(compoundTag);
    }

    /**
     * A sick animal yields nothing on death; the vanilla loot path is skipped entirely.
     */
    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        if (AnimalSicknessLogic.isSick(this)) {
            return;
        }
        super.dropAllDeathLoot(level, damageSource);
    }
}
