package com.acuteterror233.mite.mixin.attribute;

import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BedRule.Rule.class)
public abstract class BedRuleMixin {
    @Final
    @Shadow
    public static BedRule.Rule ALWAYS;
    @Final
    @Shadow
    public static BedRule.Rule WHEN_DARK;
    @Final
    @Shadow
    public static BedRule.Rule NEVER;

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
