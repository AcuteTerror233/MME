package com.acuteterror233.mite.mixin.world.inventory;

import com.acuteterror233.mite.block.GradeCraftingTableBlock;
import com.acuteterror233.mite.interfaces.TimedCraftingMenuExtension;
import com.acuteterror233.mite.inventory.TimedCraftingSession;
import com.acuteterror233.mite.inventory.slot.TimedCraftingResultSlot;
import com.acuteterror233.mite.material.MetalMaterial;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
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
 * Mixin for {@code CraftingMenu} — Integrates the metal material timed crafting system,
 * replacing the old custom {@code GradeCraftingTableMenu}.
 * When a metal material is injected (opened by {@link GradeCraftingTableBlock}), crafting becomes a timed synthesis
 * with material grade restrictions; without an injected material the vanilla behavior is fully preserved.
 */
@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin extends AbstractCraftingMenu implements TimedCraftingMenuExtension {
    @Unique
    @Nullable
    private MetalMaterial mme$metal;
    @Unique
    @Nullable
    private TimedCraftingSession mme$session;
    @Final
    @Shadow
    private Player player;
    @Final
    @Shadow
    private ContainerLevelAccess access;

    public CraftingMenuMixin(MenuType<?> type, int syncId, int width, int height) {
        super(type, syncId, width, height);
    }

    /**
     * Lazily creates the timed crafting session bound to this menu. Built on first touch (menu
     * constructor) so {@code mme$metal} can be applied before any crafting logic runs; reused
     * on both sides — the session itself is client-agnostic (state is synced via ContainerData).
     */
    @Unique
    private TimedCraftingSession mme$session() {
        if (this.mme$session == null) {
            this.mme$session = new TimedCraftingSession(this, this.player, this.craftSlots, this.resultSlots);
        }
        return this.mme$session;
    }

    /** Registers the session's data slots ({@code data()}) right after menu construction so the client receives state[] updates. */
    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At("RETURN"))
    private void mme$init(int syncId, Inventory playerInventory, ContainerLevelAccess access, CallbackInfo ci) {
        this.addDataSlots(this.mme$session().data());
    }

    /** Replaces the vanilla immediate-craft result slot with the timed crafting result slot. */
    @Override
    protected @NotNull Slot addResultSlot(Player player, int x, int y) {
        return this.addSlot(new TimedCraftingResultSlot(player, this.craftSlots, this.resultSlots, this::mme$session, x, y));
    }

    /** Duck interface: injects the metal material after the menu is opened server-side (never called on the client). */
    @Override
    public void MME$SetMetalMaterial(@Nullable MetalMaterial metal) {
        this.mme$metal = metal;
        this.mme$session().setMetal(metal);
    }

    /** Duck interface: returns the metal injected at open time, or {@code null} for a vanilla crafting table. */
    @Override
    public @Nullable MetalMaterial MME$GetMetalMaterial() {
        return this.mme$metal;
    }

    /** Duck interface: whether the current inputs pass the material restrictions (allowed to start/continue crafting). */
    @Override
    public boolean MME$IsAllowCrafting() {
        return this.mme$session().isAllowCrafting();
    }

    /** Duck interface: current crafting progress in ticks (used by the HUD/screen overlay). */
    @Override
    public double MME$GetCraftingTime() {
        return this.mme$session().getCraftingTime();
    }

    /** Duck interface: whether a metal material is active (always backed by the session so the client can see it). */
    @Override
    public boolean MME$HasMetal() {
        return this.mme$session().hasMetal();
    }

    /**
     * Vanilla {@code slotsChanged} resolves the recipe immediately; here it only triggers a
     * server-side result re-evaluation. The {@code isFilling} guard skips re-evaluation while the
     * client is still drag-placing ingredients (batch fill would otherwise restart the recipe
     * match on every single placement), which is also what resets/clears stale progress.
     */
    @Inject(method = "slotsChanged", at = @At("HEAD"), cancellable = true)
    private void mme$slotsChanged(Container inventory, CallbackInfo ci) {
        TimedCraftingSession session = this.mme$session;
        if (session == null || !session.hasMetal()) {
            return;
        }
        if (!session.isFilling() && this.player.level() instanceof ServerLevel serverLevel) {
            session.updateResult(serverLevel, null);
        }
        ci.cancel();
    }

    /** Redirects the vanilla instant recipe-fill handshake into the timed session (placeholders are placed without resolving a result). */
    @Inject(method = "beginPlacingRecipe", at = @At("HEAD"), cancellable = true)
    private void mme$beginPlacingRecipe(CallbackInfo ci) {
        TimedCraftingSession session = this.mme$session;
        if (session != null && session.hasMetal()) {
            session.beginPlacing();
            ci.cancel();
        }
    }

    /** Completes the recipe-fill handshake inside the session (vanilla would craft instantly). */
    @Inject(method = "finishPlacingRecipe", at = @At("HEAD"), cancellable = true)
    private void mme$finishPlacingRecipe(ServerLevel level, RecipeHolder<CraftingRecipe> recipe, CallbackInfo ci) {
        TimedCraftingSession session = this.mme$session;
        if (session != null && session.hasMetal()) {
            session.finishPlacing(level, recipe);
            ci.cancel();
        }
    }

    /**
     * CraftingMenu does not declare broadcastChanges (inherited from AbstractContainerMenu), so @Inject
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
     * Validates the menu against the injected metal's block: the opened block must still be a grade
     * crafting table of the same metal, the player must stay in range (4 blocks, not vanilla's 8),
     * and the block above must remain air (grading tables need headroom). {@code true} is the
     * evaluation fallback when the position is unloaded, matching vanilla tolerance.
     */
    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void mme$stillValid(Player player, CallbackInfoReturnable<Boolean> cir) {
        MetalMaterial metal = this.mme$metal;
        if (metal == null) {
            return;
        }
        cir.setReturnValue(this.access.evaluate((world, pos) ->
                world.getBlockState(pos).getBlock() instanceof GradeCraftingTableBlock table
                        && table.metal() == metal
                        && player.isWithinBlockInteractionRange(pos, 4.0)
                        && world.getBlockState(pos.above()).isAir(), true));
        cir.cancel();
    }

    /**
     * Guarded by {@code hasMetal()} instead of the {@code mme$metal} field: the client-side menu is built
     * over the network and never receives {@code MME$SetMetalMaterial}, so the synced ACTIVE data slot is
     * the only way for the client to intercept too — otherwise the client predicts the vanilla
     * quick-move and the server immediately corrects it (visible item flicker in the player inventory).
     */
    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    private void mme$quickMoveStack(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        TimedCraftingSession session = this.mme$session();
        if (this.getSlot(index) instanceof TimedCraftingResultSlot && session.hasMetal()) {
            // Shift-click on the result slot never moves the item; it only re-evaluates the running
            // craft (progress/finish) so the timed session stays the sole path to the output.
            session.evaluateRunning();
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

    /** Shift-click "pick all" is disallowed on the result slot while timed crafting is active (output must be claimed by a normal click). */
    @Inject(method = "canTakeItemForPickAll", at = @At("HEAD"), cancellable = true)
    private void mme$canTakeItemForPickAll(ItemStack stack, Slot slot, CallbackInfoReturnable<Boolean> cir) {
        if (slot instanceof TimedCraftingResultSlot && this.mme$session().hasMetal()) {
            cir.setReturnValue(false);
        }
    }
}
