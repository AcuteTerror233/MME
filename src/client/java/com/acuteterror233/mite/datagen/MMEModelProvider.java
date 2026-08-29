package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.interfaces.BlockModelGeneratorsExtension;
import com.acuteterror233.mite.interfaces.ItemModelGeneratorsExtension;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.item.armor.MMEArmorMaterials;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


/**
 * MME 模型数据生成器。
 * 为 MME 物品和方块生成模型 JSON。
 */
public class MMEModelProvider extends FabricModelProvider {
    public MMEModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        BlockModelGeneratorsExtension extendedAccessor = (BlockModelGeneratorsExtension) blockStateModelGenerator;
        blockStateModelGenerator.createTrivialCube(MMEBlocks.ADAMANTIUM_ORE);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.ADAMANTIUM_BLOCK);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.MITHRIL_BLOCK);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.SILVER_BLOCK);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.ANCIENT_METAL_BLOCK);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.MANTLE);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.MITHRIL_ORE);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.SILVER_ORE);

        extendedAccessor.MME$registerAnvil(MMEBlocks.NETHERITE_ANVIL, MMEBlocks.CHIPPED_NETHERITE_ANVIL, MMEBlocks.DAMAGED_NETHERITE_ANVIL);
        extendedAccessor.MME$registerAnvil(MMEBlocks.ADAMANTIUM_ANVIL, MMEBlocks.CHIPPED_ADAMANTIUM_ANVIL, MMEBlocks.DAMAGED_ADAMANTIUM_ANVIL);
        extendedAccessor.MME$registerAnvil(MMEBlocks.MITHRIL_ANVIL, MMEBlocks.CHIPPED_MITHRIL_ANVIL, MMEBlocks.DAMAGED_MITHRIL_ANVIL);
        extendedAccessor.MME$registerAnvil(MMEBlocks.ANCIENT_METAL_ANVIL, MMEBlocks.CHIPPED_ANCIENT_METAL_ANVIL, MMEBlocks.DAMAGED_ANCIENT_METAL_ANVIL);
        extendedAccessor.MME$registerAnvil(MMEBlocks.GOLDEN_ANVIL, MMEBlocks.CHIPPED_GOLDEN_ANVIL, MMEBlocks.DAMAGED_GOLDEN_ANVIL);
        extendedAccessor.MME$registerAnvil(MMEBlocks.SILVER_ANVIL, MMEBlocks.CHIPPED_SILVER_ANVIL, MMEBlocks.DAMAGED_SILVER_ANVIL);
        extendedAccessor.MME$registerAnvil(MMEBlocks.COPPER_ANVIL, MMEBlocks.CHIPPED_COPPER_ANVIL, MMEBlocks.DAMAGED_COPPER_ANVIL);
        
        blockStateModelGenerator.createFurnace(MMEBlocks.CLAY_FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);
        blockStateModelGenerator.createFurnace(MMEBlocks.HARDENED_CLAY_FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);
        blockStateModelGenerator.createFurnace(MMEBlocks.NETHERRACK_FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);
        blockStateModelGenerator.createFurnace(MMEBlocks.OBSIDIAN_FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);
        blockStateModelGenerator.createFurnace(MMEBlocks.SANDSTONE_FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);

        blockStateModelGenerator.createTrivialCube(MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.DEEPSLATE_MITHRIL_ORE);
        blockStateModelGenerator.createTrivialCube(MMEBlocks.DEEPSLATE_SILVER_ORE);

        extendedAccessor.MME$registerCrop(Blocks.WHEAT, BlockStateProperties.AGE_7, 0, 1, 2, 3, 4, 5, 6, 7);
        extendedAccessor.MME$registerCrop(Blocks.CARROTS, BlockStateProperties.AGE_7, 0, 0, 1, 1, 2, 2, 2, 3);
        extendedAccessor.MME$registerCrop(Blocks.POTATOES, BlockStateProperties.AGE_7, 0, 0, 1, 1, 2, 2, 2, 3);
        extendedAccessor.MME$registerCrop(Blocks.BEETROOTS, BlockStateProperties.AGE_3, 0, 1, 2, 3);
        extendedAccessor.MME$registerFarmland();

