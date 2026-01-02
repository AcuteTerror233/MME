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

public class NuggetItem extends Item {
    private final int experience;
    public NuggetItem(Settings settings, int experience) {
        super(settings);
        this.experience = experience;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user.totalExperience >= this.experience) {
            user.addExperience(-this.experience);
            user.getStackInHand(hand).decrementUnlessCreative(1, user);
            user.getInventory().insertStack(new ItemStack(EXCHANGEITEM.getOrDefault(this, Items.AIR)));
            return ActionResult.CONSUME;
        }
        return ActionResult.FAIL;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept(Text.translatable("mme.nugget.tooltip", this.experience));
    }
    public static final Map<Item, Item> EXCHANGEITEM = new HashMap<>(){{
        put(AtItems.ADAMANTIUM_NUGGET, AtItems.ADAMANTIUM_COINS);
        put(AtItems.MITHRIL_NUGGET, AtItems.MITHRIL_COINS);
        put(AtItems.ANCIENT_METAL_NUGGET, AtItems.ANCIENT_METAL_COINS);
        put(Items.IRON_NUGGET, AtItems.IRON_COINS);
        put(AtItems.SILVER_NUGGET, AtItems.SILVER_COINS);
        put(AtItems.COPPER_NUGGET, AtItems.COPPER_COINS);
        put(Items.GOLD_NUGGET, AtItems.GOLDEN_COINS);
    }};
}
