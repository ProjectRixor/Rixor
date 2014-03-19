package com.projectrixor.rixor.scrimmage.commands;

import com.projectrixor.rixor.scrimmage.Rixor;
import com.projectrixor.rixor.scrimmage.utils.StringUtils;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;


public class MapsCommand {

	@Command(aliases = { "maps", "ml", "mapsloaded"}, desc = "Lists all the maps loaded", usage = "<page>", min = 0, max = 1)
	public static void maps(final CommandContext args, CommandSender sender) throws Exception {

		List<String> maps = Rixor.getMapsLoaded();
		if (args.argsLength() == 1){
			StringUtils.paginate(sender, maps, args.getInteger(0), 8, "Loaded Maps", ChatColor.DARK_AQUA);
		}
		else {
			StringUtils.paginate(sender, maps, 1, 8, "Loaded Maps", ChatColor.DARK_AQUA);
		}



	}
}
