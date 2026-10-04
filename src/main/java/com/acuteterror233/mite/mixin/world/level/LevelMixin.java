package com.acuteterror233.mite.mixin.world.level;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.GlobalAttachmentsProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code Level} — rewrites precipitation behavior under special moon phases.
 *
 * <p>The {@code precipitationAt} method normally delegates to the biome's precipitation rules.
 * A redirect on that call forces snowfall during a {@link SpecialMoonPhase#FROST_MOON frost moon},
 * so every exposed position freezes regardless of the biome's climate. Other moon phases fall
 * through to the vanilla logic untouched.</p>
 *
 * <p>Applies on both sides: precipitation queries are used by both the server (weather ticking,
 * freezing) and the client (visual precipitation rendering).</p>
 */
@Mixin(Level.class)
public abstract class LevelMixin implements LevelAccessor, AutoCloseable, AttachmentTarget, GlobalAttachmentsProvider {
    /**
     * Redirects the {@code Biome.getPrecipitationAt} call inside {@code Level#precipitationAt}.
     * Returns snow unconditionally while the frost moon is active; otherwise delegates to the
     * biome's own decision.
     *
     * @param instance the biome whose vanilla precipitation is being queried
     * @param pos      position precipitation is evaluated for
     * @param seaLevel current sea level, used by the vanilla climate check
     * @return the precipitation type at {@code pos}
     */
    @Redirect(method = "precipitationAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private Biome.Precipitation getPrecipitationAt(Biome instance, BlockPos pos, int seaLevel){
        if (environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.FROST_MOON)) {
            return Biome.Precipitation.SNOW;
        }
        return instance.getPrecipitationAt(pos, seaLevel);
    }
}
