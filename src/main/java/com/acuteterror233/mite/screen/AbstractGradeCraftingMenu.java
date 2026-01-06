package com.acuteterror233.mite.screen;

import com.acuteterror233.mite.component.MMEDataComponentTypes;
import com.acuteterror233.mite.screen.slot.CraftingTableResultSlot;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class AbstractGradeCraftingMenu extends AbstractCraftingMenu {
    private final int DefaultCraftingTime = 100;
    private final ContainerData property = new ContainerData() {
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
                case 2 -> CraftingTime[2] = Mth.clamp(value, 0, 1);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    private final Block[] UpperLevelCraftingTable;
    public final int[] CraftingTime = new int[]{0, DefaultCraftingTime, 0};
    private final TagKey<Item> DisableMaterialsTag;
    public AbstractGradeCraftingMenu(MenuType<?> type, int syncId, Block[] upperLevelCraftingTable, TagKey<Item> disableMaterialsTag, int width, int height) {
        super(type, syncId, width, height);
        this.UpperLevelCraftingTable = upperLevelCraftingTable;
        this.DisableMaterialsTag = disableMaterialsTag;
        this.addDataSlots(this.property);
    }
    @Override
    protected @NotNull Slot addResultSlot(Player player, int x, int y) {
        return this.addSlot(new CraftingTableResultSlot(player, this.craftSlots, this.resultSlots, this, 0, x, y));
    }
    protected void updateResult(
            AbstractContainerMenu handler,
            ServerLevel world,
            Player player,
            CraftingContainer craftingInventory,
            ResultContainer resultInventory,
            @Nullable RecipeHolder<CraftingRecipe> recipe
    ) {
        int CraftingTime = 0;
        CraftingInput craftingRecipeInput = craftingInventory.asCraftInput();
        ServerPlayer serverPlayerEntity = (ServerPlayer)player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<RecipeHolder<CraftingRecipe>> optional = world.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingRecipeInput, world, recipe);
        if (optional.isPresent()) {
            RecipeHolder<CraftingRecipe> recipeEntry = optional.get();
            CraftingRecipe craftingRecipe = recipeEntry.value();
            if (resultInventory.setRecipeUsed(serverPlayerEntity, recipeEntry)) {
                ItemStack craftItem = craftingRecipe.assemble(craftingRecipeInput, world.registryAccess());
                if (craftItem.isItemEnabled(world.enabledFeatures())) {
                    this.property.set(2, 1);
                    isAllowedCrafting(craftingInventory, craftItem);
                    CraftingTime = AdditionalCraftingTime(craftingInventory);
                    itemStack = craftItem;
                }
            }
        }else {
            if (this.getResultSlot() instanceof CraftingTableResultSlot slot) {
                this.property.set(2, 0);
                this.property.set(0, 0);
                slot.ClearCraftingState();
            }
        }
        this.property.set(1, CraftingTime + this.DefaultCraftingTime);
        resultInventory.setItem(0, itemStack);
        handler.setRemoteSlot(0, itemStack);
        serverPlayerEntity.connection.send(new ClientboundContainerSetSlotPacket(handler.containerId, handler.incrementStateId(), 0, itemStack));
    }

    private void isAllowedCrafting(CraftingContainer craftingInventory, ItemStack craftItem) {
        boolean itemFound = true;
        for (Block block : this.UpperLevelCraftingTable) {
            if (block.asItem() == craftItem.getItem()) {
                itemFound = false;
                break;
            }
        }
        if (itemFound) {
            for (ItemStack stack : craftingInventory) {
                if (stack.is(this.DisableMaterialsTag)) {
                    this.property.set(2, 0);
                    break;
                }
            }
        }
    }

    @Override
    public void broadcastChanges(){
        if (getResultSlot() instanceof CraftingTableResultSlot slot) {
            if (slot.isCrafting()) {
                this.CraftingTime[0]++;
                if (this.CraftingTime[0] >= this.CraftingTime[1]){
                    Player player = owner();
                    ItemStack stack = slot.getItem();
                    if (!player.getInventory().add(stack)) {
                        player.drop(stack, false);
                    }
                    player.getFoodData().addExhaustion(0.3f);
                    slot.onTake(player, stack);
                    if (slot.getItem().isEmpty()){
                        slot.ClearCraftingState();
                    }
                    this.CraftingTime[0] = 0;
                }
            }
        }
        super.broadcastChanges();
    }
    public int AdditionalCraftingTime(CraftingContainer inventory){
        int CraftingTime = 0;
        for (ItemStack stack : inventory){
            Integer i = stack.get(MMEDataComponentTypes.CRAFTING_TIME);
            if (i != null) {
                CraftingTime += i * 20;
            }
        }
        return (int) (CraftingTime / (1 + ((double) (owner().experienceLevel * 2) / 100)) );
    }

    public double getCraftingTime() {
        return Mth.clamp((double) this.property.get(0) / this.property.get(1), 0, 1);
    }

    public boolean isAllowCrafting() {
        return this.property.get(2) == 1;
    }

}
