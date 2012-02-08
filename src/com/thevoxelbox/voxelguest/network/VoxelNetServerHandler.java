/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.network;

import java.lang.reflect.Field;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet17EntityLocationAction;
import net.minecraft.server.Packet18ArmAnimation;
import net.minecraft.server.Packet19EntityAction;
import net.minecraft.server.Packet201PlayerInfo;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet28EntityVelocity;
import net.minecraft.server.Packet30Entity;
import net.minecraft.server.Packet31RelEntityMove;
import net.minecraft.server.Packet32EntityLook;
import net.minecraft.server.Packet34EntityTeleport;
import net.minecraft.server.Packet38EntityStatus;
import net.minecraft.server.Packet39AttachEntity;
import net.minecraft.server.Packet40EntityMetadata;
import net.minecraft.server.Packet41MobEffect;
import net.minecraft.server.Packet42RemoveMobEffect;
import net.minecraft.server.Packet5EntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author patrick
 */
public class VoxelNetServerHandler extends NetServerHandler {
    public VanishManager vm = new VanishManager();
    
    public VoxelNetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }
    
    public static void resetPlayerNetHandler(Player player) {
        CraftPlayer cp = (CraftPlayer) player;
        CraftServer cs = (CraftServer) Bukkit.getServer();
        
        if (cp.getHandle().netServerHandler instanceof VoxelNetServerHandler) {
            NetServerHandler oldHandler = cp.getHandle().netServerHandler;
            Location loc = player.getLocation();
            VoxelNetServerHandler vnsh = new VoxelNetServerHandler(cs.getHandle().server, cp.getHandle().netServerHandler.networkManager, cp.getHandle());
            vnsh.a(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            cp.getHandle().netServerHandler = vnsh;
            NetworkManager nm = cp.getHandle().netServerHandler.networkManager;
            setNetServerHandler(nm, cp.getHandle().netServerHandler);
            oldHandler.disconnected = true;
            ((CraftServer) Bukkit.getServer()).getServer().networkListenThread.a(vnsh);
        } 
    }
    
    public static void reassignPlayerNetHandler(Player player) {
        CraftPlayer cp = (CraftPlayer) player;
        CraftServer cs = (CraftServer) Bukkit.getServer();
        
        if (!(cp.getHandle().netServerHandler instanceof VoxelNetServerHandler)) {
            NetServerHandler oldHandler = cp.getHandle().netServerHandler;
            Location loc = player.getLocation();
            VoxelNetServerHandler vnsh = new VoxelNetServerHandler(cs.getHandle().server, cp.getHandle().netServerHandler.networkManager, cp.getHandle());
            vnsh.a(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            cp.getHandle().netServerHandler = vnsh;
            NetworkManager nm = cp.getHandle().netServerHandler.networkManager;
            setNetServerHandler(nm, cp.getHandle().netServerHandler);
            oldHandler.disconnected = true;
            ((CraftServer) Bukkit.getServer()).getServer().networkListenThread.a(vnsh);
        } 
    }
    
    public static boolean setNetServerHandler(NetworkManager nm, NetServerHandler nsh) {
        try {
            Field p = nm.getClass().getDeclaredField("packetListener");
            p.setAccessible(true);
            p.set(nm, nsh);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void a(Packet17EntityLocationAction pckt) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pckt);
                    continue;
                }    
            }
        } else {
            super.a(pckt);
        }
    }

    @Override
    public void a(Packet18ArmAnimation packet18armanimation) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(packet18armanimation);
                    continue;
                }    
            }
        } else {
            super.a(packet18armanimation);
        }
    }

    @Override
    public void a(Packet19EntityAction packet19entityaction) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(packet19entityaction);
                    continue;
                }    
            }
        } else {
            super.a(packet19entityaction);
        }
    }

    @Override
    public void a(Packet20NamedEntitySpawn pnes) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pnes);
                    continue;
                }    
            }
        } else {
            super.a(pnes);
        }
    }

    @Override
    public void a(Packet28EntityVelocity pev) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pev);
                    continue;
                }    
            }
        } else {
            super.a(pev);
        }
    }

    @Override
    public void a(Packet30Entity pe) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pe);
                    continue;
                }    
            }
        } else {
            super.a(pe);
        }
    }

    @Override
    public void a(Packet34EntityTeleport pet) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pet);
                    continue;
                }    
            }
        } else {
            super.a(pet);
        }
    }

    @Override
    public void a(Packet38EntityStatus pes) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pes);
                    continue;
                }    
            }
        } else {
            super.a(pes);
        }
    }

    @Override
    public void a(Packet39AttachEntity pae) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pae);
                    continue;
                }    
            }
        } else {
            super.a(pae);
        }
    }

    @Override
    public void a(Packet40EntityMetadata pem) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pem);
                    continue;
                }    
            }
        } else {
            super.a(pem);
        }
    }

    @Override
    public void a(Packet41MobEffect pme) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(pme);
                    continue;
                }    
            }
        } else {
            super.a(pme);
        }
    }

    @Override
    public void a(Packet42RemoveMobEffect prme) {
        if (VanishManager.isVanished(getPlayer().getPlayer())) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.equals(getPlayer().getPlayer()))
                    continue;
                if (!VanishManager.isOnSafeList(p)) {
                    VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                    VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                    continue;
                } else {
                    super.a(prme);
                    continue;
                }    
            }
        } else {
            super.a(prme);
        }
    }

    @Override
    public void sendPacket(Packet packet) {
        if (packet instanceof CloakPacket) {
            if (VanishManager.isOnSafeList(this.getPlayer().getPlayer()))
                return;
        }
        
        if (packet instanceof Packet31RelEntityMove) {
            if (VanishManager.isVanished(getPlayer().getPlayer())) {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (p.equals(getPlayer().getPlayer()))
                        continue;
                    if (!VanishManager.isOnSafeList(p)) {
                        VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                        VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                        continue;
                    } else {
                        super.sendPacket(packet);
                        continue;
                    }    
                }
                
                return;
            } else {
                super.sendPacket(packet);
                return;
            }
        }
        
        if (packet instanceof Packet32EntityLook) {
            if (VanishManager.isVanished(getPlayer().getPlayer())) {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (p.equals(getPlayer().getPlayer()))
                        continue;
                    if (!VanishManager.isOnSafeList(p)) {
                        VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                        VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                        continue;
                    } else {
                        super.sendPacket(packet);
                        continue;
                    }    
                }
                
                return;
            } else {
                super.sendPacket(packet);
                return;
            }
        }
        
        if (packet instanceof Packet5EntityEquipment) {
            if (VanishManager.isVanished(getPlayer().getPlayer())) {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (p.equals(getPlayer().getPlayer()))
                        continue;
                    if (!VanishManager.isOnSafeList(p)) {
                        VanishUtils.sendPacketToPlayer(p, new CloakPacket((getPlayer().getEntityId())));
                        VanishUtils.sendPacketToPlayer(p, new Packet201PlayerInfo(getPlayer().getPlayer().getName(), false, 0));
                        continue;
                    } else {
                        super.sendPacket(packet);
                        continue;
                    }    
                }
                
                return;
            } else {
                super.sendPacket(packet);
                return;
            }
        }
        
        super.sendPacket(packet);
    }
}
