package com.sniper.staffantiabuse.listeners;

import com.sniper.staffantiabuse.StaffAntiAbuse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private StaffAntiAbuse plugin;

    public PlayerJoinListener(StaffAntiAbuse plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Getting all player names, that they do not want on there server from on then, it executes a command that they want.
        for (String playerNames : plugin.getConfig().getStringList("PLAYERS.NOT.ALLOWED"))
            if (player.getName().equalsIgnoreCase(playerNames)) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/" + plugin.getConfig().getString("COMMAND.EXECUTED.PLAYERS"));
            }
        // Checking if the player is in Gamemode Creative, and if they don't have the permission for creative.permission, they will be set to survival.
        if (player.getGameMode() == GameMode.CREATIVE && !player.hasPermission(plugin.getConfig().getString("CREATIVE.PERMISSION"))) {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(ChatColor.RED + "You should not be in creative.");
        }
    }

}
