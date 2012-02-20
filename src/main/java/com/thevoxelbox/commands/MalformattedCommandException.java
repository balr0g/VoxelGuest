package com.thevoxelbox.commands;

public class MalformattedCommandException extends CommandException {
    private static final long serialVersionUID = 6045041945680663L;

    public MalformattedCommandException(String reason) {
        super(reason);
    }
}
