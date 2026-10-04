package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

/**
 * Mixin for {@code CampfireBlock} — lets campfires be refueled with fuel items.
 *
 * <p>{@code useItemOn} is fully overwritten. Interacting with the held item is split into two
 * branches: items accepted by the vanilla {@code CAMPFIRE_INPUT} property set keep the vanilla
 * "place food on the campfire" flow, while any item carrying the {@code COOKING_FUEL} data
 * component (burn time resolved through a loot context) extends the campfire's remaining ignition
 * time via {@code CampfireBlockEntity#MME$AddRemainingIgnitionTime}, consuming the item and giving
 * back its crafting remainder if one exists. The interaction is evaluated on both sides (the
 * client returns {@code CONSUME} for animation, the server {@code SUCCESS_SERVER} + stat), and the
 * fuel burn actually ticks server-side inside the block entity.</p>
 */
@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    /**
     * @author AcuteTerror233
     * @reason Added fuel addition logic
     *
     * <p>Overwrites vanilla {@code useItemOn}: vanilla food placement first, then MME fuel
     * replenishment, otherwise fall through to the empty-hand branch.</p>
     *
     * @param stack  the item the player used on the campfire
     * @param state  the campfire block state
     * @param world  the level the interaction happened in
     * @param pos    the campfire position
     * @param player the interacting player
     * @param hand   the hand holding {@code stack}
     * @param hit    the hit result of the interaction
     * @return the interaction outcome (SUCCESS_SERVER on the server when accepted, CONSUME on the
     *         client, or TRY_WITH_EMPTY_HAND when the item fits neither branch)
     */
    @Overwrite
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof CampfireBlockEntity campfireBlockEntity && world instanceof ServerLevel serverLevel) {
            ItemStack itemStack = player.getItemInHand(hand);
            int fuel = ResolvableInt.getFromItem(itemStack, DataComponents.COOKING_FUEL, CookingFuel::burnTime, new LootContext.Builder(new LootParams.Builder(serverLevel).create(LootContextParamSets.CONTAINER_PROCESS)).create(Optional.empty()), 0);
            if (world.recipeAccess().propertySet(RecipePropertySet.CAMPFIRE_INPUT).test(itemStack)) {
                if (world instanceof ServerLevel serverWorld && campfireBlockEntity.placeFood(serverWorld, player, itemStack)) {
                    player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return InteractionResult.SUCCESS_SERVER;
                }
                return InteractionResult.CONSUME;
            } else if (fuel > 0){
                if (world instanceof ServerLevel) {
                    campfireBlockEntity.MME$AddRemainingIgnitionTime(fuel);
                    ItemStackTemplate craftingRemainder = itemStack.getItem().getCraftingRemainder();
                    if (craftingRemainder != null && !craftingRemainder.create().isEmpty()) {
                        player.setItemInHand(hand, craftingRemainder.create());
                    } else {
                        itemStack.consumeAndReturn(1, player);
                    }
                    player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return InteractionResult.SUCCESS_SERVER;
                }
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }
}
