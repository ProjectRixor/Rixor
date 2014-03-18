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

public class StartCommand {

	@Command(aliases = { "start"}, desc = "Starts the match", usage = "[seconds]", flags = "f", min = 1, max = 1)
	public static void start(final CommandContext args, CommandSender sender) throws Exception {

		if(sender instanceof Player) {
			if(!Client.getClient((Player)sender).isRanked()) {
				sender.sendMessage(ChatColor.RED + "No permission!");
				throw new CommandPermissionsException();
			}
		}

		Match match = Scrimmage.getRotation().getSlot().getMatch();
		if(!match.isCurrentlyStarting()) {
			throw new CommandException("A match is already running!");
		}
		
		int time = 30;
		if(args.argsLength() == 1)
			if(ConversionUtil.convertStringToInteger(args.getString(0),-1) > -1)
				time = ConversionUtil.convertStringToInteger(args.getString(0), -1);
			else {
				throw new CommandException("Please supply a valid time greater than -1");
			}
		if (match.isHasEnded() && args.hasFlag('f')){
			Scrimmage.getRotation().getSlot().getMatch().start(time);
		}
		else if (match.isHasEnded()) {
			throw new CommandException("This match has already ended. Use -f to force start.");
		}
		Scrimmage.getRotation().getSlot().getMatch().start(time);
		/*Scrimmage.broadcast(ChatColor.RED + sender.getName() + ChatColor.DARK_PURPLE + " has started the countdown.");*/
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player) Online).isRanked()) {

				Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.WHITE + " has started the countdown at " + time + ".");

			}
		}

	}
	
}
