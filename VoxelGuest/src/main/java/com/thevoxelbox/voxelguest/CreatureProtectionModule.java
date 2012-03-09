package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.modules.BukkitEventWrapper;
import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleConfiguration;
import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import com.thevoxelbox.voxelguest.modules.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.CreatureType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * The Creature Protection Module was created to help maintain various creature
 * aspects, such as mob spawning. The Creature Protection Module offers
 * extreme amounts of customization based on config variables.
 * 
 * Handles:
 *      Mob Spawning
 *      Mob Events
 */
@MetaData(name="Creature Protection", description="Various creature protection methods.")
public class CreatureProtectionModule extends Module{
    Server s = Bukkit.getServer();

    public CreatureProtectionModule(){
        super(CreatureProtectionModule.class.getAnnotation(MetaData.class));
    }
    
    class CreatureProtectionConfiguration extends ModuleConfiguration {
        
        @Setting("disable-blaze-spawning") public boolean blaze = false;
        @Setting("disable-cavespider-spawning") public boolean cavespider = false;
        @Setting("disable-chicken-spawning") public boolean chicken = false;
        @Setting("disable-cow-spawning") public boolean cow = false;
        @Setting("disable-creeper-spawning") public boolean creeper = false;
        @Setting("disable-enderman-spawning") public boolean enderman = false;
        @Setting("disable-enderdragon-spawning") public boolean enderdragon = false;
        @Setting("disable-ghast-spawning") public boolean ghast = false;
        @Setting("disable-magmacube-spawning") public boolean magmacube = false;
        @Setting("disable-mushroomcow-spawning") public boolean mushroomcow = false;
        @Setting("disable-pig-spawning") public boolean pig = false;
        @Setting("disable-pigzombie-spawning") public boolean pigzombie = false;
        @Setting("disable-sheep-spawning") public boolean sheep = false;
        @Setting("disable-silverfish-spawning") public boolean silverfish = false;
        @Setting("disable-skeleton-spawning") public boolean skeleton = false;
        @Setting("disable-slime-spawning") public boolean slime = false;
        @Setting("disable-snowman-spawning") public boolean snowman = false;
        @Setting("disable-spider-spawning") public boolean spider = false;
        @Setting("disable-squid-spawning") public boolean squid = false;
        @Setting("disable-villager-spawning") public boolean villager = false;
        @Setting("disable-wolf-spawning") public boolean wolf = false;
        @Setting("disable-zombie-spawning") public boolean zombie = false;
        @Setting("disable-creeper-explosion") public boolean creeperexplode = false;
        
        public CreatureProtectionConfiguration(CreatureProtectionModule parent) {
        
            super(parent);
        }
    }

    @Override
    public void enable() {
        return;
    }

    @Override
    public String getLoadMessage() {
        return "Creature Protection has been loaded.";
    }
    
    @Override
    public void disable() {
        return;
    }
    
        
    /*
     * Creature Protection - CreatureSpawn Event
     * Written by: Razorcane
     * 
     * Handles creature spawning event.
     */
    @ModuleEvent(event=CreatureSpawnEvent.class)
    public void onCreatureSpawn(BukkitEventWrapper wrapper){
        CreatureSpawnEvent event = (CreatureSpawnEvent) wrapper.getEvent();
        CreatureType mob = event.getCreatureType();
        
        if(mob.equals(CreatureType.BLAZE) && getConfiguration().getBoolean("disable-blaze-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.CAVE_SPIDER) && getConfiguration().getBoolean("disable-cavespider-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.CHICKEN) && getConfiguration().getBoolean("disable-chicken-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.COW) && getConfiguration().getBoolean("disable-cow-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.CREEPER) && getConfiguration().getBoolean("disable-creeper-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.ENDERMAN) && getConfiguration().getBoolean("disable-enderman-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.ENDER_DRAGON) && getConfiguration().getBoolean("disable-enderdragon-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.GHAST) && getConfiguration().getBoolean("disable-ghast-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.MAGMA_CUBE) && getConfiguration().getBoolean("disable-magmacube-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.MUSHROOM_COW) && getConfiguration().getBoolean("disable-mushroomcow-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.PIG) && getConfiguration().getBoolean("disable-pig-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.PIG_ZOMBIE) && getConfiguration().getBoolean("disable-pigzombie-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SHEEP) && getConfiguration().getBoolean("disable-sheep-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SILVERFISH) && getConfiguration().getBoolean("disable-silverfish-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SKELETON) && getConfiguration().getBoolean("disable-skeleton-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SLIME) && getConfiguration().getBoolean("disable-slime-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SNOWMAN) && getConfiguration().getBoolean("disable-snowman-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SPIDER) && getConfiguration().getBoolean("disable-spider-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SQUID) && getConfiguration().getBoolean("disable-squid-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.VILLAGER) && getConfiguration().getBoolean("disable-villager-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.WOLF) && getConfiguration().getBoolean("disable-wolf-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.ZOMBIE) && getConfiguration().getBoolean("disable-zombie-spawning")){
            event.setCancelled(true);
        }
    }
    
    /*
     * Creature Protection - EntityExplode Event
     * Written by: Razorcane
     * 
     * Handles mob explosions, such as creepers.
     */
    @ModuleEvent(event=EntityExplodeEvent.class)
    public void onEntityExplode(BukkitEventWrapper wrapper){
        EntityExplodeEvent event = (EntityExplodeEvent) wrapper.getEvent();
        
        if(getConfiguration().getBoolean("disable-creeper-explosion")){
            event.setCancelled(true);
        }
    }
}