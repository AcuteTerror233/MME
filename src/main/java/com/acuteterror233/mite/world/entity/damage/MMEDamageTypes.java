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
    /** Corrosion damage type (message id {@code corrosion}, exhaustion 0.1) used by acid-themed attacks. */
    ResourceKey<DamageType> CORROSION = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, "corrosion"));

    /** Datagen bootstrap: registers {@link #CORROSION} with death message key {@code death.attack.corrosion}. */
    static void bootstrap(BootstrapContext<DamageType> damageTypeRegisterable){
        damageTypeRegisterable.register(CORROSION, new DamageType("corrosion", 0.1f));
    }
}
