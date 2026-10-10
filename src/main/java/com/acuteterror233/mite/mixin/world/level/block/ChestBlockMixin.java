package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.world.effect.curse.MMECurses;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code ChestBlock} — CHEST_FEAR carriers cannot open chests (trapped chests
 * included, they inherit the override). On the client the interaction is returned as
 * CONSUME (instead of FAIL) so the use packet still reaches the server, which then
 * reveals the curse.
 */
@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin {

    /** Blocks opening chests for CHEST_FEAR carriers and reveals the curse server-side. */
    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void mme$chestFear(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (!player.hasEffect(MMECurses.CHEST_FEAR)) {
            return;
        }
        if (level.isClientSide()) {
            cir.setReturnValue(InteractionResult.CONSUME);
        } else {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
