package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.modules.BukkitEventWrapper;
import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleConfiguration;
import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import com.thevoxelbox.voxelguest.modules.Setting;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * The Player Protection Module was created to help maintain various player
 * aspects, such as death prevention. The Player Protection Module offers
 * extreme amounts of customization based on config variables.
 * 
 * Handles:
 *      Food Level Changes
 *      DamageCause Death Preventions
 *      Player XP Changes
 */

@MetaData(name="Player Protection", description="Various player protection methods.")
public class PlayerProtectionModule extends Module {
    
    public PlayerProtectionModule() {
        super(PlayerProtectionModule.class.getAnnotation(MetaData.class));
    }
    
    class PlayerProtectionConfiguration extends ModuleConfiguration {
        @Setting("disable-tnt-damage") public boolean tnt = false;
        @Setting("disable-cactus-damage") public boolean cactus = false;
        @Setting("disable-drowning-damage") public boolean drowning = false;
        @Setting("disable-pvp-damage") public boolean pvp = false;
        @Setting("disable-explosion-damage") public boolean explosion = false;
        @Setting("disable-fall-damage") public boolean fall = false;
        @Setting("disable-fire-damage") public boolean fire = false;
        @Setting("disable-firetick-damage") public boolean firetick = false;
        @Setting("disable-lava-damage") public boolean lava = false;
        @Setting("disable-lightning-damage") public boolean lightning = false;
        @Setting("disable-potion-damage") public boolean potion = false;
        @Setting("disable-magic-damage") public boolean magic = false;
        @Setting("disable-projectile-damage") public boolean projectile = false;
        @Setting("disable-starvation-damage") public boolean starvation = false;
        @Setting("disable-suffocation-damage") public boolean suffocation = false;
        @Setting("disable-void-damage") public boolean voiddamage = false;
        @Setting("disable-food-changes") public boolean foodchange = false;
        
        public PlayerProtectionConfiguration(PlayerProtectionModule parent) {
            super(parent);
        }
    }
    
    @Override
    public void enable() {
        setConfiguration(new PlayerProtectionConfiguration(this));
    }

    @Override
    public String getLoadMessage() {
        return "Player protection module loaded";
    }
    
    @Override
    public void disable() {
        
    }
    
    /* Player Protection - EntityDamage Event
     * Written by: Razorcane
     * 
     * Handles all player-based damages.
     */
    @ModuleEvent(event=EntityDamageEvent.class)
    public void onEntityDamage(BukkitEventWrapper wrapper) {
        EntityDamageEvent event = (EntityDamageEvent) wrapper.getEvent();
        Entity e = event.getEntity();
        DamageCause dc = event.getCause();
        
        if(e instanceof Player) {
            if(dc.equals(DamageCause.BLOCK_EXPLOSION) && getConfiguration().getBoolean("disable-tnt-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.CONTACT) && getConfiguration().getBoolean("disable-cactus-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.DROWNING) && getConfiguration().getBoolean("disable-drowning-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.ENTITY_ATTACK) && getConfiguration().getBoolean("disable-pvp-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.ENTITY_EXPLOSION) && getConfiguration().getBoolean("disable-explosion-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.FALL) && getConfiguration().getBoolean("disable-fall-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.FIRE) && getConfiguration().getBoolean("disable-fire-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.FIRE_TICK) && getConfiguration().getBoolean("disable-firetick-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.LAVA) && getConfiguration().getBoolean("disable-lava-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.LIGHTNING) && getConfiguration().getBoolean("disable-lightning-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.MAGIC) && getConfiguration().getBoolean("disable-magic-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.POISON) && getConfiguration().getBoolean("disable-poison-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.PROJECTILE) && getConfiguration().getBoolean("disable-projectile-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.STARVATION) && getConfiguration().getBoolean("disable-starvation-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.SUFFOCATION) && getConfiguration().getBoolean("disable-suffocation-damage")) {
                event.setDamage(0);
                event.setCancelled(true);
            }
            else if(dc.equals(DamageCause.VOID) && getConfiguration().getBoolean("disable-void-damage")) {
                event.setDamage(0);
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
    public void onFoodLevelChange(BukkitEventWrapper wrapper) {
        FoodLevelChangeEvent event = (FoodLevelChangeEvent) wrapper.getEvent();
        
        if(event.getFoodLevel() < 20 && getConfiguration().getBoolean("disable-food-changes")) {
            event.setFoodLevel(20);
            event.setCancelled(true);
        }
    }
}
