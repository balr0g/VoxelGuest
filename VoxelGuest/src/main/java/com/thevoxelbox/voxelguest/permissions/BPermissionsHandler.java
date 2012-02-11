/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.permissions;

import org.bukkit.Server;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author psanker
 */
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
    
}
