package com.projectrixor.rixor.scrimmage.map;

import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.ServerLog;
import com.projectrixor.rixor.scrimmage.map.objective.CoreObjective;
import com.projectrixor.rixor.scrimmage.map.objective.CoreStage;
import com.projectrixor.rixor.scrimmage.map.objective.MonumentBlock;
import com.projectrixor.rixor.scrimmage.map.objective.MonumentObjective;
import com.projectrixor.rixor.scrimmage.map.objective.WoolObjective;
import com.projectrixor.rixor.scrimmage.map.region.ConfiguredRegion;
import com.projectrixor.rixor.scrimmage.map.region.Region;
import com.projectrixor.rixor.scrimmage.map.region.RegionType;
import com.projectrixor.rixor.scrimmage.player.Client;
import com.projectrixor.rixor.scrimmage.utils.ConversionUtil;
import lombok.Getter;
import lombok.Setter;
import com.projectrixor.rixor.scrimmage.map.objective.TeamObjective;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.scoreboard.Team;
import org.dom4j.Element;

public class MapTeam {
	
	public static int DEFAULT_TEAM_CAP = 8;
	
	public static ChatColor getChatColorFromString(String string) {
		if(string.equalsIgnoreCase("purple")) return ChatColor.DARK_PURPLE;
		if(string.equalsIgnoreCase("cyan")) return ChatColor.DARK_AQUA;
		
		for(ChatColor color : ChatColor.values())
			if(color.name().equalsIgnoreCase(string.replaceAll(" ", "_")))
				return color;
		
		return null;
	}
	
	@Getter Map map;
	@Getter Team team;
	
	@Getter String name;
	@Getter ChatColor color;
	@Getter int cap;
	@Getter @Setter int score = 0;
	
	@Getter String displayName;
	@Getter List<MapTeamSpawn> spawns;
	@Getter List<TeamObjective> objectives;
	
	private MapTeam(Map map, String name, ChatColor color, int cap, List<MapTeamSpawn> spawns) {
		this.map = map;
		this.name = name;
		setDisplayName();
		this.color = color;
		this.cap = cap;
		
		this.spawns = new ArrayList<MapTeamSpawn>();
		for(MapTeamSpawn spawn : spawns)
			this.spawns.add(spawn.clone());
	}
	
	public MapTeam(Map map, String name, ChatColor color, int cap) {
		this.map = map;
		this.name = name;
		setDisplayName();
		if(color == ChatColor.AQUA) name = "Observers";
		this.color = color;
		this.cap = cap;
	}
	
	public MapTeam(Map map, String name, String color, String cap) {
		this(map, name, getChatColorFromString(color), ConversionUtil.convertStringToInteger(cap,DEFAULT_TEAM_CAP));
	}
	
	public MapTeam(Map map, String name, ChatColor color, String cap) {
		this(map, name, color, ConversionUtil.convertStringToInteger(cap, DEFAULT_TEAM_CAP));
	}
	
	public MapTeam(Map map, String name, String color, int cap) {
		this(map, name, getChatColorFromString(color), cap);
	}
	
	public void addScore(int amount) {
		setScore(getScore() + amount);
	}
	
	public void setDisplayName() {
		setDisplayName(name, false);
	}
	
	public void setDisplayName(String name, boolean update) {
		this.displayName = name;
		if(update) getMap().reloadSidebar(true, null);
	}
	
	public void loadTeam() {
		this.team = getMap().getBoard().registerNewTeam(getName());
		this.team.setPrefix(getColor() + "");
		this.team.setDisplayName(getColor() + getName());
		this.team.setCanSeeFriendlyInvisibles(true);
	}
	
	public int loadTeamObjectives(int start) {
		return loadTeamObjectives(true, start);
	}
	
