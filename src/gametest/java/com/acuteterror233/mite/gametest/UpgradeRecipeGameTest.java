package com.acuteterror233.mite.gametest;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.item.MMEItems;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.List;

/**
 * GameTests for the MME strictly tiered equipment upgrade chain.
 *
 * <p>Copper and silver gear rank as the same entry tier. Crafting-table upgrades
 * ({@code mme:<item>_upgrade}, vanilla {@code transmute} recipes) must accept one piece of the
 * immediately preceding tier's gear plus exactly the configured ingot count, must reject missing
 * gear, a wrong material count, or a cross-tier source (e.g. silver gear cannot skip the iron tier
 * and jump straight to ancient metal), and the assembled result must carry the input's enchantments
 * while leaving fully repaired ({@code damage:0} result template). Spears climb the same tiers.
 * The netherite smithing recipes ({@code mme:netherite_*_smithing}) must likewise forge a fully
 * repaired piece that keeps the adamantium base's enchantments.</p>
 */
public class UpgradeRecipeGameTest {

    /** Protection III is stamped onto every upgraded source item and asserted on the result. */
    private static final int PROTECTION_LEVEL = 3;

    /** Entry-tier upgrade: a damaged enchanted silver helmet plus 5 iron ingots -> full-durability iron helmet. */
    @GameTest
    public void helmetUpgradeKeepsEnchantmentsAndRepairs(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "iron_helmet_upgrade");

        ItemStack source = damagedEnchanted(level, new ItemStack(MMEItems.SILVER_HELMET));
        CraftingInput input = grid(source, new ItemStack(Items.IRON_INGOT), 5);
        if (!recipe.matches(input, level)) {
            helper.fail("Damaged enchanted silver helmet + 5 iron ingots should match mme:iron_helmet_upgrade");
            return;
        }

