package com.projectrixor.rixor.scrimmage.map;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.ServerLog;
import com.projectrixor.rixor.scrimmage.map.extras.Contributor;
import com.projectrixor.rixor.scrimmage.map.extras.SidebarType;
import com.projectrixor.rixor.scrimmage.map.extras.TNTSettings;
import com.projectrixor.rixor.scrimmage.map.filter.Filter;
import com.projectrixor.rixor.scrimmage.map.kit.ItemKit;
import com.projectrixor.rixor.scrimmage.map.kit.KitLoader;
import com.projectrixor.rixor.scrimmage.map.objective.CoreObjective;
import com.projectrixor.rixor.scrimmage.map.objective.MonumentObjective;
import com.projectrixor.rixor.scrimmage.map.objective.WoolObjective;
import com.projectrixor.rixor.scrimmage.map.region.ConfiguredRegion;
import com.projectrixor.rixor.scrimmage.map.region.Region;
import com.projectrixor.rixor.scrimmage.map.region.RegionGroup;
import com.projectrixor.rixor.scrimmage.map.region.RegionGroupType;
import com.projectrixor.rixor.scrimmage.map.region.RegionType;
import com.projectrixor.rixor.scrimmage.rotation.RotationSlot;
import com.projectrixor.rixor.scrimmage.utils.ConversionUtil;
import com.projectrixor.rixor.scrimmage.utils.FileUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.dom4j.Attribute;
import org.dom4j.Element;

import lombok.Getter;

public class Map {
	
	/*
	 * When the Map object is loaded all locations in any lists must have the world updated
	 * this means that there needs to be 1 map object per play
	 * An "update" method will be added where the RotationSlot must be specified, so that the world can be set to the correct name, etc, etc.
	 * 
	 * Or I could just supply the RotationSlot in the map object? I guess that could work ^.^
	 */
	
	@Getter MapLoader loader;
	@Getter
	RotationSlot slot;
	@Getter World world;
	@Getter File folder;
	
	@Getter String name;
	@Getter String version;
	@Getter String objective;
	@Getter List<String> rules;
	@Getter List<Contributor> authors;
	@Getter List<Contributor> contributors;
	@Getter List<ItemKit> kits;
	@Getter List<ItemStack> itemRemove = new ArrayList<ItemStack>();
	@Getter ItemStack killReward;
	@Getter TNTSettings tntsettings = new TNTSettings();
	@Getter List<MapTeam> teams = new ArrayList<MapTeam>();
	@Getter MapTeam observers;
	
	@Getter int maxbuildheight;
	@Getter List<RegionGroup> regions;
	@Getter List<Filter> filters;
	
	@Getter
	SidebarType sidebar = SidebarType.OBJECTIVES;
	@Getter Scoreboard board;
	@Getter Objective boardObjective;
	
	@Getter int timeLimit;
	@Getter int scoreLimit;
	
	public Map(MapLoader loader, RotationSlot slot, String name, String version, String objective, List<String> rules,
			List<Contributor> authors, List<Contributor> contributors, List<MapTeam> teams, MapTeam observers, int maxbuildheight, SidebarType sidebar, int timeLimit, int scoreLimit) {
		this.loader = loader;
		this.slot = slot;
		this.name = name;
		this.version = version;
		this.objective = objective;
		this.rules = rules;
		this.authors = authors;
		this.contributors = contributors;
		if(teams != null) this.teams = teams;
		if(observers != null) this.observers = observers;
		this.maxbuildheight = maxbuildheight;
		this.sidebar = sidebar;
		this.timeLimit = timeLimit;
		this.scoreLimit = scoreLimit;
		
		this.board = Scrimmage.getInstance().getServer().getScoreboardManager().getNewScoreboard();
		reloadSidebar(true, null);
	}
	
	public List<MapTeam> getAllTeams() {
		List<MapTeam> teams = new ArrayList<MapTeam>();
		teams.addAll(getTeams());
		teams.add(getObservers());
		
		return teams;
	}
	
