package com.thevoxelbox.voxelguest.permissions;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;

// -- From WEPIF

public class DinnerpermsHandler extends PermissionsHandler {

    private static final String GROUP_PREFIX = "group.";
    
    @Override
    public PermissionsHandler initialize(Server server) {
        return new DinnerpermsHandler(server);
    }
    
    public DinnerpermsHandler(Server server) {
        this.server = server;
    }

    @Override
    public String getDetectionMessage() {
        return "Using Bukkit's permissions as permissions system";
    }

    @Override
    public boolean hasPermission(String name, String permission) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Permissible perms = getPermissible(op);
        
        if (perms == null)
            return false;
        
        switch (internalHasPermission(perms, permission)) {
            case -1:
                return false;
            case 1:
                return true;
        }
        
        int dotPos = permission.lastIndexOf(".");
        while (dotPos > -1) {
            switch (internalHasPermission(perms, permission.substring(0, dotPos + 1) + "*")) {
                case -1:
                    return false;
                case 1:
                    return true;
            }
            
            dotPos = permission.lastIndexOf(".", dotPos - 1);
        }
        
        return internalHasPermission(perms, "*") == 1;
    }

    @Override
    public boolean hasPermission(String world, String name, String permission) {
        return hasPermission(name, permission);
    }

    @Override
    public boolean inGroup(String name, String group) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        
        final Permissible perms = getPermissible(op);
        
        if (perms == null) {
            return false;
        }

        final String perm = GROUP_PREFIX + group;
        return perms.isPermissionSet(perm) && perms.hasPermission(perm);
    }

    @Override
    public String[] getGroups(String name) {
        OfflinePlayer op = server.getOfflinePlayer(name);
        
        Permissible perms = getPermissible(op);
        
        if (perms == null) {
            return new String[0];
        }
        
        List<String> groupNames = new ArrayList<String>();
        
        for (PermissionAttachmentInfo permAttach : perms.getEffectivePermissions()) {
            String perm = permAttach.getPermission();
            if (!(perm.startsWith(GROUP_PREFIX) && permAttach.getValue())) {
                continue;
            }
            groupNames.add(perm.substring(GROUP_PREFIX.length(), perm.length()));
        }
        
        return groupNames.toArray(new String[groupNames.size()]);
    }
    
    private Permissible getPermissible(OfflinePlayer offline) {
        if (offline == null) return null;
        
        Permissible perm = null;
        
        if (offline instanceof Permissible) {  
            perm = (Permissible) offline;
        } else {
            Player player = offline.getPlayer();
            if (player != null) perm = player;
        }
        
        return perm;
    }
    
    public int internalHasPermission(Permissible perms, String permission) {
        if (perms.isPermissionSet(permission)) {
            return perms.hasPermission(permission) ? 1 : -1;
        } else {
            Permission perm = server.getPluginManager().getPermission(permission);
            
            if (perm != null) {
                return perm.getDefault().getValue(perms.isOp()) ? 1 : 0;
            } else {
                return 0;
            }
        }
    }
    
}
