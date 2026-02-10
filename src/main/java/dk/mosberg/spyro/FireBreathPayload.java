package dk.mosberg.spyro;

import org.jspecify.annotations.NonNull;
import dk.mosberg.Spyro;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record FireBreathPayload() implements CustomPayload {
    public static final CustomPayload.@NonNull Id<FireBreathPayload> ID =
            new CustomPayload.Id<>(Identifier.of(Spyro.MOD_ID, "fire_breath"));
    public static final @NonNull PacketCodec<RegistryByteBuf, FireBreathPayload> CODEC =
            PacketCodec.unit(new FireBreathPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
