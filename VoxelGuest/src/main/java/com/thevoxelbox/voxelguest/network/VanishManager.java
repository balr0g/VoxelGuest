/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.network;

import java.util.TreeSet;
import net.minecraft.server.Packet201PlayerInfo;
import net.minecraft.server.Packet20NamedEntitySpawn;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author patrick
 */
public class VanishManager {
    
    protected static TreeSet<String> vanished = new TreeSet<String>();
    protected static TreeSet<String> safeList = new TreeSet<String>();
    
    public void hidePlayer(Player hidden) {
        if (!vanished.contains(hidden.getName())) {
            vanished.add(hidden.getName());
            VanishUtils.sendPacketNearby(hidden.getLocation(), 300, new CloakPacket(((CraftPlayer) hidden).getEntityId()));
            VanishUtils.sendPacketNearby(hidden.getLocation(), 300, new Packet201PlayerInfo(hidden.getName(), false, 0));
        }
    }
    
    public void revealPlayer(Player hidden) {
        if (vanished.contains(hidden.getName())) {
            vanished.remove(hidden.getName());
            VanishUtils.sendPacketNearby(hidden.getLocation(), 300, new Packet20NamedEntitySpawn(((CraftPlayer) hidden).getHandle()));
            VanishUtils.sendPacketNearby(hidden.getLocation(), 300, new Packet201PlayerInfo(hidden.getName(), true, 1));
        }
    }
    
    public void resetHiddenPlayer(Player hidden) {
        if (vanished.contains(hidden.getName())) {
            VanishUtils.sendPacketNearby(hidden.getLocation(), 300, new CloakPacket(((CraftPlayer) hidden).getEntityId()));
            VanishUtils.sendPacketNearby(hidden.getLocation(), 300, new Packet201PlayerInfo(hidden.getName(), false, 0));
        }
    }
    
    public static void addMemberToSafeList(Player p) {
        if (!safeList.contains(p.getName()))
            safeList.add(p.getName());
    }
    
    public static boolean isOnSafeList(Player p) {
        return safeList.contains(p.getName());
    }
    
    public static boolean isVanished(Player p) {
        return vanished.contains(p.getName());
    }
}
