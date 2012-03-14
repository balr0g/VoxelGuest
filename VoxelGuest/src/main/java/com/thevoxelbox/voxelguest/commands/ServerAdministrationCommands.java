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
package com.thevoxelbox.voxelguest.commands;

import com.thevoxelbox.commands.Command;
import com.thevoxelbox.commands.CommandPermission;
import com.thevoxelbox.voxelguest.VoxelGuest;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleManager;
import java.lang.management.ManagementFactory;
import java.text.NumberFormat;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class ServerAdministrationCommands {
    private long pollInterval = 150L;
    private long lastTimestamp = 0L;
    private long lastDifference = 0L;
    
    public ServerAdministrationCommands() {
        lastTimestamp = System.currentTimeMillis();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(VoxelGuest.getInstance(), new Runnable() {

            @Override
            public void run() {
                lastDifference = (System.currentTimeMillis() - lastTimestamp) / 1000;
                lastTimestamp = System.currentTimeMillis();
            }
            
        }, 0L, pollInterval);
    }
    
    @Command(aliases = {"system", "sys"},
        bounds = {0, -1})
    @CommandPermission(permission = "system.admin")
    public void system(CommandSender cs, String[] args) {
        if (args != null) {
            if (args[0].equalsIgnoreCase("settings")) {
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("set")) {
                    }
                }

                cs.sendMessage("§8==============================");
                cs.sendMessage("§bVoxelGuest Master Settings");
                cs.sendMessage("§8==============================");

                for (Map.Entry<String, Object> entry : VoxelGuest.getConfigData().getAllEntries().entrySet()) {
                    cs.sendMessage("§7" + entry.getKey() + "§6=§a" + entry.getValue().toString());
                }

                for (Module module : ModuleManager.getManager().getModules()) {
                    if (module.getConfiguration() != null) {
                        cs.sendMessage("§8==============================");
                        cs.sendMessage("§fModule§f: §6" + module.getName());
                        cs.sendMessage("§8==============================");

                        for (Map.Entry<String, Object> entry : module.getConfiguration().getAllEntries().entrySet()) {
                            cs.sendMessage("§7" + entry.getKey() + "§6=§a" + entry.getValue().toString());
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("modules")) {
                cs.sendMessage("§8==============================");
                cs.sendMessage("§bLoaded Modules");
                cs.sendMessage("§8==============================");

                Module[] activeModules = ModuleManager.getManager().getActiveModules();
                Module[] inactiveModules = ModuleManager.getManager().getInactiveModules();

                if (activeModules != null || activeModules.length > 0) {
                    for (Module module : activeModules) {
                        cs.sendMessage("§a" + module.getName() + "§f:§7 " + module.getDescription());
                    }
                }

                if (inactiveModules != null || inactiveModules.length > 0) {
                    for (Module module : inactiveModules) {
                        cs.sendMessage("§c" + module.getName() + "§f:§7 " + module.getDescription());
                    }
                }
            } else if (args[0].equalsIgnoreCase("specs")) {
                cs.sendMessage("§8==============================");
                cs.sendMessage("§bServer Specs");
                
                cs.sendMessage("§7Operating System§f: §a" + ManagementFactory.getOperatingSystemMXBean().getName() + " version " + ManagementFactory.getOperatingSystemMXBean().getVersion());
                cs.sendMessage("§7Architecture§f: §a" + ManagementFactory.getOperatingSystemMXBean().getArch());
                
                cs.sendMessage("§8==============================");
                cs.sendMessage("§bCPU Specs");
                double rawCPUUsage = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
                cs.sendMessage("§7CPU Usage§f: " + renderBar(rawCPUUsage, (Runtime.getRuntime().availableProcessors())));
                cs.sendMessage("§7Available cores§f: §a" + Runtime.getRuntime().availableProcessors());
                
                cs.sendMessage("§8==============================");
                cs.sendMessage("§bMemory Specs");
                double memUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576;
		double memMax = Runtime.getRuntime().maxMemory() / 1048576;
                
                cs.sendMessage("§7JVM Memory§f: " + renderBar(memUsed, memMax));
                cs.sendMessage("§7JVM Heap Memory (MB)§f: §a" + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1048576);
                cs.sendMessage("§7JVM Free Memory (MB)§f: §a" + Runtime.getRuntime().freeMemory() / 1048576);
                cs.sendMessage("§7JVM Maximum Memory (MB)§f: §a" + ((Runtime.getRuntime().maxMemory() == Long.MAX_VALUE) ? "No defined limit" : Runtime.getRuntime().maxMemory() / 1048576));
                cs.sendMessage("§7JVM Used Memory (MB)§f: §a" + Runtime.getRuntime().totalMemory() / 1048576);
                
                cs.sendMessage("§8==============================");
                cs.sendMessage("§bBukkit Specs");
                
                World[] loadedWorlds = new World[Bukkit.getWorlds().size()];
                loadedWorlds = Bukkit.getWorlds().toArray(loadedWorlds);
                
                cs.sendMessage("§7Loaded Worlds§f:");
                
                for (World world : loadedWorlds) {
                    Chunk[] chunks = world.getLoadedChunks();
                    
                    Entity[] entities = new Entity[world.getEntities().size()];
                    entities = world.getEntities().toArray(entities);
                    
                    cs.sendMessage("§a- " + world.getName() + " §7[§fChunks: §6" + chunks.length + "§f, Entities: §6" + entities.length + "§7]");
                }
                
                String ticks = "§7TPS§f: ";
                
                if (lastDifference == 0L) {
                    cs.sendMessage(ticks + "§cNo TPS poll yet");
                } else {
                    cs.sendMessage(ticks + renderTPSBar(calculateTPS(), 20));
                }
            }
        }
    }
    
    private String renderBar(double value, double max) {
        double usedLevel = 20 * (value / max);
        int usedRounded = (int) Math.round(usedLevel);
        String bar = "§8[";
        
        for (int i = 0; i < 20; i++) {
            if ((i + 1) <= usedRounded) {
                bar += "§b#";
            } else {
                bar += "§7_";
            }
        }
        
        double percent = (usedLevel / 20);
        NumberFormat format = NumberFormat.getPercentInstance();
        
        return (bar + "§8] (§f" + format.format(percent) + "§8)");
    }
    
    private String renderTPSBar(double value, double max) {
        double usedLevel = 20 * (value / max);
        int usedRounded = (int) Math.round(usedLevel);
        String bar = "§8[";
        
        for (int i = 0; i < 20; i++) {
            if ((i + 1) <= usedRounded) {
                bar += "§b#";
            } else {
                bar += "§7_";
            }
        }
        
        double percent = (usedLevel / 20);
        if (percent > 1) {
            percent = 1;
        }
        
        return (bar + "§8] (§f" + percent * 20 + " TPS§8)");
    }
    
    private double calculateTPS() {
        if (lastDifference == 0L)
            lastDifference = 1L;
        
        double tps = pollInterval / lastDifference;
        return tps;
    }
}
