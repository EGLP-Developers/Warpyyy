package me.Warpyyy.EGLP.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class CustomWarp {
	
	private double x, y, z;
	private float yaw, pitch;
	private String world, name;
	private UUID uuid;
	private boolean isPrivate;
	
	public CustomWarp(UUID uuid, String name, String world, double x, double y, double z, float yaw, float pitch, boolean isPrivate) {
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.uuid = uuid;
		this.isPrivate = isPrivate;
	}
	
	public String getName() {
		return name;
	}
	
	public String getWorld() {
		return world;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public OfflinePlayer getPlayerFromUUID() {
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	public boolean isPrivate() {
		return isPrivate;
	}
	
	public Location getCustomWarpLocation() {
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}
	
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
		Warps.saveCustomWarps();
	}

}
