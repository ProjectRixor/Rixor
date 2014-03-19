package com.projectrixor.rixor.scrimmage.api;

import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.match.Match;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author MasterEjay
 */
public class MatchCycleEvent extends Event{
	private static final HandlerList handlers = new HandlerList();

	Map mapFrom;
	Map mapTo;

	public MatchCycleEvent(Map mapFrom, Map mapTo){
		this.mapFrom = mapFrom;
		this.mapTo = mapTo;
	}

	public Map getMapFrom(){
		return mapFrom;
	}

	public Map getMapTo(){
		return mapTo;
	}

	@Override
	public HandlerList getHandlers(){
		return handlers;
	}
}
