package com.acuteterror233.mite.mixin.world.inventory;

import com.acuteterror233.mite.interfaces.EnchantmentMenuExtension;
import com.acuteterror233.mite.registry.EnchantedUpgradeRegistry;
import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Mixin for {@code EnchantmentMenu} — Integrates the MME enchantment mechanics into the vanilla
 * menu, replacing the old custom {@code MMEEnchantmentMenu}:
 * <ul>
 * <li>{@code slotsChanged} is reimplemented: enchanted-upgrade items (e.g. golden apple) are
 * accepted and offered through the {@link EnchantedUpgradeRegistry} path, and the enchantment
 * power gains a moon phase influence capped by the per-instance maximum from the
 * {@link EnchantmentMenuExtension} duck interface;</li>
 * <li>upgrade items are enchanted through a transmutation branch of {@code clickMenuButton};</li>
 * <li>the menu stays valid on every block tagged {@code mme:enchanting_table}.</li>
 * </ul>
 */
@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin extends AbstractContainerMenu implements EnchantmentMenuExtension {

    /**
     * Per-instance cap for the enchantment power (bookshelf count + moon influence).
     * Defaults to 15; the vanilla table raises it to 20 and the emerald table lowers it to 10
     * through the duck interface.
     */
    @Unique
    private int mme$maxEnchantmentLevel = 15;

    @Final
    @Shadow
    private Container enchantSlots;
    @Final
    @Shadow
    private ContainerLevelAccess access;
    @Final
    @Shadow
    private RandomSource random;
    @Final
    @Shadow
    private DataSlot enchantmentSeed;
    @Final
    @Shadow
    public int[] costs;
    @Final
    @Shadow
    public int[] enchantClue;
    @Final
    @Shadow
    public int[] levelClue;

    protected EnchantmentMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }
    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At("TAIL"))
    public void init(int containerId, Inventory inventory, ContainerLevelAccess access, CallbackInfo ci){
        this.addDataSlot(DataSlot.shared(EnchantmentMenuExtension.xpCosts, 0));
        this.addDataSlot(DataSlot.shared(EnchantmentMenuExtension.xpCosts, 1));
        this.addDataSlot(DataSlot.shared(EnchantmentMenuExtension.xpCosts, 2));
    }

    @Shadow
    protected abstract List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int button, int cost);

    /** Duck interface: raises/lowers the per-instance power cap (vanilla table 20, emerald table 10, default 15). */
    @Override
    public void MME$SetMaxEnchantmentLevel(int maxLevel) {
        this.mme$maxEnchantmentLevel = maxLevel;
    }

    /** Duck interface: returns the current per-instance power cap. */
    @Override
    public int MME$GetMaxEnchantmentLevel() {
        return this.mme$maxEnchantmentLevel;
    }

    /**
     * Reimplements the vanilla offer recalculation with two MME additions:
     * upgrade items are accepted even though they carry no ENCHANTABLE component, and the raw
     * bookshelf power is adjusted by the current moon phase and clamped to
     * [0, maxEnchantmentLevel] (the floor at zero guards against negative powers crashing
     * RandomSource.nextInt during waning/new moons). The vanilla logic itself is replicated
     * verbatim from the 26.3 bytecode.
     */
    @Override
    public void slotsChanged(@NonNull Container container) {
        if (container != this.enchantSlots) {
            return;
        }
        ItemStack stack = container.getItem(0);
        if (!stack.isEmpty() && (stack.isEnchantable() || EnchantedUpgradeRegistry.getUpgrade(stack) != null)) {
            this.access.execute((level, pos) -> {
                Item upgrade = EnchantedUpgradeRegistry.getUpgrade(stack);
                if (upgrade != null) {
                    for (int i = 0; i < 3; i++) {
                        this.costs[i] = 551775262;
                        EnchantmentMenuExtension.xpCosts[i] = 200;
                        this.enchantClue[i] = -1;
                        this.levelClue[i] = 1;
                    }
                    this.broadcastChanges();
                    return;
                }
                int bookcases = 0;

                for (BlockPos offset : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
                    if (EnchantingTableBlock.isValidBookShelf(level, pos, offset)) {
                        bookcases++;
                    }
                }
                this.random.setSeed(this.enchantmentSeed.get());

                for (int j = 0; j < 3; j++) {
                    this.costs[j] = EnchantmentHelper.getEnchantmentCost(this.random, j, Math.min(bookcases, mme$maxEnchantmentLevel), stack);
                    EnchantmentMenuExtension.xpCosts[j] = (int) ((bookcases * stack.get(DataComponents.ENCHANTABLE).value() * this.costs[j]) * 0.6);
                    this.enchantClue[j] = -1;
                    this.levelClue[j] = -1;
                    if (this.costs[j] < j + 1) {
                        this.costs[j] = 0;
                    }
                }

                for (int j = 0; j < 3; j++) {
                    if (this.costs[j] > 0) {
                        List<EnchantmentInstance> list = this.getEnchantmentList(level.registryAccess(), stack, j, this.costs[j]);
                        if (!list.isEmpty()) {
                            this.enchantClue[j] = -1;
                            this.levelClue[j] = -1;
                        }
                    }
                }

                this.broadcastChanges();
            });
        } else {
            for (int i = 0; i < 3; i++) {
                this.costs[i] = 0;
                EnchantmentMenuExtension.xpCosts[i] = 0;
                this.enchantClue[i] = -1;
                this.levelClue[i] = -1;
            }
        }
    }

    /** Upgrade items are enchanted through the transmutation path (vanilla enchantment-list path is skipped). */
    @Override
    public boolean clickMenuButton(@NonNull Player player, int buttonId) {
        if (buttonId >= 0 && buttonId < this.costs.length) {
            ItemStack itemStack = this.enchantSlots.getItem(0);
            ItemStack currency = this.enchantSlots.getItem(1);
            int enchantmentCost = buttonId + 1;
            Item upgrade = EnchantedUpgradeRegistry.getUpgrade(itemStack);
            if ((currency.isEmpty() || currency.getCount() < enchantmentCost) && !player.hasInfiniteMaterials() && upgrade == null) {
                return false;
            }

            if (this.costs[buttonId] <= 0 || itemStack.isEmpty() || player.totalExperience < EnchantmentMenuExtension.xpCosts[buttonId] && !player.hasInfiniteMaterials()) {
                return false;
            }

            this.access.execute((level, pos) -> {
                if (upgrade != null) {
                    player.onEnchantmentPerformed(upgrade.getDefaultInstance(), EnchantmentMenuExtension.xpCosts[buttonId]);
                    ItemStack stack = upgrade.getDefaultInstance();
                    this.enchantSlots.setItem(0, stack);
                    player.awardStat(Stats.ENCHANT_ITEM);
                    this.enchantSlots.setChanged();
                    if (player instanceof ServerPlayer serverPlayer) {
                        CriteriaTriggers.ENCHANTED_ITEM.trigger(serverPlayer, stack, enchantmentCost);
                    }
                    this.slotsChanged(this.enchantSlots);
                    level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                    return;
                }
                ItemStack enchantmentItem = itemStack;
                List<EnchantmentInstance> newEnchantment = this.getEnchantmentList(level.registryAccess(), enchantmentItem, buttonId, this.costs[buttonId]);
                if (!newEnchantment.isEmpty()) {
                    player.onEnchantmentPerformed(enchantmentItem, this.xpCosts[buttonId]);
                    if (enchantmentItem.is(Items.BOOK)) {
                        enchantmentItem = itemStack.transmuteCopy(Items.ENCHANTED_BOOK);
                        this.enchantSlots.setItem(0, enchantmentItem);
                    }

                    for (EnchantmentInstance enchantment : newEnchantment) {
                        enchantmentItem.enchant(enchantment.enchantment(), enchantment.level());
                    }

                    currency.consume(enchantmentCost, player);
                    if (currency.isEmpty()) {
                        this.enchantSlots.setItem(1, ItemStack.EMPTY);
                    }

                    player.awardStat(Stats.ENCHANT_ITEM);
                    if (player instanceof ServerPlayer serverPlayer) {
                        CriteriaTriggers.ENCHANTED_ITEM.trigger(serverPlayer, enchantmentItem, enchantmentCost);
                    }

                    this.enchantSlots.setChanged();
                    this.enchantmentSeed.set(player.getEnchantmentSeed());
                    this.slotsChanged(this.enchantSlots);
                    level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                }
            });
            return true;
        } else {
            Util.logAndPauseIfInIde(player.getPlainTextName() + " pressed invalid button id: " + buttonId);
            return false;
        }
    }

    /** The menu stays valid on every block tagged {@code mme:enchanting_table} (vanilla + emerald tables). */
    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void mme$stillValid(Player player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.access.evaluate((level, pos) ->
                level.getBlockState(pos).is(MMEBlockTags.ENCHANTING_TABLE) && player.isWithinBlockInteractionRange(pos, 4.0), true));
    }
}
