package com.thevoxelbox.voxelguest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class OfflineModeManager {
    
    protected YamlConfiguration tempBan = new YamlConfiguration();
    protected static HashMap<String, Long> timemap = new HashMap<String, Long>();
    protected static List<String> banned = new LinkedList<String>();
    
    private static File f = new File("plugins/VoxelGuest/tempban.yml");
    
    public OfflineModeManager() {
        if (!f.exists()) {
            try {
                f.createNewFile();
                return;
            } catch (IOException ex) {
                VoxelGuest.log("Could not create new " + f.getAbsolutePath(), 2);
                return;
            }
        }
        try {
            tempBan.load(f);
        } catch (FileNotFoundException ex) {
            return;
        } catch (IOException ex) {
            return;
        } catch (InvalidConfigurationException ex) {
            return;
        }
    }
    
    public boolean isActive() {
        return VoxelGuest.getInstance().getConfigData().getBoolean("offline-mode");
    }
    
    public boolean isPassword(String name, String input) {
        String protectedPass = VoxelGuest.getGuestPlayer(Bukkit.getPlayer(name)).get(VoxelGuest.getPluginId(VoxelGuest.getInstance()), "offline-password").toString();
        String test = "";
        byte[] shhash = new byte[40];

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(input.getBytes("iso-8859-1"), 0, input.length());
            shhash = md.digest();
            test = convertToHex(shhash);
            
            if (test.equals(protectedPass))
                return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        
        return false;
    }
    
    public void setPassword(String name, String input) throws CouldNotStoreOfflinePasswordException {
        byte[] shhash = new byte[40];
        String store = "";
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(input.getBytes("iso-8859-1"), 0, input.length());
            shhash = md.digest();
            store = convertToHex(shhash);
            
            VoxelGuest.getGuestPlayer(Bukkit.getPlayer(name)).store(VoxelGuest.getPluginId(VoxelGuest.getInstance()), "offline-password", store);
        } catch (NoSuchAlgorithmException e) {
            throw new CouldNotStoreOfflinePasswordException("Fatal error in storage - NoSuchAlgorithmException");
        } catch (UnsupportedEncodingException e) {
            throw new CouldNotStoreOfflinePasswordException("Fatal error in storage - UnsupportedEncodingException");
        }
    }
    
    public boolean isTempBanned(String player) {
        int counts = tempBan.getInt(player+".counts", 0);
        
        if (timemap.containsKey(player) && (System.currentTimeMillis() - timemap.get(player)) > 1800000) {
            tempBan.set(player+".counts", 0);
            
            if (banned.contains(player))
                banned.remove(player);
            
            return false;
        }
        
        if (banned.contains(player) && timemap.containsKey(player) && (System.currentTimeMillis() - timemap.get(player)) <= 1800000) {
            return true;
        } else if (banned.contains(player)) {
            timemap.put(player, System.currentTimeMillis());
            return true;
        } else if (counts >= 3) {
            timemap.put(player, System.currentTimeMillis());
            banned.add(player);
            return true;
        } else {
            return false;
        }
    }
    
    public void logTempBan(String player, String IP, Long time) {
        tempBan.set(player+".IP", IP);

        int counts = tempBan.getInt(player+".counts", 0);
        if (counts < 3) {
            counts++;
            
            if (banned.contains(player))
                banned.remove(player);
        }
        
        tempBan.set(player+".counts", counts);
        timemap.put(player, System.currentTimeMillis());
        
        
        if (counts >= 3)
            banned.add(player);
        try {
            tempBan.save(f);
        } catch (IOException ex) {
            VoxelGuest.log("Could not log temp ban on " + player, 1);
        }
    }
    
    public void removeTempBan(String player) {
        tempBan.set(player+".IP", null);
        tempBan.set(player+".counts", null);
        tempBan.set(player+".ban", null);
        tempBan.set(player, null);
        
        if (banned.contains(player))
            banned.remove(player);
        try {
            tempBan.save(f);
        } catch (IOException ex) {
            VoxelGuest.log("Could not remove temp ban on " + player, 1);
        }
    }
    
    private static String convertToHex(byte[] data) { 
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
}
