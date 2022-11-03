package me.rowanscripts.doublelife.commands;

import me.rowanscripts.doublelife.data.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class setup {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender)
            return true;

        if ((args.length != 2) || !args[1].equalsIgnoreCase("confirm")){
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "Are you sure? " + ChatColor.RESET + ChatColor.GRAY + "This command should only be ran once, while setting up the server! Please run '/dl setup confirm' to execute the command!");
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();
        String gameruleSettingsPath = "settings.gamerules.";

        world.setGameRule(GameRule.RANDOM_TICK_SPEED, ConfigHandler.configYaml.getInt(gameruleSettingsPath + "randomTickSpeed"));
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, ConfigHandler.configYaml.getBoolean(gameruleSettingsPath + "spectatorsGenerateChunks"));
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, ConfigHandler.configYaml.getBoolean(gameruleSettingsPath + "doWeatherCycle"));
        world.setGameRule(GameRule.DO_INSOMNIA, ConfigHandler.configYaml.getBoolean(gameruleSettingsPath + "doInsomnia"));
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, ConfigHandler.configYaml.getBoolean(gameruleSettingsPath + "announceAdvancements"));
        world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, ConfigHandler.configYaml.getInt(gameruleSettingsPath + "playersSleepingPercentage"));
        world.setGameRule(GameRule.SPAWN_RADIUS, ConfigHandler.configYaml.getInt(gameruleSettingsPath + "spawnRadius"));

        world.setSpawnLocation(player.getLocation());
        world.getWorldBorder().setCenter(player.getLocation());
        world.getWorldBorder().setSize(ConfigHandler.configYaml.getDouble("settings.other.borderSize"));

        player.sendMessage(ChatColor.DARK_GREEN + "The world has been set up!");
        return true;
    }

}
