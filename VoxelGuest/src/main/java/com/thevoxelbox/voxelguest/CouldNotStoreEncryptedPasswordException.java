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

public class CouldNotStoreEncryptedPasswordException extends Exception {
    private static final long serialVersionUID = 6084439435116546L;
    
    private String reason;
    
    public CouldNotStoreEncryptedPasswordException(String string) {
        super(string);
        
        this.reason = string;
    }
    
    public String getReason() {
        return this.reason;
    }
}
