package me.slrnt.lightlevel.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

    public static boolean isPlayer(CommandSender sender) {
        return (sender instanceof Player);
    }


    public static boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }
}
