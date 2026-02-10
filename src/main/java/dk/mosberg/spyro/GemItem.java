package dk.mosberg.spyro;

public class GemItem extends CollectibleItem {
    public GemItem(Settings settings, String gemKey, int defaultValue) {
        super(settings, gemKey, "gems", defaultValue);
    }
}
