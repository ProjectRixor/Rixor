package com.projectrixor.rixor.scrimmage.map.region;

import org.bukkit.Location;
import org.dom4j.Element;

import java.util.List;

public class ConfiguredRegion {
	
	Element element;
	
	String name;
	List<Location> locations;
	
	public ConfiguredRegion(List<Location> locations, Element element) {
		this(null, locations, element);
	}
	
	public ConfiguredRegion(String name, List<Location> locations, Element element) {
		this.element = element;
		this.name = name;
		this.locations = locations;
	}

	public Element getElement(){
		return this.element;
	}

	public String getName(){
		return this.name;
	}

	public List<Location> getLocations(){
		return this.locations;
	}
}
