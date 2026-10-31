package com.acuteterror233.mite.mixin.world.level.block.entity;

import com.acuteterror233.mite.component.MMEDataComponents;
import com.acuteterror233.mite.interfaces.AbstractFurnaceBlockEntityExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code AbstractFurnaceBlockEntity} — adds MME's combustion-grade (fuel tier) system to
 * all furnaces, exposed through {@link AbstractFurnaceBlockEntityExtension}.
 *
 * <p>Fuel items carry a {@code COMBUSTION_GRADE} data component (assigned from
 * {@code MME.CORRESPONDING_COMBUSTION_GRADE}), ingredients a {@code REQUIRED_COMBUSTION_GRADE}, and
 * the furnace block itself a {@code MAX_COMBUSTION_GRADE}. While burning, the current grade is
 * persisted in a unique {@code combustionGrade} field (saved as {@code combustion_grade}). In
 * {@code serverTick} the {@code canBurn} result is only honored when the furnace's max grade covers
 * the stored grade and the smelting input's required grade is met; when lighting up with fresh fuel
 * the stored grade is updated to that fuel's grade, which the check sees on the following tick. The
 * {@code setItem} inject resets cooking progress (timer 0, total 200) whenever a different item
 * enters the input slot on the server and the active fuel grade satisfies the new input. All gameplay
 * logic runs server-side ({@code serverTick} / server-side {@code setItem}); save/load applies
 * wherever the block entity is (de)serialized.</p>
 */
@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin
        extends BaseContainerBlockEntity
        implements WorldlyContainer,
        StackedContentsCompatible,
        RecipeCraftingHolder,
        AbstractFurnaceBlockEntityExtension {
    /** Shadowed furnace inventory: slot 0 input, slot 1 fuel, slot 2 result. */
    @Shadow
    public NonNullList<ItemStack> items;
    /** Shadowed vanilla total cooking duration of the current recipe. */
    @Shadow
    private int cookingTotalTime;
    /** Shadowed vanilla current cooking progress. */
    @Shadow
    private int cookingTimer;
    /** MME active fuel grade currently powering this furnace. */
    @Unique
    private int combustionGrade;

    /**
     * Mirrors {@code AbstractFurnaceBlockEntity}'s constructor; required because the mixin extends
     * {@link BaseContainerBlockEntity}.
     *
     * @param type          the block entity type
     * @param worldPosition the block position
     * @param blockState    the block state
     */
    protected AbstractFurnaceBlockEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    /**
     * Injected at the tail of {@code loadAdditional}: restores the persisted combustion grade.
     *
     * @param input the value input holding the saved block entity data
     * @param ci    injection callback (unused; never cancelled)
     */
    @Inject(method = "loadAdditional", at = @At("TAIL"))
    protected void loadAdditional(ValueInput input, CallbackInfo ci){
        this.combustionGrade = input.getIntOr("combustion_grade", 0);
    }

    /**
     * Injected at the tail of {@code saveAdditional}: persists the combustion grade.
     *
     * @param output the value output receiving the block entity data
     * @param ci     injection callback (unused; never cancelled)
     */
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    protected void saveAdditional(ValueOutput output, CallbackInfo ci){
        output.putInt("combustion_grade", this.combustionGrade);
    }

    /**
     * Modifies the expression value of the {@code canBurn} call inside {@code serverTick}: adopts the
     * newly consumed fuel's grade into {@code combustionGrade} when the furnace has just run out of
     * burn time, then only allows burning when the furnace's {@code MAX_COMBUSTION_GRADE} covers the
     * stored grade and the input's {@code REQUIRED_COMBUSTION_GRADE} is satisfied. The check reads the
     * grade as it was before this tick's adoption, so a newly adopted grade takes effect next tick.
     *
     * @param origina the vanilla {@code canBurn} result
     * @param entity  the ticking furnace block entity (injected from method args)
     * @return whether the furnace may burn with the current grade constraints
     */
    @ModifyExpressionValue(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;canBurn(Lnet/minecraft/core/NonNullList;ILnet/minecraft/world/item/ItemStack;)Z"))
    private static boolean canBurn(boolean origina, @Local(argsOnly = true, name = "entity") AbstractFurnaceBlockEntity entity) {
        ItemStack ingredient = entity.items.get(0);
        ItemStack fuel = entity.items.get(1);
        ItemStack furnace = entity.getBlockState().getBlock().asItem().getDefaultInstance();
        int i = entity.MME$getCombustionGrade();
        Integer cg = fuel.getOrDefault(MMEDataComponents.COMBUSTION_GRADE, i);
        if (!fuel.equals(ItemStack.EMPTY) && entity.litTimeRemaining <= 0) {
            entity.MME$setCombustionGrade(cg);
        }
        if (furnace.getOrDefault(MMEDataComponents.MAX_COMBUSTION_GRADE, 1) >= i && ingredient.getOrDefault(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 1) <= i) {
            return origina;
        }
        return false;
    }

    /**
     * Targets the {@code ItemStack#limitSize(I)V} call inside {@code setItem}. The stray
     * {@code args = "ldc="} is inert ({@code @At.args} entries are named parameters and
     * {@code BeforeInvoke} never reads an {@code ldc} parameter): when a different item enters the
     * input slot on the server and the fuel grade satisfies the new input, resets cooking progress and cancels the vanilla tail.
     *
     * @param slot      the inventory slot being set
     * @param itemStack the stack being placed into {@code slot}
     * @param ci        cancellation callback; cancelling skips the vanilla remaining logic
     */
    @Inject(method = "setItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;limitSize(I)V", args = "ldc="), cancellable = true)
    private void setItem(int slot, ItemStack itemStack, CallbackInfo ci){
        ItemStack oldStack = this.items.get(slot);
        ItemStack ingredient = items.get(0);
        ItemStack fuel = items.get(1);
        boolean same = !itemStack.isEmpty() && ItemStack.isSameItemSameComponents(oldStack, itemStack);
        Integer cg = fuel.getOrDefault(MMEDataComponents.COMBUSTION_GRADE, 0);
        if (slot == 0 && !same && this.level instanceof ServerLevel && cg >= ingredient.getOrDefault(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 1)){
            this.cookingTimer = 0;
            this.cookingTotalTime = 200;
            ci.cancel();
        }
    }

    /** {@return the active fuel grade powering this furnace} */
    @Override
    public int MME$getCombustionGrade() {
        return this.combustionGrade;
    }

    /**
     * Sets the active fuel grade (adopted when new fuel starts burning).
     *
     * @param cg the fuel grade to apply
     */
    @Override
    public void MME$setCombustionGrade(Integer cg) {
        this.combustionGrade = cg;
    }
}
