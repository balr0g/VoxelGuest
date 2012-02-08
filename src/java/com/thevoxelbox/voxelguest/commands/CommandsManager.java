/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelguest.commands;

import com.thevoxelbox.voxelguest.VoxelGuest;
import com.thevoxelbox.voxelguest.permissions.GuestPermissions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author patrick
 */
public class CommandsManager {
    // =============================
    // - VOXELGUEST COMMAND ENGINE
    // - 
    // - Built by: psanker
    // =============================
    
    private String[] helpArgs = {"help", "h", "?"};
    
    protected Map<String, Method> aliases = new HashMap<String, Method>();
    protected Map<String, String> help = new HashMap<String, String>();
    protected Map<Method, Object> instances = new HashMap<Method, Object>();
    
    public void registerCommands(Class<?> cls) {
        Object obj = null;
        
        try {
            obj = cls.newInstance();
        } catch (InstantiationException ex) {
            VoxelGuest.log("Could not register commands from " + cls.getCanonicalName(), 2);
        } catch (IllegalAccessException ex) {
            VoxelGuest.log("Could not register commands from " + cls.getCanonicalName(), 2);
        }
        
        for (Method method : cls.getMethods()) {
            if (!method.isAnnotationPresent(Command.class)) {
                continue; // Improper command registration, helper method, or other method type
            }
            
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            Command command = method.getAnnotation(Command.class);
            
            // If not static, grab the instance to reference from.
            // If the instance doesn't exist, move on. Command is not registered.
            if (!isStatic) {
                if (obj == null) {
                    continue;
                } else {
                    instances.put(method, obj);
                }
            }
            
            for (String alias : command.aliases()) {
                String al = alias.toLowerCase();
                
                aliases.put(al, method);
                help.put(al, command.help());
            }
        }
    }
    
    private boolean isRegistered(String command) {
        return aliases.containsKey(command.toLowerCase());
    }
    
    public boolean executeCommand(org.bukkit.command.Command command, CommandSender cs, String[] args) {
        // Search if command is registered
        if (!this.isRegistered(command.getName())) {
            cs.sendMessage("¤cUnhandled command: " + command.getName());
            return false;
        }
        
        // Get method and check to see if it matches the Command method interface
        Method method = aliases.get(command.getName());
        
        if (!method.isAnnotationPresent(Command.class)) {
            cs.sendMessage("¤cMalformatted command: " + command.getName());
            VoxelGuest.log("Found incorrectly formatted command " + command.getName() + " that was registered.", 1);
            return false;
        }
        
        Command cmd = method.getAnnotation(Command.class);
        
        // Check out of bounds for arguments and other things like such
        boolean playerOnly = cmd.playerOnly();
        
        if (playerOnly && !(cs instanceof Player)) {
            cs.sendMessage("¤cPlayer-only command: " + command.getName());
            return false;
        }
        
        int[] bounds = cmd.bounds();
        if (args.length < bounds[0] || (args.length > bounds[1] && bounds[1] >= 0)) {
            cs.sendMessage("¤cArgument length out of bounds: " + command.getName());
            cs.sendMessage("¤6Usage: " + command.getUsage());
            return false;
        }
        
        if (args.length == 1 && Arrays.asList(helpArgs).contains(args[0])) {
            cs.sendMessage("¤6===Help: " + command.getName() + "===");
            cs.sendMessage(cmd.help());
            cs.sendMessage("¤6=========================");
            return true;
        }
        
        if (method.isAnnotationPresent(CommandPermission.class)) {
        	CommandPermission perm = method.getAnnotation(CommandPermission.class);
        	
        	if (!GuestPermissions.hasPermission(cs, perm.permission())) {
        		cs.sendMessage("¤cYou do not have sufficient privileges to access this command.");
        		return false;
        	}
        }
        
        // Checks clear... Run command
        Object instance = instances.get(method);
        return invokeMethod(method, cs, args, instance);
    }
    
    private boolean invokeMethod(Method method, CommandSender cs, String[] args, Object instance) {
        Object[] commandMethodArgs = {cs, args};
        
        try {
            method.invoke(instance, commandMethodArgs);
            return true;
        } catch (IllegalAccessException ex) {
            cs.sendMessage("¤cInternal error. Could not execute command.");
            return false;
        } catch (IllegalArgumentException ex) {
            cs.sendMessage("¤cInternal error. Could not execute command.");
            return false;
        } catch (InvocationTargetException ex) {
            cs.sendMessage("¤cInternal error. Could not execute command.");
            return false;
        }
    }
    
    public void commandLog(org.bukkit.command.Command command, CommandSender cs, String[] args, boolean status) {
    	File f = new File("plugins/VoxelGuest/logs/commands.txt");
    	PrintWriter pw = null;
    	
    	try {
    		if (!f.exists()) {
    			f.getParentFile().mkdirs();
    			f.createNewFile();
    		}
    		
    		pw = new PrintWriter(f);
    		Date d = new Date();
    		
    		if (cs instanceof Player) {
    			Player p = (Player) cs;
    			
    			String concat = "";
    			
    			for (int i = 0; i < args.length; i++) {
    				if (i == (args.length - 1)) {
    					concat = concat + args[i];
    					continue;
    				}
    				
    				concat = concat + args[i] + " ";
    			}
    			
    			pw.append(d.toString() + " Command: " + command.getName() + 
    					", Player: " + p.getName() +
    					", Location: [" + p.getLocation().getWorld().getName() + "] (" + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ() +
    					"), Arguments: \"" + concat + "\"" +
    					", Status: " + ((status) ? "EXECUTED" : "FAILED"));
    			
    			pw.close();
    		} else {
    			String concat = "";
    			
    			for (int i = 0; i < args.length; i++) {
    				if (i == (args.length - 1)) {
    					concat = concat + args[i];
    					continue;
    				}
    				
    				concat = concat + args[i] + " ";
    			}
    			
    			pw.append(d.toString() + " Command: " + command.getName() + 
    					", Sender: [CONSOLE]" +
    					", Arguments: \"" + concat + "\"" +
    					", Status: " + ((status) ? "EXECUTED" : "FAILED"));
    			
    			pw.close();
    		}
    	} catch (IOException ex) {
    		VoxelGuest.log("Could not create command log file", 1);
    	}
    }
}

/*
 * ========================
 * Example command method
 * ========================
 * 
 * @Command(
 *      aliases={"command", "cmnd", "cmd"},
 *      bounds={0,-1},
 *      help="This is the help for this command\n" +
 *           "This is another line of help"
 * )
 * public void command(CommandSender cs, String[] args) { // <-- YOU NEED THESE ARGS
 *      ... Code to execute...
 * }
 * 
 * 
 */
