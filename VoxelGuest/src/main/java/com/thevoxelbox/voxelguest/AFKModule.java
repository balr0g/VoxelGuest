package com.thevoxelbox.voxelguest;

import com.thevoxelbox.commands.Command;
import com.thevoxelbox.commands.CommandPermission;
import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@MetaData(name="AFK", description="Handles all AFK players and whatnot")
public class AFKModule extends Module {
    
    protected HashMap<Player, Long> timeMap = new HashMap<Player, Long>();
    protected List<Player> afkList = new ArrayList<Player>();
    
    private int afkTaskID = -1;
    
    public AFKModule() {
        super(AFKModule.class.getAnnotation(MetaData.class));
    }

    @Override
    public void enable() {
        timeMap.clear();
        
        for (Player player : Bukkit.getOnlinePlayers())
            timeMap.put(player, System.currentTimeMillis());
        
        if (VoxelGuest.getConfigData().getBoolean("afk-timeout-enabled")) {
            if (VoxelGuest.getConfigData().getInt("afk-timeout-minutes") >= 0) {
                final long timeout = 60000L * VoxelGuest.getConfigData().getInt("afk-timeout-minutes");
                
                afkTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(VoxelGuest.getInstance(), new Runnable() {

                    @Override
                    public void run() {
                        for (Map.Entry<Player, Long> entry : timeMap.entrySet()) {
                            Player player = entry.getKey();
                            
                            if ((System.currentTimeMillis() - entry.getValue() > timeout) && !isAFK(player)) {
                                setAFK(player, true);
                                broadcastAFKMessage(player);
                            }
                        }
                    }
                    
                }, 0L, 1800L);
            }
        }
    }
    
    @Override
    public void disable() {
        timeMap.clear();
        afkList.clear();
        
        if (afkTaskID != -1)
            Bukkit.getScheduler().cancelTask(afkTaskID);
        
        super.disable();
    }

    @Override
    public String getLoadMessage() {
        return "AFK module enabled - Auto-AFK timeout is " + (VoxelGuest.getConfigData().getBoolean("afk-timeout-enabled") ? "enabled" : "disabled");
    }
    
    public boolean isAFK(Player player) {
        return afkList.contains(player);
    }
    
    public void setAFK(Player player, boolean bool) {
        if (bool && !isAFK(player))
            afkList.add(player);
        if (!bool & isAFK(player))
            afkList.remove(player);
    }
    
    @Command(aliases={"afk", "vafk"},
            bounds={0, -1},
            help="To go AFK, type §c/afk (message)",
            playerOnly=true)
    @CommandPermission(permission="voxelguest.afk.afk")
    public void afk(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        
        if (args.length == 0 && !isAFK(p)) {
            setAFK(p, true);
            broadcastAFKMessage(p);
            return;
        } else if (!isAFK(p)) {
            String concat = "";
            
            for (int i = 0; i < args.length; i++) {
                concat = concat + args[i] + " ";
            }
            
            concat = concat.trim();
            
            setAFK(p, true);
            broadcastAFKMessage(p, concat);
            return;
        }
    }
    
    @ModuleEvent(event=PlayerJoinEvent.class)
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        updateTimeEntry(event.getPlayer());
        return false;
    }
    
    @ModuleEvent(event=PlayerQuitEvent.class)
    public boolean onPlayerQuit(PlayerQuitEvent event) {
        timeMap.remove(event.getPlayer());
        return false;
    }
    
    @ModuleEvent(event=PlayerKickEvent.class)
    public boolean onPlayerKick(PlayerKickEvent event) {
        timeMap.remove(event.getPlayer());
        return false;
    }
    
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player p = event.getPlayer();
        
        if (isAFK(p)) {
            setAFK(p, false);
            broadcastAFKMessage(p);
        }
        
        updateTimeEntry(p);
    }
    
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        
        if (isAFK(p)) {
            setAFK(p, false);
            broadcastAFKMessage(p);
        }
        
        updateTimeEntry(p);
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        
        if (isAFK(p)) {
            setAFK(p, false);
            broadcastAFKMessage(p);
        }
        
        updateTimeEntry(p);
    }
    
    private void updateTimeEntry(Player player) {
        timeMap.put(player, System.currentTimeMillis());
    }
    
    private void broadcastAFKMessage(Player player) {
        broadcastAFKMessage(player, null);
    }
    
    private void broadcastAFKMessage(Player player, String message) {
        if (message == null) {
            Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.DARK_GRAY + ((isAFK(player)) ? "has gone AFK" : "has returned"));
        } else {
            Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.DARK_GRAY + ((isAFK(player)) ? message : "has returned"));
        }
    }
}
