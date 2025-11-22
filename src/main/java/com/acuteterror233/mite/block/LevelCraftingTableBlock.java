package com.acuteterror233.mite.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

public class LevelCraftingTableBlock extends Block {
    public LevelCraftingTableBlock(Settings settings) {
        super(settings);
    }
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        }
        return ActionResult.SUCCESS;
    }
    public static Function<RecipeInputInventory, Boolean> getTestRules(TagKey<Item> notAllowedMaterial, Item... HigherLevelMaterials){
        //史山.ing
        return (inventory) -> {
            for (ItemStack stack : inventory) {
                if (stack.isIn(notAllowedMaterial)) {
                    for (Item item : HigherLevelMaterials){
                        if (stack.isOf(item)){
                            for (ItemStack stack1 : inventory){
                                if (stack1.isIn(ItemTags.PLANKS)) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                }
            }
            return true;
        };
    }
}
