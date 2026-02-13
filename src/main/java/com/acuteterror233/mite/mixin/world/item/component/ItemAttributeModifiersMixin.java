package com.acuteterror233.mite.mixin.world.item.component;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemAttributeModifiers.Display.Default.class)
public class ItemAttributeModifiersMixin {
    @Inject(method = "apply", at = @At("HEAD"), cancellable = true)
    private void appendAttributeModifierTooltip(
            Consumer<Component> textConsumer, @Nullable Player player, Holder<Attribute> attribute, AttributeModifier modifier, CallbackInfo ci
    ) {
        if (player != null) {
            if (modifier.is(MME.BASE_BLOCK_INTERACTION_RANGE) || modifier.is(MME.BASE_ENTITY_INTERACTION_RANGE)) {
                ci.cancel();
            }
        }
    }
}