	public MapTeam getLowest() {
		List<MapTeam> teams = new ArrayList<MapTeam>();
		teams.add(getTeams().get(0));
		int lowest = teams.get(0).getPlayers().size();
		
		for(MapTeam team : getTeams())
			if(!teams.contains(team))
				if(lowest > team.getPlayers().size()) {
					teams = new ArrayList<MapTeam>();
					teams.add(team);
					lowest = team.getPlayers().size();
				} else if(lowest == team.getPlayers().size())
					teams.add(team);
		
		return teams.get(Scrimmage.random(0, teams.size() - 1));
	}
	
	public MapTeam getTeam(String title) {
		for(MapTeam team : getAllTeams())
			if(team.getName().toLowerCase().contains(title.toLowerCase()) || team.getColorName().toLowerCase().contains(title.toLowerCase()))
				return team;
		
		return null;
	}
	
	public void reloadSidebar(boolean objectives, SidebarType sidebar) {
		if(getSidebar() == SidebarType.OBJECTIVES && (sidebar == SidebarType.OBJECTIVES || sidebar == null)) {
			if(boardObjective != null)
				this.boardObjective.unregister();
			
			this.boardObjective = board.registerNewObjective("Objectives", "dummy");
			this.boardObjective.setDisplayName(ChatColor.GOLD + "Objectives");
			this.boardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			if(objectives) {
				int i = 1;
				for(MapTeam team : getTeams()) {
					if(team.getObjectives() == null || team.getObjectives().size() == 0)
						i = team.loadTeamObjectives(true, i);
					else i = team.loadTeamObjectives(false, i);
					//if(teams.get(teams.size() - 1) != team) {
						i++;
						OfflinePlayer player = Scrimmage.getInstance().getServer().getOfflinePlayer(getSpaces(i));
						getBoardObjective().getScore(player).setScore(i);
						i++;
						
				//	}
				}
			}
		} else if(getSidebar() == SidebarType.SCORE && (sidebar == SidebarType.SCORE || sidebar == null)) {
			if(objectives) {
				if(boardObjective != null)
					this.boardObjective.unregister();
				
				
				this.boardObjective = board.registerNewObjective("Score", "dummy");
				this.boardObjective.setDisplayName(ChatColor.GOLD + "Score");
				this.boardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
				
				for(MapTeam team : getTeams()) {
					OfflinePlayer player = Scrimmage.getInstance().getServer().getOfflinePlayer(team.getColor() + team.getDisplayName());
					this.boardObjective.getScore(player).setScore(1);
				}
				
			}
			
			for(MapTeam team : getTeams()) {
				OfflinePlayer player = Scrimmage.getInstance().getServer().getOfflinePlayer(team.getColor() + team.getDisplayName());
				this.boardObjective.getScore(player).setScore(team.getScore());
				
			}
			
		}
		
	}
	
	public static String getSpaces(int used) {
		String s = "";
		
		int i = 0;
		while(i <= used) {
			s += " ";
			i++;
		}
		
		return s;
	}
	
	public ItemKit getKit(String name) {
		for(ItemKit kit : getKits())
			if(kit.getName().equalsIgnoreCase(name))
				return kit;
		
		return null;
	}
	
	public void unload() {
		String name = getWorld().getName();
		Scrimmage.getInstance().getServer().unloadWorld(getWorld(), false);
		
		FileUtil.delete(new File(name));
	}
	
	public void update() {
		String name = "" + Scrimmage.random(1, 10000);
		/*
		 * What on earth could be null there?
		 * Scrimmage = not null (100% sure)
		 * Rotation = could be null, pretty sure it ain't tho.
		 * Nothing else could be null...
		 */
		update(Scrimmage.getInstance().getServer().getWorld(name) == null);
	}
	
