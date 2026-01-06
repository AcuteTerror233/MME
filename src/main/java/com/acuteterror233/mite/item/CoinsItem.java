package com.acuteterror233.mite.item;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CoinsItem extends Item {
    private final int experience;
    public CoinsItem(Properties settings, int experience) {
        super(settings);
        this.experience = experience;
    }
    @Override
    public @NotNull InteractionResult use(Level world, Player user, InteractionHand hand) {
        user.giveExperiencePoints(this.experience);
        Item item = BuiltInRegistries.ITEM.getValue(COINS_EXCHANGEITEM.get(builtInRegistryHolder().key().location()));
        ItemStack stack = new ItemStack(item);
        user.getInventory().add(stack);
        user.getItemInHand(hand).consume(1, user);
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept(Component.translatable("mme.coins.tooltip", this.experience));
    }

    public static final Map<ResourceLocation, ResourceLocation> COINS_EXCHANGEITEM = new HashMap<>(){{
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "adamantium_coins"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "adamantium_nugget"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "mithril_coins"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "mithril_nugget"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "ancient_metal_coins"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "ancient_metal_nugget"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "iron_coins"), ResourceLocation.withDefaultNamespace("iron_nugget"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "silver_coins"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "silver_nugget"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "copper_coins"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "copper_nugget"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "gold_coins"), ResourceLocation.withDefaultNamespace("gold_nugget"));
    }};
}
