package me.rowanscripts.doublelife.data;

import me.rowanscripts.doublelife.DoubleLife;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    public static File configFile;
    public static YamlConfiguration configYaml;

    public static void construct() {
        configFile = new File(DoubleLife.plugin.getDataFolder(), "config.yml");
        configYaml = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()){
            configYaml.set("settings.gamerules.randomTickSpeed", 6);
            configYaml.set("settings.gamerules.spectatorsGenerateChunks", false);
            configYaml.set("settings.gamerules.doWeatherCycle", false);
            configYaml.set("settings.gamerules.doInsomnia", false);
            configYaml.set("settings.gamerules.announceAdvancements", false);
            configYaml.set("settings.gamerules.playersSleepingPercentage", 100);
            configYaml.set("settings.gamerules.spawnRadius", 0);

            configYaml.set("settings.items.ban_helmets", true);
            configYaml.set("settings.items.ban_op_potions", true);
            configYaml.set("settings.items.ban_god_apples", true);

            configYaml.set("settings.enchanter.craftable", false);
            configYaml.set("settings.enchanter.bookshelves_craftable", false);
            configYaml.set("settings.enchanter.unbreakable", true);
            configYaml.set("settings.enchanter.indestructible_on_drop", true);
            configYaml.set("settings.enchanter.nerf_enchantments", true);

            configYaml.set("settings.other.setSpawnPointAtDistributedLocation", false);
            configYaml.set("settings.other.banPlayersUponLosing", false);
            configYaml.set("settings.other.playExplosionSoundOnDeath", true);
            configYaml.set("settings.other.borderSize", 700D);
        }
        save();
    }

    public static void save() {
        try {
            configYaml.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            configYaml.load(configFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

}
