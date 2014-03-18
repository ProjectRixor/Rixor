package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.player.Client;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.player.PlayerChatEvent;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlobalCommand  {


	@Command(aliases = { "g", "global"}, desc = "Speaks in Global Chat", usage = "[message]", min = 1, max = -1)
	public static void global(final CommandContext args, CommandSender sender) throws Exception {

		String message = "";

		message = args.getJoinedStrings(0);
		Scrimmage.callEvent(new PlayerChatEvent(Client.getClient((Player)sender), message, false));
	}
	
}
