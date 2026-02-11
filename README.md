# Spyro in Minecraft - Fabric Mod (1.21.11)

Bring Spyro-inspired movement and collectibles to Minecraft 1.21.11 using Fabric.

This repo uses:

- Fabric Loader and Fabric API
- Yarn mappings
- Split-sources project structure
- Java 21

## Scope

This codebase targets Spyro 1-3 mechanics with expandable new content and progression systems on vanilla worlds, plus new dimensions (flight levels). It is not a full story campaign, but a foundation for Spyro-inspired gameplay.

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

### UI & Settings

- Spyro HUD with icon, keybind hints, collectibles, score
- Spyro Menu with Stats, Abilities, and Settings screens
- Settings screen with ability tuning, HUD toggles, and planned content toggles
- Configurable HUD offsets and display options
- Optional realm label

### Commands

- /spyro, /spyro on, /spyro off, /spyro toggle
- /spyro reload
- /spyro give gems|talismans|orbs <amount>
- /spyro unlock fire|charge|glide
- /spyro reset

### Blocks & Entities (NEW)

- Spyro Portal block (scaffold for future dimension travel)
- Rhynoc enemy mob (basic charging behavior framework)

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

Planned content toggles:

- enableSpyroWorlds (WIP)
- enableSpyroMobs (WIP)
- enablePortals (WIP)
- enableFlightLevels (WIP)
- enableNpcHelpers (WIP)

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
    java/        Core mod code (abilities, collectibles, commands, blocks, entities)
    resources/   Assets and data
  client/
    java/        Client-only code (HUD, screens, keybinds)
    resources/
```

## Roadmap

### Phase 1: Core Mechanics (Complete)

- [x] Fire breath, charge, glide
- [x] Gems, talismans, orbs
- [x] Inventory auto-collection
- [x] Ability unlocks via progression
- [x] HUD and keybinds
- [x] Stats and menu screens
- [x] Commands and config

### Phase 2: Expanded Content & UI (In Progress)

- [x] Settings screen with toggles
- [x] Portal block scaffold
- [x] Enemy mob framework (Rhynoc)
- [ ] Portal dimension travel
- [ ] Flight levels dimension
- [ ] Additional enemies and variants
- [ ] Dragon rescue/statues
- [ ] Time trials and challenges

### Phase 3: World & Story (Planned)

- [ ] Spyro-themed biomes and worlds
- [ ] NPC helpers and dialogue
- [ ] Worldgen populator for collectibles
- [ ] Boss encounters
- [ ] Advancement system

### Phase 4: Polish & Expansions (Future)

- [ ] Custom animations and models
- [ ] Additional abilities (time slow, super charge, flight)
- [ ] Spyro 2/3 mechanics (talismans, orbs, light gems)
- [ ] Localization

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
- Enemy AI and mob variants
- World design and content

## License

Choose a license appropriate for your project and place it in LICENSE.

## Links

- Fabric Documentation: https://docs.fabricmc.net/develop/
- Spyro Wiki: https://spyro.fandom.com/wiki/Spyro_the_Dragon_(character)
- Minecraft Wiki: https://minecraft.wiki/
