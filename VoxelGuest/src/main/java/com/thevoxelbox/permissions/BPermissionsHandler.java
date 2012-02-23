package com.thevoxelbox.permissions;

import org.bukkit.Server;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import de.bananaco.bpermissions.api.util.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class BPermissionsHandler extends PermissionsHandler {

    @Override
    public PermissionsHandler initialize(Server server) {
        try {
           Class.forName("de.bananaco.bpermissions.api.ApiLayer"); 
        } catch (ClassNotFoundException ex) {
            return null;
        }
        
        return new BPermissionsHandler(server);
    }
    
    @Override
    public String getDetectionMessage() {
        return "Using bPermissions as permissions system";
    }
    
    public BPermissionsHandler(Server server) {
        this.server = server;
    }

    @Override
    public boolean hasPermission(String name, String permission) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p == null) {
            return ApiLayer.hasPermission(null, CalculableType.USER, op.getName(), permission);
        } else {
            return ApiLayer.hasPermission(p.getWorld().getName(), CalculableType.USER, p.getName(), permission);
        }
    }

    @Override
    public boolean hasPermission(String world, String name, String permission) {
        Player p = server.getPlayer(name);
        return ApiLayer.hasPermission(world, CalculableType.USER, p.getName(), permission);
    }

    @Override
    public boolean inGroup(String name, String group) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p == null) {
            return ApiLayer.hasGroupRecursive(null, CalculableType.USER, op.getName(), group);
        } else {
            return ApiLayer.hasGroupRecursive(p.getWorld().getName(), CalculableType.USER, p.getName(), group);
        }
    }

    @Override
    public String[] getGroups(String name) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p == null) {
            return ApiLayer.getGroups(null, CalculableType.USER, op.getName());
        } else {
            return ApiLayer.getGroups(p.getWorld().getName(), CalculableType.USER, p.getName());
        }
    }

    @Override
    public void givePermission(String world, String name, String permission) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        Permission perm = Permission.loadFromString(permission);
        
        if (p == null) {
            ApiLayer.addPermission(world, CalculableType.USER, name, perm);
        } else {
            ApiLayer.addPermission(world, CalculableType.USER, p.getName(), perm);
        }
    }

    @Override
    public void givePermission(String name, String permission) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        Permission perm = Permission.loadFromString(permission);
        
        if (p == null) {
            ApiLayer.addPermission(null, CalculableType.USER, name, perm);
        } else {
            ApiLayer.addPermission(p.getWorld().getName(), CalculableType.USER, p.getName(), perm);
        }
    }

    @Override
    public void removePermission(String world, String name, String permission) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p == null) {
            ApiLayer.removePermission(world, CalculableType.USER, name, permission);
        } else {
            ApiLayer.removePermission(world, CalculableType.USER, p.getName(), permission);
        }
    }

    @Override
    public void removePermission(String name, String permission) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p == null) {
            ApiLayer.removePermission(null, CalculableType.USER, name, permission);
        } else {
            ApiLayer.removePermission(p.getWorld().getName(), CalculableType.USER, p.getName(), permission);
        }
    }

    @Override
    public void addGroup(String username, String groupname) {
        OfflinePlayer op = server.getOfflinePlayer(username);
        Player p = op.getPlayer();
        
        if (p == null) {
            ApiLayer.addGroup(null, CalculableType.USER, username, groupname);
        } else {
            ApiLayer.addGroup(p.getWorld().getName(), CalculableType.USER, p.getName(), groupname);
        }
    }

    @Override
    public void removeGroup(String username, String groupname) {
        OfflinePlayer op = server.getOfflinePlayer(username);
        Player p = op.getPlayer();
        
        if (p == null) {
            ApiLayer.removeGroup(null, CalculableType.USER, username, groupname);
        } else {
            ApiLayer.removeGroup(p.getWorld().getName(), CalculableType.USER, username, groupname);
        }
    }

    @Override
    public void giveGroupPermission(String world, String name, String permission) {
        Permission perm = Permission.loadFromString(permission);
        
        ApiLayer.addPermission(world, CalculableType.GROUP, name, perm);
    }

    @Override
    public void giveGroupPermission(String name, String permission) {
        Permission perm = Permission.loadFromString(permission);
        
        ApiLayer.addPermission(null, CalculableType.GROUP, name, perm);
    }

    @Override
    public void removeGroupPermission(String world, String name, String permission) {
        ApiLayer.removePermission(world, CalculableType.GROUP, name, permission);
    }

    @Override
    public void removeGroupPermission(String name, String permission) {
        ApiLayer.removePermission(null, CalculableType.GROUP, name, permission);
    }
}
