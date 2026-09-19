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

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin
        extends BaseContainerBlockEntity
        implements WorldlyContainer,
        StackedContentsCompatible,
        RecipeCraftingHolder,
        AbstractFurnaceBlockEntityExtension {
    @Shadow
    public NonNullList<ItemStack> items;
    @Shadow
    private int cookingTotalTime;
    @Shadow
    private int cookingTimer;
    @Unique
    private int combustionGrade;

    protected AbstractFurnaceBlockEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    protected void loadAdditional(ValueInput input, CallbackInfo ci){
        this.combustionGrade = input.getIntOr("combustion_grade", 0);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    protected void saveAdditional(ValueOutput output, CallbackInfo ci){
        output.putInt("combustion_grade", this.combustionGrade);
    }

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

    @Override
    public int MME$getCombustionGrade() {
        return this.combustionGrade;
    }

    @Override
    public void MME$setCombustionGrade(Integer cg) {
        this.combustionGrade = cg;
    }
}
