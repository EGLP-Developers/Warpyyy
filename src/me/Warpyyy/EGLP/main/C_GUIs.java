package me.Warpyyy.EGLP.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.mrletsplay.mrcore.bukkitimpl.ItemUtils;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUI;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIBuilder;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIBuilderMultiPage;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIElement;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIMultiPage;
import me.mrletsplay.mrcore.bukkitimpl.gui.ItemSupplier;
import me.mrletsplay.mrcore.bukkitimpl.gui.StaticGUIElement;
import me.mrletsplay.mrcore.bukkitimpl.gui.event.GUIBuildEvent;
import me.mrletsplay.mrcore.bukkitimpl.gui.event.GUIBuildPageItemEvent;
import me.mrletsplay.mrcore.bukkitimpl.versioned.VersionedDyeColor;
import me.mrletsplay.mrcore.bukkitimpl.versioned.VersionedMaterial;

public class C_GUIs {
	
	public static final GUIMultiPage<CustomWarp> CUSTOMWARP_LIST_GUI, DEL_CUSTOMWARP_GUI;
	public static final GUIMultiPage<World> WORLD_LIST;
	public static final GUI SETTINGS;
	
	static {
		CUSTOMWARP_LIST_GUI = buildCustomWarpListGUI();
		DEL_CUSTOMWARP_GUI = buildCustomWarpDelGUI();
		SETTINGS = buildSettingsGUI();
		
		WORLD_LIST = buildWorldListGUI();
	}
	
	public static Inventory getCustomWarpListGUI(Player p) {
		return CUSTOMWARP_LIST_GUI.getForPlayer(p);
	}
	
	public static Inventory getDeleteCustomWarpGUI(Player p) {
		return DEL_CUSTOMWARP_GUI.getForPlayer(p);
	}
	
	public static Inventory getSettingsGUI(Player p, CustomWarp cw) {
		Map<String, Object> properties = new HashMap<>();
		properties.put("customwarp", cw);
		return SETTINGS.getForPlayer(p, Main.pl, properties);
	}
	
	public static Inventory getWorldListGUI(Player p) {
		return WORLD_LIST.getForPlayer(p);
	}
	
