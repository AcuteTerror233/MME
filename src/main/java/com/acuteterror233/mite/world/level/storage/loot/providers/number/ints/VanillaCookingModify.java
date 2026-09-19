package com.acuteterror233.mite.world.level.storage.loot.providers.number.ints;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class VanillaCookingModify {
    //Stupid approach. Improve later.
    public static final int DEFAULT_TIME = 200;
    public static final Map<ResourceKey<ContextIntProvider>, Function<BootstrapContext<ContextIntProvider>,  ContextIntProvider>> IN_KEY_COOKING_MODIFY = createCookingModifyMapByKey();

    private static Map<ResourceKey<ContextIntProvider>, Function<BootstrapContext<ContextIntProvider>,  ContextIntProvider>> createCookingModifyMapByKey() {
        Map<ResourceKey<ContextIntProvider>, Function<BootstrapContext<ContextIntProvider>,  ContextIntProvider>> map = new HashMap<>();

        map.put(ContextIntProviders.COOKING_TIME_LAVA_BUCKET, context -> {
            HolderGetter<LootItemCondition> predicates = context.lookup(Registries.PREDICATE);
            Holder.Reference<ContextIntProvider> normalBurnTime = context.register(ContextIntProviders.COOKING_NORMAL_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(1));
            Holder.Reference<ContextIntProvider> fastBurnTime = context.register(ContextIntProviders.COOKING_FAST_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(2));
            return ContextIntProviders.cooking(predicates, normalBurnTime, fastBurnTime, DEFAULT_TIME * 16);
        });

        map.put(ContextIntProviders.COOKING_TIME_COAL_BLOCK, context -> {
            HolderGetter<LootItemCondition> predicates = context.lookup(Registries.PREDICATE);
            Holder.Reference<ContextIntProvider> normalBurnTime = context.register(ContextIntProviders.COOKING_NORMAL_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(1));
            Holder.Reference<ContextIntProvider> fastBurnTime = context.register(ContextIntProviders.COOKING_FAST_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(2));
            return ContextIntProviders.cooking(predicates, normalBurnTime, fastBurnTime, DEFAULT_TIME * 72);
        });

        map.put(ContextIntProviders.COOKING_TIME_DRIED_KELP_BLOCK, context -> {
            HolderGetter<LootItemCondition> predicates = context.lookup(Registries.PREDICATE);
            Holder.Reference<ContextIntProvider> normalBurnTime = context.register(ContextIntProviders.COOKING_NORMAL_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(1));
            Holder.Reference<ContextIntProvider> fastBurnTime = context.register(ContextIntProviders.COOKING_FAST_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(2));
            return ContextIntProviders.cooking(predicates, normalBurnTime, fastBurnTime, DEFAULT_TIME * 8);
        });

        return map;
    }
}
