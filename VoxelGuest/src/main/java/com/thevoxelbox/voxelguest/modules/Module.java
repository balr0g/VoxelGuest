package com.thevoxelbox.voxelguest.modules;

import org.bukkit.event.Listener;

public abstract class Module implements Listener {
    protected String name;
    protected String description;
    
    protected boolean enabled = false;
    
    // Search for necessary classes and return new Module, if all checks are good
    //
    // MAKE SURE YOU INCLUDE @METADATA
    // else your module won't load
    public abstract Module install();
    
    // Enable module
    public abstract void enable();
    
    public abstract String getLoadMessage();
    
    public void disable() {
        setEnabled(false);
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean bool) {
        this.enabled = bool;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
}
