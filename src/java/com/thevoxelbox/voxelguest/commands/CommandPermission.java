package com.thevoxelbox.voxelguest.commands;

public @interface CommandPermission {
	/**
	 * 
	 * The permission needed
	 * 
	 */
	
	String permission() default "";
}