	public int loadTeamObjectives(boolean objectives, int start) {
		if(this.objectives == null)
			this.objectives = new ArrayList<TeamObjective>();
		
		if(objectives) {
			this.objectives = new ArrayList<TeamObjective>();
			Element root = getMap().getLoader().getDoc().getRootElement();
			
			// LOAD CTW OBJECTIVES HERE...
			List<Element> wools = new ArrayList<Element>();
			for(Element element : MapLoader.getElements(root, "wools")) {
				if(element.attributeValue("team") != null && isThisTeam(element.attributeValue("team"))) {
					wools.addAll(MapLoader.getElements(element, "wool"));
					ServerLog.info("Found "+MapLoader.getElements(element,"wool").size()+" wools!");
				} else
					for(Element element2 : MapLoader.getElements(element, "wools"))
						if(element.attributeValue("team") != null && isThisTeam(element.attributeValue("team")))
							wools.add(element2);
			}

			for(Element element : MapLoader.getElements(root.element("wools"), "wool")) {
				if(element.attributeValue("team") != null && isThisTeam(element.attributeValue("team"))) {
					wools.add(element);
					ServerLog.info("Found 1 wool!");
				}
			}
			
			for(Element wool : wools) {
				ServerLog.info("Found wool '" + wool.attributeValue("color") + "'!");
				Element block = wool.element("block");
				Location place = null;
				try {
					String text = block.attributeValue("location");
					if(text == null) text = block.getText();
					String[] xyz = text.split(",");
					double x = Double.parseDouble(xyz[0]);
					double y = Double.parseDouble(xyz[1]);
					double z = Double.parseDouble(xyz[2]);
					
					place = new Location(getSpawn().getSpawn().getWorld(), x, y, z);
				} catch(Exception e) {
					e.printStackTrace();
				}
				ChatColor woolColor = WoolObjective.getColor(wool.attributeValue("color"));
				DyeColor dye = WoolObjective.getDye(wool.attributeValue("color"));
				String display = WordUtils.capitalizeFully(wool.attributeValue("color"));
				if(dye != null && place != null)
					this.objectives.add(new WoolObjective(getMap(), this, display, place, dye));
			}
			
			// LOAD DTM OBJECTIVES HERE...
			List<Element> rootDestroyables = MapLoader.getElements(root, "destroyables");
			
			for(Element element : rootDestroyables) {
				List<Element> destroyables = new ArrayList<Element>();

				for(Element destroyableGroup : rootDestroyables) {
					destroyables.addAll(MapLoader.getElements(destroyableGroup, "destroyable"));
					ServerLog.info("Found " + MapLoader.getElements(destroyableGroup, "destroyable").size() + " destroyables! (Search)");
				}

				
				for(Element destroyable : destroyables) {
					ServerLog.info("Found a destroyable! (Loop)");
					
					String name = "Core";
					MapTeam team = this;
					int completion = 0;
					List<Material> materials = new ArrayList<Material>();
					String materialNames = "";
					
					if(destroyable.attributeValue("owner") == null && destroyable.getParent().attributeValue("owner") == null) continue;
					if(destroyable.attributeValue("owner") != null && !isThisTeam(destroyable.attributeValue("owner"))) continue;
					if(destroyable.getParent().attributeValue("owner") != null && !isThisTeam(destroyable.getParent().attributeValue("owner"))) continue;
					
					if(destroyable.attributeValue("materials") == null && destroyable.getParent().attributeValue("materials") == null) continue;
					if(destroyable.attributeValue("materials") != null) materialNames = destroyable.attributeValue("materials");
					else if(destroyable.getParent().attributeValue("materials") != null) materialNames = destroyable.getParent().attributeValue("materials");
					if(materialNames == null) continue;
					
					String[] materialStrings = new String[]{materialNames};
					if(materialNames.contains(";")) materialStrings = materialNames.split(";");
					
					for(String material : materialStrings)
						if(ConversionUtil.convertStringToMaterial(material) != null)
							materials.add(ConversionUtil.convertStringToMaterial(material));
					
					if(destroyable.attributeValue("completion") == null && destroyable.getParent().attributeValue("completion") == null) completion = 100;
					if(destroyable.attributeValue("completion") != null) completion = ConversionUtil.convertStringToInteger(destroyable.attributeValue("completion"));
					else if(destroyable.getParent().attributeValue("completion") != null) completion = ConversionUtil.convertStringToInteger(destroyable.getParent().attributeValue("completion"));
					
					if(destroyable.attributeValue("name") != null) name = destroyable.attributeValue("name");
					else if(destroyable.getParent().attributeValue("name") != null) name = destroyable.getParent().attributeValue("name");
					
					List<Location> locations = new ArrayList<Location>();
					Region region = new Region(map, destroyable, RegionType.ALL);
					for(Location location : region.getLocations())
						if(materials.size() == 0 || materials.contains(location.getBlock().getType()))
							locations.add(location);
					
					List<MonumentBlock> blocks = new ArrayList<MonumentBlock>();
					for(Location location : locations)
						blocks.add(new MonumentBlock(location));
					
					this.objectives.add(new MonumentObjective(map, team, name, blocks, completion));
				}
			}

			List<Element> rootCores = MapLoader.getElements(root, "cores");
			
			for(Element element : rootCores) {
				List<Element> cores = new ArrayList<Element>();
				
				List<Element> coreGroups = MapLoader.getElements(element, "cores");
				for(Element coreGroup : coreGroups) {
					cores.addAll(MapLoader.getElements(coreGroup, "core"));
					ServerLog.info("Found " + MapLoader.getElements(coreGroup, "core").size() + " cores! (Search)");
				}
				
				List<Element> coreGroup = MapLoader.getElements(element, "core");
				for(Element core : coreGroup) {
					cores.add(core);
					ServerLog.info("Found a core! (Search)");
				}
				
				for(Element core : cores) {
					ServerLog.info("Found a core! (Loop)");
					
					String name = "Core";
					MapTeam team = this;
					int leak = 8;
					Material material = Material.AIR;
					
					if(core.attributeValue("team") == null && core.getParent().attributeValue("team") == null) continue;
					if(core.attributeValue("team") != null && !isThisTeam(core.attributeValue("team"))) continue;
					if(core.getParent().attributeValue("team") != null && !isThisTeam(core.getParent().attributeValue("team"))) continue;
					
					if(core.attributeValue("material") == null && core.getParent().attributeValue("material") == null) continue;
					if(core.attributeValue("material") != null) material = ConversionUtil.convertStringToMaterial(core.attributeValue("material"));
					else if(core.getParent().attributeValue("material") != null) material = ConversionUtil.convertStringToMaterial(core.getParent().attributeValue("material"));
					if(material == null) continue;
					
					if(core.attributeValue("leak") != null) leak = ConversionUtil.convertStringToInteger(core.attributeValue("leak"));
					else if(core.getParent().attributeValue("leak") != null) leak = ConversionUtil.convertStringToInteger(core.getParent().attributeValue("leak"));
					
					if(core.attributeValue("name") != null) name = core.attributeValue("name");
					else if(core.getParent().attributeValue("name") != null) name = core.getParent().attributeValue("name");
					
					List<Location> blocks = new ArrayList<Location>();
					Region region = new Region(map, core, RegionType.ALL);
					for(Location location : region.getLocations())
						if(material == location.getBlock().getType())
							blocks.add(location);
					
					CoreStage stage = CoreStage.OTHER;
					if(material == Material.OBSIDIAN) stage = CoreStage.OBSIDIAN;
					else if(material == Material.GOLD_BLOCK) stage = CoreStage.GOLD;
					else if(material == Material.GLASS) stage = CoreStage.GLASS;
					
					CoreObjective coreObject = new CoreObjective(map, team, name, blocks, leak, stage);
					this.objectives.add(coreObject);
					
				}
			}
		}

		List<String> names = new ArrayList<String>();
		for(TeamObjective objective : this.objectives) {
			String name = " " + objective.getColor() + objective.getName() + objective.getSpaces();
			//Scrimmage.getInstance().getLogger().severe(name.length() + " Thats's " + name + "(" + objective.getName() + " AND " + objective.getSpaces() + ")");
			if(name.length() > 16) {
				int extra = name.length() - 16;
				String trimmed = objective.getName().substring(0, objective.getName().length() - 1 - extra);
				name = " " + objective.getColor() + trimmed + objective.getSpaces();
			}
			
			names.add(name);
		}
		
		String name = getColor() + getDisplayName();
		if(name.length() > 16) {
			int extra = name.length() - 16;
			name = name.substring(0, name.length() - 1 - extra);
		}
		
		names.add(name);
		
		int score = start;
		for(String offlineName : names) {
			Scrimmage.getInstance().getServer().getScoreboardManager().getNewScoreboard().registerNewTeam("" + score);
			OfflinePlayer player = Scrimmage.getInstance().getServer().getOfflinePlayer(offlineName);
			//Scrimmage.getInstance().getServer().getScoreboardManager().getMainScoreboard().getTeam(offlineName + score).addPlayer(player);
			getMap().getBoardObjective().getScore(player).setScore(score);
			score++;
		}
		
		return names.size(); // start at score 1. Have for example, 4 objecitves 1 team. 5. return 5 + 1, for the next team.
	}
	
