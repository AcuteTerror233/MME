package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.registry.tag.MMETags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MMERecipeGenerator extends FabricRecipeProvider {
    public MMERecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEItems.RAW_ADAMANTIUM), RecipeCategory.MISC, MMEItems.ADAMANTIUM_INGOT, 5F, 200)
                    .unlockedBy("has_raw_adamantium", this.has(MMEItems.RAW_ADAMANTIUM))
                    .save(this.output, "adamantium_ingot_from_raw_adamantium");
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEItems.RAW_MITHRIL), RecipeCategory.MISC, MMEItems.MITHRIL_INGOT, 3F, 200)
                        .unlockedBy("has_raw_mithril", this.has(MMEItems.RAW_MITHRIL))
                        .save(this.output, "mithril_ingot_from_raw_mithril");
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEItems.RAW_SILVER), RecipeCategory.MISC, MMEItems.SILVER_INGOT, 1F, 200)
                        .unlockedBy("has_raw_silver", this.has(MMEItems.RAW_SILVER))
                        .save(this.output, "silver_ingot_from_raw_silver");

                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEBlocks.ADAMANTIUM_ORE), RecipeCategory.MISC, MMEItems.ADAMANTIUM_INGOT, 5F, 200)
                        .unlockedBy("has_adamantium_ore", this.has(MMEBlocks.ADAMANTIUM_ORE))
                        .save(this.output, "adamantium_ingot_from_adamantium_ore");
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEBlocks.MITHRIL_ORE), RecipeCategory.MISC, MMEItems.MITHRIL_INGOT, 3F, 200)
                        .unlockedBy("has_mithril_ore", this.has(MMEBlocks.MITHRIL_ORE))
                        .save(this.output, "mithril_ingot_from_mithril_ore");
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEBlocks.SILVER_ORE), RecipeCategory.MISC, MMEItems.SILVER_INGOT, 1F, 200)
                        .unlockedBy("has_silver_ore", this.has(MMEBlocks.SILVER_ORE))
                        .save(this.output, "silver_ingot_from_silver_ore");

                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE), RecipeCategory.MISC, MMEItems.ADAMANTIUM_INGOT, 5F, 200)
                        .unlockedBy("has_deepslate_adamantium_ore", this.has(MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE))
                        .save(this.output, "adamantium_ingot_from_deepslate_adamantium_ore");
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEBlocks.DEEPSLATE_MITHRIL_ORE), RecipeCategory.MISC, MMEItems.MITHRIL_INGOT, 3F, 200)
                        .unlockedBy("has_deepslate_mithril_ore", this.has(MMEBlocks.DEEPSLATE_MITHRIL_ORE))
                        .save(this.output, "mithril_ingot_from_deepslate_mithril_ore");
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(MMEBlocks.DEEPSLATE_SILVER_ORE), RecipeCategory.MISC, MMEItems.SILVER_INGOT, 1F, 200)
                        .unlockedBy("has_deepslate_silver_ore", this.has(MMEBlocks.DEEPSLATE_SILVER_ORE))
                        .save(this.output, "silver_ingot_from_deepslate_silver_ore");

                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.ADAMANTIUM_INGOT, RecipeCategory.BUILDING_BLOCKS, MMEBlocks.ADAMANTIUM_BLOCK, "adamantium_block_from_adamantium_ingot", null , "adamantium_ingot_from_adamantium_block",null);
                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.ANCIENT_METAL_INGOT, RecipeCategory.BUILDING_BLOCKS, MMEBlocks.ANCIENT_METAL_BLOCK, "ancient_metal_block_from_ancient_metal_ingot", null , "ancient_metal_ingot_from_ancient_metal_block", null);
                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.MITHRIL_INGOT, RecipeCategory.BUILDING_BLOCKS, MMEBlocks.MITHRIL_BLOCK, "mithril_block_from_mithril_ingot", null , "mithril_ingot_from_mithril_block", null);
                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.SILVER_INGOT, RecipeCategory.BUILDING_BLOCKS, MMEBlocks.SILVER_BLOCK, "silver_block_from_silver_ingot", null , "silver_ingot_from_silver_block", null);

                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.ADAMANTIUM_NUGGET, RecipeCategory.MISC, MMEItems.ADAMANTIUM_INGOT, "adamantium_ingot_from_adamantium_nugget", null,  "adamantium_nugget_from_adamantium_ingot", null);
                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.ANCIENT_METAL_NUGGET, RecipeCategory.MISC, MMEItems.ANCIENT_METAL_INGOT, "ancient_metal_ingot_from_ancient_metal_nugget", null,  "ancient_metal_nugget_from_ancient_metal_ingot", null);
                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.MITHRIL_NUGGET, RecipeCategory.MISC, MMEItems.MITHRIL_INGOT, "mithril_ingot_from_mithril_nugget", null,  "mithril_nugget_from_mithril_ingot", null);
                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.SILVER_NUGGET, RecipeCategory.MISC, MMEItems.SILVER_INGOT, "silver_ingot_from_silver_nugget", null,  "silver_nugget_from_silver_ingot", null);
                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.COPPER_NUGGET, RecipeCategory.MISC, Items.COPPER_INGOT, "copper_ingot_from_copper_nugget", null,  "copper_nugget_from_copper_ingot", null);

                twoByTwoPacker(RecipeCategory.DECORATIONS, Items.CLAY, MMEBlocks.CLAY_FURNACE);
                shaped(RecipeCategory.DECORATIONS, MMEBlocks.HARDENED_CLAY_FURNACE)
                        .define('a', Items.TERRACOTTA)
                        .pattern("aaa")
                        .pattern("a a")
                        .pattern("aaa")
                        .unlockedBy("has_terracotta", this.has(Items.TERRACOTTA))
                        .save(this.output);
                shaped(RecipeCategory.DECORATIONS, MMEBlocks.NETHERRACK_FURNACE)
                        .define('a', Items.NETHERRACK)
                        .pattern("aaa")
                        .pattern("a a")
                        .pattern("aaa")
                        .unlockedBy("has_netherrack", this.has(Items.TERRACOTTA))
                        .save(this.output);
                shaped(RecipeCategory.DECORATIONS, MMEBlocks.OBSIDIAN_FURNACE)
                        .define('a', Items.OBSIDIAN)
                        .pattern("aaa")
                        .pattern("a a")
                        .pattern("aaa")
                        .unlockedBy("has_obsidian", this.has(Items.OBSIDIAN))
                        .save(this.output);
                shaped(RecipeCategory.DECORATIONS, MMEBlocks.SANDSTONE_FURNACE)
                        .define('a', Items.SANDSTONE)
                        .pattern("aaa")
                        .pattern("a a")
                        .pattern("aaa")
                        .unlockedBy("has_sandstone", this.has(Items.SANDSTONE))
                        .save(this.output);

                nineBlockStorageRecipes(RecipeCategory.MISC, MMEItems.OBSIDIAN_SHARD, RecipeCategory.MISC, Blocks.OBSIDIAN, "obsidian_from_obsidian_shard", null,  "obsidian_shard_from_obsidian", null);
                twoByTwoPacker(RecipeCategory.MISC, Items.FLINT, MMEItems.FLINT_SHARD);

                offerCraftingTableRecipes(MMEBlocks.ADAMANTIUM_CRAFTING_TABLE, MMEItems.ADAMANTIUM_INGOT);
                offerCraftingTableRecipes(MMEBlocks.ANCIENT_METAL_CRAFTING_TABLE, MMEItems.ANCIENT_METAL_INGOT);
                offerCraftingTableRecipes(MMEBlocks.MITHRIL_CRAFTING_TABLE, MMEItems.MITHRIL_INGOT);
                offerCraftingTableRecipes(MMEBlocks.SILVER_CRAFTING_TABLE, MMEItems.SILVER_INGOT);
                offerCraftingTableRecipes(MMEBlocks.COPPER_CRAFTING_TABLE, Items.COPPER_INGOT);
                offerCraftingTableRecipes(MMEBlocks.IRON_CRAFTING_TABLE, Items.IRON_INGOT);
                offerCraftingTableRecipes(MMEBlocks.GOLD_CRAFTING_TABLE, Items.GOLD_INGOT);
                shapeless(RecipeCategory.DECORATIONS, MMEBlocks.FLINT_CRAFTING_TABLE)
                        .requires(MMEItems.FLINT_KNIFE)
                        .requires(ItemTags.OAK_LOGS)
                        .unlockedBy(getHasName(MMEItems.FLINT_KNIFE), this.has(MMEItems.FLINT_KNIFE))
                        .save(this.output);
                shapeless(RecipeCategory.DECORATIONS, MMEBlocks.OBSIDIAN_CRAFTING_TABLE)
                        .requires(MMEItems.OBSIDIAN_KNIFE)
                        .requires(ItemTags.OAK_LOGS)
                        .unlockedBy(getHasName(MMEItems.OBSIDIAN_KNIFE), this.has(MMEItems.OBSIDIAN_KNIFE))
                        .save(this.output);

                offerAnvilRecipes(MMEBlocks.NETHERITE_ANVIL, Items.NETHERITE_INGOT, Blocks.NETHERITE_BLOCK);
                offerAnvilRecipes(MMEBlocks.ADAMANTIUM_ANVIL, MMEItems.ADAMANTIUM_INGOT, MMEBlocks.ADAMANTIUM_BLOCK);
                offerAnvilRecipes(MMEBlocks.ANCIENT_METAL_ANVIL, MMEItems.ANCIENT_METAL_INGOT, MMEBlocks.ANCIENT_METAL_BLOCK);
                offerAnvilRecipes(MMEBlocks.MITHRIL_ANVIL, MMEItems.MITHRIL_INGOT, MMEBlocks.MITHRIL_BLOCK);
                offerAnvilRecipes(MMEBlocks.SILVER_ANVIL, MMEItems.SILVER_INGOT, MMEBlocks.SILVER_BLOCK);
                offerAnvilRecipes(MMEBlocks.COPPER_ANVIL, Items.COPPER_INGOT, Blocks.COPPER_BLOCK);
                offerAnvilRecipes(MMEBlocks.GOLDEN_ANVIL, Items.GOLD_INGOT, Blocks.GOLD_BLOCK);

                offerFishingRodRecipes(MMEItems.ADAMANTIUM_FISHING_ROD, MMEItems.ADAMANTIUM_NUGGET);
                offerFishingRodRecipes(MMEItems.ANCIENT_METAL_FISHING_ROD, MMEItems.ANCIENT_METAL_NUGGET);
                offerFishingRodRecipes(MMEItems.MITHRIL_FISHING_ROD, MMEItems.MITHRIL_NUGGET);
                offerFishingRodRecipes(MMEItems.SILVER_FISHING_ROD, MMEItems.SILVER_NUGGET);
                offerFishingRodRecipes(MMEItems.COPPER_FISHING_ROD, MMEItems.COPPER_NUGGET);
                offerFishingRodRecipes(MMEItems.IRON_FISHING_ROD, Items.IRON_NUGGET);
                offerFishingRodRecipes(MMEItems.GOLDEN_FISHING_ROD, Items.GOLD_NUGGET);
                offerFishingRodRecipes(MMEItems.OBSIDIAN_FISHING_ROD, MMEItems.OBSIDIAN_SHARD);
                offerFishingRodRecipes(MMEItems.FLINT_FISHING_ROD, Items.FLINT);
                
                offerArmorRecipes(MMEItems.ADAMANTIUM_INGOT, MMEItems.ADAMANTIUM_HELMET, MMEItems.ADAMANTIUM_CHESTPLATE, MMEItems.ADAMANTIUM_LEGGINGS, MMEItems.ADAMANTIUM_BOOTS);
                offerArmorRecipes(MMEItems.ANCIENT_METAL_INGOT, MMEItems.ANCIENT_METAL_HELMET, MMEItems.ANCIENT_METAL_CHESTPLATE, MMEItems.ANCIENT_METAL_LEGGINGS, MMEItems.ANCIENT_METAL_BOOTS);
                offerArmorRecipes(MMEItems.MITHRIL_INGOT, MMEItems.MITHRIL_HELMET, MMEItems.MITHRIL_CHESTPLATE, MMEItems.MITHRIL_LEGGINGS, MMEItems.MITHRIL_BOOTS);
                offerArmorRecipes(MMEItems.SILVER_INGOT, MMEItems.SILVER_HELMET, MMEItems.SILVER_CHESTPLATE, MMEItems.SILVER_LEGGINGS, MMEItems.SILVER_BOOTS);
                offerArmorRecipes(Items.COPPER_INGOT, MMEItems.COPPER_HELMET, MMEItems.COPPER_CHESTPLATE, MMEItems.COPPER_LEGGINGS, MMEItems.COPPER_BOOTS);

                offerArmorRecipes(MMEItems.ADAMANTIUM_CHAINS, MMEItems.ADAMANTIUM_CHAINMAIL_HELMET, MMEItems.ADAMANTIUM_CHAINMAIL_CHESTPLATE, MMEItems.ADAMANTIUM_CHAINMAIL_LEGGINGS, MMEItems.ADAMANTIUM_CHAINMAIL_BOOTS);
                offerArmorRecipes(MMEItems.ANCIENT_METAL_CHAINS, MMEItems.ANCIENT_METAL_CHAINMAIL_HELMET, MMEItems.ANCIENT_METAL_CHAINMAIL_CHESTPLATE, MMEItems.ANCIENT_METAL_CHAINMAIL_LEGGINGS, MMEItems.ANCIENT_METAL_CHAINMAIL_BOOTS);
                offerArmorRecipes(MMEItems.MITHRIL_CHAINS, MMEItems.MITHRIL_CHAINMAIL_HELMET, MMEItems.MITHRIL_CHAINMAIL_CHESTPLATE, MMEItems.MITHRIL_CHAINMAIL_LEGGINGS, MMEItems.MITHRIL_CHAINMAIL_BOOTS);
                offerArmorRecipes(MMEItems.SILVER_CHAINS, MMEItems.SILVER_CHAINMAIL_HELMET, MMEItems.SILVER_CHAINMAIL_CHESTPLATE, MMEItems.SILVER_CHAINMAIL_LEGGINGS, MMEItems.SILVER_CHAINMAIL_BOOTS);
                offerArmorRecipes(MMEItems.COPPER_CHAINS, MMEItems.COPPER_CHAINMAIL_HELMET, MMEItems.COPPER_CHAINMAIL_CHESTPLATE, MMEItems.COPPER_CHAINMAIL_LEGGINGS, MMEItems.COPPER_CHAINMAIL_BOOTS);

                netheriteSmithing(MMEItems.ADAMANTIUM_HELMET, RecipeCategory.COMBAT, Items.NETHERITE_HELMET);
                netheriteSmithing(MMEItems.ADAMANTIUM_CHESTPLATE, RecipeCategory.COMBAT, Items.NETHERITE_CHESTPLATE);
                netheriteSmithing(MMEItems.ADAMANTIUM_LEGGINGS, RecipeCategory.COMBAT, Items.NETHERITE_LEGGINGS);
                netheriteSmithing(MMEItems.ADAMANTIUM_BOOTS, RecipeCategory.COMBAT, Items.NETHERITE_BOOTS);

                netheriteSmithing(MMEItems.ADAMANTIUM_SWORD, RecipeCategory.COMBAT, Items.NETHERITE_SWORD);
                netheriteSmithing(MMEItems.ADAMANTIUM_DAGGER, RecipeCategory.COMBAT, MMEItems.NETHERITE_DAGGER);
                netheriteSmithing(MMEItems.ADAMANTIUM_PICKAXE, RecipeCategory.TOOLS, Items.NETHERITE_PICKAXE);
                netheriteSmithing(MMEItems.ADAMANTIUM_WAR_HAMMER, RecipeCategory.COMBAT, MMEItems.NETHERITE_WAR_HAMMER);
                netheriteSmithing(MMEItems.ADAMANTIUM_AXE, RecipeCategory.TOOLS, Items.NETHERITE_AXE);
                netheriteSmithing(MMEItems.ADAMANTIUM_HATCHET, RecipeCategory.TOOLS, MMEItems.NETHERITE_HATCHET);
                netheriteSmithing(MMEItems.ADAMANTIUM_BATTLE_AXE, RecipeCategory.COMBAT, MMEItems.NETHERITE_BATTLE_AXE);
                netheriteSmithing(MMEItems.ADAMANTIUM_SHOVEL, RecipeCategory.TOOLS, Items.NETHERITE_SHOVEL);
                netheriteSmithing(MMEItems.ADAMANTIUM_HOE, RecipeCategory.TOOLS, Items.NETHERITE_HOE);
                netheriteSmithing(MMEItems.ADAMANTIUM_MATTOCK, RecipeCategory.TOOLS, MMEItems.NETHERITE_MATTOCK);
                netheriteSmithing(MMEItems.ADAMANTIUM_SCYTHE, RecipeCategory.TOOLS, MMEItems.NETHERITE_SCYTHE);
                netheriteSmithing(MMEItems.ADAMANTIUM_SHEARS, RecipeCategory.TOOLS, MMEItems.NETHERITE_SHEARS);
                netheriteSmithing(MMEItems.ADAMANTIUM_BUCKET, RecipeCategory.TOOLS, MMEItems.NETHERITE_BUCKET);
                
                saveolRecipes(MMEItems.ADAMANTIUM_INGOT,
                        MMEItems.ADAMANTIUM_SWORD,
                        MMEItems.ADAMANTIUM_DAGGER,
                        MMEItems.ADAMANTIUM_PICKAXE,
                        MMEItems.ADAMANTIUM_WAR_HAMMER,
                        MMEItems.ADAMANTIUM_AXE,
                        MMEItems.ADAMANTIUM_HATCHET,
                        MMEItems.ADAMANTIUM_BATTLE_AXE,
                        MMEItems.ADAMANTIUM_SHOVEL,
                        MMEItems.ADAMANTIUM_HOE,
                        MMEItems.ADAMANTIUM_MATTOCK,
                        MMEItems.ADAMANTIUM_SCYTHE,
                        MMEItems.ADAMANTIUM_SHEARS
                );
                saveolRecipes(MMEItems.ANCIENT_METAL_INGOT,
                        MMEItems.ANCIENT_METAL_SWORD,
                        MMEItems.ANCIENT_METAL_DAGGER,
                        MMEItems.ANCIENT_METAL_PICKAXE,
                        MMEItems.ANCIENT_METAL_WAR_HAMMER,
                        MMEItems.ANCIENT_METAL_AXE,
                        MMEItems.ANCIENT_METAL_HATCHET,
                        MMEItems.ANCIENT_METAL_BATTLE_AXE,
                        MMEItems.ANCIENT_METAL_SHOVEL,
                        MMEItems.ANCIENT_METAL_HOE,
                        MMEItems.ANCIENT_METAL_MATTOCK,
                        MMEItems.ANCIENT_METAL_SCYTHE,
                        MMEItems.ANCIENT_METAL_SHEARS
                );
                saveolRecipes(MMEItems.MITHRIL_INGOT,
                        MMEItems.MITHRIL_SWORD,
                        MMEItems.MITHRIL_DAGGER,
                        MMEItems.MITHRIL_PICKAXE,
                        MMEItems.MITHRIL_WAR_HAMMER,
                        MMEItems.MITHRIL_AXE,
                        MMEItems.MITHRIL_HATCHET,
                        MMEItems.MITHRIL_BATTLE_AXE,
                        MMEItems.MITHRIL_SHOVEL,
                        MMEItems.MITHRIL_HOE,
                        MMEItems.MITHRIL_MATTOCK,
                        MMEItems.MITHRIL_SCYTHE,
                        MMEItems.MITHRIL_SHEARS
                );
                saveolRecipes(MMEItems.SILVER_INGOT,
                        MMEItems.SILVER_SWORD,
                        MMEItems.SILVER_DAGGER,
                        MMEItems.SILVER_PICKAXE,
                        MMEItems.SILVER_WAR_HAMMER,
                        MMEItems.SILVER_AXE,
                        MMEItems.SILVER_HATCHET,
                        MMEItems.SILVER_BATTLE_AXE,
                        MMEItems.SILVER_SHOVEL,
                        MMEItems.SILVER_HOE,
                        MMEItems.SILVER_MATTOCK,
                        MMEItems.SILVER_SCYTHE,
                        MMEItems.SILVER_SHEARS
                );
                saveolRecipes(Items.COPPER_INGOT,
                        MMEItems.COPPER_SWORD,
                        MMEItems.COPPER_DAGGER,
                        MMEItems.COPPER_PICKAXE,
                        MMEItems.COPPER_WAR_HAMMER,
                        MMEItems.COPPER_AXE,
                        MMEItems.COPPER_HATCHET,
                        MMEItems.COPPER_BATTLE_AXE,
                        MMEItems.COPPER_SHOVEL,
                        MMEItems.COPPER_HOE,
                        MMEItems.COPPER_MATTOCK,
                        MMEItems.COPPER_SCYTHE,
                        MMEItems.COPPER_SHEARS
                );
                offerBattleaxeRecipes(Items.IRON_INGOT, MMEItems.IRON_BATTLE_AXE);
                offerDaggerRecipes(Items.IRON_INGOT, MMEItems.IRON_DAGGER);
                offerHatchetRecipes(Items.IRON_INGOT, MMEItems.IRON_HATCHET);
                offerMattockRecipes(Items.IRON_INGOT, MMEItems.IRON_MATTOCK);
                offerScytheRecipes(Items.IRON_INGOT, MMEItems.IRON_SCYTHE);
                offerWarhammerRecipes(Items.IRON_INGOT, MMEItems.IRON_WAR_HAMMER);

                offerBattleaxeRecipes(Items.GOLD_INGOT, MMEItems.GOLDEN_BATTLE_AXE);
                offerDaggerRecipes(Items.GOLD_INGOT, MMEItems.GOLDEN_DAGGER);
                offerHatchetRecipes(Items.GOLD_INGOT, MMEItems.GOLDEN_HATCHET);
                offerMattockRecipes(Items.GOLD_INGOT, MMEItems.GOLDEN_MATTOCK);
                offerScytheRecipes(Items.GOLD_INGOT, MMEItems.GOLDEN_SCYTHE);
                offerWarhammerRecipes(Items.GOLD_INGOT, MMEItems.GOLDEN_WAR_HAMMER);

                offerAxeRecipesInString(Items.FLINT, MMEItems.FLINT_AXE);
                offerHatchetRecipesInString(Items.FLINT, MMEItems.FLINT_HATCHET);
                offerDaggerRecipesIsString(Items.FLINT, MMEItems.FLINT_KNIFE);
                offerShovelRecipesIsString(Items.FLINT, MMEItems.FLINT_SHOVEL);

                offerAxeRecipesInString(MMEItems.OBSIDIAN_SHARD, MMEItems.OBSIDIAN_AXE);
                offerHatchetRecipesInString(MMEItems.OBSIDIAN_SHARD, MMEItems.OBSIDIAN_HATCHET);
                offerDaggerRecipesIsString(MMEItems.OBSIDIAN_SHARD, MMEItems.OBSIDIAN_KNIFE);
                offerShovelRecipesIsString(MMEItems.OBSIDIAN_SHARD, MMEItems.OBSIDIAN_SHOVEL);

                shaped(RecipeCategory.COMBAT, MMEItems.WOODEN_CLUB)
                        .define('a', ItemTags.PLANKS)
                        .define('b', Items.STICK)
                        .pattern("a")
                        .pattern("a")
                        .pattern("b")
                        .unlockedBy(getHasName(Items.STICK), this.has(Items.STICK))
                        .save(this.output);
                shaped(RecipeCategory.COMBAT, MMEItems.WOODEN_CUDGEL)
                        .define('a', ItemTags.PLANKS)
                        .define('b', Items.STICK)
                        .pattern("a")
                        .pattern("b")
                        .unlockedBy(getHasName(Items.STICK), this.has(Items.STICK))
                        .save(this.output);

                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_bowl_milk");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 2)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_bowl_milk_2");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 2)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket_2");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 3)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket_3");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 4)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket_4");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 5)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket_5");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 6)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket_6");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 7)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket_7");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 8)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket_8");
                shapeless(RecipeCategory.FOOD, MMEItems.CHEESE, 9)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(MMETags.MILK_BUCKET)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output, "cheese_form_milk_bucket_9");
                shapeless(RecipeCategory.FOOD, MMEItems.CHOCOLATE)
                        .requires(Items.SUGAR)
                        .requires(Items.COCOA_BEANS)
                        .unlockedBy(getHasName(Items.SUGAR), this.has(Items.SUGAR))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.DOUGH)
                        .requires(MMEItems.FLOUR)
                        .requires(MMEItems.BOWL_WATER)
                        .unlockedBy(getHasName(MMEItems.FLOUR), this.has(MMEItems.FLOUR))
                        .save(this.output, "dough_form_bowl_water");
                shapeless(RecipeCategory.FOOD, MMEItems.DOUGH, 4)
                        .requires(MMEItems.FLOUR)
                        .requires(MMEItems.FLOUR)
                        .requires(MMEItems.FLOUR)
                        .requires(MMEItems.FLOUR)
                        .requires(MMETags.WATER_BUCKET)
                        .unlockedBy(getHasName(MMEItems.FLOUR), this.has(MMEItems.FLOUR))
                        .save(this.output, "dough_form_water_bucket");
                shapeless(RecipeCategory.FOOD, MMEItems.PUMPKIN_SOUP)
                        .requires(Items.PUMPKIN)
                        .requires(MMEItems.BOWL_WATER)
                        .unlockedBy(getHasName(Items.BOWL), this.has(Items.BOWL))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.SORBET)
                        .requires(Items.SUGAR)
                        .requires(Items.BOWL)
                        .requires(Items.SNOWBALL)
                        .requires(MMEItems.ORANGE)
                        .unlockedBy(getHasName(Items.BOWL), this.has(Items.BOWL))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.VEGETABLE_SOUP)
                        .requires(Items.CARROT)
                        .requires(Items.POTATO)
                        .requires(Items.BOWL)
                        .requires(MMEItems.ONION)
                        .unlockedBy(getHasName(Items.BOWL), this.has(Items.BOWL))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.BEEF_STEW)
                        .requires(Items.COOKED_BEEF)
                        .requires(Items.POTATO)
                        .requires(Items.BOWL)
                        .requires(Items.BROWN_MUSHROOM)
                        .unlockedBy(getHasName(Items.BOWL), this.has(Items.BOWL))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.BOWL_MILK, 4)
                        .requires(MMETags.MILK_BUCKET)
                        .requires(Items.BOWL)
                        .requires(Items.BOWL)
                        .requires(Items.BOWL)
                        .requires(Items.BOWL)
                        .unlockedBy(getHasName(Items.BOWL), this.has(Items.BOWL))
                        .save(this.output);
                offerMilkBucketRecipes(MMEItems.MILK_NETHERITE_BUCKET, MMEItems.NETHERITE_BUCKET);
                offerMilkBucketRecipes(MMEItems.MILK_ADAMANTIUM_BUCKET, MMEItems.ADAMANTIUM_BUCKET);
                offerMilkBucketRecipes(MMEItems.MILK_MITHRIL_BUCKET, MMEItems.MITHRIL_BUCKET);
                offerMilkBucketRecipes(MMEItems.MILK_ANCIENT_METAL_BUCKET, MMEItems.ANCIENT_METAL_BUCKET);
                offerMilkBucketRecipes(Items.MILK_BUCKET, Items.BUCKET);
                offerMilkBucketRecipes(MMEItems.MILK_GOLD_BUCKET, MMEItems.GOLD_BUCKET);
                offerMilkBucketRecipes(MMEItems.MILK_COPPER_BUCKET, MMEItems.COPPER_BUCKET);
                offerMilkBucketRecipes(MMEItems.MILK_SILVER_BUCKET, MMEItems.SILVER_BUCKET);
                shapeless(RecipeCategory.FOOD, MMEItems.BOWL_SALAD)
                        .requires(Items.BOWL)
                        .requires(Items.DANDELION)
                        .requires(Items.DANDELION)
                        .requires(Items.DANDELION)
                        .unlockedBy(getHasName(Items.BOWL), this.has(Items.BOWL))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.BOWL_WATER, 3)
                        .requires(MMETags.WATER_BUCKET)
                        .requires(Items.BOWL)
                        .requires(Items.BOWL)
                        .requires(Items.BOWL)
                        .unlockedBy(getHasName(Items.BOWL), this.has(Items.BOWL))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.CEREAL)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(Items.WHEAT)
                        .requires(MMEItems.FLOUR)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.CHICKEN_SOUP)
                        .requires(Items.COOKED_CHICKEN)
                        .requires(MMEItems.BOWL_WATER)
                        .requires(MMEItems.ONION)
                        .requires(Items.CARROT)
                        .unlockedBy(getHasName(Items.COOKED_CHICKEN), this.has(Items.COOKED_CHICKEN))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.CREAM_OF_MUSHROOM_SOUP)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(Items.BROWN_MUSHROOM)
                        .requires(Items.BROWN_MUSHROOM)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.CREAM_OF_VEGETABLE_SOUP)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(Items.CARROT)
                        .requires(Items.POTATO)
                        .requires(MMEItems.ONION)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.ICE_CREAM)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(Items.SUGAR)
                        .requires(Items.COCOA_BEANS)
                        .requires(Items.SNOWBALL)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.MASHED_POTATO)
                        .requires(Items.BAKED_POTATO)
                        .requires(MMEItems.CHEESE)
                        .requires(MMEItems.BOWL_MILK)
                        .unlockedBy(getHasName(MMEItems.BOWL_MILK), this.has(MMEItems.BOWL_MILK))
                        .save(this.output);
                shapeless(RecipeCategory.FOOD, MMEItems.PORRIDGE)
                        .requires(MMEItems.BOWL_WATER)
                        .requires(MMEItems.BLUE_BERRIE)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT_SEEDS)
                        .unlockedBy(getHasName(MMEItems.BOWL_WATER), this.has(MMEItems.BOWL_WATER))
                        .save(this.output);

                offerChainsRecipes(MMEItems.ADAMANTIUM_NUGGET, MMEItems.ADAMANTIUM_CHAINS);
                offerChainsRecipes(MMEItems.MITHRIL_NUGGET, MMEItems.MITHRIL_CHAINS);
                offerChainsRecipes(MMEItems.ANCIENT_METAL_NUGGET, MMEItems.ANCIENT_METAL_CHAINS);
                offerChainsRecipes(Items.IRON_NUGGET, MMEItems.IRON_CHAINS);
                offerChainsRecipes(Items.GOLD_NUGGET, MMEItems.GOLDEN_CHAINS);
                offerChainsRecipes(MMEItems.SILVER_NUGGET, MMEItems.SILVER_CHAINS);
                offerChainsRecipes(MMEItems.COPPER_NUGGET, MMEItems.COPPER_CHAINS);

                offerBucketRecipes(MMEItems.ADAMANTIUM_INGOT, MMEItems.ADAMANTIUM_BUCKET);
                offerBucketRecipes(Items.GOLD_INGOT, MMEItems.GOLD_BUCKET);
                offerBucketRecipes(MMEItems.MITHRIL_INGOT, MMEItems.MITHRIL_BUCKET);
                offerBucketRecipes(MMEItems.SILVER_INGOT, MMEItems.SILVER_BUCKET);
                offerBucketRecipes(MMEItems.ANCIENT_METAL_INGOT, MMEItems.ANCIENT_METAL_BUCKET);
                offerBucketRecipes(Items.COPPER_INGOT, MMEItems.COPPER_BUCKET);
                offerBucketRecipes(Items.NETHERITE_INGOT, MMEItems.NETHERITE_BUCKET);
                shapeless(RecipeCategory.MISC, MMEItems.SINEW, 4)
                        .requires(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                        .save(this.output);
                shaped(RecipeCategory.DECORATIONS, MMEBlocks.EMERALD_ENCHANTING_TABLE)
                        .define('B', Items.BOOK)
                        .define('#', Blocks.OBSIDIAN)
                        .define('D', Items.EMERALD)
                        .pattern(" B ")
                        .pattern("D#D")
                        .pattern("###")
                        .unlockedBy("has_obsidian", this.has(Blocks.OBSIDIAN))
                        .save(this.output);
            }

            private void offerMilkBucketRecipes(Item milkBucket, Item bucket) {
                shapeless(RecipeCategory.FOOD, milkBucket)
                        .requires(bucket)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .requires(MMEItems.BOWL_MILK)
                        .unlockedBy(getHasName(bucket), this.has(bucket))
                        .save(this.output);
            }

            private void offerBucketRecipes(Item ingot, Item bucket) {
                shaped(RecipeCategory.MISC, bucket)
                        .define('a', ingot)
                        .pattern("a a")
                        .pattern(" a ")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerChainsRecipes(Item nugget, Item chains) {
                shaped(RecipeCategory.MISC, chains)
                        .define('a', nugget)
                        .pattern(" a ")
                        .pattern("a a")
                        .pattern(" a ")
                        .unlockedBy(getHasName(nugget), this.has(nugget))
                        .save(this.output);
            }

            private void offerArrowRecipes(Item nugget, Item arrow) {
                shaped(RecipeCategory.COMBAT, arrow)
                        .define('a', nugget)
                        .define('b', Items.STICK)
                        .define('c', Items.FEATHER)
                        .pattern("a")
                        .pattern("b")
                        .pattern("c")
                        .unlockedBy(getHasName(nugget), this.has(nugget))
                        .save(this.output);
            }

            private void offerCraftingTableRecipes(Block craftingTable, Item ingot){
                shaped(RecipeCategory.DECORATIONS, craftingTable)
                        .define('a', ingot)
                        .define('b', Items.LEATHER)
                        .define('c', Items.STICK)
                        .define('d', ItemTags.PLANKS)
                        .pattern("ab")
                        .pattern("cd")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerAnvilRecipes(Block anvil, Item ingot, Block block) {
                shaped(RecipeCategory.DECORATIONS, anvil)
                        .define('a', ingot)
                        .define('b', block)
                        .pattern("bbb")
                        .pattern(" a ")
                        .pattern("aaa")
                        .unlockedBy(getHasName(block), this.has(block))
                        .save(this.output);
            }

            private void offerBarsRecipes(Block bars, Item ingot) {
                shaped(RecipeCategory.DECORATIONS, bars)
                        .define('a', ingot)
                        .pattern("aaa")
                        .pattern("aaa")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerDoorRecipes(Block door, Item ingot) {
                shaped(RecipeCategory.REDSTONE, door)
                        .define('a', ingot)
                        .pattern("aa")
                        .pattern("aa")
                        .pattern("aa");
            }
            private void offerFishingRodRecipes(Item rod, Item nugget) {
                shaped(RecipeCategory.TOOLS, rod)
                        .define('a', nugget)
                        .define('b', Items.STRING)
                        .define('c', Items.STICK)
                        .pattern("  c")
                        .pattern(" cb")
                        .pattern("cab")
                        .unlockedBy(getHasName(nugget), this.has(nugget))
                        .save(this.output);

            }
            private void offerArmorRecipes(Item ingot, Item helmet, Item chestplate, Item leggings, Item boots) {
                offerHelmetRecipes(ingot,  helmet);
                offerChestplateRecipes(ingot,  chestplate);
                offerLeggingsRecipes(ingot,  leggings);
                offerBootsRecipes(ingot,  boots);
            }
            private void offerHelmetRecipes(Item ingot, Item helmet) {
                shaped(RecipeCategory.COMBAT, helmet)
                        .define('a', ingot)
                        .pattern("aaa")
                        .pattern("a a")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void offerChestplateRecipes(Item ingot, Item chestplate) {
                shaped(RecipeCategory.COMBAT, chestplate)
                        .define('a', ingot)
                        .pattern("a a")
                        .pattern("aaa")
                        .pattern("aaa")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void offerLeggingsRecipes(Item ingot, Item leggings) {
                shaped(RecipeCategory.COMBAT, leggings)
                        .define('a', ingot)
                        .pattern("aaa")
                        .pattern("a a")
                        .pattern("a a")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void offerBootsRecipes(Item ingot, Item boots) {
                shaped(RecipeCategory.COMBAT, boots)
                        .define('a', ingot)
                        .pattern("a a")
                        .pattern("a a")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void saveolRecipes(Item ingot,
                                          Item sword,
                                          Item dagger,
                                          Item pickaxe,
                                          Item warhammer,
                                          Item axe,
                                          Item hatchet,
                                          Item battleaxe,
                                          Item shovel,
                                          Item hoe,
                                          Item mattock,
                                          Item scythe,
                                          Item shears) {
                offerSwordRecipes(ingot, sword, dagger);
                offerPickaxeRecipes(ingot, pickaxe, warhammer);
                offerAxeRecipes(ingot, axe, hatchet, battleaxe);
                offerShovelRecipes(ingot, shovel);
                offerHoeRecipes(ingot, hoe, mattock);
                offerScytheRecipes(ingot, scythe);
                offerShearsRecipes(ingot, shears);
            }

            private void offerSwordRecipes(Item ingot, Item sword, Item dagger) {
                offerSwordRecipes(ingot, sword);
                offerDaggerRecipes(ingot, dagger);
            }

            private void offerSwordRecipes(Item ingot, Item sword) {
                shaped(RecipeCategory.COMBAT, sword)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("a")
                        .pattern("a")
                        .pattern("b")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void offerDaggerRecipes(Item ingot, Item dagger) {
                shaped(RecipeCategory.COMBAT, dagger)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("a")
                        .pattern("b")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void offerDaggerRecipesIsString(Item ingot, Item dagger) {
                shaped(RecipeCategory.COMBAT, dagger)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .define('c', MMETags.STRING)
                        .pattern(" a")
                        .pattern("cb")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerPickaxeRecipes(Item ingot, Item pickaxe, Item warhammer) {
                offerPickaxeRecipes(ingot, pickaxe);
                offerWarhammerRecipes(ingot, warhammer);
            }

            private void offerPickaxeRecipes(Item ingot, Item pickaxe) {
                shaped(RecipeCategory.TOOLS, pickaxe)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("aaa")
                        .pattern(" b ")
                        .pattern(" b ")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerWarhammerRecipes(Item ingot, Item warhammer) {
                shaped(RecipeCategory.TOOLS, warhammer)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("aaa")
                        .pattern("aba")
                        .pattern(" b ")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerAxeRecipes(Item ingot, Item axe, Item hatchet, Item battleaxe) {
                offerAxeRecipes(ingot, axe);
                offerHatchetRecipes(ingot, hatchet);
                offerBattleaxeRecipes(ingot, battleaxe);
            }

            private void offerAxeRecipes(Item ingot, Item axe) {
                shaped(RecipeCategory.TOOLS, axe)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("aa")
                        .pattern("ab")
                        .pattern(" b")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void offerAxeRecipesInString(Item ingot, Item axe) {
                shaped(RecipeCategory.TOOLS, axe)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .define('c', MMETags.STRING)
                        .pattern("aa")
                        .pattern("ab")
                        .pattern("cb")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerHatchetRecipes(Item ingot, Item hatchet) {
                shaped(RecipeCategory.TOOLS, hatchet)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("ab")
                        .pattern(" b")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void offerHatchetRecipesInString(Item ingot, Item hatchet) {
                shaped(RecipeCategory.TOOLS, hatchet)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .define('c', MMETags.STRING)
                        .pattern("ab")
                        .pattern("cb")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerBattleaxeRecipes(Item ingot, Item battleaxe) {
                shaped(RecipeCategory.COMBAT, battleaxe)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("a a")
                        .pattern("aba")
                        .pattern(" b ")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerShovelRecipes(Item ingot, Item shovel) {
                shaped(RecipeCategory.TOOLS, shovel)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("a")
                        .pattern("b")
                        .pattern("b")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
            private void offerShovelRecipesIsString(Item ingot, Item shovel) {
                shaped(RecipeCategory.TOOLS, shovel)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .define('c', MMETags.STRING)
                        .pattern(" a")
                        .pattern("cb")
                        .pattern(" b")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerHoeRecipes(Item ingot, Item hoe, Item mattock) {
                offerHoeRecipes(ingot, hoe);
                offerMattockRecipes(ingot, mattock);
            }

            private void offerHoeRecipes(Item ingot, Item hoe) {
                shaped(RecipeCategory.TOOLS, hoe)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("aa")
                        .pattern(" b")
                        .pattern(" b")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerMattockRecipes(Item ingot, Item mattock) {
                shaped(RecipeCategory.TOOLS, mattock)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("aaa")
                        .pattern("ab ")
                        .pattern(" b ")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerScytheRecipes(Item ingot, Item scythe) {
                shaped(RecipeCategory.TOOLS, scythe)
                        .define('a', ingot)
                        .define('b', Items.STICK)
                        .pattern("ba ")
                        .pattern("b a")
                        .pattern("b  ")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }

            private void offerShearsRecipes(Item ingot, Item shears) {
                shaped(RecipeCategory.TOOLS, shears)
                        .define('a', ingot)
                        .pattern(" a")
                        .pattern("a ")
                        .unlockedBy(getHasName(ingot), this.has(ingot))
                        .save(this.output);
            }
        };
    }



    @Override
    public @NotNull String getName() {
        return MME.MOD_ID;
    }
}
