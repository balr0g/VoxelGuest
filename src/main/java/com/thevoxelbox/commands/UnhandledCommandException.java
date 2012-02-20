package com.thevoxelbox.commands;

public class UnhandledCommandException extends CommandException {
    private static final long serialVersionUID = 47749220015843974L;

    public UnhandledCommandException(String reason) {
        super(reason);
    }
}
