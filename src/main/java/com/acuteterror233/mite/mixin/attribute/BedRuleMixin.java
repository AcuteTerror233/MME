package com.acuteterror233.mite.mixin.attribute;

import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin for {@code BedRule.Rule} — lets the {@code WHEN_DARK} bed rule always pass its level test.
 *
 * <p>The {@code test} overwrite makes {@code WHEN_DARK} behave like {@code ALWAYS} (no actual
 * darkness evaluation), so dimensions configured with the sleep-at-dark rule accept beds at any
 * light level; {@code NEVER} still refuses. Applies wherever bed rules are evaluated (server-side
 * sleep handling drives the decision).</p>
 */
@Mixin(BedRule.Rule.class)
public abstract class BedRuleMixin {
    /** Shadowed vanilla rule constant. */
    @Final
    @Shadow
    public static BedRule.Rule ALWAYS;
    /** Shadowed vanilla rule constant. */
    @Final
    @Shadow
    public static BedRule.Rule WHEN_DARK;
    /** Shadowed vanilla rule constant. */
    @Final
    @Shadow
    public static BedRule.Rule NEVER;

    /**
     * Overwrites vanilla {@code test}: {@code ALWAYS} and {@code WHEN_DARK} accept, {@code NEVER}
     * refuses (vanilla evaluates darkness for {@code WHEN_DARK}).
     *
     * @param level the level the bed is being used in (unused by the rewritten rules)
     * @return whether sleeping is permitted by this rule
     */
    @Overwrite
    public boolean test(final Level level) {
        return switch ((BedRule.Rule) (Object)this) {
            case ALWAYS -> true;
            case WHEN_DARK -> true;
            case NEVER -> false;
        };
    }

//    @Shadow
//    @Final
//    @Mutable
//    public static final BedRule CAN_SLEEP_WHEN_DARK = new BedRule(
//            BedRule.Rule.ALWAYS, BedRule.Rule.ALWAYS, false, Optional.of(Component.translatable("block.minecraft.bed.no_sleep"))
//    );
}
