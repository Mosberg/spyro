package dk.mosberg.spyro;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class SpyroNetworking {
    private static boolean payloadsRegistered;

    public static void registerServer() {
        registerPayloads();
        ServerPlayNetworking.registerGlobalReceiver(AbilityStatePayload.ID,
                (payload, context) -> context.server().execute(() -> {
                    SpyroPlayerState state = SpyroStateManager.get(context.player());
                    state.setCharging(payload.charging());
                    state.setGliding(payload.gliding());
                }));

        ServerPlayNetworking.registerGlobalReceiver(FireBreathPayload.ID,
                (payload, context) -> context.server()
                        .execute(() -> SpyroStateManager.get(context.player()).requestFire()));
    }

    public static void registerPayloads() {
        if (payloadsRegistered) {
            return;
        }
        payloadsRegistered = true;
        PayloadTypeRegistry.playC2S().register(AbilityStatePayload.ID, AbilityStatePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(FireBreathPayload.ID, FireBreathPayload.CODEC);
    }
}
