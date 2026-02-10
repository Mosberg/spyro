# Spyro Mod - Expanded Features

## Overview

The Spyro mod has been significantly expanded with multiple collectible types, enhanced configuration system, and improved HUD display.

## New Collectible Types

### Gems (6 types)

- **Red Gem** - Value: 1 point (default)
- **Green Gem** - Value: 2 points
- **Purple Gem** - Value: 5 points
- **Orange Gem** - Value: 3 points
- **Blue Gem** - Value: 4 points
- **Yellow Gem** - Value: 2 points

### Talismans (3 types) - Powerful protective artifacts

- **Red Talisman** - Value: 10 points
- **Green Talisman** - Value: 15 points
- **Blue Talisman** - Value: 20 points

### Orbs (3 types) - Magical energy containers

- **Dragonfly Orb** - Value: 5 points
- **Crystal Orb** - Value: 8 points
- **Shadow Orb** - Value: 12 points

## System Architecture

### CollectibleItem (Base Class)

- Generic base class for all collectible items
- Supports multiple collectible types (gems, talismans, orbs)
- Configurable values per item
- Automatic inventory collection and conversion to scoreboard scores

### SpyroCollectibles (Management System)

- Handles all collectible types uniformly
- Manages three separate Scoreboard objectives:
  - `spyro_gems` - Gem count tracking
  - `spyro_talismans` - Talisman count tracking
  - `spyro_orbs` - Orb count tracking
- Automatic inventory collection on server tick
- Sound effects on collection (experience orb pickup sound)

### Enhanced Configuration System

Config file: `config/spyro.json`

**Gem Values:**

```json
"gems": {
  "red": 1,
  "green": 2,
  "purple": 5,
  "orange": 3,
  "blue": 4,
  "yellow": 2
}
```

**Talisman Values:**

```json
"talismans": {
  "red": 10,
  "green": 15,
  "blue": 20
}
```

**Orb Values:**

```json
"orbs": {
  "dragonfly": 5,
  "crystal": 8,
  "shadow": 12
}
```

**Ability Settings:**

- `fireCooldownTicks`: Fire breath cooldown in ticks (default: 20)
- `fireRange`: Fire breath range in blocks (default: 4)
- `fireDamage`: Fire breath damage per tick (default: 4.0)
- `fireBurnSeconds`: Fire effect duration (default: 4)
- `chargeDamage`: Charge attack damage (default: 6.0)
- `chargeKnockback`: Charge knockback strength (default: 0.6)
- `chargeHitCooldownTicks`: Charge attack cooldown (default: 6)
- `glideMaxFallSpeed`: Maximum fall speed while gliding (default: -0.08)

## HUD Display

The enhanced HUD now shows:

1. **Spyro Mode** indicator (orange text)
2. **Keybinds** display (Fire: R, Charge: V, Glide: G)
3. **Gem Count** in cyan
4. **Talisman Count** in purple
5. **Orb Count** in light blue
6. **Total Score** in gold (calculated as: gems + talismans×10 + orbs×8)

## File Structure

```
src/main/java/dk/mosberg/spyro/
  ├── CollectibleItem.java (NEW) - Base class for all collectibles
  ├── GemItem.java (UPDATED) - Now extends CollectibleItem
  ├── SpyroItems.java (UPDATED) - 12 new items registered
  ├── SpyroCollectibles.java (NEW) - Universal collectible management
  ├── SpyroGems.java (UPDATED) - Legacy wrapper for backward compatibility
  ├── SpyroConfig.java (UPDATED) - Enhanced with gems/talismans/orbs maps
  └── SpyroStateManager.java (UPDATED) - Uses SpyroCollectibles

src/main/resources/assets/spyro/
  ├── models/item/ - 9 new JSON item models
  ├── textures/item/ - 9 new texture files
  └── lang/en_us.json (UPDATED) - 12 new item name translations

src/client/java/dk/mosberg/
  └── SpyroClient.java (UPDATED) - Multi-line HUD display
```

## Gameplay Flow

1. **Collecting Collectibles**: NPCs, blocks, or other sources drop gems, talismans, and orbs
2. **Inventory Detection**: Server tick event scans player inventory each tick
3. **Automatic Collection**: Matching items are removed and converted to points
4. **Scoreboards**: Points added to respective objectives (gems/talismans/orbs)
5. **HUD Display**: Real-time display of all collectible counts and total score
6. **Configuration**: Admin can tune values via `config/spyro.json` JSON file

## Creating Custom Collectibles

To add new collectible types, follow this pattern:

```java
// 1. Create item instance
public static final CollectibleItem CUSTOM_ITEM = registerCollectible("custom_item", "custom", "type", 25);

// 2. Add registration method
private static CollectibleItem registerCollectible(String id, String key, String type, int value) {
    Identifier identifier = Identifier.of(Spyro.MOD_ID, id);
    Item.Settings settings = new Item.Settings()
        .registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier));
    return Registry.register(getItemRegistry(), identifier,
        new CollectibleItem(settings, key, type, value));
}

// 3. Add to item group in register()
entries.add(CUSTOM_ITEM);

// 4. Update config with values
"type": { "custom": 25 }

// 5. Create texture and model JSON
src/main/resources/assets/spyro/textures/item/custom_item.png
src/main/resources/assets/spyro/models/item/custom_item.json

// 6. Add localization
"item.spyro.custom_item": "Custom Item"
```

## Configuration Examples

### Adjust Gem Values

```json
"gems": {
  "red": 5,
  "green": 10,
  "purple": 25,
  "orange": 7,
  "blue": 8,
  "yellow": 5
}
```

### Balance Talismans

```json
"talismans": {
  "red": 20,
  "green": 30,
  "blue": 50
}
```

### Tune Abilities from Config

Modify ability values in `spyro.json` and restart - all ability mechanics use `SpyroConfig.get()` for values.

## Backward Compatibility

- `SpyroGems` class maintained as legacy wrapper for `SpyroCollectibles`
- Existing fire/charge/glide abilities unchanged
- Config system supports both old `gemValues` and new typed maps (`gems`, `talismans`, `orbs`)

## Future Enhancement Ideas

1. **Gem Clusters** - Decorative/mineable blocks containing gems
2. **Talismans Effects** - Temporary stat boosts (increased fire damage, extended glide, etc.)
3. **Orb Crafting** - Recipes to create orbs from gems
4. **Progression Tracking** - Achievements for collecting milestone amounts
5. **Custom Particle Effects** - Visual feedback when collecting items
6. **Boss Drops** - Special rare collectibles from defeated enemies
7. **Level Themes** - World-specific gem types with unique properties
8. **Trading System** - Exchange collectibles for items/abilities

## Testing Checklist

- [x] Mod compiles without errors
- [x] Game client launches successfully
- [x] Player can join world
- [x] All 12 new items appear in creative inventory
- [ ] Items appear in INGREDIENTS tab
- [ ] Picking up items converts to scoreboard scores
- [ ] HUD displays all collectible types
- [ ] Config file generates on first run
- [ ] Ability values update from config
