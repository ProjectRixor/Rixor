package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.Var;
import com.projectrixor.rixor.scrimmage.map.MapLoader;
import com.projectrixor.rixor.scrimmage.player.Client;
import com.projectrixor.rixor.scrimmage.rotation.Rotation;
import com.projectrixor.rixor.scrimmage.rotation.RotationSlot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetNextCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdl, String[] args) {
		if(sender instanceof Player) {
			if(!Client.getClient((Player)sender).isRanked()) {
				sender.sendMessage(ChatColor.RED + "No permission!");
				return false;
			}
		}
		
		if(args.length < 1) {
			sender.sendMessage(ChatColor.RED + "/setnext <map>");
			return false;
		}
		
		String name = "";
		int i = 0;
		while(i < args.length) {
			name += " " + args[i];
			i++;
		}
		name = name.substring(1);
		MapLoader found = Rotation.getMap(name);
	
		if(found == null) {
			sender.sendMessage(ChatColor.GRAY + "We could not find a map by the name '" + ChatColor.GOLD + name + ChatColor.GRAY + "'.");
			return false;
		}
	
		Rotation rot = Scrimmage.getRotation();
		RotationSlot after = rot.getNext();
		
		rot.setNext(new RotationSlot(found));
		if (Var.canSetNext == 0) {
		Var.nextMap = found.getName();
		}
		Var.canSetNext = 1;
		
		RotationSlot current = rot.getSlot();
		if(current.getMatch().isLoaded()) {
			after.getMap().unload();
			rot.getNext().load();
			after.getMatch().setLoaded(true);
		}
		
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player) Online).isRanked()) {
				Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.WHITE + " has set the next map to " + ChatColor.GOLD + found.getName() + ChatColor.WHITE + ".");
			}
		}
		return false;
	}
	
}
