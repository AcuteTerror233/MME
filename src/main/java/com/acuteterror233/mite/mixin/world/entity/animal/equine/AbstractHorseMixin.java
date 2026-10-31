package com.acuteterror233.mite.mixin.world.entity.animal.equine;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code AbstractHorse} — adds a feeding cooldown and raises the maximum temper of
 * every horse, donkey, mule and llama.
 *
 * <p>Feeding any food sets a 20-minute cooldown ({@link #DEFAULT_FEEDING_COOLING}); while it
 * runs {@code handleEating} is suppressed, so the horse ignores further food. The vanilla max
 * temper of 100 is raised to 300, making taming noticeably harder.</p>
 */
@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal implements PlayerRideableJumping, HasCustomInventoryScreen, OwnableEntity {

    protected AbstractHorseMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    /** Feeding cooldown applied after each feed, in ticks (24000 = 20 minutes). */
    @Unique
    private static final int DEFAULT_FEEDING_COOLING = 24000;
    /** Ticks left on the current feeding cooldown (0 = can be fed). */
    @Unique
    public int feedingCooling = 0;

    /** Counts the feeding cooldown down every tick. */
    @Override
    public void tick() {
        super.tick();
        if (feedingCooling > 0) {
            feedingCooling--;
        }
    }

    /** Starts a full feeding cooldown whenever food is actually consumed. */
    @Inject(method = "fedFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"))
    public void fedFood(Player player, ItemStack itemStack, CallbackInfoReturnable<InteractionResult> cir) {
        feedingCooling += DEFAULT_FEEDING_COOLING;
    }

    /** Suppresses eating while the feeding cooldown is still running. */
    @Inject(method = "handleEating", at = @At("HEAD"), cancellable = true)
    public void handleEating(Player player, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
        if (feedingCooling > 0) {
            cir.setReturnValue(false);
        }
    }

    /** Raises the horse max temper from the vanilla 100 to 300. */
    @Inject(method = "getMaxTemper", at = @At("TAIL"), cancellable = true)
    public void getMaxTemper(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(300);
    }

}
