package com.acuteterror233.mite.mixin.client;

import com.acuteterror233.mite.hotkey.MMEAutoModes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.phys.HitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Minecraft}: drives the AHM/AUM state machine every frame at the HEAD of
 * {@code handleKeybinds}, right before vanilla's keybind handling, so the forced attack/use key
 * states apply within the same frame and vanilla consumes any fresh clicks normally
 * (activating, cancelling or continuing manual play).
 *
 * <p>Also gives every block placement a 0.1s wind-up followed by a half-second backswing:
 * {@code startUseItem} is the single entry point for both click and held right-click triggers,
 * so gating it there paces placements (a block item in the main hand with a non-entity
 * crosshair) at once, while use actions with other main-hand items keep vanilla timing.
 * Vanilla triggers are deferred into the wind-up window and re-fired once it elapses;
 * afterwards the vanilla {@code rightClickDelay} write is lengthened so follow-up triggers stay
 * throttled. A committed trigger fires even if the key is released mid-wind-up, and triggers
 * landing during the backswing are committed and honored the instant it elapses, so held or
 * spam-clicked input chains placements back-to-back.</p>
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    /** Ticks of wind-up before a triggered placement actually fires (2 = 0.1s). */
    private static final int USE_WIND_UP_TICKS = 2;

    /** Half a second between placement triggers (vanilla re-fires the held key every 4 ticks). */
    private static final int USE_COOLDOWN_TICKS = 10;

    @Shadow
    private int rightClickDelay;

    /** Remaining wind-up ticks before a deferred use action fires; 0 = idle. */
    @Unique
    private int mme$useWindUpTicks;

    /** Set when a use trigger lands during the backswing; honored as soon as it elapses. */
    @Unique
    private boolean mme$committedUse;

    /** Marks the deferred re-fire so the trigger gate lets it through instead of re-deferring. */
    @Unique
    private boolean mme$firingUse;

    /**
     * Delegates to {@link MMEAutoModes} at the HEAD of {@code Minecraft.handleKeybinds}, before
     * vanilla consumes the click counters, so the forced attack/use key states apply in the same
     * frame.
     */
    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void mme$autoModes(CallbackInfo ci) {
        MMEAutoModes.tick((Minecraft) (Object) this);
    }

    /**
     * Runs at the HEAD of {@code Minecraft.handleKeybinds}: counts the wind-up down and re-fires
     * the deferred use action once it elapses; a trigger committed during the backswing is
     * honored as soon as {@code rightClickDelay} reaches 0, so held or spam-clicked input chains
     * into the next action without a gap.
     */
    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void mme$useWindUpTick(CallbackInfo ci) {
        if (this.mme$useWindUpTicks > 0) {
            if (--this.mme$useWindUpTicks == 0) {
                this.mme$fireDeferred();
            }
        } else if (this.mme$committedUse && this.rightClickDelay == 0) {
            this.mme$fireDeferred();
        }
    }

    /** Fires one deferred use action, bypassing the trigger gate for this single call. */
    @Unique
    private void mme$fireDeferred() {
        this.mme$committedUse = false;
        this.mme$firingUse = true;
        try {
            this.startUseItem();
        } finally {
            this.mme$firingUse = false;
        }
    }

    /**
     * @return whether the pending trigger qualifies for placement pacing: a block item held in
     * the main hand while the crosshair is not on an entity (block face or air/miss). Note that
     * right-clicking an interactive block with a block item in hand also qualifies, so those
     * interactions are paced as well.
     */
    @Unique
    private boolean mme$isPlacementUse() {
        Minecraft self = (Minecraft) (Object) this;
        return self.player != null
                && self.hitResult != null
                && self.hitResult.getType() != HitResult.Type.ENTITY
                && self.player.getMainHandItem().getItem() instanceof BlockItem;
    }

    /**
     * Gate at the HEAD of {@code Minecraft.startUseItem} (cancellable): swallows placement
     * triggers fired while the backswing is still counting down (they are committed for the next
     * free tick instead of being lost), and defers fresh triggers into the wind-up window instead
     * of firing them instantly. Use actions with other main-hand items pass straight through.
     */
    @Inject(method = "startUseItem", at = @At("HEAD"), cancellable = true)
    private void mme$useTriggerGate(CallbackInfo ci) {
        if (this.mme$firingUse) {
            return;
        }
        if (!this.mme$isPlacementUse()) {
            return;
        }
        if (this.rightClickDelay > 0) {
            this.mme$committedUse = true;
            ci.cancel();
            return;
        }
        if (this.mme$useWindUpTicks == 0) {
            this.mme$useWindUpTicks = USE_WIND_UP_TICKS;
        }
        ci.cancel();
    }

    /**
     * Rewrites the backswing length right after vanilla's own {@code rightClickDelay = 4} write in
     * {@code Minecraft.startUseItem} (an {@code @Inject} at the PUTFIELD with shift AFTER, because
     * a redirect would have to reproduce that write itself). Applies to placements only: 10 ticks
     * (0.5 s) instead of vanilla's 4.
     */
    @Inject(method = "startUseItem", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;rightClickDelay:I", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void mme$useBackswing(CallbackInfo ci) {
        if (this.mme$isPlacementUse()) {
            this.rightClickDelay = USE_COOLDOWN_TICKS;
        }
    }

    @Shadow
    protected abstract void startUseItem();
}
