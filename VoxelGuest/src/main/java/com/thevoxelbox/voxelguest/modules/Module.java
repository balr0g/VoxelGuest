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

package com.thevoxelbox.voxelguest.modules;

public abstract class Module {
    protected String name;
    protected String description;
    
    protected boolean enabled = false;
    
    public Module(MetaData md) {
        this.name = md.name();
        this.description = md.description();
    }
    
    // Enable module
    public abstract void enable();
    
    public abstract String getLoadMessage();
    
    public void disable() {
        setEnabled(false);
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean bool) {
        this.enabled = bool;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
}
