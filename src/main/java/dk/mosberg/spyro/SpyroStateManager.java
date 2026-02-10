package dk.mosberg.spyro;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import dk.mosberg.Spyro;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

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
            SpyroPlayerStats stats = SpyroStatsAttachment.get(player);
            SpyroAbilityUnlocks unlocks = stats.getAbilityUnlocks();
            boolean spyroMode = player.getCommandTags().contains(Spyro.SPYRO_TAG);
            SpyroPlayerState state = STATES.get(player.getUuid());
            if (!spyroMode) {
                if (state != null) {
                    state.clearInputs();
                    state.tickCooldowns();
                }
                continue;
            }
            SpyroCollectibles.collectFromInventory(player);
            updateProgression(player, unlocks);
            if (state == null) {
                state = new SpyroPlayerState();
                STATES.put(player.getUuid(), state);
            }
            enforceUnlocks(state, unlocks);
            state.tickCooldowns();
            SpyroAbilities.apply(player, state, unlocks);
        }
    }

    private static void enforceUnlocks(SpyroPlayerState state, SpyroAbilityUnlocks unlocks) {
        if (!unlocks.chargeUnlocked) {
            state.setCharging(false);
        }
        if (!unlocks.glideUnlocked) {
            state.setGliding(false);
        }
        if (!unlocks.fireBreathUnlocked) {
            state.consumeFireRequest();
        }
    }

    private static void updateProgression(ServerPlayerEntity player, SpyroAbilityUnlocks unlocks) {
        SpyroConfig config = SpyroConfig.get();
        if (!config.enableProgressTracking) {
            return;
        }
        ServerWorld world = (ServerWorld) player.getEntityWorld();
        int gems = SpyroCollectibles.getGemCount(world.getScoreboard(), player);

        if (!unlocks.fireBreathUnlocked && gems >= config.unlockFireBreathGems) {
            unlocks.fireBreathUnlocked = true;
            player.sendMessage(Text.literal("Unlocked Spyro ability: Fire Breath."), false);
        }
        if (!unlocks.chargeUnlocked && gems >= config.unlockChargeGems) {
            unlocks.chargeUnlocked = true;
            player.sendMessage(Text.literal("Unlocked Spyro ability: Charge."), false);
        }
        if (!unlocks.glideUnlocked && gems >= config.unlockGlideGems) {
            unlocks.glideUnlocked = true;
            player.sendMessage(Text.literal("Unlocked Spyro ability: Glide."), false);
        }
    }
}
