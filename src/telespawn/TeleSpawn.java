/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telespawn;

import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.util.permissions.*;
/**
 *
 * @author Adam
 */
public class TeleSpawn extends JavaPlugin{


   public static final Logger LOG = Logger.getLogger("Minecraft1");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments){
        LOG.info("[TeleSpawn]" + label );
        if(label.equalsIgnoreCase("tbcreate")){
            
            return handleTbcreate(sender, arguments);
        }
        if(label.equalsIgnoreCase("tb")){
            
            return handleTb(sender, arguments);
        }
        if(label.equalsIgnoreCase("tblist")) {
            return handleTbList(sender);
        }
        if(label.equalsIgnoreCase("ts")) {
            return handleTs(sender, arguments);
        }
        
     return false;       
    }
    
    boolean handleTs(CommandSender sender, String[] arguments) {
        
        if(sender instanceof Player){
            LOG.info("[TeleSpawn] teleporting");
            Location senderLocation=((Player) sender).getLocation();
            World world=((Player) sender).getWorld();
            if (arguments.length>0){
                Player player = getServer().getPlayer(arguments[0]);
                if (player == null){
                    sender.sendMessage("Player " + arguments[0] +" not found.");
                    return true;
                }
                else{
                    sender.sendMessage("Player " + arguments[0] + " teleported.");
                    player.teleport(senderLocation);
                    player.setBedSpawnLocation​(senderLocation, true);
                } 

            }
            return true;
        }
        return false;
    }
    
    boolean handleTbcreate(CommandSender sender, String[] arguments){
    if(arguments.length>0){
        LOG.info("[TeleSpawn] creating teleblock");
        Location senderLocation=((Player) sender).getLocation();
        World world=((Player) sender).getWorld();
        this.getConfig().set("Teleport."+arguments[0]+".location", senderLocation.toVector());
        this.getConfig().set("Teleport."+arguments[0]+".world", world.getName());
        this.saveConfig();
        return true;
    }
    return false;
    }
    
    boolean handleTb(CommandSender sender, String[] arguments){
        if(arguments.length>0){
            LOG.info("[TeleSpawn] teleporting");
            Vector vector =  this.getConfig().getVector("Teleport."+arguments[0]+".location");
            String name =  this.getConfig().getString("Teleport."+arguments[0]+".world");
            World world=Bukkit.getWorld(name);
            Location location =  new Location(world, vector.getX(), vector.getY(), vector.getZ());
            ((Player) sender).teleport(location);
        }
        return false;
    }
    
    boolean handleTbList(CommandSender sender) {
        LOG.info("[TeleSpawn] lisiting");
       Set<String> teleBlocks =  this.getConfig().getConfigurationSection("Teleport").getKeys(false); 
       for(String teleport: teleBlocks) {
           String name =  this.getConfig().getString("Teleport."+teleport+".world");
           sender.sendMessage(teleport + " in " + name);
       }
       return true;
    }
}

