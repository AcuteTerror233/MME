package com.acuteterror233.mite.mixin.inventory.slot;

import com.acuteterror233.mite.registry.tag.MMETags;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
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
        return stack.is(MMETags.BUCKET);
    }
}
