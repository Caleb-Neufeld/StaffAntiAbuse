package com.sniper.staffantiabuse.listeners;

import com.sniper.staffantiabuse.StaffAntiAbuse;
import com.sniper.staffantiabuse.commands.CommandSpyCommand;
import com.sniper.staffantiabuse.objects.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.UUID;

public class CommandSpyListener implements Listener {

    private StaffAntiAbuse plugin;

    public CommandSpyListener(StaffAntiAbuse plugin) {
        this.plugin = plugin;
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        /* Checking if the player has a permission, if so we check if they execute something with the start /
         if so we get all players, if they have a certain permission, we send a message. */
        if(player.hasPermission(plugin.getConfig().getString("PERMISSION.STAFF"))) {
                for(Player allOnline : Bukkit.getOnlinePlayers()) {
                    if(allOnline.hasPermission("command.spy") && CommandSpyCommand.staff.contains(allOnline)) {
                        allOnline.sendMessage(new CC(plugin.getConfig().getString("COMMAND.SPY.MESSAGE")).translate());
                }
            }
        }

    }
}
