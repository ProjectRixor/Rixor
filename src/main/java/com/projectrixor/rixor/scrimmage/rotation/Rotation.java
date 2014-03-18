package com.projectrixor.rixor.scrimmage.rotation;

import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.MapLoader;
import lombok.Getter;
import lombok.Setter;

public class Rotation {
	
	static @Getter List<MapLoader> loaded = new ArrayList<MapLoader>();
	@Getter List<RotationSlot> rotation = new ArrayList<RotationSlot>();
	
	@Getter @Setter RotationSlot slot;
	
	public Rotation() {
		List<MapLoader> maps = new ArrayList<MapLoader>();
		List<RotationSlot> slots = new ArrayList<RotationSlot>();

		List<String> rotation = Scrimmage.getInstance().getConfig().getStringList("rotation");
		if(rotation == null)
			maps.addAll(loaded);
		else {
			for(String map : rotation)
				maps.add(getMap(loaded, map));
		}
		
		for(MapLoader loader : maps)
			slots.add(new RotationSlot(loader));
		
		this.rotation = slots;
		
		Scrimmage.getInstance().getLogger().info("Rotation: " + getRotationString());
		//Scrimmage.getInstance().getConfig().set("rotation", getRotationString());
		//Scrimmage.getInstance().saveConfig();
	}
	
	public void start() {
		RotationSlot slot = rotation.get(0);
		this.slot = slot;
		
		slot.load();
		
		Scrimmage.setOpen(true);
	}
	
	public String getRotationString() {
		String rotationString = "";
		for(RotationSlot slot : rotation) {
			rotationString += slot.getLoader().getName();
			if(rotation.get(rotation.size() - 1) != slot)
				rotationString += ",";
		}
		return rotationString;
	}
	
	public void setNext(RotationSlot slot) {
		int current = getLocation(slot);
		
		List<RotationSlot> pre = rotation.subList(0, current);
		List<RotationSlot> aft = new ArrayList<RotationSlot>();
		try {
			aft = rotation.subList(current + 1, rotation.size() - 1);
		} catch(Exception e) {

		}
		
		List<RotationSlot> rotation = new ArrayList<RotationSlot>();
		rotation.addAll(pre);
		rotation.add(slot);
		rotation.addAll(aft);
		
		this.rotation = rotation;
	}
	
	public RotationSlot getSlot(int slot) throws IndexOutOfBoundsException {
		return getRotation().get(slot);
	}
	
	public int getLocation(RotationSlot slot) {
		int s = 0;
		for(RotationSlot search : getRotation()) {
			if(search == slot)
				return s;
			s++;
		}
		
		return s;
	}
	
	public RotationSlot getNext() {
		int current = getLocation(getSlot());
		
		try {
			return getSlot(current + 1);
		} catch(IndexOutOfBoundsException ioobe) {
			return null;
		}
	}
	
	public static boolean addMap(MapLoader loader) {
		return loaded.add(loader);
	}
	
	public static MapLoader getMap(String name) {
		return getMap(loaded, name);
	}
	
	public static MapLoader getMap(List<MapLoader> loaded, String name) {
		for(MapLoader loader : loaded)
			if(loader.getName().equalsIgnoreCase(name))
				return loader;
		
		for(MapLoader loader : loaded)
			if(loader.getName().toLowerCase().startsWith(name.toLowerCase()))
				return loader;
		
		return null;
	}
	
}
