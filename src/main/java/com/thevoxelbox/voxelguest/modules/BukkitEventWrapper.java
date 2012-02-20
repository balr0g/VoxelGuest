package com.thevoxelbox.voxelguest.modules;

import org.bukkit.event.Event;

public class BukkitEventWrapper {
    private Event event;
    private static boolean cancelled = false;
    
    public BukkitEventWrapper(Event event) {
        this.event = event;
    }
    
    public Event getEvent() {
        return event;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
