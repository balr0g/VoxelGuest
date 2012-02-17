package com.thevoxelbox.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Subcommands {

    /**
     * 
     * Arguments of subcommands
     * NOTE: ORDER MATTERS
     * 
     */
    String[] arguments();

    /**
     * 
     * Permissions needed for subcommands
     * NOTE: ORDER MATTERS
     * 
     */
    String[] permission();
}
