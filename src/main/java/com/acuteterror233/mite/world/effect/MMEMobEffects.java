package com.acuteterror233.mite.world.effect;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

/**
 * MME 模组状态效果注册中心。
 * 注册自定义药水效果（营养不良、胰岛素抵抗等）。
 */
public class MMEMobEffects {
    public static final Holder<MobEffect> MALNUTRITION = register(
            "malnutrition",
            new PermanentNegativeMobEffect(10404919)
    );
    public static final Holder<MobEffect> INSULIN_RESISTANCE = register(
            "insulin_resistance",
            new PermanentNegativeMobEffect(16777215).setBlendDuration(150, 20, 60)
    );

    private static Holder<MobEffect> register(String id, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id), mobEffect);
    }

    public static void init() {

    }
}