	private static GUIMultiPage<World> buildWorldListGUI(){
		GUIBuilderMultiPage<World> builder = new GUIBuilderMultiPage<>("§7§lWold management", 6);
		
		GUIElement gP = new StaticGUIElement(ItemUtils.createItem(VersionedMaterial.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		for(int i = 0; i < 6*9; i++) {
			builder.addElement(i, gP);
		}
		
		for(int y = 1; y < 5; y++) {
			for(int x = 3; x < 8; x++) {
				int slot = y * 9 + x;
				builder.addElement(slot, (GUIElement) null);
				builder.addPageSlots(slot);
			}
		}
		
		builder.addElement(10, new GUIElement() {
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				return ItemUtils.createItem(Material.DISPENSER, 1, 0, "§7§lInformation", 
						"§7Existing worlds: §b" + Bukkit.getWorlds().size(),
						"§7Enabled worlds: §b" + Main.getEnabledWorlds().size(),
						" ",
						"§7(Click to sync)");
			}
		}.setAction(e -> {
			List<World> w = Bukkit.getWorlds();
			List<String> worlds = Main.getEnabledWorlds();
			worlds.clear();
			Config.cfg.set("enabled-worlds", worlds);
			Config.cfg.saveToFile();
			
			w.forEach(wo -> {
				if(!Main.containsWorld(wo)) {
					worlds.add(wo.getName());
				}
			});
			
			Config.cfg.set("enabled-worlds", worlds);
			Config.cfg.saveToFile();
			e.getGUI().refreshAllInstances();
		}));
		
		builder.setSupplier(new ItemSupplier<World>() {

			@Override
			public List<World> getItems(GUIBuildEvent event) {
				return Bukkit.getWorlds();
			}

			@Override
			public GUIElement toGUIElement(GUIBuildPageItemEvent<World> event, World world) {
				return new GUIElement() {
					@Override
					public ItemStack getItem(GUIBuildEvent event) {
						return ItemUtils.createItem(Material.PAPER, 1, 0, "§a§l" + world.getName(),
								(Main.containsWorld(world)?"§7Click to §cremove§7 world from the list":"§7Click to §aadd§7 world to the list"));
					}
				}.setAction(e -> {
					if(Main.containsWorld(world)) {
						Main.removeWorld(world);
					}else {
						Main.addWorld(world);
					}
					e.getGUI().refreshInstance(e.getPlayer());
				});
			}
		});
		
		builder.addPreviousPageItem(48, ItemUtils.createItem(ItemUtils.arrowLeft(VersionedDyeColor.WHITE), "§7Previous page"));
		builder.addNextPageItem(52, ItemUtils.createItem(ItemUtils.arrowRight(VersionedDyeColor.WHITE), "§7Next page"));
		
		return builder.create();
	}
	
	private static GUI buildSettingsGUI() {
		GUIBuilder builder = new GUIBuilder("§7§lSettings", 3);
		
		GUIElement gP = new StaticGUIElement(ItemUtils.createItem(VersionedMaterial.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		for(int i = 0; i < 3*9; i++) {
			builder.addElement(i, gP);
		}
		
		for(int y = 1; y < 2; y++) {
			for(int x = 3; x < 8; x++) {
				int slot = y * 9 + x;
				builder.addElement(slot, (GUIElement) null);
			}
		}
		
		builder.addElement(10, new GUIElement() {
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				return ItemUtils.createItem(Material.DISPENSER, 1, 0, "§0");
			}
		});
		
		builder.addElement(12, new GUIElement() {
			
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				CustomWarp w = (CustomWarp) event.getGUIHolder().getProperty(Main.pl, "customwarp");
				if(w.isPrivate()) {
					return ItemUtils.createItem(VersionedMaterial.GRAY_DYE, 1, "§aSet to public");
				}else {
					return ItemUtils.createItem(VersionedMaterial.LIME_DYE, 1, "§cSet to private");
				}
			}
		}.setAction(e -> {
			CustomWarp w = (CustomWarp) e.getGUIHolder().getProperty(Main.pl, "customwarp");
			w.setPrivate(!w.isPrivate());
			e.getGUI().refreshInstance(e.getPlayer());
			CUSTOMWARP_LIST_GUI.refreshAllInstances();
		}));
		
		builder.addElement(18, new GUIElement() {
			
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				return ItemUtils.createItem(Material.ENDER_PEARL, 1, 0, "§7§lBack");
			}
		}.setAction(e -> {
			e.getPlayer().openInventory(getCustomWarpListGUI(e.getPlayer()));
		}));
		
		return builder.create();
	}
	
