package com.thevoxelbox.voxelguest.permissions;

import org.bukkit.Server;

public class GuestPermissionsHandler extends PermissionsHandler {
    /*
     * ===========================
     * BOUNCER PERMISSIONS SYSTEM
     * 
     * by: psanker
     * ===========================
     * 
     */
    
    @Override
    public PermissionsHandler initialize(Server server) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermission(String name, String permission) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermission(String world, String name, String permission) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean inGroup(String name, String group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] getGroups(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDetectionMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
