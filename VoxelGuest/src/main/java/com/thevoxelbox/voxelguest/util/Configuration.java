package com.thevoxelbox.voxelguest.util;

public class Configuration {
    // -- Stores everything VG master server related
    
    public static boolean useDinnerPerms  = false;
    public static boolean explorationMode = false;
    public static boolean enableGreylist  = false;
    
    public static String joinFormat         = "&8($gc:$g#&8) &3$n &7joined";
    public static String leaveFormat        = "&8($gc:$g#&8) &3$n &7left";
    public static String gagBroadcastFormat = "&6==============================\n" +
                                              "&$gagged has been gagged by $gagger for:" + 
                                              "";
    
    public Configuration(String target, String destination) {
        
    }
}
