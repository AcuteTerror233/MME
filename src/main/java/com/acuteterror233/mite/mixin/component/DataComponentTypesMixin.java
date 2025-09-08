package com.acuteterror233.mite.mixin.component;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DataComponentTypes.class)
public class DataComponentTypesMixin {
    @Final
    @Shadow public static ComponentType<Integer> MAX_STACK_SIZE;
    @Final
    @Shadow public static ComponentType<LoreComponent> LORE;
    @Final
    @Shadow public static ComponentType<ItemEnchantmentsComponent> ENCHANTMENTS;
    @Final
    @Shadow public static ComponentType<Integer> REPAIR_COST;
    @Final
    @Shadow public static ComponentType<AttributeModifiersComponent> ATTRIBUTE_MODIFIERS;
    @Final
    @Shadow public static ComponentType<Rarity> RARITY;
    @Final
    @Shadow public static ComponentType<RegistryEntry<SoundEvent>> BREAK_SOUND;
    @Final
    @Shadow public static ComponentType<TooltipDisplayComponent> TOOLTIP_DISPLAY;

    @Final
    @Shadow
    @Mutable
    public static ComponentMap DEFAULT_ITEM_COMPONENTS = ComponentMap.builder()
            .add(MAX_STACK_SIZE, 4)
            .add(LORE, LoreComponent.DEFAULT)
            .add(ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT)
            .add(REPAIR_COST, 0)
            .add(ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT)
            .add(RARITY, Rarity.COMMON)
            .add(BREAK_SOUND, SoundEvents.ENTITY_ITEM_BREAK)
            .add(TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT)
            .build();
}
