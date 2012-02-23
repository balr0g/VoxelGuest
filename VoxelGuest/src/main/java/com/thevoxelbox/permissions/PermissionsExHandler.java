package com.thevoxelbox.permissions;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;


public class PermissionsExHandler extends PermissionsHandler {

    private final PermissionManager manager;
    
    @Override
    public PermissionsHandler initialize(Server server) {
        try {
            PermissionManager manager = server.getServicesManager().load(PermissionManager.class);
            
            if (manager == null)
                return null;
            
            return new PermissionsExHandler(server, manager);
        } catch (Throwable t) {
            return null;
        }
    }
    
    public PermissionsExHandler(Server server, PermissionManager manager) {
        this.server = server;
        this.manager = manager;
    }
    
    @Override
    public String getDetectionMessage() {
        return "Using PermissionsEx as the permissions system";
    }

    @Override
    public boolean hasPermission(String name, String permission) {
        Player p = server.getPlayerExact(name);
        return manager.has(name, permission, p != null ? p.getWorld().getName() : null);
    }

    @Override
    public boolean hasPermission(String world, String name, String permission) {
        Player p = server.getPlayerExact(name);
        return manager.has(name, permission, world);
    }

    @Override
    public boolean inGroup(String name, String group) {
        PermissionUser user = manager.getUser(name);
        PermissionGroup g = manager.getGroup(group);
        
        if (user == null) {
            return false;
        }
        
        return user.inGroup(g, true);
    }

    @Override
    public String[] getGroups(String name) {
        PermissionUser user = manager.getUser(name);
        
        if (user == null) {
            return new String[0];
        }
        
        return user.getGroupsNames();
    }

    @Override
    public void givePermission(String world, String name, String permission) {
        PermissionUser user = manager.getUser(name);
        
        if (user == null)
            return;
        
        if (world == null)
            user.addPermission(permission);
        else
            user.addPermission(permission, world);
    }

    @Override
    public void givePermission(String name, String permission) {
        givePermission(null, name, permission);
    }

    @Override
    public void removePermission(String world, String name, String permission) {
        PermissionUser user = manager.getUser(name);
        
        if (user == null)
            return;
        
        if (world == null)
            user.removePermission(permission);
        else
            user.removePermission(permission, world);
    }

    @Override
    public void removePermission(String name, String permission) {
        removePermission(null, name, permission);
    }

    @Override
    public void addGroup(String username, String groupname) {
        PermissionUser user = manager.getUser(username);
        PermissionGroup group = manager.getGroup(groupname);
        
        if (group == null || user == null)
            return;
        
        user.addGroup(group);
    }

    @Override
    public void removeGroup(String username, String groupname) {
        PermissionUser user = manager.getUser(username);
        PermissionGroup group = manager.getGroup(groupname);
        
        if (group == null || user == null)
            return;
        
        user.removeGroup(group);
    }

    @Override
    public void giveGroupPermission(String world, String name, String permission) {
        PermissionGroup group = manager.getGroup(name);
        
        if (group == null)
            return;
        
        if (world == null)
            group.addPermission(permission);
        else
            group.addPermission(permission, world);
    }

    @Override
    public void giveGroupPermission(String name, String permission) {
        giveGroupPermission(null, name, permission);
    }

    @Override
    public void removeGroupPermission(String world, String name, String permission) {
        PermissionGroup group = manager.getGroup(name);
        
        if (group == null)
            return;
        
        if (world == null)
            group.removePermission(permission);
        else
            group.removePermission(permission, world);
    }

    @Override
    public void removeGroupPermission(String name, String permission) {
        removeGroupPermission(null, name, permission);
    }
}
