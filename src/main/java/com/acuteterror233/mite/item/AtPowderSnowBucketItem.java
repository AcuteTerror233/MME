package com.acuteterror233.mite.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;

public class AtPowderSnowBucketItem extends PowderSnowBucketItem {
    Item item;
    public AtPowderSnowBucketItem(Block block, SoundEvent placeSound, Settings settings, Item item) {
        super(block, placeSound, settings);
        this.item = item;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult actionResult = super.useOnBlock(context);
        PlayerEntity playerEntity = context.getPlayer();
        if (actionResult.isAccepted() && playerEntity != null) {
            playerEntity.setStackInHand(context.getHand(), !playerEntity.isInCreativeMode() ? new ItemStack(item) : context.getStack());
        }
        return actionResult;
    }
}
