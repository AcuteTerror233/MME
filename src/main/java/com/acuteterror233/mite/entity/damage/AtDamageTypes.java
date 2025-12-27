package com.acuteterror233.mite.entity.damage;

import com.acuteterror233.mite.Mme;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public interface AtDamageTypes {
    RegistryKey<DamageType> CORROSION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Mme.MOD_ID, "corrosion"));

    static void bootstrap(Registerable<DamageType> damageTypeRegisterable){
        damageTypeRegisterable.register(CORROSION, new DamageType("corrosion", 0.1f));
    }
}
