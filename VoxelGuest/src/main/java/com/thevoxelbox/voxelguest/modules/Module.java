package com.thevoxelbox.voxelguest.modules;

import org.bukkit.event.Listener;

public abstract class Module implements Listener {
    protected String name;
    protected String description;
    
    protected boolean enabled = false;
    
    public Module(MetaData md) {
        this.name = md.name();
        this.description = md.description();
    }
    
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
