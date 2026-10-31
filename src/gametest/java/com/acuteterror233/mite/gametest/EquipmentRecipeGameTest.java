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
 * GameTests for the MME equipment recipes.
 *
 * <p>The MME-only tiers (ancient metal, mithril, adamantium) are crafted straight from their
 * ingots on the crafting table, exactly like silver: there is no lower-tier piece of gear to
 * transmute from. These tests pin the shaped patterns down and assert that the former
 * crafting-table transmute upgrades no longer exist. The netherite smithing recipes
 * ({@code mme:netherite_*_smithing}) are the one remaining upgrade path and must forge a fully
 * repaired piece that keeps the adamantium base's enchantments.</p>
 */
public class EquipmentRecipeGameTest {

    /** Protection III is stamped onto the smithing base item and asserted on the result. */
    private static final int PROTECTION_LEVEL = 3;

    /** Five ancient metal ingots make a helmet (3 on top, 2 on the sides). */
    @GameTest
    public void ancientMetalHelmetCraftsFromIngots(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "ancient_metal_helmet");

        ItemStack ingot = new ItemStack(MMEItems.ANCIENT_METAL_INGOT);
        CraftingInput input = grid(ingot, ingot, ingot,
                                   ingot, null, ingot,
                                   null, null, null);
        if (!recipe.matches(input, level)) {
            helper.fail("Five ancient metal ingots must match mme:ancient_metal_helmet");
            return;
        }
        assertCrafted(helper, recipe.assemble(input), MMEItems.ANCIENT_METAL_HELMET, "helmet");
        helper.succeed();
    }

    /** Eight mithril ingots make a chestplate (two full rows plus the outer two of the third). */
    @GameTest
    public void mithrilChestplateCraftsFromIngots(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "mithril_chestplate");

        ItemStack ingot = new ItemStack(MMEItems.MITHRIL_INGOT);
        CraftingInput input = grid(ingot, null, ingot,
                                   ingot, ingot, ingot,
                                   ingot, ingot, ingot);
        if (!recipe.matches(input, level)) {
            helper.fail("Eight mithril ingots must match mme:mithril_chestplate");
            return;
        }
        assertCrafted(helper, recipe.assemble(input), MMEItems.MITHRIL_CHESTPLATE, "chestplate");
        helper.succeed();
    }

    /** Three adamantium ingots on two sticks make a pickaxe, i.e. the vanilla tool pattern. */
    @GameTest
    public void adamantiumPickaxeCraftsFromIngots(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "adamantium_pickaxe");

        ItemStack ingot = new ItemStack(MMEItems.ADAMANTIUM_INGOT);
        ItemStack stick = new ItemStack(Items.STICK);
        CraftingInput input = grid(ingot, ingot, ingot,
                                   null, stick, null,
                                   null, stick, null);
        if (!recipe.matches(input, level)) {
            helper.fail("Three adamantium ingots plus two sticks must match mme:adamantium_pickaxe");
            return;
        }
        assertCrafted(helper, recipe.assemble(input), MMEItems.ADAMANTIUM_PICKAXE, "pickaxe");
        helper.succeed();
    }

    /** Two adamantium ingots on one stick make a sword. */
    @GameTest
    public void adamantiumSwordCraftsFromIngots(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Recipe<CraftingInput> recipe = recipe(level, "adamantium_sword");

        ItemStack ingot = new ItemStack(MMEItems.ADAMANTIUM_INGOT);
        ItemStack stick = new ItemStack(Items.STICK);
        CraftingInput input = grid(ingot, null, null,
                                   ingot, null, null,
                                   stick, null, null);
        if (!recipe.matches(input, level)) {
            helper.fail("Two adamantium ingots plus one stick must match mme:adamantium_sword");
            return;
        }
        assertCrafted(helper, recipe.assemble(input), MMEItems.ADAMANTIUM_SWORD, "sword");
        helper.succeed();
    }

    /** The former tiered transmute upgrades must be gone; gear is crafted from ingots instead. */
    @GameTest
    public void transmuteUpgradesAreGone(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        for (String path : new String[]{
                "iron_helmet_upgrade",
                "ancient_metal_helmet_upgrade",
                "mithril_chestplate_upgrade",
                "adamantium_dagger_upgrade",
                "adamantium_pickaxe_upgrade"}) {
            ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE,
                    Identifier.fromNamespaceAndPath(MME.MOD_ID, path));
            if (level.recipeAccess().byKey(key).isPresent()) {
                helper.fail("Transmute upgrade mme:" + path + " should no longer exist");
                return;
            }
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
        if (!result.is(Items.NETHERITE_HELMET)) {
            helper.fail("Expected a netherite helmet, got: " + result);
            return;
        }
        if (result.getDamageValue() != 0) {
            helper.fail("Smithing result must come out fully repaired, got damage " + result.getDamageValue());
            return;
        }
        int protection = result.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                .getLevel(protection(level));
        if (protection != PROTECTION_LEVEL) {
            helper.fail("Smithing result must keep the base's enchantments, got Protection " + protection);
            return;
        }
        helper.succeed();
    }

    /** Fetches an MME recipe by path under the {@code mme} namespace, typed to the caller's input. */
    @SuppressWarnings("unchecked")
    private static <T extends RecipeInput> Recipe<T> recipe(ServerLevel level, String path) {
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, path));
        return (Recipe<T>) level.recipeAccess().byKey(key).orElseThrow(() ->
                new IllegalStateException("Missing recipe mme:" + path)).value();
    }

    /** Builds a 3x3 crafting grid from nine slots in row-major order; {@code null} means empty. */
    private static CraftingInput grid(ItemStack... slots) {
        List<ItemStack> items = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            items.add(i < slots.length && slots[i] != null ? slots[i].copy() : ItemStack.EMPTY);
        }
        return CraftingInput.of(3, 3, items);
    }

    /** Half-damages the stack and stamps Protection III onto it. */
    private static ItemStack damagedEnchanted(ServerLevel level, ItemStack stack) {
        stack.setDamageValue(stack.getMaxDamage() / 2);
        stack.enchant(protection(level), PROTECTION_LEVEL);
        return stack;
    }

    /** The crafted result must be the expected item and come out undamaged. */
    private static void assertCrafted(GameTestHelper helper, ItemStack result, Item expected, String label) {
        if (!result.is(expected)) {
            helper.fail("Expected " + expected + " as the crafted " + label + ", got: " + result);
            return;
        }
        if (result.getDamageValue() != 0) {
            helper.fail("Crafted " + label + " must come out undamaged, got damage " + result.getDamageValue());
        }
    }

    /** Resolves the Protection enchantment holder in the test level's registry. */
    private static Holder<Enchantment> protection(ServerLevel level) {
        return level.registryAccess().getOrThrow(Enchantments.PROTECTION);
    }
}
