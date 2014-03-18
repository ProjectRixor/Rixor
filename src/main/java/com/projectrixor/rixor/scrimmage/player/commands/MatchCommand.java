package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.Var;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.extras.Contributor;
import com.projectrixor.rixor.scrimmage.rotation.Rotation;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class MatchCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdl, String[] args) {
		List<String> pendingSend = new ArrayList<>();
		/*Current Map*/
		Map map = Scrimmage.getRotation().getSlot().getMap();
		
		//Client client = Client.getClient((Player) sender);
		
		String score = ChatColor.AQUA + "Score: ";
		
		Rotation rot = Scrimmage.getRotation();

		pendingSend.add(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "---------------------" + ChatColor.DARK_AQUA + map.getName() + ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "---------------------");
		pendingSend.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Objective: " + ChatColor.RESET + "" + ChatColor.GOLD + map.getObjective());
		if (map.getAuthors().size() == 1){
			pendingSend.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Author: " + ChatColor.RESET + "" + ChatColor.GOLD + map.getAuthors().get(0).getName());
		}
		else {
			pendingSend.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Authors: " + ChatColor.RESET + "" + ChatColor.GOLD + map.getAuthors());
			for (Contributor c : map.getAuthors()){
				if (c.getContribution() != null){
					pendingSend.add(ChatColor.WHITE + "- " + ChatColor.DARK_AQUA + c.getName() + ChatColor.ITALIC + c.getContribution());
				}
				else {
					pendingSend.add(ChatColor.WHITE + "- " + ChatColor.DARK_AQUA + c.getName());
				}
			}
		}
		if (map.getRules().size() > 0){
			pendingSend.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Rules:");
			int ruleNumber = 1;
			for (final String rule : map.getRules()) {
				pendingSend.add(ChatColor.RESET + "" + ChatColor.GOLD + ruleNumber + ") " + rule);
				++ruleNumber;
			}

		}
		if (Var.nextMap == null){
			pendingSend.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Next Map: " + ChatColor.RESET + "" + ChatColor.GOLD + "None");

		}
		else {
			pendingSend.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Next Map: " + ChatColor.RESET + "" + ChatColor.GOLD + Var.nextMap);
		}

		for (String s : pendingSend){
			sender.sendMessage(s);
		}
		pendingSend.clear();
		return true;
	}
	
}
