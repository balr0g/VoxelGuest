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

package com.thevoxelbox.voxelguest.players;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.thevoxelbox.voxelguest.VoxelGuest;
import com.thevoxelbox.permissions.PermissionsManager;
import com.thevoxelbox.voxelguest.util.PropertyManager;

public class GuestPlayer {

    protected Player p;
    protected Map<String, HashMap<String, Object>> storage = new HashMap<String, HashMap<String, Object>>();
    protected String[] groups;
    
    public GuestPlayer(Player player) {
        this.p = player;

        Map<String, Object> data = PropertyManager.load(p.getName(), "/players");
        storage.put(VoxelGuest.getPluginId(VoxelGuest.getInstance()), ((HashMap<String, Object>) data));
        
        groups = PermissionsManager.getHandler().getGroups(p.getName());
    }

    public Player getPlayer() {
        return this.p;
    }

    // -- Storage Stuff -- //
    public void store(String id, String k, Object v) {
        if (!storage.containsKey(id)) {
            VoxelGuest.log("Tried to store to unregistered plugin storage for player \"" + p.getName() + "\"", 1);
            return;
        }

        if (storage.get(k) != null) {
            HashMap<String, Object> _map = storage.get(k);
            _map.put(k, v);
            storage.put(id, _map);
        } else {
            HashMap<String, Object> _map = new HashMap<String, Object>();
            _map.put(k, v);
            storage.put(id, _map);
        }
    }

    public Object get(String id, String k) {
        if (!storage.containsKey(id)) {
            VoxelGuest.log("Tried to retrieve from unregistered plugin storage for player \"" + p.getName() + "\"", 1);
            return null;
        }

        if (storage.get(k) != null) {
            HashMap<String, Object> _map = storage.get(k);
            return _map.get(k);
        } else {
            return null;
        }
    }
    
    public void saveData(String pluginID) {
        HashMap<String, Object> map = storage.get(pluginID);
        
        if (map != null)
            PropertyManager.save(p.getName(), map, "/players");
    }
    
    public String[] getGroups() {
        return groups;
    }
}
