package com.projectrixor.rixor.scrimmage.map.region;

import java.util.List;

import org.bukkit.Location;
import org.dom4j.Element;

import lombok.Getter;

public class ConfiguredRegion {
	
	@Getter Element element;
	
	@Getter String name;
	@Getter List<Location> locations;
	
	public ConfiguredRegion(List<Location> locations, Element element) {
		this(null, locations, element);
	}
	
	public ConfiguredRegion(String name, List<Location> locations, Element element) {
		this.element = element;
		this.name = name;
		this.locations = locations;
	}
	
}
