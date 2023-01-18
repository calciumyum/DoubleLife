package me.rowanscripts.doublelife.data;


import me.rowanscripts.doublelife.DoubleLife;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigHandler {

    private static JavaPlugin plugin;

    public ConfigHandler() {
        plugin = DoubleLife.plugin;
        plugin.saveDefaultConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
    }

}