	@SuppressWarnings("unchecked")
	public void update(boolean load) {
		long start = System.currentTimeMillis();
		Scrimmage.getInstance().getLogger().info("Started loading '" + name + "'!");
		if(Scrimmage.getRotation() == null)
			Scrimmage.getInstance().getLogger().info("Rotation is null... WTF?!");
		
		String name = "" + Scrimmage.random(1, 10000);
		World world = Scrimmage.getInstance().getServer().getWorld(name);
		
		if(load) {
			File src = loader.getFolder();
			File dest = new File(name);

			dest.mkdirs();

			try {
				FileUtil.copyFolder(src, dest);
			} catch (IOException e) {
				Scrimmage.getInstance().getLogger().severe("ERROR COPYING WORLDS!!!!!!!!!!!!!!!! \n" + e.getMessage());
			}
			folder = dest;
			WorldCreator wc = new WorldCreator(name);
			wc.generator(new ChunkGenerator() {
				
				public byte[] generate(World world, Random random, int x, int z) {
					return new byte[65536];
				}
				
			});
			world = wc.createWorld();
		}
		
		Scrimmage.getInstance().getLogger().info("Loaded the World for '" + this.name + "' taking "
				+ (System.currentTimeMillis() - start) + "ms!");
		
		long step = System.currentTimeMillis();
		Scrimmage.getInstance().getLogger().info("Total load time for '" + this.name + "' is currently "
				+ (System.currentTimeMillis() - start) + "ms!");
		
		this.world = world;

		if(world != null) {
			Element root = loader.getDoc().getRootElement();
			
			teams = new ArrayList<MapTeam>();
			Element teamsElement = root.element("teams");
			List<Element> teamsList = teamsElement.elements("team");
			for(Element element : teamsList) {
				String teamName = element.getText();
				String teamCap = element.attributeValue("max");
				String teamColor = element.attributeValue("color");
				MapTeam team = new MapTeam(this, teamName, teamColor, teamCap);
				if(team.getColor() == null || team.getColor() == ChatColor.AQUA)
					Scrimmage.getInstance().getLogger().info("Failed to load team '"
							+ teamName + "' due to having an invalid color supplied!");
				else teams.add(team);
			}
			
			List<String> values = new ArrayList<String>();
			for(MapTeam team : teams) values.add(team.getName());
			ServerLog.info("Loaded "+teams.size()+" teams ("+ConversionUtil.commaList(values)+")");
			
			Scrimmage.getInstance().getLogger().info("Loaded the Teams for '" + this.name + "' taking "
					+ (System.currentTimeMillis() - step) + "ms!");
			step = System.currentTimeMillis();
			Scrimmage.getInstance().getLogger().info("Total load time for '" + this.name + "' is currently "
					+ (System.currentTimeMillis() - start) + "ms!");
			
			kits = new ArrayList<ItemKit>();
			for(Element kitsElement : MapLoader.getElements(root, "kits"))
				for(Element kitElement : MapLoader.getElements(kitsElement, "kit"))
					kits.add(new KitLoader(this, kitElement).load());
			for(Element kitsElement : MapLoader.getElements(root, "kits"))
				for(Element kitsElement2 : MapLoader.getElements(kitsElement, "kits"))
					for(Element kitElement : MapLoader.getElements(kitsElement2, "kit"))
						kits.add(new KitLoader(this, kitElement).load());

			Scrimmage.getInstance().getLogger().info("Loaded the Kits for '" + this.name + "' taking "
					+ (System.currentTimeMillis() - step) + "ms!");
			step = System.currentTimeMillis();
			Scrimmage.getInstance().getLogger().info("Total load time for '" + this.name + "' is currently "
					+ (System.currentTimeMillis() - start) + "ms!");


			for (Element e : MapLoader.getElements(root, "itemremove")){
				for (Element item : MapLoader.getElements(e, "item")) {
					ItemStack itemstack = new ItemStack(ConversionUtil.convertStringToMaterial(item.getText()));
					itemRemove.add(itemstack);
					Scrimmage.getInstance().getLogger().info("Loaded ItemRemove - " + item.getText());
				}
			}

			for (Element e : MapLoader.getElements(root, "tnt")){
				for (Element i : MapLoader.getElements(e, "instantignite")) {
					if (i.getText().equalsIgnoreCase("on")){
						tntsettings.setInstantIgnite(true);
						Scrimmage.getInstance().getLogger().info("Instant Ignite is ON");
					}
					else {
						tntsettings.setInstantIgnite(false);
					}
				}
				for (Element i : MapLoader.getElements(e, "blockdamage")){
					if (i.getText().equalsIgnoreCase("off")){
						tntsettings.setBlockDamage(false);
						Scrimmage.getInstance().getLogger().info("Block Damage is OFF");
					}
					else {
						tntsettings.setBlockDamage(true);
					}
				}
			}

			for (Element m : MapLoader.getElements(root, "killreward")){
				for (Element item : MapLoader.getElements(m, "item")){
					Attribute amount = item.attribute("amount");
					String itemstackS = item.getText();
					ItemStack itemStack = new ItemStack(ConversionUtil.convertStringToMaterial(itemstackS));
					itemStack.setAmount(Integer.parseInt(amount.getText()));
					killReward = itemStack;
					Scrimmage.getInstance().getLogger().info("KillReward is - " + itemStack.getType().toString());
				}
			}
			for(MapTeam team : teams)
				team.load(root.element("spawns"), -1);
			
			observers = new MapTeam(this, "Observers", ChatColor.AQUA, -1);
			observers.load(root.element("spawns"));

			Scrimmage.getInstance().getLogger().info("Loaded the Spawns for '" + this.name + "' taking "
					+ (System.currentTimeMillis() - step) + "ms!");
			step = System.currentTimeMillis();
			Scrimmage.getInstance().getLogger().info("Total load time for '" + this.name + "' is currently "
					+ (System.currentTimeMillis() - start) + "ms!");
			
			for(MapTeam team : teams)
				team.loadTeam();
			
			observers.loadTeam();

			Scrimmage.getInstance().getLogger().info("Loaded the Scoreboard Teams for '" + this.name + "' taking "
					+ (System.currentTimeMillis() - step) + "ms!");
			step = System.currentTimeMillis();
			Scrimmage.getInstance().getLogger().info("Total load time for '" + this.name + "' is currently "
					+ (System.currentTimeMillis() - start) + "ms!");
			
			reloadSidebar(true, null);

			Scrimmage.getInstance().getLogger().info("Loaded the Objectives for '" + this.name + "' taking "
					+ (System.currentTimeMillis() - step) + "ms!");
			step = System.currentTimeMillis();
			Scrimmage.getInstance().getLogger().info("Total load time for '" + this.name + "' is currently "
					+ (System.currentTimeMillis() - start) + "ms!");

			regions = new ArrayList<RegionGroup>();
			
			Element regions = root.element("regions");
			if(regions != null) {
				Region shapes = new Region(this, regions, RegionType.ALL);
				for(ConfiguredRegion conf : shapes.getRegions())
					this.regions.add(new RegionGroup(conf.getName(), conf.getLocations()));
				
				List<String> names = new ArrayList<String>();
				names.add(RegionGroupType.NEGATIVE.name().toLowerCase());
				names.add(RegionGroupType.UNION.name().toLowerCase());
				names.add(RegionGroupType.COMPLEMENT.name().toLowerCase());
				names.add(RegionGroupType.INTERSECT.name().toLowerCase());
				names.add(RegionGroupType.APPLY.name().toLowerCase());
				
				List<Element> elements = MapLoader.getElements(regions, names);
				for(Element element : elements)
					this.regions.add(new RegionGroup(element, this));
			}

			Scrimmage.getInstance().getLogger().info("Loaded the Regions for '" + this.name + "' taking "
					+ (System.currentTimeMillis() - step) + "ms!");
			step = System.currentTimeMillis();
			Scrimmage.getInstance().getLogger().info("Total load time for '" + this.name + "' is currently "
					+ (System.currentTimeMillis() - start) + "ms!");
			
			/*
			List<Element> negatives = getElements(regions, RegionGroupType.NEGATIVE.name().toLowerCase());
			List<Element> unions = getElements(regions, RegionGroupType.UNION.name().toLowerCase());
			List<Element> complements = getElements(regions, RegionGroupType.COMPLEMENT.name().toLowerCase());
			List<Element> intersects = getElements(regions, RegionGroupType.INTERSECT.name().toLowerCase());
			
			List<Element> all = new ArrayList<Element>();
			all.addAll(negatives);
			all.addAll(unions);
			all.addAll(complements);
			all.addAll(intersects);
			*/
			
			/*
			 * Going to skip filters for now, I want to see the plugin working ;-;
			 */
		}
		long finish = System.currentTimeMillis();
		Scrimmage.getInstance().getLogger().info("Loaded '" + this.name + "' taking " + (finish - start) + "ms!");
		mapcommand.add(this.name);
		ConversionUtil.commaList(mapcommand);
	}
	
