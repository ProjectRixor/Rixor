package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.player.Client;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapTeam;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceCommand {

	@Command(aliases = { "force"}, desc = "Forces a player onto a team", usage = "[name], [team]", min = 2, max = 2)
	public static void force(final CommandContext args, CommandSender sender) throws Exception {
		if(sender instanceof Player == false) {
			throw new CommandException("This command is for players only!");
		}
		

		Player target = Bukkit.getPlayer(args.getString(0));
		Map map = Scrimmage.getRotation().getSlot().getMap();
		Client targetClient = Client.getClient((Player) target);
		
		MapTeam team = map.getObservers();
		if(args.argsLength() == 2) {
			team = map.getTeam(args.getString(0));
			if(team == null) {
				throw new CommandException("No teams matched query.");
			}
		}
		if (targetClient.getTeam().equals(team)) {
			sender.sendMessage(ChatColor.RED + "That person is already on that team!");
		} else {
			Scrimmage.broadcast(team.getColor() + target.getName() + ChatColor.GRAY + " has been forced to join " + team.getColor() + team.getDisplayName() + ChatColor.GRAY + ".");
			for (Player Online : Bukkit.getOnlinePlayers()) {
				if (Client.getClient((Player) Online).isRanked()) {
					Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (targetClient.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.WHITE + " forced " + (targetClient.getClient((Player) sender).getStars()) + team.getColor() + target.getName() + ChatColor.WHITE + ".");
				}
			}
	    }
		targetClient.setTeam(team);

	}
	
}
