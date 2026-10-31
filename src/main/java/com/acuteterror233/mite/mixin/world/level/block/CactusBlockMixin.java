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
 * <p>The {@code randomTick} redirect halves the single {@code BlockState#getValue(AGE)} read that
 * vanilla performs. Since the halved value never reaches 8 (flower) or 15 (upward growth), the
 * cactus neither flowers nor grows taller; the age converges to 1 (an age of 1 halves to 0 and is
 * written back as 1). Server-driven via random ticks; the mixin is side-neutral.</p>
 */
@Mixin(CactusBlock.class)
public class CactusBlockMixin {
    /**
     * Redirects the {@code BlockState#getValue} call inside {@code randomTick}: returns the stored
     * age divided by two, so the vanilla comparisons against age 8 and age 15 can never match.
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
