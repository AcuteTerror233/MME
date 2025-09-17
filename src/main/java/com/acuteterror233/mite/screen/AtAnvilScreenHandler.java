package com.acuteterror233.mite.screen;

import com.acuteterror233.mite.block.AnvilBlockEntity;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.registry.tag.AtTags;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AtAnvilScreenHandler extends ForgingScreenHandler {
    private final Property levelCost = Property.create();
    private boolean keepSecondSlot = false;
    //维修扣除计数
    private int repairItemUsage;
    @Nullable
    private String newItemName;
    public AtAnvilScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory,ScreenHandlerContext.EMPTY);
    }
    public AtAnvilScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(AtBlocks.ATANVILSCREENHANDLER, syncId, playerInventory, context, getForgingSlotsManager());
        this.addProperty(this.levelCost);
    }
    public static ForgingSlotsManager getForgingSlotsManager() {
        return ForgingSlotsManager.builder().input(0, 27, 47, stack -> true).input(1, 76, 47, stack -> true).output(2, 134, 47).build();
    }

    //输出被拿走的时候执行,player拿走的玩家,stack拿走的堆栈
    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        //判断是否在创造
        if (!player.isInCreativeMode()) {
            //不在创造就扣相应等级
            player.addExperienceLevels(-this.levelCost.get());
        }
        int i;
        //判断是否有维修项目扣除
        if (this.repairItemUsage > 0) {
            //获取第二个堆栈
            ItemStack itemStack = this.input.getStack(1);
            //第二个堆栈不为空且堆栈计数大于维修扣除计数
            if (!itemStack.isEmpty() && itemStack.getCount() > this.repairItemUsage) {
                //堆栈减去维修扣除计数
                itemStack.decrement(this.repairItemUsage);
                //设置回第二个堆栈
                this.input.setStack(1, itemStack);
            } else {
                //否则就清空第二个堆栈
                this.input.setStack(1, ItemStack.EMPTY);
            }
            i = this.input.getStack(0).getDamage() - this.output.getStack(0).getDamage();
            //如果不是维修项目,且没设置保留状态
        } else {
            i = 0;
            if (!this.keepSecondSlot) {
                //清空第二个堆栈
                this.input.setStack(1, ItemStack.EMPTY);
            }
        }

        //触发世界事件
        this.context.run((world, pos) -> {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof AnvilBlockEntity blockEntity){
                blockEntity.addDamage(i);
            }
        });
        this.levelCost.set(0);
        this.input.setStack(0, ItemStack.EMPTY);
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isIn(BlockTags.ANVIL);
    }

    @Override
    public void updateResult() {
        //获取第一个堆栈
        ItemStack itemStack = this.input.getStack(0);
        //默认不保留第二个堆栈
        this.keepSecondSlot = false;
        //默认等级消耗为1
        this.levelCost.set(1);
        int i = 0;
        long repair_cost = 0L;
        int j = 0;
        //检查是否为空,是否可以有附魔
        if (!itemStack.isEmpty() && EnchantmentHelper.canHaveEnchantments(itemStack)) {
            //创建第一个堆栈副本
            ItemStack itemStack2 = itemStack.copy();
            //获取第二个堆栈
            ItemStack itemStack3 = this.input.getStack(1);
            //获取堆栈的附魔组件builder
            ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(EnchantmentHelper.getEnchantments(itemStack2));
            //获取堆栈的REPAIR_COST累加和
            repair_cost += (long) itemStack.getOrDefault(DataComponentTypes.REPAIR_COST, 0) + itemStack3.getOrDefault(DataComponentTypes.REPAIR_COST, 0);
            //默认维修数量为0
            this.repairItemUsage = 0;
            //判断堆栈是否不为空
            if (!itemStack3.isEmpty()) {
                //获取堆栈的STORED_ENCHANTMENTS
                boolean bl = itemStack3.contains(DataComponentTypes.STORED_ENCHANTMENTS);
                Optional<BlockState> state = this.context.get((World::getBlockState));
                TagKey<Item> key = AtTags.ADAMANTIUM_NONREPAIRABLE;
                if (state.isPresent()) {
                    BlockState blockState = state.get();
                    key = GetNonrepairableTag(blockState);
                }
                // 判断堆栈是否可损坏,堆栈一可以被堆栈二修复
                if (itemStack2.isDamageable() && itemStack.canRepairWith(itemStack3) && !itemStack.isIn(key)) {
                    int i1 = 1;
                    if (itemStack3.isIn(AtTags.NUGGET)) {
                        i1 = 4;
                    }
                    //维修耐久
                    int Damage = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / i1);
                    if (Damage <= 0) {
                        this.output.setStack(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
                    int m;
                    //计算需要维修需要计数
                    for (m = 0; Damage > 0 && m < itemStack3.getCount(); m++) {
                        int n = itemStack2.getDamage() - Damage;
                        itemStack2.setDamage(n);
                        i++;
                        Damage = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                    }
    
                    this.repairItemUsage = m;
                } else {
                    // 检查是否可以合并两个相同类型的物品（如附魔书）
                    if (!bl && (!itemStack2.isOf(itemStack3.getItem()) || !itemStack2.isDamageable())) {
                        this.output.setStack(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
    
                    // 合并两个损坏物品以恢复耐久度
                    if (itemStack2.isDamageable() && !bl) {
                        int kx = itemStack.getMaxDamage() - itemStack.getDamage();
                        int m = itemStack3.getMaxDamage() - itemStack3.getDamage();
                        int n = m + itemStack2.getMaxDamage() * 12 / 100;
                        int o = kx + n;
                        int p = itemStack2.getMaxDamage() - o;
                        if (p < 0) {
                            p = 0;
                        }
    
                        if (p < itemStack2.getDamage()) {
                            itemStack2.setDamage(p);
                            i += 2;
                        }
                    }
    
                    ItemEnchantmentsComponent itemEnchantmentsComponent = EnchantmentHelper.getEnchantments(itemStack3);
                    boolean bl2 = false;
                    boolean bl3 = false;
    
                    // 遍历第二个物品上的附魔，并尝试将其应用到第一个物品上
                    for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
                        RegistryEntry<Enchantment> registryEntry = entry.getKey();
                        int q = builder.getLevel(registryEntry);
                        int r = entry.getIntValue();
                        r = q == r ? r + 1 : Math.max(r, q);
                        Enchantment enchantment = registryEntry.value();
                        boolean bl4 = enchantment.isAcceptableItem(itemStack);
                        if (this.player.isInCreativeMode() || itemStack.isOf(Items.ENCHANTED_BOOK)) {
                            bl4 = true;
                        }
    
                        for (RegistryEntry<Enchantment> registryEntry2 : builder.getEnchantments()) {
                            if (!registryEntry2.equals(registryEntry) && !Enchantment.canBeCombined(registryEntry, registryEntry2)) {
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
    
                    // 如果存在冲突且没有成功附魔，则清空输出
                    if (bl3 && !bl2) {
                        this.output.setStack(0, ItemStack.EMPTY);
                        this.levelCost.set(0);
                        return;
                    }
                }
            }
    
            // 处理自定义名称的设置或移除
            if (this.newItemName != null && !StringHelper.isBlank(this.newItemName)) {
                if (!this.newItemName.equals(itemStack.getName().getString())) {
                    j = 1;
                    i += j;
                    itemStack2.set(DataComponentTypes.CUSTOM_NAME, Text.literal(this.newItemName));
                }
            } else if (itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
                j = 1;
                i += j;
                itemStack2.remove(DataComponentTypes.CUSTOM_NAME);
            }
    
            // 计算总等级消耗
            int t = i <= 0 ? 0 : (int) MathHelper.clamp(repair_cost + i, 0L, 2147483647L);
            this.levelCost.set(t);
            if (i <= 0) {
                itemStack2 = ItemStack.EMPTY;
            }
    
            // 设置是否保留副槽位
            if (j == i && j > 0) {
                if (this.levelCost.get() >= 40) {
                    this.levelCost.set(39);
                }
    
                this.keepSecondSlot = true;
            }
    
            // 如果等级消耗过高且玩家不是创造模式，则清空输出
            if (this.levelCost.get() >= 40 && !this.player.isInCreativeMode()) {
                itemStack2 = ItemStack.EMPTY;
            }
    
            // 更新修复成本和附魔信息
            if (!itemStack2.isEmpty()) {
                int kxx = itemStack2.getOrDefault(DataComponentTypes.REPAIR_COST, 0);
                if (kxx < itemStack3.getOrDefault(DataComponentTypes.REPAIR_COST, 0)) {
                    kxx = itemStack3.getOrDefault(DataComponentTypes.REPAIR_COST, 0);
                }
    
                if (j != i || j == 0) {
                    kxx = AnvilScreenHandler.getNextCost(kxx);
                }
    
                itemStack2.set(DataComponentTypes.REPAIR_COST, kxx);
                EnchantmentHelper.set(itemStack2, builder.build());
            }
    
            this.output.setStack(0, itemStack2);
            this.sendContentUpdates();
        } else {
            this.output.setStack(0, ItemStack.EMPTY);
            this.levelCost.set(0);
        }
    }

    private TagKey<Item> GetNonrepairableTag(BlockState state) {
        if (state.isIn(AtTags.ADAMANTIUM_ANVIL)) return AtTags.ADAMANTIUM_NONREPAIRABLE;
        else if (state.isIn(AtTags.MITHRIL_ANVIL)) return AtTags.MITHRIL_NONREPAIRABLE;
        else if (state.isIn(AtTags.ANCIENT_METAL_ANVIL)) return AtTags.ANCIENT_METAL_NONREPAIRABLE;
        else if (state.isIn(AtTags.GOLDEN_ANVIL)) return AtTags.GOLDEN_NONREPAIRABLE;
        else if (state.isIn(AtTags.COPPER_ANVIL)) return AtTags.COPPER_NONREPAIRABLE;
        else if (state.isIn(AtTags.SILVER_ANVIL)) return AtTags.SILVER_NONREPAIRABLE;
        else if (state.isIn(AtTags.IRON_ANVIL)) return AtTags.IRON_NONREPAIRABLE;
        return AtTags.ADAMANTIUM_NONREPAIRABLE;
    }

    public boolean setNewItemName(String newItemName) {
        String string = sanitize(newItemName);
        if (string != null && !string.equals(this.newItemName)) {
            this.newItemName = string;
            if (this.getSlot(2).hasStack()) {
                ItemStack itemStack = this.getSlot(2).getStack();
                if (StringHelper.isBlank(string)) {
                    itemStack.remove(DataComponentTypes.CUSTOM_NAME);
                } else {
                    itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(string));
                }
            }

            this.updateResult();
            return true;
        } else {
            return false;
        }
    }
    @Nullable
    private static String sanitize(String name) {
        String string = StringHelper.stripInvalidChars(name);
        return string.length() <= 50 ? string : null;
    }
    public int getLevelCost() {
        return this.levelCost.get();
    }
    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return (player.isInCreativeMode() || player.experienceLevel >= this.levelCost.get()) && this.levelCost.get() > 0;
    }
}
