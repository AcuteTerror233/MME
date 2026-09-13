package com.acuteterror233.mite.inventory;

import com.acuteterror233.mite.block.MMEMenuTypes;
import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

/**
 * Grade anvil menu, extends {@link ItemCombinerMenu}.
 * Restricts available material grades, controlling the cost and output of repair/combination operations.
 */
public class GradeAnvilMenu extends ItemCombinerMenu {
    private final DataSlot levelCost = DataSlot.standalone();
    private final TagKey<Item> notAllowedMaterial;
    private boolean keepSecondSlot = false;
    private int repairItemUsage;
    @Nullable
    private String newItemName;

    public GradeAnvilMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL, null);
    }

    public GradeAnvilMenu(int syncId, Inventory playerInventory, ContainerLevelAccess context, TagKey<Item> notAllowedMaterial) {
        super(MMEMenuTypes.GRADE_ANVIL, syncId, playerInventory, context, getForgingSlotsManager());
        this.addDataSlot(this.levelCost);
        this.notAllowedMaterial = notAllowedMaterial;
    }

    public static ItemCombinerMenuSlotDefinition getForgingSlotsManager() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, stack -> true)
                .withSlot(1, 76, 47, stack -> true)
                .withResultSlot(2, 134, 47).build();
    }

    @Nullable
    private static String sanitize(String name) {
        String string = StringUtil.filterText(name);
        return string.length() <= 50 ? string : null;
    }

    @Override
    protected void onTake(Player player, @NonNull ItemStack stack) {
        if (!player.hasInfiniteMaterials()) {
            player.giveExperienceLevels(-this.levelCost.get());
        }
        int i;
        if (this.repairItemUsage > 0) {
            ItemStack itemStack = this.inputSlots.getItem(1);
            if (!itemStack.isEmpty() && itemStack.getCount() > this.repairItemUsage) {
                itemStack.shrink(this.repairItemUsage);
                this.inputSlots.setItem(1, itemStack);
            } else {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
            i = this.inputSlots.getItem(0).getDamageValue() - this.resultSlots.getItem(0).getDamageValue();
        } else {
            i = 0;
            if (!this.keepSecondSlot) {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
        }
        this.access.execute((world, pos) -> {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof AnvilBlockEntity blockEntity) {
                int i1 = blockEntity.getDamage() + i;
                blockEntity.addDamage(i);
                if (i1 >= blockEntity.getMaxDamage()) {
                    if (this.player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.closeContainer();
                    }
                }
                world.levelEvent(1030, pos, 0);
            }
        });
        this.levelCost.set(0);
        this.inputSlots.setItem(0, ItemStack.EMPTY);
    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(BlockTags.ANVIL);
    }
    @Override
    public boolean stillValid(@NonNull Player player) {
        return this.access.evaluate((world, pos) -> player.isWithinBlockInteractionRange(pos, 4.0) && world.getBlockState(pos.above()).isAir(), true);
    }

    /**
     * Creates and sets the crafting result item.
     * This method processes items in the input slots, calculating repair costs, enchantment levels,
     * and ultimately generating the result item placed in the output slot based on item type,
     * enchantment attributes, durability, and other factors.
     * Main logic includes:
     * 1. Check if the input item can store enchantments.
     * 2. Process the item in the second input slot (may be repair material or enchanted book).
     * 3. Calculate repair based on item durability.
     * 4. Merge enchantment attributes of both items and check compatibility.
     * 5. Set custom name (if any).
     * 6. Calculate total repair cost and update player's experience level consumption.
     * 7. Place the final result in the output slot.
     */
    @Override
    public void createResult() {
        // Get the item from the first input slot
        ItemStack itemStack = this.inputSlots.getItem(0);
        this.keepSecondSlot = false;
        this.levelCost.set(0);
        int i = 0;
        long repair_cost = 0L;
        int j = 0;

        // Check if the first item exists and can store enchantments
        if (!itemStack.isEmpty() && EnchantmentHelper.canStoreEnchantments(itemStack)) {
            // Copy the first item for subsequent operations
            ItemStack itemStack2 = itemStack.copy();
            // Get the item from the second input slot
            ItemStack itemStack3 = this.inputSlots.getItem(1);
            // Build a mutable enchantment map object
            ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(itemStack2));
            // Accumulate repair costs of both items
            repair_cost += (long) itemStack.getOrDefault(DataComponents.REPAIR_COST, 0) + itemStack3.getOrDefault(DataComponents.REPAIR_COST, 0);
            this.repairItemUsage = 0;

            // If the second item exists
            if (!itemStack3.isEmpty()) {
                boolean bl = itemStack3.has(DataComponents.STORED_ENCHANTMENTS);

                // If the first item is damageable and the second item is valid repair material
                if (itemStack2.isDamageableItem() && itemStack.isValidRepairItem(itemStack3) && this.notAllowedMaterial != null && !itemStack3.is(this.notAllowedMaterial)) {
                    int i1 = 1;
                    // If the second item is a metal nugget, repair efficiency is reduced
                    if (itemStack3.is(MMEItemTags.NUGGET)) {
                        i1 = 6;
                    }
                    // Calculate maximum repairable durability
                    int Damage = Math.min(itemStack2.getDamageValue(), itemStack2.getMaxDamage() / i1);
                    if (Damage <= 0) {
                        // If repair is not possible, clear the result slot and return
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
                    int m;
                    // Loop to repair the item until reaching maximum count or full repair
                    for (m = 0; Damage > 0 && m < itemStack3.getCount(); m++) {
                        int n = itemStack2.getDamageValue() - Damage;
                        itemStack2.setDamageValue(n);
                        Damage = Math.min(itemStack2.getDamageValue(), itemStack2.getMaxDamage() / 4);
                    }
                    j = 1;
                    i += j;
                    this.repairItemUsage = m;
                } else {
                    // If not a repair operation, process enchantment merging logic
                    if (!bl && (!itemStack2.is(itemStack3.getItem()) || !itemStack2.isDamageableItem())) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
                    if (itemStack2.isDamageableItem() && !bl) {
                        // Calculate remaining durability after merging both items' durability
                        int kx = itemStack.getMaxDamage() - itemStack.getDamageValue();
                        int m = itemStack3.getMaxDamage() - itemStack3.getDamageValue();
                        int n = m + itemStack2.getMaxDamage() * 12 / 100;
                        int o = kx + n;
                        int p = itemStack2.getMaxDamage() - o;
                        if (p < 0) {
                            p = 0;
                        }

                        if (p < itemStack2.getDamageValue()) {
                            itemStack2.setDamageValue(p);
                            i += 2;
                        }
                    }

                    // Get enchantment information of the second item
                    ItemEnchantments itemEnchantmentsComponent = EnchantmentHelper.getEnchantmentsForCrafting(itemStack3);
                    boolean bl2 = false;
                    boolean bl3 = false;

                    // Iterate through all enchantments of the second item
                    for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemEnchantmentsComponent.entrySet()) {
                        Holder<Enchantment> registryEntry = entry.getKey();
                        int q = builder.getLevel(registryEntry);
                        int r = entry.getIntValue();
                        r = q == r ? r + 1 : Math.max(r, q);
                        Enchantment enchantment = registryEntry.value();
                        boolean bl4 = enchantment.canEnchant(itemStack);
                        if (this.player.hasInfiniteMaterials() || itemStack.is(Items.ENCHANTED_BOOK)) {
                            bl4 = true;
                        }

                        // Check compatibility of new enchantment with existing enchantments
                        for (Holder<Enchantment> registryEntry2 : builder.keySet()) {
                            if (!registryEntry2.equals(registryEntry) && !Enchantment.areCompatible(registryEntry, registryEntry2)) {
                                bl4 = false;
                                i++;
                            }
                        }

                        if (!bl4) {
                            bl3 = true;
                        } else {
                            bl2 = true;
                            if (r > enchantment.getMaxLevel()) {
                                r = enchantment.getMaxLevel();
                            }

                            builder.set(registryEntry, r);
                            int s = enchantment.getAnvilCost();
                            if (bl) {
                                s = Math.max(1, s / 2);
                            }

                            i += s * r;
                            if (itemStack.getCount() > 1) {
                                i = 40;
                            }
                        }
                    }

                    // If all enchantments are incompatible, clear the result slot and return
                    if (bl3 && !bl2) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
                }
            }

            // Process custom name logic
            if (this.newItemName != null && !StringUtil.isBlank(this.newItemName)) {
                if (!this.newItemName.equals(itemStack.getHoverName().getString())) {
                    j = 1;
                    i += j;
                    itemStack2.set(DataComponents.CUSTOM_NAME, Component.literal(this.newItemName));
                }
            } else if (itemStack.has(DataComponents.CUSTOM_NAME)) {
                j = 1;
                i += j;
                itemStack2.remove(DataComponents.CUSTOM_NAME);
            }

            // Calculate total repair cost
            int t = i <= 0 ? 0 : (int) Mth.clamp(repair_cost + i, 0L, 2147483647L);
            this.levelCost.set(j == i ? 0 : t);
            if (i <= 0) {
                itemStack2 = ItemStack.EMPTY;
            }
            if (j == i && j > 0) {
                if (this.levelCost.get() >= 40) {
                    this.levelCost.set(39);
                }

                this.keepSecondSlot = true;
            }

            // If repair cost is too high and player doesn't have infinite materials permission, clear the result slot
            if (this.levelCost.get() >= 40 && !this.player.hasInfiniteMaterials()) {
                itemStack2 = ItemStack.EMPTY;
            }

            // Update repair cost and enchantment information of the result item
            if (!itemStack2.isEmpty()) {
                int kxx = itemStack2.getOrDefault(DataComponents.REPAIR_COST, 0);
                if (kxx < itemStack3.getOrDefault(DataComponents.REPAIR_COST, 0)) {
                    kxx = itemStack3.getOrDefault(DataComponents.REPAIR_COST, 0);
                }

                if (j != i || j == 0) {
                    kxx = AnvilMenu.calculateIncreasedRepairCost(kxx);
                }

                itemStack2.set(DataComponents.REPAIR_COST, kxx);
                EnchantmentHelper.setEnchantments(itemStack2, builder.toImmutable());
            }

            // Place the final result in the output slot and broadcast changes
            this.resultSlots.setItem(0, itemStack2);
            this.broadcastChanges();
        } else {
            // If the first item is invalid, clear the result slot
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            this.levelCost.set(0);
        }
    }


    public boolean setNewItemName(String newItemName) {
        String string = sanitize(newItemName);
        if (string != null && !string.equals(this.newItemName)) {
            this.newItemName = string;
            if (this.getSlot(2).hasItem()) {
                ItemStack itemStack = this.getSlot(2).getItem();
                if (StringUtil.isBlank(string)) {
                    itemStack.remove(DataComponents.CUSTOM_NAME);
                } else {
                    itemStack.set(DataComponents.CUSTOM_NAME, Component.literal(string));
                }
            }

            this.createResult();
            return true;
        } else {
            return false;
        }
    }

    public int getLevelCost() {
        return this.levelCost.get();
    }

    @Override
    protected boolean mayPickup(Player player, boolean present) {
        return (player.hasInfiniteMaterials() || player.experienceLevel >= this.levelCost.get());
    }
}
