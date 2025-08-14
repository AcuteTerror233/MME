package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.At_mite;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Overwrite
    private void appendAttributeModifierTooltip(
            Consumer<Text> textConsumer, @Nullable PlayerEntity player, RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier
    ) {
        double d = modifier.value();
        boolean bl = false;
        if (player != null) {
            if (modifier.idMatches(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID)) {
                d += player.getAttributeBaseValue(EntityAttributes.ATTACK_DAMAGE);
                bl = true;
            } else if (modifier.idMatches(Item.BASE_ATTACK_SPEED_MODIFIER_ID)) {
                d += player.getAttributeBaseValue(EntityAttributes.ATTACK_SPEED);
                bl = true;
            } else if (modifier.idMatches(At_mite.BASE_BLOCK_INTERACTION_RANGE)) {
                d += player.getAttributeBaseValue(EntityAttributes.BLOCK_INTERACTION_RANGE);
                bl = true;
            } else if (modifier.idMatches(At_mite.BASE_ENTITY_INTERACTION_RANGE)) {
                d += player.getAttributeBaseValue(EntityAttributes.ENTITY_INTERACTION_RANGE);
                bl = true;
            }
        }

        double e;
        if (modifier.operation() == EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                || modifier.operation() == EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
            e = d * 100.0;
        } else if (attribute.matches(EntityAttributes.KNOCKBACK_RESISTANCE)) {
            e = d * 10.0;
        } else {
            e = d;
        }

        if (bl) {
            textConsumer.accept(
                    ScreenTexts.space()
                            .append(
                                    Text.translatable(
                                            "attribute.modifier.equals." + modifier.operation().getId(),
                                            AttributeModifiersComponent.DECIMAL_FORMAT.format(e),
                                            Text.translatable(attribute.value().getTranslationKey())
                                    )
                            )
                            .formatted(Formatting.DARK_GREEN)
            );
        } else if (d > 0.0) {
            textConsumer.accept(
                    Text.translatable(
                                    "attribute.modifier.plus." + modifier.operation().getId(),
                                    AttributeModifiersComponent.DECIMAL_FORMAT.format(e),
                                    Text.translatable(attribute.value().getTranslationKey())
                            )
                            .formatted(attribute.value().getFormatting(true))
            );
        } else if (d < 0.0) {
            textConsumer.accept(
                    Text.translatable(
                                    "attribute.modifier.take." + modifier.operation().getId(),
                                    AttributeModifiersComponent.DECIMAL_FORMAT.format(-e),
                                    Text.translatable(attribute.value().getTranslationKey())
                            )
                            .formatted(attribute.value().getFormatting(false))
            );
        }
    }
}
