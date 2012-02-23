/*
 * VoxelGuest
 *
 * Copyright (C) 2011, 2012 psanker and contributors

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.thevoxelbox.voxelguest;

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