	public void load(Element search) {
		load(search, 0);
	}
	
	public void load(Element search, int i) {
		String tag = "spawn";
		if(isObserver())
			tag = "default";
		
		List<MapTeamSpawn> spawns = new ArrayList<MapTeamSpawn>();
		List<Element> spawnElements = MapLoader.getElements(search, tag);
		
		
		for(Element element : spawnElements)
			if(isObserver() || (element.attributeValue("team") != null && isThisTeam(element.attributeValue("team")))) {
				Region region = new Region(map, element, RegionType.ALL);
				String kit = element.attributeValue("kit");
				
				List<ConfiguredRegion> configured = region.getRegions();
				for(ConfiguredRegion conf : configured) {
					if(kit == null) kit = element.attributeValue("kit");
					spawns.add(new MapTeamSpawn(getMap(), conf, kit));
				}
			}
		
		if(!isObserver())
			for(Element element : MapLoader.getElements(search, "spawns"))
				if(element.attributeValue("team") != null && isThisTeam(element.attributeValue("team"))) {
					search = element;
					
					spawnElements = MapLoader.getElements(element, tag);
					for(Element element2 : spawnElements) {
						Region region = new Region(map, element2, RegionType.ALL);
						String kit = element.attributeValue("kit");
						
						List<ConfiguredRegion> configured = region.getRegions();
						for(ConfiguredRegion conf : configured) {
							if(kit == null) kit = element2.attributeValue("kit");
							spawns.add(new MapTeamSpawn(getMap(), conf, kit));
						}
					}
				} else
					for(Element element2 : MapLoader.getElements(element, "spawn"))
						if(element2.attributeValue("team") != null && isThisTeam(element2.attributeValue("team").toLowerCase())) {
							Region region = new Region(map, element2, RegionType.ALL);
							String kit = element.attributeValue("kit");
							
							List<ConfiguredRegion> configured = region.getRegions();
							for(ConfiguredRegion conf : configured) {
								if(kit == null) kit = element2.attributeValue("kit");
								spawns.add(new MapTeamSpawn(getMap(), conf, kit));
							}
						}
		
		/*
		 * Now I have to make these spawns into their actual spawn value regions/points... * fun *
		 * Dat class shift...
		 */
		
		this.spawns = spawns;
		
		int spawnCount = spawns.size();
		int locationCount = 0;
		for(MapTeamSpawn spawn : this.spawns)
			locationCount += spawn.getPossibles().size();
		
		ServerLog.info("Loaded " + spawnCount + " spawn(s), containing " + locationCount + " location(s) for '" + name + "'!");
		
		if(!isObserver() && i != -1) loadTeamObjectives(true, i);
	}
	