	private static GUIMultiPage<CustomWarp> buildCustomWarpListGUI(){
		GUIBuilderMultiPage<CustomWarp> builder = new GUIBuilderMultiPage<>("§7§lCustomwarps", 6);
		
		GUIElement gP = new StaticGUIElement(ItemUtils.createItem(VersionedMaterial.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		for(int i = 0; i < 6*9; i++) {
			builder.addElement(i, gP);
		}
		
		for(int y = 1; y < 5; y++) {
			for(int x = 3; x < 8; x++) {
				int slot = y * 9 + x;
				builder.addElement(slot, (GUIElement) null);
				builder.addPageSlots(slot);
			}
		}
		
		builder.addElement(10, new GUIElement() {
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				int my = Warps.myCustomWarps(event.getPlayer()).size();
				int other = Warps.otherCustomWarps(event.getPlayer()).size();
				return ItemUtils.createItem(Material.DISPENSER, 1, 0, "§7§lInformation", 
						  "§7Own customwarps: §b" + my,
						  "§7Public customwarps: §b" + other);
			}
		});
		
		builder.addElement(28, new GUIElement() {
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				if(Config.data.getBoolean(event.getPlayer().getUniqueId() + ".hide-other")) {
					return ItemUtils.createItem(VersionedMaterial.GRAY_DYE, 1, "§5Hide other customwarps");
				}else {
					return ItemUtils.createItem(VersionedMaterial.LIME_DYE, 1, "§7Show other customwarps");
				}
			}
		}.setAction(e -> {
			if(Config.data.getBoolean(e.getPlayer().getUniqueId() + ".hide-other")) {
				Config.data.set(e.getPlayer().getUniqueId() + ".hide-other", false, true);
			}else {
				Config.data.set(e.getPlayer().getUniqueId() + ".hide-other", true, true);
			}
			e.getGUI().refreshInstance(e.getPlayer());
		}));
		
		builder.setSupplier(new ItemSupplier<CustomWarp>() {
			
			@Override
			public GUIElement toGUIElement(GUIBuildPageItemEvent<CustomWarp> event, CustomWarp item) {
				return new GUIElement() {
					
					@Override
					public ItemStack getItem(GUIBuildEvent event) {
						return ItemUtils.createItem(Material.PAPER, 1, 0, "§6" + item.getName() + (!item.isPrivate() && !item.getUUID().equals(event.getPlayer().getUniqueId())?" §7(§bOwned by " + item.getPlayerFromUUID().getName() + "§7)":""), 
								"§7World: §b" + item.getWorld(),
								"§7X: §b" + item.getX(),
								"§7Y: §b" + item.getY(),
								"§7Z: §b" + item.getZ(),
								"§7Yaw: §b" + item.getYaw(),
								"§7Pitch: §b" + item.getPitch(),
								(item.getPlayerFromUUID().equals(event.getPlayer())?" ":""),
								(item.getPlayerFromUUID().equals(event.getPlayer())?"§7(Click to change settings)":""));
					}
				}.setAction(e -> {
					if(!item.getUUID().equals(event.getPlayer().getUniqueId())) return;
					e.getPlayer().openInventory(getSettingsGUI(e.getPlayer(), item));
				});
			}
			
			@Override
			public List<CustomWarp> getItems(GUIBuildEvent event) {
				if(Config.data.getBoolean(event.getPlayer().getUniqueId() + ".hide-other")) {
					return Warps.myCustomWarps(event.getPlayer());
				}else {
					return Warps.getCustomWarps(event.getPlayer());
				}
			}
		});
		
		builder.addPreviousPageItem(48, ItemUtils.createItem(ItemUtils.arrowLeft(VersionedDyeColor.WHITE), "§7Previous page"));
		builder.addNextPageItem(52, ItemUtils.createItem(ItemUtils.arrowRight(VersionedDyeColor.WHITE), "§7Next page"));
		
		return builder.create();
	}
	
	private static GUIMultiPage<CustomWarp> buildCustomWarpDelGUI(){
		GUIBuilderMultiPage<CustomWarp> builder = new GUIBuilderMultiPage<>("§7§lClick to delete", 6);
		
		GUIElement gP = new StaticGUIElement(ItemUtils.createItem(VersionedMaterial.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		for(int i = 0; i < 6*9; i++) {
			builder.addElement(i, gP);
		}
		
		for(int y = 1; y < 5; y++) {
			for(int x = 3; x < 8; x++) {
				int slot = y * 9 + x;
				builder.addElement(slot, (GUIElement) null);
				builder.addPageSlots(slot);
			}
		}
		
		builder.addElement(10, new GUIElement() {
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				int my = Warps.myCustomWarps(event.getPlayer()).size();
				return ItemUtils.createItem(Material.DISPENSER, 1, 0, "§7§lInformation", 
						  "§7Created customwarps: §b" + my);
			}
		});
		
		builder.setSupplier(new ItemSupplier<CustomWarp>() {
			
			@Override
			public GUIElement toGUIElement(GUIBuildPageItemEvent<CustomWarp> event, CustomWarp item) {
				return new GUIElement() {
					
					@Override
					public ItemStack getItem(GUIBuildEvent event) {
						return ItemUtils.createItem(Material.PAPER, 1, 0, "§6" + item.getName(), 
								"§7World: §b" + item.getWorld(),
								"§7X: §b" + item.getX(),
								"§7Y: §b" + item.getY(),
								"§7Z: §b" + item.getZ(),
								"§7Yaw: §b" + item.getYaw(),
								"§7Pitch: §b" + item.getPitch());
					}
				}.setAction(e -> {
					Warps.deleteCustomWarp(event.getPlayer(), item);
					e.getPlayer().closeInventory();
				});
			}
			
			@Override
			public List<CustomWarp> getItems(GUIBuildEvent event) {
				return Warps.myCustomWarps(event.getPlayer());
			}
		});
		
		builder.addPreviousPageItem(48, ItemUtils.createItem(ItemUtils.arrowLeft(VersionedDyeColor.WHITE), "§7Previous page"));
		builder.addNextPageItem(52, ItemUtils.createItem(ItemUtils.arrowRight(VersionedDyeColor.WHITE), "§7Next page"));
		
		return builder.create();
	}

}
