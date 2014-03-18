package com.projectrixor.rixor.scrimmage.map.filter.events;

import com.projectrixor.rixor.scrimmage.player.Client;
import lombok.Getter;
import com.projectrixor.rixor.scrimmage.map.Map;

import org.bukkit.block.BlockState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BlockChangeEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    
    @Getter Event cause;
    @Getter Map map;
    @Getter
    Client client;
    @Getter BlockState oldState;
    @Getter BlockState newState;
 
    public BlockChangeEvent(Event cause, Map map, Client client, BlockState oldState, BlockState newState) {
    	this.cause = cause;
    	this.map = map;
    	this.client = client;
    	this.oldState = oldState;
    	this.newState = newState;
    }
    
    public boolean isCancelled() {
    	if(cause instanceof Cancellable == false) return false;
    	Cancellable cancel = (Cancellable) cause;
    	return cancel.isCancelled();
    }
    
    public boolean setCancelled(boolean cancelled) {
    	if(cause instanceof Cancellable == false) return false;
    	Cancellable cancel = (Cancellable) cause;
    	cancel.setCancelled(true);
    	
    	return true;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
	
}