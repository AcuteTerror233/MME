package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.interfaces.FluidDrainableExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Mixin for {@code LiquidBlock} — MITE fluid scoop rework plus the MME
 * {@link FluidDrainableExtension} duck interface.
 *
 * <p>Scooping (both the vanilla {@code BucketPickup} path and MME's unified drainable path)
 * works on <em>any</em> fluid level and never consumes the fluid: the matching bucket is
 * returned while the fluid block stays in place, making liquids reusable. Sources are only
 * created through the dedicated place-source hotkey (see {@code SourceBucketLogic}).
 * The pickup sound comes from the underlying fluid.</p>
 */
@Mixin(LiquidBlock.class)
public class LiquidBlockMixin implements FluidDrainableExtension {
    /** Shadowed vanilla fluid level property (0 = source block). */
    @Shadow
    @Final
    public static IntegerProperty LEVEL;
    /** Shadowed reference to the fluid this block renders/flows. */
    @Shadow
    @Final
    protected FlowingFluid fluid;

    /**
     * Vanilla {@code BucketPickup} path: any fluid level is scoopable and the block is never
     * consumed — returns the matching bucket without touching the world.
     *
     * @param entity the entity taking the fluid
     * @param world  the level containing the fluid
     * @param pos    the fluid block position
     * @param state  the fluid block state
     * @return a bucket of this fluid
     */
    @Inject(method = "pickupBlock", at = @At("HEAD"), cancellable = true)
    private void mme$pickupAnyLevelWithoutConsuming(LivingEntity entity, LevelAccessor world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(new ItemStack(this.fluid.getBucket()));
    }

    /**
     * Duck-interface implementation: scoops any fluid level into a bucket without consuming
     * the fluid block.
     *
     * @param drainer the entity taking the fluid, if any
     * @param world   the level containing the fluid
     * @param pos     the fluid block position
     * @param state   the fluid block state
     * @param bucket  the (empty) bucket item used for pickup
     * @return a bucket of this fluid (the fluid block stays in place)
     */
    @Override
    public ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item bucket) {
        return new ItemStack(getFluidBucket(fluid, bucket));
    }

    /**
     * Duck-interface implementation: the sound played when this fluid is scooped into a bucket.
     *
     * @return the fluid's pickup sound, if defined
     */
    @Override
    public Optional<SoundEvent> MME$GetBucketFillSound() {
        return this.fluid.getPickupSound();
    }
}
