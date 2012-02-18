package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleException;
import com.thevoxelbox.voxelguest.players.GuestPlayer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SystemListener implements Listener {
    
    @EventHandler(priority = EventPriority.HIGH) 
    public void onPlayerJoin(PlayerJoinEvent event) {
        VoxelGuest.registerPlayer(event.getPlayer());
        GuestPlayer gp = VoxelGuest.getGuestPlayer(event.getPlayer());
        
        runModuleEventChecks(event);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        GuestPlayer gp = VoxelGuest.getGuestPlayer(event.getPlayer());
        VoxelGuest.unregsiterPlayer(gp);
        gp.saveData(VoxelGuest.getPluginId(VoxelGuest.getInstance()));
        
        runModuleEventChecks(event);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKick(PlayerKickEvent event) {
        GuestPlayer gp = VoxelGuest.getGuestPlayer(event.getPlayer());
        VoxelGuest.unregsiterPlayer(gp);
        gp.saveData(VoxelGuest.getPluginId(VoxelGuest.getInstance()));
        
        runModuleEventChecks(event);
    }
    
    public synchronized void runModuleEventChecks(Event event) {
        for (Module module : VoxelGuest.getModules()) {
            try {
                boolean cancel = checkModuleEventMethods(module, event);
                
                if (cancel) break;
            } catch (ModuleException ex) {
                VoxelGuest.log(module.getName(), ex.getMessage(), 2);
                ex.printStackTrace();
                continue;
            }
        }
    }
    
    public boolean checkModuleEventMethods(Module module, Event event) throws ModuleException {
        Method[] methods = module.getClass().getDeclaredMethods();
        
        for (Method method : methods) {
            if (method.isAnnotationPresent(ModuleEvent.class)) {
                ModuleEvent ee = method.getAnnotation(ModuleEvent.class);
                
                if (ee.event() == event.getClass()) {
                    try {
                        boolean isCancelled = (Boolean) method.invoke(module, event);
                        return isCancelled;
                    } catch (IllegalAccessException ex) {
                        throw new ModuleException("Could not execute @ModuleEvent method: " + method.getName());
                    } catch (IllegalArgumentException ex) {
                        throw new ModuleException("Could not execute @ModuleEvent method: " + method.getName());
                    } catch (InvocationTargetException ex) {
                        throw new ModuleException("Could not execute @ModuleEvent method: " + method.getName());
                    }
                }
            }
        }
        
        return true;
    }
}
