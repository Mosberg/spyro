package dk.mosberg.spyro;

import org.jspecify.annotations.NonNull;
import dk.mosberg.Spyro;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class SpyroEntities {
    public static final EntityType<RhynocEntity> RHYNOC = Registry.register(getEntityRegistry(),
            Identifier.of(Spyro.MOD_ID, "rhynoc"),
            EntityType.Builder.<RhynocEntity>create(RhynocEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.9f, 1.3f).maxTrackingRange(16).build(RegistryKey
                            .of(RegistryKeys.ENTITY_TYPE, Identifier.of(Spyro.MOD_ID, "rhynoc"))));

    private SpyroEntities() {}

    public static void register() {
        // Static initializers handle registration
    }

    @SuppressWarnings("unchecked")
    private static Registry<@NonNull EntityType<?>> getEntityRegistry() {
        return (Registry<@NonNull EntityType<?>>) (Registry<?>) Registries.ENTITY_TYPE;
    }
}
