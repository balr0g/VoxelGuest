package com.thevoxelbox.voxelguest.players;

import java.util.LinkedList;
import java.util.List;

public class AsshatMitigationManager {

    private final List<String> gagged = new LinkedList<String>();
    private final List<String> banned = new LinkedList<String>();

    public boolean isGagged(String name) {
        return gagged.contains(name);
    }

    public boolean isBanned(String name) {
        return banned.contains(name);
    }

    public void gag(String name) {
        if (!gagged.contains(name)) {
            gagged.add(name);
        }
    }

    public void ban(String name) {
        if (!banned.contains(name)) {
            banned.add(name);
        }
    }

    public void ungag(String name) {
        if (gagged.contains(name)) {
            gagged.remove(name);
        }
    }

    public void unban(String name) {
        if (banned.contains(name)) {
            banned.remove(name);
        }
    }
}
