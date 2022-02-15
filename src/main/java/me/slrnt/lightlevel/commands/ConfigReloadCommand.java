package me.slrnt.lightlevel.commands;

import me.slrnt.lightlevel.LightLevel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ConfigReloadCommand implements CommandExecutor, TabCompleter {

    private LightLevel plugin;

    public ConfigReloadCommand(LightLevel plugin) {
        this.plugin = plugin;
    }

    List<String> tabArguments = new ArrayList<String>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {

        if (command.getName().equalsIgnoreCase("lightlevelconfig")) {
            if (!(sender.hasPermission("lightlevelconfig.use"))) {
                sender.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use /lightlevelconfig help");
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    if (!(sender.hasPermission("lightlevelconfig.use.help"))) {
                        sender.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                        return false;
                    }
                    sender.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/lightlevelconfig reload" + ChatColor.GREEN + " to " +
                            "reload configs");
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!(sender.hasPermission("lightlevelconfig.use.reload"))) {
                        sender.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                        return false;
                    }
                    reloadConfigs();
                    sender.sendMessage(ChatColor.GOLD + "Configs reloaded!");
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (tabArguments.isEmpty()) {
            tabArguments.add("help");
            tabArguments.add("reload");
        }

        List<String> result = new ArrayList<>();

        if (args.length == 1) {
            for (String a : tabArguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }

        return null;
    }

    private void reloadConfigs() {
        //LightLevelConfig.reload();
        plugin.reloadConfig();
    }
}