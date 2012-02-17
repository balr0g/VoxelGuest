package com.thevoxelbox.permissions;

public class InsufficientPermissionsException extends PermissionsException {
    private static final long serialVersionUID = 20814324162432463L;
    
    public InsufficientPermissionsException(String reason) {
        super(reason);
    }   
}
