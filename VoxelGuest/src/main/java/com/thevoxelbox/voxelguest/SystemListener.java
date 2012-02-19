package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.players.GuestPlayer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SystemListener implements Listener {
    private HashMap<Class<? extends Event>, HashMap<Method, ModuleEvent>> moduleEventMap = new HashMap<Class<? extends Event>, HashMap<Method, ModuleEvent>>();
    private HashMap<Method, Module> instances = new HashMap<Method, Module>();
    
    public void registerModuleEvents() {
        for (Module module : VoxelGuest.getModules()) {
            for (Method method : module.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(ModuleEvent.class)) {
                    ModuleEvent moduleEvent = method.getAnnotation(ModuleEvent.class);
                    
                    if (!moduleEventMap.containsKey(moduleEvent.event())) {
                        HashMap<Method, ModuleEvent> map = new HashMap<Method, ModuleEvent>();
                        map.put(method, moduleEvent);
                        moduleEventMap.put(moduleEvent.event(), map);
                    } else {
                        HashMap<Method, ModuleEvent> map = moduleEventMap.get(moduleEvent.event());
                        map.put(method, moduleEvent);
                        moduleEventMap.put(moduleEvent.event(), map);
                    }
                    
                    instances.put(method, module);
                }
            }
        }
    }
    
    public void processModuleEvents(Event event) {
        HashMap<Method, ModuleEvent> map = moduleEventMap.get(event.getClass());
        boolean cancelled = false;
        
        if (map == null)
            return;
        
        for (int i = 4; i >= 0; i--) {
            for (Map.Entry<Method, ModuleEvent> entry : map.entrySet()) {
                if (entry.getValue().event() != event.getClass())
                    continue;
                
                if (entry.getValue().priority().getIntValue() < i)
                    continue;
                
                Method method = entry.getKey();
                
                try {
                    cancelled = ((Boolean) method.invoke(instances.get(method), event)).booleanValue();
                } catch (IllegalAccessException ex) {
                    continue;
                } catch (IllegalArgumentException ex) {
                    continue;
                } catch (InvocationTargetException ex) {
                    continue;
                } catch (ClassCastException ex) {
                    continue;
                }
                
                if (cancelled) break;
            }
            
            if (cancelled) break;
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH) 
    public void onPlayerJoin(PlayerJoinEvent event) {
        VoxelGuest.registerPlayer(event.getPlayer());
        GuestPlayer gp = VoxelGuest.getGuestPlayer(event.getPlayer());
        
        processModuleEvents(event);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        GuestPlayer gp = VoxelGuest.getGuestPlayer(event.getPlayer());
        VoxelGuest.unregsiterPlayer(gp);
        gp.saveData(VoxelGuest.getPluginId(VoxelGuest.getInstance()));
        
        processModuleEvents(event);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKick(PlayerKickEvent event) {
        GuestPlayer gp = VoxelGuest.getGuestPlayer(event.getPlayer());
        VoxelGuest.unregsiterPlayer(gp);
        gp.saveData(VoxelGuest.getPluginId(VoxelGuest.getInstance()));
        
        processModuleEvents(event);
    }
}
