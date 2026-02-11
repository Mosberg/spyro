package dk.mosberg.spyro;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Finish line block for flight level time trials. Completes the trial when a player touches it.
 */
public class FlightFinishLineBlock extends Block {
    public FlightFinishLineBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);

        if (world.isClient()) {
            return;
        }

        if (entity instanceof ServerPlayerEntity player) {
            SpyroTimeTrial.FlightSessionData session = SpyroTimeTrial.getSession(player);
            if (session != null && !session.completed) {
                SpyroTimeTrial.completeTrial(player);
            }
        }
    }
}
