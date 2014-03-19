package com.projectrixor.rixor.scrimmage.rotation;

import com.projectrixor.rixor.scrimmage.Rixor;
import com.projectrixor.rixor.scrimmage.map.MapLoader;

import java.util.ArrayList;
import java.util.List;

public class Rotation {
	
	static List<MapLoader> loaded = new ArrayList<MapLoader>();
	List<RotationSlot> rotation = new ArrayList<RotationSlot>();
	
	RotationSlot slot;
	
	public Rotation() {
		List<MapLoader> maps = new ArrayList<MapLoader>();
		List<RotationSlot> slots = new ArrayList<RotationSlot>();

		List<String> rotation = Rixor.getInstance().getConfig().getStringList("rotation");
		if(rotation == null)
			maps.addAll(loaded);
		else {
			for(String map : rotation)
				maps.add(getMap(loaded, map));
		}
		
		for(MapLoader loader : maps)
			slots.add(new RotationSlot(loader));
		
		this.rotation = slots;
		
		Rixor.getInstance().getLogger().info("Rotation: " + getRotationString());
		//Rixor.getInstance().getConfig().set("rotation", getRotationString());
		//Rixor.getInstance().saveConfig();
	}

	public static List<MapLoader> getLoaded(){
		return Rotation.loaded;
	}

	public void start() {
		RotationSlot slot = rotation.get(0);
		this.slot = slot;
		
		slot.load();
		
		Rixor.setOpen(true);
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

		
		List<RotationSlot> rotation = getRotation();
		slot.load().getMap().getName();
		//rotation.remove(current);
		rotation.add(current - rotation.size() + 1, slot);

		for (RotationSlot s : rotation){
			s.load();
			Rixor.getInstance().getLogger().info(s.getMap().getName());
		}

		Rixor.getInstance().getLogger().info(getNext().getMap().getName());


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
			return getSlot(current);
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

	public List<RotationSlot> getRotation(){
		return this.rotation;
	}

	public RotationSlot getSlot(){
		return this.slot;
	}

	public void setSlot(RotationSlot slot){
		this.slot=slot;
	}
}
