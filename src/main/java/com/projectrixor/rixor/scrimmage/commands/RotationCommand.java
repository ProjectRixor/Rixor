package com.projectrixor.rixor.scrimmage.commands;

import com.projectrixor.rixor.scrimmage.Rixor;
import com.projectrixor.rixor.scrimmage.rotation.Rotation;
import com.projectrixor.rixor.scrimmage.rotation.RotationSlot;
import com.projectrixor.rixor.scrimmage.utils.StringUtils;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MasterEjay
 */
public class RotationCommand{


	@Command(aliases = { "rot", "rotation", "rotlist"}, desc = "Shows the rotation", usage = "<page>", min = 0, max = 1)
	public static void rot(final CommandContext args, CommandSender sender) throws Exception {

		List<RotationSlot> rotationSlots = Rixor.getRotation().getRotation();
		List<String> names = new ArrayList<String>();
		for (RotationSlot s : rotationSlots) {
			s.getLoader().parseName();
			names.add(s.getLoader().getName());
		}

		if (args.argsLength() == 1){
			StringUtils.paginate(sender,names,args.getInteger(0),8,"Rotation",ChatColor.DARK_AQUA);
		}
		else {
			StringUtils.paginate(sender, names, 1, 8, "Rotation", ChatColor.DARK_AQUA);
		}

	}
}
