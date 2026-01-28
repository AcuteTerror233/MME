package com.acuteterror233.mite.item;

import com.acuteterror233.mite.atinterface.FluidDrainableExtension;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class MMEBucketItem extends BucketItem {
    private final Item empty_barrel;
    private final Fluid fluid;

    // 注册名格式必须是"生物/液体_材质"
    public MMEBucketItem(Fluid fluid, Properties settings, Item empty_barrel) {
        super(fluid, settings);
        this.fluid = fluid;
        this.empty_barrel = empty_barrel;
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(
                world, user, this.fluid == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE
        );
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!world.mayInteract(user, blockPos) || !user.mayUseItemAt(blockPos2, direction, itemStack)) {
                return InteractionResult.FAIL;
            } else if (this.fluid == Fluids.EMPTY) {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.getBlock() instanceof FluidDrainableExtension fluidDrainable) {
                    ItemStack itemStack2 = fluidDrainable.MME$TakeFluid(user, world, blockPos, blockState, this);
                    if (!itemStack2.isEmpty()) {
                        user.awardStat(Stats.ITEM_USED.get(this));
                        fluidDrainable.MME$GetBucketFillSound().ifPresent(sound -> user.playSound(sound, 1.0F, 1.0F));
                        world.gameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                        ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, user, itemStack2);
                        if (!world.isClientSide) {
                            CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) user, itemStack2);
                        }
                        return InteractionResult.SUCCESS.heldItemTransformedTo(itemStack3);
                    }
                }
                return InteractionResult.FAIL;
            } else {
                BlockState blockState = world.getBlockState(blockPos);
                BlockPos blockPos3 = blockState.getBlock() instanceof LiquidBlockContainer && this.fluid == Fluids.WATER ? blockPos : blockPos2;
                if (this.emptyContents(user, world, blockPos3, blockHitResult)) {
                    this.checkExtraContent(user, world, itemStack, blockPos3);
                    if (user instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) user, blockPos3, itemStack);
                    }
                    user.awardStat(Stats.ITEM_USED.get(this));
                    ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, user, !user.hasInfiniteMaterials() ? new ItemStack(empty_barrel) : itemStack);
                    return InteractionResult.SUCCESS.heldItemTransformedTo(itemStack2);
                } else {
                    return InteractionResult.FAIL;
                }
            }
        }
    }
}