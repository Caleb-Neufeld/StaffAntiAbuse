package com.sniper.staffantiabuse.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemPickupListener implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        if(ItemDropListener.droppedItems.contains(e.getItem().getItemStack())) {
            //todo pickup dropped item code

        }
    }
}
