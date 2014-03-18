package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.player.Client;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author MasterEjay
 */
public class IdCommand {

	public static Player checkPlayer(CommandSender sender)
			throws CommandException{
		if (sender instanceof Player) {
			return (Player) sender;
		} else {
			throw new CommandException("A player context is required. (Specify a world or player if the command supports it.)");
		}
	}

	@Command(aliases = { "matchnum", "id"}, desc = "Tells you what match number this is", usage = "", min = 0, max = 0)
	public static void global(final CommandContext args, CommandSender sender) throws Exception {
		Player sourcePlayer = checkPlayer(sender);
		World world = sourcePlayer.getWorld();
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player)Online).isRanked()) {
				Online.sendMessage(ChatColor.RED + "The match you are playing has the current ID of" + ChatColor.GREEN + " #" + ChatColor.BLUE + world.getName() + ChatColor.RED + "! If you would like a copy of this match, find the world folder with this ID.");
			}
		}
	}
}
