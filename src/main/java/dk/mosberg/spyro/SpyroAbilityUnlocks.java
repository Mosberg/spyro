package dk.mosberg.spyro;

/**
 * Tracks player ability unlocks and progression. Determines which abilities are available to the
 * player.
 */
public class SpyroAbilityUnlocks {
    public boolean fireBreathUnlocked = true;
    public boolean chargeUnlocked = false;
    public boolean glideUnlocked = false;
    public boolean flightUnlocked = false;
    public boolean timeSlowUnlocked = false;
    public boolean superChargeUnlocked = false;

    public SpyroAbilityUnlocks() {
        applyDefaults(SpyroConfig.get());
    }

    public SpyroAbilityUnlocks(SpyroConfig config) {
        applyDefaults(config);
    }

    public void applyDefaults(SpyroConfig config) {
        fireBreathUnlocked = config.startWithFireBreath;
        chargeUnlocked = config.startWithCharge;
        glideUnlocked = config.startWithGlide;
        flightUnlocked = false;
        timeSlowUnlocked = false;
        superChargeUnlocked = false;
    }

    public void unlockFireBreath() {
        fireBreathUnlocked = true;
    }

    public void unlockCharge() {
        chargeUnlocked = true;
    }

    public void unlockGlide() {
        glideUnlocked = true;
    }

    public void unlockFlight() {
        flightUnlocked = true;
    }

    public void unlockTimeSlow() {
        timeSlowUnlocked = true;
    }

    public void unlockSuperCharge() {
        superChargeUnlocked = true;
    }

    public int countUnlocked() {
        int count = 0;
        if (fireBreathUnlocked)
            count++;
        if (chargeUnlocked)
            count++;
        if (glideUnlocked)
            count++;
        if (flightUnlocked)
            count++;
        if (timeSlowUnlocked)
            count++;
        if (superChargeUnlocked)
            count++;
        return count;
    }

    public String getAbilityStatus() {
        return countUnlocked() + "/6 Abilities Unlocked";
    }
}