	public RegionGroup getRegionGroup(String name) {
		for(RegionGroup group : getRegions())
			if(group.getName().equalsIgnoreCase(name))
				return group;
		
		return null;
	}
	
	public List<Filter> getFilters(Location loc) {
		List<Filter> found = new ArrayList<Filter>();

		/*
		for(Filter filter : filters) {
			if(filter)
		}
		*/
		
		return found;
	}
	
	public List<CoreObjective> getCores() {
		List<CoreObjective> cores = new ArrayList<CoreObjective>();
		
		for(MapTeam team : getAllTeams())
			cores.addAll(team.getCores());
		
		return cores;
	}
	
	public List<CoreObjective> getCores(MapTeam team) {
		return team.getCores();
	}
	
	public CoreObjective getCore(Location location) {
		for(MapTeam team : getTeams())
			if(team.getCore(location) != null)
				return team.getCore(location);
		
		return null;
	}
	
	public CoreObjective getCoreLeak(Location location) {
		for(MapTeam team : getTeams())
			if(team.getCoreLeak(location) != null) {
				//Scrimmage.debug(team.getCoreLeak(location).getName(), "d");
				return team.getCoreLeak(location);
			}

		
		return null;
	}
	
	public boolean isCoreLeak(Location location) {
		return getCoreLeak(location) != null;
	}
	
