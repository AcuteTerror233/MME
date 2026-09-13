package com.acuteterror233.mite.world.entity.damage;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

/**
 * MME mod damage type definitions.
 * Registers custom damage types (like corrosion damage) for data generation.
 */
public interface MMEDamageTypes {
    ResourceKey<DamageType> CORROSION = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, "corrosion"));

    static void bootstrap(BootstrapContext<DamageType> damageTypeRegisterable){
        damageTypeRegisterable.register(CORROSION, new DamageType("corrosion", 0.1f));
    }
}
