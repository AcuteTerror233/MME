package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code CactusBlock} — strongly suppresses cactus growth.
 *
 * <p>The {@code randomTick} redirect halves every {@code BlockState#getValue} result read by the
 * vanilla growth logic. Because the recomputed age is derived from that halved value, the counter
 * cannot climb toward the vanilla fully-grown threshold (age 15), so cactus barely advances and
 * effectively stops self-propagating. Server-driven via random ticks; the mixin is side-neutral.</p>
 */
@Mixin(CactusBlock.class)
public class CactusBlockMixin {
    /**
     * Redirects the {@code BlockState#getValue} call inside {@code randomTick}: returns the stored
     * age divided by two so the vanilla growth comparison never sees a fully-grown value.
     *
     * @param blockState the cactus state being read
     * @param property   the age property being queried
     * @return the halved property value
     */
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"))
    public Comparable<Integer> getValue(BlockState blockState, Property<Integer> property) {
        return blockState.getValue(property) / 2;
    }
}
