package com.projectrixor.rixor.scrimmage.api;

import com.projectrixor.rixor.scrimmage.map.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDiedEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    boolean ended = false;
    Map map;
    Player killed;
    Player killer;
 
    public PlayerDiedEvent(Map map, Player killed, Player killer) {
    	this.map = map;
		this.killed = killed;
		this.killer = killer;
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

	public Map getMap(){
		return this.map;
	}

	public Player getKilled(){
		return this.killed;
	}

	public Player getKiller(){
		return this.killer;
	}

	public void setEnded(boolean ended){
		this.ended=ended;
	}
}