package com.sniper.staffantiabuse.managers.sessions;

import com.sniper.staffantiabuse.objects.AbuseType;
import com.sniper.staffantiabuse.objects.Drop;
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
    }

    public static ArrayList<PlayerSession> getPlayerSessions() {
        return playerSessions;
    }

    public static PlayerSession fromUUID(UUID uuid) {
        for(PlayerSession session : getPlayerSessions()) {
            if(session.getPlayerUUID().equals(uuid))
                return session;
        }
        return null;
    }

    public void addDrop(Drop drop) {
        dropSession.put(UUID.randomUUID(), drop);
    }

    public Drop fromItemStack(ItemStack item) {
        for(Map.Entry<UUID, Drop> entry : dropSession.entrySet())
            if(entry.getValue().getItem().equals(item))
                return entry.getValue();
            return null;
    }

    public void removeDrop(Drop drop) {
        dropSession.remove(drop);
    }

    //Checks if the player is most likely abusing
    public boolean isAbusing(AbuseType type) {
        if(type==AbuseType.DROP) {
            HashMap<Location, ArrayList<Location>> locationClose = new HashMap<>();
            HashMap<Location, ArrayList<UUID>> locationCommonPlayers = new HashMap<>();
            toploop: for(Drop drop : dropSession.values()) {
                for(ArrayList<Location> existsList : locationClose.values())
                    for(Location exists : existsList)
                        if(exists.equals(drop.getLocation()))
                            continue toploop;
                for(Drop drop2 : dropSession.values()) {
                    if(drop.getLocation().distance(drop2.getLocation()) <= 50) {
                        if(locationClose.containsKey(drop.getLocation())) {
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
            toploop: for(Drop drop : dropSession.values()) {
                for(ArrayList<Location> existsList : locationClose.values())
                    for(Location exists : existsList)
                        if(exists.equals(drop.getLocation()))
                            continue toploop;
                for(Drop drop2 : dropSession.values()) {
                    topentity: for(Entity entity : drop.getLocation().getWorld().getNearbyEntities(drop.getLocation(), 25, 25, 25)) {
                        for(Entity entity2 : drop2.getLocation().getWorld().getNearbyEntities(drop2.getLocation(), 25, 25, 25)) {
                            if(entity.getType()!= EntityType.PLAYER) continue topentity;
                            if(entity2.getType()!= EntityType.PLAYER) continue;
                            Player loaded = (Player)entity;
                            Player loaded2 = (Player)entity2;
                            if(loaded.getUniqueId().equals(loaded2.getUniqueId())) {
                                if(locationClose.containsKey(drop.getLocation())) {
                                    ArrayList<UUID> players = new ArrayList<>();
                                    players.addAll(locationCommonPlayers.get(drop.getLocation()));
                                    players.add(loaded2.getUniqueId());
                                    locationCommonPlayers.put(drop.getLocation(), players);
                                } else {
                                    ArrayList<UUID> players = new ArrayList<>();
                                    players.add(loaded2.getUniqueId());
                                    locationCommonPlayers.put(drop.getLocation(), players);
                                }
                            }
                        }
                    }
                }
            }
            for(Map.Entry<Location, ArrayList<Location>> entry : locationClose.entrySet())
                if(entry.getValue().size() > 5)
                    return true;
            for(Map.Entry<Location, ArrayList<UUID>> entry : locationCommonPlayers.entrySet())
                if(entry.getValue().size() > 4)
                    return true;
                return false;
        }
        return false;
    }




}
