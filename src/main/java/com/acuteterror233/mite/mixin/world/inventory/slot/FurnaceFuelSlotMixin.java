package com.acuteterror233.mite.mixin.world.inventory.slot;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code FurnaceFuelSlot} — Modify furnace fuel slot check logic.
 */
@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin {

    /**
     * @author AcuteTerror233
     * @reason Modified bucket check
     */
    @Overwrite
    public static boolean isBucket(ItemStack stack){
        return stack.is(MMEItemTags.BUCKET);
    }
}
