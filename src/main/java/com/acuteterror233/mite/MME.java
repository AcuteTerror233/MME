package com.acuteterror233.mite;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.event.ServerRecipeModify;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.registry.LootTableReplace;
import com.acuteterror233.mite.registry.tag.MMETags;
import com.acuteterror233.mite.world.gen.feature.OverworldOrePlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class MME implements ModInitializer {
    public static final String MOD_ID = "mme";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ResourceLocation BASE_BLOCK_INTERACTION_RANGE = ResourceLocation.withDefaultNamespace("block_interaction_range");
    public static final ResourceLocation BASE_ENTITY_INTERACTION_RANGE = ResourceLocation.withDefaultNamespace("entity_interaction_range");

    @Override
    public void onInitialize() {
        LOGGER.info("Make Minecraft Easy");

        MMEItems.init();
        MMEBlocks.init();

        PointOfInterestHelper.register(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "underground_portal"), 0, 1, MMEBlocks.UNDERGROUND_PORTAL);

        InOverworldAdd(OverworldOrePlacedFeatures.OVERWORLD_ORE_SILVER_SMALL);
        InOverworldAdd(OverworldOrePlacedFeatures.OVERWORLD_ORE_SILVER);
        InOverworldRemovals(OrePlacements.ORE_DIAMOND_BURIED);
        InOverworldRemovals(OrePlacements.ORE_DIAMOND_LARGE);
        InOverworldRemovals(OrePlacements.ORE_DIAMOND_MEDIUM);

        ServerRecipeModify.EVENT.register(list -> list.removeIf(recipeEntry -> MME.FILTER_RECIPE_SET.contains(recipeEntry.id().location())));

        LootTableReplace.init();

        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(MMEItems.WOODEN_CLUB, context.baseSmeltTime());
            builder.add(MMEItems.WOODEN_CUDGEL, context.baseSmeltTime());
            builder.add(MMETags.LAVA_BUCKET,  context.baseSmeltTime() * 16);
            builder.add(Blocks.COAL_BLOCK, context.baseSmeltTime() * 72);
            builder.add(Blocks.DRIED_KELP_BLOCK, context.baseSmeltTime() * 8);
            builder.add(Items.TORCH, context.baseSmeltTime() * 4);
            builder.add(Items.SOUL_TORCH, context.baseSmeltTime() * 6);
        });

        EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) -> InteractionResult.SUCCESS);
        EntitySleepEvents.ALLOW_RESETTING_TIME.register(player -> !player.level().isBrightOutside());
    }

    private void InOverworldAdd(ResourceKey<PlacedFeature> key) {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                key
        );
    }

    private static void InOverworldRemovals(ResourceKey<PlacedFeature> oreDiamond) {
        BiomeModifications.create(oreDiamond.location()).add(
                ModificationPhase.REMOVALS,
                BiomeSelectors.foundInOverworld(),
                context -> context.getGenerationSettings().removeFeature(oreDiamond)
        );
    }

    /**
     * 筛选掉的配方标识符集合。
     */
    public static Set<ResourceLocation> FILTER_RECIPE_SET = Set.of(
            ResourceLocation.withDefaultNamespace("wooden_pickaxe"),
            ResourceLocation.withDefaultNamespace("wooden_axe"),
            ResourceLocation.withDefaultNamespace("wooden_hoe"),
            ResourceLocation.withDefaultNamespace("wooden_sword"),
            ResourceLocation.withDefaultNamespace("stone_shovel"),
            ResourceLocation.withDefaultNamespace("stone_pickaxe"),
            ResourceLocation.withDefaultNamespace("stone_axe"),
            ResourceLocation.withDefaultNamespace("stone_hoe"),
            ResourceLocation.withDefaultNamespace("stone_sword"),
            ResourceLocation.withDefaultNamespace("diamond_shovel"),
            ResourceLocation.withDefaultNamespace("diamond_pickaxe"),
            ResourceLocation.withDefaultNamespace("diamond_axe"),
            ResourceLocation.withDefaultNamespace("diamond_hoe"),
            ResourceLocation.withDefaultNamespace("diamond_sword"),
            ResourceLocation.withDefaultNamespace("crafting_table"),
            ResourceLocation.withDefaultNamespace("blast_furnace"),
            ResourceLocation.withDefaultNamespace("netherite_axe_smithing"),
            ResourceLocation.withDefaultNamespace("netherite_boots_smithing"),
            ResourceLocation.withDefaultNamespace("netherite_chestplate_smithing"),
            ResourceLocation.withDefaultNamespace("netherite_helmet_smithing"),
            ResourceLocation.withDefaultNamespace("netherite_hoe_smithing"),
            ResourceLocation.withDefaultNamespace("netherite_leggings_smithing"),
            ResourceLocation.withDefaultNamespace("netherite_pickaxe_smithing"),
            ResourceLocation.withDefaultNamespace("netherite_shovel_smithing"),
            ResourceLocation.withDefaultNamespace("netherite_sword_smithing"),
            ResourceLocation.withDefaultNamespace("fishing_rod")
    );

}