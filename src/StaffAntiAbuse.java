import managers.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class StaffAntiAbuse extends JavaPlugin {

    private static MySQL sql;

    public void onEnable() {
        loadSQL();
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
