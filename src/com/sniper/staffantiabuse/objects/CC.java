package com.sniper.staffantiabuse.objects;

import org.bukkit.ChatColor;

public class CC {

    private String string;

    public CC(String string) {
        this.string = string;
    }

    public String translate() {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
