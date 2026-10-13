package com.acuteterror233.mite.mixin.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Rebalances the vanilla tool materials' enchantment value onto the MME tier scale —
 * 4 / 8 / 12 / 16 / 20, evenly spaced — so vanilla tools enchant like their MME counterparts:
 * copper {@code 13 -> 4} (bottom tier shared with silver and rusted iron), iron
 * {@code 14 -> 8}, gold {@code 22 -> 20} and netherite {@code 15 -> 20} (top tier shared with
 * adamantium). Wooden, stone and diamond tools are disabled in MME and left untouched.
 *
 * <p>The interception happens at the tail of {@code applyCommonProperties} — the single choke
 * point that writes the {@code ENCHANTABLE} item component — because the component is read
 * directly from the record field there, so overriding the {@code enchantmentValue()} accessor
 * would have no effect on item registration. Identity comparison against the vanilla constants
 * is safe here: this method only runs during item registration ({@code Items.<clinit>}), long
 * after the {@code ToolMaterial} constants have been fully constructed. MME's own materials are
 * unaffected (they do not match any vanilla constant and already carry the target values).</p>
 */
@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {
    @Inject(method = "applyCommonProperties", at = @At("RETURN"), cancellable = true)
    private void mme$rebalanceEnchantable(Item.Properties properties, CallbackInfoReturnable<Item.Properties> cir) {
        ToolMaterial self = (ToolMaterial) (Object) this;
        if (self == ToolMaterial.COPPER) {
            cir.setReturnValue(properties.enchantable(4));
        } else if (self == ToolMaterial.IRON) {
            cir.setReturnValue(properties.enchantable(8));
        } else if (self == ToolMaterial.GOLD || self == ToolMaterial.NETHERITE) {
            cir.setReturnValue(properties.enchantable(20));
        }
    }
}
