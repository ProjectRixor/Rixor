package com.projectrixor.rixor.scrimmage.map.region;

import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.MapLoader;
import com.projectrixor.rixor.scrimmage.utils.RegionUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import com.projectrixor.rixor.scrimmage.map.Map;

import org.bukkit.Location;
import org.dom4j.Element;

public class Region {
	
	@Getter @Setter public static int MAX_BUILD_HEIGHT = 0;
	
	@Getter Map map;
	@Getter List<ConfiguredRegion> regions;
	@Getter List<Element> elements;
	
	public Region(Map map, Element element) {
		List<Element> elements = new ArrayList<Element>();
		elements.add(element);
		
		RegionType type = RegionType.getByElementName(element.getName());
		if(type == null) {
			this.elements = new ArrayList<Element>();
			this.regions = new ArrayList<ConfiguredRegion>();
			return;
		}
		
		Region region = new Region(map, elements, type);
		this.elements = region.getElements();
		this.regions = region.getRegions();
	}
	
	public Region(Map map, List<Element> elements, @NonNull RegionType type) {
		this.map = map;
		regions = new ArrayList<ConfiguredRegion>();
		this.elements = new ArrayList<Element>();
		
		for(Element element : elements) {
			if((type == RegionType.RECTANGLE || type == RegionType.ALL) && element.getName().equalsIgnoreCase("rectangle")) {
				this.elements.add(element);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getRectangle(element), element));
			}
			
