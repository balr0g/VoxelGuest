package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.util.Formatter;
import com.thevoxelbox.voxelguest.players.GuestPlayer;

public class SimpleFormatter extends Formatter {
    
    /*
     * --------------
     * FORMAT HANDLES
     * --------------
     * $n = name of the player
     * $name = long form of $n
     * $g = group of the player // How do I handle multigroup? Queries?
     * $group = long form of $g
     * $g# = number of online players of player's group
     * $gc = code for that group (if desired) // How do I handle multigroup? Queries?
     * 
     */
    
    @Override
    public Formatter install() {
        return new SimpleFormatter();
    }
    
    @Override
    public String[] format(String in) {
        return format(in, null);
    }
    
    public String[] format(String input, GuestPlayer gp) {
        String copy = input;
        boolean groupParsing;
        
        groupParsing = (gp == null) ? false : true;
        
        // -- Procesing --
        
        String[] copies = copy.split("\n");
        
        // -- Procesing --
        
        return copies;
    }
}
