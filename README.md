# 🛗 Elevator Plugin v1.1
**Created by: comonier**

A simple and efficient elevator plugin for Minecraft **1.21.1**. Use configurable blocks to teleport vertically with custom sounds and Discord integration.

> [!IMPORTANT]  
> **UPGRADING FROM v1.0?**  
> You **MUST** delete the old plugin folder (`/plugins/Elevator`) before installing v1.1. This version introduces new configuration keys for detection sounds and cooldowns that are required for the plugin to function correctly.

## 🚀 Features
- **Vertical Teleportation:** Press `Jump` to go up and `Sneak` to go down between matching blocks.
- **Visual Feedback:** Shows a title on the screen when stepping on an elevator block.
- **Customizable Blocks:** Define which blocks act as elevators via `config.yml`.
- **Global Announcement:** Notifies the server when a new elevator (with 2 or more floors) is created.
- **Discord Integration:** Sends an alert via Webhook when a valid elevator is detected.
- **Custom Sounds:** Different sound effects for detection, going up, and going down.
- **Multi-language Support:** Native support for English (`en`) and Portuguese (`pt`).

## 🛠 Commands
- `/elevator` - Shows the help menu, version 1.1, and creator info.
- `/elevator reload` - Reloads the configuration and message files.

## 🔑 Permissions
- `elevator.use` - Allows using elevators (Default: Everyone, unless `require-permission` is set to true).
- `elevator.admin` - Allows using the reload command (Default: OP).

## 📂 Installation
1. Place the `Elevator-1.1.jar` file in your server's `plugins` folder.
2. **Delete the old configuration folder** if you are updating.
3. Start the server to generate the new files.
4. Edit `config.yml` to set your language, sounds, and Discord Webhook URL.
5. Use `/elevator reload` to apply the changes.

## 📋 Height Reference (Y)
- **Overworld:** -64 to 320
- **Nether:** 0 to 128
- **The End:** 0 to 256
