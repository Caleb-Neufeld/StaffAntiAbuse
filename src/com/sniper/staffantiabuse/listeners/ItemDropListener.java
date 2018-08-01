package com.sniper.staffantiabuse.listeners;

import com.sniper.staffantiabuse.managers.sessions.PlayerSession;
import com.sniper.staffantiabuse.objects.AbuseType;
import com.sniper.staffantiabuse.objects.Drop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemDropListener implements Listener {

    public static ArrayList<ItemStack> droppedItems = new ArrayList<>();

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        if(e.isCancelled()) return;
        if(!e.getPlayer().hasPermission("antistaffabuse.check")) return;
        ItemStack item = e.getItemDrop().getItemStack();
        droppedItems.add(item);
        PlayerSession session = PlayerSession.fromUUID(e.getPlayer().getUniqueId());
        session.addDrop(new Drop(e.getPlayer(), item, e.getPlayer().getLocation()));
        session.isAbusing(AbuseType.DROP);
        //System.out.println("#########################################");
    }
}
