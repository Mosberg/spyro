package dk.mosberg.spyro;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import dk.mosberg.Spyro;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class SpyroStateManager {
    private static final Map<UUID, SpyroPlayerState> STATES = new ConcurrentHashMap<>();

    public static SpyroPlayerState get(ServerPlayerEntity player) {
        return STATES.computeIfAbsent(player.getUuid(), id -> new SpyroPlayerState());
    }

    public static void remove(UUID playerId) {
        STATES.remove(playerId);
    }

    public static void onServerTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            SpyroCollectibles.collectFromInventory(player);
            boolean spyroMode = player.getCommandTags().contains(Spyro.SPYRO_TAG);
            SpyroPlayerState state = STATES.get(player.getUuid());
            if (!spyroMode) {
                if (state != null) {
                    state.clearInputs();
                    state.tickCooldowns();
                }
                continue;
            }
            if (state == null) {
                state = new SpyroPlayerState();
                STATES.put(player.getUuid(), state);
            }
            state.tickCooldowns();
            SpyroAbilities.apply(player, state);
        }
    }
}
