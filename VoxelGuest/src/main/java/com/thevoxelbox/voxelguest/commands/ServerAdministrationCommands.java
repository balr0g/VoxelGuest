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
import java.util.Map;
import org.bukkit.command.CommandSender;

public class ServerAdministrationCommands {
    @Command(aliases={"vsystem", "vsys"},
            bounds={0,-1})
    @CommandPermission(permission="voxelguest.admin")
    public void system(CommandSender cs, String[] args) {
        if (args != null) {
            if (args[0].equalsIgnoreCase("settings")) {
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
            }
        }
    }
}
