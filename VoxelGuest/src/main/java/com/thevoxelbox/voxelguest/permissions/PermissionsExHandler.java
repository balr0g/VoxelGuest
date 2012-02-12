package com.thevoxelbox.voxelguest.permissions;

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
}
