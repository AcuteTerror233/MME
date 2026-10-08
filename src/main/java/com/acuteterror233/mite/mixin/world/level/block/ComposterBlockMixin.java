package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.item.MMEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin extends Block implements WorldlyContainerHolder {
    public ComposterBlockMixin(Properties properties) {
        super(properties);
    }

    @Shadow
    private static BlockState empty(final @Nullable Entity sourceEntity, final BlockState state, final LevelAccessor level, final BlockPos pos){
        return null;
    }

    @Overwrite
    public static BlockState extractProduce(final Entity sourceEntity, final BlockState state, final Level level, final BlockPos pos) {
        if (!level.isClientSide()) {
            Vec3 itemPos = Vec3.atLowerCornerWithOffset(pos, 0.5, 1.01, 0.5).offsetRandomXZ(level.getRandom(), 0.7F);
            ItemEntity entity = new ItemEntity(level, itemPos.x(), itemPos.y(), itemPos.z(), new ItemStack(MMEItems.MANURE));
            entity.setDefaultPickUpDelay();
            level.addFreshEntity(entity);
        }

        BlockState emptyState = empty(sourceEntity, state, level, pos);
        level.playSound(null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        return emptyState;
    }

    @Inject(method = "getContainer", at = @At("RETURN"), cancellable = true)
    public void getContainer(BlockState state, LevelAccessor level, BlockPos pos, CallbackInfoReturnable<WorldlyContainer> cir) {
        if (cir.getReturnValue() instanceof ComposterBlock.OutputContainer) {
            cir.setReturnValue(new ComposterBlock.OutputContainer(state, level, pos, new ItemStack(MMEItems.MANURE)));
        }
    }

    @Mixin(ComposterBlock.OutputContainer.class)
    private static abstract class OutputContainer extends SimpleContainer implements WorldlyContainer{
        @Redirect(method = "canTakeItemThroughFace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
        private static boolean asd(ItemStack instance, Object o){
            return instance.is(MMEItems.MANURE);
        }
    }
}
