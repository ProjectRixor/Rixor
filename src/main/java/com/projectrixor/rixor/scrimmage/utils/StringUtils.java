package com.projectrixor.rixor.scrimmage.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author MasterEjay
 */
public class StringUtils {

	public static void paginate(CommandSender sender, List<String> results,
	                      int page, int pageLength, String title, ChatColor color) {

		SortedMap<Integer, String> map = new TreeMap<Integer, String>();
		int id = 1;
		for (String s : results){
			map.put(id, s);
			id++;
		}

		String currentPage = String.valueOf(page);

		int totalPages = (((map.size() % pageLength) == 0) ? map.size() / pageLength : (map.size() / pageLength) + 1);

		sender.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "--------------- " + color + title + " (" + ChatColor.AQUA + currentPage + color + " of "
				+ ChatColor.AQUA + totalPages + color + ")"
		+ ChatColor.RED + "" + ChatColor.STRIKETHROUGH + " ---------------");
		int i = 0, k = 0;
		page--;
		for (final Map.Entry<Integer, String> e : map.entrySet()) {
			k++;
			if ((((page * pageLength) + i + 1) == k) && (k != ((page * pageLength) + pageLength + 1))) {
				i++;
				sender.sendMessage(ChatColor.YELLOW + " - " + e.getValue());
			}
		}
	}
}
