/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.commands.CommandsManager;
import com.thevoxelbox.voxelguest.commands.commands.AsshatMitigationCommands;
import com.thevoxelbox.voxelguest.listeners.ChatEventListener;
import com.thevoxelbox.voxelguest.listeners.LoginEventListener;
import com.thevoxelbox.voxelguest.permissions.PermissionsManager;
import com.thevoxelbox.voxelguest.players.GuestPlayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class VoxelGuest extends JavaPlugin {

    private static VoxelGuest instance;
    protected static CommandsManager commandsManager = new CommandsManager();
    protected static ChatEventListener chatListener = new ChatEventListener();
    protected static LoginEventListener loginListener = new LoginEventListener();
    protected static List<GuestPlayer> guestPlayers = new LinkedList<GuestPlayer>();
    protected static Map<Plugin, String> pluginIds = new HashMap<Plugin, String>();
    protected static PermissionsManager perms;

    @Override
    public void onDisable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onEnable() {
        instance = this;
        perms = new PermissionsManager(this.getServer());
        registerPluginIds();

        Bukkit.getPluginManager().registerEvents(chatListener, this);
        Bukkit.getPluginManager().registerEvents(loginListener, this);
        Bukkit.getPluginManager().registerEvents(perms, this);

        commandsManager.registerCommands(AsshatMitigationCommands.class);

        for (Player player : Bukkit.getOnlinePlayers()) {
            GuestPlayer gp = new GuestPlayer(player);

            if (isPlayerRegistered(gp)) {
                continue;
            }

            guestPlayers.add(gp); // KEEP THIS LAST
        }
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
        boolean status = commandsManager.executeCommand(command, cs, args);
        commandsManager.commandLog(command, cs, args, status);
        return true;
    }

    public static GuestPlayer getGuestPlayer(Player player) {
        Iterator<GuestPlayer> it = guestPlayers.listIterator();

        while (it.hasNext()) {
            GuestPlayer gp = it.next();

            if (player.equals(gp.getPlayer())) {
                return gp;
            }
        }

        return new GuestPlayer(player);
    }

    public static GuestPlayer[] getRegisteredPlayers() {
        GuestPlayer[] gps = new GuestPlayer[guestPlayers.size()];
        return guestPlayers.toArray(gps);
    }

    public static boolean isPlayerRegistered(GuestPlayer gp) {
        return guestPlayers.contains(gp);
    }

    private void registerPluginIds() {
        if (pluginIds.isEmpty()) {
            for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                if (pluginIds.containsKey(plugin)) {
                    VoxelGuest.log("Attempted to register multiple IDs for plugin \"" + plugin.getDescription().getName() + "\"", 1);
                    continue;
                }

                String sample = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
                char[] key = new char[16];
                Random rand = new Random();

                for (int i = 0; i < 16; i++) {
                    key[i] = sample.charAt(rand.nextInt(sample.length()));
                }

                String id = new String(key);
                pluginIds.put(plugin, id);
            }
        }
    }

    public static String getPluginId(Plugin plugin) {
        if (pluginIds.containsKey(plugin)) {
            return pluginIds.get(plugin);
        }

        return null;
    }

    public static VoxelGuest getInstance() {
        return instance;
    }

    public static CommandsManager getCommandsManager() {
        return commandsManager;
    }

    public static void log(String str, int importance) {
        switch (importance) {
            case 0:
                Logger.getLogger("Mincraft").info("[VoxelGuest] " + str);
                return;
            case 1:
                Logger.getLogger("Mincraft").warning("[VoxelGuest] " + str);
                return;
            case 2:
                Logger.getLogger("Mincraft").severe("[VoxelGuest] " + str);
                return;
            default:
                Logger.getLogger("Mincraft").info("[VoxelGuest] " + str);
                return;
        }
    }
}
