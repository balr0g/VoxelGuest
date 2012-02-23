/*
 * VoxelGuest
 *
 * Copyright (C) 2011, 2012 psanker and contributors

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.thevoxelbox.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * Inspired by sk89q's command system
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * 
     * List of aliases for the command. ALWAYS SET THE PRIMARY COMMAND AS INDEX 0
     * 
     */
    String[] aliases();

    /**
     * 
     * Minimum and maximum allowed argument lengths
     * Index 0 is min number and index 1 is max number 
     * (set index 1 to -1 or lower to have unlimited arguments)
     * 
     */
    int[] bounds();

    /**
     * 
     * Prints this out when person does "/<command> ?", "/<command> help", or "/<command> h"
     * 
     */
    String help() default "";

    /**
     * 
     * 
     * Checks if the command is for players only
     * 
     */
    boolean playerOnly() default false;
}
