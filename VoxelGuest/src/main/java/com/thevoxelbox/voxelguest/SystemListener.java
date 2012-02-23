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

package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.modules.ModuleSystemListener;
import com.thevoxelbox.voxelguest.players.GuestPlayer;
import com.thevoxelbox.voxelguest.util.FormatException;
import com.thevoxelbox.voxelguest.util.Formatter;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SystemListener extends ModuleSystemListener {
    
    @EventHandler(priority = EventPriority.HIGH) 
    public void onPlayerJoin(PlayerJoinEvent event) {
        GuestPlayer gp = VoxelGuest.registerPlayer(event.getPlayer());
        
        try {
            String format = VoxelGuest.getConfigData().getString("join-message-format");
            event.setJoinMessage(formatJoinQuitKickMessage(format, gp));
        } catch (FormatException ex) {
            VoxelGuest.log(ex.getMessage(), 1);
        } catch (NullPointerException ex) {
            event.setJoinMessage(ChatColor.YELLOW + gp.getPlayer().getName() + " joined");
            ex.printStackTrace();
        }
        
        super.processModuleEvents(event);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        GuestPlayer gp = VoxelGuest.getGuestPlayer(event.getPlayer());
        VoxelGuest.unregsiterPlayer(gp);
        gp.saveData(VoxelGuest.getPluginId(VoxelGuest.getInstance()));
        
        try {
            String format = VoxelGuest.getConfigData().getString("leave-message-format");
            event.setQuitMessage(formatJoinQuitKickMessage(format, gp));
        } catch (FormatException ex) {
            VoxelGuest.log(ex.getMessage(), 1);
        } catch (NullPointerException ex) {
            event.setQuitMessage(ChatColor.YELLOW + gp.getPlayer().getName() + " left");
        }
        
        super.processModuleEvents(event);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKick(PlayerKickEvent event) {
        GuestPlayer gp = VoxelGuest.getGuestPlayer(event.getPlayer());
        VoxelGuest.unregsiterPlayer(gp);
        gp.saveData(VoxelGuest.getPluginId(VoxelGuest.getInstance()));
        
        try {
            String format = VoxelGuest.getConfigData().getString("kick-message-format");
            event.setLeaveMessage(formatJoinQuitKickMessage(format, gp));
        } catch (FormatException ex) {
            VoxelGuest.log(ex.getMessage(), 1);
        } catch (NullPointerException ex) {
            event.setLeaveMessage(ChatColor.YELLOW + gp.getPlayer().getName() + " was kicked out");
        }
        
        super.processModuleEvents(event);
    }
    
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        super.processModuleEvents(event);
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        super.processModuleEvents(event);
    }
    
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        super.processModuleEvents(event);
    }
    
    private String formatJoinQuitKickMessage(String format, GuestPlayer gp) throws FormatException {
        if (format.contains("\n"))
            throw new FormatException("Line feeds are not accepted in join/quit/kick messages");
        
        return Formatter.selectFormatter(SimpleFormatter.class).format(format, gp)[0];
    }
}
