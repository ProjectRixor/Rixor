package com.projectrixor.rixor.scrimmage.map.objective;

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.Location;

import lombok.Getter;
import lombok.Setter;

public class MonumentBlock {
	
	@Getter @Setter
	Client breaker;
	@Getter Location location;
	
	public MonumentBlock(Location location) {
		this.location = location;
	}
	
	public boolean isBroken() {
		return breaker != null;
	}
	
}
