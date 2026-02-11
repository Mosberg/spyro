package dk.mosberg.spyro;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Tracks flight level time trials and player progress.
 */
public class SpyroTimeTrial {
    private static final Map<UUID, FlightSessionData> ACTIVE_SESSIONS = new HashMap<>();

    public static class FlightSessionData {
        public long startTime;
        public String levelName;
        public int checkpointsReached;
        public boolean completed;

        public FlightSessionData(String levelName) {
            this.levelName = levelName;
            this.startTime = System.currentTimeMillis();
            this.checkpointsReached = 0;
            this.completed = false;
        }

        public long getElapsedMillis() {
            return System.currentTimeMillis() - startTime;
        }

        public String getFormattedTime() {
            long millis = getElapsedMillis();
            long seconds = (millis / 1000) % 60;
            long minutes = (millis / 60000) % 60;
            return String.format("%d:%02d", minutes, seconds);
        }
    }

    public static void startTrial(ServerPlayerEntity player, String levelName) {
        ACTIVE_SESSIONS.put(player.getUuid(), new FlightSessionData(levelName));
        player.sendMessage(net.minecraft.text.Text.literal("§6Flight Trial Started: " + levelName),
                false);
    }

    public static void completeTrial(ServerPlayerEntity player) {
        FlightSessionData session = ACTIVE_SESSIONS.get(player.getUuid());
        if (session != null) {
            session.completed = true;
            player.sendMessage(
                    net.minecraft.text.Text.literal(
                            "§aFlight Trial Completed in " + session.getFormattedTime() + "§a!"),
                    false);
            // Award gems based on time
            int gemsAwarded = calculateGemsFromTime(session.getElapsedMillis());
            SpyroCollectibles.addCollectible(player, SpyroCollectibles.GEMS_OBJECTIVE_ID,
                    gemsAwarded);
            ACTIVE_SESSIONS.remove(player.getUuid());
        }
    }

    public static FlightSessionData getSession(ServerPlayerEntity player) {
        return ACTIVE_SESSIONS.get(player.getUuid());
    }

    private static int calculateGemsFromTime(long millis) {
        long seconds = millis / 1000;
        if (seconds < 30) {
            return 10; // Gold time
        } else if (seconds < 60) {
            return 7; // Good time
        } else if (seconds < 120) {
            return 5; // Okay time
        } else {
            return 3; // Slow time
        }
    }
}
