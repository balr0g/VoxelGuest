/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.permissions;

import com.thevoxelbox.voxelguest.VoxelGuest;
import java.lang.reflect.Method;
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
    
    protected PermissionsHandler handler; // instance of external handler
    protected GuestPermissionsHandler defaultHandler = new GuestPermissionsHandler();
    
    public PermissionsManager(Server server) {
        this.server = server;
    }
    
    protected Class<? extends PermissionsHandler>[] availableHandlers = new Class[] {
        GuestPermissionsHandler.class,
        PermissionsExHandler.class
    };
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
        registerActiveHandler();
    }
    
    public void registerActiveHandler() {
        for (Class<? extends PermissionsHandler> handlerClass : availableHandlers) {
            try {
                Method init = handlerClass.getMethod("initialize", Server.class);

                PermissionsHandler _handler = (PermissionsHandler) init.invoke(null, this.server);

                if (!(_handler.equals(defaultHandler))) {
                    handler = _handler;
                    VoxelGuest.log(handler.getDetectionMessage(), 0);
                }
            } catch (Throwable t) {
                VoxelGuest.log("Error in installing permissions handler", 2);
                handler = defaultHandler;
                VoxelGuest.log(handler.getDetectionMessage(), 0);
            }
        }
        
        handler = defaultHandler;
        VoxelGuest.log(handler.getDetectionMessage(), 0);
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
}
