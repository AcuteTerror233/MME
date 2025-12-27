package com.acuteterror233.mite.mixin.screen.slot;

import com.acuteterror233.mite.registry.tag.AtTags;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin {
    /**
     * @author AcuteTerror233
     * @reason 桶的判断修改
     */
    @Overwrite
    public static boolean isBucket(ItemStack stack){
        return stack.isIn(AtTags.BUCKET);
    }
}
