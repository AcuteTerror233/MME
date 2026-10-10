package com.acuteterror233.mite.mixin.world.inventory;

import com.acuteterror233.mite.interfaces.TimedCraftingMenuExtension;
import com.acuteterror233.mite.inventory.TimedCraftingSession;
import com.acuteterror233.mite.inventory.slot.TimedCraftingResultSlot;
import com.acuteterror233.mite.material.MMEMaterials;
import com.acuteterror233.mite.material.MetalMaterial;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code InventoryMenu} — Hooks the 2×2 inventory crafting into the metal timed crafting
 * system (bare-hand = {@link MMEMaterials#HAND}), reusing {@link TimedCraftingSession} to share recipe
 * resolution, material restrictions, and timing logic with the grade crafting tables, replacing the
 * legacy built-in updateResult/manual-packet implementation.
 */
@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends AbstractCraftingMenu implements TimedCraftingMenuExtension {
    @Unique
    @Nullable
    private MetalMaterial mme$metal;
    @Unique
    @Nullable
    private TimedCraftingSession mme$session;
    @Final
    @Shadow
    private Player owner;

    @Shadow
    public abstract @NotNull Slot getResultSlot();

    public InventoryMenuMixin(MenuType<?> type, int syncId, int width, int height) {
        super(type, syncId, width, height);
    }

    /**
     * Lazily creates the timed crafting session bound to this menu; see
     * {@code CraftingMenuMixin#mme$session} for the lifecycle rationale.
     */
    @Unique
    private TimedCraftingSession mme$session() {
        if (this.mme$session == null) {
            this.mme$session = new TimedCraftingSession(this, this.owner, this.craftSlots, this.resultSlots);
        }
        return this.mme$session;
    }

    /**
     * The 2×2 inventory grid is always bare-hand crafting ({@link MMEMaterials#HAND}), so the metal
     * is injected unconditionally on both sides. Data slots are registered here so the client's
     * own InventoryMenu instance receives the session state[] sync without manual packets.
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void mme$init(Inventory inventory, boolean onServer, Player owner, CallbackInfo ci) {
        this.mme$metal = MMEMaterials.HAND;
        this.mme$session().setMetal(MMEMaterials.HAND);
        this.addDataSlots(this.mme$session().data());
    }

    /** Replaces the vanilla immediate-craft result slot with the timed crafting result slot. */
    @Override
    protected @NotNull Slot addResultSlot(Player player, int x, int y) {
        return this.addSlot(new TimedCraftingResultSlot(player, this.craftSlots, this.resultSlots, this::mme$session, x, y));
    }

    /** Duck interface: metal is fixed to HAND here; setter exists only to satisfy the shared interface. */
    @Override
    public void MME$SetMetalMaterial(@Nullable MetalMaterial metal) {
        this.mme$metal = metal;
        this.mme$session().setMetal(metal);
    }

    /** Duck interface: always returns {@link MMEMaterials#HAND} for the inventory grid. */
    @Override
    public @Nullable MetalMaterial MME$GetMetalMaterial() {
        return this.mme$metal;
    }

    /** Duck interface: whether the current inputs pass the bare-hand material restrictions. */
    @Override
    public boolean MME$IsAllowCrafting() {
        return this.mme$session().isAllowCrafting();
    }

    /** Duck interface: current crafting progress in ticks (used by the HUD/screen overlay). */
    @Override
    public double MME$GetCraftingTime() {
        return this.mme$session().getCraftingTime();
    }

    /** Duck interface: always {@code true} — the inventory menu always has the HAND metal active. */
    @Override
    public boolean MME$HasMetal() {
        return this.mme$session().hasMetal();
    }

    /**
     * Same {@code isFilling} guard as {@code CraftingMenuMixin#mme$slotsChanged}, additionally
     * scoped to {@code craftSlots} (InventoryMenu fires slotsChanged for every container change,
     * including the player inventory itself, which must not re-evaluate the recipe).
     */
    @Inject(method = "slotsChanged", at = @At("HEAD"), cancellable = true)
    private void mme$slotsChanged(Container inventory, CallbackInfo ci) {
        TimedCraftingSession session = this.mme$session;
        if (session == null || !session.hasMetal()) {
            return;
        }
        if (inventory == this.craftSlots && !session.isFilling() && this.owner.level() instanceof ServerLevel serverLevel) {
            session.updateResult(serverLevel, null);
        }
        ci.cancel();
    }

    /**
     * InventoryMenu does not declare broadcastChanges (inherited from AbstractContainerMenu), so @Inject
     * is not applicable; instead a mixin-added override (method merge) advances the timed crafting
     * before the vanilla logic runs.
     */
    @Override
    public void broadcastChanges() {
        TimedCraftingSession session = this.mme$session;
        if (session != null && session.hasMetal()) {
            session.tick();
        }
        super.broadcastChanges();
    }

    /**
     * Unlike {@code CraftingMenuMixin} no {@code hasMetal()} check is needed — the HAND metal is
     * active for every inventory menu (both sides), so shift-clicking the result slot can always
     * be redirected into the session's re-evaluation instead of vanilla quick-move.
     */
    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    protected void mme$quickMoveStack(Player player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
        if (this.getSlot(slotIndex) instanceof TimedCraftingResultSlot) {
            this.mme$session().evaluateRunning();
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

    /** Resets the timed session when the menu closes so leftover progress/state cannot leak into the next open. */
    @Inject(method = "removed", at = @At("HEAD"))
    public void mme$onClosed(Player player, CallbackInfo ci) {
        if (this.mme$session != null) {
            this.mme$session.reset();
        }
    }

    /**
     * Numeric hotbar-key swaps (and the offhand key) on the result slot bypass {@code remove}
     * entirely: vanilla's {@code doClick} SWAP branch moves the item into the hotbar and calls
     * {@code onTake} directly, which would claim the output and consume the ingredients while
     * skipping the timed craft. Swallowing that click keeps every take path inside the session;
     * like shift-click it only re-evaluates the running craft.
     */
    @Override
    public void clicked(int slotIndex, int button, ContainerInput input, Player player) {
        if (input == ContainerInput.SWAP && slotIndex >= 0
                && this.getSlot(slotIndex) instanceof TimedCraftingResultSlot) {
            this.mme$session().evaluateRunning();
            return;
        }
        super.clicked(slotIndex, button, input, player);
    }
}
