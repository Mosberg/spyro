package dk.mosberg.spyro;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * Spyro enemy: Charging Rhynoc with enhanced AI behaviors. - Charges at players aggressively -
 * Counterattacks when hit - Flees from stronger threats
 */
public class RhynocEntity extends net.minecraft.entity.mob.PathAwareEntity {
    private int chargeTicksRemaining = 0;
    private static final int CHARGE_DURATION = 40;

    public RhynocEntity(EntityType<? extends RhynocEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(net.minecraft.entity.data.DataTracker.Builder builder) {
        super.initDataTracker(builder);
        // Set health after initialization
        if (this.getHealth() <= 0) {
            this.setHealth(20.0f);
        }
    }

    @Override
    protected void initGoals() {
        // Priority 0: Panic/flee from strong threats
        this.goalSelector.add(0, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0f, 1.3, 1.5));

        // Priority 1: Melee attack with charging
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.3, true));

        // Priority 2: Look at nearby entities
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));

        // Priority 3: Wander around
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8));

        // Priority 4: Look around
        this.goalSelector.add(4, new LookAroundGoal(this));

        // Target goals
        this.targetSelector.add(0, new RevengeGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        // Charge mechanic: accelerate when targeting player
        if (this.getTarget() != null && this.getTarget() instanceof PlayerEntity) {
            this.chargeTicksRemaining = CHARGE_DURATION;
            this.setMovementSpeed(1.8f); // Fast charge speed
        } else if (chargeTicksRemaining > 0) {
            chargeTicksRemaining--;
            this.setMovementSpeed(1.3f); // Momentum decay
        } else {
            this.setMovementSpeed(0.7f); // Normal idle speed
        }
    }
}
