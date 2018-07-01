package me.Warpyyy.EGLP.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warps {
	
	//Warps
	
	private static List<Warp> warps = new ArrayList<>();
	
	public static void createWarp(String name, Location loc) {
		warps.add(new Warp(name, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
		saveWarps();
	}
	
	public static void deleteWarp(Warp warp) {
		warps.removeIf(w -> w.equals(warp));
		saveWarps();
	}
	
	public static void loadWarps() {
		warps = Config.warps.getMappableList("warps", Warp.class, new ArrayList<>(), false);
	}
	
	public static Warp getWarp(String name) {
		return warps.stream()
				.filter(wa -> wa.getName().equals(name))
				.findFirst()
				.orElse(null);
	}
	
	public static List<Warp> getWarps(){
		return warps;
	}
	
	public static void saveWarps() {
		Config.warps.set("warps", warps);
		Config.warps.saveConfigSafely();
	}
	
	//CustomWarps
	
	private static List<CustomWarp> cWarps = new ArrayList<>();
	
	public static void createCustomWarp(Player p, String warpname) {
		Location loc = p.getLocation();
		UUID uuid = p.getUniqueId();
		cWarps.add(new CustomWarp(uuid, warpname, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), true));
		saveCustomWarps();
	}
	
	public static void deleteCustomWarp(Player p, CustomWarp cw) {
		cWarps.removeIf(c -> c.equals(cw));
		saveCustomWarps();
	}
	
	public static CustomWarp getCustomWarp(Player p, String warpname) {
		return cWarps.stream()
				.filter(c -> c.getUUID().equals(p.getUniqueId()) && c.getName().equals(warpname))
				.findFirst()
				.orElse(null);
	}
	
	public static void loadCustomWarps() {
		cWarps = Config.c_warps.getMappableList("customwarps", CustomWarp.class, new ArrayList<>(), false);
	}
	
	public static List<CustomWarp> getCustomWarps(Player p){
		return cWarps.stream()
		.filter(c -> p.getUniqueId().equals(c.getUUID()) || !c.isPrivate())
		.collect(Collectors.toList());
	}
	
	public static List<CustomWarp> myCustomWarps(Player p){
		return cWarps.stream()
				.filter(c -> p.getUniqueId().equals(c.getUUID()))
				.collect(Collectors.toList());
	}
	
	public static List<CustomWarp> otherCustomWarps(Player p) {
		return cWarps.stream()
				.filter(c -> !p.getUniqueId().equals(c.getUUID()) && !c.isPrivate())
				.collect(Collectors.toList());
	}
	
	public static void saveCustomWarps() {
		Config.c_warps.set("customwarps", cWarps);
		Config.c_warps.saveConfigSafely();
	}

}
