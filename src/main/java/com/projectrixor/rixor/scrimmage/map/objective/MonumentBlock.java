package com.projectrixor.rixor.scrimmage.map.objective;

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.Location;

public class MonumentBlock {
	
	Client breaker;
	Location location;
	
	public MonumentBlock(Location location) {
		this.location = location;
	}
	
	public boolean isBroken() {
		return breaker != null;
	}

	public Client getBreaker(){
		return this.breaker;
	}

	public Location getLocation(){
		return this.location;
	}

	public void setBreaker(Client breaker){
		this.breaker=breaker;
	}
}
