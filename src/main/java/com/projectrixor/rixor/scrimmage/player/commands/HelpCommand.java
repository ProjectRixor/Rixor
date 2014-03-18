package com.projectrixor.rixor.scrimmage.player.commands;

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdl, String[] args) {
		if(args.length > 0)	{
			if(args[0].equalsIgnoreCase("staff")) {
					if(Client.getClient((Player)sender).isRanked()) {
				// typed /help staff
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						sender.sendMessage(ChatColor.RED + "() = Optional, <> = Required");
						sender.sendMessage(ChatColor.RED + "/a <message> " + ChatColor.GRAY + ": Admin chat.");
						sender.sendMessage(ChatColor.RED + "/cancel " + ChatColor.GRAY + ": Cancels the countdown or match.");
						sender.sendMessage(ChatColor.RED + "/cycle (time) " + ChatColor.GRAY + ": Cycle to the next map.");
						sender.sendMessage(ChatColor.RED + "/force <name> <team> " + ChatColor.GRAY + ": Force a player to a team.");
						sender.sendMessage(ChatColor.RED + "/help (player/staff) " + ChatColor.GRAY + ": A list of all ParaPGM commands.");
						sender.sendMessage(ChatColor.RED + "/setnext <map> " + ChatColor.GRAY + ": Change the next map.");
						sender.sendMessage(ChatColor.RED + "/setteam <team> <new name> " + ChatColor.GRAY + ": Change the name of a team.");
						sender.sendMessage(ChatColor.RED + "/start (time) " + ChatColor.GRAY + ": Starts the countdown to begin.");
						sender.sendMessage(ChatColor.RED + "/stoptheserver <yes/no>" + ChatColor.GRAY + ": Stops the server.");
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
				/*if(args.length > 1  && Client.getClient((Player) sender).isRanked())
				{
					if(args[1].equalsIgnoreCase("1"))
					{
						// typed /help staff 1
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						sender.sendMessage(ChatColor.RED + "/a " + ChatColor.GRAY + ": Admin chat.");
						sender.sendMessage(ChatColor.RED + "/cancel " + ChatColor.GRAY + ": Cancels the countdown or match.!");
						sender.sendMessage(ChatColor.RED + "/cycle " + ChatColor.GRAY + ": Cycle to the next map.");
						sender.sendMessage(ChatColor.RED + "/setnext " + ChatColor.GRAY + ": Change the next map.");
						sender.sendMessage(ChatColor.RED + "/setteam " + ChatColor.GRAY + ": Change the name of a team.");
						sender.sendMessage(ChatColor.RED + "/start " + ChatColor.GRAY + ": Starts the countdown to begin.");
						sender.sendMessage(ChatColor.RED + "/stoptheserver " + ChatColor.GRAY + ": Stops the server.");
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
					}
					else if(args[1].equalsIgnoreCase("2"))
					{
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						sender.sendMessage(ChatColor.RED + "/a " + ChatColor.GRAY + ": Admin chat.");
						sender.sendMessage(ChatColor.RED + "/cancel " + ChatColor.GRAY + ": Cancels the countdown or match.!");
						sender.sendMessage(ChatColor.RED + "/cycle " + ChatColor.GRAY + ": Cycle to the next map.");
						sender.sendMessage(ChatColor.RED + "/setnext " + ChatColor.GRAY + ": Change the next map.");
						sender.sendMessage(ChatColor.RED + "/setteam " + ChatColor.GRAY + ": Change the name of a team.");
						sender.sendMessage(ChatColor.RED + "/start " + ChatColor.GRAY + ": Starts the countdown to begin.");
						sender.sendMessage(ChatColor.RED + "/stoptheserver " + ChatColor.GRAY + ": Stops the server.");
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						// typed /help staff 2
					}
					else
					{
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						sender.sendMessage(ChatColor.RED + "/a " + ChatColor.GRAY + ": Admin chat.");
						sender.sendMessage(ChatColor.RED + "/cancel " + ChatColor.GRAY + ": Cancels the countdown or match.!");
						sender.sendMessage(ChatColor.RED + "/cycle " + ChatColor.GRAY + ": Cycle to the next map.");
						sender.sendMessage(ChatColor.RED + "/setnext " + ChatColor.GRAY + ": Change the next map.");
						sender.sendMessage(ChatColor.RED + "/setteam " + ChatColor.GRAY + ": Change the name of a team.");
						sender.sendMessage(ChatColor.RED + "/start " + ChatColor.GRAY + ": Starts the countdown to begin.");
						sender.sendMessage(ChatColor.RED + "/stoptheserver " + ChatColor.GRAY + ": Stops the server.");
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						// typed /help staff ??? - print available commands
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "You cannot use this command!");// typed /help staff - not ranked
				}
				
			}*/ 	} else {
					sender.sendMessage(ChatColor.RED + "You may not use this command!");
					}
				} else if(args[0].equalsIgnoreCase("player")) {
				// typed /cc tool
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
				sender.sendMessage(ChatColor.RED + "() = Optional, <> = Required");
				sender.sendMessage(ChatColor.RED + "/g <message>" + ChatColor.GRAY + ": Global chat.");
				sender.sendMessage(ChatColor.RED + "/help (player/staff) " + ChatColor.GRAY + ": A list of all ParaPGM commands.");
				sender.sendMessage(ChatColor.RED + "/join (team) " + ChatColor.GRAY + ": Join the match.");
				sender.sendMessage(ChatColor.RED + "/match " + ChatColor.GRAY + ": A command with some information.");
				sender.sendMessage(ChatColor.RED + "/request <message> " + ChatColor.GRAY + ": Send a request to staff.");
				sender.sendMessage(ChatColor.RED + "/staff " + ChatColor.GRAY + ": List all online staff.");
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
			}
			/*	if(args.length > 1)
				{
					if(args[1].equalsIgnoreCase("1"))
					{
						// typed /cc tool add
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						sender.sendMessage(ChatColor.RED + "/g " + ChatColor.GRAY + ": Global chat.");
						sender.sendMessage(ChatColor.RED + "/join " + ChatColor.GRAY + ": Join the match.");
						sender.sendMessage(ChatColor.RED + "/request " + ChatColor.GRAY + ": Send a request to staff.");
						sender.sendMessage(ChatColor.RED + "/staff " + ChatColor.GRAY + ": List all online staff.");
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
					}
					else if(args[1].equalsIgnoreCase("2"))
					{
						// typed /cc tool del
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						sender.sendMessage(ChatColor.RED + "/g " + ChatColor.GRAY + ": Global chat.");
						sender.sendMessage(ChatColor.RED + "/join " + ChatColor.GRAY + ": Join the match.");
						sender.sendMessage(ChatColor.RED + "/request " + ChatColor.GRAY + ": Send a request to staff.");
						sender.sendMessage(ChatColor.RED + "/staff " + ChatColor.GRAY + ": List all online staff.");
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
					}
					else
					{
						// typed /cc tool ??? - print available commands
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
						sender.sendMessage(ChatColor.RED + "/g " + ChatColor.GRAY + ": Global chat.");
						sender.sendMessage(ChatColor.RED + "/join " + ChatColor.GRAY + ": Join the match.");
						sender.sendMessage(ChatColor.RED + "/request " + ChatColor.GRAY + ": Send a request to staff.");
						sender.sendMessage(ChatColor.RED + "/staff " + ChatColor.GRAY + ": List all online staff.");
						sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
					}
				}
				else
				{
					// typed /cc tool - print help
					sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
					sender.sendMessage(ChatColor.RED + "/g " + ChatColor.GRAY + ": Global chat.");
					sender.sendMessage(ChatColor.RED + "/join " + ChatColor.GRAY + ": Join the match.");
					sender.sendMessage(ChatColor.RED + "/request " + ChatColor.GRAY + ": Send a request to staff.");
					sender.sendMessage(ChatColor.RED + "/staff " + ChatColor.GRAY + ": List all online staff.");
					sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
				}
			} else {
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
				sender.sendMessage(ChatColor.RED + "/g " + ChatColor.GRAY + ": Global chat.");
				sender.sendMessage(ChatColor.RED + "/join " + ChatColor.GRAY + ": Join the match.");
				sender.sendMessage(ChatColor.RED + "/request " + ChatColor.GRAY + ": Send a request to staff.");
				sender.sendMessage(ChatColor.RED + "/staff " + ChatColor.GRAY + ": List all online staff.");
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
			}*/
		} else {
			sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
			sender.sendMessage(ChatColor.RED + "() = Optional, <> = Required");
			sender.sendMessage(ChatColor.RED + "/g <message>" + ChatColor.GRAY + ": Global chat.");
			sender.sendMessage(ChatColor.RED + "/help (player/staff) " + ChatColor.GRAY + ": A list of all ParaPGM commands.");
			sender.sendMessage(ChatColor.RED + "/join (team) " + ChatColor.GRAY + ": Join the match.");
			sender.sendMessage(ChatColor.RED + "/match " + ChatColor.GRAY + ": A command with some information.");
			sender.sendMessage(ChatColor.RED + "/request <message> " + ChatColor.GRAY + ": Send a request to staff.");
			sender.sendMessage(ChatColor.RED + "/staff " + ChatColor.GRAY + ": List all online staff.");
			sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "----------------------------");
			// typed /cc, print list of sub-commands
		}
		return true;
	}
}
/*
package me.projectrixor.rixor.scrimmage.player.commands;

import Scrimmage;
import Map;
import MapTeam;
import Client;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTeamCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdl, String[] args) {
		if(sender instanceof Player) {
			if(!Client.getClient((Player) sender).isRanked()) {
				sender.sendMessage(ChatColor.RED + "No permission!");
				return false;
			}
		}
		
		Map map = Scrimmage.getRotation().getSlot().getMap();
		
		if(args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Invalid Arguments supplied!");
			sender.sendMessage(ChatColor.RED + "/setteam <team> <new name>");
			return false;
		}
		
		MapTeam team = map.getTeam(args[0]);
		
		String name = "";
		int i = 1;
		while(i < args.length) {
			name += " " + args[i];
			i++;
		}
		name = name.substring(1);
		team.setDisplayName(name, true);
		/sender.sendMessage(team.getColor() + team.getName() + ChatColor.GRAY + " has been changed to " + team.getColor() + team.getDisplayName() + ChatColor.GRAY + ".");/
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if (Client.getClient((Player) Online).isRanked()) {
				Online.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + (Client.getClient((Player) sender).getStars()) + (Client.getClient((Player) sender).getTeam().getColor()) + sender.getName() + ChatColor.WHITE + " has changed " + team.getColor() + team.getName() + ChatColor.WHITE + " to " + team.getColor() + team.getDisplayName() + ChatColor.WHITE + ".");
			}
		}
		return false;
	}
	
}

*/