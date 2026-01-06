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

public class NuggetItem extends Item {
    private final int experience;
    public NuggetItem(Properties settings, int experience) {
        super(settings);
        this.experience = experience;
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (user.totalExperience >= this.experience) {
            user.giveExperiencePoints(-this.experience);
            Item item = BuiltInRegistries.ITEM.getValue(NUGGET_EXCHANGEITEM.get(builtInRegistryHolder().key().location()));
            user.getInventory().add(new ItemStack(item));
            user.getItemInHand(hand).consume(1, user);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept(Component.translatable("mme.nugget.tooltip", this.experience));
    }
    public static final Map<ResourceLocation, ResourceLocation> NUGGET_EXCHANGEITEM = new HashMap<>(){{
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "adamantium_nugget"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "adamantium_coins"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "mithril_nugget"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "mithril_coins"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "ancient_metal_nugget"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "ancient_metal_coins"));
        put(ResourceLocation.withDefaultNamespace("iron_nugget"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "iron_coins"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "silver_nugget"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "silver_coins"));
        put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "copper_nugget"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "copper_coins"));
        put(ResourceLocation.withDefaultNamespace("gold_nugget"), ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "gold_coins"));
    }};
}
