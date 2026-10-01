package net.kirks.exoticores.network.payload;

import io.netty.buffer.ByteBuf;
import net.kirks.exoticores.ExoticOres;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record GeigerPayload(float intensity) implements CustomPacketPayload {
    public static final Type<GeigerPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "geiger"));

    public static final StreamCodec<ByteBuf, GeigerPayload> STREAM_CODEC =
            ByteBufCodecs.FLOAT.map(GeigerPayload::new, GeigerPayload::intensity);

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
