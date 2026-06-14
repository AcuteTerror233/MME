package com.acuteterror233.mite.block;

import com.acuteterror233.mite.inventory.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

/**
 * MME 菜单类型注册中心。
 */
public class MMEMenuTypes {

    public static final MenuType<GradeAnvilMenu> GRADE_ANVIL = register("grade_anvil", GradeAnvilMenu::new);
    public static final MenuType<GradeCraftingTableMenu> GRADE_CRAFTING_TABLE = register("grade_crafting_table", GradeCraftingTableMenu::new);
    public static final MenuType<GradeFurnaceMenu> GRADE_FURNACE = register("grade_furnace", GradeFurnaceMenu::new);
    public static final MenuType<GradeSmokerMenu> SMOKER_GRADE_FURNACE = register("smoker_grade_furnace", GradeSmokerMenu::new);
    public static final MenuType<GradeBlastFurnaceMenu> BLAST_GRADE_FURNACE = register("blast_grade_furnace", GradeBlastFurnaceMenu::new);
    public static final MenuType<MMEEnchantmentMenu> MME_ENCHANTMENT = register("mme_enchantment", MMEEnchantmentMenu::new);

    private static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, id, new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }

    public static void init() {
    }
}
