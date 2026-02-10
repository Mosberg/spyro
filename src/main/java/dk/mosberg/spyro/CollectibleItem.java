package dk.mosberg.spyro;

import net.minecraft.item.Item;

/**
 * Base class for collectible items (gems, talismans, orbs). Provides common interface for items
 * that contribute to player progression.
 */
public class CollectibleItem extends Item {
    private final String collectibleKey;
    private final String collectibleType;
    private final int defaultValue;

    public CollectibleItem(Settings settings, String key, String type, int defaultValue) {
        super(settings);
        this.collectibleKey = key;
        this.collectibleType = type;
        this.defaultValue = defaultValue;
    }

    public int getValue() {
        return SpyroConfig.get().getCollectibleValue(collectibleType, collectibleKey, defaultValue);
    }

    public String getCollectibleKey() {
        return collectibleKey;
    }

    public String getCollectibleType() {
        return collectibleType;
    }
}
