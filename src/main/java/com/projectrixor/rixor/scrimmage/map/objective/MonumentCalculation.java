package com.projectrixor.rixor.scrimmage.map.objective;

import com.projectrixor.rixor.scrimmage.player.Client;

import java.util.ArrayList;
import java.util.List;

public class MonumentCalculation {
	
	MonumentObjective monument;
	
	Client client;
	private List<MonumentBlock> destroyed;
	
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

	public MonumentObjective getMonument(){
		return this.monument;
	}

	public Client getClient(){
		return this.client;
	}

	public List<MonumentBlock> getDestroyed(){
		return this.destroyed;
	}
}
