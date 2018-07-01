package me.Warpyyy.EGLP.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Warp {
	
	private double x, y, z;
	private float yaw, pitch;
	private String world, name;
	
	public Warp(String name, String world, double x, double y, double z, float yaw, float pitch) {
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
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
	
	public String getWorld() {
		return world;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getWarpLocation() {
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

}
