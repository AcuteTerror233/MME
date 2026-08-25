package com.acuteterror233.mite.mixin.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code ShearsItem} — 修改剪刀挖掘速度判定。
 */
@Mixin(ShearsItem.class)
public class ShearsItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.is(BlockTags.LEAVES)){
            if (!level.isClientSide()){
                ItemStack itemStack = context.getItemInHand();
                Player player = context.getPlayer();
                if (player != null) {
                    itemStack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                }
                level.destroyBlock(blockPos, false);
                Block.dropResources(blockState, level, blockPos, null, player, itemStack);
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
