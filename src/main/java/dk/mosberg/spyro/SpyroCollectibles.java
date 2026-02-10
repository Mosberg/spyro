package dk.mosberg.spyro;

import dk.mosberg.Spyro;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.ScoreAccess;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

/**
 * Manages all Spyro collectibles (gems, talismans, orbs). Handles inventory collection, scoreboard
 * tracking, and value calculation.
 */
public final class SpyroCollectibles {
    public static final String GEMS_OBJECTIVE_ID = "spyro_gems";
    public static final String TALISMANS_OBJECTIVE_ID = "spyro_talismans";
    public static final String ORBS_OBJECTIVE_ID = "spyro_orbs";

    private SpyroCollectibles() {}

    public static void ensureObjectives(MinecraftServer server) {
        Scoreboard scoreboard = server.getScoreboard();

        if (scoreboard.getNullableObjective(GEMS_OBJECTIVE_ID) == null) {
            scoreboard.addObjective(GEMS_OBJECTIVE_ID, ScoreboardCriterion.DUMMY,
                    Text.literal("Spyro Gems"), ScoreboardCriterion.RenderType.INTEGER, false,
                    null);
        }
        if (scoreboard.getNullableObjective(TALISMANS_OBJECTIVE_ID) == null) {
            scoreboard.addObjective(TALISMANS_OBJECTIVE_ID, ScoreboardCriterion.DUMMY,
                    Text.literal("Spyro Talismans"), ScoreboardCriterion.RenderType.INTEGER, false,
                    null);
        }
        if (scoreboard.getNullableObjective(ORBS_OBJECTIVE_ID) == null) {
            scoreboard.addObjective(ORBS_OBJECTIVE_ID, ScoreboardCriterion.DUMMY,
                    Text.literal("Spyro Orbs"), ScoreboardCriterion.RenderType.INTEGER, false,
                    null);
        }
    }

    public static void collectFromInventory(ServerPlayerEntity player) {
        SpyroConfig config = SpyroConfig.get();
        if (!config.enableAutoCollect) {
            return;
        }
        PlayerInventory inventory = player.getInventory();
        int gems = 0;
        int talismans = 0;
        int orbs = 0;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof CollectibleItem collectible)) {
                continue;
            }

            int count = stack.getCount();
            int value = collectible.getValue();
            if (count <= 0 || value <= 0) {
                continue;
            }

            switch (collectible.getCollectibleType()) {
                case "gems" -> gems += count * value;
                case "talismans" -> talismans += count * value;
                case "orbs" -> orbs += count * value;
            }

            stack.decrement(count);
        }

        if (gems > 0) {
            addCollectible(player, GEMS_OBJECTIVE_ID, gems);
        }
        if (talismans > 0) {
            addCollectible(player, TALISMANS_OBJECTIVE_ID, talismans);
        }
        if (orbs > 0) {
            addCollectible(player, ORBS_OBJECTIVE_ID, orbs);
        }

        if (gems > 0 || talismans > 0 || orbs > 0) {
            float volume = Math.max(0.0f, Math.min(1.0f, config.collectSoundVolume));
            player.getEntityWorld().playSound(null, player.getBlockPos(),
                    SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, volume, 1.2f);
        }
    }

    public static void addCollectible(ServerPlayerEntity player, String objectiveId, int amount) {
        ServerWorld serverWorld = (ServerWorld) player.getEntityWorld();
        MinecraftServer server = serverWorld.getServer();
        if (server == null) {
            return;
        }

        Scoreboard scoreboard = server.getScoreboard();
        ScoreboardObjective objective = scoreboard.getNullableObjective(objectiveId);
        if (objective == null) {
            ensureObjectives(server);
            objective = scoreboard.getNullableObjective(objectiveId);
            if (objective == null) {
                Spyro.LOGGER.warn("Spyro objective '" + objectiveId + "' could not be created.");
                return;
            }
        }

        ScoreAccess score = scoreboard.getOrCreateScore(player, objective);
        score.setScore(score.getScore() + amount);

        SpyroConfig config = SpyroConfig.get();
        if (!config.enableStatsTracking) {
            return;
        }
        SpyroPlayerStats stats = SpyroStatsAttachment.get(player);
        switch (objectiveId) {
            case GEMS_OBJECTIVE_ID -> stats.addGemsCollected(amount);
            case TALISMANS_OBJECTIVE_ID -> stats.addTalismansCollected(amount);
            case ORBS_OBJECTIVE_ID -> stats.addOrbsCollected(amount);
            default -> {
                return;
            }
        }
    }

    public static int getCollectibleCount(Scoreboard scoreboard, ScoreHolder holder,
            String objectiveId) {
        ScoreboardObjective objective = scoreboard.getNullableObjective(objectiveId);
        if (objective == null) {
            return 0;
        }
        ReadableScoreboardScore score = scoreboard.getScore(holder, objective);
        return score == null ? 0 : score.getScore();
    }

    public static int getGemCount(Scoreboard scoreboard, ScoreHolder holder) {
        return getCollectibleCount(scoreboard, holder, GEMS_OBJECTIVE_ID);
    }

    public static int getTalismanCount(Scoreboard scoreboard, ScoreHolder holder) {
        return getCollectibleCount(scoreboard, holder, TALISMANS_OBJECTIVE_ID);
    }

    public static int getOrbCount(Scoreboard scoreboard, ScoreHolder holder) {
        return getCollectibleCount(scoreboard, holder, ORBS_OBJECTIVE_ID);
    }

    public static int getTotalScore(Scoreboard scoreboard, ScoreHolder holder) {
        int gems = getGemCount(scoreboard, holder);
        int talismans = getTalismanCount(scoreboard, holder) * 10;
        int orbs = getOrbCount(scoreboard, holder) * 8;
        return gems + talismans + orbs;
    }
}
