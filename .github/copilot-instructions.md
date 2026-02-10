# Copilot Instructions (Spyro Fabric Mod)

## Remote Indexing

- The codebase is indexed remotely, so Copilot may not have access to all project files. If you encounter suggestions that reference missing classes or methods, please refer to the project structure and key files outlined below to understand the context and relationships between components.

[Remote Index External Link File](remote-index.md)

## Big picture architecture

- Split-sources Fabric mod for Minecraft 1.21.11: server logic in src/main/java, client-only UI/render in src/client/java.
- Main entrypoints: `Spyro` (server init) wires config, items, commands, networking, and server tick; `SpyroClient` (client init) wires keybinds, HUD, and payload registration.
- Ability flow: client sends C2S payloads (`AbilityStatePayload`, `FireBreathPayload`) via `SpyroClientNetworking` -> server receiver in `SpyroNetworking` -> `SpyroStateManager` -> `SpyroAbilities` applies effects each server tick.
- Progression/collectibles: `SpyroCollectibles` uses scoreboard objectives (gems/talismans/orbs) and inventory scans on server tick; totals feed HUD and stats UI.
- Config: `SpyroConfig` loads/saves JSON in config/spyro.json and provides gameplay + HUD toggles.

## Key files to reference

- Server entry: src/main/java/dk/mosberg/Spyro.java
- Client entry + HUD/keybinds: src/client/java/dk/mosberg/SpyroClient.java
- Networking: src/main/java/dk/mosberg/spyro/SpyroNetworking.java and payload records in src/main/java/dk/mosberg/spyro
- Ability system: src/main/java/dk/mosberg/spyro/SpyroAbilities.java, SpyroPlayerState.java, SpyroStateManager.java
- Collectibles/scoreboard: src/main/java/dk/mosberg/spyro/SpyroCollectibles.java
- Config: src/main/java/dk/mosberg/spyro/SpyroConfig.java
- Screens (client UI): src/client/java/dk/mosberg/spyro/\*Screen.java

## Project-specific patterns and conventions

- Use Fabric `PayloadTypeRegistry.playC2S()` for C2S payload registration; ensure registration happens once (`SpyroNetworking.registerPayloads()`).
- Ability inputs are edge-triggered on client and sent only when state changes to reduce network traffic.
- Server tick is authoritative for ability effects and collectible auto-collection.
- Scoreboard objectives are created lazily via `SpyroCollectibles.ensureObjectives` and guarded on access.
- Use `Spyro.SPYRO_TAG` command tag to gate Spyro-only logic (HUD, abilities).

## Developer workflows

- Build/run with Gradle wrapper (JDK 21 required).
- Common dev run: `./gradlew runClient` (Fabric Loom). Use Gradle wrapper on Windows: `./gradlew.bat runClient`.

## Integration points

- Fabric API events: server tick (`ServerTickEvents.END_SERVER_TICK`), server lifecycle, and client tick.
- HUD registration uses `HudElementRegistry` and `VanillaHudElements.MISC_OVERLAYS`.

## Guidance for changes

- Keep client-only code in src/client to preserve split-sources.
- When adding abilities, update payloads + `SpyroPlayerState` + `SpyroAbilities` + client input wiring.
- When adding collectibles, extend `CollectibleItem` and update config defaults in `SpyroConfig` plus scoreboard handling in `SpyroCollectibles`.
