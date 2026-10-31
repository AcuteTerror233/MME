package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.acuteterror233.mite.world.effect.MMEMobEffects;
import com.acuteterror233.mite.world.effect.curse.MMECurses;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalDouble;
import java.util.function.BiConsumer;

/**
 * Mixin for {@code ItemStack} — Implements durability-based armor decay.
 * Every time the damage value changes, armor pieces (humanoid armor slots carrying both the
 * EQUIPPABLE and ATTRIBUTE_MODIFIERS components) have their ARMOR attribute modifier rewritten to
 * the product of the item's default armor value and a decay multiplier derived from the current
 * damage percentage, so armor protection fades as durability is consumed.
 *
 * <p>Two further hooks share this class: {@code forEachModifier} grants the MITE-style Protection
 * armor bonus on the enchanted piece, and {@code hurtAndBreak} doubles durability loss for
 * CORROSIVE_SKIN carriers.</p>
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin  implements DataComponentHolder, FabricItemStack {
    /** Re-entrancy guard for the doubled recursive {@code hurtAndBreak} call. */
    @Unique
    private static final ThreadLocal<Boolean> MME$CORROSIVE_REENTRY = ThreadLocal.withInitial(() -> false);

    /** Damage percentage at or below which an armor piece still grants its full armor value (50% durability or better). */
    @Unique
    private static final int FULL_ARMOR_DAMAGE_PERCENT = 50;
    /** Damage percentage at or above which an armor piece grants no armor value at all (5% durability or worse). */
    @Unique
    private static final int ZERO_ARMOR_DAMAGE_PERCENT = 95;

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
     * @return decay multiplier for the ARMOR attribute, stated on the piece's remaining
     * durability: at 50% durability or better the piece still grants its full armor value
     * (1.0); from 50% down to 5% durability the value decays linearly to 0.0; at 5%
     * durability or worse the piece grants no armor at all.
     */
    @Unique
    private float getArmorMultiplier() {
        int currentDamage = this.getDamageValue();
        int maxDamage = this.getMaxDamage();
        // The thresholds are stated on the durability axis (50% and 5% remaining), which
        // read as damage percentages 50 and 95 here.
        int damagePercentage = (currentDamage * 100) / maxDamage;
        if (damagePercentage <= FULL_ARMOR_DAMAGE_PERCENT) {
            return 1.0f;
        }
        if (damagePercentage >= ZERO_ARMOR_DAMAGE_PERCENT) {
            return 0.0f;
        }
        // Linear across the 45-point band: 50% damage -> 1.0, 95% damage -> 0.0
        return 1.0f - ((float) (damagePercentage - FULL_ARMOR_DAMAGE_PERCENT)
                / (ZERO_ARMOR_DAMAGE_PERCENT - FULL_ARMOR_DAMAGE_PERCENT));
    }

    /**
     * Stretches the use duration of sugary food while the eater is diabetic: the vanilla
     * consume time is multiplied by 5/10/20 for diabetes tier 1/2/3. Only
     * {@code #mme:sugary_foods} is affected, so a diabetic's non-sweet food still goes down at
     * the normal pace. The multiplier is applied on both sides, so the client's eating
     * animation and the server's consumption tick stay in sync.
     *
     * @param entity the entity about to use the stack (a player, for the diabetes check)
     * @param cir    return-value callback carrying the vanilla use duration in ticks
     */
    @Inject(method = "getUseDuration(Lnet/minecraft/world/entity/LivingEntity;)I", at = @At("RETURN"), cancellable = true)
    private void mme$slowSugarEating(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int vanillaTicks = cir.getReturnValue();
        if (vanillaTicks <= 0 || !(entity instanceof Player player)) {
            return;
        }
        if (!this.getItem().builtInRegistryHolder().is(MMEItemTags.SUGARY_FOODS)) {
            return;
        }
        MobEffectInstance diabetes = player.getEffect(MMEMobEffects.INSULIN_RESISTANCE);
        if (diabetes == null) {
            return;
        }
        cir.setReturnValue(vanillaTicks * MMEMobEffects.sugarEatingPenaltyMultiplier(diabetes.getAmplifier()));
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
