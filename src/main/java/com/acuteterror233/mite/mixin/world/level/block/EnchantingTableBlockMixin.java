package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.interfaces.EnchantmentMenuExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code EnchantingTableBlock} — opens the vanilla {@code EnchantmentMenu} with its
 * per-instance enchantment power cap raised to 20.
 *
 * <p>Power caps come from {@link EnchantmentMenuExtension}: the mixin default is 15, this block
 * sets 20, and the emerald enchanting table sets 10. Upgrade-offer handling lives in
 * {@code EnchantmentMenuMixin}; the special-moon-phase influence applies to mob XP, not to this
 * menu, and is handled in {@code EnchantmentHelperMixin}.</p>
 */
@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
    /**
     * @author AcuteTerror233
     * @reason Inject the MME enchantment power cap into the vanilla menu opened by the table.
     *
     * <p>Overwrites vanilla {@code getMenuProvider}: builds the menu exactly like vanilla, then
     * sets its maximum enchantment power to 20 via the {@link EnchantmentMenuExtension} duck
     * interface before handing it to the player.</p>
     *
     * @param blockState the enchanting table block state
     * @param level      the level containing the table
     * @param blockPos   the table position
     * @return a menu provider for the boosted enchantment menu, or null when the block entity is
     *         not an enchanting table
     */
    @Overwrite
    public @Nullable MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof EnchantingTableBlockEntity) {
            Component component = ((Nameable)blockEntity).getDisplayName();
            return new SimpleMenuProvider((i, inventory, player) -> {
                EnchantmentMenu menu = new EnchantmentMenu(i, inventory, ContainerLevelAccess.create(level, blockPos));
                ((EnchantmentMenuExtension) menu).MME$SetMaxEnchantmentLevel(20);
                return menu;
            }, component);
        } else {
            return null;
        }
    }
}
