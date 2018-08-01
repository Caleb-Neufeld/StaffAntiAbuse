package com.sniper.staffantiabuse.commands;

import com.sniper.staffantiabuse.StaffAntiAbuse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffAntiAbuseCommand implements CommandExecutor {

    private StaffAntiAbuse plugin;

    public StaffAntiAbuseCommand(StaffAntiAbuse plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(cmd.getName().equalsIgnoreCase("staffantiabuse")) {
            if(sender instanceof Player) {
                Player player = (Player)sender;
                for(String stringX : plugin.getConfig().getStringList("ANTI.ABUSE.COMMAND"))
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', stringX));
                    }
        }
        return false;
    }
}
