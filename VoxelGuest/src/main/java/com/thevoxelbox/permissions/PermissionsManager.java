package com.thevoxelbox.permissions;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

/**
 *
 * @author psanker
 * 
 * Inspired by WEPIF
 * 
 */
public class PermissionsManager implements Listener {
    private final Server server;
    private static String tag;
    protected static PermissionsHandler handler;
    
    public PermissionsManager(Server s, String pluginPrefix) {
        server = s;
        tag = pluginPrefix;
    }
    
    protected Class<? extends PermissionsHandler>[] availableHandlers = new Class[] {
        PermissionsExHandler.class,
        BPermissionsHandler.class,
        DinnerpermsHandler.class,
        GuestPermissionsHandler.class
    };
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
        String[] plugins = {"PermissionsEx", "bPermissions"};
        
        if (Arrays.asList(plugins).contains(event.getPlugin().getDescription().getName())) {
            registerActiveHandler();
        }
    }
    
    public void registerActiveHandler() {
        for (Class<? extends PermissionsHandler> handlerClass : availableHandlers) {
            try {
                Method init = handlerClass.getMethod("initialize", Server.class);

                PermissionsHandler _handler = (PermissionsHandler) init.invoke(null, this.server);

                if (_handler != null) {
                    if (_handler instanceof GuestPermissionsHandler)
                        continue;
                    
                    handler = _handler;
                    log(handler.getDetectionMessage(), 0);
                    break;
                }
                
            } catch (Throwable t) {
                continue;
            }
        }
        
        handler = new GuestPermissionsHandler(server);
        log(handler.getDetectionMessage(), 0);
    }
    
    public static PermissionsHandler getHandler() {
        return handler;
    }
    
    public boolean hasPermission(String name, String permission) {
        return handler.hasPermission(name, permission);
    }
    
    public boolean hasPermission(String world, String name, String permission) {
        return handler.hasPermission(world, name, permission);
    }
    
    public boolean inGroup(String name, String group) {
        return handler.inGroup(name, group);
    }
    
    public String[] getGroups(String name) {
        return handler.getGroups(name);
    }

    public String getDetectionMessage() {
        return handler.getDetectionMessage();
    }
    
    public static void log(String str, int importance) {
        switch (importance) {
            case 0:
                Logger.getLogger("Mincraft").info(tag + " " + str);
                return;
            case 1:
                Logger.getLogger("Mincraft").warning(tag + " " + str);
                return;
            case 2:
                Logger.getLogger("Mincraft").severe(tag + " " + str);
                return;
            default:
                Logger.getLogger("Mincraft").info(tag + " " + str);
                return;
        }
    }
}
