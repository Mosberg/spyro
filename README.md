# Spyro in Minecraft - Fabric Mod (1.21.11)

Bring Spyro-inspired movement and collectibles to Minecraft 1.21.11 using Fabric.

This repo uses:

- Fabric Loader and Fabric API
- Yarn mappings
- Split-sources project structure
- Java 21

## Scope

This codebase currently targets a core Spyro 1-inspired mechanics slice on vanilla worlds. It is not a full content conversion (no custom worlds, mobs, bosses, or story).

## Features (Implemented)

### Abilities

- Fire breath (cooldown, range, damage, burn time)
- Charge (knockback, damage, hit cooldown, charge speed)
- Glide (max fall speed clamp)
- Server-authoritative ability execution with client input sync

### Collectibles

- 12 collectible items:
  - Gems: red, green, yellow, orange, blue, purple
  - Talismans: red, green, blue
  - Orbs: dragonfly, crystal, shadow
- Scoreboard-backed totals and weighted score
- Auto-collection from inventory (configurable)
- HUD display of counts and total score

### Progression

- Ability unlocks tied to gem totals (configurable)
- Optional progression tracking
- Player stats stored via attachments

### HUD

- Spyro HUD with icon, keybind hints, collectibles, score
- Configurable offsets and display toggles
- Optional realm label

### Commands

- /spyro, /spyro on, /spyro off, /spyro toggle
- /spyro reload
- /spyro give gems|talismans|orbs <amount>
- /spyro unlock fire|charge|glide
- /spyro reset

## Default Controls

- Fire Breath: R
- Charge: V
- Glide: G
- Menu: M
- Stats: L

## Configuration

Config file: config/spyro.json

Ability tuning:

- fireCooldownTicks
- fireRange
- fireDamage
- fireBurnSeconds
- chargeDamage
- chargeKnockback
- chargeSpeed
- chargeHitCooldownTicks
- glideMaxFallSpeed

HUD and display:

- enableHudDisplay
- enableStatsTracking
- hudOffsetX
- hudOffsetY
- hudShowAbilityInfo
- hudShowLevelName

Collectibles and gameplay:

- enableAutoCollect
- enableProgressTracking
- collectSoundVolume
- enableParticles
- difficultyMultiplier

Progression defaults and unlocks:

- startWithFireBreath
- startWithCharge
- startWithGlide
- unlockFireBreathGems
- unlockChargeGems
- unlockGlideGems

Collectible values:

- gems, talismans, orbs (maps keyed by item color/type)
- gemValues (legacy compatibility)

## Scoreboard Objectives

- spyro_gems
- spyro_talismans
- spyro_orbs

Weighted total score:

Total = Gems + (Talismans _ 10) + (Orbs _ 8)

## Build and Run

Requirements:

- JDK 21+

Commands:

```
./gradlew.bat build
./gradlew.bat runClient
```

## Project Structure (Split Sources)

```
src/
  main/
    java/        Core mod code
    resources/   Assets and data
  client/
    java/        Client-only code (HUD, screens, keybinds)
    resources/
```

## Install (Players)

1. Install Fabric Loader for Minecraft 1.21.11
2. Install Fabric API
3. Drop the mod jar into mods/
4. Launch Minecraft

## Compatibility

- Fabric only
- Not compatible with Forge or NeoForge
- May conflict with mods that heavily alter player movement or networking

## Contributing

PRs are welcome for:

- HUD and UI polish
- Ability tuning and balance
- Collectible and progression improvements
- Localization and documentation

## License

Choose a license appropriate for your project and place it in LICENSE.
