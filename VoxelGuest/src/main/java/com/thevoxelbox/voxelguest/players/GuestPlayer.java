package com.thevoxelbox.voxelguest.players;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.thevoxelbox.voxelguest.VoxelGuest;
import com.thevoxelbox.voxelguest.util.IOManager;

public class GuestPlayer {

    protected Player p;
    protected Map<String, HashMap<String, Object>> storage = new HashMap<String, HashMap<String, Object>>();
    
    public GuestPlayer(Player player) {
        this.p = player;

        Map<String, Object> data = IOManager.load(p.getName(), "/players");
        storage.put(VoxelGuest.getPluginId(VoxelGuest.getInstance()), ((HashMap<String, Object>) data));
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
}