			if((type == RegionType.CUBOID || type == RegionType.ALL) && element.getName().equalsIgnoreCase("cuboid")) {
				this.elements.add(element);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getCuboid(element), element));
			}
			
			if((type == RegionType.CIRCLE || type == RegionType.ALL) && element.getName().equalsIgnoreCase("circle")) {
				this.elements.add(element);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getCircle(element), element));
			}
			
			if((type == RegionType.CYLINDER || type == RegionType.ALL) && element.getName().equalsIgnoreCase("cylinder")) {
				this.elements.add(element);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getCylinder(element), element));
			}
			
			if((type == RegionType.SPHERE || type == RegionType.ALL) && element.getName().equalsIgnoreCase("sphere")) {
				this.elements.add(element);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getSphere(element), element));
			}
			
			if((type == RegionType.BLOCK || type == RegionType.ALL) && element.getName().equalsIgnoreCase("point")) {
				this.elements.add(element);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getPoint(element), element));
			}
		}
	}
	
	/*
	 * The element variable is actually the root element - pretty obvious from the code...
	 */
	public Region(Map map, Element element, @NonNull RegionType type) {
		MAX_BUILD_HEIGHT = Scrimmage.getInstance().getServer().getWorlds().get(0).getMaxHeight();
		regions = new ArrayList<ConfiguredRegion>();
		this.map = map;
		
		List<Element> rectangles = MapLoader.getElements(element,"rectangle");
		List<Element> cuboids = MapLoader.getElements(element, "cuboid");
		List<Element> circles = MapLoader.getElements(element, "circle");
		List<Element> cylinders = MapLoader.getElements(element, "cylinder");
		List<Element> spheres = MapLoader.getElements(element, "sphere");
		List<Element> points = MapLoader.getElements(element, "point");
		
		elements = new ArrayList<Element>();
		
		if(type == RegionType.RECTANGLE || type == RegionType.ALL)
			for(Element rectangle : rectangles) {
				elements.add(rectangle);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getRectangle(rectangle), rectangle));
			}
		
		if(type == RegionType.CUBOID || type == RegionType.ALL)
			for(Element cuboid : cuboids) {
				elements.add(cuboid);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getCuboid(cuboid), cuboid));
			}
		
		if(type == RegionType.CIRCLE || type == RegionType.ALL)
			for(Element circle : circles) {
				elements.add(circle);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getCircle(circle), circle));
			}
		
		if(type == RegionType.CYLINDER || type == RegionType.ALL)
			for(Element cylinder : cylinders) {
				elements.add(cylinder);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getCylinder(cylinder), cylinder));
			}
		
		if(type == RegionType.SPHERE || type == RegionType.ALL)
			for(Element sphere : spheres) {
				elements.add(sphere);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getSphere(sphere), sphere));
			}
		
		if(type == RegionType.BLOCK || type == RegionType.ALL) {
			points.addAll(MapLoader.getElements(element, "block"));
			for(Element point : points) {
				elements.add(point);
				String name = null;
				if(element.attributeValue("name") != null) name = element.attributeValue("name");
				regions.add(new ConfiguredRegion(name, getPoint(point), point));
			}
		}
	}
	
	public List<Location> getLocations() {
		List<Location> locations = new ArrayList<Location>();
		
		for(ConfiguredRegion conf : getRegions())
			locations.addAll(conf.getLocations());
		
		return locations;
	}
	
	public static Float parseFloat(String parse) {
		if(parse == null)
			return 0F;
		
		try {
			return Float.parseFloat(parse);
		} catch(NumberFormatException e) {
			return 0F;
		}
	}
	
	public Double parseInfiniteDouble(LocationPoint point, String parse) throws NumberFormatException {
		double start = 0;
		double value = 0;
		if(parse.equalsIgnoreCase("oo") || parse.equalsIgnoreCase("-oo")) {
			int difference = 1000;
			if(point == LocationPoint.X)
				start = map.getObservers().getSpawn().getSpawn().getX();
			else if(point == LocationPoint.Y) {
				start = map.getObservers().getSpawn().getSpawn().getY();
				if(parse.equalsIgnoreCase("oo")) difference = (int) (255 - start);
				else if(parse.equalsIgnoreCase("-oo")) difference = (int) (start);
				
				if(parse.equalsIgnoreCase("oo") && difference > ((int) (255 + start))) difference = 255 - ((int) start);
			}
			else if(point == LocationPoint.Z)
				start = map.getObservers().getSpawn().getSpawn().getZ();
			
			if(parse.equalsIgnoreCase("oo")) value = start + difference;
			else if(parse.equalsIgnoreCase("-oo")) value = start - difference;
		} else value = Double.parseDouble(parse);
		return value;
	}
	
	public List<Location> getRectangle(Element rectangle) {
		/*
		 * Setup Rectangle Regions
		 * Example: <rectangle name="something" min="X1,Z1" max="X2,Z2"/>
		 * Notes: Not sure if there is no Y value because it's meant to be infinite Y (bottom -> top) or just that 1 layer...
		 * It's just 1 layer. Nope, it has to be infinite otherwise it would be at void level.
		 * For now I'm only going to do the min & max attrs because this is a spawn-region rather than a region-region.
		 * To anybody who has read this paragraph of mine... gj. lol
		 */
		
		List<Location> locations = new ArrayList<Location>();
		boolean failed = false;
		String min = rectangle.attributeValue("min");
		String max = rectangle.attributeValue("max");
		
		String yaw = rectangle.getParent().attributeValue("yaw");
		String pitch = rectangle.getParent().attributeValue("pitch");
		
		double minX = 0;
		double minY = 0;
		double minZ = 0;
		
		double maxX = 0;
		double maxY = MAX_BUILD_HEIGHT;
		double maxZ = 0;
		
		String[] minSplit = min.split(",");
		String minXS = minSplit[0];
		String minZS = minSplit[1];
		
		String[] maxSplit = max.split(",");
		String maxXS = maxSplit[0];
		String maxZS = maxSplit[1];
		
		try {
			minX = parseInfiniteDouble(LocationPoint.X, minXS);
			minZ = parseInfiniteDouble(LocationPoint.Z, minZS);
			
			maxX = parseInfiniteDouble(LocationPoint.X, maxXS);
			maxZ = parseInfiniteDouble(LocationPoint.Z, maxZS);
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			failed = true;
		}
		
		if(!failed) {
			Location minL = new Location(map.getWorld(), minX, minY, minZ, parseFloat(yaw), parseFloat(pitch));
			Location maxL = new Location(map.getWorld(), maxX, maxY, maxZ, parseFloat(yaw), parseFloat(pitch));
			List<Location> possibles = RegionUtil.contains(minL,maxL);
			locations.addAll(possibles);
		}
		
		return locations;
	}
	
	public List<Location> getCuboid(Element cuboid) {
		/*
		 * Setup Cuboid Regions
		 * Example: <cuboid name="something" min="X1,Y1,Z1" max="X2,Y2,Z2"/>
		 * Notes: N/A
		 */

		List<Location> locations = new ArrayList<Location>();
		boolean failed = false;
		String min = cuboid.attributeValue("min");
		String max = cuboid.attributeValue("max");
		
		String yaw = cuboid.getParent().attributeValue("yaw");
		String pitch = cuboid.getParent().attributeValue("pitch");
		
		double minX = 0;
		double minY = 0;
		double minZ = 0;
		
		double maxX = 0;
		double maxY = 0;
		double maxZ = 0;
		
		String[] minSplit = min.split(",");
		String minXS = minSplit[0];
		String minYS = minSplit[1];
		String minZS = minSplit[2];
		
		String[] maxSplit = max.split(",");
		String maxXS = maxSplit[0];
		String maxYS = maxSplit[1];
		String maxZS = maxSplit[2];
		
		try {
			minX = parseInfiniteDouble(LocationPoint.X, minXS);
			minY = parseInfiniteDouble(LocationPoint.Y, minYS);
			minZ = parseInfiniteDouble(LocationPoint.Z, minZS);
			
			maxX = parseInfiniteDouble(LocationPoint.X, maxXS);
			maxY = parseInfiniteDouble(LocationPoint.Y, maxYS);
			maxZ = parseInfiniteDouble(LocationPoint.Z, maxZS);
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			failed = true;
		}
		
		if(!failed) {
			Location minL = new Location(map.getWorld(), minX, minY, minZ, parseFloat(yaw), parseFloat(pitch));
			Location maxL = new Location(map.getWorld(), maxX, maxY, maxZ, parseFloat(yaw), parseFloat(pitch));
			List<Location> possibles = RegionUtil.contains(minL, maxL);
			locations.addAll(possibles);
		}
		
		return locations;
	}
	
	public List<Location> getCircle(Element circle) {
		/*
		 * Setup Circle Regions
		 * Example: <circle name="something" center="X1,Z1" radius="R"/>
		 * Notes: From 0 to Max Build Height
		 */
		
		List<Location> locations = new ArrayList<Location>();
		boolean failed = false;
		String center = circle.attributeValue("center");
		String radius = circle.attributeValue("radius");
		
		String yaw = circle.getParent().attributeValue("yaw");
		String pitch = circle.getParent().attributeValue("pitch");
		
		double cR = 0;
		double cX = 0;
		double cZ = 0;
		
		String[] cSplit = center.split(",");
		String cXS = cSplit[0];
		String cZS = cSplit[1];
		
		try {
			cR = parseInfiniteDouble(LocationPoint.X, radius);
			cX = parseInfiniteDouble(LocationPoint.X, cXS);
			cZ = parseInfiniteDouble(LocationPoint.Z, cZS);
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			failed = true;
		}
		
		if(!failed) {
			Location centerL = new Location(map.getWorld(), cX, 0, cZ, parseFloat(yaw), parseFloat(pitch));
			
			List<Location> possibles = RegionUtil.circle(centerL, cR, MAX_BUILD_HEIGHT, false, false);
			locations.addAll(possibles);
		}
		
		return locations;
	}
	
	public List<Location> getCylinder(Element cylinder) {
		/*
		 * Setup Cylinder Regions
		 * Example: <cylinder name="something" base="X1,Y1,Z1" radius="R" height="H"/>
		 * Notes: From 0 to the defined height
		 */
		
		List<Location> locations = new ArrayList<Location>();
		boolean failed = false;
		String center = cylinder.attributeValue("base");
		String radius = cylinder.attributeValue("radius");
		String height = cylinder.attributeValue("height");
		
		String yaw = cylinder.getParent().attributeValue("yaw");
		String pitch = cylinder.getParent().attributeValue("pitch");
		
		double cR = 0;
		double cH = 0;
		double cX = 0;
		double cY = 0;
		double cZ = 0;
		
		String[] cSplit = center.split(",");
		String cXS = cSplit[0];
		String cYS = cSplit[1];
		String cZS = cSplit[2];
		
		try {
			cR = parseInfiniteDouble(LocationPoint.X, radius);
			cH = parseInfiniteDouble(LocationPoint.X, height);
			cX = parseInfiniteDouble(LocationPoint.X, cXS);
			cY = parseInfiniteDouble(LocationPoint.Y, cYS);
			cZ = parseInfiniteDouble(LocationPoint.Z, cZS);
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			failed = true;
		}
		
		if(!failed) {
			Location centerL = new Location(map.getWorld(), cX, cY, cZ, parseFloat(yaw), parseFloat(pitch));
			
			List<Location> possibles = RegionUtil.circle(centerL, cR, cH, false, false);
			locations.addAll(possibles);
		}
		
		return locations;
	}
	
	public List<Location> getSphere(Element sphere) {
		/*
		 * Setup Sphere Regions
		 * Example: <sphere name="something" origin="X1,Y1,Z1" radius="R"/>
		 * Notes: I can't remember how my RegionUtil works. That sucks, I guess... hahah
		 */
		
		List<Location> locations = new ArrayList<Location>();
		boolean failed = false;
		String center = sphere.attributeValue("origin");
		String radius = sphere.attributeValue("radius");
		
		String yaw = sphere.getParent().attributeValue("yaw");
		String pitch = sphere.getParent().attributeValue("pitch");
		
		double cR = 0;
		double cH = 0;
		double cX = 0;
		double cY = 0;
		double cZ = 0;
		
		String[] cSplit = center.split(",");
		String cXS = cSplit[0];
		String cYS = cSplit[1];
		String cZS = cSplit[2];
		
		try {
			cR = parseInfiniteDouble(LocationPoint.X, radius);
			cH = cR;
			cX = parseInfiniteDouble(LocationPoint.X, cXS);
			cY = parseInfiniteDouble(LocationPoint.Y, cYS);
			cZ = parseInfiniteDouble(LocationPoint.Z, cZS);
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			failed = true;
		}
		
		if(!failed) {
			Location centerL = new Location(map.getWorld(), cX, cY, cZ, parseFloat(yaw), parseFloat(pitch));
			
			List<Location> possibles = RegionUtil.circle(centerL, cR, cH, false, false);
			locations.addAll(possibles);
		}
		
		return locations;
	}
	
	public List<Location> getPoint(Element point) {
		/*
		 * Setup Point/Block Regions
		 * Example: <block name="something">X,Y,Z</block> or <point>X,Y,Z</point>
		 * Notes: N/A
		 */
		
		List<Location> locations = new ArrayList<Location>();
		boolean failed = false;
		String center = point.getText();
		
		String yaw = point.getParent().attributeValue("yaw");
		String pitch = point.getParent().attributeValue("pitch");
		
		double cX = 0;
		double cY = 0;
		double cZ = 0;
		
		String[] cSplit = center.split(",");
		String cXS = cSplit[0];
		String cYS = cSplit[1];
		String cZS = cSplit[2];
		
		try {
			cX = parseInfiniteDouble(LocationPoint.X, cXS);
			cY = parseInfiniteDouble(LocationPoint.Y, cYS);
			cZ = parseInfiniteDouble(LocationPoint.Z, cZS);
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			failed = true;
		}
		
		if(!failed) {
			Location location = new Location(map.getWorld(), cX, cY, cZ, parseFloat(yaw), parseFloat(pitch));
			
			List<Location> possibles = new ArrayList<Location>();
			possibles.add(location);
			locations.addAll(possibles);
		}
		
		return locations;
	}
	
}
