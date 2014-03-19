package com.projectrixor.rixor.scrimmage.api;

import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.match.Match;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author MasterEjay
 */
public class MatchStartEvent extends Event{
	private static final HandlerList handlers = new HandlerList();

	Map map;
	Match match;

	public MatchStartEvent(Map map, Match match){
		this.map = map;
		this.match = match;
	}

	public Map getMap(){
		return map;
	}

	public Match getMatch(){
		return match;
	}


	@Override
	public HandlerList getHandlers(){
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
