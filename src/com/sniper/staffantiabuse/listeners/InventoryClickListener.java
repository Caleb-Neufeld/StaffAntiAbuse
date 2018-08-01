package com.sniper.staffantiabuse.listeners;

import com.sniper.staffantiabuse.StaffAntiAbuse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {

    private StaffAntiAbuse plugin;

    public InventoryClickListener(StaffAntiAbuse plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        // Checking if a player with the permission PERMISSION.STAFF, is in one of the below Inventory's, if so (method to be made)
        if (event.getClickedInventory().getType() == InventoryType.CHEST || event.getClickedInventory().getType() == InventoryType.FURNACE ||
                event.getClickedInventory().getType() == InventoryType.DISPENSER || event.getClickedInventory().getType() == InventoryType.HOPPER) {
            if (player.hasPermission(plugin.getConfig().getString("PERMISSION.STAFF"))) {
                //TODO: Make a method to check for anything that can be abused.
            }
        }
    }
}