	public MapTeamSpawn getSpawn() {
		try {
			return spawns.get(Scrimmage.random(0, spawns.size() - 1));
		} catch(IndexOutOfBoundsException ioobe) {
			// What a lovely Exception label... hahah
			ioobe.printStackTrace();
		}
		
		return null;
	}
	
	public String getColorName() {
		return color.name().replaceAll("_", " ");
	}
	
	public boolean isObserver() {
		return ChatColor.AQUA == getColor();
	}
	
	/*public boolean isShiny() {
		Player player = Bukkit.getPlayer(name);
		if (player.getDisplayName().equals("ShinyDialga45") && client.isObserver()); {
		return ChatColor.AQUA == getColor();
		}
	}*/
	public MapTeam clone() {
		return new MapTeam(getMap(), getName(), getColor(), getCap(), getSpawns());
	}
	
	public List<Client> getPlayers() {
		List<Client> clients = new ArrayList<Client>();
		
		for(Client client : Client.getClients())
			if(client.getTeam() == this)
				clients.add(client);
		
		return clients;
	}
	
	@SuppressWarnings("deprecation")
	public MapTeamSpawn loadout(Client client, boolean teleport, boolean clear) {
		if(clear) {
			client.getPlayer().getInventory().clear();
			client.getPlayer().getInventory().setHelmet(null);
			client.getPlayer().getInventory().setChestplate(null);
			client.getPlayer().getInventory().setLeggings(null);
			client.getPlayer().getInventory().setBoots(null);
		}
		
		MapTeamSpawn spawn = getSpawn();
		if(teleport) client.getPlayer().teleport(spawn.getSpawn());
		
		String[] perms = new String[]{"commandbook.teleport", "worldedit.navigation.thru.tool", "worldedit.navigation.jumpto.tool"};
		
		if(isObserver()) {
			for(String perm : perms) client.getPerms().setPermission(perm, true);
			client.getPlayer().setGameMode(GameMode.CREATIVE);
			client.getPlayer().getInventory().setItem(0, new ItemStack(Material.COMPASS));
			ItemStack mm = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta mainMenuButton = mm.getItemMeta();
            String mmName = ChatColor.RED + "" + ChatColor.BOLD + "Team Picker";
            mainMenuButton.setDisplayName(mmName);
            mm.setItemMeta(mainMenuButton);
            client.getPlayer().getInventory().addItem(mm);
		} else {
			for(String perm : perms) client.getPerms().unsetPermission(perm);
			client.getPlayer().setGameMode(GameMode.SURVIVAL);
			
			// Load the kit here.
			if(spawn.getKit() != null)
				spawn.getKit().load(client);
		}

		client.getPlayer().updateInventory();
		return spawn;
	}
	
