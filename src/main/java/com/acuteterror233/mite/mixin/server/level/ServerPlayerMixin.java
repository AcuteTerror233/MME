package com.acuteterror233.mite.mixin.server.level;

import com.acuteterror233.mite.world.player.ExperienceSynchronizer;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code ServerPlayer} — extends server-side player ticking with MME status sync.
 *
 * <p>An injected hook inside {@code doTick} (right before the vanilla {@code Player#tick} call)
 * pushes a {@code ClientboundSetHealthPacket} every tick so health, hunger and saturation changes
 * made by MME systems reach the client immediately, and advances the per-player
 * {@link ExperienceSynchronizer} (MME's experience rework). Everything here is server-side only —
 * {@code doTick} runs exclusively on the server player.</p>
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    /** Shadowed vanilla network connection used to push packets. */
    @Shadow
    public ServerGamePacketListenerImpl connection;

    /** Per-player synchronizer for MME's custom experience handling. */
    @Unique
    private final ExperienceSynchronizer mme$experienceSynchronizer = new ExperienceSynchronizer((ServerPlayer) (Object) this);

    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    /**
     * Injected before the vanilla tick call in {@code doTick}: synchronizes health/food/saturation
     * to the client and ticks the MME experience synchronizer.
     *
     * @param ci injection callback (unused; never cancelled)
     */
    @Inject(method = "doTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;tick()V"))
    public void doTick(CallbackInfo ci) {
        this.connection.send(new ClientboundSetHealthPacket(this.getHealth(), this.getFoodData().getFoodLevel(), this.getFoodData().getSaturationLevel()));
        this.mme$experienceSynchronizer.tick();
    }
}
