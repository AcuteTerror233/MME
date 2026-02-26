package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.component.MMEDataComponentTypes;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin  implements DataComponentHolder, FabricItemStack {

    @Shadow
    public abstract int getDamageValue();

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    @Nullable
    public abstract <T> T set(DataComponentType<T> type, @Nullable T value);

    @Inject(method = "addModifierTooltip", at = @At("HEAD"), cancellable = true)
    private void addModifierTooltip(
            Consumer<Component> textConsumer, @Nullable Player player, Holder<Attribute> attribute, AttributeModifier modifier, CallbackInfo ci
    ) {
        if (player != null) {
            if (modifier.is(MME.BASE_BLOCK_INTERACTION_RANGE) || modifier.is(MME.BASE_ENTITY_INTERACTION_RANGE)) {
                ci.cancel();
            }
        }
    }
    @Inject(method = "setDamageValue", at = @At("TAIL"))
    private void setDamageValue(int damage, CallbackInfo ci) {
        armorChanges();
    }

    @Unique
    private void armorChanges() {
        if (this.has(DataComponents.EQUIPPABLE) && this.has(DataComponents.ATTRIBUTE_MODIFIERS) && this.has(MMEDataComponentTypes.ORIGINAL_ARMOR) && this.get(DataComponents.EQUIPPABLE).slot().getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
            ItemAttributeModifiers component = this.get(DataComponents.ATTRIBUTE_MODIFIERS);
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            component.modifiers().forEach(entry -> {
                if (entry.attribute() == Attributes.ARMOR) {
                    builder.add(
                            entry.attribute(),
                            new AttributeModifier(entry.modifier().id(), this.get(MMEDataComponentTypes.ORIGINAL_ARMOR) * getArmorMultiplier(), AttributeModifier.Operation.ADD_VALUE),
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
