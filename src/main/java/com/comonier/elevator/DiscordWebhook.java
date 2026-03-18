package com.comonier.elevator;

import org.bukkit.Bukkit;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook {

    public static void send(String playerName, int floors, String blockType) {
        Elevator plugin = Elevator.getInstance();
        String webhookUrl = plugin.getConfig().getString("discord-webhook", "");

        if (webhookUrl.isEmpty()) return;
        if (false == webhookUrl.startsWith("https://")) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL(webhookUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("User-Agent", "Elevator-Plugin");
                conn.setDoOutput(true);

                String msg = "**[Elevator]** " + playerName + " created a " + floors + " floor elevator using " + blockType + "!";
                String json = "{\"content\": \"" + msg + "\"}";

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                    os.flush();
                }

                // Codigo de resposta para garantir que enviou
                int code = conn.getResponseCode();
                if (code > 299) {
                    plugin.getLogger().warning("Webhook Error: " + code);
                }

                conn.disconnect();
            } catch (Exception e) {
                plugin.getLogger().warning("Webhook fail: " + e.getMessage());
            }
        });
    }
}
