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

package com.thevoxelbox.voxelguest.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FlatFileManager {
    
    private static String directory = "plugins/VoxelGuest/data";
    
    public static String[] load(String target) {
        return load(target, "");
    }
    
    public static String[] load(String target, String destination) {
        // Note: Destination is appended to plugins/VoxelGuest/data
        // For example, when destination is "/channels",
        // the target directory will be "plugins/VoxelGuest/data/channels/"
        
        File f = new File(directory + destination + "/" + target + ".txt");
        Scanner snr = null;
        
        if (f.exists()) {
            try {
                snr = new Scanner(f);
                String toCut = "";
                
                while (snr.hasNextLine()) {
                    String line = snr.nextLine();
                    
                    if (line.startsWith("#"))
                        continue;
                    
                    toCut = toCut + line + "\n";
                }
                
                snr.close();
                String[] split = toCut.split("\n");
                return split;
            } catch (FileNotFoundException ex) {
                return null;
            }
        } else {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                return null;
            }
        }
        
        return null;
    }
    
    public static void save(String[] strs, String target) {
        save(strs, target, "");
    }
    
    public static void save(String[] strs, String target, String destination) {
        // Note: Destination is appended to plugins/VoxelGuest/data
        // For example, when destination is "/channels",
        // the target directory will be "plugins/VoxelGuest/data/channels/"
        
        File f = new File(directory + destination + "/" + target + ".txt");
        PrintWriter pw = null;
        
        if (f.exists()) {
            try {
                pw = new PrintWriter(f);
                
                String concat = "";
                
                for (int i = 0; i < strs.length; i++) {
                    if (i == (strs.length - 1)) {
                        concat = concat + strs[i];
                    } else {
                        concat = concat + strs[i] + "\n";
                    }
                }
                
                pw.write(concat);
                pw.close();
            } catch (FileNotFoundException ex) {
                return;
            }
        }
    }
}
