package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.player.Client;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AdminChat  {


	@Command(aliases = { "admin", "a" , "adminchat"}, desc = "Speaks in admin chat", usage = "[message]", min = 1, max = -1)
	public static void admin(final CommandContext args, CommandSender sender) throws Exception { {
		if(sender instanceof Player) {
			if(!Client.getClient((Player)sender).isRanked()) {
					throw new CommandPermissionsException();
			}
		}
		
		String message = args.getJoinedStrings(0);
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player) Online).isRanked()) {
				Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + message);
			}
		}
		}
	}
	
}
