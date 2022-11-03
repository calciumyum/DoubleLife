package me.rowanscripts.doublelife.commands;

import me.rowanscripts.doublelife.data.SaveHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class randomizePairs {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(ChatColor.RED + "This is a player only command!");
            return true;
        }
        if ((args.length != 2) || !args[1].equalsIgnoreCase("confirm")){
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "Are you sure? " + ChatColor.RESET + ChatColor.GRAY + "This command should only be ran once, at the start of a season! Please run '/dl randomize confirm' to execute the command!");
            return true;
        }

        SaveHandler.clearFile();

        Player executor = (Player) sender;
        World world = executor.getWorld();

        List<UUID> availablePlayers = new ArrayList<>();
        for(Player player : world.getPlayers())
            if (!player.hasPermission("doublelife.nopair"))
                availablePlayers.add(player.getUniqueId());

        if (availablePlayers.size() == 1){
            sender.sendMessage(ChatColor.DARK_RED + "Not enough players!");
            return true;
        }

        Random r = new Random();
        while (availablePlayers.size() > 1){
            UUID playerOne = availablePlayers.get(r.nextInt(availablePlayers.size()));
            UUID playerTwo = availablePlayers.get(r.nextInt(availablePlayers.size()));
            if (playerOne != playerTwo) {
                availablePlayers.remove(playerOne);
                availablePlayers.remove(playerTwo);
                SaveHandler.createPair(playerOne, playerTwo, 3);
            }
        }

        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Everyone now has a random soulmate!");
        return true;
    }

}
