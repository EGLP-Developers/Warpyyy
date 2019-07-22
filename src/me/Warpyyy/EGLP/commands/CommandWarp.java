package me.Warpyyy.EGLP.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Warpyyy.EGLP.main.GUIs;
import me.Warpyyy.EGLP.main.Main;
import me.Warpyyy.EGLP.main.Messages;
import me.Warpyyy.EGLP.main.Warp;
import me.Warpyyy.EGLP.main.Warps;

public class CommandWarp implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Messages.getString("console-error"));
			return true;
		}
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("setwarp")) {
			if(args.length != 1) return sendCommandHelp(p);
			if(!p.hasPermission(Main.perm_create_warps)) {
				p.sendMessage(Messages.getString("perm-error"));
				return true;
			}
			if(Warps.getWarp(args[0]) != null) {
				p.sendMessage(Messages.getString("warp-already-exist"));
				return true;
			}
			Location loc = p.getLocation();
			Warps.createWarp(args[0], loc);
			p.sendMessage(Messages.getString("warp-created", "warp", args[0]));
		}
		
		if(cmd.getName().equalsIgnoreCase("delwarp")) {
			if(args.length != 0) return sendCommandHelp(p);
			if(!p.hasPermission(Main.perm_delete_warps)) {
				p.sendMessage(Messages.getString("perm-error"));
				return true;
			}
			if(Warps.getWarps().isEmpty()) {
				p.sendMessage(Messages.getString("no-warps"));
				return true;
			}
			p.openInventory(GUIs.getDelWarpGUI());
		}
		
		if(cmd.getName().equalsIgnoreCase("warps")) {
			if(args.length != 0) return sendCommandHelp(p);
			if(!p.hasPermission(Main.perm_list_warps)) {
				p.sendMessage(Messages.getString("perm-error"));
				return true;
			}
			if(Warps.getWarps().isEmpty()) {
				p.sendMessage(Messages.getString("no-warps"));
				return true;
			}
			p.openInventory(GUIs.getWarpListGUI());
		}
		
		if(cmd.getName().equalsIgnoreCase("warp")) {
			if(args.length != 1) return sendCommandHelp(p);
			Warp warp = Warps.getWarp(args[0]);
			if(warp == null) {
				p.sendMessage(Messages.getString("warp-error", "warp", args[0]));
				return true;
			}
			if(Bukkit.getWorld(warp.getWorld()) == null) {
				p.sendMessage(Messages.getString("world-error", "world", warp.getWorld()));
				return true;
			}
			p.teleport(warp.getWarpLocation());
		}
		return false;
	}
	
	private static boolean sendCommandHelp(CommandSender sender) {
		sender.sendMessage("§7Command help:");
		sender.sendMessage("§7/warp <Warp> - Teleport you to a created warp");
		sender.sendMessage("§7/setwarp <name> - Create a warp");
		sender.sendMessage("§7/delwarp - Show a gui to delete a created warp");
		sender.sendMessage("§7/warps - Show a gui with all warps");
		return true;
	}

}
