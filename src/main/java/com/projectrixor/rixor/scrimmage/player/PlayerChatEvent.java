package com.projectrixor.rixor.scrimmage.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChatEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
    boolean cancelled = false;
    
    Client client;
    String message;
    boolean team;

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

	public boolean isCancelled(){
		return this.cancelled;
	}

	public Client getClient(){
		return this.client;
	}

	public String getMessage(){
		return this.message;
	}

	public boolean isTeam(){
		return this.team;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled=cancelled;
	}
}
