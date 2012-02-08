package com.thevoxelbox.voxelguest.permissions;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thevoxelbox.voxelguest.VoxelGuest;
import com.thevoxelbox.voxelguest.players.GuestPlayer;

public class GuestPermissions {
    /*
     * ===========================
     * BOUNCER PERMISSIONS SYSTEM
     * 
     * by: Patrick S. Anker
     * ===========================
     * 
     */

    @SuppressWarnings("unchecked")
    public static boolean hasPermission(CommandSender cs, String permission) {
        if (!(cs instanceof Player)) {
            return true;
        }

        GuestPlayer gp = VoxelGuest.getGuestPlayer((Player) cs);

        if (gp == null) {
            VoxelGuest.log("Player wrapper not found for " + ((Player) cs).getName(), 2);
            return false;
        }

        if (!(gp.get(VoxelGuest.getPluginId(VoxelGuest.getInstance()), "permissions") instanceof List<?>)) {
            VoxelGuest.log("Incorrect loading of permissions list for " + ((Player) cs).getName(), 2);
            return false;
        }

        List<String> permissions = (List<String>) gp.get(VoxelGuest.getPluginId(VoxelGuest.getInstance()), "permissions");

        if (permissions.contains("*") || permissions.contains("all")) {
            return true;
        } else {
            String[] levels = interpretLevels(permission);
            String test = "";

            for (int i = 0; i < levels.length; i++) {
                test = test + levels[i];

                if (permissions.contains(test)) {
                    return true;
                }

                if (i != (levels.length - 1)) {
                    test = test + ".";
                }
            }
        }

        return false;
    }

    private static String[] interpretLevels(String permission) {
        return permission.trim().split(".");
    }
}
