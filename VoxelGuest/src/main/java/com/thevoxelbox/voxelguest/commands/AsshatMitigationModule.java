package com.thevoxelbox.voxelguest.commands;

import com.thevoxelbox.commands.Command;
import com.thevoxelbox.commands.CommandPermission;
import com.thevoxelbox.voxelguest.VoxelGuest;
import com.thevoxelbox.voxelguest.modules.BukkitEventWrapper;
import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import com.thevoxelbox.voxelguest.util.PropertyManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

/**
 *
 * @author Razorcane
 */
@MetaData(name="Asshat Mitigator", description="Major asshat handling.")
public class AsshatMitigationModule extends Module {
    
    public HashMap<String, Object> banned = new HashMap();
    public HashSet<String> gagged = new HashSet();
    private Server s = Bukkit.getServer();
    
    public AsshatMitigationModule(){
        super(AsshatMitigationModule.class.getAnnotation(MetaData.class));
    }

    @Override
    public void enable() {
        banned.clear();
        gagged.clear();
    }
    
    @Override
    public void disable() {
        PropertyManager.save("banned", banned, "/asshatmitigation");
    }

    @Override
    public String getLoadMessage() {
        return "Asshat Mitigator has been loaded.";
    }
    
    @Command(aliases = {"ban", "vban", "vbano", "bano"},
            bounds = {1, -1},
            help = "To ban someone, simply type\n"
            + "§c/ban [player] (reason)",
            playerOnly=false)
    @CommandPermission(permission="voxelguest.asshat.ban")
    public void ban(CommandSender cs, String[] args) {
        List<Player> l = s.matchPlayer(args[0]);
        String reason = "";
        
        if(args.length > 1){
            for(int i = 1; i < args.length; i++){
                reason += args[i] + " ";
            }
        }
        
        if(l.size() < 1){
            cs.sendMessage(ChatColor.RED + "Partial match.");
        }
        else if(l.isEmpty()){
            String player = args[0];
            
            banned.put(player, reason);
            if(args.length > 1){
                s.broadcastMessage(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + player + ChatColor.DARK_GRAY + " has been banned by " + ChatColor.RED + cs.getName() + ChatColor.DARK_GRAY + " for:");
                s.broadcastMessage(ChatColor.BLUE + reason);
            }
            else{
                s.broadcastMessage(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + player + ChatColor.DARK_GRAY + " has been banned by " + ChatColor.RED + cs.getName() + ChatColor.DARK_GRAY + ".");
            }
        }
        else{
            l.get(0).kickPlayer(reason);
            banned.put(l.get(0).getName(), reason);
            if(args.length > 1){
                s.broadcastMessage(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + l.get(0).getName() + ChatColor.DARK_GRAY + " has been banned by " + ChatColor.RED + cs.getName() + ChatColor.DARK_GRAY + " for:");
                s.broadcastMessage(ChatColor.BLUE + reason);
            }
            else{
                s.broadcastMessage(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + l.get(0).getName() + ChatColor.DARK_GRAY + " has been banned by " + ChatColor.RED + cs.getName() + ChatColor.DARK_GRAY + ".");
            }
        }
        
        if(VoxelGuest.getConfigData().getBoolean("save-banlist-on-ban")){
            PropertyManager.save("banned", banned, "/asshatmitigation");
        }
    }
    
    @Command(aliases = {"unban", "vunban"},
            bounds = {1, -1},
            help = "To unban someone, simply type\n"
            + "§c/unban [player]",
            playerOnly=false)
    @CommandPermission(permission="voxelguest.asshat.unban")
    public void unban(CommandSender cs, String[] args){
        if(args.length < 1){
            cs.sendMessage(ChatColor.RED + "Invalid arguments.");
        }
        else if(args.length > 1){
            cs.sendMessage(ChatColor.RED + "Too many arguments.");
        }
        else{
            String player = args[0];
            if(banned.containsKey(player)){
                banned.remove(player);
            }
            else{
                cs.sendMessage(ChatColor.RED + "Player isn't banned.");
            }
        }
    }
    
