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

/**
 * MME powder snow bucket item, extending {@link SolidBucketItem}.
 * Supports powder snow buckets made from custom materials.
 */
public class MMEPowderSnowBucketItem extends SolidBucketItem {
    Item bucket;

    /**
     * @param block      powder snow block placed on use
     * @param placeSound sound played when the content is placed
     * @param settings   item properties
     * @param bucket     empty bucket item handed back after placing (survival mode only)
     */
    public MMEPowderSnowBucketItem(Block block, SoundEvent placeSound, Properties settings, Item bucket) {
        super(block, placeSound, settings);
        this.bucket = bucket;
    }

    /** Places the powder snow, then swaps the held stack for the configured empty bucket (custom material support). */
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
