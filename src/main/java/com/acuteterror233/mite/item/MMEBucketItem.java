package com.acuteterror233.mite.item;

import com.acuteterror233.mite.interfaces.FluidDrainableExtension;
import net.minecraft.advancements.triggers.CriteriaTriggers;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

/**
 * MME 桶物品，继承 {@link BucketItem}。
 * 支持自定义材料（铜、银等）的桶装液体操作。
 */
public class MMEBucketItem extends BucketItem {
    private final Item empty_barrel;

    public MMEBucketItem(Fluid fluid, Properties settings, Item empty_barrel) {
        super(fluid, settings);
        this.empty_barrel = empty_barrel;
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(
                level, player, getFluidContext()
        );
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        }

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        }

        BlockPos pos = hitResult.getBlockPos();
        Direction direction = hitResult.getDirection();
        BlockPos directionOffsetPos = pos.relative(direction);
        if (level.mayInteract(player, pos) && player.mayUseItemAt(directionOffsetPos, direction, itemStack)) {
            BlockState clicked = level.getBlockState(pos);
            BlockPos placePos = clicked.getBlock() instanceof LiquidBlockContainer && this.content == Fluids.WATER ? pos : directionOffsetPos;
            if (this.emptyContents(player, level, placePos, hitResult)) {
                this.checkExtraContent(player, level, itemStack, placePos);
                if (player instanceof ServerPlayer serverPlayer && this.content != Fluids.EMPTY) {
                    CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, placePos, itemStack);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                ItemStack emptyResult = ItemUtils.createFilledResult(itemStack, player, getEmptyBarrelSuccessItem(itemStack, player));
                return InteractionResult.SUCCESS.heldItemTransformedTo(emptyResult);
            } else {
                if (this.content == Fluids.EMPTY) {
                    BlockState blockState = level.getBlockState(pos);
                    if (blockState.getBlock() instanceof FluidDrainableExtension bucketPickupBlock) {
                        ItemStack taken = bucketPickupBlock.MME$TakeFluid(player, level, pos, blockState, this);
                        if (!taken.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            bucketPickupBlock.MME$GetBucketFillSound().ifPresent(soundEvent -> player.playSound(soundEvent, 1.0F, 1.0F));
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
                            ItemStack result = ItemUtils.createFilledResult(itemStack, player, taken);
                            if (!level.isClientSide()) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, taken);
                            }

                            return InteractionResult.SUCCESS.heldItemTransformedTo(result);
                        }
                    }
                }

                return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }
    public ItemStack getEmptyBarrelSuccessItem ( final ItemStack itemStack, final Player player){
        return !player.hasInfiniteMaterials() ? new ItemStack(this.empty_barrel) : itemStack;
    }
}