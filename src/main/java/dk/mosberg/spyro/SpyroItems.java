package dk.mosberg.spyro;

import java.util.Objects;
import org.jspecify.annotations.NonNull;
import dk.mosberg.Spyro;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class SpyroItems {
    // Gems (Red family)
    public static final GemItem RED_GEM = registerGem("red_gem", "red", 1);
    public static final GemItem GREEN_GEM = registerGem("green_gem", "green", 2);
    public static final GemItem PURPLE_GEM = registerGem("purple_gem", "purple", 5);

    // Gems (Bonus colors)
    public static final GemItem ORANGE_GEM = registerGem("orange_gem", "orange", 3);
    public static final GemItem BLUE_GEM = registerGem("blue_gem", "blue", 4);
    public static final GemItem YELLOW_GEM = registerGem("yellow_gem", "yellow", 2);

    // Talismans (powerful protective artifacts)
    public static final CollectibleItem RED_TALISMAN = registerTalisman("red_talisman", "red", 10);
    public static final CollectibleItem GREEN_TALISMAN =
            registerTalisman("green_talisman", "green", 15);
    public static final CollectibleItem BLUE_TALISMAN =
            registerTalisman("blue_talisman", "blue", 20);

    // Orbs (magical energy containers)
    public static final CollectibleItem DRAGONFLY_ORB =
            registerOrb("dragonfly_orb", "dragonfly", 5);
    public static final CollectibleItem CRYSTAL_ORB = registerOrb("crystal_orb", "crystal", 8);
    public static final CollectibleItem SHADOW_ORB = registerOrb("shadow_orb", "shadow", 12);

    private SpyroItems() {}

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(Objects.requireNonNull(ItemGroups.INGREDIENTS))
                .register(entries -> {
                    // Gems
                    entries.add(RED_GEM);
                    entries.add(GREEN_GEM);
                    entries.add(PURPLE_GEM);
                    entries.add(ORANGE_GEM);
                    entries.add(BLUE_GEM);
                    entries.add(YELLOW_GEM);
                    // Talismans
                    entries.add(RED_TALISMAN);
                    entries.add(GREEN_TALISMAN);
                    entries.add(BLUE_TALISMAN);
                    // Orbs
                    entries.add(DRAGONFLY_ORB);
                    entries.add(CRYSTAL_ORB);
                    entries.add(SHADOW_ORB);
                });
    }

    private static GemItem registerGem(String id, String key, int fallbackValue) {
        Identifier identifier = Identifier.of(Spyro.MOD_ID, id);
        Item.Settings settings =
                new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier));
        return Registry.register(getItemRegistry(), identifier,
                new GemItem(settings, key, fallbackValue));
    }

    private static CollectibleItem registerTalisman(String id, String key, int fallbackValue) {
        Identifier identifier = Identifier.of(Spyro.MOD_ID, id);
        Item.Settings settings =
                new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier));
        return Registry.register(getItemRegistry(), identifier,
                new CollectibleItem(settings, key, "talismans", fallbackValue));
    }

    private static CollectibleItem registerOrb(String id, String key, int fallbackValue) {
        Identifier identifier = Identifier.of(Spyro.MOD_ID, id);
        Item.Settings settings =
                new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier));
        return Registry.register(getItemRegistry(), identifier,
                new CollectibleItem(settings, key, "orbs", fallbackValue));
    }

    @SuppressWarnings("unchecked")
    private static Registry<@NonNull Item> getItemRegistry() {
        return (Registry<@NonNull Item>) (Registry<?>) Registries.ITEM;
    }
}
