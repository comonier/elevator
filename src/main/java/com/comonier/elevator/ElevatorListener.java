package com.comonier.elevator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ElevatorListener implements Listener {

    private final Elevator plugin = Elevator.getInstance();
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void onStep(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX()) {
            if (event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
                if (event.getFrom().getBlockY() == event.getTo().getBlockY()) {
                    return;
                }
            }
        }

        Player player = event.getPlayer();
        Block blockUnder = event.getTo().getBlock().getRelative(0, -1, 0);

        if (isValidElevatorBlock(blockUnder.getType())) {
            UUID uuid = player.getUniqueId();
            long now = System.currentTimeMillis();
            
            int cooldownTime = plugin.getConfig().getInt("detect-cooldown", 5) * 1000;
            
            if (false == cooldowns.containsKey(uuid) || now > (cooldowns.get(uuid) + cooldownTime)) {
                // Som de deteccao configurado
                playDetectSound(player);
                
                String title = plugin.getMsg("detect.title");
                String subtitle = plugin.getMsg("detect.subtitle");
                player.sendTitle(title, subtitle, 10, 40, 10);
                
                cooldowns.put(uuid, now);
            }
        }
    }

    private void playDetectSound(Player player) {
        try {
            String soundName = plugin.getConfig().getString("sound-detect-type", "BLOCK_BEEHIVE_ENTER");
            float pitch = (float) plugin.getConfig().getDouble("sound-detect-pitch", 1.0);
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            player.playSound(player.getLocation(), sound, 1.0f, pitch);
        } catch (Exception e) {
            plugin.getLogger().warning("Invalid detect sound: " + e.getMessage());
        }
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        if (event.getTo().getY() > event.getFrom().getY()) {
            Player player = event.getPlayer();
            Block blockUnder = player.getLocation().getBlock().getRelative(0, -1, 0);

            if (isValidElevatorBlock(blockUnder.getType())) {
                if (hasPermission(player)) {
                    searchFloor(player, blockUnder.getLocation(), "UP");
                }
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (true == event.isSneaking()) {
            Player player = event.getPlayer();
            Block blockUnder = player.getLocation().getBlock().getRelative(0, -1, 0);

            if (isValidElevatorBlock(blockUnder.getType())) {
                if (hasPermission(player)) {
                    searchFloor(player, blockUnder.getLocation(), "DOWN");
                }
            }
        }
    }

    private void searchFloor(Player player, Location startLoc, String direction) {
        int maxDist = plugin.getConfig().getInt("max-distance", -1);
        int currentDist = 0;
        int limitY = 320; 
        if (direction.equals("DOWN")) limitY = -64;

        Location currentLoc = startLoc.clone();

        while (true) {
            currentDist = currentDist + 1;
            if (maxDist > -1) {
                if (currentDist > maxDist) break;
            }

            if (direction.equals("UP")) {
                currentLoc.add(0, 1, 0);
                if (currentLoc.getBlockY() > limitY) break;
            } else {
                currentLoc.subtract(0, 1, 0);
                if (limitY > currentLoc.getBlockY()) break;
            }

            Block targetBlock = currentLoc.getBlock();
            if (isValidElevatorBlock(targetBlock.getType())) {
                if (targetBlock.getRelative(0, 1, 0).getType().isAir()) {
                    if (targetBlock.getRelative(0, 2, 0).getType().isAir()) {
                        Location teleLoc = targetBlock.getLocation().add(0.5, 1.0, 0.5);
                        teleLoc.setYaw(player.getLocation().getYaw());
                        teleLoc.setPitch(player.getLocation().getPitch());
                        
                        player.teleport(teleLoc);
                        playConfiguredSound(teleLoc, direction);
                        
                        String msgKey = direction.equals("UP") ? "teleport.up" : "teleport.down";
                        player.sendMessage(plugin.getMsg("prefix") + plugin.getMsg(msgKey));
                        return;
                    }
                }
            }
        }
        player.sendMessage(plugin.getMsg("prefix") + plugin.getMsg("teleport.no-floor-found"));
    }

    private void playConfiguredSound(Location loc, String direction) {
        try {
            String key = direction.equals("UP") ? "sound-up" : "sound-down";
            String soundName = plugin.getConfig().getString(key + "-type", "ENTITY_SHULKER_TELEPORT");
            float pitch = (float) plugin.getConfig().getDouble(key + "-pitch", 1.0);
            float volume = (float) plugin.getConfig().getDouble("sound-volume", 2.0);
            
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            loc.getWorld().playSound(loc, sound, volume, pitch);
        } catch (Exception e) {
            plugin.getLogger().warning("Invalid teleport sound: " + e.getMessage());
        }
    }

    private boolean isValidElevatorBlock(Material type) {
        List<String> allowedBlocks = plugin.getConfig().getStringList("block-types");
        for (String blockName : allowedBlocks) {
            if (type.name().equalsIgnoreCase(blockName)) return true;
        }
        return false;
    }

    private boolean hasPermission(Player player) {
        if (false == plugin.getConfig().getBoolean("require-permission", false)) return true;
        if (player.hasPermission("elevator.use")) return true;
        player.sendMessage(plugin.getMsg("prefix") + plugin.getMsg("no-permission"));
        return false;
    }
}
