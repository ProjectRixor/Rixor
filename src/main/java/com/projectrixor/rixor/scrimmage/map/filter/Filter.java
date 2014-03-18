package com.projectrixor.rixor.scrimmage.map.filter;

import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.map.MapTeam;
import org.bukkit.Material;
import org.dom4j.Element;

import lombok.Getter;

public class Filter {
	
	/*
	 * Having to get used to the OCN XML filter system, this is going to be a challenge...
	 * But then again, I thought that regions would be a challenge, hahah... Then they were easy >;D
	 * 
	 * There are 2 known exception elements - team & block.
	 * <team>[team]</team>
	 * <block>[block name]</block>
	 * 
	 * To get the block name I need to iterate through the materials, get the .name() and replace the "_"s with " "s (underscore with space)
	 * Not all filters will have parents, but I have no clue what to do with a filter without a parent? Maybe it's already defined...
	 * Maybe I'm just going to use a predefined filter, which means I have to apply the same tactics as I did with regions *yay*
	 * This is probably going to be one of the hardest ones to do, right?
	 * 
	 * Need to make <not> invert the outcome of anything inside it.
	 * Maybe check for not first, then after determining the outcode from inside, if(not) o = o != o or something like that? xD o = !o might work.
	 */
	
	@Getter String name;
	@Getter List<FilterType> parents;
	@Getter String message;
	
	@Getter List<MapTeam> allowTeams; // teams must be initialised before these lists can be filled.
	@Getter List<MapTeam> denyTeams;

	@Getter List<Material> allowBlocks; // only really allowing/denying materials...
	@Getter List<Material> denyBlocks;
	
	private Filter(String name, List<FilterType> parents, List<MapTeam> allowTeams,
			List<MapTeam> denyTeams, List<Material> allowBlocks, List<Material> denyBlocks) {
		this.name = name;
		this.parents = parents;
		this.allowTeams = allowTeams;
		this.denyTeams = denyTeams;
		this.allowBlocks = allowBlocks;
		this.denyBlocks = denyBlocks;
	}
	
	public Filter(Element element) {
		this.name = element.attributeValue("name");
		List<FilterType> list = new ArrayList<FilterType>();
		
		if(element.attributeValue("parents") != null) {
			String[] parents = element.attributeValue("parents").split(" ");
			
			for(String parent : parents)
				if(FilterType.getBySplitAttribute(parent) != null)
					list.add(FilterType.getBySplitAttribute(parent));
		}
		
		if(FilterType.getBySplitAttribute(name) != null)
			list.add(FilterType.getBySplitAttribute(name));
	}
	
	public boolean hasMessage() {
		return getMessage() != null;
	}
	
	/*
	 * I need a way of updating all the teams to the correct team of the new object
	 * Supplying all the teams inside the clone should do the trick - HOWEVER, I need a way of matching each one
	 * 
	 * Using the ChatColor seems like the best way to do it.
	 * 
	 * For the sake of whatever, I'll allow a pure-clone method, which uses the same team objects as before
	 */
	
	public Filter clone(List<MapTeam> replace) {
		List<MapTeam> allowTeams = new ArrayList<MapTeam>();
		List<MapTeam> denyTeams = new ArrayList<MapTeam>();
		
		for(MapTeam team : getAllowTeams())
			allowTeams.add(MapTeam.getTeamByChatColor(replace, team.getColor()));
		for(MapTeam team : getDenyTeams())
			allowTeams.add(MapTeam.getTeamByChatColor(replace, team.getColor()));
		
		return new Filter(name, parents, allowTeams, denyTeams, allowBlocks, denyBlocks);
	}
	
	public Filter clone() {
		return new Filter(name, parents, allowTeams, denyTeams, allowBlocks, denyBlocks);
	}
	
}
