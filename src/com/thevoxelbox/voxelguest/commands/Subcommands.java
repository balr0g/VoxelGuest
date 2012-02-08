package com.thevoxelbox.voxelguest.commands;

public @interface Subcommands {
	/**
	 * 
	 * Arguments of subcommands
	 * NOTE: ORDER MATTERS
	 * 
	 */
	
	String[] arguments();
	
	/**
	 * 
	 * Permissions needed for subcommands
	 * NOTE: ORDER MATTERS
	 * 
	 */
	
	String[] permission();
}
