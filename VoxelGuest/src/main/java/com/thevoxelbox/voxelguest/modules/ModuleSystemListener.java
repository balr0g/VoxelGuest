package com.thevoxelbox.voxelguest.modules;

import com.thevoxelbox.voxelguest.VoxelGuest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class ModuleSystemListener implements Listener {
    private HashMap<Class<? extends Event>, HashMap<Method, ModuleEvent>> moduleEventMap = new HashMap<Class<? extends Event>, HashMap<Method, ModuleEvent>>();
    private HashMap<Method, Module> instances = new HashMap<Method, Module>();
    
    public void registerModuleEvents() {
        for (Module module : ModuleManager.getManager().getModules()) {
            registerModuleEvents(module);
        }
    }
    
    public void registerModuleEvents(Module module) {
        for (Method method : module.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ModuleEvent.class)) {
                Class classType = method.getParameterTypes()[0];
                if (!BukkitEventWrapper.class.isAssignableFrom(classType) || method.getParameterTypes().length != 1)
                    continue;
                
                if (!void.class.isAssignableFrom(method.getReturnType()))
                    continue;
                
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
    
    public void processModuleEvents(Event event) {
        HashMap<Method, ModuleEvent> map = moduleEventMap.get(event.getClass());
        BukkitEventWrapper wrapper = new BukkitEventWrapper(event);
        
        if (map == null)
            return;
        
        for (int i = 4; i >= 0; i--) {
            for (Map.Entry<Method, ModuleEvent> entry : map.entrySet()) {
                if (entry.getValue().priority().getIntValue() < i)
                    continue;
                
                Method method = entry.getKey();
                boolean ignoreCancelled = entry.getValue().ignoreCancelledEvents();
                
                if (wrapper.isCancelled() && ignoreCancelled)
                    continue;
                
                try {
                    method.invoke(instances.get(method), wrapper);
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                } catch (ClassCastException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
