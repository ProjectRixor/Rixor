package com.projectrixor.rixor.scrimmage.match;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MatchTickTimer extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    boolean ended = false;
    Match match;
 
    public MatchTickTimer(Match match) {
    	this.match = match;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

	public boolean isEnded(){
		return this.ended;
	}

	public Match getMatch(){
		return this.match;
	}

	public void setEnded(boolean ended){
		this.ended=ended;
	}
}