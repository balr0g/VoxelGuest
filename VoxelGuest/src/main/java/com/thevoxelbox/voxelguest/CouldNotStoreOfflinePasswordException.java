package com.thevoxelbox.voxelguest;

public class CouldNotStoreOfflinePasswordException extends Exception {
    private static final long serialVersionUID = 6084439435116546L;
    
    private String reason;
    
    public CouldNotStoreOfflinePasswordException(String string) {
        super(string);
        
        this.reason = string;
    }
    
    public String getReason() {
        return this.reason;
    }
}