        ItemStack result = recipe.assemble(input);
        assertUpgraded(level, helper, result, Items.IRON_HELMET);
        helper.succeed();
    }

    /** Copper and silver are the same entry tier, so a copper helmet upgrades to iron just the same. */
    @GameTest
    public void copperHelmetUpgradeGoesToIron(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "iron_helmet_upgrade");

        ItemStack source = damagedEnchanted(level, new ItemStack(Items.COPPER_HELMET));
        CraftingInput input = grid(source, new ItemStack(Items.IRON_INGOT), 5);
        if (!recipe.matches(input, level)) {
            helper.fail("Damaged enchanted copper helmet + 5 iron ingots should match mme:iron_helmet_upgrade");
            return;
        }

        ItemStack result = recipe.assemble(input);
        assertUpgraded(level, helper, result, Items.IRON_HELMET);
        helper.succeed();
    }

    /** The chain continues tier by tier: iron helmet + 5 ancient metal ingots -> ancient metal helmet. */
    @GameTest
    public void helmetIronUpgradeGoesToAncient(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "ancient_metal_helmet_upgrade");

        ItemStack source = damagedEnchanted(level, new ItemStack(Items.IRON_HELMET));
        CraftingInput input = grid(source, new ItemStack(MMEItems.ANCIENT_METAL_INGOT), 5);
        if (!recipe.matches(input, level)) {
            helper.fail("Damaged enchanted iron helmet + 5 ancient metal ingots should match mme:ancient_metal_helmet_upgrade");
            return;
        }

        ItemStack result = recipe.assemble(input);
        assertUpgraded(level, helper, result, MMEItems.ANCIENT_METAL_HELMET);
        helper.succeed();
    }

    /** Cross-tier jumps are forbidden: silver gear cannot skip the iron tier and reach ancient metal. */
    @GameTest
    public void upgradeRejectsCrossTierSource(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "ancient_metal_helmet_upgrade");

        CraftingInput input = grid(new ItemStack(MMEItems.SILVER_HELMET), new ItemStack(MMEItems.ANCIENT_METAL_INGOT), 5);
        if (recipe.matches(input, level)) {
            helper.fail("Silver helmet + ancient metal ingots must not match mme:ancient_metal_helmet_upgrade (cross-tier forbidden)");
            return;
        }
        helper.succeed();
    }

    /** Spears climb the same tiers from the copper entry: copper spear + 2 iron ingots -> iron spear. */
    @GameTest
    public void spearCopperUpgradeGoesToIron(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "iron_spear_upgrade");

        ItemStack source = damagedEnchanted(level, new ItemStack(Items.COPPER_SPEAR));
        CraftingInput input = grid(source, new ItemStack(Items.IRON_INGOT), 2);
        if (!recipe.matches(input, level)) {
            helper.fail("Damaged enchanted copper spear + 2 iron ingots should match mme:iron_spear_upgrade");
            return;
        }

        ItemStack result = recipe.assemble(input);
        assertUpgraded(level, helper, result, Items.IRON_SPEAR);
        helper.succeed();
    }

    /** Silver spear + 2 iron ingots -> iron spear; the chain then continues up to the smithing tier. */
    @GameTest
    public void spearSilverUpgradeGoesToIron(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "iron_spear_upgrade");

        ItemStack source = damagedEnchanted(level, new ItemStack(MMEItems.SILVER_SPEAR));
        CraftingInput input = grid(source, new ItemStack(Items.IRON_INGOT), 2);
        if (!recipe.matches(input, level)) {
            helper.fail("Damaged enchanted silver spear + 2 iron ingots should match mme:iron_spear_upgrade");
            return;
        }

        ItemStack result = recipe.assemble(input);
        assertUpgraded(level, helper, result, Items.IRON_SPEAR);
        helper.succeed();
    }

    /** MME special weapons share the same-tier entry: copper dagger + 1 ancient ingot -> ancient dagger. */
    @GameTest
    public void copperDaggerUpgradeGoesToAncient(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "ancient_metal_dagger_upgrade");

        ItemStack source = damagedEnchanted(level, new ItemStack(MMEItems.COPPER_DAGGER));
        CraftingInput input = grid(source, new ItemStack(MMEItems.ANCIENT_METAL_INGOT), 1);
        if (!recipe.matches(input, level)) {
            helper.fail("Damaged enchanted copper dagger + 1 ancient metal ingot should match mme:ancient_metal_dagger_upgrade");
            return;
        }

        ItemStack result = recipe.assemble(input);
        assertUpgraded(level, helper, result, MMEItems.ANCIENT_METAL_DAGGER);
        helper.succeed();
    }

    /** Without the source piece of gear the upgrade must not match, even with the right ingots. */
    @GameTest
    public void upgradeRejectsMissingSourceGear(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "iron_helmet_upgrade");

        CraftingInput input = grid(ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT), 5);
        if (recipe.matches(input, level)) {
            helper.fail("Iron ingots alone must not match mme:iron_helmet_upgrade");
            return;
        }
        helper.succeed();
    }

    /** The material count is exact: 4 ingots instead of 5 must not match. */
    @GameTest
    public void upgradeRejectsWrongMaterialCount(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "iron_helmet_upgrade");

        CraftingInput input = grid(new ItemStack(MMEItems.SILVER_HELMET), new ItemStack(Items.IRON_INGOT), 4);
        if (recipe.matches(input, level)) {
            helper.fail("Silver helmet + 4 iron ingots must not match mme:iron_helmet_upgrade (exactly 5 required)");
            return;
        }
        helper.succeed();
    }

    /** Netherite smithing must forge a fully repaired netherite helmet that keeps the base's enchantments. */
    @GameTest
    public void netheriteSmithingForgesFullDurability(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<SmithingRecipeInput> recipe = recipe(level, "netherite_helmet_smithing");

        ItemStack base = damagedEnchanted(level, new ItemStack(MMEItems.ADAMANTIUM_HELMET));
        SmithingRecipeInput input = new SmithingRecipeInput(
                new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), base, new ItemStack(Items.NETHERITE_INGOT));
        if (!recipe.matches(input, level)) {
            helper.fail("Damaged enchanted adamantium helmet + template + netherite ingot should match mme:netherite_helmet_smithing");
            return;
        }

        ItemStack result = recipe.assemble(input);
        assertUpgraded(level, helper, result, Items.NETHERITE_HELMET);
        helper.succeed();
    }

    /** Fetches an MME recipe by path under the {@code mme} namespace, typed to the caller's input. */
    @SuppressWarnings("unchecked")
    private static <T extends RecipeInput> Recipe<T> recipe(ServerLevel level, String path) {
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, path));
        return (Recipe<T>) level.recipeAccess().byKey(key).orElseThrow(() ->
                new IllegalStateException("Missing recipe mme:" + path)).value();
    }

    /** Builds a 3x3 crafting grid holding the source gear plus one single-item stack per material slot. */
    private static CraftingInput grid(ItemStack source, ItemStack material, int materialCount) {
        List<ItemStack> items = new ArrayList<>(9);
        items.add(source);
        for (int i = 0; i < materialCount; i++) {
            items.add(material.copyWithCount(1));
        }
        for (int i = items.size(); i < 9; i++) {
            items.add(ItemStack.EMPTY);
        }
        return CraftingInput.of(3, 3, items);
    }

    /** Half-damages the stack and stamps Protection III onto it. */
    private static ItemStack damagedEnchanted(ServerLevel level, ItemStack stack) {
        stack.setDamageValue(stack.getMaxDamage() / 2);
        stack.enchant(protection(level), PROTECTION_LEVEL);
        return stack;
    }

    /** The upgrade result must be the expected item, fully repaired, and keep Protection III. */
    private static void assertUpgraded(ServerLevel level, GameTestHelper helper, ItemStack result, Item expected) {
        if (!result.is(expected)) {
            helper.fail("Expected " + expected + " as the upgrade result, got: " + result);
            return;
        }
        if (result.getDamageValue() != 0) {
            helper.fail("Upgrade result must come out fully repaired, got damage " + result.getDamageValue());
            return;
        }
        int protection = result.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(protection(level));
        if (protection != PROTECTION_LEVEL) {
            helper.fail("Upgrade result must keep the input's enchantments, got Protection " + protection);
            return;
        }
    }

    /** Resolves the Protection enchantment holder in the test level's registry. */
    private static Holder<Enchantment> protection(ServerLevel level) {
        return level.registryAccess().getOrThrow(Enchantments.PROTECTION);
    }
}
