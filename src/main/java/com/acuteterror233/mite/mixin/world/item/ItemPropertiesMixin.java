package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.component.MMEDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code Item.Properties} — Derives MME combustion data from vanilla fuel registrations.
 * Whenever an item declares a cooking-fuel burn time ({@code cookingFuel}), this mixin additionally
 * attaches the MME {@code COMBUSTION_GRADE} component, looked up from the burn time's
 * {@code ContextIntProvider} key (unknown values default to grade 1).
 */
@Mixin(Item.Properties.class)
public abstract class ItemPropertiesMixin {
    @Shadow
    public abstract <T> Item.Properties component(DataComponentType<T> type, T value);

    /** Runs inside vanilla {@code cookingFuel} right after the fuel component is set, appending the matching COMBUSTION_GRADE. */
    @Inject(method = "cookingFuel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;component(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Lnet/minecraft/world/item/Item$Properties;"))
    public void cookingFuel(ResourceKey<ContextIntProvider> burnTime, CallbackInfoReturnable<Item.Properties> cir){
        this.component(MMEDataComponents.COMBUSTION_GRADE, MME.CORRESPONDING_COMBUSTION_GRADE.getOrDefault(burnTime, 1));
    }
}
