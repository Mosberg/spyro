package dk.mosberg.spyro;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.mosberg.Spyro;
import net.fabricmc.loader.api.FabricLoader;

public final class SpyroConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "spyro.json";
    private static SpyroConfig instance = new SpyroConfig();

    // Ability Settings
    public int fireCooldownTicks = 20;
    public int fireRange = 4;
    public float fireDamage = 4.0f;
    public int fireBurnSeconds = 4;
    public float chargeDamage = 6.0f;
    public float chargeKnockback = 0.6f;
    public float chargeSpeed = 0.45f;
    public int chargeHitCooldownTicks = 6;
    public double glideMaxFallSpeed = -0.08;

    // Collectible Values
    public Map<String, Integer> gems = new HashMap<>();
    public Map<String, Integer> talismans = new HashMap<>();
    public Map<String, Integer> orbs = new HashMap<>();

    // GUI/Display Settings
    public boolean enableHudDisplay = true;
    public boolean enableStatsTracking = true;
    public int hudOffsetX = 8;
    public int hudOffsetY = 8;
    public boolean hudShowAbilityInfo = true;
    public boolean hudShowLevelName = true;

    // Gameplay Settings
    public boolean enableAutoCollect = true;
    public boolean enableProgressTracking = true;
    public int collectSoundVolume = 1;
    public boolean enableParticles = true;
    public int difficultyMultiplier = 1;

    // Planned Content Toggles
    public boolean enableSpyroWorlds = false;
    public boolean enableSpyroMobs = false;
    public boolean enablePortals = false;
    public boolean enableFlightLevels = false;
    public boolean enableNpcHelpers = false;

    // Progression Settings
    public boolean startWithFireBreath = true;
    public boolean startWithCharge = false;
    public boolean startWithGlide = false;
    public int unlockFireBreathGems = 0;
    public int unlockChargeGems = 50;
    public int unlockGlideGems = 120;

    // Legacy support
    public Map<String, Integer> gemValues = new HashMap<>();

    public SpyroConfig() {
        initializeDefaults();
    }

    private void initializeDefaults() {
        // Gems
        gems.put("red", 1);
        gems.put("green", 2);
        gems.put("purple", 5);
        gems.put("orange", 3);
        gems.put("blue", 4);
        gems.put("yellow", 2);

        // Talismans (higher value)
        talismans.put("red", 10);
        talismans.put("green", 15);
        talismans.put("blue", 20);

        // Orbs (magical energy containers)
        orbs.put("dragonfly", 5);
        orbs.put("crystal", 8);
        orbs.put("shadow", 12);

        // Legacy support
        gemValues.put("red", 1);
        gemValues.put("green", 2);
        gemValues.put("purple", 5);
    }

    public static SpyroConfig get() {
        return instance;
    }

    public static void load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
        if (Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                SpyroConfig loaded = GSON.fromJson(reader, SpyroConfig.class);
                if (loaded != null) {
                    loaded.applyDefaults();
                    instance = loaded;
                }
            } catch (IOException ex) {
                Spyro.LOGGER.warn("Failed to read Spyro config, using defaults.", ex);
            }
        } else {
            save();
        }
    }

    public static void save() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
        try {
            Files.createDirectories(configPath.getParent());
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                GSON.toJson(instance, writer);
            }
        } catch (IOException ex) {
            Spyro.LOGGER.warn("Failed to write Spyro config.", ex);
        }
    }

    public int getCollectibleValue(String type, String key, int fallback) {
        Map<String, Integer> typeMap = switch (type) {
            case "gems" -> gems;
            case "talismans" -> talismans;
            case "orbs" -> orbs;
            default -> null;
        };

        if (typeMap == null || typeMap.isEmpty()) {
            return fallback;
        }
        return typeMap.getOrDefault(key, fallback);
    }

    public int getGemValue(String key, int fallback) {
        if (gems == null || gems.isEmpty()) {
            return fallback;
        }
        return gems.getOrDefault(key, fallback);
    }

    private void applyDefaults() {
        if (gems == null || gems.isEmpty()) {
            gems = new HashMap<>();
            gems.put("red", 1);
            gems.put("green", 2);
            gems.put("purple", 5);
            gems.put("orange", 3);
            gems.put("blue", 4);
            gems.put("yellow", 2);
        }

        if (talismans == null || talismans.isEmpty()) {
            talismans = new HashMap<>();
            talismans.put("red", 10);
            talismans.put("green", 15);
            talismans.put("blue", 20);
        }

        if (orbs == null || orbs.isEmpty()) {
            orbs = new HashMap<>();
            orbs.put("dragonfly", 5);
            orbs.put("crystal", 8);
            orbs.put("shadow", 12);
        }

        if (gemValues == null || gemValues.isEmpty()) {
            gemValues = new HashMap<>();
            gemValues.put("red", 1);
            gemValues.put("green", 2);
            gemValues.put("purple", 5);
        }

        enableSpyroWorlds = false;
        enableSpyroMobs = false;
        enablePortals = false;
        enableFlightLevels = false;
        enableNpcHelpers = false;
    }
}
