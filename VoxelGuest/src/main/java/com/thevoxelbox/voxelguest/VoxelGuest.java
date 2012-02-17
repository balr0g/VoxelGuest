/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest;

import com.thevoxelbox.commands.ArgumentOutOfBoundsException;
import com.thevoxelbox.commands.CommandException;
import com.thevoxelbox.commands.CommandMethodInvocationException;
import com.thevoxelbox.commands.CommandsManager;
import com.thevoxelbox.commands.MalformattedCommandException;
import com.thevoxelbox.voxelguest.commands.AsshatMitigationCommands;
import com.thevoxelbox.voxelguest.listeners.ChatEventListener;
import com.thevoxelbox.voxelguest.listeners.LoginEventListener;
import com.thevoxelbox.permissions.InsufficientPermissionsException;
import com.thevoxelbox.permissions.PermissionsManager;
import com.thevoxelbox.voxelguest.players.GroupManager;
import com.thevoxelbox.voxelguest.players.GuestPlayer;

import com.thevoxelbox.voxelguest.util.Configuration;
import com.thevoxelbox.voxelguest.util.Formatter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class VoxelGuest extends JavaPlugin {

    private static VoxelGuest instance;
    protected static CommandsManager commandsManager = new CommandsManager("[VoxelGuest]");
    protected static ChatEventListener chatListener = new ChatEventListener();
    protected static LoginEventListener loginListener = new LoginEventListener();
    protected static List<GuestPlayer> guestPlayers = new LinkedList<GuestPlayer>();
    protected static Map<Plugin, String> pluginIds = new HashMap<Plugin, String>();
    protected static GroupManager groupManager;
    protected static PermissionsManager perms;
    
    protected final Configuration config = new Configuration("VoxelGuest");

    @Override
    public void onDisable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onEnable() {
        instance = this;
        perms = new PermissionsManager(this.getServer(), "[VoxelGuest]");
        groupManager = new GroupManager();
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
        try {
            commandsManager.executeCommand(command, cs, args);
            commandLog(command, cs, args, true);
        } catch (CommandException ex) {
            String report = "&c" + ex.getReason();
            
            for (String str : Formatter.selectFormatter(SimpleFormatter.class).format(report)) {
                cs.sendMessage(str);
            }
            
            if (ex instanceof CommandMethodInvocationException || ex instanceof MalformattedCommandException) {
                log(ex.getReason(), 2);
                ex.printStackTrace();
                return true;
            } else if (ex instanceof ArgumentOutOfBoundsException) {
                try {
                    commandsManager.sendHelp(cs, command);
                } catch (MalformattedCommandException ex1) {
                    String _report = "&c" + ex1.getReason();
            
                    for (String str : Formatter.selectFormatter(SimpleFormatter.class).format(_report)) {
                        cs.sendMessage(str);
                    }
                    
                    log(ex.getReason(), 2);
                    ex.printStackTrace();
                    return true;
                }
            }
            
            commandLog(command, cs, args, false);
        } catch (InsufficientPermissionsException ex) {
            String report = "&c" + ex.getReason();
            
            for (String str : Formatter.selectFormatter(SimpleFormatter.class).format(report)) {
                cs.sendMessage(str);
            }
            
            commandLog(command, cs, args, false);
        }
        
        return true;
    }
    
    public Configuration getConfigData() {
        return config;
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
    
    public static GroupManager getGroupManager() {
        return groupManager;
    }
    
    public static void log(String str) {
        log(str, 0);
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
    
    public void commandLog(org.bukkit.command.Command command, CommandSender cs, String[] args, boolean status) {
        File f = new File("plugins/VoxelGuest/logs/commands.txt");
        PrintWriter pw = null;

        try {
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }

            pw = new PrintWriter(f);
            Date d = new Date();

            if (cs instanceof Player) {
                Player p = (Player) cs;

                String concat = "";

                for (int i = 0; i < args.length; i++) {
                    if (i == (args.length - 1)) {
                        concat = concat + args[i];
                        continue;
                    }

                    concat = concat + args[i] + " ";
                }

                pw.append(d.toString() + " Command: " + command.getName()
                        + ", Player: " + p.getName()
                        + ", Location: [" + p.getLocation().getWorld().getName() + "] (" + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ()
                        + "), Arguments: \"" + concat + "\""
                        + ", Status: " + ((status) ? "EXECUTED" : "FAILED"));

                pw.close();
            } else {
                String concat = "";

                for (int i = 0; i < args.length; i++) {
                    if (i == (args.length - 1)) {
                        concat = concat + args[i];
                        continue;
                    }

                    concat = concat + args[i] + " ";
                }

                pw.append(d.toString() + " Command: " + command.getName()
                        + ", Sender: [CONSOLE]"
                        + ", Arguments: \"" + concat + "\""
                        + ", Status: " + ((status) ? "EXECUTED" : "FAILED"));

                pw.close();
            }
        } catch (IOException ex) {
            VoxelGuest.log("Could not create command log file", 1);
        }
    }
}
