package dk.mosberg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dk.mosberg.spyro.SpyroBlocks;
import dk.mosberg.spyro.SpyroCollectibles;
import dk.mosberg.spyro.SpyroCommand;
import dk.mosberg.spyro.SpyroConfig;
import dk.mosberg.spyro.SpyroEntities;
import dk.mosberg.spyro.SpyroItems;
import dk.mosberg.spyro.SpyroNetworking;
import dk.mosberg.spyro.SpyroStateManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class Spyro implements ModInitializer {
    public static final String MOD_ID = "spyro";
    public static final String SPYRO_TAG = "spyro_mode";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        SpyroConfig.load();
        SpyroItems.register();
        SpyroBlocks.register();
        SpyroEntities.register();
        SpyroCommand.register();
        SpyroNetworking.registerServer();
        ServerLifecycleEvents.SERVER_STARTED.register(SpyroCollectibles::ensureObjectives);
        ServerTickEvents.END_SERVER_TICK.register(SpyroStateManager::onServerTick);
        ServerPlayConnectionEvents.DISCONNECT.register(
                (handler, server) -> SpyroStateManager.remove(handler.getPlayer().getUuid()));

        LOGGER.info("Spyro mod initialized with gems, talismans, orbs, portals, and enemies.");
    }
}
