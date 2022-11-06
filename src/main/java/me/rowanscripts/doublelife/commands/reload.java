package me.rowanscripts.doublelife.commands;

import me.rowanscripts.doublelife.data.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class reload {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ConfigHandler.configFile.exists()) {
            sender.sendMessage(ChatColor.DARK_RED + "Couldn't find the configuration file! Have you deleted it? If so, please either restart or reload the server.");
            return true;
        }
        ConfigHandler.load();
        sender.sendMessage(ChatColor.DARK_GREEN + "You've reloaded the configuration file!");
        return true;
    }

}
