package com.acuteterror233.mite.mixin.component;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DataComponents.class)
public class DataComponentsMixin {
    @Final
    @Shadow
    public static DataComponentType<Integer> MAX_STACK_SIZE;
    @Final
    @Shadow
    public static DataComponentType<ItemLore> LORE;
    @Final
    @Shadow
    public static DataComponentType<ItemEnchantments> ENCHANTMENTS;
    @Final
    @Shadow
    public static DataComponentType<Integer> REPAIR_COST;
    @Final
    @Shadow
    public static DataComponentType<ItemAttributeModifiers> ATTRIBUTE_MODIFIERS;
    @Final
    @Shadow
    public static DataComponentType<Rarity> RARITY;
    @Final
    @Shadow
    public static DataComponentType<Holder<SoundEvent>> BREAK_SOUND;
    @Final
    @Shadow
    public static DataComponentType<TooltipDisplay> TOOLTIP_DISPLAY;

    @Final
    @Shadow
    @Mutable
    public static DataComponentMap COMMON_ITEM_COMPONENTS = DataComponentMap.builder()
            .set(MAX_STACK_SIZE, 4)
            .set(LORE, ItemLore.EMPTY)
            .set(ENCHANTMENTS, ItemEnchantments.EMPTY)
            .set(REPAIR_COST, 0)
            .set(ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY)
            .set(RARITY, Rarity.COMMON)
            .set(BREAK_SOUND, SoundEvents.ITEM_BREAK)
            .set(TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT)
            .build();
}
