package com.acuteterror233.mite.mixin.world.inventory.slot;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FurnaceFuelSlot.class)
/**
 * Mixin for {@code FurnaceFuelSlot} — 修改熔炉燃料栏位判定逻辑。
 */
public class FurnaceFuelSlotMixin {
    /**
     * @author AcuteTerror233
     * @reason 桶的判断修改
     */
    @Overwrite
    public static boolean isBucket(ItemStack stack){
        return stack.is(MMEItemTags.BUCKET);
    }
}
