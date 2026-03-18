package com.comonier.elevator;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class Elevator extends JavaPlugin {

    private static Elevator instance;
    private FileConfiguration messagesConfig;

    @Override
    public void onEnable() {
        instance = this;

        // Salva a config.yml padrao se nao existir
        saveDefaultConfig();
        
        // Garante que ambos os arquivos de mensagens existam na pasta do plugin
        saveResourceIfNotExists("messages_pt.yml");
        saveResourceIfNotExists("messages_en.yml");
        
        // Carrega as mensagens baseadas no idioma definido na config
        loadMessages();

        // Registro do Comando Principal e do Tab Completer
        ElevatorCommand elevCmd = new ElevatorCommand();
        getCommand("elevator").setExecutor(elevCmd);
        getCommand("elevator").setTabCompleter(elevCmd);

        // Registro dos Listeners separados (Movimento e Criacao)
        getServer().getPluginManager().registerEvents(new ElevatorListener(), this);
        getServer().getPluginManager().registerEvents(new ElevatorCreationListener(), this);

        getLogger().info("Elevator v1.0 by comonier loaded successfully!");
    }

    private void saveResourceIfNotExists(String resourceName) {
        File file = new File(getDataFolder(), resourceName);
        if (false == file.exists()) {
            saveResource(resourceName, false);
        }
    }

    public void loadMessages() {
        String lang = getConfig().getString("lang", "messages_pt");
        File msgFile = new File(getDataFolder(), lang + ".yml");

        // Fallback para Portugues caso o arquivo configurado nao seja encontrado
        if (false == msgFile.exists()) {
            msgFile = new File(getDataFolder(), "messages_pt.yml");
        }

        messagesConfig = YamlConfiguration.loadConfiguration(msgFile);
    }

    public String getMsg(String path) {
        String message = messagesConfig.getString(path);
        if (null == message) {
            return "Path not found: " + path;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public List<String> getMsgList(String path) {
        return messagesConfig.getStringList(path);
    }

    public static Elevator getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("Elevator disabled.");
    }
}
