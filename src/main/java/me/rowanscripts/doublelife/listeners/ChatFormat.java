package me.rowanscripts.doublelife.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatFormat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String format = "<player>: <message>";
        format = format.replace("<player>", "%1$s");
        format = format.replace("<message>", "%2$s");
        format = format.replace(":", ChatColor.RESET + ":");
        event.setFormat(format);
    }
}