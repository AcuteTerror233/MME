package com.acuteterror233.mite.inventory;

import com.acuteterror233.mite.component.MMEDataComponents;
import com.acuteterror233.mite.inventory.slot.TimedCraftingResultSlot;
import com.acuteterror233.mite.material.MetalMaterial;
import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import com.acuteterror233.mite.world.effect.curse.MMECurses;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Timed crafting session: the single source of truth for the metal material timed crafting state.
 * Shared by {@code CraftingMenuMixin} and {@code InventoryMenuMixin}, unifying recipe resolution,
 * material grade restrictions, crafting time calculation, progress advancement, and result writing.
 * State is synchronized via 4 data slots: {progress, total duration, whether allowed, whether metal is mounted}.
 */
public final class TimedCraftingSession {
    /** Base crafting duration in ticks (100 = 5 s); the synced total duration is this plus the ingredient-derived extra time. */
    public static final int DEFAULT_CRAFTING_TIME_TICKS = 20;

    /** Data-slot indices into {@link #state}: progress, total duration, allow bit, metal-mounted bit. */
    private static final int SLOT_PROGRESS = 0;
    private static final int SLOT_TOTAL = 1;
    private static final int SLOT_ALLOW = 2;
    private static final int SLOT_ACTIVE = 3;

    private final AbstractCraftingMenu menu;
    private final Player owner;
    private final CraftingContainer craftSlots;
    private final ResultContainer resultSlots;
    @Nullable
    private MetalMaterial metal;
    /** State array: {progress tick, total duration tick, whether allowed, whether metal is mounted} */
    private final int[] state = {0, DEFAULT_CRAFTING_TIME_TICKS, 0, 0};
    private boolean running;
    private boolean filling;
    /** Game time of the last progress advance: {@code broadcastChanges} (the tick hook) also fires once per click packet, so this deduplicates advancement to once per game tick. */
    private long lastAdvanceGameTime = -1L;
    @Nullable
    private Identifier lastRecipeId;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case SLOT_PROGRESS -> TimedCraftingSession.this.state[0];
                case SLOT_TOTAL -> TimedCraftingSession.this.state[1];
                case SLOT_ALLOW -> TimedCraftingSession.this.state[2];
                case SLOT_ACTIVE -> TimedCraftingSession.this.state[3];
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case SLOT_PROGRESS -> TimedCraftingSession.this.state[0] = value;
                case SLOT_TOTAL -> TimedCraftingSession.this.state[1] = value;
                case SLOT_ALLOW -> TimedCraftingSession.this.state[2] = Mth.clamp(value, 0, 1);
                case SLOT_ACTIVE -> TimedCraftingSession.this.state[3] = Mth.clamp(value, 0, 1);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    /**
     * Creates the session for one menu instance.
     *
     * @param menu        owning crafting menu (provides the result slot)
     * @param owner       player whose inventory/experience drive the crafting
     * @param craftSlots  the crafting grid
     * @param resultSlots the menu's result container (recipe-used tracking)
     */
    public TimedCraftingSession(AbstractCraftingMenu menu, Player owner, CraftingContainer craftSlots, ResultContainer resultSlots) {
        this.menu = menu;
        this.owner = owner;
        this.craftSlots = craftSlots;
        this.resultSlots = resultSlots;
    }

    /** {@link ContainerData} mirroring {@link #state} to the client as 4 synced slots. */
    public ContainerData data() {
        return this.data;
    }

    /** Mounts (or clears with {@code null}) the metal material and updates the synced metal-mounted bit. */
    public void setMetal(@Nullable MetalMaterial metal) {
        this.metal = metal;
        this.state[SLOT_ACTIVE] = metal != null ? 1 : 0;
    }

    /** @return the mounted metal material, or {@code null} when none. */
    @Nullable
    public MetalMaterial metal() {
        return this.metal;
    }

    /** Whether the metal timed crafting system is enabled: the server checks the field, the client checks the synced data slot. */
    public boolean hasMetal() {
        return this.metal != null || this.state[SLOT_ACTIVE] == 1;
    }

    /** @return whether timed synthesis is currently progressing. */
    public boolean isRunning() {
        return this.running;
    }

    /** Stops progress without resetting the stored progress value. */
    public void clearRunning() {
        this.running = false;
    }

    /** Called when the result slot is clicked or quick-moved: re-evaluate based on the current result and the allow bit whether synthesis is running. */
    public void evaluateRunning() {
        this.running = !this.getResultSlot().getItem().isEmpty() && this.isAllowCrafting();
    }

    /** Called when the recipe book starts filling: suspend result recomputation triggered by slotsChanged. */
    public void beginPlacing() {
        this.filling = true;
    }

    public boolean isFilling() {
        return this.filling;
    }

    /** Called when the recipe book finishes filling: resume recomputation and compute the result with the specified recipe. */
    public void finishPlacing(ServerLevel level, RecipeHolder<CraftingRecipe> recipe) {
        this.filling = false;
        this.updateResult(level, recipe);
    }

    /** Called when the inventory menu closes: the InventoryMenu instance is long-lived, so its state must be reset. */
    public void reset() {
        this.state[SLOT_PROGRESS] = 0;
        this.state[SLOT_TOTAL] = DEFAULT_CRAFTING_TIME_TICKS;
        this.state[SLOT_ALLOW] = 0;
        this.running = false;
    }

