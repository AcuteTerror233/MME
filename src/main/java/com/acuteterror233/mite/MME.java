package com.acuteterror233.mite;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.interfaces.FoodDataExtension;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.attribute.MMEAttributeTypes;
import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.biome.BiomeModification;
import com.acuteterror233.mite.world.effect.MMEMobEffects;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.food.FoodNutrition;
import com.acuteterror233.mite.world.gen.feature.OverworldPlacedFeatures;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.commands.Commands;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * MME mod main entry point, implements {@link ModInitializer}.
 * Responsible for registering all blocks, items, entities, biome modifications, loot table replacements, and commands.
 */
public class MME implements ModInitializer {
    /** Mod id; also the namespace for every MME registry id, tag, and data path. */
    public static final String MOD_ID = "mme";
    /** Shared SLF4J logger, named after the mod id. */
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    /** Vanilla {@code block_interaction_range} attribute id; MME items attach {@code ADD_VALUE} modifiers under it. */
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE = Identifier.withDefaultNamespace("block_interaction_range");
    /** Vanilla {@code entity_interaction_range} attribute id; MME items attach {@code ADD_VALUE} modifiers under it. */
    public static final Identifier BASE_ENTITY_INTERACTION_RANGE = Identifier.withDefaultNamespace("entity_interaction_range");

    /**
     * Fabric init entry point: runs registry inits, biome/attribute setup, POI registration,
     * overworld ore adjustments, sleep-rule overrides, flammability tweaks, and command registration.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("Make Minecraft Easy!");

        // === Registry initialization ===
        MMEItems.init();
        MMEBlocks.init();
        MMEMobEffects.init();
        MMEEntityTypes.init();

        // === Data & world gen ===
        BiomeModification.init();
        MMEAttributeTypes.init();
        MMEEnvironmentAttributes.init();

        // === Point of Interest registration ===
        PoiHelper.register(Identifier.fromNamespaceAndPath(MME.MOD_ID, "underground_portal"), 0, 1, MMEBlocks.UNDERGROUND_PORTAL);

        // === Overworld ore generation ===
        inOverworldAdd(OverworldPlacedFeatures.OVERWORLD_ORE_SILVER_SMALL);
        inOverworldAdd(OverworldPlacedFeatures.OVERWORLD_ORE_SILVER);
        inOverworldRemovals(OrePlacements.ORE_DIAMOND_BURIED);
        inOverworldRemovals(OrePlacements.ORE_DIAMOND_LARGE);
        inOverworldRemovals(OrePlacements.ORE_DIAMOND_MEDIUM);

        // === Player sleep behavior ===
        registerSleepEvents();

        // === Block flammability ===
        FireBlock fireBlock = (FireBlock)Blocks.FIRE;
        fireBlock.setFlammable(MMEBlocks.BLUE_BERRY_BUSH, 60, 100);

        // === Custom commands ===
        registerCommands();
    }

    /**
     * Overrides vanilla sleep rules: cancels sleeping entirely, and prevents time reset unless it's dark.
     */
    private static void registerSleepEvents() {
        EntitySleepEvents.ALLOW_SLEEPING.register((player, _) -> player.level().environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.BLOOD_MOON) ? Player.BedSleepingProblem.NOT_SAFE : null);
        EntitySleepEvents.ALLOW_RESETTING_TIME.register(player -> !player.level().isBrightOutside());
    }

    /**
     * Registers the /nutrition command to display a player's current nutrition status.
     */
    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(
                        Commands.literal("nutrition")
                                .requires(stack -> stack.getPlayer() != null)
                                .executes(context -> {
                                    context.getSource().sendSuccess(() -> {
                                                ServerPlayer player = context.getSource().getPlayer();
                                                if (player != null) {
                                                    FoodNutrition foodNutrition = ((FoodDataExtension) player.getFoodData()).MME$GetFoodNutrition();
                                                    return Component.translatable("mme.nutrition.tooltip", player.getName(), foodNutrition.fiber(), foodNutrition.protein(), foodNutrition.sugar());
                                                }
                                                return Component.empty();
                                            },
                                            false);
                                    return 1;
                                })
                )
        );
    }

    /** Adds a placed feature to every overworld biome in the {@code UNDERGROUND_ORES} decoration step. */
    private void inOverworldAdd(ResourceKey<PlacedFeature> key) {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                key
        );
    }

    /** Removes a vanilla placed feature from every overworld biome via a dedicated {@code REMOVALS} modification. */
    private static void inOverworldRemovals(ResourceKey<PlacedFeature> oreDiamond) {
        BiomeModifications.create(oreDiamond.identifier()).add(
                ModificationPhase.REMOVALS,
                BiomeSelectors.foundInOverworld(),
                context -> context.getGenerationSettings().removeFeature(oreDiamond)
        );
    }

    /**
     * Vanilla recipe ids removed from the recipe map by {@code RecipeMapMixin}, grouped by reason:
     * wooden and stone tools (replaced by the flint-tier early-game progression);
     * diamond tools and armor (diamond gear is gated behind the metal crafting tables);
     * the crafting table and crafter (superseded by MME's crafting progression);
     * netherite smithing upgrades (handled by MME's own anvil/upgrade system);
     * the fishing rod (replaced by MME's metal fishing rods);
     * raw metal smelting (raw iron/copper/gold must be processed by MME means);
     * the bundle.
     * These ids are hardcoded — a vanilla rename in an MC upgrade silently breaks the filter.
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
            Identifier.withDefaultNamespace("diamond_helmet"),
            Identifier.withDefaultNamespace("diamond_chestplate"),
            Identifier.withDefaultNamespace("diamond_leggings"),
            Identifier.withDefaultNamespace("diamond_boots"),
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
            Identifier.withDefaultNamespace("crafter"),
            Identifier.withDefaultNamespace("raw_iron"),
            Identifier.withDefaultNamespace("raw_copper"),
            Identifier.withDefaultNamespace("raw_gold"),
            Identifier.withDefaultNamespace("bundle")
    );

    /**
     * Maps vanilla fuel burn-time provider keys to their MME "combustion grade" (fuel strength class).
     * Applied by {@code ItemPropertiesMixin}: a fuel item's {@code combustion_grade} component is looked
     * up here and defaults to 1 for fuels not listed. Higher is stronger:
     * 1 default, 2 coal / coal block, 3 lava bucket, 4 blaze rod. Furnaces compare this grade against
     * the fuel's {@code max_combustion_grade} cap and the ingredient's {@code required_combustion_grade}.
     */
    public static Map<ResourceKey<ContextIntProvider>, Integer> CORRESPONDING_COMBUSTION_GRADE = Map.ofEntries(
            Map.entry(ContextIntProviders.COOKING_TIME_COAL, 2),
            Map.entry(ContextIntProviders.COOKING_TIME_BLAZE_ROD, 4),
            Map.entry(ContextIntProviders.COOKING_TIME_COAL_BLOCK, 2),
            Map.entry(ContextIntProviders.COOKING_TIME_LAVA_BUCKET, 3)
    );
}