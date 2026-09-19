package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.MME;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.trading.VillagerTrade;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * MME dynamic registry data provider.
 * Registers dimension types, biomes, configured features, and other world generation data.
 */
public class MMEDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public MMEDynamicRegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.DIMENSION_TYPE));
        entries.addAll(registries.lookupOrThrow(Registries.BIOME));
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
        entries.addAll(registries.lookupOrThrow(Registries.DAMAGE_TYPE));
        entries.addAll(registries.lookupOrThrow(Registries.PAINTING_VARIANT));
        HolderLookup.RegistryLookup<VillagerTrade> villagerTrades = registries.lookupOrThrow(Registries.VILLAGER_TRADE);
        villagerTrades.listElementIds().forEach(key -> entries.add(villagerTrades, key));
    }

    @Override
    public @NotNull String getName() {
        return MME.MOD_ID + "_dynamic_registry";
    }
}
