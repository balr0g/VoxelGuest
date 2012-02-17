package com.thevoxelbox.permissions;

public class PermissionsException extends Exception {
    private static final long serialVersionUID = 22300013938599292L;
    
    private String reason;

    public PermissionsException(String reason) {
        this.reason = reason;
    }
    
    public String getReason() {
        return this.reason;
    }
}
