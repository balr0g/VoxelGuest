package com.thevoxelbox.commands;

public class CommandException extends Exception {
    private static final long serialVersionUID = 17124527707790318L;
    
    private String reason;

    public CommandException(String reason) {
        this.reason = reason;
    }
    
    public String getReason() {
        return this.reason;
    }
}
