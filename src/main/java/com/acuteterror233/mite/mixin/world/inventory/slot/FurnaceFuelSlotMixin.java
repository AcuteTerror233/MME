package com.acuteterror233.mite.mixin.world.inventory.slot;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code FurnaceFuelSlot} — overrides the bucket check so any item in the
 * {@code MMEItemTags.BUCKET} tag is treated as a bucket (vanilla only accepts the literal
 * water/lava/milk powder-snow bucket items), keeping MME buckets out of the fuel slot.
 */
@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin {

    /**
     * @author AcuteTerror233
     * @reason Replace the hard-coded bucket item comparison with an MME bucket tag check.
     *
     * @param stack the item stack being tested for the fuel slot
     * @return whether the stack belongs to the MME bucket tag
     */
    @Overwrite
    public static boolean isBucket(ItemStack stack){
        return stack.is(MMEItemTags.BUCKET);
    }
}
