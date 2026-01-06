package com.acuteterror233.mite.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MMEPowderSnowBucketItem extends SolidBucketItem {
    Item bucket;

    public MMEPowderSnowBucketItem(Block block, SoundEvent placeSound, Properties settings, Item bucket) {
        super(block, placeSound, settings);
        this.bucket = bucket;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        InteractionResult actionResult = super.useOn(context);
        Player playerEntity = context.getPlayer();
        if (actionResult.consumesAction() && playerEntity != null) {
            playerEntity.setItemInHand(context.getHand(), !playerEntity.hasInfiniteMaterials() ? new ItemStack(bucket) : context.getItemInHand());
        }
        return actionResult;
    }
}
