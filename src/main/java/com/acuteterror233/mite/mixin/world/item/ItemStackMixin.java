package com.acuteterror233.mite.mixin.world.item;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code ItemStack} — 扩展物品堆行为。
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin  implements DataComponentHolder, FabricItemStack {

    @Shadow
    public abstract int getDamageValue();

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    @Nullable
    public abstract <T> T set(DataComponentType<T> type, @Nullable T value);

    @Shadow
    public abstract Item getItem();

    @Inject(method = "setDamageValue", at = @At("TAIL"))
    private void setDamageValue(int damage, CallbackInfo ci) {
        armorChanges();
    }

    @Unique
    private void armorChanges() {
        if (this.has(DataComponents.EQUIPPABLE) && this.has(DataComponents.ATTRIBUTE_MODIFIERS) && this.get(DataComponents.EQUIPPABLE).slot().getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
            ItemAttributeModifiers component = this.get(DataComponents.ATTRIBUTE_MODIFIERS);
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            double originalArmor = getItem()
                    .getDefaultInstance()
                    .get(DataComponents.ATTRIBUTE_MODIFIERS)
                    .modifiers()
                    .stream()
                    .filter(entry -> entry.attribute().equals(Attributes.ARMOR))
                    .mapToDouble(e -> e.modifier().amount())
                    .findFirst()
                    .orElseThrow();

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

    @Unique
    private float getArmorMultiplier() {
        int currentDamage = this.getDamageValue();
        int maxDamage = this.getMaxDamage();
        int durabilityPercentage = (currentDamage * 100) / maxDamage;
        // 计算护甲值衰减系数
        float armorMultiplier;
        if (durabilityPercentage >= 20 && durabilityPercentage < 80) {
            // 20%-80%线性衰减: 从1.0降到0.0
            armorMultiplier = 1.0f - ((float)(durabilityPercentage - 20) / 60.0f);
        } else if (durabilityPercentage >= 80) {
            // 80%以上完全失效
            armorMultiplier = 0.0f;
        } else {
            armorMultiplier = 1.0f;
        }
        return armorMultiplier;
    }
}
