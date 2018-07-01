package me.Warpyyy.EGLP.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.mrletsplay.mrcore.bukkitimpl.BukkitCustomConfig;
import me.mrletsplay.mrcore.config.CompactCustomConfig;
import me.mrletsplay.mrcore.config.ConfigExpansions.ExpandableCustomConfig.ObjectMapper;
import me.mrletsplay.mrcore.config.ConfigLoader;
import me.mrletsplay.mrcore.config.CustomConfig.ConfigSaveProperty;

public class Config {
	
	public static BukkitCustomConfig cfg, warps, c_warps;
	public static CompactCustomConfig data;
	
	public static void init() {
		cfg = ConfigLoader.loadBukkitConfig(new File(Main.getBaseFolder(), "config.yml"), ConfigSaveProperty.KEEP_CONFIG_SORTING);
		try {
			BukkitCustomConfig defaults = new BukkitCustomConfig((File) null).loadConfig(Main.pl.getResource("config.yml"));
			cfg.loadDefault(defaults, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		warps = (BukkitCustomConfig) new BukkitCustomConfig(new File(Main.getBaseFolder(), "warps.yml"), ConfigSaveProperty.KEEP_CONFIG_SORTING);
		warps.loadConfigSafely();
		
		warps.registerMapper(new ObjectMapper<Warp>(Warp.class) {

			@Override
			public Map<String, Object> mapObject(Warp object) {
				Map<String, Object> map = new HashMap<>();
				map.put("name", object.getName());
				map.put("world", object.getWorld());
				map.put("x", object.getX());
				map.put("y", object.getY());
				map.put("z", object.getZ());
				map.put("yaw", object.getYaw());
				map.put("pitch", object.getPitch());
				return map;
			}

			@Override
			public Warp constructObject(Map<String, Object> map) {
				String name = (String) map.get("name");
				String world = (String) map.get("world");
				double x = castGeneric(map.get("x"), Double.class);
				double y = castGeneric(map.get("y"), Double.class);
				double z = castGeneric(map.get("z"), Double.class);
				float yaw = castGeneric(map.get("yaw"), Float.class);
				float pitch = castGeneric(map.get("pitch"), Float.class);
				return new Warp(name, world, x, y, z, yaw, pitch);
			}
		});
		
		c_warps = (BukkitCustomConfig) new BukkitCustomConfig(new File(Main.getBaseFolder(), "customwarps.yml"), ConfigSaveProperty.KEEP_CONFIG_SORTING);
		c_warps.loadConfigSafely();
		
		c_warps.registerMapper(new ObjectMapper<CustomWarp>(CustomWarp.class) {

			@Override
			public Map<String, Object> mapObject(CustomWarp object) {
				Map<String, Object> map = new HashMap<>();
				map.put("name", object.getName());
				map.put("world", object.getWorld());
				map.put("player-uuid", object.getUUID());
				map.put("x", object.getX());
				map.put("y", object.getY());
				map.put("z", object.getZ());
				map.put("yaw", object.getYaw());
				map.put("pitch", object.getPitch());
				map.put("isPrivate", object.isPrivate());
				return map;
			}

			@Override
			public CustomWarp constructObject(Map<String, Object> map) {
				String name = (String) map.get("name");
				String world = (String) map.get("world");
				String uuid = (String) map.get("player-uuid");
				double x = castGeneric(map.get("x"), Double.class);
				double y = castGeneric(map.get("y"), Double.class);
				double z = castGeneric(map.get("z"), Double.class);
				float yaw = castGeneric(map.get("yaw"), Float.class);
				float pitch = castGeneric(map.get("pitch"), Float.class);
				boolean isPrivate = castGeneric(map.get("isPrivate"), Boolean.class);
				return new CustomWarp(UUID.fromString(uuid), name, world, x, y, z, yaw, pitch, isPrivate);
			}
		});
		
		cfg.saveConfigSafely();
		
		data = new CompactCustomConfig(new File(Main.getBaseFolder(), "data.yml"));
		data.loadConfigSafely();
	}

}
