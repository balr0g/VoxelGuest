package com.thevoxelbox.voxelguest.players;

public class GroupNotFoundException extends Exception {
    private static final long serialVersionUID = -640627620741724L;
    
    public GroupNotFoundException(String reason) {
        super(reason);
    }
}
