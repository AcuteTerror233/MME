package com.acuteterror233.mite.mixin.world.inventory;

import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.interfaces.MetalMenuExtension;
import com.acuteterror233.mite.material.MetalMaterial;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Mixin for {@code AnvilMenu} — Integrates the metal material anvil system,
 * replacing the old custom {@code GradeAnvilMenu}.
 * When a metal material is injected (opened by {@code MMEAnvilBlock}), repair materials are restricted
 * and iron-sand (nugget) repairs use a lower efficiency divisor; damage is accumulated on the
 * {@link AnvilBlockEntity} instead of the vanilla block-state degradation.
 * Without an injected material the vanilla behavior is fully preserved.
 */
@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu implements MetalMenuExtension {
    @Unique
    @Nullable
    private MetalMaterial mme$metal;

    @Final
    @Shadow
    private DataSlot cost;
    @Shadow
    private int repairItemCountCost;
    @Shadow
    private boolean onlyRenaming;

    /**
     * Compile-time phantom constructor: its signature matches neither of AnvilMenu's constructors and
     * is discarded at merge time; it exists only to satisfy the mixed-in class's compile-time
     * requirement to chain to the ItemCombinerMenu super constructor.
     */
    public AnvilMenuMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition definition) {
        super(type, syncId, playerInventory, access, definition);
    }

    /** Duck interface: injects the metal material after the menu is opened server-side (never called on the client). */
    @Override
    public void MME$SetMetalMaterial(@Nullable MetalMaterial metal) {
        this.mme$metal = metal;
    }

    /** Duck interface: returns the metal injected at open time, or {@code null} for a vanilla anvil. */
    @Override
    public @Nullable MetalMaterial MME$GetMetalMaterial() {
        return this.mme$metal;
    }

    /** Repair material restriction: with a metal injected, the item is considered unrepairable when the second slot's material belongs to the metal's disallowed repair tag. */
    @ModifyExpressionValue(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isValidRepairItem(Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean mme$restrictRepairMaterial(boolean original) {
        MetalMaterial.AnvilFunction anvil = this.mme$metal != null ? this.mme$metal.anvil() : null;
        if (anvil == null) {
            return original;
        }
        return original && !this.inputSlots.getItem(1).is(anvil.notAllowedRepairMaterials());
    }

    /** Nugget repair efficiency: both maxDamage/4 divisors become /6 when repairing with anvil nuggets. */
    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 4))
    private int mme$modifyRepairDivisor(int original) {
        MetalMaterial.AnvilFunction anvil = this.mme$metal != null ? this.mme$metal.anvil() : null;
        if (anvil == null || !this.inputSlots.getItem(1).is(MMEItemTags.NUGGET)) {
            return original;
        }
        return 6;
    }

    /**
     * @author AcuteTerror233
     * @reason Replace the vanilla block-state degradation with damage accumulation and the degradation
     * chain on AnvilBlockEntity (in this modpack the vanilla anvil blocks are replaced by MMEAnvilBlock,
     * which carries a block entity), while keeping the vanilla level deduction, material consumption,
     * and slot clearing semantics.
     */
    @Overwrite
    protected void onTake(Player player, ItemStack stack) {
        if (!player.hasInfiniteMaterials()) {
            player.giveExperienceLevels(-this.cost.get());
        }
        int damage;
        if (this.repairItemCountCost > 0) {
            ItemStack material = this.inputSlots.getItem(1);
            if (!material.isEmpty() && material.getCount() > this.repairItemCountCost) {
                material.shrink(this.repairItemCountCost);
                this.inputSlots.setItem(1, material);
            } else {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
            damage = this.inputSlots.getItem(0).getDamageValue() - this.resultSlots.getItem(0).getDamageValue();
        } else {
            damage = 0;
            if (!this.onlyRenaming) {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
        }
        this.access.execute((world, pos) -> {
            if (world.getBlockEntity(pos) instanceof AnvilBlockEntity blockEntity) {
                int nextDamage = blockEntity.getDamage() + damage;
                blockEntity.addDamage(damage);
                if (nextDamage >= blockEntity.getMaxDamage()) {
                    if (this.player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.closeContainer();
                    }
                }
                world.levelEvent(1030, pos, 0);
            }
        });
        this.cost.set(0);
        this.inputSlots.setItem(0, ItemStack.EMPTY);
    }

    /**
     * AnvilMenu does not declare stillValid (inherited from ItemCombinerMenu), so @Inject is not
     * applicable; instead a mixin-added override: with a metal injected only the interaction range and
     * the air block above are validated (keeping the current gameplay constraint), otherwise the
     * vanilla check applies.
     */
    @Override
    public boolean stillValid(Player player) {
        if (this.mme$metal == null) {
            return super.stillValid(player);
        }
        return this.access.evaluate((world, pos) ->
                player.isWithinBlockInteractionRange(pos, 4.0)
                        && world.getBlockState(pos.above()).isAir(), true);
    }
}
