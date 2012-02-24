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

package com.thevoxelbox.voxelguest;

import com.thevoxelbox.commands.Command;
import com.thevoxelbox.commands.CommandPermission;
import com.thevoxelbox.permissions.PermissionsManager;
import com.thevoxelbox.voxelguest.modules.BukkitEventWrapper;
import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import com.thevoxelbox.voxelguest.modules.ModuleEventPriority;
import com.thevoxelbox.voxelguest.util.FlatFileManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

@MetaData(name="Vanish", description="Vanish in front of your peers!")
public class VanishModule extends Module {
    
    protected static List<String> vanished = new ArrayList<String>();
    protected static List<String> safeList = new ArrayList<String>();
    
    private String[] reloadVanishedList;
    
    public VanishModule() {
        super(VanishModule.class.getAnnotation(MetaData.class));
    }
    
    @Override
    public void enable() {
        reloadVanishedList = FlatFileManager.load("reload-vanished-list", "", true);
        
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PermissionsManager.getHandler().hasPermission(p.getName(), "voxelguest.vanish.safelist")) {
                addMemberToSafeList(p);
            }
        }
        
        if (reloadVanishedList != null) {
            for (String str : reloadVanishedList) {
                hidePlayer(Bukkit.getPlayer(str));
            }
        }
    }

    @Override
    public String getLoadMessage() {
        return "Vanish module loaded";
    }
    
    @Command(aliases="vanish",
            bounds={0,0},
            help="To toggle your vanish setting, type:\n"
            + "§c/vanish",
            playerOnly=true)
    @CommandPermission(permission="voxelguest.vanish.toggle")
    public void vanish(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        
        if (!vanished.contains(p.getName()))
            hidePlayer(p);
        else
            revealPlayer(p);
    }
    
    @ModuleEvent(event=PlayerJoinEvent.class, priority=ModuleEventPriority.LOW)
    public void onPlayerJoin(BukkitEventWrapper wrapper) {
        PlayerJoinEvent event = (PlayerJoinEvent) wrapper.getEvent();
        
        if (PermissionsManager.getHandler().hasPermission(event.getPlayer().getName(), "voxelguest.vanish.safelist")) {
            addMemberToSafeList(event.getPlayer());
            revealVanishedToPlayer(event.getPlayer());
        }
    }
    
    public static void hidePlayer(Player hidden) {
        if (!vanished.contains(hidden.getName())) {
            vanished.add(hidden.getName());
            
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (!safeList.contains(p.getName()))
                    p.hidePlayer(hidden);
            }
        }
    }
    
    public static void revealPlayer(Player hidden) {
        if (vanished.contains(hidden.getName())) {
            vanished.remove(hidden.getName());
            
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (!safeList.contains(p.getName()))
                    p.showPlayer(hidden);
            }
        }
    }
    
    public static void revealVanishedToPlayer(Player p) {
        Iterator<String> it = vanished.listIterator();
        
        while (it.hasNext()) {
            String vanishedName = it.next();
            Player vanishedPlayer = Bukkit.getPlayer(vanishedName);
            
            p.showPlayer(vanishedPlayer);
        }
    }
    
    public static void resetHiddenPlayer(Player hidden) {
        if (vanished.contains(hidden.getName())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (!safeList.contains(p.getName()))
                    p.hidePlayer(hidden);
            }
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
