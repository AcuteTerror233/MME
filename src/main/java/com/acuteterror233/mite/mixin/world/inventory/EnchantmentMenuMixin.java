package com.acuteterror233.mite.mixin.world.inventory;

import com.acuteterror233.mite.interfaces.EnchantmentMenuExtension;
import com.acuteterror233.mite.item.enchantment.MMEEnchantments;
import com.acuteterror233.mite.registry.EnchantedUpgradeRegistry;
import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.block.EnchantingTableBlock;
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
public abstract class EnchantmentMenuMixin implements EnchantmentMenuExtension {
    @Unique
    private static final int[] mme$MOON_INFLUENCE_INTENSITY_PER_PHASE = new int[]{3, 2, 1, -1, -2, -1, 1, 2};

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

    @Shadow
    public abstract void slotsChanged(Container container);

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
    @Inject(method = "slotsChanged", at = @At("HEAD"), cancellable = true)
    private void mme$slotsChanged(Container container, CallbackInfo ci) {
        if (container != this.enchantSlots) {
            return;
        }
        ci.cancel();
        ItemStack stack = container.getItem(0);
        if (!stack.isEmpty() && (stack.isEnchantable() || EnchantedUpgradeRegistry.getUpgrade(stack) != null)) {
            this.access.execute((level, pos) -> {
                IdMap<Holder<Enchantment>> idMap = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).asHolderIdMap();
                int shelves = 0;

                for (BlockPos offset : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
                    if (EnchantingTableBlock.isValidBookShelf(level, pos, offset)) {
                        shelves++;
                    }
                }
                MoonPhase moonPhase = level.environmentAttributes().getValue(EnvironmentAttributes.MOON_PHASE, pos);
                int power = Math.max(0, Math.min(shelves + mme$MOON_INFLUENCE_INTENSITY_PER_PHASE[moonPhase.index()], this.mme$maxEnchantmentLevel));
                this.random.setSeed(this.enchantmentSeed.get());

                for (int j = 0; j < 3; j++) {
                    this.costs[j] = EnchantmentHelper.getEnchantmentCost(this.random, j, power, stack);
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
                            EnchantmentInstance instance = list.get(this.random.nextInt(list.size()));
                            this.enchantClue[j] = idMap.getId(instance.enchantment());
                            this.levelClue[j] = instance.level();
                        }
                    }
                }

                Item upgrade = EnchantedUpgradeRegistry.getUpgrade(stack);
                if (upgrade != null) {
                    this.costs[0] = 2;
                    this.costs[1] = 0;
                    this.costs[2] = 0;
                    this.enchantClue[0] = idMap.getId(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(MMEEnchantments.UPGRADE).orElseThrow());
                    this.levelClue[0] = 1;
                    this.enchantClue[1] = -1;
                    this.levelClue[1] = -1;
                    this.enchantClue[2] = -1;
                    this.levelClue[2] = -1;
                }

                ((AbstractContainerMenu) (Object) this).broadcastChanges();
            });
        } else {
            for (int i = 0; i < 3; i++) {
                this.costs[i] = 0;
                this.enchantClue[i] = -1;
                this.levelClue[i] = -1;
            }
        }
    }

    /** Upgrade items are enchanted through the transmutation path (vanilla enchantment-list path is skipped). */
    @Inject(method = "clickMenuButton", at = @At("HEAD"), cancellable = true)
    private void mme$performUpgrade(Player player, int button, CallbackInfoReturnable<Boolean> cir) {
        if (button < 0 || button >= this.costs.length) {
            return;
        }
        ItemStack stack = this.enchantSlots.getItem(0);
        Item upgrade = EnchantedUpgradeRegistry.getUpgrade(stack);
        if (upgrade == null) {
            return;
        }
        if (button != 0) {
            cir.setReturnValue(false);
            return;
        }
        ItemStack lapis = this.enchantSlots.getItem(1);
        int cost = button + 1;
        if ((lapis.isEmpty() || lapis.getCount() < cost) && !player.hasInfiniteMaterials()) {
            cir.setReturnValue(false);
        } else if (this.costs[button] <= 0 || stack.isEmpty() || (player.experienceLevel < cost || player.experienceLevel < this.costs[button]) && !player.hasInfiniteMaterials()) {
            cir.setReturnValue(false);
        } else {
            this.access.execute((level, pos) -> {
                player.onEnchantmentPerformed(stack, cost);
                ItemStack result = stack.transmuteCopy(upgrade);
                this.enchantSlots.setItem(0, result);
                lapis.consume(cost, player);
                if (lapis.isEmpty()) {
                    this.enchantSlots.setItem(1, ItemStack.EMPTY);
                }
                player.awardStat(Stats.ENCHANT_ITEM);
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ENCHANTED_ITEM.trigger(serverPlayer, result, cost);
                }
                this.enchantSlots.setChanged();
                this.enchantmentSeed.set(player.getEnchantmentSeed());
                this.slotsChanged(this.enchantSlots);
                level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
            });
            cir.setReturnValue(true);
        }
    }

    /** The menu stays valid on every block tagged {@code mme:enchanting_table} (vanilla + emerald tables). */
    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void mme$stillValid(Player player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.access.evaluate((level, pos) ->
                level.getBlockState(pos).is(MMEBlockTags.ENCHANTING_TABLE) && player.isWithinBlockInteractionRange(pos, 4.0), true));
    }
}
