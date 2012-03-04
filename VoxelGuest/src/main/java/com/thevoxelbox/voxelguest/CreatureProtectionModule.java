
import com.thevoxelbox.voxelguest.VoxelGuest;
import com.thevoxelbox.voxelguest.modules.BukkitEventWrapper;
import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.modules.ModuleEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.CreatureType;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * The Server Protection Module was created to help maintain various server
 * aspects, such as grief prevention. The Server Protection Module offers
 * extreme amounts of customization based on config variables.  Please refer
 * to VoxelGuest.java for variables.
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

    @Override
    public void enable() {
        return;
    }

    @Override
    public String getLoadMessage() {
        return "Creature Protection has been loaded.";
    }
    
        
    /*
     * World Protection - CreatureSpawn Event
     * Written by: Razorcane
     * 
     * Handles creature spawning event.
     */
    @ModuleEvent(event=CreatureSpawnEvent.class)
    public void onCreatureSpawn(BukkitEventWrapper wrapper){
        CreatureSpawnEvent event = (CreatureSpawnEvent) wrapper.getEvent();
        CreatureType mob = event.getCreatureType();
        
        if(mob.equals(CreatureType.BLAZE) && VoxelGuest.getConfigData().getBoolean("disable-blaze-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.CAVE_SPIDER) && VoxelGuest.getConfigData().getBoolean("disable-cavespider-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.CHICKEN) && VoxelGuest.getConfigData().getBoolean("disable-chicken-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.COW) && VoxelGuest.getConfigData().getBoolean("disable-cow-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.CREEPER) && VoxelGuest.getConfigData().getBoolean("disable-creeper-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.ENDERMAN) && VoxelGuest.getConfigData().getBoolean("disable-enderman-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.ENDER_DRAGON) && VoxelGuest.getConfigData().getBoolean("disable-enderdragon-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.GHAST) && VoxelGuest.getConfigData().getBoolean("disable-ghast-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.MAGMA_CUBE) && VoxelGuest.getConfigData().getBoolean("disable-magmacube-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.MUSHROOM_COW) && VoxelGuest.getConfigData().getBoolean("disable-mushroomcow-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.PIG) && VoxelGuest.getConfigData().getBoolean("disable-pig-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.PIG_ZOMBIE) && VoxelGuest.getConfigData().getBoolean("disable-pigzombie-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SHEEP) && VoxelGuest.getConfigData().getBoolean("disable-sheep-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SILVERFISH) && VoxelGuest.getConfigData().getBoolean("disable-silverfish-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SKELETON) && VoxelGuest.getConfigData().getBoolean("disable-skeleton-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SLIME) && VoxelGuest.getConfigData().getBoolean("disable-slime-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SNOWMAN) && VoxelGuest.getConfigData().getBoolean("disable-snowman-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SPIDER) && VoxelGuest.getConfigData().getBoolean("disable-spider-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.SQUID) && VoxelGuest.getConfigData().getBoolean("disable-squid-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.VILLAGER) && VoxelGuest.getConfigData().getBoolean("disable-villager-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.WOLF) && VoxelGuest.getConfigData().getBoolean("disable-wolf-spawning")){
            event.setCancelled(true);
        }
        
        if(mob.equals(CreatureType.ZOMBIE) && VoxelGuest.getConfigData().getBoolean("disable-zombie-spawning")){
            event.setCancelled(true);
        }
    }
}