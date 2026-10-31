package com.acuteterror233.mite.mixin.world.entity.projectile;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code FishingHook} — Keeps the bobber attached while any tagged fishing rod
 * (main or off hand) is held within 32 blocks of the owner, and re-anchors the fish-wait
 * roll to 2400-6000 ticks (2-5 minutes) instead of vanilla 100-600 (5-30 seconds).
 */
@Mixin(FishingHook.class)
public abstract class FishingBobberEntityMixin extends Projectile {
    public FishingBobberEntityMixin(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    /**
     * Keeps the bobber attached while any item tagged {@code FISHING_RODS} is held, replacing
     * vanilla's exact-item check.
     *
     * <p>Written as a HEAD-cancellable inject rather than an {@code @Overwrite}: an overwrite
     * deletes the vanilla body, which is the target of fabric-item-api-v1's
     * {@code @WrapOperation} inside this method, so mixin application fails and the game
     * (and any test that bootstraps Minecraft) crashes.</p>
     */
    @Inject(method = "shouldStopFishing", at = @At("HEAD"), cancellable = true)
    private void mme$shouldStopFishing(Player player, CallbackInfoReturnable<Boolean> cir) {
        boolean mainHandRod = player.getMainHandItem().is(MMEItemTags.FISHING_RODS);
        boolean offHandRod = player.getOffhandItem().is(MMEItemTags.FISHING_RODS);
        if (!player.isRemoved() && player.isAlive() && (mainHandRod || offHandRod)
                && !(this.distanceToSqr(player) > 1024.0)) {
            cir.setReturnValue(false);
            return;
        }
        this.discard();
        cir.setReturnValue(true);
    }

    /**
     * Vanilla wait roll is {@code nextInt(100, 600) - lureSpeed}; re-anchors the lower bound
     * to 2400 ticks (2 minutes). The lure-speed subtraction is left untouched.
     */
    @ModifyConstant(method = "catchingFish", constant = @Constant(intValue = 100))
    private int mme$minFishWaitTicks(int original) {
        return 2400;
    }

    /**
     * Re-anchors the upper bound of the wait roll to 6000 ticks (5 minutes).
     */
    @ModifyConstant(method = "catchingFish", constant = @Constant(intValue = 600))
    private int mme$maxFishWaitTicks(int original) {
        return 6000;
    }
}
