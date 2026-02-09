package com.acuteterror233.mite.inventory;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.registry.tag.MMETags;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
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
        super(MMEBlocks.GRADE_ANVIL, syncId, playerInventory, context, getForgingSlotsManager());
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
    protected void onTake(Player player, ItemStack stack) {
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
                blockEntity.addDamage(i);
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
    public boolean stillValid(Player player) {
        return this.access.evaluate((world, pos) -> player.canInteractWithBlock(pos, 4.0) && world.getBlockState(pos.above()).isAir(), true);
    }

    @Override
    public void createResult() {
        ItemStack itemStack = this.inputSlots.getItem(0);
        this.keepSecondSlot = false;
        this.levelCost.set(0);
        int i = 0;
        long repair_cost = 0L;
        int j = 0;
        if (!itemStack.isEmpty() && EnchantmentHelper.canStoreEnchantments(itemStack)) {
            ItemStack itemStack2 = itemStack.copy();
            ItemStack itemStack3 = this.inputSlots.getItem(1);
            ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(itemStack2));
            repair_cost += (long) itemStack.getOrDefault(DataComponents.REPAIR_COST, 0) + itemStack3.getOrDefault(DataComponents.REPAIR_COST, 0);
            this.repairItemUsage = 0;
            if (!itemStack3.isEmpty()) {
                boolean bl = itemStack3.has(DataComponents.STORED_ENCHANTMENTS);
                if (itemStack2.isDamageableItem() && itemStack.isValidRepairItem(itemStack3) && this.notAllowedMaterial != null && !itemStack3.is(this.notAllowedMaterial)) {
                    int i1 = 1;
                    if (itemStack3.is(MMETags.NUGGET)) {
                        i1 = 6;
                    }
                    int Damage = Math.min(itemStack2.getDamageValue(), itemStack2.getMaxDamage() / i1);
                    if (Damage <= 0) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
                    int m;
                    for (m = 0; Damage > 0 && m < itemStack3.getCount(); m++) {
                        int n = itemStack2.getDamageValue() - Damage;
                        itemStack2.setDamageValue(n);
                        i++;
                        Damage = Math.min(itemStack2.getDamageValue(), itemStack2.getMaxDamage() / 4);
                    }
                    this.repairItemUsage = m;
                } else {
                    if (!bl && (!itemStack2.is(itemStack3.getItem()) || !itemStack2.isDamageableItem())) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
                    if (itemStack2.isDamageableItem() && !bl) {
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

                    ItemEnchantments itemEnchantmentsComponent = EnchantmentHelper.getEnchantmentsForCrafting(itemStack3);
                    boolean bl2 = false;
                    boolean bl3 = false;
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
                    if (bl3 && !bl2) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
                }
            }
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
            int t = i <= 0 ? 0 : (int) Mth.clamp(repair_cost + i, 0L, 2147483647L);
            this.levelCost.set(t);
            if (i <= 0) {
                itemStack2 = ItemStack.EMPTY;
            }
            if (j == i && j > 0) {
                if (this.levelCost.get() >= 40) {
                    this.levelCost.set(39);
                }

                this.keepSecondSlot = true;
            }
            if (this.levelCost.get() >= 40 && !this.player.hasInfiniteMaterials()) {
                itemStack2 = ItemStack.EMPTY;
            }
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

            this.resultSlots.setItem(0, itemStack2);
            this.broadcastChanges();
        } else {
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
        return (player.hasInfiniteMaterials() || player.experienceLevel >= this.levelCost.get()) && this.levelCost.get() > 0;
    }
}
