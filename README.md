# 🐉 Spyro in Minecraft — Fabric Mod (1.21.11)

Bring the magic, charm, and fire‑breathing attitude of **Spyro the Dragon** into **Minecraft 1.21.11** with this Fabric‑based mod.
This project recreates **Spyro**, his abilities, worlds, enemies, collectibles, and gameplay systems from the classic _Spyro the Dragon_ series, adapted faithfully into Minecraft’s sandbox environment.

This mod uses:

- **Fabric Loader** & **Fabric API**
- **Yarn mappings**
- **Split‑sources project structure**
- **Minecraft 1.21.11**

---

## 🌀 Features

### 🐉 Play as Spyro

- Fully animated Spyro model
- Glide, charge, and breathe fire
- Unique Spyro‑style movement physics
- Custom Spyro HUD elements (health gems, ability indicators)

### 🌍 Spyro Worlds

Recreated and reimagined realms inspired by the Spyro franchise, including:

- Artisan‑style hub worlds
- Themed realms with puzzles, enemies, and collectibles
- Portals connecting worlds, just like in the original games

### 💎 Collectibles

- **Gems** of all colors
- **Dragon Statues** (rescuable dragons)
- **Orbs**, **Eggs**, and other series‑specific items
- Custom UI for tracking progress

### 👾 Enemies & Bosses

- Classic Spyro enemies with custom AI
- Boss encounters adapted to Minecraft combat
- Enemy behaviors inspired by the original games (charging, fleeing, ranged attacks)

### 🔥 Spyro Abilities

- Fire breath
- Charge attack
- Glide & hover
- Swim (if applicable to your design)
- Ability upgrades depending on world progression

---

## ✅ Current Playable Slice

This initial build includes a basic Spyro mode with core abilities:

- Fire breath burst
- Charge sprint with knockback
- Glide to slow falling
- Simple HUD indicator
- Gem items and a gem counter

Use `/spyro` (or `/spyro on|off|toggle`) to enable Spyro mode for your player.

### 🎮 Default Controls

- Fire Breath: **R**
- Charge: **V**
- Glide: **G**

### ⚙️ Config

On first run, a config file is created at:

`config/spyro.json`

You can tune cooldowns, damage, glide speed, and gem values there.

### 🧪 Items & Blocks

- Spyro‑themed blocks
- Portal frames
- Gem clusters
- Decorative world assets
- Ability‑granting items

---

## 🛠️ Development Setup

This project uses **split‑sources** and **Yarn mappings** for clean organization and modding clarity.

### Requirements

- JDK 21+
- Gradle (wrapper included)
- Fabric Loader & Fabric API
- IntelliJ IDEA or VSCode recommended

### Cloning the Project

```bash
git clone https://github.com/yourname/spyro-fabric-mod.git
cd spyro-fabric-mod
```

### Importing Into Your IDE

1. Open the project as a **Gradle** project.
2. Let Gradle download dependencies.
3. Run the `fabric-client` or `fabric-server` run configs.

### Project Structure (Split‑Sources)

```
src/
 ├─ main/
 │   ├─ java/        # Core mod code
 │   ├─ resources/   # Assets, data, mixins
 ├─ client/
 │   ├─ java/        # Client-only code (renderers, models, HUD)
 │   ├─ resources/
 └─ generated/
     └─ ...          # Data generation output
```

---

## 📦 Installation (Players)

1. Install **Fabric Loader** for Minecraft 1.21.11
2. Install **Fabric API**
3. Drop the mod `.jar` into your `mods/` folder
4. Launch the game and enjoy exploring the Spyro universe

---

## 🧩 Compatibility

- Designed for **Fabric** only
- Not compatible with Forge or NeoForge
- Should work with most content mods unless they heavily modify player movement or dimension logic

---

## 📚 Lore & References

This mod draws inspiration from:

- **Spyro the Dragon** series (Insomniac Games, Toys for Bob)
- Spyro’s character design, abilities, and lore as described on:
  - Wikipedia: [https://en.wikipedia.org/wiki/Spyro](https://en.wikipedia.org/wiki/Spyro)
  - Spyro Wiki: `https://spyro.fandom.com/wiki/Spyro_the_Dragon_(character)` [(spyro.fandom.com in Bing)](https://www.bing.com/search?q="https%3A%2F%2Fspyro.fandom.com%2Fwiki%2FSpyro_the_Dragon_%28character%29")

All rights to Spyro belong to their respective owners. This is a fan‑made, non‑commercial project.

---

## 🤝 Contributing

Pull requests are welcome!
If you want to help with:

- Modeling & animation
- AI behavior
- World design
- Sound design
- Code cleanup
- Translations

…feel free to open an issue or PR.

---

## 📜 License

Choose a license appropriate for your project (MIT, LGPL, ARR, etc.).
Place the license text in `LICENSE`.

---

## 🐲 Final Notes

Spyro has always been about exploration, charm, and playful adventure.
This mod aims to bring that same energy into Minecraft while staying true to both games’ identities.

If you want, I can also generate:

- A full `fabric.mod.json`
- A roadmap or design document
