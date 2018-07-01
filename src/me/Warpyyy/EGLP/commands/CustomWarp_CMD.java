package me.Warpyyy.EGLP.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Warpyyy.EGLP.main.C_GUIs;
import me.Warpyyy.EGLP.main.Config;
import me.Warpyyy.EGLP.main.CustomWarp;
import me.Warpyyy.EGLP.main.Main;
import me.Warpyyy.EGLP.main.Messages;
import me.Warpyyy.EGLP.main.Warps;

public class CustomWarp_CMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Messages.getString("console-error"));
			return true;
		}
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("customwarp")) {
			if(args.length < 1) return sendCommandHelp(p);
			
			if(!Config.cfg.getBoolean("enable-customwarps")) {
				p.sendMessage(Messages.getString("disabled-customwarp-system"));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length != 2) return sendCommandHelp(p);
				int max = Config.cfg.getInt("max-customwarps");
				int my = Warps.myCustomWarps(p).size();
				if(my > max) {
					p.sendMessage("You can't create more than " + max + " customwarps");
					return true;
				}
				Warps.createCustomWarp(p, args[1]);
				p.sendMessage(Messages.getString("customwarp-created", "customwarp", args[1]));
			}
			
			if(args[0].equalsIgnoreCase("delete")) {
				if(args.length != 1) return sendCommandHelp(p);
				int my = Warps.myCustomWarps(p).size();
				if(my <= 0) {
					p.sendMessage(Messages.getString("no-customwarps"));
					return true;
				}
				p.openInventory(C_GUIs.getDeleteCustomWarpGUI(p));
			}
			
			if(args[0].equalsIgnoreCase("list")) {
				if(args.length != 1) return sendCommandHelp(p);
				p.openInventory(C_GUIs.getCustomWarpListGUI(p));
			}
			
			if(args[0].equalsIgnoreCase("worlds")) {
				if(args.length != 1) return sendCommandHelp(p);
				if(!p.hasPermission(Main.perm_customwarps_settings)) {
					p.sendMessage(Messages.getString("perm-error"));
					return true;
				}
				p.openInventory(C_GUIs.getWorldListGUI(p));
			}
			
			if(args[0].equalsIgnoreCase("tp")) {
				if(args.length != 2) return sendCommandHelp(p);
				CustomWarp cw = Warps.getCustomWarp(p, args[1]);
				if(cw == null) {
					p.sendMessage(Messages.getString("customwarp-error", "warp", args[1]));
					return true;
				}
				if(Bukkit.getWorld(cw.getWorld()) == null) {
					p.sendMessage(Messages.getString("world-error", "world", cw.getWorld()));
					return true;
				}
				p.teleport(cw.getCustomWarpLocation());
			}
		}
		return false;
	}
	
	private static boolean sendCommandHelp(CommandSender sender) {
		sender.sendMessage("§7Command help:");
		sender.sendMessage("§7/cw list - List all customwarps");
		sender.sendMessage("§7/cw create <warpname> - Create a customwarp");
		sender.sendMessage("§7/cw delete - Show a gui to delete a customwarp");
		sender.sendMessage("§7/cw tp <warpname> - Teleport you to a customwarp");
		if(sender.hasPermission(Main.perm_customwarps_settings)) {
			sender.sendMessage("§7/cw worlds - Show a world list gui");
		}
		return true;
	}

}
