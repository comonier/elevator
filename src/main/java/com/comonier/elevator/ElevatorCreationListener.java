package com.comonier.elevator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public class ElevatorCreationListener implements Listener {

    private final Elevator plugin = Elevator.getInstance();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block placed = event.getBlock();
        if (isValidElevatorBlock(placed.getType())) {
            checkAndAnnounce(event.getPlayer(), placed);
        }
    }

    private void checkAndAnnounce(Player player, Block placed) {
        int floors = 0;
        int minY = -64;
        int maxY = 320;
        
        String worldName = placed.getWorld().getName();
        if (worldName.endsWith("_nether")) {
            minY = 0;
            maxY = 128;
        } else if (worldName.endsWith("_the_end")) {
            minY = 0;
            maxY = 256;
        }

        for (int y = minY; maxY > y; y = y + 1) {
            Block b = placed.getWorld().getBlockAt(placed.getX(), y, placed.getZ());
            if (b.getType() == placed.getType()) {
                if (b.getRelative(0, 1, 0).getType().isAir()) {
                    if (b.getRelative(0, 2, 0).getType().isAir()) {
                        floors = floors + 1;
                    }
                }
            }
        }

        if (floors > 1) {
            String msg = plugin.getMsg("elevator-created")
                    .replace("%player%", player.getName())
                    .replace("%floors%", String.valueOf(floors))
                    .replace("%block%", placed.getType().name().toLowerCase());
            
            Bukkit.broadcastMessage(msg);
            
            // Referencia a classe utilitaria de Webhook
            DiscordWebhook.send(player.getName(), floors, placed.getType().name());
        }
    }

    private boolean isValidElevatorBlock(Material type) {
        List<String> allowedBlocks = plugin.getConfig().getStringList("block-types");
        for (String blockName : allowedBlocks) {
            if (type.name().equalsIgnoreCase(blockName)) return true;
        }
        return false;
    }
}
