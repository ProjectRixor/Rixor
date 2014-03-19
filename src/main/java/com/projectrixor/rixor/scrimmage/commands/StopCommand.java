package com.projectrixor.rixor.scrimmage.commands;

import com.projectrixor.rixor.scrimmage.Rixor;
import com.projectrixor.rixor.scrimmage.match.Match;
import com.projectrixor.rixor.scrimmage.player.Client;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdl, String[] args) {
		if(sender instanceof Player) {
			if(!Client.getClient((Player)sender).isRanked()) {
				sender.sendMessage(ChatColor.RED + "No permission!");
				return false;
			}
		}
		
		Match match = Rixor.getRotation().getSlot().getMatch();
		if(match.isCurrentlyRunning()) {
			match.end(true);
			Rixor.getRotation().getSlot().getMatch().stop();
			
			for (Player Online : Bukkit.getOnlinePlayers()) {
				if (Client.getClient((Player) Online).isRanked()) {
					Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.WHITE + " has forced the game to stop.");
				}
			}
			return true;
		}
		
		Rixor.getRotation().getSlot().getMatch().stop();
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player) Online).isRanked()) {
				Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.WHITE + " has forced the timer to stop.");
					BarAPI.setMessage(Online, ChatColor.RED + "Countdown has been cancelled.", 100f);
			}
			
		}
		return true;
	}
	
}
