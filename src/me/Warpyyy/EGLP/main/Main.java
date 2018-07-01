package me.Warpyyy.EGLP.main;

import java.io.File;
import java.util.List;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.Warpyyy.EGLP.commands.CustomWarp_CMD;
import me.Warpyyy.EGLP.commands.Warp_CMDs;
import me.mrletsplay.mrcore.bukkitimpl.MrCoreBukkitImpl;

public class Main extends JavaPlugin implements Listener{
	
	public static Main pl;
	
	public static String perm_create_warps, perm_delete_warps, perm_list_warps, perm_customwarps_settings;
	
	private static List<String> worlds;
	
	@Override
	public void onEnable() {
		pl = this;
		
		MrCoreBukkitImpl.loadMrCore(this);
		
		Config.init();
		Messages.init();
		
		Warps.loadWarps();
		Warps.loadCustomWarps();
		
		perm_create_warps = "warpyyy.create";
		perm_delete_warps = "warpyyy.delete";
		perm_list_warps = "warpyyy.list";
		perm_customwarps_settings = "warpyyy.customwarp.settings";
		
		getCommand("setwarp").setExecutor(new Warp_CMDs());
		getCommand("delwarp").setExecutor(new Warp_CMDs());
		getCommand("warps").setExecutor(new Warp_CMDs());
		getCommand("warp").setExecutor(new Warp_CMDs());
		
		getCommand("customwarp").setExecutor(new CustomWarp_CMD());
		getCommand("cwarp").setExecutor(new CustomWarp_CMD());
		
		worlds = Config.cfg.getStringList("enabled-worlds");
		
		getLogger().info("Enabled warpyyy");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabled warpyyy");
	}
	
	public static String getPrefix() {
		return Config.cfg.getString("prefix");
	}
	
	public static File getBaseFolder() {
		return pl.getDataFolder();
	}
	
	public static void addWorld(World world) {
		worlds.add(world.getName());
		Config.cfg.set("enabled-worlds", worlds);
		Config.cfg.saveConfigSafely();
	}
	
	public static void removeWorld(World world) {
		worlds.remove(world.getName());
		Config.cfg.set("enabled-worlds", worlds);
		Config.cfg.saveConfigSafely();
	}
	
	public static List<String> getEnabledWorlds(){
		return worlds;
	}
	
	public static boolean containsWorld(World world) {
		return worlds.stream().filter(w -> world.getName().equals(w)).findFirst().isPresent();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().isOp()) {
			UpdateChecker.checkForUpdate(e.getPlayer());
		}
	}

}
