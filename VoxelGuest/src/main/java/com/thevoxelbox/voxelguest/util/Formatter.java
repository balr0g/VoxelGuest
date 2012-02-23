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

package com.thevoxelbox.voxelguest.util;

import com.thevoxelbox.voxelguest.players.GuestPlayer;

public abstract class Formatter {
    
    public static Formatter selectFormatter(Class<? extends Formatter> cls) {
        try {
            Formatter formatter = cls.newInstance();
            return formatter;
        } catch (Throwable t) {
            return null;
        }
    }
    
    public String encodeColors(String input) {
        for (FormatColors color : FormatColors.values()) {
            input = input.replace(color.getColorCode(), color.getBukkitColorCode());
        }
        
        return input;
    }
    
    public abstract String[] format(String in, GuestPlayer gp);
}

enum FormatColors {
    WHITE("&f"),
    DARK_BLUE("&1"),
    DARK_GREEN("&2"),
    TEAL("&3"),
    DARK_RED("&4"),
    PURPLE("&5"),
    ORANGE("&6"),
    LIGHT_GREY("&7"),
    DARK_GREY("&8"),
    INDIGO("&9"),
    LIGHT_GREEN("&a"),
    CYAN("&b"),
    RED("&c"),
    PINK("&d"),
    YELLOW("&e"),
    BLACK("&0");
    
    private String color;
    
    private FormatColors(String c) {
        color = c;
    }
    
    public String getColorCode() {
        return color;
    }
    
    public String getBukkitColorCode() {
        return color.replace("&", "\u00A7");
    }
 }
