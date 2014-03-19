package com.projectrixor.rixor.scrimmage.map;

import com.projectrixor.rixor.scrimmage.Rixor;
import com.projectrixor.rixor.scrimmage.map.kit.ItemKit;
import com.projectrixor.rixor.scrimmage.map.region.ConfiguredRegion;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class MapTeamSpawn {
	
	public static float DEFAULT_YAW_VALUE = 0;
	public static float DEFAULT_PITCH_VALUE = 0;
	
	Map map;
	
	List<Location> possibles = new ArrayList<Location>();
	String kitName;
	
	public MapTeamSpawn(Map map, List<Location> possibles, String kitName) {
		this.map = map;
		this.possibles = possibles;
		this.kitName = kitName;
	}
	
	public MapTeamSpawn(Map map, ConfiguredRegion region, String kitName) {
		this(map, region.getLocations(), kitName);
	}
	
	public Location getSpawn() {
		try {
			return possibles.get(Rixor.random(0,possibles.size()-1));
		} catch(IndexOutOfBoundsException ioobe) {
			// What a lovely Exception label... hahah
			ioobe.printStackTrace();
		}
		
		return null;
	}
	
	public ItemKit getKit() {
		for(ItemKit kit : getMap().getKits())
			if(kit.getName().equalsIgnoreCase(kitName))
				return kit;
		
		return null;
	}
	
	public MapTeamSpawn clone() {
		return new MapTeamSpawn(getMap(), getPossibles(), getKitName());
	}

	public Map getMap(){
		return this.map;
	}

	public List<Location> getPossibles(){
		return this.possibles;
	}

	public String getKitName(){
		return this.kitName;
	}
}
