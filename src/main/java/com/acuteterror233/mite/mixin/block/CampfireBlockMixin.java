package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.CampfireBlockEntityExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    /**
     * @author AcuteTerror233
     * @reason 添加了添加燃料的判定
     */
    @Overwrite
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof CampfireBlockEntity campfireBlockEntity) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (world.recipeAccess().propertySet(RecipePropertySet.CAMPFIRE_INPUT).test(itemStack)) {
                if (world instanceof ServerLevel serverWorld && campfireBlockEntity.placeFood(serverWorld, player, itemStack)) {
                    player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return InteractionResult.SUCCESS_SERVER;
                }
                return InteractionResult.CONSUME;
            } else if (world.fuelValues().isFuel(itemStack)){
                if (world instanceof ServerLevel && campfireBlockEntity instanceof CampfireBlockEntityExtension blockEntity) {
                    blockEntity.MME$AddRemainingIgnitionTime(world.fuelValues().burnDuration(itemStack));
                    if (!itemStack.getRecipeRemainder().isEmpty()) {
                        player.setItemInHand(hand, itemStack.getRecipeRemainder());
                    }else {
                        itemStack.consumeAndReturn(1, player);
                    }
                    player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return InteractionResult.SUCCESS_SERVER;
                }
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }
}
