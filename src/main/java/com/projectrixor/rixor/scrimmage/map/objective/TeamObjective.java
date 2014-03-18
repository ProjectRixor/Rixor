package com.projectrixor.rixor.scrimmage.map.objective;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapTeam;
import com.projectrixor.rixor.scrimmage.map.extras.SidebarType;
import com.projectrixor.rixor.scrimmage.utils.Characters;
import org.bukkit.ChatColor;

import lombok.Getter;
import lombok.Setter;

public class TeamObjective {
	
	@Getter
	Map map;
	
	@Getter String name;
	@Getter MapTeam team;
	@Getter boolean complete;
	@Getter boolean destroyed;
	@Getter int used;
	
	@Getter @Setter int touched;
	
	public TeamObjective(Map map, MapTeam owner, String name) {
		this.name = name;
		this.team = owner;
		this.complete = false;
		this.destroyed = false;
		
		this.used = 0;
		try {
			for(MapTeam team : map.getTeams())
				for(TeamObjective objective : team.getObjectives())
					if(objective.getName().equalsIgnoreCase(name))
						this.used++;
		} catch(Exception e) {
			// ignore, it literally just means that it should be 0.
		}
	}
	
	/*
	 * The integer of 'used' is there so I can add an extra space (or two) to the end of objectives for the scoreboard
	 */
	
	public String getColor() {
		if(complete) return ChatColor.GREEN + Characters.check + " " + ChatColor.WHITE;
		return ChatColor.RED + Characters.x + " " + ChatColor.WHITE;
	}
	
	public ChatColor getWoolColor() {
		if(complete) return ChatColor.GREEN;
		return ChatColor.RED;
	}
	
	public String getSpaces() {
		return Map.getSpaces(used - 1);
	}
	
	public ObjectiveType getType() {
		if(this instanceof WoolObjective)
			return ObjectiveType.CTW;
		else if(this instanceof MonumentObjective)
			return ObjectiveType.DTM;
		return ObjectiveType.NONE;
	}
	
	public void addTouch() {
		addTouch(1);
	}
	
	public void addTouch(int amount) {
		setTouched(getTouched() + amount);
	}

	private void checkForWinner(MapTeam whoCompleted){
		if(whoCompleted.getCompleted() == getTeam().getObjectives().size())  {
			Scrimmage.getRotation().getSlot().getMatch().end(whoCompleted);
		}
	}

	public void complete(){
		this.complete = true;
	}
	
	public void setComplete(boolean complete, MapTeam whoCompleted) {
		destroyed = true;
		//Scrimmage.getInstance().getLogger().info(getName() + " and " + getType().toString() + " and " + getTeam().getDisplayName() + " and " + getTouched());
		if (complete){
			for (TeamObjective t : whoCompleted.getObjectives()){
				if (t.getName().equals(getName()) && t.getType() == getType()){
					t.complete();
				}
			}
		}

		whoCompleted.getMap().reloadSidebar(true, SidebarType.OBJECTIVES);
		checkForWinner(whoCompleted);
	}
	
}
