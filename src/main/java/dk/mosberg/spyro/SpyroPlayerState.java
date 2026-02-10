package dk.mosberg.spyro;

public class SpyroPlayerState {
    private boolean charging;
    private boolean gliding;
    private boolean fireRequested;
    private int fireCooldown;
    private int chargeHitCooldown;

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public boolean isGliding() {
        return gliding;
    }

    public void setGliding(boolean gliding) {
        this.gliding = gliding;
    }

    public void requestFire() {
        this.fireRequested = true;
    }

    public boolean consumeFireRequest() {
        if (!fireRequested) {
            return false;
        }
        fireRequested = false;
        return true;
    }

    public int getFireCooldown() {
        return fireCooldown;
    }

    public void setFireCooldown(int fireCooldown) {
        this.fireCooldown = fireCooldown;
    }

    public int getChargeHitCooldown() {
        return chargeHitCooldown;
    }

    public void setChargeHitCooldown(int chargeHitCooldown) {
        this.chargeHitCooldown = chargeHitCooldown;
    }

    public void tickCooldowns() {
        if (fireCooldown > 0) {
            fireCooldown--;
        }
        if (chargeHitCooldown > 0) {
            chargeHitCooldown--;
        }
    }

    public void clearInputs() {
        charging = false;
        gliding = false;
        fireRequested = false;
    }
}
