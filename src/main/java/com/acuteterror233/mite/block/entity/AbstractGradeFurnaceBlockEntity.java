package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.component.MMEDataComponents;
import com.acuteterror233.mite.interfaces.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public abstract class AbstractGradeFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    private static final Codec<Map<ResourceKey<Recipe<?>>, Integer>> CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);
    protected NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    int litTimeRemaining;
    int litTotalTime;
    int cookingTimeSpent;
    int cookingTotalTime;
    int currentCombustionGrade;
    int maxCombustionGrade;
    protected final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AbstractGradeFurnaceBlockEntity.this.litTimeRemaining;
                case 1 -> AbstractGradeFurnaceBlockEntity.this.litTotalTime;
                case 2 -> AbstractGradeFurnaceBlockEntity.this.cookingTimeSpent;
                case 3 -> AbstractGradeFurnaceBlockEntity.this.cookingTotalTime;
                case 4 -> AbstractGradeFurnaceBlockEntity.this.currentCombustionGrade;
                case 5 -> AbstractGradeFurnaceBlockEntity.this.maxCombustionGrade;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> AbstractGradeFurnaceBlockEntity.this.litTimeRemaining = value;
                case 1 -> AbstractGradeFurnaceBlockEntity.this.litTotalTime = value;
                case 2 -> AbstractGradeFurnaceBlockEntity.this.cookingTimeSpent = value;
                case 3 -> AbstractGradeFurnaceBlockEntity.this.cookingTotalTime = value;
                case 4 -> AbstractGradeFurnaceBlockEntity.this.currentCombustionGrade = value;
                case 5 -> AbstractGradeFurnaceBlockEntity.this.maxCombustionGrade = value;
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    };
    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed = new Reference2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> matchGetter;

    protected AbstractGradeFurnaceBlockEntity(
            BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType, int maxCombustionGrade
    ) {
        super(blockEntityType, pos, state);
        this.maxCombustionGrade = maxCombustionGrade;
        this.matchGetter = RecipeManager.createCheck(recipeType);
    }

    private boolean isBurning() {
        return this.litTimeRemaining > 0;
    }

    @Override
    protected void loadAdditional(ValueInput nbt) {
        super.loadAdditional(nbt);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory);
        this.cookingTimeSpent = nbt.getShortOr("cooking_time_spent", (short)0);
        this.cookingTotalTime = nbt.getShortOr("cooking_total_time", (short)0);
        this.litTimeRemaining = nbt.getShortOr("lit_time_remaining", (short)0);
        this.litTotalTime = nbt.getShortOr("lit_total_time", (short)0);
        this.currentCombustionGrade = nbt.getIntOr("current_combustion_grade", (short)0);
        this.maxCombustionGrade = nbt.getIntOr("max_combustion_grade", (short)0);
        this.recipesUsed.clear();
        this.recipesUsed.putAll(nbt.read("RecipesUsed", CODEC).orElse(Map.of()));
    }

    @Override
    protected void saveAdditional(ValueOutput nbt) {
        super.saveAdditional(nbt);
        nbt.putShort("cooking_time_spent", (short)this.cookingTimeSpent);
        nbt.putShort("cooking_total_time", (short)this.cookingTotalTime);
        nbt.putShort("lit_time_remaining", (short)this.litTimeRemaining);
        nbt.putShort("lit_total_time", (short)this.litTotalTime);
        nbt.putShort("current_combustion_grade", (short)this.currentCombustionGrade);
        nbt.putShort("max_combustion_grade", (short)this.maxCombustionGrade);
        ContainerHelper.saveAllItems(nbt, this.inventory);
        nbt.store("RecipesUsed", CODEC, this.recipesUsed);
    }

    public static void tick(ServerLevel world, BlockPos pos, BlockState state, AbstractGradeFurnaceBlockEntity blockEntity) {
        // Get the block state in the facing direction and check if it's air (i.e., not blocked)
        BlockState state1 = world.getBlockState(pos.relative(state.getValue(HorizontalDirectionalBlock.FACING)));
        boolean noBlocked = state1.isAir();

        // Check if currently burning
        boolean isBurning = blockEntity.isBurning();

        // Flag whether dirty data needs to be marked to trigger save or update
        boolean markDirty = false;

        // If burning, decrease remaining burn time
        if (blockEntity.isBurning()) {
            blockEntity.litTimeRemaining--;
        }

        // Get item stacks from input slot and fuel slot
        ItemStack inputSlot = blockEntity.inventory.get(0);
        ItemStack fuelSlot = blockEntity.inventory.get(1);

        // Check if both slots have items
        boolean inputSlotExists = !inputSlot.isEmpty();
        boolean fuelSlotExists = !fuelSlot.isEmpty();

        if (noBlocked && (blockEntity.isBurning() || fuelSlotExists && inputSlotExists)) {
            // Construct recipe input for a single item
            SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(inputSlot);
            RecipeHolder<? extends AbstractCookingRecipe> recipeEntry;
            // Try to match the current input with a smelting recipe
            if (inputSlotExists) {
                recipeEntry = blockEntity.matchGetter.getRecipeFor(singleStackRecipeInput, world).orElse(null);
            } else {
                recipeEntry = null;
            }

            // Get fuel grade and maximum stack count limit
            int fuelGrade = blockEntity.getFuelGrade(((GetFuelGradeRegistryExtension) world).MME$GetFuelGradeRegistry(), fuelSlot);
            int maxCountPerStack = blockEntity.getMaxStackSize();
            Integer itemRequiredCombustionGrade = inputSlot.get(MMEDataComponents.REQUIRED_COMBUSTION_GRADE);
            int requiredCombustionGrade = itemRequiredCombustionGrade == null ? 0 : itemRequiredCombustionGrade;
            // Check if block entity is not burning, fuel grade meets requirements, doesn't exceed maximum combustion grade,
            // and can accept current recipe output; if conditions are met, set block entity's combustion grade
            if (!blockEntity.isBurning() && requiredCombustionGrade <= fuelGrade && fuelGrade <= blockEntity.maxCombustionGrade && canAcceptRecipeOutput(recipeEntry, singleStackRecipeInput, blockEntity.inventory, maxCountPerStack)) {
                blockEntity.currentCombustionGrade = fuelGrade;
                blockEntity.litTimeRemaining = blockEntity.getFuelTime(world.fuelValues(), fuelSlot);
                blockEntity.litTotalTime = blockEntity.litTimeRemaining;

                // After successful ignition, perform related settings and consume one fuel
                if (blockEntity.isBurning()) {
                    markDirty = true;
                    if (fuelSlotExists) {
                        Item fuel = fuelSlot.getItem();
                        fuelSlot.shrink(1);
                        if (fuelSlot.isEmpty()) {
                            blockEntity.inventory.set(1, fuel.getCraftingRemainder().create());
                        }
                    }
                }
            }
            // Check if block entity is burning, can accept current recipe output, and combustion grade meets requirements;
            // if all conditions are met, increase block entity's cooking time counter.
            if (blockEntity.isBurning() && blockEntity.currentCombustionGrade >= requiredCombustionGrade && canAcceptRecipeOutput(recipeEntry, singleStackRecipeInput, blockEntity.inventory, maxCountPerStack)) {
                blockEntity.cookingTimeSpent++;

                // When cooking time reaches total cooking time, complete a full cooking process
                if (blockEntity.cookingTimeSpent == blockEntity.cookingTotalTime) {
                    blockEntity.cookingTimeSpent = 0;
                    blockEntity.cookingTotalTime = getCookTime(world, blockEntity);

                    // Execute the actual crafting operation and record the last used recipe
                    if (craftRecipe(recipeEntry, singleStackRecipeInput, blockEntity.inventory, maxCountPerStack)) {
                        blockEntity.setRecipeUsed(recipeEntry);
                    }

                    markDirty = true;
                }
            }
            else {
                // If conditions are not met, roll back cooking time
                blockEntity.cookingTimeSpent = 0;
            }
        } else if (blockEntity.cookingTimeSpent > 0 || blockEntity.litTimeRemaining > 0) {
            // Quickly reduce cooking time to minimum of 0
            blockEntity.cookingTimeSpent = Mth.clamp(blockEntity.cookingTimeSpent - 2, 0, blockEntity.cookingTotalTime);
            blockEntity.litTimeRemaining = 0;
        }

        // If burning state changes, update block state and notify client
        if (isBurning != blockEntity.isBurning()) {
            markDirty = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isBurning());
            world.setBlock(pos, state, Block.UPDATE_ALL);
        }

        // If necessary, mark the chunk as dirty to sync changes
        if (markDirty) {
            setChanged(world, pos, state);
        }
    }


    private static boolean canAcceptRecipeOutput(
            @Nullable RecipeHolder<? extends AbstractCookingRecipe> recipe,
            SingleRecipeInput input,
            NonNullList<ItemStack> inventory,
            int maxCount
    ) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.value().assemble(input);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = inventory.get(2);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameComponents(itemStack2, itemStack)) {
                    return false;
                } else {
                    return itemStack2.getCount() < maxCount && itemStack2.getCount() < itemStack2.getMaxStackSize() || itemStack2.getCount() < itemStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean craftRecipe(
            @Nullable RecipeHolder<? extends AbstractCookingRecipe> recipe,
            SingleRecipeInput input,
            NonNullList<ItemStack> inventory,
            int maxCount
    ) {
        if (recipe != null && canAcceptRecipeOutput(recipe, input, inventory, maxCount)) {
            ItemStack itemStack = inventory.get(0);
            ItemStack itemStack2 = recipe.value().assemble(input);
            ItemStack itemStack3 = inventory.get(2);
            if (itemStack3.isEmpty()) {
                inventory.set(2, itemStack2.copy());
            } else if (ItemStack.isSameItemSameComponents(itemStack3, itemStack2)) {
                itemStack3.grow(1);
            }

            if (itemStack.is(Blocks.WET_SPONGE.asItem()) && !inventory.get(1).isEmpty() && inventory.get(1).is(MMEItemTags.BUCKET)) {
                Item waterItem = BuiltInRegistries.ITEM.getValue(BuiltInRegistries.ITEM.getKey(inventory.get(1).getItem()).withPrefix("water_"));
                inventory.set(1, new ItemStack(waterItem));
            }

            itemStack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getFuelTime(FuelValues fuelRegistry, ItemStack stack) {
        return fuelRegistry.burnDuration(stack);
    }

    private int getFuelGrade(FuelGradeRegistry fuelGradeRegistry, ItemStack stack) {
        return fuelGradeRegistry.getFuelGrade(stack);
    }

    private static int getCookTime(ServerLevel world, AbstractGradeFurnaceBlockEntity furnace) {
        SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(furnace.getItem(0));
        return furnace.matchGetter
                .getRecipeFor(singleStackRecipeInput, world)
                .map(recipe -> ((AbstractCookingRecipe)recipe.value()).cookingTime())
                .orElse(200);
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return dir != Direction.DOWN || slot != 1 || stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, stack);
        this.inventory.set(slot, stack);
        stack.limitSize(this.getMaxStackSize(stack));
        if (slot == 0 && !bl && this.level instanceof ServerLevel serverWorld) {
            this.cookingTotalTime = getCookTime(serverWorld, this);
            this.cookingTimeSpent = 0;
            this.setChanged();
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            ItemStack itemStack = this.inventory.get(1);
            return this.level.fuelValues().isFuel(stack) || stack.is(MMEItemTags.BUCKET) && !itemStack.is(MMEItemTags.BUCKET);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            ResourceKey<Recipe<?>> registryKey = recipe.id();
            this.recipesUsed.addTo(registryKey, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> ingredients) {
    }

    public void dropExperienceForRecipesUsed(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesUsedAndDropExperience(player.level(), player.position());
        player.awardRecipes(list);

        for (RecipeHolder<?> recipeEntry : list) {
            if (recipeEntry != null) {
                player.triggerRecipeCrafted(recipeEntry, this.inventory);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesUsedAndDropExperience(ServerLevel world, Vec3 pos) {
        List<RecipeHolder<?>> list = Lists.<RecipeHolder<?>>newArrayList();

        for (Reference2IntMap.Entry<ResourceKey<Recipe<?>>> entry : this.recipesUsed.reference2IntEntrySet()) {
            world.recipeAccess().byKey(entry.getKey()).ifPresent(recipe -> {
                list.add(recipe);
                dropExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe.value()).experience());
            });
        }

        return list;
    }

    private static void dropExperience(ServerLevel world, Vec3 pos, int multiplier, float experience) {
        int i = Mth.floor((float)multiplier * experience);
        float f = Mth.frac((float)multiplier * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            i++;
        }

        ExperienceOrb.award(world, pos, i);
    }

    @Override
    public void fillStackedContents(StackedItemContents finder) {
        for (ItemStack itemStack : this.inventory) {
            finder.accountStack(itemStack);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        super.preRemoveSideEffects(pos, oldState);
        if (this.level instanceof ServerLevel serverWorld) {
            this.getRecipesUsedAndDropExperience(serverWorld, Vec3.atCenterOf(pos));
        }
    }
}
