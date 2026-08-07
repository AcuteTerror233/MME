package com.acuteterror233.mite.item;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
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

/**
 * 硬币物品，继承 {@link Item}。
 * 用作货币的堆叠物品。
 */
public class CoinsItem extends Item {
    private final int experience;
    public static final Map<Identifier, Identifier> COINS_EXCHANGEITEM = new HashMap<>(){{
        put(Identifier.fromNamespaceAndPath(MME.MOD_ID, "adamantium_coins"), Identifier.fromNamespaceAndPath(MME.MOD_ID, "adamantium_nugget"));
        put(Identifier.fromNamespaceAndPath(MME.MOD_ID, "mithril_coins"), Identifier.fromNamespaceAndPath(MME.MOD_ID, "mithril_nugget"));
        put(Identifier.fromNamespaceAndPath(MME.MOD_ID, "ancient_metal_coins"), Identifier.fromNamespaceAndPath(MME.MOD_ID, "ancient_metal_nugget"));
        put(Identifier.fromNamespaceAndPath(MME.MOD_ID, "iron_coins"), Identifier.withDefaultNamespace("iron_nugget"));
        put(Identifier.fromNamespaceAndPath(MME.MOD_ID, "silver_coins"), Identifier.fromNamespaceAndPath(MME.MOD_ID, "silver_nugget"));
        put(Identifier.fromNamespaceAndPath(MME.MOD_ID, "copper_coins"), Identifier.withDefaultNamespace("copper_nugget"));
        put(Identifier.fromNamespaceAndPath(MME.MOD_ID, "gold_coins"), Identifier.withDefaultNamespace("gold_nugget"));
    }};
    public CoinsItem(Properties settings, int experience) {
        super(settings);
        this.experience = experience;
    }
    @Override
    public @NotNull InteractionResult use(Level world, Player user, InteractionHand hand) {
        user.giveExperiencePoints(this.experience);
        Item item = BuiltInRegistries.ITEM.getValue(COINS_EXCHANGEITEM.get(builtInRegistryHolder().key().identifier()));
        ItemStack stack = new ItemStack(item);
        user.getInventory().add(stack);
        user.getItemInHand(hand).consume(1, user);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, user.getSoundSource(), 1.0F, 1.0F);
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept(Component.translatable("mme.coins.tooltip", this.experience));
    }
}
