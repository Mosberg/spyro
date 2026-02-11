package dk.mosberg.spyro;

import net.minecraft.util.Identifier;

/**
 * Flight dimension world keys and dimension identifiers.
 */
public class SpyroDimensions {
    public static final Identifier FLIGHT_LEVEL_ID = Identifier.of("spyro", "flight_level");

    private SpyroDimensions() {}

    public static void initialize() {
        // Called on server startup to ensure dimensions are loaded
    }
}
