package com.projectrixor.rixor.scrimmage.utils;

import java.awt.Color;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import com.projectrixor.rixor.scrimmage.map.MapTeam;

public class ConversionUtil {
	
	public static int convertStringToInteger(String value) {
		return convertStringToInteger(value, -1);
	}
	
	public static int convertStringToInteger(String value, int fallback) {
		try {
			return Integer.parseInt(value);
		} catch(NumberFormatException e) {
			return fallback;
		}
	}
	
	public static DyeColor convertTeamColorToDyeColor(MapTeam team) {
		DyeColor dye = DyeColor.WHITE;
		if (team.getColor().equals(ChatColor.AQUA)) {
			dye = DyeColor.LIGHT_BLUE;
		} else if (team.getColor().equals(ChatColor.BLACK)) {
			dye = DyeColor.BLACK;
		} else if (team.getColor().equals(ChatColor.BLUE)) {
			dye = DyeColor.BLUE;
		} else if (team.getColor().equals(ChatColor.DARK_AQUA)) {
			dye = DyeColor.CYAN;
		} else if (team.getColor().equals(ChatColor.DARK_BLUE)) {
			dye = DyeColor.BLUE;
		} else if (team.getColor().equals(ChatColor.DARK_GRAY)) {
			dye = DyeColor.GRAY;
		} else if (team.getColor().equals(ChatColor.DARK_GREEN)) {
			dye = DyeColor.GREEN;
		} else if (team.getColor().equals(ChatColor.DARK_PURPLE)) {
			dye = DyeColor.PURPLE;
		} else if (team.getColor().equals(ChatColor.DARK_RED)) {
			dye = DyeColor.RED;
		} else if (team.getColor().equals(ChatColor.GOLD)) {
			dye = DyeColor.ORANGE;
		} else if (team.getColor().equals(ChatColor.GRAY)) {
			dye = DyeColor.SILVER;
		} else if (team.getColor().equals(ChatColor.GREEN)) {
			dye = DyeColor.LIME;
		} else if (team.getColor().equals(ChatColor.LIGHT_PURPLE)) {
			dye = DyeColor.PINK;
		} else if (team.getColor().equals(ChatColor.RED)) {
			dye = DyeColor.RED;
		} else if (team.getColor().equals(ChatColor.YELLOW)) {
			dye = DyeColor.YELLOW;
		}
		return dye;	
	}
	
	public static Material convertStringToMaterial(String convert) {
		if(convert == null) return null;
		
		for(Material option : Material.values())
			if(option.name().replaceAll("_", " ").equalsIgnoreCase(convert) || option.name().equalsIgnoreCase(convert))
				return option;
		
		return Material.AIR;
	}
	
	public static DyeColor convertStringToDyeColor(String convert) {
		if(convert == null) return null;
		
		for(DyeColor option : DyeColor.values())
			if(option.name().replaceAll("_", " ").equalsIgnoreCase(convert) || option.name().equalsIgnoreCase(convert))
				return option;
		
		return DyeColor.WHITE;
	}
	
	public static Enchantment convertStringToEnchantment(String convert) {
		if(convert == null) return null;
		
		for(Enchantment option : Enchantment.values())
			if(option.getName().replaceAll("_", " ").equalsIgnoreCase(convert) || option.getName().equalsIgnoreCase(convert))
				return option;
		
		return null;
	}
	
	public static PotionEffectType convertStringToPotionEffectType(String convert) {
		if(convert == null) return null;
		
		for(PotionEffectType option : PotionEffectType.values()) {
			if(option != null && option.getName() != null && (option.getName().replaceAll("_", " ").equalsIgnoreCase(convert) || option.getName().equalsIgnoreCase(convert)))
				return option;
		}
		
		return null;
	}
	
	public static Color convertHexStringToColor(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	public static String commaList(List<String> values) {
		return commaList(values, "");
	}
	
	public static String commaList(List<String> values, ChatColor suffix) {
		return commaList(values, "" + suffix);
	}
	
	public static String commaList(List<String> values, String suffix) {
		String text = "";
		if(values.size() == 1)
			text += values.get(0);
		else if(values.size() >= 2) {
			/*
			 * index 0 should prefix ""
			 * index 1 to (max index - 1) should prefix ", "
			 * index max index should prefix " and "
			 */
			
			int index = 0;
			while(index < values.size()) {
				if(index > 0) {
					text += suffix;
					if(index == (values.size() -1))
						text += " and ";
					else text += ", ";
				}
				
				text += ChatColor.RED + values.get(index);
				index++;
			}
		}
		
		return text + suffix;
	}
	
	public static String staffList(List<String> values, ChatColor suffix) {
		String text = "";
		if(values.size() == 1)
			text += values.get(0);
		else if(values.size() >= 2) {
			/*
			 * index 0 should prefix ""
			 * index 1 to (max index - 1) should prefix ", "
			 * index max index should prefix " and "
			 */
			
			int index = 0;
			while(index < values.size()) {
				if(index > 0) {
					text += suffix;
					if(index == (values.size() -1))
						text += ", ";
					else text += ", ";
				}
				
				text += ChatColor.RED + values.get(index);
				index++;
			}
		}
		
		return text + suffix;
	}
	
	public static String formatTime(int originalTime) {
		int time = originalTime;
		int hours = (time - time % (60 * 60)) / 60 / 60;
		String hS = "" + hours;
		if(hours < 10) hS = "0" + hours;
		
		time = time - (hours * 60 * 60);
		int minutes = (time - time % 60) / 60;
		String mS = "" + minutes;
		if(minutes < 10) mS = "0" + minutes;
		
		time = time - (minutes * 60);
		int seconds = time;
		String sS = "" + seconds;
		if(seconds < 10) sS = "0" + seconds;
		
		String text = mS + ":" + sS;
		if(hours > 0) text = hS + ":" + text;
		
		return text;
	}
}
