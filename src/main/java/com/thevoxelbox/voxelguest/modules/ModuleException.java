package com.thevoxelbox.voxelguest.modules;

public class ModuleException extends Exception {
    private static final long serialVersionUID = 9683063058881090L;
    
    public ModuleException(String reason) {
        super(reason);
    }
}
