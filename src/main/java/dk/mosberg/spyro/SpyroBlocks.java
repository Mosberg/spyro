package dk.mosberg.spyro;

import org.slf4j.LoggerFactory;
import dk.mosberg.Spyro;

public final class SpyroBlocks {
    public static final Object SPYRO_PORTAL = null;
    public static final Object FLIGHT_FINISH_LINE = null;

    private SpyroBlocks() {}

    public static void register() {
        // Block registration disabled for now - requires Fabric Registry API setup
        // TODO: Re-implement using proper Fabric block registration pattern
        LoggerFactory.getLogger(Spyro.MOD_ID).info("Block registration deferred");
    }
}
