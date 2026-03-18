package com.comonier.elevator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class ElevatorCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Elevator plugin = Elevator.getInstance();

        // Se o comando for apenas /elevator sem argumentos, mostra o help
        if (0 == args.length) {
            List<String> helpLines = plugin.getMsgList("help");
            for (String line : helpLines) {
                sender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', line));
            }
            return true;
        }

        // Subcomando: reload
        if (args[0].equalsIgnoreCase("reload")) {
            if (false == sender.hasPermission("elevator.admin")) {
                sender.sendMessage(plugin.getMsg("prefix") + plugin.getMsg("no-admin-permission"));
                return true;
            }

            plugin.reloadConfig();
            plugin.loadMessages();
            sender.sendMessage(plugin.getMsg("prefix") + plugin.getMsg("reload-success"));
            return true;
        }

        // Se digitar qualquer outra coisa, mostra a sintaxe correta
        sender.sendMessage(plugin.getMsg("prefix") + "§cSintaxe incorreta! Use: §f/elevator reload");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        // Sugestões para o primeiro argumento
        if (1 == args.length) {
            if (sender.hasPermission("elevator.admin")) {
                String input = args[0].toLowerCase();
                if ("reload".startsWith(input)) {
                    completions.add("reload");
                }
            }
        }

        return completions;
    }
}
