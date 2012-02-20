package com.thevoxelbox.voxelguest;

import com.thevoxelbox.voxelguest.modules.MetaData;
import com.thevoxelbox.voxelguest.modules.Module;
import com.thevoxelbox.voxelguest.util.FlatFileManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@MetaData(name="Greylist", description="Allows for the setup of a greylist system!")
public class GreylistModule extends Module {
    
    private static final List<String> greylist = new ArrayList<String>();
    
    private int streamTask = -1;
    private int readerTask = -1;
    private String streamPasswordHash;
    private int streamPort;
    private boolean couldNotConnect = false;
    private long disconnectTimestamp = -1;

    public GreylistModule() {
        super(GreylistModule.class.getAnnotation(MetaData.class));
    }
    
    @Override
    public void enable() {
        String[] list = FlatFileManager.load("greylist");
        
        if (list == null) {
            VoxelGuest.getConfigData().setBoolean("enable-greylist", false);
            super.disable();
            return;
        } else if (!VoxelGuest.getConfigData().getBoolean("enable-greylist")) {
            disable();
            return;
        }
        
        injectGreylist(list);
        
        if (VoxelGuest.getConfigData().getBoolean("enable-greylist-stream") && 
                VoxelGuest.getConfigData().getString("greylist-stream-password") != null &&
                VoxelGuest.getConfigData().getInt("greylist-stream-port") != -1) {
            
            streamPort = VoxelGuest.getConfigData().getInt("greylist-stream-port");
            streamPasswordHash = VoxelGuest.getConfigData().getString("greylist-stream-password");
            streamTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(VoxelGuest.getInstance(), new Runnable() {

                @Override
                public void run() {
                    if (couldNotConnect && (System.currentTimeMillis() - disconnectTimestamp) < 60000)
                        return;
                    
                    couldNotConnect = false;
                    
                    try {
                        ServerSocket socket = new ServerSocket(streamPort);
                        Future<List<String>> future = Bukkit.getScheduler().callSyncMethod(VoxelGuest.getInstance(), new StreamReader(socket.accept()));
                        
                        List<String> list = future.get();
                        
                        if (list == null || list.isEmpty())
                            return;
                        
                        injectGreylist(list);
                        announceGreylist(list);
                    } catch (InterruptedException ex) {
                        // Don't inject greylist
                    } catch (ExecutionException ex) {
                        // Don't inject greylist
                    } catch (IOException ex) {
                        VoxelGuest.log(name, "Could not connect to stream -- Retrying in a minute...", 1);
                        couldNotConnect = true;
                        disconnectTimestamp = System.currentTimeMillis();
                    }
                }
            }, 0L, 200L);
        }
    }
    
    @Override
    public void disable() {
        Bukkit.getScheduler().cancelTask(streamTask);
    }

    @Override
    public String getLoadMessage() {
        return "Greylist module loaded";
    }
    
    private void announceGreylist(String user) {
        Bukkit.getServer().broadcastMessage(ChatColor.GRAY + user + ChatColor.DARK_GRAY + " has been accepted to the greylist");
    }
    
    private void announceGreylist(List<String> users) {
        Iterator<String> it = users.listIterator();
        
        while (it.hasNext()) {
            String user = it.next();
            announceGreylist(user);
        }
    }
    
    private void injectGreylist(String[] strs) {
        for (String str : strs) {
            if (!greylist.contains(str))
                greylist.add(str);
        }
    }
    
    private void injectGreylist(List<String> list) {
        Iterator<String> it = list.listIterator();
        
        while (it.hasNext()) {
            String user = it.next();
            
            if (!greylist.contains(user))
                greylist.add(user);
        }
    }
    
    private String interpretStreamInput(String input) {
        String[] args = input.split("\\:");
        
        if (args[0].equals(VoxelGuest.getConfigData().getString("greylist-stream-password"))) {
            String user = args[1];
            boolean accepted = !(Boolean.parseBoolean(args[2]));
            
            if (accepted) {
                return user;
            }
        }
        
        return null;
    }
    
    class StreamReader implements Callable<List<String>> {
        private final Socket socket;

        public StreamReader(Socket s) {
            socket = s;
        }

        @Override
        public List<String> call() {
            List<String> list = new ArrayList<String>();
            String line = null;
            
            try {
                BufferedReader stream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                while ((line = stream.readLine()) != null) {
                    String toAdd = interpretStreamInput(line);
                    
                    if (toAdd != null) {
                        if (!list.contains(toAdd))
                            list.add(line);
                    }
                }
                
                stream.close();
                return list;
            } catch (IOException ex) {
                return null;
            }
        }
    }
}
