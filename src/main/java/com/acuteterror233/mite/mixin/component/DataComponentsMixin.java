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

/**
 * Mixin for {@code DataComponents} — shrinks the default stack size of all items from 64 to 4.
 *
 * <p>The static final {@code COMMON_ITEM_COMPONENTS} map is shadowed {@code @Mutable} and
 * re-initialized at class load with MITE's default component set: identical to vanilla except
 * {@code MAX_STACK_SIZE} defaults to 4, so every item without an explicit stack-size component
 * stacks far lower. The shadowed component types above mirror the vanilla fields used to rebuild
 * the map. Applies during class initialization on both sides.</p>
 */
@Mixin(DataComponents.class)
public class DataComponentsMixin {
    /** Shadowed vanilla component type (referenced when rebuilding the default map). */
    @Final
    @Shadow
    public static DataComponentType<Integer> MAX_STACK_SIZE;
    /** Shadowed vanilla component type. */
    @Final
    @Shadow
    public static DataComponentType<ItemLore> LORE;
    /** Shadowed vanilla component type. */
    @Final
    @Shadow
    public static DataComponentType<ItemEnchantments> ENCHANTMENTS;
    /** Shadowed vanilla component type. */
    @Final
    @Shadow
    public static DataComponentType<Integer> REPAIR_COST;
    /** Shadowed vanilla component type. */
    @Final
    @Shadow
    public static DataComponentType<ItemAttributeModifiers> ATTRIBUTE_MODIFIERS;
    /** Shadowed vanilla component type. */
    @Final
    @Shadow
    public static DataComponentType<Rarity> RARITY;
    /** Shadowed vanilla component type. */
    @Final
    @Shadow
    public static DataComponentType<Holder<SoundEvent>> BREAK_SOUND;
    /** Shadowed vanilla component type. */
    @Final
    @Shadow
    public static DataComponentType<TooltipDisplay> TOOLTIP_DISPLAY;

    /** Replacement default component map: vanilla set, but items stack to 4 instead of 64. */
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
