package com.acuteterror233.mite.mixin.screen;

import com.acuteterror233.mite.atinterface.InventoryMenuExtension;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.component.MMEDataComponentTypes;
import com.acuteterror233.mite.registry.tag.MMETags;
import com.acuteterror233.mite.screen.slot.PlayerCraftingResultSlot;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
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
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends AbstractCraftingMenu implements InventoryMenuExtension {
    @Unique
    private final int DefaultCraftingTime = 100;
    @Unique
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
    @Unique
    private final Block[] CraftingTable = new Block[]{AtBlocks.COPPER_CRAFTING_TABLE, AtBlocks.SILVER_CRAFTING_TABLE};
    @Unique
    public final int[] CraftingTime = new int[]{0, DefaultCraftingTime, 0};
    @Unique
    private final TagKey<Item> DisableMaterialsTag = MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL;
    @Final
    @Shadow
    private Player owner;

    @Shadow
    public abstract @NotNull Slot getResultSlot();

    public InventoryMenuMixin(MenuType<?> type, int syncId, int width, int height) {
        super(type, syncId, width, height);
    }
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(Inventory inventory, boolean onServer, Player owner, CallbackInfo ci) {
        this.addDataSlots(this.property);
    }
    /**
     * @author AcuteTerror233
     * @reason 更新结果重构
     */
    @Overwrite
    public void slotsChanged(Container inventory) {
        if (this.owner.level() instanceof ServerLevel serverWorld) {
            updateResult(this, serverWorld, this.owner, this.craftSlots, this.resultSlots);
        }
    }

    @Override
    protected @NotNull Slot addResultSlot(Player player, int x, int y) {
        return this.addSlot(new PlayerCraftingResultSlot(player, this.craftSlots, this.resultSlots, (InventoryMenu)(Object)this, 0, x, y));
    }

    @Override
    public void broadcastChanges() {
        if (getResultSlot() instanceof PlayerCraftingResultSlot slot) {
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

    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    protected void quickMoveStack(Player player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
        if (this.getSlot(slotIndex) instanceof PlayerCraftingResultSlot) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }
    @Inject(method = "removed", at = @At("HEAD"))
    public void onClosed(Player player, CallbackInfo ci) {
        this.property.set(0, 0);
        this.property.set(1, DefaultCraftingTime);
        this.property.set(2, 0);
        if (this.getResultSlot() instanceof PlayerCraftingResultSlot slot) {
            slot.ClearCraftingState();
        }
    }
    @Unique
    protected void updateResult(
            AbstractContainerMenu handler,
            ServerLevel world,
            Player player,
            CraftingContainer craftingInventory,
            ResultContainer resultInventory
    ) {
        int CraftingTime = 0;
        CraftingInput craftingRecipeInput = craftingInventory.asCraftInput();
        ServerPlayer serverPlayerEntity = (ServerPlayer)player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<RecipeHolder<CraftingRecipe>> optional = world.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingRecipeInput, world, (RecipeHolder<CraftingRecipe>) null);
        if (optional.isPresent()) {
            RecipeHolder<CraftingRecipe> recipeEntry = optional.get();
            CraftingRecipe craftingRecipe = recipeEntry.value();
            if (resultInventory.setRecipeUsed(serverPlayerEntity, recipeEntry)) {
                ItemStack craftItem = craftingRecipe.assemble(craftingRecipeInput, world.registryAccess());
                if (craftItem.isItemEnabled(world.enabledFeatures())) {
                    this.property.set(2, 1);
                    isAllowedCrafting(craftingInventory, craftItem);
                    CraftingTime = additionalCraftingTime(craftingInventory);
                    itemStack = craftItem;
                }
            }
        }else {
            if (this.getResultSlot() instanceof PlayerCraftingResultSlot slot) {
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
    @Unique
    private void isAllowedCrafting(CraftingContainer craftingInventory, ItemStack craftItem) {
        for (Block block : this.CraftingTable) {
            if (block.asItem() == craftItem.getItem()) {
                this.property.set(2, 0);
                break;
            }
        }
        for (ItemStack stack : craftingInventory) {
            if (stack.is(this.DisableMaterialsTag)) {
                this.property.set(2, 0);
                break;
            }
        }
    }
    @Unique
    public int additionalCraftingTime(CraftingContainer inventory){
        int CraftingTime = 0;
        for (ItemStack stack : inventory){
            Integer i = stack.get(MMEDataComponentTypes.CRAFTING_TIME);
            if (i != null) {
                CraftingTime += i * 20;
            }
        }
        return (int) (CraftingTime / (1 + ((double) (owner().experienceLevel * 2) / 100)) );
    }

    @Override
    public boolean MME$IsAllowCrafting() {
        return this.property.get(2) == 1;
    }

    @Override
    public double MME$GetCraftingTime() {
        return Mth.clamp((double) this.property.get(0) / this.property.get(1), 0, 1);
    }
}
