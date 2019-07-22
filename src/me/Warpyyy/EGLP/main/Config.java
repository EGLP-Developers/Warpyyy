package me.Warpyyy.EGLP.main;

import java.io.File;
import java.util.UUID;

import me.mrletsplay.mrcore.bukkitimpl.config.BukkitCustomConfig;
import me.mrletsplay.mrcore.config.ConfigLoader;
import me.mrletsplay.mrcore.config.CustomConfig;
import me.mrletsplay.mrcore.config.FileCustomConfig;
import me.mrletsplay.mrcore.config.mapper.JSONObjectMapper;
import me.mrletsplay.mrcore.json.JSONObject;

public class Config {

	private static File configFile = new File(Main.getBaseFolder(), "config.yml"),
			warpsFile = new File(Main.getBaseFolder(), "warps.yml"),
			cWarpsFile = new File(Main.getBaseFolder(), "customwarps.yml"),
			dataFile = new File(Main.getBaseFolder(), "data.yml");

	public static BukkitCustomConfig cfg, warps, c_warps;
	public static FileCustomConfig data;

	public static void init() {
		cfg = ConfigLoader.loadConfigFromFile(new BukkitCustomConfig(configFile), configFile, true);
		CustomConfig defaults = ConfigLoader.loadStreamConfig(Main.pl.getResource("config.yml"), true);
		cfg.addDefaults(defaults);
		cfg.applyDefaults();

		warps = ConfigLoader.loadConfigFromFile(new BukkitCustomConfig(warpsFile), warpsFile, true);

		warps.registerMapper(JSONObjectMapper.create(Warp.class, (s, warp) -> {
			JSONObject map = new JSONObject();
			map.put("name", warp.getName());
			map.put("world", warp.getWorld());
			map.put("x", warp.getX());
			map.put("y", warp.getY());
			map.put("z", warp.getZ());
			map.put("yaw", warp.getYaw());
			map.put("pitch", warp.getPitch());
			return map;
		}, (s, j) -> {
			String name = (String) j.get("name");
			String world = (String) j.get("world");
			double x = j.getDouble("x");
			double y = j.getDouble("y");
			double z = j.getDouble("z");
			float yaw = (float) j.getDouble("yaw");
			float pitch = (float) j.getDouble("pitch");
			return new Warp(name, world, x, y, z, yaw, pitch);
		}));

		c_warps = ConfigLoader.loadConfigFromFile(new BukkitCustomConfig(cWarpsFile), cWarpsFile, true);

		c_warps.registerMapper(JSONObjectMapper.create(CustomWarp.class, (s, object) -> {
			JSONObject map = new JSONObject();
			map.put("name", object.getName());
			map.put("world", object.getWorld());
			map.put("player-uuid", object.getUUID().toString());
			map.put("x", object.getX());
			map.put("y", object.getY());
			map.put("z", object.getZ());
			map.put("yaw", object.getYaw());
			map.put("pitch", object.getPitch());
			map.put("isPrivate", object.isPrivate());
			return map;
		}, (s, j) -> {
			String name = (String) j.get("name");
			String world = (String) j.get("world");
			String uuid = (String) j.get("player-uuid");
			double x = j.getDouble("x");
			double y = j.getDouble("y");
			double z = j.getDouble("z");
			float yaw = (float) j.getDouble("yaw");
			float pitch = (float) j.getDouble("pitch");
			boolean isPrivate = j.getBoolean("isPrivate");
			return new CustomWarp(UUID.fromString(uuid), name, world, x, y, z, yaw, pitch, isPrivate);
		}));

		cfg.saveToFile();

		data = ConfigLoader.loadFileConfig(dataFile, true);
	}

}
