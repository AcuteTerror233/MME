package com.acuteterror233.mite.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class BowlItem extends Item {
    public BowlItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
    ItemStack itemStack = player.getItemInHand(interactionHand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!level.mayInteract(player, blockPos)) {
                    return InteractionResult.PASS;
                }
                if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
                    return InteractionResult.SUCCESS
                            .heldItemTransformedTo(this.turnBowlIntoItem(itemStack, player, MMEItems.BOWL_WATER.getDefaultInstance()));
                }
            }

        }
        return InteractionResult.PASS;
    }

    protected ItemStack turnBowlIntoItem(ItemStack itemStack, Player player, ItemStack itemStack2) {
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.createFilledResult(itemStack, player, itemStack2);
    }
}
