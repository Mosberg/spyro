package dk.mosberg.spyro;

import dk.mosberg.Spyro;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Manages per-player Spyro stats attachment.
 */
public class SpyroStatsAttachment {
    public static final AttachmentType<SpyroPlayerStats> STATS = AttachmentRegistry
            .createPersistent(Identifier.of(Spyro.MOD_ID, "spyro_stats"), SpyroPlayerStats.CODEC);

    public static SpyroPlayerStats get(PlayerEntity player) {
        return player.getAttachedOrCreate(STATS, SpyroPlayerStats::new);
    }

    public static void register() {
        // No-op, registration happens automatically
    }
}
