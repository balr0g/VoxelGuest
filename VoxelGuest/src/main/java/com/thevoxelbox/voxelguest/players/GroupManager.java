/*
 * Copyright (C) 2011 - 2012, psanker and contributors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are 
 * permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this list of 
 *   conditions and the following 
 * * Redistributions in binary form must reproduce the above copyright notice, this list of 
 *   conditions and the following disclaimer in the documentation and/or other materials 
 *   provided with the distribution.
 * * Neither the name of The VoxelPlugineering Team nor the names of its contributors may be 
 *   used to endorse or promote products derived from this software without specific prior 
 *   written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
