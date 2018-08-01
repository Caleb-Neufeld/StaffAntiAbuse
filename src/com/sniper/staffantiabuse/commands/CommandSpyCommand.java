package com.sniper.staffantiabuse.commands;

import com.sniper.staffantiabuse.StaffAntiAbuse;
import com.sniper.staffantiabuse.objects.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class CommandSpyCommand implements CommandExecutor {

    private StaffAntiAbuse plugin;

    public static ArrayList<UUID> staff;

    public CommandSpyCommand(StaffAntiAbuse plugin) {
        this.plugin = plugin;
        this.staff = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(cmd.getName().equalsIgnoreCase("spy")) {
            if(sender instanceof Player) {
                Player player = (Player)sender;
                if(player.hasPermission("command.spy")) {
                    // Checking if the player has CommandSpy enabled already, if so we remove them from it.
                    if(staff.contains(player.getUniqueId())) {
                        staff.remove(player.getUniqueId());
                        player.sendMessage(new CC("&7You have successfully &cDisabled &7Staff Monitoring.").translate());
                        return true;
                    } else {
                        // Adding the player to CommandSpy, as they did not have it enabled already.
                        staff.add(player.getUniqueId());
                        player.sendMessage(new CC("&7You have successfully &aEnabled &7Staff Monitoring.").translate());
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
