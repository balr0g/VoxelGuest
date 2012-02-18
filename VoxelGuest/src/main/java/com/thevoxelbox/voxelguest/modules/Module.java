package com.thevoxelbox.voxelguest.modules;

import org.bukkit.event.Listener;

public abstract class Module implements Listener {
    
    protected boolean enabled = false;
    
    // Search for necessary classes and return new Module, if all checks are good
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
}
