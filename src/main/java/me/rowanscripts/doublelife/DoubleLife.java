package me.rowanscripts.doublelife;

import me.rowanscripts.doublelife.commands.*;
import me.rowanscripts.doublelife.data.ConfigHandler;
import me.rowanscripts.doublelife.data.SaveHandler;
import me.rowanscripts.doublelife.listeners.BlockBannedItems;
import me.rowanscripts.doublelife.listeners.ChatFormat;
import me.rowanscripts.doublelife.listeners.PairHealth;
import me.rowanscripts.doublelife.listeners.ShareEffects;
import me.rowanscripts.doublelife.scoreboard.TeamHandler;
import me.rowanscripts.doublelife.util.commandArguments;
import me.rowanscripts.doublelife.util.hiddenClass;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DoubleLife extends JavaPlugin {

    public static JavaPlugin plugin;

    public static Collection<NamespacedKey> recipeKeys;

    public void createRecipes(){

        if (ConfigHandler.configYaml.getBoolean("settings.recipes.craftable_saddle")) {
            ItemStack saddle = new ItemStack(Material.SADDLE, 1);
            NamespacedKey saddleKey = new NamespacedKey(plugin, "saddle");
            ShapedRecipe saddleRecipe = new ShapedRecipe(saddleKey, saddle);
            saddleRecipe.shape("LLL", " X ", "X X");
            saddleRecipe.setIngredient('X', Material.LEATHER);
            Bukkit.addRecipe(saddleRecipe);
            recipeKeys.add(saddleKey);
        }

        if (ConfigHandler.configYaml.getBoolean("settings.recipes.craftable_nametag")) {
            ItemStack nameTag = new ItemStack(Material.NAME_TAG, 1);
            NamespacedKey nameTagKey = new NamespacedKey(plugin, "nametag");
            ShapedRecipe nameTagRecipe = new ShapedRecipe(nameTagKey, nameTag);
            nameTagRecipe.shape("XXX", " S ", " P ");
            nameTagRecipe.setIngredient('S', Material.STRING);
            nameTagRecipe.setIngredient('P', Material.PAPER);
            Bukkit.addRecipe(nameTagRecipe);
            recipeKeys.add(nameTagKey);
        }

        if (ConfigHandler.configYaml.getBoolean("settings.recipes.paper_tnt")) {
            ItemStack TNT = new ItemStack(Material.TNT, 1);
            NamespacedKey TNTKey = new NamespacedKey(plugin, "customtnt");
            ShapedRecipe TNTRecipe = new ShapedRecipe(TNTKey, TNT);
            TNTRecipe.shape("PSP", "SGS", "PSP");
            TNTRecipe.setIngredient('P', Material.PAPER);
            TNTRecipe.setIngredient('S', Material.SAND);
            TNTRecipe.setIngredient('G', Material.GUNPOWDER);
            Bukkit.addRecipe(TNTRecipe);
            recipeKeys.add(TNTKey);
        }

        if (ConfigHandler.configYaml.getBoolean("settings.recipes.craftable_sporeblossom")) {
            ItemStack sporeBlossom = new ItemStack(Material.SPORE_BLOSSOM, 1);
            NamespacedKey sporeBlossomKey = new NamespacedKey(plugin, "sporeblossom");
            ShapedRecipe sporeBlossomRecipe = new ShapedRecipe(sporeBlossomKey, sporeBlossom);
            sporeBlossomRecipe.shape("  M", " L ", "XXX");
            sporeBlossomRecipe.setIngredient('M', Material.MOSS_BLOCK);
            sporeBlossomRecipe.setIngredient('L', Material.LILAC);
            Bukkit.addRecipe(sporeBlossomRecipe);
            recipeKeys.add(sporeBlossomKey);
        }

        for (Player player : Bukkit.getOnlinePlayers())
            for (NamespacedKey key : recipeKeys)
                player.discoverRecipe(key);

    }

    @Override
    public void onEnable() {
        plugin = this;

        int pluginId = hiddenClass.getMetricsId();
        Metrics metrics = new Metrics(plugin, pluginId);

        ConfigHandler.construct();
        SaveHandler.construct();

        plugin.getCommand("doublelife").setExecutor(new mainCommandExecutor());
        plugin.getCommand("doublelife").setTabCompleter(new mainTabCompleter());

        Bukkit.getPluginManager().registerEvents(new TeamHandler(), plugin);
        Bukkit.getPluginManager().registerEvents(new PairHealth(), plugin);
        Bukkit.getPluginManager().registerEvents(new ShareEffects(), plugin);
        Bukkit.getPluginManager().registerEvents(new BlockBannedItems(), plugin);
        Bukkit.getPluginManager().registerEvents(new ChatFormat(), plugin);

        recipeKeys = new ArrayList<>();
        createRecipes();
    }

    public class mainCommandExecutor implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length < 1)
                return false;

            if (args[0].equalsIgnoreCase("setup"))
                return setup.onCommand(sender, command, label, args);
            else if (args[0].equalsIgnoreCase("randomizepairs"))
                return randomizePairs.onCommand(sender, command, label, args);
            else if (args[0].equalsIgnoreCase("pair"))
                return pair.onCommand(sender, command, label, args);
            else if (args[0].equalsIgnoreCase("unpair"))
                return unpair.onCommand(sender, command, label, args);
            else if (args[0].equalsIgnoreCase("setLives"))
                return setLives.onCommand(sender, command, label, args);
            else if (args[0].equalsIgnoreCase("distributeplayers"))
                return distributePlayers.onCommand(sender, command, label, args);
            else if (args[0].equalsIgnoreCase("help"))
                return help.onCommand(sender, command, label, args);
            else if (args[0].equalsIgnoreCase("reload"))
                return reload.onCommand(sender, command, label, args);

            return false;
        }

    }

    public class mainTabCompleter implements TabCompleter {

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

            if ((label.equalsIgnoreCase("dl") || label.equalsIgnoreCase("doublelife")) && sender.hasPermission("doublelife.admin")){
                if (args.length == 1)
                    return commandArguments.getAdministrativeCommands();

                if (args[0].equalsIgnoreCase("pair"))
                    return pair.getAppropriateArguments(args);
                else if (args[0].equalsIgnoreCase("unpair"))
                    return unpair.getAppropriateArguments(args);
                else if (args[0].equalsIgnoreCase("setLives"))
                    return setLives.getAppropriateArguments(args);
            }

            return new ArrayList<>();
        }

    }

}
