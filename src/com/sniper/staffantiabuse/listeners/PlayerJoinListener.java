package com.sniper.staffantiabuse.listeners;

import com.sniper.staffantiabuse.StaffAntiAbuse;
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
        if(player.getGameMode() == GameMode.CREATIVE && !player.hasPermission(plugin.getConfig().getString("CREATIVE_PERMISSION"))) {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(ChatColor.RED + "You should not be in creative.");
        }
    }
}
