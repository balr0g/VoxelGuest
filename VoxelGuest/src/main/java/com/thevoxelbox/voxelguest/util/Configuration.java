/*
 * VoxelGuest
 *
 * Copyright (C) 2011, 2012 psanker and contributors

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.thevoxelbox.voxelguest.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Configuration {
    private HashMap<String, Object> map = new HashMap<String, Object>();
    private String target;
    private String destination;
    
    public Configuration (String target) {
        HashMap<String, Object> data = (HashMap<String, Object>) PropertyManager.load(target);
        
        if (data == null)
            map.clear();
        else
            map = data;
        
        this.target = target;
        this.destination = "";
    }
    
    public Configuration(String target, String destination) {
        HashMap<String, Object> data = (HashMap<String, Object>) PropertyManager.load(target, destination);
        
        if (data == null)
            map.clear();
        else
            map = data;
        
        this.target = target;
        this.destination = destination;
    }
    
    public void load() {
        HashMap<String, Object> data = (HashMap<String, Object>) PropertyManager.load(target, destination);
        
        if (data == null)
            return;
        else
            map = data;
    }
    
    public void save() {
        if (map == null)
            return;
        
        PropertyManager.save(target, map, destination);
    }
    
    public Object getEntry(String key) {
        if (map == null)
            return null;
        else if (!map.containsKey(key))
            return null;
        else
            return map.get(key);
    }
    
    public Object[] getArray(String key) {
        if (map == null)
            return null;
        else if (!map.containsKey(key))
            return null;
        else if (!(map.get(key) instanceof Object[]))
            return null;
        else
            return (Object[]) map.get(key);
    }
    
    public String getString(String key) {
        if (map == null)
            return null;
        else if (!map.containsKey(key))
            return null;
        else if (!(map.get(key) instanceof String))
            return null;
        else
            return map.get(key).toString();
    }
    
    public List<String> getStringList(String key) {
        if (map == null)
            return null;
        else if (!map.containsKey(key))
            return null;
        else if (!(map.get(key) instanceof String[]))
            return null;
        else
            return Arrays.asList((String[]) map.get(key));
    }
    
    public boolean getBoolean(String key) {
        if (map == null)
            return false;
        else if (!map.containsKey(key))
            return false;
        else if (!(map.get(key) instanceof Boolean))
            return false;
        else
            return ((Boolean) map.get(key)).booleanValue();
    }
    
    public int getInt(String key) {
        if (map == null)
            return -1;
        else if (!map.containsKey(key))
            return -1;
        else if (!(map.get(key) instanceof Integer))
            return -1;
        else
            return ((Integer) map.get(key)).intValue();
    }
    
    public void setEntry(String key, Object value) {
        if (map == null)
            return;
        
        map.put(key, value);
    }
    
    public void setArray(String key, Object[] value) {
        if (map == null)
            return;
        
        map.put(key, value);
    }
    
    public void setString(String key, String value) {
        if (map == null)
            return;
        
        map.put(key, value);
    }
    
    public void setStringList(String key, List<String> value) {
        if (map == null)
            return;
        
        Object[] objs = value.toArray();
        String[] strs = new String[objs.length];
        
        for (int i = 0; i < strs.length; i++) {
            strs[i] = objs[i].toString();
        }
        
        map.put(key, strs);
    }
    
    public void setBoolean(String key, boolean value) {
        if (map == null)
            return;
        
        map.put(key, Boolean.valueOf(value));
    }
    
    public void setInt(String key, int value) {
        if (map == null)
            return;
        
        map.put(key, Integer.valueOf(value));
    }
}
