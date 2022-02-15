package me.slrnt.lightlevel.commands;

import me.slrnt.lightlevel.LightLevel;
import me.slrnt.lightlevel.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LightlevelCommand implements TabCompleter, CommandExecutor {

    private final LightLevel plugin;

    public LightlevelCommand(LightLevel plugin) {
        this.plugin = plugin;
    }

    List<String> tabArguments = new ArrayList<String>();

    private static boolean isIndicator;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!Utils.isPlayer(sender)) {
            sender.sendMessage(ChatColor.RED + "This command is for players!");
            return false;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("lightlevel")) {
            if (!(player.hasPermission("lightlevel.use"))) {
                player.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                return false;
            }
            if (args.length == 0) {
                setIndicator(!getIndicator());

                //plugin.getConfig().set("indicator", isIndicator);
                //plugin.saveConfig();
                player.sendMessage(ChatColor.GREEN + "Spawn indicator set to " + isIndicator);
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    if (!(player.hasPermission("lightlevel.use.help"))) {
                        player.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                        return false;
                    }
                    player.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/lightlevel radius" + ChatColor.GREEN +
                            " to see set radius");
                    player.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/lightlevel radius <number>" + ChatColor.GREEN +
                            " to set radius");
                    player.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/lightlevel level" + ChatColor.GREEN +
                            " to see set light level");
                    player.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/lightlevel level <number>" + ChatColor.GREEN +
                            " to set light level");
                    return true;
                }

                if (args[0].equalsIgnoreCase("radius")) {
                    if (!(player.hasPermission("lightlevel.use.radius"))) {
                        player.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                        return false;
                    }
                    player.sendMessage(ChatColor.GREEN + "Indicator radius is set to " + plugin.getConfig().getInt("radius"));
                    return true;
                }

                if (args[0].equalsIgnoreCase("level")) {
                    if (!(player.hasPermission("lightlevel.use.level"))) {
                        player.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                        return false;
                    }
                    player.sendMessage(ChatColor.GREEN + "Lightlevel is set to " + plugin.getConfig().getInt("lightlevel"));
                    return true;
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("radius")) {
                    if (!(player.hasPermission("lightlevel.set.radius"))) {
                        player.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                        return false;
                    }
                    if (!isInteger(args[1])) return true;

                    int checkInt = Integer.parseInt(args[1]);
                    if (!isCorrectRange(checkInt, plugin.getConfig().getInt("minRadius"),
                            plugin.getConfig().getInt("maxRadius"))) {
                        player.sendMessage(ChatColor.RED + "Number must be between " + plugin.getConfig().getInt(
                                "minRadius") + "-" + plugin.getConfig().getInt("maxRadius") + "!");
                        return true;
                    }

                    int radius = Integer.parseInt(args[1]);
                    player.sendMessage(ChatColor.GREEN + "Indicator radius is set to " + radius);
                    plugin.getConfig().set("radius", radius);
                    plugin.saveConfig();
                    return true;
                }

                if (args[0].equalsIgnoreCase("level")) {
                    if (!(player.hasPermission("lightlevel.set.level"))) {
                        player.sendMessage(ChatColor.RED + "You don't have permissions to use that!");
                        return false;
                    }

                    boolean parsedBool = isInteger(args[1]);
                    if (!parsedBool) {
                        player.sendMessage(ChatColor.RED + args[1] + " is not a number!");
                        return true;
                    }

                    int checkInt = Integer.parseInt(args[1]);

                    if (!isCorrectRange(checkInt, plugin.getConfig().getInt("minLightLevel"),
                            plugin.getConfig().getInt("maxLightLevel"))) {
                        player.sendMessage(ChatColor.RED + "Number must be between " + plugin.getConfig().getInt(
                                "minLightLevel") + "-" + plugin.getConfig().getInt("maxLightLevel") + "!");
                        return false;
                    }
                    int lightLevel = checkInt;
                    player.sendMessage(ChatColor.GREEN + "Lightlevel is set to " + lightLevel);
                    plugin.getConfig().set("lightlevel", lightLevel);
                    plugin.saveConfig();
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (tabArguments.isEmpty()) {
            tabArguments.add("radius");
            tabArguments.add("level");
            tabArguments.add("help");
        }

        List<String> result = new ArrayList<String>();


        if (args.length == 1) {
            for (String a : tabArguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }
        if (args.length == 2) {
            List<String> test = new ArrayList<String>();

            if (args[0].equalsIgnoreCase("level")) {
                for (int i = plugin.getConfig().getInt("minLightLevel"); i <= plugin.getConfig().getInt(
                        "maxLightLevel"); i++) {
                    String int2String = "" + i;
                    test.add(int2String);
                }
            }

            if (args[0].equalsIgnoreCase("radius")) {
                for (int i = plugin.getConfig().getInt("minRadius"); i <= plugin.getConfig().getInt("maxRadius"); i++) {
                    String int2String = "" + i;
                    test.add(int2String);
                }
            }

            return test;
        }
        return null;
    }


    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isCorrectRange(Integer lightlevel, Integer min, Integer max) {
        return lightlevel >= min && lightlevel <= max;
    }

    public boolean getIndicator() {
        return this.isIndicator;
    }

    public void setIndicator(boolean indicator) {
        this.isIndicator = indicator;
    }
}
