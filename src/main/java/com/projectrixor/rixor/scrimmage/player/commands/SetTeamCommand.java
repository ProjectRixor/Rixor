package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapTeam;

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTeamCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdl, String[] args) {
		if(sender instanceof Player) {
			if(!Client.getClient((Player)sender).isRanked()) {
				sender.sendMessage(ChatColor.RED + "No permission!");
				return false;
			}
		}
		
		Map map = Scrimmage.getRotation().getSlot().getMap();
		
		if(args.length < 2) {
			sender.sendMessage(ChatColor.RED + "/setteam <team> <new name>");
			return false;
		}
		
		MapTeam team = map.getTeam(args[0]);
		
		String name = "";
		int i = 1;
		while(i < args.length) {
			name += " " + args[i];
			i++;
		}
		name = name.substring(1);
		team.setDisplayName(name, true);
		/*sender.sendMessage(team.getColor() + team.getName() + ChatColor.GRAY + " has been changed to " + team.getColor() + team.getDisplayName() + ChatColor.GRAY + ".");*/
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player) Online).isRanked()) {
				Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.WHITE + " has changed " + team.getColor() + team.getName() + ChatColor.WHITE + " to " + team.getColor() + team.getDisplayName() + ChatColor.WHITE + ".");
			}
		}
		return false;
	}
	
}
