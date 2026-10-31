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

/**
 * Mixin for {@code ComposterBlock} — makes the composter output {@link MMEItems#MANURE} instead of
 * bone meal.
 *
 * <p>Three touch points: {@code extractProduce} is overwritten to spawn a manure item entity;
 * {@code getContainer} is redirected at RETURN so the composter's output container is backed by a
 * manure stack; and {@code ComposterBlock.OutputContainer#canTakeItemThroughFace} is redirected so
 * hoppers treat manure as the extractable output. Output changes are server-side.</p>
 */
@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin extends Block implements WorldlyContainerHolder {
    /**
     * Mirrors {@code ComposterBlock}'s constructor; required because the mixin extends {@link Block}.
     *
     * @param properties block behaviour properties forwarded to {@link Block}
     */
    public ComposterBlockMixin(Properties properties) {
        super(properties);
    }

    /** Shadowed vanilla helper that resets the composter to its empty state and returns the new state. */
    @Shadow
    private static BlockState empty(final @Nullable Entity sourceEntity, final BlockState state, final LevelAccessor level, final BlockPos pos){
        return null;
    }

    /**
     * Overwrites vanilla {@code ComposterBlock#extractProduce} (which drops bone meal): spawns one
     * {@link MMEItems#MANURE} item entity above the composter on the server, then resets the
     * composter to its empty state and plays the vanilla empty sound.
     *
     * @param sourceEntity the entity that triggered extraction, if any
     * @param state        the composter block state
     * @param level        the level containing the composter
     * @param pos          the composter position
     * @return the resulting empty composter state
     */
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

    /**
     * Injected at the return of {@code ComposterBlock#getContainer}: when vanilla returned an
     * {@code OutputContainer} (the composter is ready), swaps it for one backed by a
     * {@link MMEItems#MANURE} stack so hoppers and players extract manure.
     *
     * @param state the composter block state
     * @param level the level accessor containing the composter
     * @param pos   the composter position
     * @param cir   return callback; set to the manure-backed container when applicable
     */
    @Inject(method = "getContainer", at = @At("RETURN"), cancellable = true)
    public void getContainer(BlockState state, LevelAccessor level, BlockPos pos, CallbackInfoReturnable<WorldlyContainer> cir) {
        if (cir.getReturnValue() instanceof ComposterBlock.OutputContainer) {
            cir.setReturnValue(new ComposterBlock.OutputContainer(state, level, pos, new ItemStack(MMEItems.MANURE)));
        }
    }

    /**
     * Nested mixin for {@code ComposterBlock.OutputContainer} — pins the container's output item to
     * manure so extraction checks accept it.
     */
    @Mixin(ComposterBlock.OutputContainer.class)
    private static abstract class OutputContainer extends SimpleContainer implements WorldlyContainer{
        /**
         * Redirects the {@code ItemStack#is} call inside {@code canTakeItemThroughFace}: matches
         * {@link MMEItems#MANURE} instead of the vanilla comparison item, so hoppers may pull manure.
         *
         * @param instance the item stack being inspected
         * @param o        the vanilla comparison object (ignored)
         * @return whether the stack is manure
         */
        @Redirect(method = "canTakeItemThroughFace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
        private static boolean asd(ItemStack instance, Object o){
            return instance.is(MMEItems.MANURE);
        }
    }
}
