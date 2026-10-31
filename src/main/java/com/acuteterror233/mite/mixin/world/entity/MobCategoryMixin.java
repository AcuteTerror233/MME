package com.acuteterror233.mite.mixin.world.entity;

import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code MobCategory} — raises the natural-spawn cap of the {@code MONSTER}
 * category from vanilla 70 to 100 instances per spawn-cycle denominator (+43%). The cap
 * formula in {@code NaturalSpawner} reads {@link MobCategory#getMaxInstancesPerChunk()},
 * which returns this {@code max} field, so every derived monster mobcap scales with it.
 */
@Mixin(MobCategory.class)
public class MobCategoryMixin {

    /** Monster spawn capacity MME raises the category to; vanilla is 70. */
    @Unique
    private static final int MME$MONSTER_SPAWN_CAP = 100;

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
     * Raises the MONSTER category capacity at the tail of the enum constructor. The category
     * is matched via the raw {@code name} field instead of the {@code MONSTER} constant, which
     * is still {@code null} while the enum's static initializer constructs its constants.
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void mme$raiseMonsterSpawnCap(CallbackInfo ci) {
        if ("monster".equals(this.name)) {
            this.max = MME$MONSTER_SPAWN_CAP;
        }
    }
}
