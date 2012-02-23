package com.thevoxelbox.permissions;

import org.bukkit.Server;

public abstract class PermissionsHandler {
    
    protected Server server;
    
    public abstract PermissionsHandler initialize(Server server);
    
    public abstract String getDetectionMessage();
    
    public abstract boolean hasPermission(String name, String permission);
    
    public abstract boolean hasPermission(String world, String name, String permission);
    
    public abstract void givePermission(String world, String name, String permission);
    
    public abstract void givePermission(String name, String permission);
    
    public abstract void removePermission(String world, String name, String permission);
    
    public abstract void removePermission(String name, String permission);
    
    public abstract boolean inGroup(String name, String group);
    
    public abstract String[] getGroups(String name);
    
    public abstract void addGroup(String username, String groupname);
    
    public abstract void removeGroup(String username, String groupname);
    
    public abstract void giveGroupPermission(String world, String name, String permission);
    
    public abstract void giveGroupPermission(String name, String permission);
    
    public abstract void removeGroupPermission(String world, String name, String permission);
    
    public abstract void removeGroupPermission(String name, String permission);
}
