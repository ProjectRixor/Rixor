package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.player.Client;
import com.projectrixor.rixor.scrimmage.utils.ConversionUtil;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.match.Match;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CycleCommand {

	@Command(aliases = { "cycle"}, desc = "Cycles the map", usage = "[seconds]", min = 1, max = 1)
	public static void cycle(final CommandContext args, CommandSender sender) throws Exception {
		if(sender instanceof Player) {
			if(!Client.getClient((Player)sender).isRanked()) {
				sender.sendMessage(ChatColor.RED + "No permission!");
				throw new CommandPermissionsException();
			}
		}
		
		Match match = Scrimmage.getRotation().getSlot().getMatch();
		if(match.isCurrentlyRunning()) {
			match.end(true);
		}
		
		int time = 0;
		if(args.argsLength() == 1)
			if(ConversionUtil.convertStringToInteger(args.getString(0),0) >= 1)
				time = ConversionUtil.convertStringToInteger(args.getString(0), 0);
			else {
				throw new CommandException("Please supply a valid time greater than or equal to 1.");
			}
		
		if(!match.isCurrentlyCycling()) Scrimmage.getRotation().getSlot().getMatch().stop();
		else Scrimmage.getRotation().getSlot().getMatch().setCycling(time);
		
		Scrimmage.getRotation().getSlot().getMatch().cycle(time);
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player) Online).isRanked()) {
				Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.WHITE + " has started the cycle at " + ChatColor.GOLD + time);
				}
		}
	}
}
