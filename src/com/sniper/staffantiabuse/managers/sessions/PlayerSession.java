package com.sniper.staffantiabuse.managers.sessions;

import com.sniper.staffantiabuse.objects.AbuseType;
import com.sniper.staffantiabuse.objects.CC;
import com.sniper.staffantiabuse.objects.Drop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlayerSession {

    private static ArrayList<PlayerSession> playerSessions = new ArrayList<>();

    private HashMap<UUID, Drop> dropSession = new HashMap<>();

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    private UUID playerUUID;

    public PlayerSession(UUID uuid) {
        this.playerUUID = uuid;
        playerSessions.add(this);
    }

    public static ArrayList<PlayerSession> getPlayerSessions() {
        return playerSessions;
    }

    public static PlayerSession fromUUID(UUID uuid) {
        for (PlayerSession session : getPlayerSessions()) {
            if (session.getPlayerUUID().equals(uuid))
                return session;
        }
        return null;
    }

    public void addDrop(Drop drop) {
        dropSession.put(UUID.randomUUID(), drop);
    }

    public Drop fromItemStack(ItemStack item) {
        for (Map.Entry<UUID, Drop> entry : dropSession.entrySet())
            if (entry.getValue().getItem().equals(item))
                return entry.getValue();
        return null;
    }

    public void removeDrop(Drop drop) {
        dropSession.remove(drop);
    }

    //Checks if the player is most likely abusing
    public boolean isAbusing(AbuseType type) {
        if (type == AbuseType.DROP) { //Checks if the player has abused from dropping
            HashMap<Location, ArrayList<Location>> locationClose = new HashMap<>(); //Stores: first checked location, similar locations within 50 blocks of eachother
            HashMap<UUID, Integer> locationCommonPlayers = new HashMap<>(); //Stores: random key, similar players found within 50 blocks of dropping
            toploop:
            for (Drop drop : dropSession.values()) {
                for (ArrayList<Location> existsList : locationClose.values())
                    for (Location exists : existsList)
                        if (exists.equals(drop.getLocation()))
                            continue toploop;
                for (Drop drop2 : dropSession.values()) {
                    if (drop.getLocation().distance(drop2.getLocation()) <= 50) {
                        if (locationClose.containsKey(drop.getLocation())) {
                            ArrayList<Location> locations = new ArrayList<>();
                            locations.addAll(locationClose.get(drop.getLocation()));
                            locations.add(drop2.getLocation());
                            locationClose.put(drop.getLocation(), locations);
                        } else {
                            ArrayList<Location> locations = new ArrayList<>();
                            locations.add(drop2.getLocation());
                            locationClose.put(drop.getLocation(), locations);
                        }
                    }
                }
            }
            toploop:
            for(Drop drop : dropSession.values()) {
                for(Drop drop2: dropSession.values()) {
                    for(Entity entity : drop.getLocation().getWorld().getNearbyEntities(drop.getLocation(), 25, 25, 25)) {
                        if(entity.getType()!=EntityType.PLAYER) continue;
                        Player player = (Player) entity;
                        for(Entity entity2 : drop.getLocation().getWorld().getNearbyEntities(drop2.getLocation(), 25, 25 ,25)) {
                            if(entity.getType()!=EntityType.PLAYER) continue;
                            Player player2 = (Player) entity;
                            if(!player.getUniqueId().equals(player2.getUniqueId())) continue toploop;
                            //System.out.println(locationCommonPlayers.containsKey(player.getUniqueId()) + " contains in hashmap - before add");
                            locationCommonPlayers.put(player.getUniqueId(), (locationCommonPlayers.containsKey(player.getUniqueId()) ? locationCommonPlayers.get(player.getUniqueId()) + 1 : 1));
                            //System.out.println(locationCommonPlayers.toString() + " - after add");
                            continue toploop;
                        }
                    }
                }
            }
            //System.out.println(locationCommonPlayers.size() + " -  after add");

            for (Map.Entry<UUID, Integer> entry : locationCommonPlayers.entrySet())
                if (entry.getValue() >= 4) {
                    Bukkit.getConsoleSender().sendMessage(new CC(" &f* &cUser: " + Bukkit.getOfflinePlayer(entry.getKey()).getName() + " has been detected within 25 blocks of " + Bukkit.getOfflinePlayer(getPlayerUUID()).getName() + "'s drops").translate());
                    return true;
                }
            for (Map.Entry<Location, ArrayList<Location>> entry : locationClose.entrySet())
                if (entry.getValue().size() >= 5)
                    return true;

            return false;
        }
        return false;
    }


}
