package me.Warpyyy.EGLP.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.mrletsplay.mrcore.config.ConfigLoader;
import me.mrletsplay.mrcore.config.CustomConfig;
import me.mrletsplay.mrcore.config.impl.DefaultFileCustomConfig;
import me.mrletsplay.mrcore.config.locale.LocaleProvider;
import me.mrletsplay.mrcore.config.locale.LocaleProvider.CustomLocaleProvider;
import me.mrletsplay.mrcore.config.locale.LocaleProvider.Locale;

public class Messages {
	
	private static LocaleProvider localeProvider;
	private static Locale locale;
	
	public static void init() {
		localeProvider = new LocaleProvider(new File(Main.pl.getDataFolder(), "lang"));
		localeProvider.setCustomLocaleProvider(new CustomLocaleProvider(new File(Main.pl.getDataFolder(), "lang")));
	
		CustomConfig en = new DefaultFileCustomConfig(null);
		
		en.addDefault("console-error", "{prefix}§cOnly players can use this command");
		en.addDefault("perm-error", "{prefix}§cSorry, but you don't have the permission to use this command");
		
		en.addDefault("warp-created", "{prefix}§aSuccessfully created warp {warp} on your current location");
		en.addDefault("no-warps", "{prefix}§cNo existing warps");
		
		en.addDefault("disabled-customwarp-system", "{prefix}§cSorry, but the customwarp system is disabled");
		en.addDefault("customwarp-created", "{prefix}§aSuccessfully created customwarp {customwarp} on you current location");
		en.addDefault("no-customwarps", "{prefix}§cNo existing customwarps");

		en.addDefault("customwarp-error", "{prefix}§cThe warp {warp} doesn't exist");
		en.addDefault("warp-error", "{prefix}§cThe warp {warp} doesn't exist");
		en.addDefault("world-error", "{prefix}§cThe world {world} doesn't exist anymore");
		en.addDefault("warp-already-exist", "{prefix}§cWarp already exist");
		
		en.applyDefaults();
		
		CustomConfig de = new DefaultFileCustomConfig(null);
		de.addDefaults(ConfigLoader.loadStreamConfig(Main.pl.getResource("de.yml"), true));
		de.applyDefaults();
		
		localeProvider.registerLocale("de", de);
		localeProvider.registerLocale("en", en);
		
		localeProvider.setDefaultLocale("en");
		locale = localeProvider.getLocale(Config.cfg.getString("locale", "en", true));
	}
	
	public static String getString(String path, String... params) {
		List<String> pars = new ArrayList<>(Arrays.asList(params));
		pars.addAll(Arrays.asList("prefix", Main.getPrefix()));
		return locale.getMessage(path, pars.toArray(new String[pars.size()]));
	}

}
