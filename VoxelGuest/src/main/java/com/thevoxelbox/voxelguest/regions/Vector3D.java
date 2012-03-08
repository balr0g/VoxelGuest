/*
 * Copyright (C) 2011 - 2012, psanker and contributors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are 
 * permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this list of 
 *   conditions and the following 
 * * Redistributions in binary form must reproduce the above copyright notice, this list of 
 *   conditions and the following disclaimer in the documentation and/or other materials 
 *   provided with the distribution.
 * * Neither the name of The VoxelPlugineering Team nor the names of its contributors may be 
 *   used to endorse or promote products derived from this software without specific prior 
 *   written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.thevoxelbox.voxelguest.regions;

import org.bukkit.Location;

public class Vector3D {
    private final double x, y, z;
    
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3D(int x, int y, int z) {
        this.x = (double) x;
        this.y = (double) y;
        this.z = (double) z;
    }
    
    public Vector3D(float x, float y, float z) {
        this.x = (double) x;
        this.y = (double) y;
        this.z = (double) z;
    }
    
    public Vector3D(Location loc) {
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
    }
    
    public Vector3D(Vector3D vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public Vector3D setX(double x) {
        return new Vector3D(x, this.y, this.z);
    }
    
    public Vector3D setX(int x) {
        return new Vector3D((double) x, this.y, this.z);
    }
    
    public Vector3D setY(double y) {
        return new Vector3D(this.x, y, this.z);
    }
    
    public Vector3D setY(int y) {
        return new Vector3D(this.x, (double) y, this.z);
    }
    
    public Vector3D setZ(double z) {
        return new Vector3D(this.x, this.y, z);
    }
    
    public Vector3D setZ(int z) {
        return new Vector3D(this.x, this.y, (double) z);
    }
    
    public Vector3D add(Vector3D vec) {
        return new Vector3D(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }
    
    public Vector3D subtract(Vector3D vec) {
        return new Vector3D(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }
    
    public double getDistanceSquared(Vector3D vec) {
        return (vec.x - this.x)*(vec.x - this.x) + (vec.y - this.y)*(vec.y - this.y) + (vec.z - this.z)*(vec.z - this.z);
    }
    
    public double getDistance(Vector3D vec) {
        return Math.sqrt(getDistanceSquared(vec));
    }
    
    public static Vector3D getMinimum(Vector3D vec1, Vector3D vec2) {
        return new Vector3D(Math.min(vec1.x, vec2.x), Math.min(vec1.y, vec2.y), Math.min(vec1.z, vec2.z));
    }
    
    public static Vector3D getMaximum(Vector3D vec1, Vector3D vec2) {
        return new Vector3D(Math.max(vec1.x, vec2.x), Math.max(vec1.y, vec2.y), Math.max(vec1.z, vec2.z));
    }
    
    @Override
    public int hashCode() {
        int hash = 9;
        
        hash = 81 * hash + (int) (Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        hash = 81 * hash + (int) (Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        hash = 81 * hash + (int) (Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Vector3D other = (Vector3D) obj;
        
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        
        return true;
    }
}
