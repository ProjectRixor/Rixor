package com.projectrixor.rixor.scrimmage.player;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChatEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
    @Getter @Setter boolean cancelled = false;
    
    @Getter Client client;
    @Getter String message;
    @Getter boolean team;

	public PlayerChatEvent(Client client, String message, boolean team) {
		this.client = client;
		this.message = message;
		this.team = team;
	}
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
	
}
