package com.projectrixor.rixor.scrimmage.match.events;

import lombok.Getter;
import lombok.Setter;
import com.projectrixor.rixor.scrimmage.map.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDiedEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    @Getter @Setter boolean ended = false;
    @Getter Map map;
    @Getter Player killed;
    @Getter Player killer;
 
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
	
}