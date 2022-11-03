package me.rowanscripts.doublelife.commands;

import me.rowanscripts.doublelife.data.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class reload {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigHandler.load();
        sender.sendMessage(ChatColor.DARK_GREEN + "You've reloaded the configuration file!");
        return true;
    }

}
