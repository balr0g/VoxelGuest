/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 *
 * @author patrick
 */
public class ChatEventListener implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(PlayerChatEvent event) {
        
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    
    }
}
