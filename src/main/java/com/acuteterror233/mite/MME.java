package com.acuteterror233.mite;

import com.acuteterror233.mite.atinterface.FoodDataExtension;
import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.event.ServerRecipeModify;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.registry.LootTableReplace;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.acuteterror233.mite.world.biome.BiomeModification;
import com.acuteterror233.mite.world.effect.MMEMobEffects;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.food.FoodNutrition;
import com.acuteterror233.mite.world.gen.feature.OverworldPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.commands.Commands;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * MME 模组主入口，实现 {@link ModInitializer}。
 * 负责注册所有方块、物品、实体、生物群落修改、战利品表替换和命令。
 */
public class MME implements ModInitializer {
    public static final String MOD_ID = "mme";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE = Identifier.withDefaultNamespace("block_interaction_range");
    public static final Identifier BASE_ENTITY_INTERACTION_RANGE = Identifier.withDefaultNamespace("entity_interaction_range");

    @Override
    public void onInitialize() {
        LOGGER.info("Make Minecraft Easy!");

        MMEItems.init();
        MMEBlocks.init();
        MMEMobEffects.init();
        MMEEntityTypes.init();
        LootTableReplace.init();
        BiomeModification.init();

        PointOfInterestHelper.register(Identifier.fromNamespaceAndPath(MME.MOD_ID, "underground_portal"), 0, 1, MMEBlocks.UNDERGROUND_PORTAL);

        inOverworldAdd(OverworldPlacedFeatures.OVERWORLD_ORE_SILVER_SMALL);
        inOverworldAdd(OverworldPlacedFeatures.OVERWORLD_ORE_SILVER);
        inOverworldRemovals(OrePlacements.ORE_DIAMOND_BURIED);
        inOverworldRemovals(OrePlacements.ORE_DIAMOND_LARGE);
        inOverworldRemovals(OrePlacements.ORE_DIAMOND_MEDIUM);

        ServerRecipeModify.EVENT.register(list -> list.removeIf(recipeEntry -> MME.FILTER_RECIPE_SET.contains(recipeEntry.id().identifier())));

        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(MMEItems.WOODEN_CLUB, context.baseSmeltTime());
            builder.add(MMEItems.WOODEN_CUDGEL, context.baseSmeltTime());
            builder.add(MMEItemTags.LAVA_BUCKET,  context.baseSmeltTime() * 16);
            builder.add(Blocks.COAL_BLOCK, context.baseSmeltTime() * 72);
            builder.add(Blocks.DRIED_KELP_BLOCK, context.baseSmeltTime() * 8);
            builder.add(Items.TORCH, context.baseSmeltTime() * 4);
            builder.add(Items.SOUL_TORCH, context.baseSmeltTime() * 6);
        });

        EntitySleepEvents.ALLOW_SLEEPING.register((player, sleepingPos) -> null);
        EntitySleepEvents.ALLOW_RESETTING_TIME.register(player -> !player.level().isBrightOutside());

        FireBlock fireBlock = (FireBlock)Blocks.FIRE;
        fireBlock.setFlammable(MMEBlocks.BLUE_BERRY_BUSH, 60, 100);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(
                        Commands.literal("nutrition")
                                .requires(stack -> stack.getPlayer() != null)
                                .executes(context -> {
                                    context.getSource().sendSuccess(() -> {
                                                ServerPlayer player = context.getSource().getPlayer();
                                                FoodNutrition foodNutrition = ((FoodDataExtension) player.getFoodData()).MME$GetFoodNutrition();
                                                return Component.translatable("mme.nutrition.tooltip", player.getName(), foodNutrition.fiber(), foodNutrition.protein(), foodNutrition.sugar());
                                            },
                                            false);
                                    return 1;
                                })
                )
        );
    }

    private void inOverworldAdd(ResourceKey<PlacedFeature> key) {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                key
        );
    }

    private static void inOverworldRemovals(ResourceKey<PlacedFeature> oreDiamond) {
        BiomeModifications.create(oreDiamond.identifier()).add(
                ModificationPhase.REMOVALS,
                BiomeSelectors.foundInOverworld(),
                context -> context.getGenerationSettings().removeFeature(oreDiamond)
        );
    }

    /**
     * 筛选掉的配方标识符集合。
     */
    public static Set<Identifier> FILTER_RECIPE_SET = Set.of(
            Identifier.withDefaultNamespace("wooden_pickaxe"),
            Identifier.withDefaultNamespace("wooden_axe"),
            Identifier.withDefaultNamespace("wooden_hoe"),
            Identifier.withDefaultNamespace("wooden_sword"),
            Identifier.withDefaultNamespace("stone_shovel"),
            Identifier.withDefaultNamespace("stone_pickaxe"),
            Identifier.withDefaultNamespace("stone_axe"),
            Identifier.withDefaultNamespace("stone_hoe"),
            Identifier.withDefaultNamespace("stone_sword"),
            Identifier.withDefaultNamespace("diamond_shovel"),
            Identifier.withDefaultNamespace("diamond_pickaxe"),
            Identifier.withDefaultNamespace("diamond_axe"),
            Identifier.withDefaultNamespace("diamond_hoe"),
            Identifier.withDefaultNamespace("diamond_sword"),
            Identifier.withDefaultNamespace("crafting_table"),
            Identifier.withDefaultNamespace("netherite_axe_smithing"),
            Identifier.withDefaultNamespace("netherite_boots_smithing"),
            Identifier.withDefaultNamespace("netherite_chestplate_smithing"),
            Identifier.withDefaultNamespace("netherite_helmet_smithing"),
            Identifier.withDefaultNamespace("netherite_hoe_smithing"),
            Identifier.withDefaultNamespace("netherite_leggings_smithing"),
            Identifier.withDefaultNamespace("netherite_pickaxe_smithing"),
            Identifier.withDefaultNamespace("netherite_shovel_smithing"),
            Identifier.withDefaultNamespace("netherite_sword_smithing"),
            Identifier.withDefaultNamespace("fishing_rod"),
            Identifier.withDefaultNamespace("crafter")
    );

}