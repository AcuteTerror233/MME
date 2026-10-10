package com.acuteterror233.mite.item;

import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

/**
 * The purifying bottle: a drinkable item that lifts every witch curse carried by the
 * drinker (the only cure besides killing the inflicting witch). The empty glass bottle
 * is returned on finish (at runtime — {@code Items.GLASS_BOTTLE} is not yet assigned
 * when the {@code MMEItems} class initializer runs mid vanilla registration).
 */
public class PurifyingBottleItem extends Item {

    /** @param properties item properties (food + consumable set up in the registry). */
    public PurifyingBottleItem(Properties properties) {
        super(properties);
    }

    /** Lifts every curse from the drinking player, then hands back the empty bottle. */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!level.isClientSide()) {
                CurseLogic.removeAllCurses(player);
            }
            super.finishUsingItem(stack, level, entity);
            return ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE));
        }
        return super.finishUsingItem(stack, level, entity);
    }
}
