package com.thevoxelbox.voxelguest.modules;

public enum ModuleEventPriority {
    LOWEST(0),
    LOW(1),
    NORMAL(2),
    HIGH(3),
    HIGHEST(4);
    
    private int level;
    
    private ModuleEventPriority(int l) {
        level = l;
    }
    
    public int getIntValue() {
        return level;
    }
}
