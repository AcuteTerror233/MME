package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.At_mite;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
/*
  Mixin: 过滤展示在物品提示中的“基础交互范围”属性修饰条目，避免干扰。
 */
public abstract class ItemStackMixin {

    @Inject(method = "appendAttributeModifierTooltip", at = @At("HEAD"), cancellable = true)
    /*
      在附加属性修饰器提示前拦截，隐藏与交互距离相关的两项条目。
     */
    private void appendAttributeModifierTooltip(
            Consumer<Text> textConsumer, @Nullable PlayerEntity player, RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier, CallbackInfo ci
    ) {
        if (player != null) {
            if (modifier.idMatches(At_mite.BASE_BLOCK_INTERACTION_RANGE) || modifier.idMatches(At_mite.BASE_ENTITY_INTERACTION_RANGE)) {
                ci.cancel();
            }
        }
    }
}
