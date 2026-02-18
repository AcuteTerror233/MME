package com.acuteterror233.mite.mixin.world.level.block.state;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.Properties.class)
public abstract class BlockBehaviourPropertiesMixin {
    /**
     * @author AcuteTerror233
     * @reason 不能秒破
     */
    @Overwrite
    public BlockBehaviour.Properties instabreak() {
        return this.strength(0.01F);
    }
    @Shadow
    public abstract BlockBehaviour.Properties strength(float f);
}
