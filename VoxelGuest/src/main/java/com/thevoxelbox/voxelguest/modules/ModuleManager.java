package com.thevoxelbox.voxelguest.modules;

import com.thevoxelbox.voxelguest.VoxelGuest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ModuleManager {
    private static Plugin plugin;
    
    protected List<Module> activeModules = new LinkedList<Module>();
    protected HashMap<Class<? extends Module>, Module> classInstanceMap = new HashMap<Class<? extends Module>, Module>();
    
    public ModuleManager(Plugin p) {
        plugin = p;
    }
    
    public void loadModules(Class<? extends Module>[] classes) {
        for (Class<? extends Module> cls : classes) {
            try {
                loadModule(cls);
            } catch (ModuleException ex) {
                if (ex instanceof ModuleInitialisationException || ex instanceof MalformattedModuleException) {
                    log(ex.getMessage(), 2);
                    ex.printStackTrace();
                }
                
                continue;
            }
        }
    }
    
    public synchronized void loadModule(Class<? extends Module> cls) throws ModuleException {
        Module module = null;
        
        if (!cls.isAnnotationPresent(MetaData.class))
                throw new MalformattedModuleException("Malformatted Module: " + cls.getCanonicalName());
        
        try {
            Method install = cls.getMethod("install");
            module = (Module) install.invoke(null);
        } catch (IllegalAccessException ex) {
            throw new ModuleInitialisationException("Failed to load Module instance: " + cls.getCanonicalName());
        } catch (IllegalArgumentException ex) {
            throw new ModuleInitialisationException("Failed to load Module instance: " + cls.getCanonicalName());
        } catch (InvocationTargetException ex) {
            throw new ModuleInitialisationException("Failed to load Module instance: " + cls.getCanonicalName());
        } catch (NoSuchMethodException ex) {
            throw new ModuleInitialisationException("Failed to load Module instance: " + cls.getCanonicalName());
        } catch (SecurityException ex) {
            throw new ModuleInitialisationException("Failed to load Module instance: " + cls.getCanonicalName());
        }
        
        if (activeModules.contains(module))
            throw new ModuleException("Module already registered: " + cls.getCanonicalName());
        
        if (module != null) {
            // Find and register commands and events
            VoxelGuest.getCommandsManager().registerCommands(cls);
            
            if (cls.isAssignableFrom(Listener.class)) {
                Bukkit.getPluginManager().registerEvents(module, plugin);
            }
            
            for (Class<?> clazz : cls.getDeclaredClasses()) {
                VoxelGuest.getCommandsManager().registerCommands(clazz);
            }
            
            module.enable();
            activeModules.add(module);
            classInstanceMap.put(cls, module);
            log(getName(module), module.getLoadMessage(), 0);
        } else {
            throw new ModuleException("Module is null or already registered"); // Only in weird cases would this happen
        }
    }
    
    public Module getModule(Class<? extends Module> cls) throws ModuleException {
        if (classInstanceMap.containsKey(cls))
            return classInstanceMap.get(cls);
        
        throw new ModuleException("Module not found: " + cls.getCanonicalName());
    }
    
    public Module[] getModules() {
        Module[] modules = new Module[activeModules.size()];
        return activeModules.toArray(modules);
    }
    
    public String getName(Module mod) {
        if (!activeModules.contains(mod))
            return null;
        
        MetaData md = mod.getClass().getAnnotation(MetaData.class);
        return md.name();
    }
    
    public String getDescription(Module mod) {
        if (!activeModules.contains(mod))
            return null;
        
        MetaData md = mod.getClass().getAnnotation(MetaData.class);
        return md.description();
    }
    
    private static void log(String str, int importance) {
        switch (importance) {
            case 0:
                Logger.getLogger("Mincraft").info("[" + plugin.getDescription().getName() + "] " + str);
                return;
            case 1:
                Logger.getLogger("Mincraft").warning("[" + plugin.getDescription().getName() + "] " + str);
                return;
            case 2:
                Logger.getLogger("Mincraft").severe("[" + plugin.getDescription().getName() + "] " + str);
                return;
            default:
                Logger.getLogger("Mincraft").info("[" + plugin.getDescription().getName() + "] " + str);
                return;
        }
    }
    
    private static void log(String module, String str, int importance) {
        switch (importance) {
            case 0:
                Logger.getLogger("Mincraft").info("[" + plugin.getDescription().getName() + ":" + module + "] " + str);
                return;
            case 1:
                Logger.getLogger("Mincraft").warning("[" + plugin.getDescription().getName() + ":" + module + "] " + str);
                return;
            case 2:
                Logger.getLogger("Mincraft").severe("[" + plugin.getDescription().getName() + ":" + module + "] " + str);
                return;
            default:
                Logger.getLogger("Mincraft").info("[" + plugin.getDescription().getName() + ":" + module + "] " + str);
                return;
        }
    }
}
