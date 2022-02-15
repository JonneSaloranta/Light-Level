package me.slrnt.lightlevel;

import me.slrnt.lightlevel.commands.ConfigReloadCommand;
import me.slrnt.lightlevel.commands.LightlevelCommand;
import me.slrnt.lightlevel.configs.PlayerSettings;
import me.slrnt.lightlevel.listeners.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LightLevel extends JavaPlugin implements Listener {

    PluginManager pm = getServer().getPluginManager();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    private void onJoinEvent(PlayerJoinEvent e) {
        loadConfig(e.getPlayer());
    }

    private void registerListeners() {
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(this, this);
    }

    private void registerCommands() {
        getCommand("lightlevel").setExecutor(new LightlevelCommand(this));
        getCommand("lightlevel").setTabCompleter(new LightlevelCommand(this));

        getCommand("lightlevelconfig").setExecutor(new ConfigReloadCommand(this));
        getCommand("lightlevelconfig").setTabCompleter(new ConfigReloadCommand(this));
    }

    public void loadConfig(Player player) {
        //TODO check if errors persist when generating file for the first time
        PlayerSettings.createPlayerFile(player);
        PlayerSettings.getPlayerSettings().options().copyDefaults();
        PlayerSettings.save();
    }
}
