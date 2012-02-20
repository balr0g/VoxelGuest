package com.thevoxelbox.commands;

public class CommandException extends Exception {
    private static final long serialVersionUID = 17124527707790318L;

    public CommandException(String reason) {
        super(reason);
    }
}
