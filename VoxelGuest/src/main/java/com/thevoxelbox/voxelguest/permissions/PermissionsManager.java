package com.thevoxelbox.voxelguest.permissions;

import com.thevoxelbox.voxelguest.VoxelGuest;
import java.lang.reflect.Method;
import java.util.Arrays;
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
    protected static PermissionsHandler handler;
    
    public PermissionsManager(Server s) {
        server = s;
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
                    VoxelGuest.log(handler.getDetectionMessage(), 0);
                    break;
                }
                
            } catch (Throwable t) {
                continue;
            }
        }
        
        handler = new GuestPermissionsHandler(server);
        VoxelGuest.log(handler.getDetectionMessage(), 0);
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
}
