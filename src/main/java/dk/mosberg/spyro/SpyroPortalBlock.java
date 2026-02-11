package dk.mosberg.spyro;

import java.util.Collections;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Portal block that transports players to Spyro-themed dimensions. Includes flight level dimension
 * access and time trial initiation.
 */
public class SpyroPortalBlock extends Block {
    private final String destinationLevel; // "flight_level_1", etc.

    public SpyroPortalBlock(Settings settings, String destinationLevel) {
        super(settings);
        this.destinationLevel = destinationLevel;
    }

    public SpyroPortalBlock(Settings settings) {
        this(settings, "flight_level_1");
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return ActionResult.FAIL;
        }

        SpyroConfig config = SpyroConfig.get();
        if (!config.enableFlightLevels) {
            player.sendMessage(Text.literal("§cFlight levels are not yet enabled in config."),
                    false);
            return ActionResult.FAIL;
        }

        ServerWorld currentWorld = (ServerWorld) world;
        ServerWorld targetWorld = currentWorld.getServer()
                .getWorld(net.minecraft.registry.RegistryKey.of(
                        net.minecraft.registry.RegistryKeys.WORLD,
                        SpyroDimensions.FLIGHT_LEVEL_ID));

        if (targetWorld == null) {
            player.sendMessage(Text.literal("§cFlight dimension not found. Create it first."),
                    false);
            return ActionResult.FAIL;
        }

        // Teleport player to flight level
        serverPlayer.teleport(targetWorld, 0.5, 100.5, 0.5, Collections.emptySet(),
                serverPlayer.getYaw(), serverPlayer.getPitch(), false);
        player.sendMessage(
                Text.literal("§6Entered " + destinationLevel + "! Complete the flight trial!"),
                false);

        // Start time trial
        SpyroTimeTrial.startTrial(serverPlayer, destinationLevel);

        return ActionResult.SUCCESS;
    }
}
