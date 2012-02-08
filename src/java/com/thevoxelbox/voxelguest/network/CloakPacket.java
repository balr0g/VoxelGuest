/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.network;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.server.Packet29DestroyEntity;

/**
 *
 * @author patrick
 */
public class CloakPacket extends Packet29DestroyEntity {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CloakPacket(int eid) {
        super(eid);
        Field field;
        try {
            field = CloakPacket.class.getSuperclass().getSuperclass().getDeclaredField("a");
        } catch (final NoSuchFieldException e) {
            return;
        } catch (final SecurityException e) {
            return;
        }
        field.setAccessible(true);
        Map<Class, Integer> map;
        try {
            map = (Map<Class, Integer>) field.get(this);
        } catch (final Exception e) {
            return;
        }
        map.put(CloakPacket.class, 29);
        try {
            field.set(this, map);
        } catch (final Exception e) {
        }
    }
}
