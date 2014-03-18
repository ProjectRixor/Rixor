package com.projectrixor.rixor.scrimmage.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.map.extras.SidebarType;
import com.projectrixor.rixor.scrimmage.utils.ConversionUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import lombok.Getter;
import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.extras.Contributor;
import com.projectrixor.rixor.scrimmage.map.region.Region;
import com.projectrixor.rixor.scrimmage.rotation.RotationSlot;

public class MapLoader {
	
	@Getter File folder;
	@Getter Document doc;
	
	@Getter Map map;

	@Getter String name;
	@Getter String version;
	@Getter String objective;
	@Getter List<String> rules;
	@Getter List<Contributor> authors;
	@Getter List<Contributor> contributors;
	@Getter List<MapTeam> teams;
	@Getter MapTeam observers;
	@Getter String bb;
	
	@Getter int maxbuildheight;
	@Getter
	SidebarType sidebar;
	
	@Getter int score = -1;
	@Getter int time = -1;
	
	@SuppressWarnings("unchecked")
	private MapLoader(File file, Document doc) {
		long start = System.currentTimeMillis();
		
		/*
		 * Load the map and it's attributes now ready for loading into the rotation
		 * 
		 * I'm so retarded...
		 */
		
		this.doc = doc;
		this.folder = file.getParentFile();
		Element root = doc.getRootElement();
		
		this.name = root.elementText("name");
		this.version = root.elementText("version");
		this.objective = root.elementText("objective");
		this.rules = getList("rules", "rule");
		this.authors = new ArrayList<Contributor>();
		Element authorsElement = root.element("authors");
		List<Element> authorsList = authorsElement.elements("author");
		for(Element element : authorsList) {
			String username = element.getText();
			String contribution = element.attributeValue("contribution");
			authors.add(new Contributor(username, contribution));
			
		}
		
		this.contributors = new ArrayList<Contributor>();
		Element contributorsElement = root.element("contributors");
		if(contributorsElement != null) {
			List<Element> contributorsList = contributorsElement.elements("contributor");
			for(Element element : contributorsList) {
				String username = element.getText();
				String contribution = element.attributeValue("contribution");
				contributors.add(new Contributor(username, contribution));
			}
		}
		
		this.maxbuildheight = Region.MAX_BUILD_HEIGHT;
		if(root.element("maxbuildheight") != null && root.element("maxbuildheight").getText() != null) {
			try {
				this.maxbuildheight = Integer.parseInt(root.element("maxbuildheight").getText());
			} catch(NumberFormatException e) {
				Scrimmage.getInstance().getLogger().info("Failed to load max build height for '" + name + "'...");
			}
		}
		
		this.sidebar = SidebarType.OBJECTIVES;
		/*
			Check if a match is TDM
			
			<score>
				<!-- Time till the match ends in seconds -->    
				<time>600</time>
				
				<!-- Game ends when a team reaches this score, defaults to -1 i.e. no limit. -->
				<limit>-1</limit>
			</score>
		 */
		
		Element score = root.element("score");
		if(score != null) {
			Element limit = score.element("limit");
			Element time = score.element("time");
			
			if(limit != null || time != null) {
				if(limit != null) this.score = ConversionUtil.convertStringToInteger(limit.getText(),-1);
				if(time != null) this.time = ConversionUtil.convertStringToInteger(time.getText(), -1);
				this.sidebar = SidebarType.SCORE;
			}
		}
		
		// this.filters = filters;
		
		long finish = System.currentTimeMillis();
		Scrimmage.getInstance().getLogger().info("Loaded '" + name + "' taking " + (finish - start) + "ms!");
		Map.mapcommand.add(this.name);
		ConversionUtil.commaList(Map.mapcommand);
	}
	
	public Map getMap(RotationSlot slot) {
		return new Map(this, slot, name, version, objective, rules, authors, contributors, teams, observers, maxbuildheight, sidebar, time, score);
	}
	
	public static boolean isLoadable(File file) {
		SAXReader reader = new SAXReader();
		try {
			reader.read(file);
			return true;
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static MapLoader getLoader(File file) {
		if(!isLoadable(file)) {
			Scrimmage.getInstance().getLogger().info("File !isLoadable() when trying to getLoader() - return null;");
			return null;
		}
		
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			Scrimmage.getInstance().getLogger().info("File fired DocumentException when trying to getLoader() - return null;");
			return null;
		}
		
		if(doc == null)
			Scrimmage.getInstance().getLogger().info("Document is null?");
		return new MapLoader(file, doc);
	}
	
	private List<String> getList(String container, String contains) {
		if(doc == null)
			Scrimmage.getInstance().getLogger().info("Document is null?");
		
		Element root = doc.getRootElement();
		
		List<String> contents = new ArrayList<String>();
		Element containerElement = root.element(container);
		
		int cur = 0;
		if(containerElement != null) {
			while(containerElement.elements().size() < cur) {
				if(((Element) containerElement.elements().get(cur)).getName().equalsIgnoreCase(contains))
					contents.add(((Element) containerElement.elements().get(cur)).getText());
				cur++;
			}
		}
		
		return contents;
	}
	
	public static List<Element> getElements(Element from, List<String> names) {
		List<Element> elements = new ArrayList<Element>();
		
		for(String name : names)
			for(Element element : getElements(from))
				if(element.getName().equalsIgnoreCase(name))
					elements.add(element);
		
		return elements;
	}
	
	public static List<Element> getElements(Element from, String name) {
		List<Element> elements = new ArrayList<Element>();
		
		for(Element element : getElements(from))
			if(element.getName().equalsIgnoreCase(name))
				elements.add(element);
		
		return elements;
	}
	
	public static List<Element> getElements(Element from) {
		List<Element> elements = new ArrayList<Element>();
		
		if(from == null || from.elements() == null)
			return elements;
		
		for(Object obj : from.elements())
			if(obj instanceof Element)
				elements.add((Element) obj);
		
		return elements;
	}
	
}
