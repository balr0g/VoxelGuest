package com.thevoxelbox.voxelguest.util;

import java.lang.reflect.Method;

public abstract class Formatter {
    
    public static Formatter selectFormatter(Class<? extends Formatter> cls) {
        try {
            Method method = cls.getMethod("install");
            
            Formatter formatter = (Formatter) method.invoke(null);
            return formatter;
        } catch (Throwable t) {
            return null;
        }
    }
    
    public abstract Formatter install();
    
    public abstract String[] format(String in);
}