	public boolean isThisTeam(String check) {
		if(check == null) return false;
		
		return contains(getColorName(), check) || contains(getDisplayName(), check) ||
				contains(getName(), check) || check.equalsIgnoreCase(getName()) ||
				check.equalsIgnoreCase(getDisplayName()) || check.equalsIgnoreCase(getColorName());
	}
	
	public boolean isThisTeam(MapTeam team) {
		return isThisTeam(team.getColorName()) || isThisTeam(team.getDisplayName()) || isThisTeam(team.getName());
	}
	
	public boolean contains(String check, String contains) {
		if(contains == null) return false;
		if(check == null) return false;
		return check.toLowerCase().contains(contains.toLowerCase());
	}
	
	public List<CoreObjective> getCores() {
		List<CoreObjective> cores = new ArrayList<CoreObjective>();
		
		if(getObjectives() == null)
			return cores;
		
		for(TeamObjective obj : getObjectives())
			if(obj instanceof CoreObjective)
				cores.add((CoreObjective) obj);
		
		return cores;
	}
	
	public CoreObjective getCore(Block block) {
		return getCore(block.getLocation());
	}
	
	public CoreObjective getCore(Location location) {
		for(CoreObjective core : getCores())
			if(core.isLocation(location))
				return core;
		
		return null;
	}
	
	public CoreObjective getCoreLeak(Location location) {
		for(CoreObjective core : getCores())
			if(core.isLeak(location))
				return core;
		
		return null;
	}
	
	public List<WoolObjective> getWools() {
		List<WoolObjective> wools = new ArrayList<WoolObjective>();
		
		if(getObjectives() == null)
			return wools;
		
		for(TeamObjective obj : getObjectives())
			if(obj instanceof WoolObjective)
				wools.add((WoolObjective) obj);
		
		return wools;
	}
	
	@SuppressWarnings("deprecation")
	public WoolObjective getWool(Block block) {
		for(WoolObjective wool : getWools())
			if(new Wool(block.getType(), block.getData()).getColor() == wool.getWool())
				return wool;
		
		return null;
	}
	
	public WoolObjective getWool(Location location) {
		for(WoolObjective wool : getWools())
			if(wool.isLocation(location))
				return wool;
		
		return null;
	}
	
	public List<MonumentObjective> getMonuments() {
		List<MonumentObjective> monuments = new ArrayList<MonumentObjective>();
		
		if(getObjectives() == null)
			return monuments;
		
		for(TeamObjective obj : getObjectives())
			if(obj instanceof MonumentObjective)
				monuments.add((MonumentObjective) obj);
		
		return monuments;
	}
	
	public MonumentObjective getMonument(Block block) {
		return getMonument(block.getLocation());
	}
	
	public MonumentObjective getMonument(Location location) {
		for(MonumentObjective monument : getMonuments())
			if(monument.isLocation(location))
				return monument;
		
		return null;
	}
	
	public int getCompleted() {
		int complete = 0;
		
		for(TeamObjective objective : getObjectives())
			if(objective.isComplete())
				complete++;
		
		return complete;
	}
	
	public int getTouches() {
		int complete = 0;
		
		for(TeamObjective objective : getObjectives())
			complete += objective.getTouched();
		
		return complete;
	}
	
	public static MapTeam getTeamByChatColor(List<MapTeam> teams, ChatColor color) {
		for(MapTeam team : teams)
			if(team.getColor() == color)
				return team;
		
		return null;
	}
	
}
