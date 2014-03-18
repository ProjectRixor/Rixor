package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.Rixor;
import com.projectrixor.rixor.scrimmage.match.Match;
import com.projectrixor.rixor.scrimmage.player.Client;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author MasterEjay
 */
public class RestartCommand {

	@Command(aliases = { "restart"}, desc = "Restarts the server", usage = "<time>", min = 0, max = 1)
	public static void restart(final CommandContext args, CommandSender sender) throws Exception {
		if(sender instanceof Player) {
			if(!Client.getClient((Player)sender).isRanked()) {
				sender.sendMessage(ChatColor.RED + "No permission!");
				throw new CommandPermissionsException();
			}
		}

		Match match = Rixor.getRotation().getSlot().getMatch();
		if(match.isCurrentlyRunning()) {
			match.end(true);
		}
		if (args.argsLength() == 0){
			Rixor.getRotation().getSlot().getMatch().restart(30);
		}
		else {
			Rixor.getRotation().getSlot().getMatch().restart(args.getInteger(0));
		}

	}


}
