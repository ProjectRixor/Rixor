package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.player.Client;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapTeam;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand{

	@com.sk89q.minecraft.util.commands.Command(aliases = { "join", "p", "play", "playgame", "joingame"}, desc = "Joins the game", usage = "[team]", min = 0, max = 1)
	public static void join(final CommandContext args, CommandSender sender) throws Exception {
		if(sender instanceof Player == false) {
			throw new CommandException("This command is for players only!");
		}
		Map map = Scrimmage.getRotation().getSlot().getMap();
		Client client = Client.getClient((Player) sender);

		if (Scrimmage.getRotation().getSlot().getMatch().isHasEnded()){
			throw new CommandException("The match has ended! Please wait for the server to cycle.");
		}

		MapTeam team = map.getObservers();
		if(args.argsLength() == 0) {
			team = map.getLowest();
		} else if(args.argsLength() == 1) {
			team = map.getTeam(args.getString(0));
			if(team == null) {
				throw new CommandException("No teams matched query.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "/join (team)");
		}
		if (client.getTeam().equals(team)) {
			sender.sendMessage(ChatColor.RED + "You are already on that team!");
		} else {
			Scrimmage.broadcast(team.getColor() + sender.getName() + ChatColor.GRAY + " has joined the " + team.getColor() + team.getDisplayName() + ChatColor.GRAY + ".");
	    }
		client.setTeam(team);
		((Player) sender).setScoreboard(team.getMap().getBoard());

	}
	
}
