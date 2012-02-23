package com.thevoxelbox.voxelguest;

import com.thevoxelbox.commands.ArgumentOutOfBoundsException;
import com.thevoxelbox.commands.CommandException;
import com.thevoxelbox.commands.CommandMethodInvocationException;
import com.thevoxelbox.commands.CommandsManager;
import com.thevoxelbox.commands.MalformattedCommandException;
import com.thevoxelbox.permissions.InsufficientPermissionsException;
import com.thevoxelbox.permissions.PermissionsManager;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleManager;
import com.thevoxelbox.voxelguest.players.GroupManager;
import com.thevoxelbox.voxelguest.players.GuestPlayer;

import com.thevoxelbox.voxelguest.util.Configuration;
import com.thevoxelbox.voxelguest.util.Formatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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
    protected static CommandsManager commandsManager = new CommandsManager("[VoxelGuest]");
    protected static SystemListener listener = new SystemListener();
    protected static List<GuestPlayer> guestPlayers = new LinkedList<GuestPlayer>();
    protected static Map<Plugin, String> pluginIds = new HashMap<Plugin, String>();
    protected static GroupManager groupManager;
    protected static PermissionsManager perms;
    protected static ModuleManager moduleManager;
    
    protected static final Configuration config = new Configuration("VoxelGuest");
    
    protected Class<? extends Module>[] availableModules = new Class[] {
        OfflineModeModule.class,
        AFKModule.class
    };

    @Override
    public void onDisable() {
        ListIterator<GuestPlayer> it = guestPlayers.listIterator();
        
        while (it.hasNext()) {
            GuestPlayer gp = it.next();
            gp.saveData(getPluginId(this));
        }
        
        guestPlayers.clear();
        
        moduleManager.shutDownModules();
        
        getConfigData().save();
    }

    @Override
    public void onEnable() {
        instance = this;
        
        if (getConfigData().getString("reset") == null || getConfigData().getString("reset").equalsIgnoreCase("yes"))
            loadFactorySettings();
        
        perms = new PermissionsManager(this.getServer(), "[VoxelGuest]", config);
        groupManager = new GroupManager();
        moduleManager = new ModuleManager(this);
        registerPluginIds();
        
        // Load system event listeners
        Bukkit.getPluginManager().registerEvents(listener, this);
        Bukkit.getPluginManager().registerEvents(perms, this);
        
        // Load permissions system
        perms.registerActiveHandler();

        // Load players
        for (Player player : Bukkit.getOnlinePlayers()) {
            GuestPlayer gp = new GuestPlayer(player);

            if (isPlayerRegistered(gp)) {
                continue;
            }

            guestPlayers.add(gp); // KEEP THIS LAST
        }
        
        // Load modules
        moduleManager.loadModules(availableModules);
        ModuleManager.setActiveModuleManager(moduleManager);
        
        // Load module events into the system listener
        listener.registerModuleEvents();
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
        try {
            commandsManager.executeCommand(command, cs, args);
            commandLog(command, cs, args, true);
        } catch (CommandException ex) {
            String report = "&c" + ex.getMessage();
            
            for (String str : Formatter.selectFormatter(SimpleFormatter.class).format(report, null)) {
                cs.sendMessage(str);
            }
            
            if (ex instanceof CommandMethodInvocationException || ex instanceof MalformattedCommandException) {
                log(ex.getMessage(), 2);
                ex.printStackTrace();
                return true;
            } else if (ex instanceof ArgumentOutOfBoundsException) {
                try {
                    commandsManager.sendHelp(cs, command);
                } catch (MalformattedCommandException ex1) {
                    String _report = "&c" + ex1.getMessage();
            
                    for (String str : Formatter.selectFormatter(SimpleFormatter.class).format(_report, null)) {
                        cs.sendMessage(str);
                    }
                    
                    log(ex.getMessage(), 2);
                    ex.printStackTrace();
                    return true;
                }
            }
            
            commandLog(command, cs, args, false);
        } catch (InsufficientPermissionsException ex) {
            String report = "&c" + ex.getMessage();
            
            for (String str : Formatter.selectFormatter(SimpleFormatter.class).format(report, null)) {
                cs.sendMessage(str);
            }
            
            commandLog(command, cs, args, false);
        }
        
        return true;
    }
    
    public static Configuration getConfigData() {
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
    
    public static GuestPlayer registerPlayer(Player player) {
        GuestPlayer gp = new GuestPlayer(player);
        
        if (!isPlayerRegistered(gp))
            guestPlayers.add(gp);
        
        return gp;
    }
    
    public static void unregsiterPlayer(GuestPlayer gp) {
        if (isPlayerRegistered(gp))
            guestPlayers.remove(gp);
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
    
    private void loadFactorySettings() {
        getConfigData().setString("join-message-format", "&8(&6$nonline&8) &3$n &7joined");
        getConfigData().setString("leave-message-format", "&8(&6$nonline&8) &3$n &7left");
        getConfigData().setString("kick-message-format", "&8(&6$nonline&8) &3$n &4was kicked out");
        getConfigData().setBoolean("afk-timeout-enabled", false);
        getConfigData().setInt("afk-timeout-minutes", 5);
        
        getConfigData().setString("reset", "no");
        log("==========================================");
        log("* VOXELGUEST");
        log("*");
        log("* The premiere server adminstration suite");
        log("*");
        log("* Built by: psanker & VoxelPlugineering");
        log("* Licensed by GPL - 2012");
        log("==========================================");
        log("Factory settings loaded");
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
    
    public static void log(String module, String str, int importance) {
        switch (importance) {
            case 0:
                Logger.getLogger("Mincraft").info("[VoxelGuest:" + module + "] " + str);
                return;
            case 1:
                Logger.getLogger("Mincraft").warning("[VoxelGuest:" + module + "] " + str);
                return;
            case 2:
                Logger.getLogger("Mincraft").severe("[VoxelGuest:" + module + "] " + str);
                return;
            default:
                Logger.getLogger("Mincraft").info("[VoxelGuest:" + module + "] " + str);
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

            pw = new PrintWriter(new FileWriter(f, true));
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
