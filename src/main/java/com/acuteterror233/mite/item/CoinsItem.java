package com.acuteterror233.mite.item;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CoinsItem extends Item {
    private final int experience;
    public CoinsItem(Settings settings, int experience) {
        super(settings);
        this.experience = experience;
    }
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.addExperience(this.experience);
        user.getStackInHand(hand).decrementUnlessCreative(1, user);
        user.getInventory().insertStack(new ItemStack(EXCHANGEITEM.getOrDefault(this, Items.AIR)));
        return ActionResult.CONSUME;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept(Text.translatable("mme.coins.tooltip", this.experience));
    }

    public static final Map<Item, Item> EXCHANGEITEM = new HashMap<>(){{
        put(AtItems.ADAMANTIUM_COINS, AtItems.ADAMANTIUM_NUGGET);
        put(AtItems.MITHRIL_COINS, AtItems.MITHRIL_NUGGET);
        put(AtItems.ANCIENT_METAL_COINS, AtItems.ANCIENT_METAL_NUGGET);
        put(AtItems.IRON_COINS, Items.IRON_NUGGET);
        put(AtItems.SILVER_COINS, AtItems.SILVER_NUGGET);
        put(AtItems.COPPER_COINS, AtItems.COPPER_NUGGET);
        put(AtItems.GOLDEN_COINS, Items.GOLD_NUGGET);
    }};
}
