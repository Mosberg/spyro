package dk.mosberg.spyro;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;

/**
 * Manages Spyro-specific player stats and progression. Stores unlocks, level progress, and
 * achievements.
 */
public class SpyroPlayerStats {
    public static final Codec<SpyroPlayerStats> CODEC = NbtCompound.CODEC.xmap(nbt -> {
        SpyroPlayerStats stats = new SpyroPlayerStats();
        stats.readNbt(nbt);
        return stats;
    }, stats -> {
        NbtCompound nbt = new NbtCompound();
        stats.writeNbt(nbt);
        return nbt;
    });

    private int totalGemsCollected = 0;
    private int totalTalismansCollected = 0;
    private int totalOrbsCollected = 0;
    private int levelsCompleted = 0;
    private int enemiesDefeated = 0;
    private SpyroAbilityUnlocks abilityUnlocks = new SpyroAbilityUnlocks(SpyroConfig.get());

    public SpyroPlayerStats() {}

    public void addGemsCollected(int count) {
        this.totalGemsCollected += count;
    }

    public void addTalismansCollected(int count) {
        this.totalTalismansCollected += count;
    }

    public void addOrbsCollected(int count) {
        this.totalOrbsCollected += count;
    }

    public void addLevelCompleted() {
        this.levelsCompleted++;
    }

    public void addEnemyDefeated() {
        this.enemiesDefeated++;
    }

    public int getTotalGemsCollected() {
        return totalGemsCollected;
    }

    public int getTotalTalismansCollected() {
        return totalTalismansCollected;
    }

    public int getTotalOrbsCollected() {
        return totalOrbsCollected;
    }

    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }

    public SpyroAbilityUnlocks getAbilityUnlocks() {
        return abilityUnlocks;
    }

    public void resetToDefaults() {
        totalGemsCollected = 0;
        totalTalismansCollected = 0;
        totalOrbsCollected = 0;
        levelsCompleted = 0;
        enemiesDefeated = 0;
        abilityUnlocks.applyDefaults(SpyroConfig.get());
    }

    public int getOverallProgress() {
        return Math.min(100, (totalGemsCollected / 100) + (levelsCompleted * 10));
    }

    public void writeNbt(NbtCompound tag) {
        tag.putInt("TotalGems", totalGemsCollected);
        tag.putInt("TotalTalismans", totalTalismansCollected);
        tag.putInt("TotalOrbs", totalOrbsCollected);
        tag.putInt("LevelsCompleted", levelsCompleted);
        tag.putInt("EnemiesDefeated", enemiesDefeated);

        NbtCompound unlocks = new NbtCompound();
        unlocks.putBoolean("Fire", abilityUnlocks.fireBreathUnlocked);
        unlocks.putBoolean("Charge", abilityUnlocks.chargeUnlocked);
        unlocks.putBoolean("Glide", abilityUnlocks.glideUnlocked);
        unlocks.putBoolean("Flight", abilityUnlocks.flightUnlocked);
        unlocks.putBoolean("TimeSlow", abilityUnlocks.timeSlowUnlocked);
        unlocks.putBoolean("SuperCharge", abilityUnlocks.superChargeUnlocked);
        tag.put("AbilityUnlocks", unlocks);
    }

    public void readNbt(NbtCompound tag) {
        totalGemsCollected = tag.getInt("TotalGems").orElse(0);
        totalTalismansCollected = tag.getInt("TotalTalismans").orElse(0);
        totalOrbsCollected = tag.getInt("TotalOrbs").orElse(0);
        levelsCompleted = tag.getInt("LevelsCompleted").orElse(0);
        enemiesDefeated = tag.getInt("EnemiesDefeated").orElse(0);
        SpyroConfig config = SpyroConfig.get();

        if (tag.contains("AbilityUnlocks")) {
            NbtCompound unlocks = tag.getCompound("AbilityUnlocks").orElse(new NbtCompound());
            abilityUnlocks.fireBreathUnlocked =
                    unlocks.getBoolean("Fire").orElse(config.startWithFireBreath);
            abilityUnlocks.chargeUnlocked =
                    unlocks.getBoolean("Charge").orElse(config.startWithCharge);
            abilityUnlocks.glideUnlocked =
                    unlocks.getBoolean("Glide").orElse(config.startWithGlide);
            abilityUnlocks.flightUnlocked = unlocks.getBoolean("Flight").orElse(false);
            abilityUnlocks.timeSlowUnlocked = unlocks.getBoolean("TimeSlow").orElse(false);
            abilityUnlocks.superChargeUnlocked = unlocks.getBoolean("SuperCharge").orElse(false);
        } else {
            abilityUnlocks.applyDefaults(config);
        }
    }
}
