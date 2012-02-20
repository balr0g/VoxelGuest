package com.thevoxelbox.voxelguest.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bukkit.event.Event;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ModuleEvent {
    // Use this annotation to queue this event method and NOT override the master
    // SystemListener copy (so no strange issues arise)
    
    // The event the module is searching for
    public Class<? extends Event> event();
    
    // The level at which ModuleEvents are processed
    public ModuleEventPriority priority() default ModuleEventPriority.NORMAL;
    
    public boolean ignoreCancelledEvents() default true;
}
