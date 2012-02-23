/*
 * VoxelGuest
 *
 * Copyright (C) 2011, 2012 psanker and contributors

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

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
