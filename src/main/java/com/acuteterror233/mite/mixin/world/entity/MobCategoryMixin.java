package com.acuteterror233.mite.mixin.world.entity;

import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code MobCategory} — Doubles the natural-spawn cap of the {@code MONSTER}
 * category (70 → 140 instances per spawn cycle denominator). The cap formula in
 * {@code NaturalSpawner} reads {@link MobCategory#getMaxInstancesPerChunk()}, which returns
 * this {@code max} field, so doubling the field doubles every derived monster mobcap.
 */
@Mixin(MobCategory.class)
public class MobCategoryMixin {

    /** Per-category spawn capacity; {@code final} in vanilla, made mutable for this tweak. */
    @Shadow
    @Final
    @Mutable
    private int max;

    /** Category identifier; {@code "monster"} for the MONSTER constant. */
    @Shadow
    @Final
    private String name;

    /**
     * Doubles the MONSTER category capacity at the tail of the enum constructor. The category
     * is matched via the raw {@code name} field instead of the {@code MONSTER} constant, which
     * is still {@code null} while the enum's static initializer constructs its constants.
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void mme$doubleMonsterSpawnCap(CallbackInfo ci) {
        if ("monster".equals(this.name)) {
            this.max = this.max * 2;
        }
    }
}
