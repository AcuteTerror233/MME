package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.world.effect.curse.MMECurses;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalDouble;
import java.util.function.BiConsumer;

/**
 * Mixin for {@code ItemStack} — Implements durability-based armor decay.
 * Every time the damage value changes, armor pieces (humanoid armor slots carrying both the
 * EQUIPPABLE and ATTRIBUTE_MODIFIERS components) have their ARMOR attribute modifier rewritten to
 * the product of the item's default armor value and a decay multiplier derived from the current
 * damage percentage, so armor protection fades as durability is consumed.
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin  implements DataComponentHolder, FabricItemStack {
    /** Re-entrancy guard for the doubled recursive {@code hurtAndBreak} call. */
    @Unique
    private static final ThreadLocal<Boolean> MME$CORROSIVE_REENTRY = ThreadLocal.withInitial(() -> false);

    @Shadow
    public abstract int getDamageValue();

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    @Nullable
    public abstract <T> T set(DataComponentType<T> type, @Nullable T value);

    @Shadow
    public abstract Item getItem();

    /** Hook on {@code setDamageValue}: any durability change re-applies the decayed armor modifiers. */
    @Inject(method = "setDamageValue", at = @At("TAIL"))
    private void setDamageValue(int value, CallbackInfo ci) {
        armorChanges();
    }

    /**
     * Rebuilds the ATTRIBUTE_MODIFIERS component for armor pieces: the ARMOR entry is replaced with
     * {@code originalArmor * getArmorMultiplier()} (amount read from the item's pristine default
     * instance so decay is always relative to full protection), all other modifiers are copied.
     * No-op for non-armor or armor without attribute modifiers.
     */
    @Unique
    private void armorChanges() {
        if (this.has(DataComponents.EQUIPPABLE) && this.has(DataComponents.ATTRIBUTE_MODIFIERS) && this.get(DataComponents.EQUIPPABLE).slot().getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
            ItemAttributeModifiers component = this.get(DataComponents.ATTRIBUTE_MODIFIERS);
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            OptionalDouble optionalDouble = getItem()
                    .getDefaultInstance()
                    .get(DataComponents.ATTRIBUTE_MODIFIERS)
                    .modifiers()
                    .stream()
                    .filter(entry -> entry.attribute().equals(Attributes.ARMOR))
                    .mapToDouble(e -> e.modifier().amount())
                    .findFirst();
            if (optionalDouble.isEmpty()) {
                return;
            }
            double originalArmor = optionalDouble.getAsDouble();

            component.modifiers().forEach(entry -> {
                if (entry.attribute().equals(Attributes.ARMOR)) {
                    builder.add(
                            entry.attribute(),
                            new AttributeModifier(entry.modifier().id(), originalArmor * getArmorMultiplier(), AttributeModifier.Operation.ADD_VALUE),
                            entry.slot());
                } else {
                    builder.add(entry.attribute(), entry.modifier(), entry.slot());
                }
            });
            this.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());
        }
    }

    /**
     * @return decay multiplier for the ARMOR attribute from the durability percentage:
     * below 20% damage → 1.0 (full protection); 20%–80% → linear decay from 1.0 down to 0.0;
     * 80%+ → 0.0 (armor fully ineffective).
     */
    @Unique
    private float getArmorMultiplier() {
        int currentDamage = this.getDamageValue();
        int maxDamage = this.getMaxDamage();
        int durabilityPercentage = (currentDamage * 100) / maxDamage;
        // Calculate armor value decay multiplier
        float armorMultiplier;
        if (durabilityPercentage >= 20 && durabilityPercentage < 80) {
            // 20%-80% linear decay: from 1.0 down to 0.0
            armorMultiplier = 1.0f - ((float)(durabilityPercentage - 20) / 60.0f);
        } else if (durabilityPercentage >= 80) {
            // Above 80%, completely ineffective
            armorMultiplier = 0.0f;
        } else {
            armorMultiplier = 1.0f;
        }
        return armorMultiplier;
    }

    /** MITE Protection: armor-value bonus fraction of the enchanted piece per level (12.5%). */
    @Unique
    private static final double MME$PROTECTION_ARMOR_BONUS_PER_LEVEL = 0.125D;

    /**
     * Injected at the tail of the equipment-attribute application pass: grants the MITE-style
     * Protection effect. Instead of the vanilla flat damage reduction (stripped from the vanilla
     * {@code protection.json} override), each level of Protection increases the armor value of the
     * enchanted piece itself by 12.5% (level IV = +50%). The bonus is layered onto the current
     * (durability-decayed) armor amount, so a worn-out piece also grants a proportionally weaker
     * enchantment bonus. One distinct modifier id per armor slot keeps the four pieces' bonuses
     * additive instead of overwriting each other in the shared attribute map.
     *
     * @param slot     the equipment slot whose attribute modifiers are being (re)applied
     * @param consumer the vanilla modifier sink feeding the living entity's attribute map
     * @param ci       injection callback (unused; never cancelled)
     */
    @Inject(method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V", at = @At("TAIL"))
    private void mme$protectionArmorBonus(EquipmentSlot slot, BiConsumer<Holder<Attribute>, AttributeModifier> consumer, CallbackInfo ci) {
        if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) {
            return;
        }
        Identifier id = switch (slot) {
            case HEAD -> Identifier.withDefaultNamespace("protection_armor_bonus.head");
            case CHEST -> Identifier.withDefaultNamespace("protection_armor_bonus.chest");
            case LEGS -> Identifier.withDefaultNamespace("protection_armor_bonus.legs");
            case FEET -> Identifier.withDefaultNamespace("protection_armor_bonus.feet");
            default -> null;
        };
        if (id == null) {
            return;
        }
        ItemEnchantments enchantments = this.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null) {
            return;
        }
        int level = 0;
        for (Holder<Enchantment> enchantment : enchantments.keySet()) {
            if (Enchantments.PROTECTION.equals(enchantment.unwrapKey().orElse(null))) {
                level = enchantments.getLevel(enchantment);
                break;
            }
        }
        if (level <= 0) {
            return;
        }
        ItemAttributeModifiers component = this.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (component == null) {
            return;
        }
        double baseArmor = 0.0D;
        for (ItemAttributeModifiers.Entry entry : component.modifiers()) {
            if (entry.attribute().equals(Attributes.ARMOR)) {
                baseArmor += entry.modifier().amount();
            }
        }
        if (baseArmor <= 0.0D) {
            return;
        }
        consumer.accept(Attributes.ARMOR, new AttributeModifier(id,
                baseArmor * MME$PROTECTION_ARMOR_BONUS_PER_LEVEL * level, AttributeModifier.Operation.ADD_VALUE));
    }

    /** Doubles durability loss for CORROSIVE_SKIN carriers. */
    @Inject(method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V",
            at = @At("HEAD"), cancellable = true)
    private void mme$corrosiveSkin(int amount, LivingEntity entity, EquipmentSlot slot, CallbackInfo ci) {
        if (MME$CORROSIVE_REENTRY.get() || !(entity instanceof Player player)
                || !player.hasEffect(MMECurses.CORROSIVE_SKIN)) {
            return;
        }
        MME$CORROSIVE_REENTRY.set(true);
        try {
            ((ItemStack) (Object) this).hurtAndBreak(amount * 2, entity, slot);
        } finally {
            MME$CORROSIVE_REENTRY.set(false);
        }
        ci.cancel();
    }
}
