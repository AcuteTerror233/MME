package com.acuteterror233.mite.mixin.screen;

import com.acuteterror233.mite.atinterface.PlayerScreenHandlerExtension;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.component.AtDataComponentTypes;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.acuteterror233.mite.screen.slot.PlayerCraftingResultSlot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractCraftingScreenHandler implements PlayerScreenHandlerExtension {
    @Unique
    private final int DefaultCraftingTime = 100;
    @Unique
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
    private final Block[] CraftingTable = new Block[]{AtBlocks.COPPER_CRAFTING_TABLE, AtBlocks.SILVER_CRAFTING_TABLE};
    public final int[] CraftingTime = new int[]{0, DefaultCraftingTime, 0};
    private final TagKey<Item> DisableMaterialsTag = AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL;
    @Final
    @Shadow
    private PlayerEntity owner;

    @Shadow
    public abstract Slot getOutputSlot();

    public PlayerScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, int width, int height) {
        super(type, syncId, width, height);
    }
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        this.addProperties(this.property);
    }
    /**
     * @author AcuteTerror233
     * @reason 更新结果重构
     */
    @Overwrite
    public void onContentChanged(Inventory inventory) {
        if (this.owner.getWorld() instanceof ServerWorld serverWorld) {
            updateResult(this, serverWorld, this.owner, this.craftingInventory, this.craftingResultInventory, null);
        }
    }

    @Override
    protected Slot addResultSlot(PlayerEntity player, int x, int y) {
        return this.addSlot(new PlayerCraftingResultSlot(player, this.craftingInventory, this.craftingResultInventory, (PlayerScreenHandler)(Object)this, 0, x, y));
    }

    @Override
    public void sendContentUpdates() {
        if (getOutputSlot() instanceof PlayerCraftingResultSlot slot) {
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

    @Inject(method = "quickMove", at = @At("HEAD"), cancellable = true)
    protected void quickMove(PlayerEntity player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
        if (this.getSlot(slotIndex) instanceof PlayerCraftingResultSlot) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }
    @Inject(method = "onClosed", at = @At("HEAD"))
    public void onClosed(PlayerEntity player, CallbackInfo ci) {
        this.property.set(0, 0);
        this.property.set(1, DefaultCraftingTime);
        this.property.set(2, 0);
        if (this.getOutputSlot() instanceof PlayerCraftingResultSlot slot) {
            slot.ClearCraftingState();
        }
    }
    @Unique
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
            if (this.getOutputSlot() instanceof PlayerCraftingResultSlot slot) {
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
        for (Block block : this.CraftingTable) {
            if (block.asItem() == craftItem.getItem()) {
                this.property.set(2, 0);
                break;
            }
        }
        for (ItemStack stack : craftingInventory) {
            if (stack.isIn(this.DisableMaterialsTag)) {
                this.property.set(2, 0);
                break;
            }
        }
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

    @Override
    public boolean isAllowCrafting() {
        return this.property.get(2) == 1;
    }

    @Override
    public double getCraftingTime() {
        return MathHelper.clamp((double) this.property.get(0) / this.property.get(1), 0, 1);
    }
}
