package com.acuteterror233.mite.mixin.world.level.block.entity;

import com.acuteterror233.mite.interfaces.CampfireBlockEntityExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code CampfireBlockEntity} — adds a fuel-time system so campfires burn out without
 * refueling, exposed through the {@link CampfireBlockEntityExtension} duck interface.
 *
 * <p>A unique {@code remainingIgnitionTime} counter (default 1600 ticks) is stored on the block
 * entity: the {@code cookTick} head inject extinguishes the campfire ({@code LIT = false}) once the
 * counter hits zero and otherwise decrements it once per tick; fuel items added via
 * {@code CampfireBlockMixin} top it up. The counter is persisted through {@code loadAdditional} /
 * {@code saveAdditional} under {@code remaining_ignition_time}. All ticking happens server-side in
 * {@code cookTick}; the extension accessors are side-neutral.</p>
 */
@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin implements CampfireBlockEntityExtension {
    /** MME remaining burn time in ticks; defaults to a full vanilla-ish burn cycle. */
    @Unique private int remainingIgnitionTime = 1600;

    /**
     * Injected at the head of {@code cookTick}: decrements the remaining ignition time or, when
     * exhausted, switches the campfire state to unlit.
     *
     * @param world             the server level the campfire ticks in
     * @param pos               the campfire position
     * @param state             the current campfire block state
     * @param blockEntity       the ticking campfire block entity
     * @param recipeMatchGetter vanilla cached cooking-recipe lookup (unused)
     * @param ci                injection callback (unused; never cancelled)
     */
    @Inject(method = "cookTick", at = @At("HEAD"))
    private static void cookTick(ServerLevel world, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> recipeMatchGetter, CallbackInfo ci) {
        if (blockEntity.MME$GetRemainingIgnitionTime() <= 0) {
            world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, false));
        }else {
            blockEntity.MME$DecreaseRemainingIgnitionTime();
        }
    }

    /**
     * Injected at the tail of {@code loadAdditional}: restores the persisted ignition counter.
     *
     * @param nbt the value input holding the saved block entity data
     * @param ci  injection callback (unused; never cancelled)
     */
    @Inject(method = "loadAdditional", at = @At("TAIL"))
    public void loadAdditional(ValueInput nbt, CallbackInfo ci) {
        nbt.getInt("remaining_ignition_time").ifPresent(remainingIgnitionTime -> this.remainingIgnitionTime = remainingIgnitionTime);
    }

    /**
     * Injected at the tail of {@code saveAdditional}: persists the ignition counter.
     *
     * @param nbt the value output receiving the block entity data
     * @param ci  injection callback (unused; never cancelled)
     */
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    public void saveAdditional(ValueOutput nbt, CallbackInfo ci) {
        nbt.putInt("remaining_ignition_time", this.remainingIgnitionTime);
    }

    /** {@return the remaining burn time in ticks} */
    @Override
    public int MME$GetRemainingIgnitionTime() {
        return this.remainingIgnitionTime;
    }

    /** Decrements the remaining burn time by one tick. */
    @Override
    public void MME$DecreaseRemainingIgnitionTime() {
        this.remainingIgnitionTime--;
    }

    /**
     * Adds fuel burn time (called when a fuel item is placed on the campfire).
     *
     * @param remainingIgnitionTime burn time in ticks to add
     */
    @Override
    public void MME$AddRemainingIgnitionTime(int remainingIgnitionTime) {
        this.remainingIgnitionTime += remainingIgnitionTime;
    }
}