    @Command(aliases={"gag","vgag"},
            bounds= {1, -1},
            help="To gag someone, simply type\n"
            + "§c/gag [player] (reason)",
            playerOnly=false)
    @CommandPermission(permission="voxelguest.asshat.gag")
    public void gag(CommandSender cs, String[] args) {
        List<Player> l = s.matchPlayer(args[0]);
        String reason = "";
        
        if(args.length > 1){
            for(int i = 1; i < args.length; i++){
                reason += args[i] + " ";
            }
        }
        
        if(l.size() < 1){
            cs.sendMessage(ChatColor.RED + "Partial match.");
        }
        else if(l.isEmpty()){
            cs.sendMessage(ChatColor.RED + "No player to match.");
        }
        else{
            if(gagged.contains(l.get(0).getName())){
                gagged.remove(l.get(0).getName());
                cs.sendMessage(ChatColor.RED + l.get(0).getName() + ChatColor.WHITE + " has been ungagged.");
            }
            else{
                gagged.add(l.get(0).getName());
                if(args.length > 1){
                    s.broadcastMessage(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + l.get(0).getName() + ChatColor.DARK_GRAY + " has been gagged by " + ChatColor.RED + cs.getName() + ChatColor.DARK_GRAY + " for:");
                    s.broadcastMessage(ChatColor.BLUE + reason);
                    l.get(0).sendMessage(VoxelGuest.getConfigData().getString("gag-message-format"));
                }
                else{
                    s.broadcastMessage(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + l.get(0).getName() + ChatColor.DARK_GRAY + " has been gagged by " + ChatColor.RED + cs.getName() + ChatColor.DARK_GRAY + ".");
                    l.get(0).sendMessage(VoxelGuest.getConfigData().getString("gag-message-format"));
                }
            }
        }
    }
    
    @Command(aliases={"kick","vkick"},
            bounds= {1, -1},
            help="To kick someone, simply type\n"
            + "§c/kick [player] (reason)",
            playerOnly=false)
    @CommandPermission(permission="voxelguest.asshat.kick")
    public void kick(CommandSender cs, String[] args) {
        List<Player> l = s.matchPlayer(args[0]);
        String reason = "";
        
        if(args.length > 1){
            for(int i = 1; i < args.length; i++){
                reason += args[i] + " ";
            }
        }
        
        if(l.size() < 1){
            cs.sendMessage(ChatColor.RED + "Partial match.");
        }
        else if(l.isEmpty()){
            cs.sendMessage(ChatColor.RED + "No player to match.");
        }
        else{
            l.get(0).kickPlayer(reason);
            if(args.length > 1){
                s.broadcastMessage(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + l.get(0).getName() + ChatColor.DARK_GRAY + " has been kicked by " + ChatColor.RED + cs.getName() + ChatColor.DARK_GRAY + " for:");
                s.broadcastMessage(ChatColor.BLUE + reason);
            }
            else{
                s.broadcastMessage(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + l.get(0).getName() + ChatColor.DARK_GRAY + " has been kicked by " + ChatColor.RED + cs.getName() + ChatColor.DARK_GRAY + ".");
            }
        }
    }
    
    @Command(aliases={"mute","vmute"},
            bounds= {2, 2},
            help="To mute someone, simply type\n"
            + "§c/mute -p [player]\n"
            + "To mute a group, simply type\n"
            + "§c/mute -g [group name]",
            playerOnly=false)
    @CommandPermission(permission="voxelguest.asshat.mute")
    public void mute(CommandSender cs, String[] args) {
        //TODO
    }
    
    @ModuleEvent(event=PlayerPreLoginEvent.class)
    public void onPlayerPreLogin(BukkitEventWrapper wrapper){
        PlayerPreLoginEvent event = (PlayerPreLoginEvent) wrapper.getEvent();
        String player = event.getName();
        
        if(banned.containsKey(player)){
            event.setResult(PlayerPreLoginEvent.Result.KICK_FULL);
            event.disallow(PlayerPreLoginEvent.Result.KICK_FULL, banned.get(player).toString());
        }
    }
    
    @ModuleEvent(event=PlayerChatEvent.class)
    public void onPlayerChat(BukkitEventWrapper wrapper){
        PlayerChatEvent event = (PlayerChatEvent) wrapper.getEvent();
        Player p = event.getPlayer();
        
        if(gagged.contains(p.getName())){
            if(event.getMessage().equals(VoxelGuest.getConfigData().getString("unrestrict-chat-message"))){
                gagged.remove(p.getName());
                p.sendMessage(VoxelGuest.getConfigData().getString("ungag-message-format"));
                event.setCancelled(true);
            }
            else{
                p.sendMessage(VoxelGuest.getConfigData().getString("gag-message-format"));
                event.setCancelled(true);
            }
        }
    }
}
