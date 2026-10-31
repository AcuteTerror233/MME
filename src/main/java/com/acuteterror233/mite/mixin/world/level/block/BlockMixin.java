package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Block} — makes breaking and placing solid blocks cost food exhaustion.
 *
 * <p>MITE-style hunger tuning: mining a solid, light-blocking block adds 2.0 exhaustion on top of
 * vanilla's mining cost ({@code playerDestroy}, server-side only). Placing a block charges the
 * placed block's {@code getBounceRestitution}, which is 0.0F for vanilla blocks unless a restitution
 * is explicitly configured (e.g. slime block 1.0F), so for most blocks the placement hook is a
 * no-op. Non-occluding blocks (plants, torches...) are exempt from both hooks. Only
 * {@code playerDestroy} is guaranteed server-side; {@code setPlacedBy} is called on both sides.</p>
 */
@Mixin(Block.class)
public class BlockMixin {
    /**
     * Injected at the return of {@code Block#playerDestroy}: charges 2.0 exhaustion when the
     * destroyed block state occludes (is a full solid block).
     *
     * @param level        the server level the block was mined in
     * @param player       the mining player
     * @param pos          the mined position
     * @param state        the state that was mined
     * @param blockEntity  block entity at the position, if any
     * @param destroyedWith the tool used to mine the block
     * @param ci           injection callback (unused; never cancelled)
     */
    @Inject(method = "playerDestroy", at = @At("RETURN"))
    private void playerDestroy(ServerLevel level, ServerPlayer player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack destroyedWith, CallbackInfo ci) {
        if (state.canOcclude()) {
            player.causeFoodExhaustion(2F);
        }
    }

    /**
     * Injected at the head of {@code Block#setPlacedBy}: charges the placed block's
     * {@code getBounceRestitution} (0.0F by default) when a player places an occluding block.
     *
     * @param level       the level the block was placed in
     * @param blockPos    the placed position
     * @param blockState  the placed state
     * @param livingEntity the placing entity (exhaustion only applies when a {@link Player})
     * @param itemStack   the item stack used for placing
     * @param ci          injection callback (unused; never cancelled)
     */
    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack, CallbackInfo ci) {
        if (livingEntity instanceof Player player && blockState.canOcclude()) {
            player.causeFoodExhaustion(blockState.getBlock().getBounceRestitution());
        }
    }
}
