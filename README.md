# Elevator Plugin v1.2
Created by: comonier

A simple and efficient elevator plugin for Minecraft 1.21.1. Use configurable blocks to teleport vertically with custom sounds and Discord integration. This version includes critical fixes for Bedrock Edition (v26.20+) compatibility.

BEDROCK 26.20 FIX: This update addresses the movement changes in the latest Bedrock versions. If you are using Geyser/Floodgate, this version prevents players from getting stuck when going down.

Features
- Vertical Teleportation: Press Jump to go up and Sneak to go down between matching blocks.
- Bedrock Optimized: Adjusted teleport offsets to support the new movement physics.
- Visual Feedback: Shows a title on the screen when stepping on an elevator block.
- Customizable Blocks: Define which blocks act as elevators via config.yml.
- Global Announcement: Notifies the server when a new elevator (with 2 or more floors) is created.
- Discord Integration: Sends an alert via Webhook when a valid elevator is detected.
- Custom Sounds: Different sound effects for detection, going up, and going down.
- Multi-language Support: Native support for English (en), Portuguese (pt), Spanish (es), and Russian (ru).

Commands
- /elevator: Shows the help menu and creator info.
- /elevator reload: Reloads the configuration and message files.

Permissions
- elevator.use: Allows using elevators (Default: Everyone).
- elevator.admin: Allows using the reload command (Default: OP).

Installation
1. Place the Elevator-1.2.jar file in your server's plugins folder.
2. Start the server to generate the new files (including the new translations).
3. Edit config.yml to set your language, sounds, and Discord Webhook URL.
4. Use /elevator reload to apply the changes.

Height Reference (Y)
- Overworld: -64 to 320
- Nether: 0 to 128
- The End: 0 to 256
