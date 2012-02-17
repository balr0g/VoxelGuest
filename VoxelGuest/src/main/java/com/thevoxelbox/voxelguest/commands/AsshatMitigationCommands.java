/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.commands;

import com.thevoxelbox.commands.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author patrick
 */
public class AsshatMitigationCommands {

    @Command(aliases = {"ban", "vban", "vbano", "bano"},
            bounds = {1, -1},
            help = "To ban someone, simply type\n"
            + "§c/ban [player] (reason)",
            playerOnly=false)
    public void ban(CommandSender cs, String[] args) {
        
    }
    
    @Command(aliases={"gag","vgag"},
            bounds= {1, -1},
            help="To gag someone, simply type\n"
            + "§c/gag [player] (reason)",
            playerOnly=false)
    public void gag(CommandSender cs, String[] args) {
        
    }
    
    @Command(aliases={"kick","vkick"},
            bounds= {1, -1},
            help="To kick someone, simply type\n"
            + "§c/kick [player] (reason)",
            playerOnly=false)
    public void kick(CommandSender cs, String[] args) {
        
    }
    
    @Command(aliases={"mute","vmute"},
            bounds= {2, 2},
            help="To mute someone, simply type\n"
            + "§c/mute -p [player]\n"
            + "To mute a group, simply type\n"
            + "§c/mute -g [group name]",
            playerOnly=false)
    public void mute(CommandSender cs, String[] args) {
        
    }
}
