package dk.mosberg.spyro;

import java.util.Objects;
import org.jspecify.annotations.NonNull;
import dk.mosberg.Spyro;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AbilityStatePayload(boolean charging, boolean gliding) implements CustomPayload {
    public static final CustomPayload.@NonNull Id<AbilityStatePayload> ID =
            new CustomPayload.Id<>(Identifier.of(Spyro.MOD_ID, "ability_state"));
    public static final @NonNull PacketCodec<RegistryByteBuf, AbilityStatePayload> CODEC =
            createCodec();

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static @NonNull PacketCodec<RegistryByteBuf, AbilityStatePayload> createCodec() {
        PacketCodec<RegistryByteBuf, AbilityStatePayload> codec =
                PacketCodec.of((AbilityStatePayload value, RegistryByteBuf buf) -> {
                    buf.writeBoolean(value.charging());
                    buf.writeBoolean(value.gliding());
                }, (RegistryByteBuf buf) -> new AbilityStatePayload(buf.readBoolean(),
                        buf.readBoolean()));
        return Objects.requireNonNull(codec);
    }
}
