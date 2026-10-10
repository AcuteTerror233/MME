package com.acuteterror233.mite.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Mixin for {@code MouseHandler} — slows the player's view rotation while hindered:
 * each Slowness level cuts turn speed by 20% and standing in a cobweb cuts it by a
 * further 65%, with the total reduction capped at 95% so control is never lost entirely.
 */
@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    /** Turn-speed reduction per Slowness level (amplifier 0 = Slowness I). */
    @Unique
    private static final float MME$SLOWNESS_REDUCTION_PER_LEVEL = 0.20F;
    /** Additional turn-speed reduction while inside a cobweb. */
    @Unique
    private static final float MME$COBWEB_REDUCTION = 0.65F;
    /** Upper bound for the total accumulated turn-speed reduction. */
    @Unique
    private static final float MME$MAX_REDUCTION = 0.95F;

    @Final
    @Shadow
    private Minecraft minecraft;

    /**
     * Scales the yaw/pitch deltas handed to {@code LocalPlayer.turn} by the hindrance factor;
     * the normal, scoping and smooth-camera paths all funnel into this single call.
     */
    @WrapOperation(
        method = "turnPlayer(D)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V")
    )
    private void mme$slowTurn(LocalPlayer player, double yawDelta, double pitchDelta, Operation<Void> original) {
        float reduction = 0.0F;
        MobEffectInstance slowness = player.getEffect(MobEffects.SLOWNESS);
        if (slowness != null) {
            reduction = Math.min(MME$MAX_REDUCTION, (slowness.getAmplifier() + 1) * MME$SLOWNESS_REDUCTION_PER_LEVEL);
        }
        if (mme$isInCobweb(player)) {
            reduction = Math.min(MME$MAX_REDUCTION, reduction + MME$COBWEB_REDUCTION);
        }
        if (reduction <= 0.0F) {
            original.call(player, yawDelta, pitchDelta);
            return;
        }
        double factor = 1.0D - reduction;
        original.call(player, yawDelta * factor, pitchDelta * factor);
    }

    /**
     * @return whether any block cell intersecting the player's bounding box is a web block.
     * Mirrors vanilla {@code checkInsideBlocks} semantics — the cobweb slow already kicks in
     * while the body merely touches the web cell, so feet/eye point sampling alone would miss it.
     */
    @Unique
    private static boolean mme$isInCobweb(LocalPlayer player) {
        AABB box = player.getBoundingBox();
        BlockPos minPos = BlockPos.containing(box.minX, box.minY, box.minZ);
        BlockPos maxPos = BlockPos.containing(box.maxX, box.maxY, box.maxZ);
        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            if (player.level().getBlockState(pos).getBlock() instanceof WebBlock) {
                return true;
            }
        }
        return false;
    }
}
