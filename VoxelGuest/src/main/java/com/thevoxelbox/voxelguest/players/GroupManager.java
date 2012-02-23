package com.thevoxelbox.voxelguest.players;

import com.thevoxelbox.voxelguest.util.Configuration;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    
    public static Configuration getGroupConfiguration(String name) {
        if (groupMap.containsKey(name))
            return groupMap.get(name);
        
        return null;
    }
    
    public static void setGroupConfiguration(String name, Configuration config) {
        groupMap.put(name, config);
    }
    
    public static String findGroup(String key, Object value) throws GroupNotFoundException {
        for (Map.Entry<String, Configuration> entry : groupMap.entrySet()) {
            Configuration config = entry.getValue();
            
            if (config.getEntry(key) != null && value.equals(config.getEntry(key)))
                return entry.getKey();
        }
        
        throw new GroupNotFoundException("No group found for key-value pair");
    }
    
    public static void saveGroupConfigurations() {
        for (Map.Entry<String, Configuration> entry : groupMap.entrySet()) {
            saveGroupConfiguration(entry.getKey());
        }
    }
    
    public static void saveGroupConfiguration(String name) {
        Configuration config = groupMap.get(name);
        config.save();
    }
}
