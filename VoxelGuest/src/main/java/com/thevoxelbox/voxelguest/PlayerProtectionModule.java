package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.modules.BukkitEventWrapper;
import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * The Player Protection Module was created to help maintain various player
 * aspects, such as death prevention. The Player Protection Module offers
 * extreme amounts of customization based on config variables.  Please refer
 * to VoxelGuest.java for variables.
 * 
 * Handles:
 *      Food Level Changes
 *      DamageCause Death Preventions
 *      Player XP Changes
 */

@MetaData(name="Player Protection", description="Various player protection methods.")
public class PlayerProtectionModule extends Module {
    
    public PlayerProtectionModule(){
        super(PlayerProtectionModule.class.getAnnotation(MetaData.class));
    }
    
    @Override
    public void enable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getLoadMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /* Player Protection - EntityDamage Event
     * Written by: Razorcane
     * 
     * Handles all player-based damages.
     */
    @ModuleEvent(event=EntityDamageEvent.class)
    public void onEntityDamage(BukkitEventWrapper wrapper){
        EntityDamageEvent event = (EntityDamageEvent) wrapper.getEvent();
        Entity e = event.getEntity();
        DamageCause dc = event.getCause();
        
        if(e instanceof Player){
            if(dc.equals(DamageCause.BLOCK_EXPLOSION) && VoxelGuest.getConfigData().getBoolean("disable-tnt-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.CONTACT) && VoxelGuest.getConfigData().getBoolean("disable-cactus-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.DROWNING) && VoxelGuest.getConfigData().getBoolean("disable-drowning-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.ENTITY_ATTACK) && VoxelGuest.getConfigData().getBoolean("disable-pvp-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.ENTITY_EXPLOSION) && VoxelGuest.getConfigData().getBoolean("disable-explosion-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.FALL) && VoxelGuest.getConfigData().getBoolean("disable-fall-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.FIRE) && VoxelGuest.getConfigData().getBoolean("disable-fire-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.FIRE_TICK) && VoxelGuest.getConfigData().getBoolean("disable-firetick-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.LAVA) && VoxelGuest.getConfigData().getBoolean("disable-lava-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.LIGHTNING) && VoxelGuest.getConfigData().getBoolean("disable-lightning-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.MAGIC) && VoxelGuest.getConfigData().getBoolean("disable-magic-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.POISON) && VoxelGuest.getConfigData().getBoolean("disable-poison-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.PROJECTILE) && VoxelGuest.getConfigData().getBoolean("disable-projectile-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.STARVATION) && VoxelGuest.getConfigData().getBoolean("disable-starvation-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.SUFFOCATION) && VoxelGuest.getConfigData().getBoolean("disable-suffocation-damage")){
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.VOID) && VoxelGuest.getConfigData().getBoolean("disable-void-damage")){
                event.setCancelled(true);
            }
        }
    }
    
     /*
     * Player Protection - FoodLevelChange Event
     * Written by: Razorcane
     * 
     * Handles food level changes, specifically those for players.
     */
    @ModuleEvent(event=FoodLevelChangeEvent.class)
    public void onFoodLevelChange(BukkitEventWrapper wrapper){
        FoodLevelChangeEvent event = (FoodLevelChangeEvent) wrapper.getEvent();
        
        if(event.getFoodLevel() < 20 && VoxelGuest.getConfigData().getBoolean("disable-food-changes")){
            event.setFoodLevel(20);
        }
    }
}
