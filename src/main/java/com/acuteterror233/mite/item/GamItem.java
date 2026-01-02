package com.acuteterror233.mite.item;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class GamItem extends Item {
    private final int experience;
    public GamItem(Settings settings, int experience) {
        super(settings);
        this.experience = experience;
    }
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.addExperience(experience);
        user.getStackInHand(hand).decrementUnlessCreative(1, user);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, user.getSoundCategory(), 1.0F, 1.0F);
        return ActionResult.CONSUME;
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept(Text.translatable("mme.coins.tooltip", this.experience));
    }
}
