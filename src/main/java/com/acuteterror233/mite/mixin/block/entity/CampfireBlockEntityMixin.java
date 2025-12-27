package com.acuteterror233.mite.mixin.block.entity;

import com.acuteterror233.mite.atinterface.GetIgnitionTimeExtension;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin implements GetIgnitionTimeExtension {
    @Unique private int remainingIgnitionTime = 1600;
    @Inject(method = "litServerTick", at = @At("HEAD"))
    private static void litServerTick(ServerWorld world, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, ServerRecipeManager.MatchGetter<SingleStackRecipeInput, CampfireCookingRecipe> recipeMatchGetter, CallbackInfo ci) {
        if (blockEntity instanceof GetIgnitionTimeExtension extension) {
            if (extension.getRemainingIgnitionTime() <= 0) {
                world.setBlockState(pos, state.with(Properties.LIT, false));
            }else {
                extension.removeRemainingIgnitionTime();
            }
        }
    }
    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries, CallbackInfo ci) {
        nbt.getInt("RemainingIgnitionTime").ifPresent(remainingIgnitionTime -> this.remainingIgnitionTime = remainingIgnitionTime);
    }
    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries, CallbackInfo ci) {
        nbt.putInt("RemainingIgnitionTime", this.remainingIgnitionTime);
    }

    @Override
    public int getRemainingIgnitionTime() {
        return this.remainingIgnitionTime;
    }

    @Override
    public void removeRemainingIgnitionTime() {
        this.remainingIgnitionTime -= 2;
    }

    @Override
    public void addRemainingIgnitionTime(int remainingIgnitionTime) {
        this.remainingIgnitionTime += remainingIgnitionTime;
    }
}
