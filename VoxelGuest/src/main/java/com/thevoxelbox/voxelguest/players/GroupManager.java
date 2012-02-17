package com.thevoxelbox.voxelguest.players;

import com.thevoxelbox.voxelguest.util.Configuration;
import java.io.File;
import java.util.HashMap;

public class GroupManager {
    
    protected static HashMap<String, Configuration> groupMap = new HashMap<String, Configuration>();
    
    public GroupManager() {
        File dir = new File("plugins/VoxelGuest/data/groups/");
        
        if (!dir.isDirectory()) {
            dir.mkdirs();
            return;
        }
        
        String[] files = dir.list();
        
        for (String file : files) {
            if (file.endsWith(".properties")) {
                String f = file.replace(".properties", "");
                Configuration config = new Configuration(f, "/groups");
                groupMap.put(f, config);
            }
        }
    }
    
    public Configuration getGroupConfiguration(String name) {
        if (groupMap.containsKey(name))
            return groupMap.get(name);
        
        return null;
    }
    
    public void setGroupConfiguration(String name, Configuration config) {
        groupMap.put(name, config);
    }
}
