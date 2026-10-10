package com.acuteterror233.mite.network;

import com.acuteterror233.mite.MME;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Client-to-server payload requesting a source-liquid placement from the held bucket.
 * The server validates the bucket, the raytraced target and the 100 XP cost (failing
 * silently when unaffordable) and plays the experience-orb pickup sound on success.
 */
public record PlaceSourceLiquidPayload() implements CustomPacketPayload {
    /** Shared stateless instance. */
    public static final PlaceSourceLiquidPayload INSTANCE = new PlaceSourceLiquidPayload();
    /** Payload channel id under the MME namespace. */
    public static final Type<PlaceSourceLiquidPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MME.MOD_ID, "place_source_liquid"));
    /** Unit codec — the payload carries no data. */
    public static final StreamCodec<io.netty.buffer.ByteBuf, PlaceSourceLiquidPayload> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
