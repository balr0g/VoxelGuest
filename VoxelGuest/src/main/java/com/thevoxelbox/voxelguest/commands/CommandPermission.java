package com.thevoxelbox.voxelguest.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandPermission {

    /**
     * 
     * The permission needed
     * 
     */
    String permission() default "";
}
