package com.sniper.staffantiabuse.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Drop {

    private Player dropper;
    private ItemStack item;
    private Location location;

    public Drop(Player dropper, ItemStack item, Location location) {
        this.dropper = dropper;
        this.item = item;
        this.location = location;
    }

    public ItemStack getItem() {
        return item;
    }

    public Location getLocation() {
        return location;
    }

    public Player getDropper() {
        return dropper;
    }
}
