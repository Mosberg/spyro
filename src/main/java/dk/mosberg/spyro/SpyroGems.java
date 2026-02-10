package dk.mosberg.spyro;

import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Legacy wrapper for SpyroCollectibles. Maintained for backward compatibility.
 */
public final class SpyroGems {
    public static final String OBJECTIVE_ID = "spyro_gems";

    private SpyroGems() {}

    public static void ensureObjective(MinecraftServer server) {
        SpyroCollectibles.ensureObjectives(server);
    }

    public static void collectFromInventory(ServerPlayerEntity player) {
        SpyroCollectibles.collectFromInventory(player);
    }

    public static void addGems(ServerPlayerEntity player, int amount) {
        SpyroCollectibles.addCollectible(player, SpyroCollectibles.GEMS_OBJECTIVE_ID, amount);
    }

    public static int getGemCount(Scoreboard scoreboard, ScoreHolder holder) {
        return SpyroCollectibles.getGemCount(scoreboard, holder);
    }
}
