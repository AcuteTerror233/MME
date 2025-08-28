package com.acuteterror233.mite.item;

import com.acuteterror233.mite.atinterface.FluidDrainableExtension;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidFillable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class AtBucketItem extends BucketItem {
    private final  Item item;
    private final  Fluid fluid;
    //注册名格式必须是"生物/液体_材质"
    public AtBucketItem(Fluid fluid, Settings settings, Item empty_barrel) {
        super(fluid, settings);
        this.fluid = fluid;
        this.item = empty_barrel;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(
                world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE
        );
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return ActionResult.PASS;
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return ActionResult.PASS;
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);
            if (!world.canEntityModifyAt(user, blockPos) || !user.canPlaceOn(blockPos2, direction, itemStack)) {
                return ActionResult.FAIL;
            } else if (this.fluid == Fluids.EMPTY) {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.getBlock() instanceof FluidDrainableExtension fluidDrainable) {
                    ItemStack itemStack2 = fluidDrainable.newtryDrainFluid(user, world, blockPos, blockState, this);
                    if (!itemStack2.isEmpty()) {
                        user.incrementStat(Stats.USED.getOrCreateStat(this));
                        fluidDrainable.getBucketFillSound().ifPresent(sound -> user.playSound(sound, 1.0F, 1.0F));
                        world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                        ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
                        if (!world.isClient) {
                            Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, itemStack2);
                        }

                        return ActionResult.SUCCESS.withNewHandStack(itemStack3);
                    }
                }

                return ActionResult.FAIL;
            } else {
                BlockState blockState = world.getBlockState(blockPos);
                BlockPos blockPos3 = blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER ? blockPos : blockPos2;
                if (this.placeFluid(user, world, blockPos3, blockHitResult)) {
                    this.onEmptied(user, world, itemStack, blockPos3);
                    if (user instanceof ServerPlayerEntity) {
                        Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, blockPos3, itemStack);
                    }
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, user, !user.isInCreativeMode() ? new ItemStack(item) : itemStack);
                    return ActionResult.SUCCESS.withNewHandStack(itemStack2);
                } else {
                    return ActionResult.FAIL;
                }
            }
        }
    }
}