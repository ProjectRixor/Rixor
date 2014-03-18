package com.projectrixor.rixor.scrimmage.map.region;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapLoader;

import org.bukkit.Location;
import org.dom4j.Element;

public class RegionGroup {
	
	/*
	 * These Region Groups can contain Named Regions or just the Region (eg: cuboid, sphere, etc)
	 * I need a way to access all the named regions before I can run this method, so I need a region loader.
	 * 
	 * I'll start by setting up Union groups, then I'll move on to the next type of region.
	 */
	
	@Getter String name;
	@Getter Element group;
	@Getter RegionGroupType type;
	@Getter List<Location> locations;
	
	private RegionGroup(String name, Element group, RegionGroupType type, List<Location> locations) {
		this.name = name;
		this.group = group;
		this.type = type;
		this.locations = locations;
	}
	
	public RegionGroup(String name, List<Location> locations) {
		this.name = name;
		this.locations = locations;
		this.type = RegionGroupType.UNION;
	}
	
	public RegionGroup(Element group, Map map) {
		type = RegionGroupType.getByElementName(group.getName());
		this.group = group;
		if(type == null)
			name = null;

		locations = new ArrayList<Location>();
		List<Element> elements = MapLoader.getElements(group);
		
		Element baseElement = elements.get(0);
		Region baseRegion = new Region(map, baseElement);
		
		if(type == RegionGroupType.NEGATIVE) {
			/*
			 * Negative Regions take the first element and ignore the rest!
			 */
			
			locations.addAll(baseRegion.getLocations());
		} else {
			if(elements.size() > 1) {
				List<Element> others = elements.subList(1, elements.size() - 1);
				if(type == RegionGroupType.UNION) {
					locations.addAll(baseRegion.getLocations());
					
					for(Element element : others) {
						List<Location> locations = new ArrayList<Location>();
						if(element.getName().equalsIgnoreCase("region")) {
							RegionGroup mapGroup = map.getRegionGroup(element.attributeValue("name"));
							if(mapGroup != null)
								locations = mapGroup.getLocations();
						}
						
						for(Location location : locations)
							if(!contains(location, false))
								locations.add(location);
					}
				} else if(type == RegionGroupType.COMPLEMENT) {
					locations.addAll(baseRegion.getLocations());
					
					for(Element element : others) {
						List<Location> locations = new ArrayList<Location>();
						if(element.getName().equalsIgnoreCase("region")) {
							RegionGroup mapGroup = map.getRegionGroup(element.attributeValue("name"));
							if(mapGroup != null)
								locations = mapGroup.getLocations();
						}
						
						for(Location location : locations) {
							int index = containsAt(location, false);
							if(index != -1)
								locations.remove(index);
						}
					}
				} else if(type == RegionGroupType.COMPLEMENT) {
					locations.addAll(baseRegion.getLocations());
					List<Location> overlap = new ArrayList<Location>();
					
					for(Element element : others) {
						List<Location> locations = new ArrayList<Location>();
						if(element.getName().equalsIgnoreCase("region")) {
							RegionGroup mapGroup = map.getRegionGroup(element.attributeValue("name"));
							if(mapGroup != null)
								locations = mapGroup.getLocations();
						}
						
						for(Location location : locations) {
							if(contains(location, false))
								overlap.add(location);
						}
					}
					
					locations = overlap;
				}
			}
		}
		
		name = group.attributeValue("name");
	}
	
	public int containsAt(Location location, boolean exact) {
		for(Location test : locations) {
			boolean x = test.getBlockX() == location.getBlockX();
			boolean y = test.getBlockY() == location.getBlockY();
			boolean z = test.getBlockZ() == location.getBlockZ();
			boolean yaw = true;
			boolean pitch = true;
			
			if(exact) {
				x = test.getX() == location.getX();
				y = test.getY() == location.getY();
				z = test.getZ() == location.getZ();
				yaw = test.getYaw() == location.getYaw();
				pitch = test.getPitch() == location.getPitch();
			}
			
			if(x && y && z && yaw && pitch)
				return locations.indexOf(test);
		}
		
		return -1;
	}
	
	public boolean contains(Location location, boolean exact) {
		for(Location test : locations) {
			boolean x = test.getBlockX() == location.getBlockX();
			boolean y = test.getBlockY() == location.getBlockY();
			boolean z = test.getBlockZ() == location.getBlockZ();
			boolean yaw = true;
			boolean pitch = true;
			
			if(exact) {
				x = test.getX() == location.getX();
				y = test.getY() == location.getY();
				z = test.getZ() == location.getZ();
				yaw = test.getYaw() == location.getYaw();
				pitch = test.getPitch() == location.getPitch();
			}
			
			if(x && y && z && yaw && pitch)
				return true;
		}
		
		return false;
	}
	
	public boolean isInverted() {
		return type == RegionGroupType.NEGATIVE;
	}
	
	public RegionGroup clone() {
		return new RegionGroup(getName(), getGroup(), getType(), getLocations());
	}
	
}
