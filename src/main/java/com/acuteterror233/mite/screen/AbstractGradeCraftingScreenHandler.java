package com.acuteterror233.mite.screen;

import com.acuteterror233.mite.component.AtDataComponentTypes;
import com.acuteterror233.mite.screen.slot.CraftingTableResultSlot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.AbstractCraftingScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class AbstractGradeCraftingScreenHandler extends AbstractCraftingScreenHandler {
    private final int DefaultCraftingTime = 100;
    private final PropertyDelegate property = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> CraftingTime[0];
                case 1 -> CraftingTime[1];
                case 2 -> CraftingTime[2];
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> CraftingTime[0] = value;
                case 1 -> CraftingTime[1] = value;
                case 2 -> CraftingTime[2] = MathHelper.clamp(value, 0, 1);
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };
    private final Block[] UpperLevelCraftingTable;
    public final int[] CraftingTime = new int[]{0, DefaultCraftingTime, 0};
    private final TagKey<Item> DisableMaterialsTag;
    public AbstractGradeCraftingScreenHandler(ScreenHandlerType<?> type, int syncId, Block[] upperLevelCraftingTable, TagKey<Item> disableMaterialsTag, int width, int height) {
        super(type, syncId, width, height);
        this.UpperLevelCraftingTable = upperLevelCraftingTable;
        this.DisableMaterialsTag = disableMaterialsTag;
        this.addProperties(this.property);
    }
    @Override
    protected Slot addResultSlot(PlayerEntity player, int x, int y) {
        return this.addSlot(new CraftingTableResultSlot(player, this.craftingInventory, this.craftingResultInventory, this, 0, x, y));
    }
    protected void updateResult(
            ScreenHandler handler,
            ServerWorld world,
            PlayerEntity player,
            RecipeInputInventory craftingInventory,
            CraftingResultInventory resultInventory,
            @Nullable RecipeEntry<CraftingRecipe> recipe
    ) {
        int CraftingTime = 0;
        CraftingRecipeInput craftingRecipeInput = craftingInventory.createRecipeInput();
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<RecipeEntry<CraftingRecipe>> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingRecipeInput, world, recipe);
        if (optional.isPresent()) {
            RecipeEntry<CraftingRecipe> recipeEntry = optional.get();
            CraftingRecipe craftingRecipe = recipeEntry.value();
            if (resultInventory.shouldCraftRecipe(serverPlayerEntity, recipeEntry)) {
                ItemStack craftItem = craftingRecipe.craft(craftingRecipeInput, world.getRegistryManager());
                if (craftItem.isItemEnabled(world.getEnabledFeatures())) {
                    this.property.set(2, 1);
                    isAllowedCrafting(craftingInventory, craftItem);
                    CraftingTime = AdditionalCraftingTime(craftingInventory);
                    itemStack = craftItem;
                }
            }
        }else {
            if (this.getOutputSlot() instanceof CraftingTableResultSlot slot) {
                this.property.set(2, 0);
                this.property.set(0, 0);
                slot.ClearCraftingState();
            }
        }
        this.property.set(1, CraftingTime + this.DefaultCraftingTime);
        resultInventory.setStack(0, itemStack);
        handler.setReceivedStack(0, itemStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
    }

    private void isAllowedCrafting(RecipeInputInventory craftingInventory, ItemStack craftItem) {
        boolean itemFound = false;
        for (Block block : this.UpperLevelCraftingTable) {
            if (block.asItem() == craftItem.getItem()) {
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            for (ItemStack stack : craftingInventory) {
                if (stack.isIn(this.DisableMaterialsTag)) {
                    this.property.set(2, 0);
                    break;
                }
            }
        }
    }

    @Override
    public void sendContentUpdates(){
        if (getOutputSlot() instanceof CraftingTableResultSlot slot) {
            if (slot.isCrafting()) {
                this.CraftingTime[0]++;
                if (this.CraftingTime[0] >= this.CraftingTime[1]){
                    PlayerEntity player = getPlayer();
                    ItemStack stack = slot.getStack();
                    if (!player.getInventory().insertStack(stack)) {
                        player.dropItem(stack, false);
                    }
                    player.getHungerManager().addExhaustion(0.3f);
                    slot.onTakeItem(player, stack);
                    if (slot.getStack().isEmpty()){
                        slot.ClearCraftingState();
                    }
                    this.CraftingTime[0] = 0;
                }
            }
        }
        super.sendContentUpdates();
    }
    public int AdditionalCraftingTime(RecipeInputInventory inventory){
        int CraftingTime = 0;
        for (ItemStack stack : inventory){
            Integer i = stack.get(AtDataComponentTypes.CRAFTING_TIME);
            if (i != null) {
                CraftingTime += i * 20;
            }
        }
        return (int) (CraftingTime / (1 + ((double) (getPlayer().experienceLevel * 2) / 100)) );
    }

    public double getCraftingTime() {
        return MathHelper.clamp((double) this.property.get(0) / this.property.get(1), 0, 1);
    }

    public boolean isAllowCrafting() {
        return this.property.get(2) == 1;
    }

}
