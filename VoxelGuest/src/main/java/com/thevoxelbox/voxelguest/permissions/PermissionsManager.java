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
public class PermissionsManager extends PermissionsHandler implements Listener {
    protected static PermissionsHandler handler;
    
    public PermissionsManager(Server server) {
        this.server = server;
    }
    
    protected Class<? extends PermissionsHandler>[] availableHandlers = new Class[] {
        PermissionsExHandler.class,
        BPermissionsHandler.class,
        DinnerpermsHandler.class
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
                    handler = _handler;
                    VoxelGuest.log(handler.getDetectionMessage(), 0);
                    break;
                }
                
            } catch (Throwable t) {
                continue;
            }
        }
    }
    
    public static PermissionsHandler getHandler() {
        return handler;
    }
    
    @Override
    public boolean hasPermission(String name, String permission) {
        return handler.hasPermission(name, permission);
    }
    
    @Override
    public boolean hasPermission(String world, String name, String permission) {
        return handler.hasPermission(world, name, permission);
    }
    
    @Override
    public boolean inGroup(String name, String group) {
        return handler.inGroup(name, group);
    }
    
    @Override
    public String[] getGroups(String name) {
        return handler.getGroups(name);
    }

    @Override
    public PermissionsHandler initialize(Server server) {
        return handler.initialize(server);
    }

    @Override
    public String getDetectionMessage() {
        return handler.getDetectionMessage();
    }
}
