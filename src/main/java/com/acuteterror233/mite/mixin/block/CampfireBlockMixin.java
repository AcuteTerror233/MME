package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.GetIgnitionTimeExtension;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipePropertySet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    /**
     * @author AcuteTerror233
     * @reason 添加了添加燃料的判定
     */
    @Overwrite
    public ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof CampfireBlockEntity campfireBlockEntity) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (world.getRecipeManager().getPropertySet(RecipePropertySet.CAMPFIRE_INPUT).canUse(itemStack)) {
                if (world instanceof ServerWorld serverWorld && campfireBlockEntity.addItem(serverWorld, player, itemStack)) {
                    player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return ActionResult.SUCCESS_SERVER;
                }
                return ActionResult.CONSUME;
            } else if (world.getFuelRegistry().isFuel(itemStack)){
                if (world instanceof ServerWorld && campfireBlockEntity instanceof GetIgnitionTimeExtension blockEntity) {
                    blockEntity.addRemainingIgnitionTime(world.getFuelRegistry().getFuelTicks(itemStack));
                    if (!itemStack.getRecipeRemainder().isEmpty()) {
                        player.setStackInHand(hand, itemStack.getRecipeRemainder());
                    }else {
                        itemStack.splitUnlessCreative(1, player);
                    }
                    player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return ActionResult.SUCCESS_SERVER;
                }
                return ActionResult.CONSUME;
            }
        }

        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }
}
