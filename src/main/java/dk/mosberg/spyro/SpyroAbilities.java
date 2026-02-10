package dk.mosberg.spyro;

import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class SpyroAbilities {

    public static void apply(ServerPlayerEntity player, SpyroPlayerState state) {
        if (state.consumeFireRequest()) {
            tryFireBreath(player, state);
        }
        if (state.isCharging()) {
            applyCharge(player, state);
        }
        if (state.isGliding()) {
            applyGlide(player);
        }
    }

    private static void tryFireBreath(ServerPlayerEntity player, SpyroPlayerState state) {
        SpyroConfig config = SpyroConfig.get();
        if (state.getFireCooldown() > 0) {
            return;
        }
        state.setFireCooldown(config.fireCooldownTicks);

        Vec3d direction = player.getRotationVec(1.0f);
        Vec3d origin = new Vec3d(player.getX(), player.getY() + player.getStandingEyeHeight(),
                player.getZ()).add(direction.multiply(0.5));
        spawnFireParticles(player, origin, direction);
        ServerWorld serverWorld = (ServerWorld) player.getEntityWorld();
        serverWorld.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT,
                SoundCategory.PLAYERS, 0.8f, 1.2f);

        double range = Math.max(1.0, config.fireRange);
        Box box = player.getBoundingBox().expand(range, 2.0, range).offset(direction.multiply(2.0));
        List<LivingEntity> targets = serverWorld.getEntitiesByClass(LivingEntity.class, box,
                entity -> entity != player && entity.isAlive());
        DamageSource source = serverWorld.getDamageSources().playerAttack(player);
        for (LivingEntity target : targets) {
            Vec3d toTarget = new Vec3d(target.getX(), target.getY() + target.getStandingEyeHeight(),
                    target.getZ()).subtract(origin);
            if (toTarget.lengthSquared() <= 0.0001) {
                continue;
            }
            Vec3d normalized = toTarget.normalize();
            double dot = normalized.dotProduct(direction);
            if (dot < 0.5) {
                continue;
            }
            target.setOnFireFor(config.fireBurnSeconds);
            target.damage(serverWorld, source, config.fireDamage);
        }
    }

    private static void spawnFireParticles(ServerPlayerEntity player, Vec3d origin,
            Vec3d direction) {
        ServerWorld serverWorld = (ServerWorld) player.getEntityWorld();
        for (int i = 0; i < 18; i++) {
            double spread = 0.2 + (i * 0.02);
            double offsetX = direction.x + (player.getRandom().nextGaussian() * spread);
            double offsetY = direction.y + (player.getRandom().nextGaussian() * spread);
            double offsetZ = direction.z + (player.getRandom().nextGaussian() * spread);
            serverWorld.spawnParticles(ParticleTypes.FLAME, origin.x, origin.y, origin.z, 1,
                    offsetX * 0.3, offsetY * 0.3, offsetZ * 0.3, 0.02);
        }
    }

    private static void applyCharge(ServerPlayerEntity player, SpyroPlayerState state) {
        SpyroConfig config = SpyroConfig.get();
        if (!player.isOnGround()) {
            return;
        }
        Vec3d forward = player.getRotationVec(1.0f).normalize();
        player.addVelocity(forward.x * 0.45, 0.0, forward.z * 0.45);
        player.setSprinting(true);

        if (state.getChargeHitCooldown() > 0) {
            return;
        }
        Box box = player.getBoundingBox().expand(1.2, 0.6, 1.2).offset(forward.multiply(0.8));
        ServerWorld serverWorld = (ServerWorld) player.getEntityWorld();
        List<LivingEntity> targets = serverWorld.getEntitiesByClass(LivingEntity.class, box,
                entity -> entity != player && entity.isAlive());
        if (!targets.isEmpty()) {
            DamageSource source = serverWorld.getDamageSources().playerAttack(player);
            for (LivingEntity target : targets) {
                target.damage(serverWorld, source, config.chargeDamage);
                target.takeKnockback(config.chargeKnockback, forward.x, forward.z);
            }
            state.setChargeHitCooldown(config.chargeHitCooldownTicks);
            serverWorld.playSound(null, player.getBlockPos(),
                    SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 0.7f, 0.9f);
        }
    }

    private static void applyGlide(ServerPlayerEntity player) {
        if (player.isOnGround()) {
            return;
        }
        SpyroConfig config = SpyroConfig.get();
        Vec3d velocity = player.getVelocity();
        double clampedY = Math.max(velocity.y, config.glideMaxFallSpeed);
        player.setVelocity(velocity.x, clampedY, velocity.z);
        player.fallDistance = 0.0f;
    }
}
