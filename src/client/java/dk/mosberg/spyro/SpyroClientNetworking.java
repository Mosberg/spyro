package dk.mosberg.spyro;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SpyroClientNetworking {
    public static void sendAbilityState(boolean charging, boolean gliding) {
        ClientPlayNetworking.send(new AbilityStatePayload(charging, gliding));
    }

    public static void sendFireRequest() {
        ClientPlayNetworking.send(new FireBreathPayload());
    }
}