//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_NUL_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_QUAS_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_POR_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_AN_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_NOX_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_FLAM_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_VAS_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_DES_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_ORT_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_TYM_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_CORP_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_LOR_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_MANI_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_JUX_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_YLEM_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.MITHRIL_SANCT_RUNESTORE);
//
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_NUL_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_QUAS_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_POR_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_AN_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_NOX_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_FLAM_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_VAS_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_DES_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_ORT_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_TYM_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_CORP_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_LOR_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_MANI_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_JUX_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_YLEM_RUNESTORE);
//        blockStateModelGenerator.registerSimpleCubeAll(AtBlocks.ADAMANTIUM_SANCT_RUNESTORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        ItemModelGeneratorsExtension extendedAccessor = (ItemModelGeneratorsExtension) itemModelGenerator;
        //装备部分
        itemModelGenerator.generateTrimmableItem(MMEItems.ADAMANTIUM_HELMET, MMEArmorMaterials.ADAMANTIUM_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ADAMANTIUM_CHESTPLATE, MMEArmorMaterials.ADAMANTIUM_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ADAMANTIUM_LEGGINGS, MMEArmorMaterials.ADAMANTIUM_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ADAMANTIUM_BOOTS, MMEArmorMaterials.ADAMANTIUM_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ADAMANTIUM_CHAINMAIL_HELMET, MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ADAMANTIUM_CHAINMAIL_CHESTPLATE, MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ADAMANTIUM_CHAINMAIL_LEGGINGS, MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ADAMANTIUM_CHAINMAIL_BOOTS, MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ANCIENT_METAL_HELMET, MMEArmorMaterials.ANCIENT_METAL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ANCIENT_METAL_CHESTPLATE, MMEArmorMaterials.ANCIENT_METAL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ANCIENT_METAL_LEGGINGS, MMEArmorMaterials.ANCIENT_METAL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ANCIENT_METAL_BOOTS, MMEArmorMaterials.ANCIENT_METAL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ANCIENT_METAL_CHAINMAIL_HELMET, MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ANCIENT_METAL_CHAINMAIL_CHESTPLATE, MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ANCIENT_METAL_CHAINMAIL_LEGGINGS, MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.ANCIENT_METAL_CHAINMAIL_BOOTS, MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.COPPER_CHAINMAIL_HELMET, MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.COPPER_CHAINMAIL_CHESTPLATE, MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.COPPER_CHAINMAIL_LEGGINGS, MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.COPPER_CHAINMAIL_BOOTS, MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.MITHRIL_HELMET, MMEArmorMaterials.MITHRIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.MITHRIL_CHESTPLATE, MMEArmorMaterials.MITHRIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.MITHRIL_LEGGINGS, MMEArmorMaterials.MITHRIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.MITHRIL_BOOTS, MMEArmorMaterials.MITHRIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.MITHRIL_CHAINMAIL_HELMET, MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.MITHRIL_CHAINMAIL_CHESTPLATE, MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.MITHRIL_CHAINMAIL_LEGGINGS, MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.MITHRIL_CHAINMAIL_BOOTS, MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.RUSTED_IRON_HELMET, MMEArmorMaterials.RUSTED_IRON_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.RUSTED_IRON_CHESTPLATE, MMEArmorMaterials.RUSTED_IRON_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.RUSTED_IRON_LEGGINGS, MMEArmorMaterials.RUSTED_IRON_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.RUSTED_IRON_BOOTS, MMEArmorMaterials.RUSTED_IRON_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        extendedAccessor.MME$registerChainmailTrimmableItem(MMEItems.RUSTED_IRON_CHAINMAIL_HELMET, MMEItems.RUSTED_IRON_HELMET, MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, "helmet");
        extendedAccessor.MME$registerChainmailTrimmableItem(MMEItems.RUSTED_IRON_CHAINMAIL_CHESTPLATE, MMEItems.RUSTED_IRON_CHESTPLATE, MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, "chestplate");
        extendedAccessor.MME$registerChainmailTrimmableItem(MMEItems.RUSTED_IRON_CHAINMAIL_LEGGINGS, MMEItems.RUSTED_IRON_LEGGINGS, MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, "leggings");
        extendedAccessor.MME$registerChainmailTrimmableItem(MMEItems.RUSTED_IRON_CHAINMAIL_BOOTS, MMEItems.RUSTED_IRON_BOOTS, MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, "boots");
        itemModelGenerator.generateTrimmableItem(MMEItems.SILVER_HELMET, MMEArmorMaterials.SILVER_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.SILVER_CHESTPLATE, MMEArmorMaterials.SILVER_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.SILVER_LEGGINGS, MMEArmorMaterials.SILVER_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.SILVER_BOOTS, MMEArmorMaterials.SILVER_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.SILVER_CHAINMAIL_HELMET, MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.SILVER_CHAINMAIL_CHESTPLATE, MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.SILVER_CHAINMAIL_LEGGINGS, MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerator.generateTrimmableItem(MMEItems.SILVER_CHAINMAIL_BOOTS, MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        extendedAccessor.MME$registerChainmailTrimmableItem(MMEItems.GOLDEN_CHAINMAIL_HELMET, Items.GOLDEN_HELMET, MMEArmorMaterials.GOLD_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, "helmet");
        extendedAccessor.MME$registerChainmailTrimmableItem(MMEItems.GOLDEN_CHAINMAIL_CHESTPLATE, Items.GOLDEN_CHESTPLATE, MMEArmorMaterials.GOLD_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, "chestplate");
        extendedAccessor.MME$registerChainmailTrimmableItem(MMEItems.GOLDEN_CHAINMAIL_LEGGINGS, Items.GOLDEN_LEGGINGS, MMEArmorMaterials.GOLD_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, "leggings");
        extendedAccessor.MME$registerChainmailTrimmableItem(MMEItems.GOLDEN_CHAINMAIL_BOOTS, Items.GOLDEN_BOOTS, MMEArmorMaterials.GOLD_MATERIAL_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, "boots");

        //食物部分
        itemModelGenerator.generateFlatItem(MMEItems.BANANA, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.BLUE_BERRIE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.CHEESE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.CHOCOLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.DOUGH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.LEMON, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ONION, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ORANGE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.WORM_COOKED, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.WORM_RAW, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.BEEF_STEW, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.BOWL_MILK, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.BOWL_SALAD, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.BOWL_WATER, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.FLOUR, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.CEREAL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.CHICKEN_SOUP, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.CREAM_OF_MUSHROOM_SOUP, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.CREAM_OF_VEGETABLE_SOUP, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ICE_CREAM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MASHED_POTATO, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.PORRIDGE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.PUMPKIN_SOUP, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SORBET, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.VEGETABLE_SOUP, ModelTemplates.FLAT_ITEM);

        //币,锁链
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_CHAINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_CHAINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_CHAINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_CHAINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_CHAINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_CHAINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_CHAINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_CHAINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_COINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_COINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_COINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_COINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_COINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_COINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_COINS, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_COINS, ModelTemplates.FLAT_ITEM);

        //工具,武器
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_SHEARS, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_SHEARS, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_SHEARS, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_SHEARS, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.IRON_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_SHEARS, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RUSTED_IRON_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_SHEARS, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.COPPER_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_SHEARS, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_BATTLE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_MATTOCK, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_SHEARS, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GOLDEN_WAR_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.FLINT_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.FLINT_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.FLINT_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.FLINT_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.OBSIDIAN_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.OBSIDIAN_HATCHET, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.OBSIDIAN_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.OBSIDIAN_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.WOODEN_CLUB, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.WOODEN_CUDGEL, ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerator.generateSpear(MMEItems.ADAMANTIUM_SPEAR);
        itemModelGenerator.generateSpear(MMEItems.ANCIENT_METAL_SPEAR);
        itemModelGenerator.generateSpear(MMEItems.MITHRIL_SPEAR);
        itemModelGenerator.generateSpear(MMEItems.RUSTED_IRON_SPEAR);
        itemModelGenerator.generateSpear(MMEItems.SILVER_SPEAR);
        itemModelGenerator.generateSpear(MMEItems.FLINT_SPEAR);

        Identifier AXOLOTL = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/axolotl");
        Identifier COD = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/cod");
        Identifier LAVA = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/lava");
        Identifier MILK = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/milk");
        Identifier POWDER_SNOW = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/powder_snow");
        Identifier PUFFERFISH = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/pufferfish");
        Identifier SALMON = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/salmon");
        Identifier TADPOLE = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/tadpole");
        Identifier TROPICAL_FISH = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/tropical_fish");
        Identifier SULFUR_CUBE = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/sulfur_cube");
        Identifier WATER = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/nobuckets/water");

        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_BUCKET, null, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_WATER_BUCKET, WATER, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_LAVA_BUCKET, LAVA, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_POWDER_SNOW_BUCKET, POWDER_SNOW, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_MILK_BUCKET, MILK, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_PUFFERFISH_BUCKET, PUFFERFISH, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_SALMON_BUCKET, SALMON, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_COD_BUCKET, COD, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_TROPICAL_FISH_BUCKET, TROPICAL_FISH, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_AXOLOTL_BUCKET, AXOLOTL, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_TADPOLE_BUCKET, TADPOLE, MMEItems.ADAMANTIUM_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ADAMANTIUM_SULFUR_CUBE_BUCKET, SULFUR_CUBE, MMEItems.ADAMANTIUM_BUCKET);

        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_BUCKET, null, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_WATER_BUCKET, WATER, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_LAVA_BUCKET, LAVA, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_POWDER_SNOW_BUCKET, POWDER_SNOW, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_MILK_BUCKET, MILK, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_PUFFERFISH_BUCKET, PUFFERFISH, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_SALMON_BUCKET, SALMON, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_COD_BUCKET, COD, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_TROPICAL_FISH_BUCKET, TROPICAL_FISH, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_AXOLOTL_BUCKET, AXOLOTL, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_TADPOLE_BUCKET, TADPOLE, MMEItems.ANCIENT_METAL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.ANCIENT_METAL_SULFUR_CUBE_BUCKET, SULFUR_CUBE, MMEItems.ANCIENT_METAL_BUCKET);

        extendedAccessor.MME$registerBucket(MMEItems.COPPER_BUCKET, null, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_WATER_BUCKET, WATER, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_LAVA_BUCKET, LAVA, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_POWDER_SNOW_BUCKET, POWDER_SNOW, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_MILK_BUCKET, MILK, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_PUFFERFISH_BUCKET, PUFFERFISH, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_SALMON_BUCKET, SALMON, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_COD_BUCKET, COD, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_TROPICAL_FISH_BUCKET, TROPICAL_FISH, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_AXOLOTL_BUCKET, AXOLOTL, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_TADPOLE_BUCKET, TADPOLE, MMEItems.COPPER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.COPPER_SULFUR_CUBE_BUCKET, SULFUR_CUBE, MMEItems.COPPER_BUCKET);

        extendedAccessor.MME$registerBucket(MMEItems.GOLD_BUCKET, null, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_WATER_BUCKET, WATER, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_LAVA_BUCKET, LAVA, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_POWDER_SNOW_BUCKET, POWDER_SNOW, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_MILK_BUCKET, MILK, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_PUFFERFISH_BUCKET, PUFFERFISH, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_SALMON_BUCKET, SALMON, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_COD_BUCKET, COD, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_TROPICAL_FISH_BUCKET, TROPICAL_FISH, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_AXOLOTL_BUCKET, AXOLOTL, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_TADPOLE_BUCKET, TADPOLE, MMEItems.GOLD_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.GOLD_SULFUR_CUBE_BUCKET, SULFUR_CUBE, MMEItems.GOLD_BUCKET);

        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_BUCKET, null, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_WATER_BUCKET, WATER, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_LAVA_BUCKET, LAVA, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_POWDER_SNOW_BUCKET, POWDER_SNOW, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_MILK_BUCKET, MILK, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_PUFFERFISH_BUCKET, PUFFERFISH, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_SALMON_BUCKET, SALMON, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_COD_BUCKET, COD, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_TROPICAL_FISH_BUCKET, TROPICAL_FISH, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_AXOLOTL_BUCKET, AXOLOTL, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_TADPOLE_BUCKET, TADPOLE, MMEItems.MITHRIL_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.MITHRIL_SULFUR_CUBE_BUCKET, SULFUR_CUBE, MMEItems.MITHRIL_BUCKET);

        extendedAccessor.MME$registerBucket(MMEItems.SILVER_BUCKET, null, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_WATER_BUCKET, WATER, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_LAVA_BUCKET, LAVA, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_POWDER_SNOW_BUCKET, POWDER_SNOW, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_MILK_BUCKET, MILK, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_PUFFERFISH_BUCKET, PUFFERFISH, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_SALMON_BUCKET, SALMON, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_COD_BUCKET, COD, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_TROPICAL_FISH_BUCKET, TROPICAL_FISH, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_AXOLOTL_BUCKET, AXOLOTL, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_TADPOLE_BUCKET, TADPOLE, MMEItems.SILVER_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.SILVER_SULFUR_CUBE_BUCKET, SULFUR_CUBE, MMEItems.SILVER_BUCKET);

        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_BUCKET, null, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_WATER_BUCKET, WATER, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_LAVA_BUCKET, LAVA, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_POWDER_SNOW_BUCKET, POWDER_SNOW, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_MILK_BUCKET, MILK, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_PUFFERFISH_BUCKET, PUFFERFISH, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_SALMON_BUCKET, SALMON, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_COD_BUCKET, COD, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_TROPICAL_FISH_BUCKET, TROPICAL_FISH, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_AXOLOTL_BUCKET, AXOLOTL, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_TADPOLE_BUCKET, TADPOLE, MMEItems.NETHERITE_BUCKET);
        extendedAccessor.MME$registerBucket(MMEItems.NETHERITE_SULFUR_CUBE_BUCKET, SULFUR_CUBE, MMEItems.NETHERITE_BUCKET);

        Identifier cast = itemModelGenerator.createFlatItemModel(Items.FISHING_ROD, "_cast", ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        Identifier netheriteCast = itemModelGenerator.createFlatItemModel(MMEItems.NETHERITE_FISHING_ROD, "_cast", ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        extendedAccessor.MME$registerFishingRod(MMEItems.NETHERITE_FISHING_ROD, netheriteCast);
        extendedAccessor.MME$registerFishingRod(MMEItems.ADAMANTIUM_FISHING_ROD, cast);
        extendedAccessor.MME$registerFishingRod(MMEItems.MITHRIL_FISHING_ROD, cast);
        extendedAccessor.MME$registerFishingRod(MMEItems.ANCIENT_METAL_FISHING_ROD, cast);
        extendedAccessor.MME$registerFishingRod(MMEItems.SILVER_FISHING_ROD, cast);
        extendedAccessor.MME$registerFishingRod(MMEItems.COPPER_FISHING_ROD, cast);
        extendedAccessor.MME$registerFishingRod(MMEItems.GOLDEN_FISHING_ROD, cast);
        extendedAccessor.MME$registerIronFishingRod(MMEItems.IRON_FISHING_ROD, cast);
        extendedAccessor.MME$registerFishingRod(MMEItems.FLINT_FISHING_ROD, cast);
        extendedAccessor.MME$registerFishingRod(MMEItems.OBSIDIAN_FISHING_ROD, cast);

        itemModelGenerator.generateFlatItem(MMEItems.RAW_ADAMANTIUM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RAW_MITHRIL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.RAW_SILVER, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_INGOT, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(MMEItems.NETHERITE_NUGGET, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ADAMANTIUM_NUGGET, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MITHRIL_NUGGET, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.ANCIENT_METAL_NUGGET, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SILVER_NUGGET, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(MMEItems.FLINT_SHARD, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.OBSIDIAN_SHARD, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(MMEItems.SINEW, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.MANURE,  ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(MMEItems.GHOUL_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.SHADOW_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.WIGHT_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.INVISIBLE_STALKER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.DEMON_SPIDER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.PHASE_SPIDER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.INFERNAL_CREEPER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.FIRE_ELEMENTAL_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.itemModelOutput.accept(MMEItems.VAMPIRE_BAT_SPAWN_EGG, ItemModelUtils.plainModel(itemModelGenerator.generateLayeredItem(MMEItems.VAMPIRE_BAT_SPAWN_EGG, TextureMapping.getItemTexture(Items.BAT_SPAWN_EGG), TextureMapping.getItemTexture(MMEItems.VAMPIRE_BAT_SPAWN_EGG))));
        itemModelGenerator.generateFlatItem(MMEItems.NIGHTWING_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(MMEItems.GIANT_VAMPIRE_BAT_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
    }
}
