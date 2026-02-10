# Implementation Guide: Adding Spyro Features

## Quick Reference: What Was Added

### New Classes

1. **CollectibleItem.java** - Generic collectible base class
2. **SpyroCollectibles.java** - Unified collectibles management system

### Updated Classes

- **GemItem.java** - Refactored to extend CollectibleItem
- **SpyroItems.java** - Added 12 new items (6 gems, 3 talismans, 3 orbs)
- **SpyroConfig.java** - Extended with gem/talisman/orb value maps
- **SpyroGems.java** - Converted to legacy wrapper
- **SpyroStateManager.java** - Uses new SpyroCollectibles
- **Spyro.java** - Updated initialization
- **SpyroClient.java** - Enhanced HUD with multi-line display

### New Assets

- 9 item models (JSON) for new collectibles
- 9 texture files (PNG) for new collectibles
- Updated localization with all new item names

## Adding More Collectible Types

### Step 1: Create Item Instance

```java
// In SpyroItems.java
public static final CollectibleItem MY_COLLECTIBLE =
    registerCustom("my_item", "my_key", "my_type", 50);
```

### Step 2: Add Registration Method

```java
private static CollectibleItem registerCustom(String id, String key, String type, int value) {
    Identifier identifier = Identifier.of(Spyro.MOD_ID, id);
    Item.Settings settings = new Item.Settings()
        .registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier));
    return Registry.register(getItemRegistry(), identifier,
        new CollectibleItem(settings, key, type, value));
}
```

### Step 3: Register in Item Group

```java
public static void register() {
    ItemGroupEvents.modifyEntriesEvent(Objects.requireNonNull(ItemGroups.INGREDIENTS))
        .register(entries -> {
            // ... existing items ...
            entries.add(MY_COLLECTIBLE);
        });
}
```

### Step 4: Update Config Defaults

```java
// In SpyroConfig.java constructor
public void initializeDefaults() {
    // ... existing code ...

    // Create new map if needed
    Map<String, Integer> myType = new HashMap<>();
    myType.put("my_key", 50);
    // Store in field for serialization
}
```

### Step 5: Create Texture

```powershell
# Copy an existing texture as template
Copy-Item red_gem.png my_item.png
```

### Step 6: Create Item Model JSON

```json
{
  "parent": "item/generated",
  "textures": {
    "layer0": "spyro:item/my_item"
  }
}
```

### Step 7: Add Localization

```json
{
  "item.spyro.my_item": "My Item Name"
}
```

### Step 8: Update HUD (Optional)

```java
// In SpyroClient.java renderHud()
int myCount = SpyroCollectibles.getCollectibleCount(
    scoreboard, player, "my_type");
drawContext.drawTextWithShadow(client.textRenderer,
    Text.literal("My Type: " + myCount), x, y + offset, 0xFFCCCCCC);
```

## Modifying Collectible Values

Edit `config/spyro.json`:

```json
{
  "gems": { ... },
  "talismans": { ... },
  "orbs": { ... },
  "myType": {
    "my_key": 75  // Update value
  }
}
```

Changes take effect on world reload (config is loaded on server start).

## Advanced: Custom Collection Logic

To add custom behavior when items are collected:

1. Override `collectFromInventory()` in a custom manager class
2. Add event hooks for collection triggers
3. Add particle effects via `SpawnParticleS2CPacket`
4. Add sound effects via `ServerWorld.playSound()`

Example:

```java
public static void collectFromInventory(ServerPlayerEntity player) {
    // ... existing logic ...

    // Add custom effect
    if (gems > 0) {
        ServerWorld world = (ServerWorld) player.getEntityWorld();
        world.spawnParticles(ParticleTypes.HAPPY_VILLAGER,
            player.getX(), player.getY() + 1, player.getZ(),
            10, 0.5, 0.5, 0.5, 0.1);
    }
}
```

## Testing New Features

1. **Build**: `./gradlew clean build`
2. **Run**: `./gradlew runClient`
3. **Test**:
   - Creative mode: search for item name
   - Verify item appears in inventory
   - Check item appears in INGREDIENTS tab
   - Use `/give` command to test collection: `/give @s spyro:my_item`
   - Verify HUD updates with new count
4. **Config**: Edit `config/spyro.json` and reload world

## Performance Considerations

- Inventory scanning occurs every server tick (once per player)
- Scoreboard operations are optimized by Minecraft
- Collectible type switching uses efficient switch statement
- Consider batch updates for large quantities

## Debugging

### Check Console Logs

```
Spyro mod initialized with gems, talismans, and orbs.
```

### Verify Config Loading

```
config/spyro.json should exist in minecraft config directory
```

### Test Collectible Creation

```bash
./gradlew build  # Should complete with no errors
```

### Inspect Item Registration

- Creative inventory search: `@spyro`
- Should show all registered items

## API Reference

### SpyroCollectibles Methods

- `ensureObjectives(MinecraftServer)` - Initialize scoreboard objectives
- `collectFromInventory(ServerPlayerEntity)` - Scan and collect items
- `addCollectible(ServerPlayerEntity, String objectiveId, int amount)` - Add points
- `getCollectibleCount(Scoreboard, ScoreHolder, String objectiveId)` - Get count
- `getGemCount(Scoreboard, ScoreHolder)` - Get gem count specifically
- `getTalismanCount(Scoreboard, ScoreHolder)` - Get talisman count
- `getOrbCount(Scoreboard, ScoreHolder)` - Get orb count
- `getTotalScore(Scoreboard, ScoreHolder)` - Get weighted total

### SpyroConfig Methods

- `get()` - Get singleton instance
- `load()` - Load from file (called on mod init)
- `save()` - Save to file
- `getCollectibleValue(String type, String key, int fallback)` - Get item value

## Common Issues & Solutions

| Issue                            | Solution                                          |
| -------------------------------- | ------------------------------------------------- |
| Item doesn't appear in inventory | Check registration in `register()` method         |
| Item doesn't collect             | Verify `CollectibleItem` type in instanceof check |
| HUD doesn't show                 | Check `SpyroClient.renderHud()` is called         |
| Config not loading               | Verify JSON syntax in `config/spyro.json`         |
| Missing texture                  | Ensure PNG file exists in textures/item/          |
