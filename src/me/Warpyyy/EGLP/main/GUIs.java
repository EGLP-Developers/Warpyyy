package me.Warpyyy.EGLP.main;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.mrletsplay.mrcore.bukkitimpl.ItemUtils;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIBuilderMultiPage;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIElement;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIMultiPage;
import me.mrletsplay.mrcore.bukkitimpl.gui.ItemSupplier;
import me.mrletsplay.mrcore.bukkitimpl.gui.StaticGUIElement;
import me.mrletsplay.mrcore.bukkitimpl.gui.event.GUIBuildEvent;
import me.mrletsplay.mrcore.bukkitimpl.gui.event.GUIBuildPageItemEvent;
import me.mrletsplay.mrcore.bukkitimpl.versioned.VersionedDyeColor;
import me.mrletsplay.mrcore.bukkitimpl.versioned.VersionedMaterial;

public class GUIs {

	public static final GUIMultiPage<Warp> DEL_WARP_GUI, WARP_LIST_GUI;
	
	static {
		DEL_WARP_GUI = buildDelWarpGUI();
		WARP_LIST_GUI = buildWarpListGUI();
	}
	
	public static Inventory getDelWarpGUI() {
		return DEL_WARP_GUI.getForPlayer(null);
	}
	
	public static Inventory getWarpListGUI() {
		return WARP_LIST_GUI.getForPlayer(null);
	}
	
	private static GUIMultiPage<Warp> buildDelWarpGUI(){
		GUIBuilderMultiPage<Warp> builder = new GUIBuilderMultiPage<>("§7§lSelect a warp to delete", 6);
		
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
						  "§7Listed warps: §b" + Warps.getWarps().size());
			}
		});
		
		builder.setSupplier(new ItemSupplier<Warp>() {
			
			@Override
			public GUIElement toGUIElement(GUIBuildPageItemEvent<Warp> event, Warp item) {
				return new GUIElement() {
					
					@Override
					public ItemStack getItem(GUIBuildEvent event) {
						return ItemUtils.createItem(Material.PAPER, 1, 0, "§6" + item.getName(), 
								"§7World: §b" + item.getWorld(),
								"§7X: §b" + item.getX(),
								"§7Y: §b" + item.getY(),
								"§7Z: §b" + item.getZ(),
								"§7Yaw: §b" + item.getYaw(),
								"§7Pitch: §b" + item.getPitch(),
								"§cClick to delete");
					}
				}.setAction(e -> {
					Warps.deleteWarp(item);
					DEL_WARP_GUI.refreshAllInstances();
				});
			}
			
			@Override
			public List<Warp> getItems(GUIBuildEvent event) {
				return Warps.getWarps();
			}
		});
		
		builder.addPreviousPageItem(48, ItemUtils.createItem(ItemUtils.arrowLeft(VersionedDyeColor.WHITE), "§7Previous page"));
		builder.addNextPageItem(52, ItemUtils.createItem(ItemUtils.arrowRight(VersionedDyeColor.WHITE), "§7Next page"));
		
		return builder.create();
	}
	
	private static GUIMultiPage<Warp> buildWarpListGUI(){
		GUIBuilderMultiPage<Warp> builder = new GUIBuilderMultiPage<>("§7§lWarps", 6);
		
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
						  "§7Listed warps: §b" + Warps.getWarps().size());
			}
		});
		
		builder.setSupplier(new ItemSupplier<Warp>() {
			
			@Override
			public GUIElement toGUIElement(GUIBuildPageItemEvent<Warp> event, Warp item) {
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
				};
			}
			
			@Override
			public List<Warp> getItems(GUIBuildEvent event) {
				return Warps.getWarps();
			}
		});
		
		builder.addPreviousPageItem(48, ItemUtils.createItem(ItemUtils.arrowLeft(VersionedDyeColor.WHITE), "§7Previous page"));
		builder.addNextPageItem(52, ItemUtils.createItem(ItemUtils.arrowRight(VersionedDyeColor.WHITE), "§7Next page"));
		
		return builder.create();
	}
	
}
