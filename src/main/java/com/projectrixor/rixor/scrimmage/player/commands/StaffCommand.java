package com.projectrixor.rixor.scrimmage.player.commands;

import java.util.ArrayList;
import java.util.List;


import com.projectrixor.rixor.scrimmage.player.Client;
import com.projectrixor.rixor.scrimmage.utils.ConversionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdl, String[] args) {
		
		List<String> staffs = new ArrayList<String>();
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player)Online).isRanked()) {
				staffs.add(Online.getDisplayName());
				}
			}
		int size = staffs.size();
		String staff = ConversionUtil.staffList(staffs,ChatColor.WHITE);
		String broadcast = "" + staff;
		String lessSpam = ChatColor.GRAY + "Staff Online (" + size + "): ";
		sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "ParaPGM v1.0" + ChatColor.WHITE + "] " + lessSpam + broadcast);
		return false;
	}
	
}