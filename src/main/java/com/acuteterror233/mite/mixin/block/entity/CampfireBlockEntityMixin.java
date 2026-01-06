package com.acuteterror233.mite.mixin.block.entity;

import com.acuteterror233.mite.atinterface.CampfireBlockEntityExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin implements CampfireBlockEntityExtension {
    @Unique private int remainingIgnitionTime = 1600;
    @Inject(method = "cookTick", at = @At("HEAD"))
    private static void cookTick(ServerLevel world, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> recipeMatchGetter, CallbackInfo ci) {
        if (blockEntity instanceof CampfireBlockEntityExtension extension) {
            if (extension.MME$GetRemainingIgnitionTime() <= 0) {
                world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, false));
            }else {
                extension.MME$DecreaseRemainingIgnitionTime();
            }
        }
    }
    @Inject(method = "loadAdditional", at = @At("TAIL"))
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries, CallbackInfo ci) {
        nbt.getInt("remaining_ignition_time").ifPresent(remainingIgnitionTime -> this.remainingIgnitionTime = remainingIgnitionTime);
    }
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries, CallbackInfo ci) {
        nbt.putInt("remaining_ignition_time", this.remainingIgnitionTime);
    }

    @Override
    public int MME$GetRemainingIgnitionTime() {
        return this.remainingIgnitionTime;
    }

    @Override
    public void MME$DecreaseRemainingIgnitionTime() {
        this.remainingIgnitionTime--;
    }

    @Override
    public void MME$AddRemainingIgnitionTime(int remainingIgnitionTime) {
        this.remainingIgnitionTime += remainingIgnitionTime;
    }
}
