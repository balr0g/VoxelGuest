package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.util.Formatter;
import com.thevoxelbox.voxelguest.players.GuestPlayer;
import com.thevoxelbox.voxelguest.util.Configuration;
import org.bukkit.Bukkit;

public class SimpleFormatter extends Formatter {
    
    /*
     * --------------
     * FORMAT HANDLES
     * --------------
     * $n = name of the player
     * $name = long form of $n
     * $g = group (INDEX 0) of the player
     * $group = long form of $g
     * $gc = code for that group (if desired)
     * $nonline = number of people online
     * 
     * Would be best to extend from this implementation
     * so you don't have to rewrite the group crap again
     * 
     */
    
    @Override
    public String[] format(String input, GuestPlayer gp) {
        String copy = input;
        boolean guestPlayerParcing;
        
        guestPlayerParcing = (gp == null) ? false : true;
        
        if (guestPlayerParcing) {
            
            if (gp.getGroups() != null) {
                String group = gp.getGroups()[0];
                Configuration config = VoxelGuest.getGroupManager().getGroupConfiguration(group);
                String groupID = config.getString("group-id");

                copy = copy.replace("$group", group);
                if (groupID != null) {copy = copy.replace("$gc", groupID);}
                copy = copy.replace("$g", group);
            }
            
            copy = copy.replace("$nonline", Integer.toString(Bukkit.getOnlinePlayers().length));
            copy = copy.replace("$name", gp.getPlayer().getName());
            copy = copy.replace("$n", gp.getPlayer().getName());
        }
        
        copy = encodeColors(copy);
        
        if (copy.contains("\n")) {
            String[] copies = copy.split("\n");
            return copies;
        } else {
            return new String[] {copy};
        }
    }
}
