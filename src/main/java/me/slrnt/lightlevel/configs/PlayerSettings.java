package me.slrnt.lightlevel.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerSettings {

    private static File playerFile;
    private static FileConfiguration playerSettings;

    public static void createPlayerFile(Player player) {

        playerFile =
                new File(Bukkit.getServer().getPluginManager().getPlugin("LightLevel").getDataFolder() + File.separator + "players",
                        player.getUniqueId().toString() + ".yml");

        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            playerSettings = YamlConfiguration.loadConfiguration(playerFile);

        }
    }

    public static FileConfiguration getPlayerSettings() {
        return playerSettings;
    }

    private static File getPlayerFile() {
        return playerFile;
    }

    private static void addConfigDefaults(Player player) {
        try {
            playerSettings.addDefault("name", player.getName());
            playerSettings.addDefault("uuid", player.getUniqueId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            getPlayerSettings().save(getPlayerFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {

        playerSettings = YamlConfiguration.loadConfiguration(getPlayerFile());
    }
}
