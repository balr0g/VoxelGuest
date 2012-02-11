/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.permissions;

import org.bukkit.Server;

/**
 *
 * @author patrick
 */
public abstract class PermissionsHandler {
    
    protected Server server;
    
    public abstract PermissionsHandler initialize(Server server);
    
    public abstract String getDetectionMessage();
    
    public abstract boolean hasPermission(String name, String permission);
    
    public abstract boolean hasPermission(String world, String name, String permission);
    
    public abstract boolean inGroup(String name, String group);
    
    public abstract String[] getGroups(String name);
}
