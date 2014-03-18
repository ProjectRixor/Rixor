package com.projectrixor.rixor.scrimmage.map.objective;

import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.player.Client;
import lombok.Getter;

public class MonumentCalculation {
	
	@Getter MonumentObjective monument;
	
	@Getter
	Client client;
	private @Getter List<MonumentBlock> destroyed;
	
	private MonumentCalculation(Client client, MonumentObjective monument) {
		this.monument = monument;
		this.client = client;
		this.destroyed = new ArrayList<MonumentBlock>();
		updateBroken();
	}
	
	private void updateBroken() {
		destroyed = new ArrayList<MonumentBlock>();
		for(MonumentBlock block : monument.getBlocks())
			if(block.isBroken() && block.getBreaker() == client)
				destroyed.add(block);
	}
	
	public double getPercentage() {
		updateBroken();
		return (destroyed.size() / monument.getBlocks().size()) * 100;
	}
	
	public static MonumentCalculation getCalculation(Client client, MonumentObjective monument) {
		return new MonumentCalculation(client, monument);
	}
	
}
