package com.projectrixor.rixor.scrimmage.map.objective;

import lombok.Getter;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapTeam;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;

public class WoolObjective extends TeamObjective {
	
	@Getter Location place;
	@Getter DyeColor wool;
	
	public WoolObjective(Map map, MapTeam owner, String name, Location place, DyeColor wool) {
		super(map, owner, name);
		this.place = place;
		this.wool = wool;
	}
	
	public static DyeColor getDye(String string) {
		for(DyeColor dye : DyeColor.values())
			if(dye.name().replaceAll("_", " ").equalsIgnoreCase(string))
				return dye;
		    
		return null;
	}
	
	public static ChatColor getColor(String string) {
		for (DyeColor dye : DyeColor.values())
			if (dye.getColor().equals(DyeColor.BLACK)) {
				return ChatColor.BLACK;
			} else if (dye.getColor().equals(DyeColor.BLUE)) {
				return ChatColor.BLUE;
			} else if (dye.getColor().equals(DyeColor.BROWN)) {
				return ChatColor.BLACK;
			} else if (dye.getColor().equals(DyeColor.CYAN)) {
				return ChatColor.DARK_AQUA;
			} else if (dye.getColor().equals(DyeColor.GRAY)) {
				return ChatColor.DARK_GRAY;
			} else if (dye.getColor().equals(DyeColor.GREEN)) {
				return ChatColor.DARK_GREEN;
			} else if (dye.getColor().equals(DyeColor.LIGHT_BLUE)) {
				return ChatColor.AQUA;
			} else if (dye.getColor().equals(DyeColor.LIME)) {
				return ChatColor.GREEN;
			} else if (dye.getColor().equals(DyeColor.MAGENTA)) {
				return ChatColor.LIGHT_PURPLE;
			} else if (dye.getColor().equals(DyeColor.ORANGE)) {
				return ChatColor.GOLD;
			} else if (dye.getColor().equals(DyeColor.PINK)) {
				return ChatColor.LIGHT_PURPLE;
			} else if (dye.getColor().equals(DyeColor.PURPLE)) {
				return ChatColor.DARK_PURPLE;
			} else if (dye.getColor().equals(DyeColor.RED)) {
				return ChatColor.RED;
			} else if (dye.getColor().equals(DyeColor.SILVER)) {
				return ChatColor.GRAY;
			} else if (dye.getColor().equals(DyeColor.WHITE)) {
				return ChatColor.WHITE;
			} else if (dye.getColor().equals(DyeColor.YELLOW)) {
				return ChatColor.YELLOW;
			} else {
				return ChatColor.WHITE;
			}
		return null;
	}
	
	public boolean isLocation(Location location) {
		boolean x = place.getBlockX() == location.getBlockX();
		boolean y = place.getBlockY() == location.getBlockY();
		boolean z = place.getBlockZ() == location.getBlockZ();
		boolean match = x && y && z;
		
		return match;
	}
	
}
