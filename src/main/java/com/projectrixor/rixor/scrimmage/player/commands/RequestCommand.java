package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RequestCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdl, String[] args) {
		if(args.length < 1) {
			sender.sendMessage(ChatColor.RED + "/request <message>");
			return false;
		}
		
		String message = "";
		int i = 0;
		while(i < args.length) {
			message += " " + args[i];
			i++;
		}
		message = message.substring(1);
		sender.sendMessage(ChatColor.RED + "Your request has been submitted.");
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player)Online).isRanked()) {
				Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.GRAY + " has requested " + ChatColor.WHITE + "'" + message + "'");
				Online.playSound(Online.getLocation(), Sound.ORB_PICKUP, 10, 1);
			}
		}
		return false;
	}
	
}
