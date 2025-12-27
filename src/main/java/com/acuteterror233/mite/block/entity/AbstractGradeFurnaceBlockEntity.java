package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.component.AtDataComponentTypes;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public abstract class AbstractGradeFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {
    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    private static final Codec<Map<RegistryKey<Recipe<?>>, Integer>> CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    int litTimeRemaining;
    int litTotalTime;
    int cookingTimeSpent;
    int cookingTotalTime;
    int combustionGrade;
    int maxCombustionGrade;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AbstractGradeFurnaceBlockEntity.this.litTimeRemaining;
                case 1 -> AbstractGradeFurnaceBlockEntity.this.litTotalTime;
                case 2 -> AbstractGradeFurnaceBlockEntity.this.cookingTimeSpent;
                case 3 -> AbstractGradeFurnaceBlockEntity.this.cookingTotalTime;
                case 4 -> AbstractGradeFurnaceBlockEntity.this.combustionGrade;
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
                case 4 -> AbstractGradeFurnaceBlockEntity.this.combustionGrade = value;
                case 5 -> AbstractGradeFurnaceBlockEntity.this.maxCombustionGrade = value;
            }
        }

        @Override
        public int size() {
            return 6;
        }
    };
    private final Reference2IntOpenHashMap<RegistryKey<Recipe<?>>> recipesUsed = new Reference2IntOpenHashMap<>();
    private final ServerRecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> matchGetter;

    protected AbstractGradeFurnaceBlockEntity(
            BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType, int maxCombustionGrade
    ) {
        super(blockEntityType, pos, state);
        this.maxCombustionGrade = maxCombustionGrade;
        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(recipeType);
    }

    private boolean isBurning() {
        return this.litTimeRemaining > 0;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registries);
        this.cookingTimeSpent = nbt.getShort("cooking_time_spent", (short)0);
        this.cookingTotalTime = nbt.getShort("cooking_total_time", (short)0);
        this.litTimeRemaining = nbt.getShort("lit_time_remaining", (short)0);
        this.litTotalTime = nbt.getShort("lit_total_time", (short)0);
        this.combustionGrade = nbt.getInt("combustion_grade", (short)0);
        this.maxCombustionGrade = nbt.getInt("max_combustion_grade", (short)0);
        this.recipesUsed.clear();
        this.recipesUsed.putAll(nbt.get("RecipesUsed", CODEC).orElse(Map.of()));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putShort("cooking_time_spent", (short)this.cookingTimeSpent);
        nbt.putShort("cooking_total_time", (short)this.cookingTotalTime);
        nbt.putShort("lit_time_remaining", (short)this.litTimeRemaining);
        nbt.putShort("lit_total_time", (short)this.litTotalTime);
        nbt.putShort("combustion_grade", (short)this.combustionGrade);
        nbt.putShort("max_combustion_grade", (short)this.maxCombustionGrade);
        Inventories.writeNbt(nbt, this.inventory, registries);
        nbt.put("RecipesUsed", CODEC, this.recipesUsed);
    }

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, AbstractGradeFurnaceBlockEntity blockEntity) {
        // 获取面向方向前方的方块状态，并判断是否为空气（即未被阻挡）
        BlockState state1 = world.getBlockState(pos.offset(state.get(HorizontalFacingBlock.FACING)));
        boolean noBlocked = state1.isAir();

        // 判断当前是否正在燃烧
        boolean isBurning = blockEntity.isBurning();

        // 标记是否需要标记脏数据以触发保存或更新
        boolean markDirty = false;

        // 如果正在燃烧，则减少剩余燃烧时间
        if (blockEntity.isBurning()) {
            blockEntity.litTimeRemaining--;
        }

        // 获取输入槽和燃料槽中的物品堆栈
        ItemStack inputSlot = blockEntity.inventory.get(0);
        ItemStack fuelSlot = blockEntity.inventory.get(1);

        // 检查两个槽位是否有物品
        boolean inputSlotExists = !inputSlot.isEmpty();
        boolean fuelSlotExists = !fuelSlot.isEmpty();

        if (noBlocked && (blockEntity.isBurning() || fuelSlotExists && inputSlotExists)) {
            // 构造单个物品的配方输入
            SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(inputSlot);

            // 尝试匹配当前输入对应的烧制配方
            RecipeEntry<? extends AbstractCookingRecipe> recipeEntry;
            if (inputSlotExists) {
                recipeEntry = blockEntity.matchGetter.getFirstMatch(singleStackRecipeInput, world).orElse(null);
            } else {
                recipeEntry = null;
            }

            // 获取燃料等级以及最大堆叠数量限制
            int fuelGrade = blockEntity.getFuelGrade(((GetFuelGradeRegistryExtension) world).getFuelGradeRegistry(), fuelSlot);
            int maxCountPerStack = blockEntity.getMaxCountPerStack();
            Integer itemRequiredCombustionGrade = inputSlot.get(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE);
            int requiredCombustionGrade = itemRequiredCombustionGrade == null ? 0 : itemRequiredCombustionGrade;
            // 检查方块实体是否未在燃烧、燃料等级是否满足要求、是否不超过最大燃烧等级，
            // 并且能否接受当前配方的输出，若条件满足则设置方块实体的燃烧等级
            if (!blockEntity.isBurning() && requiredCombustionGrade <= fuelGrade && fuelGrade <= blockEntity.maxCombustionGrade && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, maxCountPerStack)) {
                blockEntity.combustionGrade = fuelGrade;
                blockEntity.litTimeRemaining = blockEntity.getFuelTime(world.getFuelRegistry(), fuelSlot);
                blockEntity.litTotalTime = blockEntity.litTimeRemaining;

                // 成功点燃后进行相关设置并消耗一个燃料
                if (blockEntity.isBurning()) {
                    markDirty = true;
                    if (fuelSlotExists) {
                        Item fuel = fuelSlot.getItem();
                        fuelSlot.decrement(1);
                        if (fuelSlot.isEmpty()) {
                            blockEntity.inventory.set(1, fuel.getRecipeRemainder());
                        }
                    }
                }
            }
            // 检查方块实体是否正在燃烧、能否接受当前配方输出以及燃烧等级是否满足要求，
            // 若所有条件均满足，则增加方块实体的已燃烧时间计数。
            if (blockEntity.isBurning() && blockEntity.combustionGrade >= requiredCombustionGrade && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, maxCountPerStack)) {
                blockEntity.cookingTimeSpent++;

                // 当烧制时间达到总烧制时间时，完成一次完整的烧制过程
                if (blockEntity.cookingTimeSpent == blockEntity.cookingTotalTime) {
                    blockEntity.cookingTimeSpent = 0;
                    blockEntity.cookingTotalTime = getCookTime(world, blockEntity);

                    // 执行实际的合成操作并记录最后使用的配方
                    if (craftRecipe(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, maxCountPerStack)) {
                        blockEntity.setLastRecipe(recipeEntry);
                    }

                    markDirty = true;
                }
            }
            else {
                // 不满足条件则倒退烹饪计时
                blockEntity.cookingTimeSpent = 0;
            }
        } else if (blockEntity.cookingTimeSpent > 0 || blockEntity.litTimeRemaining > 0) {
            // 快速降低烹饪时间至最小为0
            blockEntity.cookingTimeSpent = MathHelper.clamp(blockEntity.cookingTimeSpent - 2, 0, blockEntity.cookingTotalTime);
            blockEntity.litTimeRemaining = 0;
        }

        // 如果燃烧状态发生变化，则更新方块状态并通知客户端
        if (isBurning != blockEntity.isBurning()) {
            markDirty = true;
            state = state.with(AbstractFurnaceBlock.LIT, blockEntity.isBurning());
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }

        // 如有必要，标记该区块为脏以便同步更改
        if (markDirty) {
            markDirty(world, pos, state);
        }
    }


    private static boolean canAcceptRecipeOutput(
            DynamicRegistryManager dynamicRegistryManager,
            @Nullable RecipeEntry<? extends AbstractCookingRecipe> recipe,
            SingleStackRecipeInput input,
            DefaultedList<ItemStack> inventory,
            int maxCount
    ) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.value().craft(input, dynamicRegistryManager);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = inventory.get(2);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
                    return false;
                } else {
                    return itemStack2.getCount() < maxCount && itemStack2.getCount() < itemStack2.getMaxCount() || itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean craftRecipe(
            DynamicRegistryManager dynamicRegistryManager,
            @Nullable RecipeEntry<? extends AbstractCookingRecipe> recipe,
            SingleStackRecipeInput input,
            DefaultedList<ItemStack> inventory,
            int maxCount
    ) {
        if (recipe != null && canAcceptRecipeOutput(dynamicRegistryManager, recipe, input, inventory, maxCount)) {
            ItemStack itemStack = inventory.get(0);
            ItemStack itemStack2 = recipe.value().craft(input, dynamicRegistryManager);
            ItemStack itemStack3 = inventory.get(2);
            if (itemStack3.isEmpty()) {
                inventory.set(2, itemStack2.copy());
            } else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
                itemStack3.increment(1);
            }

            if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !inventory.get(1).isEmpty() && inventory.get(1).isIn(AtTags.BUCKET)) {
                Item waterItem = Registries.ITEM.get(Registries.ITEM.getId(inventory.get(1).getItem()).withPrefixedPath("water_"));
                inventory.set(1, new ItemStack(waterItem));
            }

            itemStack.decrement(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getFuelTime(FuelRegistry fuelRegistry, ItemStack stack) {
        return fuelRegistry.getFuelTicks(stack);
    }

    private int getFuelGrade(FuelGradeRegistry fuelGradeRegistry, ItemStack stack) {
        return fuelGradeRegistry.getFuelGrade(stack);
    }

    private static int getCookTime(ServerWorld world, AbstractGradeFurnaceBlockEntity furnace) {
        SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(furnace.getStack(0));
        return furnace.matchGetter
                .getFirstMatch(singleStackRecipeInput, world)
                .map(recipe -> ((AbstractCookingRecipe)recipe.value()).getCookingTime())
                .orElse(200);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir != Direction.DOWN || slot != 1 || stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET);
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(itemStack, stack);
        this.inventory.set(slot, stack);
        stack.capCount(this.getMaxCount(stack));
        if (slot == 0 && !bl && this.world instanceof ServerWorld serverWorld) {
            this.cookingTotalTime = getCookTime(serverWorld, this);
            this.cookingTimeSpent = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            ItemStack itemStack = this.inventory.get(1);
            return this.world.getFuelRegistry().isFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
    }

    @Override
    public void setLastRecipe(@Nullable RecipeEntry<?> recipe) {
        if (recipe != null) {
            RegistryKey<Recipe<?>> registryKey = recipe.id();
            this.recipesUsed.addTo(registryKey, 1);
        }
    }

    @Nullable
    @Override
    public RecipeEntry<?> getLastRecipe() {
        return null;
    }

    @Override
    public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<RecipeEntry<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
        player.unlockRecipes(list);

        for (RecipeEntry<?> recipeEntry : list) {
            if (recipeEntry != null) {
                player.onRecipeCrafted(recipeEntry, this.inventory);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeEntry<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        List<RecipeEntry<?>> list = Lists.<RecipeEntry<?>>newArrayList();

        for (Reference2IntMap.Entry<RegistryKey<Recipe<?>>> entry : this.recipesUsed.reference2IntEntrySet()) {
            world.getRecipeManager().get(entry.getKey()).ifPresent(recipe -> {
                list.add(recipe);
                dropExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe.value()).getExperience());
            });
        }

        return list;
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor((float)multiplier * experience);
        float f = MathHelper.fractionalPart((float)multiplier * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            i++;
        }

        ExperienceOrbEntity.spawn(world, pos, i);
    }

    @Override
    public void provideRecipeInputs(RecipeFinder finder) {
        for (ItemStack itemStack : this.inventory) {
            finder.addInput(itemStack);
        }
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        super.onBlockReplaced(pos, oldState);
        if (this.world instanceof ServerWorld serverWorld) {
            this.getRecipesUsedAndDropExperience(serverWorld, Vec3d.ofCenter(pos));
        }
    }
}
