package com.thevoxelbox.voxelguest.permissions;

import com.thevoxelbox.voxelguest.VoxelGuest;
import java.io.File;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;

public class GuestPermissionsHandler extends PermissionsHandler {

    private final File file = new File("plugins/VoxelGuest/permissions.yml");
    private final YamlConfiguration yamlPerms = new YamlConfiguration();
    
    private final String header = "#########################################\n" +
            "#\n" +
            "# =============================\n" +
            "# VOXELGUEST 4 PERMISSIONS FILE\n" +
            "#\n" +
            "# Built by: psanker\n" +
            "# =============================\n" +
            "#\n" +
            "# This is a standard permissions system for VoxelGuest, which VoxelGuest handles after all\n" +
            "# other systems are disregarded OR the config explicitly says to use the VG perms system.\n" +
            "# This file accepts both UNIX LF line endings (\"\\n\") and Windows line endings (\"\\r\\n\").\n" +
            "#\n" +
            "# BE SURE TO USE SPACES AND NOT TABS. Use a good text editor to edit this file.\n" +
            "# Notepad++ is recommended for Windows, and TextMate is recommended for Mac.\n" +
            "#\n" +
            "# Comments start with a pound sign (like the beginning of this and the above lines).\n" +
            "# The YAML processor will ignore these lines. To verify your YAML syntax, please use\n" +
            "# http://yaml-online-parser.appspot.com/\n" +
            "#\n" +
            "#########################################\n" +
            "\n";
    
    @Override
    public PermissionsHandler initialize(Server server) {
        return new GuestPermissionsHandler(server);
    }
    
    public GuestPermissionsHandler(Server server) {
        try {
            
            this.server = server;
        
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            
            yamlPerms.load(file);
            yamlPerms.options().header(header);
            
        } catch (Throwable t) {
            VoxelGuest.log("Error in loading permissions file", 2);
            t.printStackTrace();
            return;
        }
    }

    @Override
    public String getDetectionMessage() {
        return "Using built-in permissions system";
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
}