	public List<WoolObjective> getWools() {
		List<WoolObjective> wools = new ArrayList<WoolObjective>();
		
		for(MapTeam team : getAllTeams())
			wools.addAll(team.getWools());
		
		return wools;
	}
	
	public List<WoolObjective> getWools(MapTeam team) {
		return team.getWools();
	}
	
	public WoolObjective getWool(Location location) {
		for(MapTeam team : getTeams())
			if(team.getWool(location) != null)
				return team.getWool(location);
		
		return null;
	}
	
	public List<MonumentObjective> getMonuments() {
		List<MonumentObjective> monuments = new ArrayList<MonumentObjective>();
		
		for(MapTeam team : getAllTeams())
			monuments.addAll(team.getMonuments());
		
		return monuments;
	}
	
	public static ArrayList<String> mapcommand = new ArrayList<String>();
	
	public List<MonumentObjective> getMonuments(MapTeam team) {
		return team.getMonuments();
	}
	
	public MonumentObjective getMonument(Location location) {
		for(MapTeam team : getTeams())
			if(team.getMonument(location) != null)
				return team.getMonument(location);
		
		return null;
	}
	
	public List<MapTeam> getWinners() {
		List<MapTeam> teams = new ArrayList<MapTeam>();
		
		if(sidebar == SidebarType.SCORE) {
			teams.add(getTeams().get(0));
			int highest = teams.get(0).getScore();
			
			for(MapTeam team : getTeams())
				if(!teams.contains(team))
					if(highest < team.getScore()) {
						teams = new ArrayList<MapTeam>();
						teams.add(team);
						highest = team.getScore();
					} else if(highest == team.getScore())
						teams.add(team);
			
			return teams;
		}
		
		teams.add(getTeams().get(0));
		int highest = teams.get(0).getCompleted();
		
		for(MapTeam team : getTeams())
			if(!teams.contains(team))
				if(highest < team.getCompleted()) {
					teams = new ArrayList<MapTeam>();
					teams.add(team);
					highest = team.getCompleted();
				} else if(highest == team.getCompleted())
					teams.add(team);
		
		if(teams.size() != 1) {
			teams = new ArrayList<MapTeam>();
			teams.add(getTeams().get(0));
			highest = teams.get(0).getTouches();
			
			for(MapTeam team : getTeams())
				if(!teams.contains(team))
					if(highest < team.getTouches()) {
						teams = new ArrayList<MapTeam>();
						teams.add(team);
						highest = team.getTouches();
					} else if(highest == team.getTouches())
						teams.add(team);
		}
		
		return teams;
	}
	
}
