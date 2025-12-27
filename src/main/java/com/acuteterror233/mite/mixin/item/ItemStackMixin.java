package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.Mme;
import com.acuteterror233.mite.component.AtDataComponentTypes;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin  implements ComponentHolder, FabricItemStack {

    @Shadow
    public abstract int getDamage();

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    @Nullable
    public abstract <T> T set(ComponentType<T> type, @Nullable T value);

    @Inject(method = "appendAttributeModifierTooltip", at = @At("HEAD"), cancellable = true)
    private void appendAttributeModifierTooltip(
            Consumer<Text> textConsumer, @Nullable PlayerEntity player, RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier, CallbackInfo ci
    ) {
        if (player != null) {
            if (modifier.idMatches(Mme.BASE_BLOCK_INTERACTION_RANGE) || modifier.idMatches(Mme.BASE_ENTITY_INTERACTION_RANGE)) {
                ci.cancel();
            }
        }
    }
    @Inject(method = "damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V", at = @At("HEAD"))
    private void damage(int amount, LivingEntity entity, EquipmentSlot slot, CallbackInfo ci) {
        if (this.contains(DataComponentTypes.EQUIPPABLE) && this.contains(DataComponentTypes.ATTRIBUTE_MODIFIERS) && this.contains(AtDataComponentTypes.ORIGINAL_ARMOR) && this.get(DataComponentTypes.EQUIPPABLE).slot().getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
            float Damage = (this.getMaxDamage() - (float) this.getDamage()) / this.getMaxDamage();
            AttributeModifiersComponent component = this.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            component.modifiers().forEach(entry -> {
                if (entry.attribute() == EntityAttributes.ARMOR){
                    builder.add(
                            entry.attribute(),
                            new EntityAttributeModifier(entry.modifier().id(), this.get(AtDataComponentTypes.ORIGINAL_ARMOR) * Damage, EntityAttributeModifier.Operation.ADD_VALUE),
                            entry.slot());
                }else {
                    builder.add(entry.attribute(), entry.modifier(), entry.slot());
                }
            });
            this.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
        }
    }
}
