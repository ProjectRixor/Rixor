package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.player.Client;
import com.projectrixor.rixor.scrimmage.utils.UpdateUtil;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author MasterEjay
 */
public class UpdateCommand {

	@Command(aliases = { "update", "updateplugin"}, desc = "Updates the plugin to the latest version", usage = "", min = 0, max = -1)
	public static void update(final CommandContext args, CommandSender sender) throws Exception {
		if(sender instanceof Player) {
			if(!Client.getClient((Player)sender).isRanked()) {
				throw new CommandPermissionsException();
			}
		}

		UpdateUtil.checkForUpdate("http://update.masterejay.us/Version.txt","http://update.masterejay.us/Rixor.jar",
				Scrimmage.getInstance().getDescription(),(Player)sender);
	}
}
