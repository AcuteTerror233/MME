package com.acuteterror233.mite;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.event.ServerRecipeModify;
import com.acuteterror233.mite.item.AtItems;
import com.acuteterror233.mite.registry.ReloadableRegistriesReplace;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.acuteterror233.mite.world.gen.feature.OverworldOrePlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.function.Function;

public class Mme implements ModInitializer {
    public static final String MOD_ID = "mme";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE = Identifier.ofVanilla("block_interaction_range");
    public static final Identifier BASE_ENTITY_INTERACTION_RANGE = Identifier.ofVanilla("entity_interaction_range");

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_ID);
        // 物品与方块注册
        AtItems.init();
        AtBlocks.init();

        // 地下传送门注册 POI
        PointOfInterestHelper.register(Identifier.of(Mme.MOD_ID, "underground_portal"), 0, 1, AtBlocks.UNDERGROUND_PORTAL);

        // 群系添加/删除矿石
        InOverworldAdd(OverworldOrePlacedFeatures.OVERWORLD_ORE_SILVER_SMALL);
        InOverworldAdd(OverworldOrePlacedFeatures.OVERWORLD_ORE_SILVER);
        InOverworldRemovals(OrePlacedFeatures.ORE_DIAMOND_BURIED);
        InOverworldRemovals(OrePlacedFeatures.ORE_DIAMOND_LARGE);
        InOverworldRemovals(OrePlacedFeatures.ORE_DIAMOND_MEDIUM);
        ServerRecipeModify.EVENT.register((list) -> list.removeIf(recipeEntry -> Mme.FILTER_RECIPE_SET.contains(recipeEntry.id().getValue())));
        LootTableEvents.REPLACE.register((key, original, source, registries) -> {
            Function<RegistryWrapper.WrapperLookup, LootTable> function = ReloadableRegistriesReplace.LOOT_TABLES.get(key);
            return function != null ? function.apply(registries) : null;
        });
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(AtItems.WOOD_CLUB, context.baseSmeltTime());
            builder.add(AtItems.WOOD_CUDGEL, context.baseSmeltTime());
            builder.add(AtTags.LAVA_BUCKET,  context.baseSmeltTime() * 16);
            builder.add(Blocks.COAL_BLOCK, context.baseSmeltTime() * 72);
            builder.add(Blocks.DRIED_KELP_BLOCK, context.baseSmeltTime() * 8);
            builder.add(Items.TORCH, context.baseSmeltTime() * 4);
            builder.add(Items.SOUL_TORCH, context.baseSmeltTime() * 6);
        });
        EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) -> ActionResult.SUCCESS);
        EntitySleepEvents.ALLOW_RESETTING_TIME.register(player -> !player.getWorld().isDay());
    }

    private void InOverworldAdd(RegistryKey<PlacedFeature> key) {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                key
        );
    }

    private static void InOverworldRemovals(RegistryKey<PlacedFeature> oreDiamond) {
        BiomeModifications.create(oreDiamond.getValue()).add(
                ModificationPhase.REMOVALS,
                BiomeSelectors.foundInOverworld(),
                context -> context.getGenerationSettings().removeFeature(oreDiamond)
        );
    }

    /**
     * 筛选掉的配方标识符集合。
     */
    public static Set<Identifier> FILTER_RECIPE_SET = Set.of(
            Identifier.ofVanilla("wooden_pickaxe"),
            Identifier.ofVanilla("wooden_axe"),
            Identifier.ofVanilla("wooden_hoe"),
            Identifier.ofVanilla("wooden_sword"),
            Identifier.ofVanilla("stone_shovel"),
            Identifier.ofVanilla("stone_pickaxe"),
            Identifier.ofVanilla("stone_axe"),
            Identifier.ofVanilla("stone_hoe"),
            Identifier.ofVanilla("stone_sword"),
            Identifier.ofVanilla("diamond_shovel"),
            Identifier.ofVanilla("diamond_pickaxe"),
            Identifier.ofVanilla("diamond_axe"),
            Identifier.ofVanilla("diamond_hoe"),
            Identifier.ofVanilla("diamond_sword"),
            Identifier.ofVanilla("crafting_table"),
            Identifier.ofVanilla("blast_furnace")
    );

}