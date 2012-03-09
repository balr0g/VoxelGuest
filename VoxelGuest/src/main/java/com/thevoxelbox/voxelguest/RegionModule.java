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
package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleConfiguration;
import com.thevoxelbox.voxelguest.modules.Setting;
import com.thevoxelbox.voxelguest.regions.Region;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@MetaData(name="Regions", description="Manage region-based build protections on your server!")
public class RegionModule extends Module {
    public List<Region> loadedRegions = new ArrayList<Region>();

    public RegionModule() {
        super(RegionModule.class.getAnnotation(MetaData.class));
    }
    
    class RegionConfiguration extends ModuleConfiguration {
        @Setting("enable-general-build-outside-defined-regions") public boolean enableGeneralBuildOutsideDefinedRegions = true;
        
        public RegionConfiguration(RegionModule parent) {
            super(parent);
        }
    }
    
    @Override
    public void enable() {
        setConfiguration(new RegionConfiguration(this));
        File dir = new File("plugins/VoxelGuest/regions");
        
        if (!dir.exists()) {
            dir.mkdirs();
        } else {
            if (dir.list().length != 0) {
                for (File f : dir.listFiles()) {
                    if (!f.getName().endsWith(".properties"))
                        continue;
                    
                    Region region = new Region(f.getName().replace(".properties", ""));
                    
                    if (!loadedRegions.contains(region))
                        loadedRegions.add(region);
                }
            }
        }
    }
    
    @Override
    public void disable() {
        Iterator<Region> it = loadedRegions.listIterator();
        
        while (it.hasNext()) {
            Region region = it.next();
            region.save();
        }
        
        loadedRegions.clear();
    }

    @Override
    public String getLoadMessage() {
        return "Region module loaded";
    }
}
