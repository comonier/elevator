# 🛗 Elevator Plugin v1.0
**Created by: comonier**

A simple and efficient elevator plugin for Minecraft **1.21.1**. Use configurable blocks to teleport vertically with custom sounds and Discord integration.

## 🚀 Features
- **Vertical Teleportation:** Press `Jump` to go up and `Sneak` to go down between matching blocks.
- **Customizable Blocks:** Define which blocks act as elevators via `config.yml`.
- **Global Announcement:** Notifies the server when a new elevator (with 2 or more floors) is created.
- **Discord Integration:** Sends an alert via Webhook when a valid elevator is detected.
- **Custom Sounds:** Different sound effects for going up and down (fully configurable).
- **Multi-language Support:** Native support for English (`en`) and Portuguese (`pt`).

## 🛠 Commands
- `/elevator` - Shows the help menu, version, and creator info.
- `/elevator reload` - Reloads the configuration and message files.

## 🔑 Permissions
- `elevator.use` - Allows using elevators (Default: Everyone, unless `require-permission` is set to true in config).
- `elevator.admin` - Allows using the reload command (Default: OP).

## 📂 Installation
1. Place the `Elevator-1.0.jar` file in your server's `plugins` folder.
2. Start the server to generate the configuration files.
3. Edit `config.yml` to set your language, blocks, and Discord Webhook URL.
4. Use `/elevator reload` to apply the changes.

## 📋 Height Reference (Y)
- **Overworld:** -64 to 320
- **Nether:** 0 to 128
- **The End:** 0 to 256