    /** Per-tick advancement: automatically collect the result when time is reached. Invoked from the menu's broadcastChanges injection. */
    public void tick() {
        if (!this.running) {
            return;
        }
        long gameTime = this.owner.level().getGameTime();
        if (gameTime == this.lastAdvanceGameTime) {
            return;
        }
        this.lastAdvanceGameTime = gameTime;
        this.state[SLOT_PROGRESS]++;
        if (this.state[SLOT_PROGRESS] < this.state[SLOT_TOTAL]) {
            return;
        }
        Player player = this.owner;
        MetalMaterial.CraftingFunction crafting = this.craftingFunction();
        if (crafting != null && crafting.exhaustionPerCraft() > 0) {
            player.getFoodData().addExhaustion(crafting.exhaustionPerCraft());
        }
        Slot resultSlot = this.getResultSlot();
        ItemStack stack = resultSlot.getItem();
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false, Prediction.PREDICTED);
        }
        resultSlot.onTake(player, stack);
        if (resultSlot.getItem().isEmpty()) {
            this.running = false;
        }
        this.state[SLOT_PROGRESS] = 0;
    }

    /**
     * Recompute the synthesis result: query the recipe → material grade restriction → synthesize total duration → write the result slot.
     * Results are written via the standard remote slot dirty check for synchronization (no manual packets sent, avoiding stateId race conditions).
     * The recipe changes or the total duration changes → reset progress and stop synthesis.
     */
    public void updateResult(ServerLevel level, @Nullable RecipeHolder<CraftingRecipe> hint) {
        if (!(this.getResultSlot() instanceof TimedCraftingResultSlot slot)) {
            return;
        }
        int additionalTime = 0;
        CraftingInput input = this.craftSlots.asCraftInput();
        ItemStack newResult = ItemStack.EMPTY;
        Optional<RecipeHolder<CraftingRecipe>> found = level.getServer().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, input, level, hint);
        if (found.isPresent()) {
            RecipeHolder<CraftingRecipe> recipeEntry = found.get();
            Player owner = this.owner;
            if (owner instanceof ServerPlayer serverPlayer && this.resultSlots.setRecipeUsed(serverPlayer, recipeEntry)) {
                ItemStack craftItem = recipeEntry.value().assemble(input);
                if (craftItem.isItemEnabled(level.enabledFeatures())) {
                    this.state[SLOT_ALLOW] = 1;
                    this.checkRestricted(craftItem);
                    additionalTime = this.additionalCraftingTime();
                    newResult = craftItem;
                }
            }
        } else {
            this.state[SLOT_ALLOW] = 0;
            this.state[SLOT_PROGRESS] = 0;
            this.clearRunning();
        }
        int total = additionalTime + DEFAULT_CRAFTING_TIME_TICKS;
        Identifier recipeId = recipeId(found);
        if (this.state[SLOT_TOTAL] != total || !Objects.equals(this.lastRecipeId, recipeId)) {
            this.state[SLOT_PROGRESS] = 0;
            this.clearRunning();
        }
        this.lastRecipeId = recipeId;
        this.state[SLOT_TOTAL] = total;
        this.resultSlots.setItem(0, newResult);
    }

    /** Material grade restriction: if the output is not in the exemption tag and the input contains disallowed materials, synthesis is forbidden. */
    private void checkRestricted(ItemStack result) {
        MetalMaterial.CraftingFunction crafting = this.craftingFunction();
        if (crafting == null || result.is(crafting.exceptions())) {
            return;
        }
        for (ItemStack stack : this.craftSlots) {
            if (stack.is(crafting.disabledMaterials())) {
                this.state[SLOT_ALLOW] = 0;
                return;
            }
        }
    }

    /**
     * Extra synthesis duration: sum of CRAFTING_TIME components (seconds → ticks; empty grid slots
     * count as 0), divided by the experience + speed bonus coefficient.
     */
    public int additionalCraftingTime() {
        MetalMaterial.CraftingFunction crafting = this.craftingFunction();
        float speedBonus = crafting != null ? crafting.speedBonus() : 0.0f;
        float seconds = 0.0F;
        for (ItemStack stack : this.craftSlots) {
            if (stack.isEmpty()) {
                continue;
            }
            Float i = stack.get(MMEDataComponents.CRAFTING_TIME);
            seconds += Objects.requireNonNullElse(i, 1.0F);
        }
        int level = this.owner.experienceLevel;
        if (CurseLogic.hasCurse(this.owner, MMECurses.DIMINISHED_INTELLECT)) {
            level = Math.max(0, level - CurseLogic.DIMINISHED_LEVEL_PENALTY);
        }
        double divisor = 1 + (level * 2 / 100.0) + speedBonus;
        return (int) (seconds * 20 / divisor);
    }

    /** @return whether the current grid passed the material grade restrictions (synced allow bit). */
    public boolean isAllowCrafting() {
        return this.state[SLOT_ALLOW] == 1;
    }

    /** @return crafting progress as a fraction clamped to [0, 1]. */
    public double getCraftingTime() {
        return Mth.clamp((double) this.state[SLOT_PROGRESS] / this.state[SLOT_TOTAL], 0, 1);
    }

    /** @return the owning menu's result slot. */
    private Slot getResultSlot() {
        return this.menu.getResultSlot();
    }

    /** @return crafting parameters of the mounted metal, or {@code null} when none is mounted or it has no crafting function. */
    @Nullable
    private MetalMaterial.CraftingFunction craftingFunction() {
        return this.metal != null ? this.metal.crafting() : null;
    }

    /** @return recipe id of an optional recipe match, or {@code null} when absent. */
    @Nullable
    private static Identifier recipeId(Optional<RecipeHolder<CraftingRecipe>> found) {
        return found.map(entry -> entry.id().identifier()).orElse(null);
    }
}
