package com.sniper.staffantiabuse;

import com.sniper.staffantiabuse.listeners.ItemDropListener;
import com.sniper.staffantiabuse.listeners.PlayerJoinListener;
import com.sniper.staffantiabuse.managers.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class StaffAntiAbuse extends JavaPlugin {

    private static MySQL sql;

    private static StaffAntiAbuse instance;

    public void onEnable() {
        //loadSQL();
        instance = this;
        registerEvents();
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ItemDropListener(), this);
        pm.registerEvents(new PlayerJoinListener(this), this);
    }

    public static StaffAntiAbuse getInstance() {
        return instance;
    }

    public void loadSQL() {
        FileConfiguration file = this.getConfig();
        String path = "MySQL database.";
        sql = new MySQL(file.getString(path + "hostname"), file.getString(path + "port"),
                file.getString(path + "database"), file.getString(path + "user"), file.getString(path + "password"));
        System.out.println("[MySQL] Successfully connected to MySQL");
        try {
            sql.openConnection();
            DatabaseMetaData dbm = sql.getConnection().getMetaData();
            ResultSet playerdatatable = dbm.getTables(null, null, "PlayerData", null);
            if (!playerdatatable.next()) {
                sql.updateSQL(
                        "CREATE TABLE IF NOT EXISTS PlayerData(uuid VARCHAR(255) PRIMARY KEY, kills INTEGER, deaths INTEGER, score INTEGER, killstreak INTEGER, balance LONG)");
                System.out.println("[MySQL SETUP] Created PlayerData table");
            }
            sql.closeConnection();
        } catch (Exception e) {
            System.out.println("[MySQL] Error. Disabling plugin.");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            e.printStackTrace();
        }
    }

    public static MySQL getSql() {
        return sql;
    }


}
