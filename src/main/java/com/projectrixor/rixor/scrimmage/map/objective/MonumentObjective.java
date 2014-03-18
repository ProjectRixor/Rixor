package com.projectrixor.rixor.scrimmage.map.objective;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.projectrixor.rixor.scrimmage.player.Client;
import lombok.Getter;
import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapTeam;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class MonumentObjective extends TeamObjective {
	
	@Getter List<MonumentBlock> blocks;
	@Getter int percentage;
	
	public MonumentObjective(Map map, MapTeam owner, String name, List<MonumentBlock> blocks, int percentage) {
		super(map, owner, name);
		this.blocks = blocks;
		this.percentage = percentage;
	}
	
	public boolean isLocation(Location location) {
		return getBlock(location) != null;
	}
	
	public boolean getLocation(Location location) {
		return getBlock(location) != null;
	}
	
	public MonumentBlock getBlock(Location location) {
		for(MonumentBlock block : getBlocks()) {
			Location loc = block.getLocation();
			boolean x = loc.getBlockX() == location.getBlockX();
			boolean y = loc.getBlockY() == location.getBlockY();
			boolean z = loc.getBlockZ() == location.getBlockZ();
			if(x && y && z) return block;
		}
		
		return null;
	}
	
	public boolean addBreak(Location location, Client breaker) {
	//	Scrimmage.debug("Monument Test 1", "Monument");
		if(!isLocation(location)) return false;
		
		MonumentBlock block = getBlock(location);
		block.setBreaker(breaker);
		
		if(isDestroyed()) {
			String teamandname = getTeam().getColor() + getTeam().getDisplayName() + "'s " + ChatColor.AQUA + getName();
			String destroyedby = ChatColor.GRAY + " destroyed by ";
			
			List<MonumentCalculation> calculations = new ArrayList<MonumentCalculation>();
			List<Client> clients = new ArrayList<Client>();
			for(MonumentBlock check : getBlocks())
				if(check.isBroken() && !clients.contains(check.getBreaker()))
					clients.add(check.getBreaker());
			
			for(Client client : clients)
				calculations.add(MonumentCalculation.getCalculation(client, this));
			
			Collections.sort(calculations, new Comparator<MonumentCalculation>() {
			    @Override
			    public int compare(MonumentCalculation c1, MonumentCalculation c2) {
			        return new Double(c1.getPercentage()).compareTo(c2.getPercentage());
			    }
			});
			
			String destroyed = "";
			if(calculations.size() == 1) {
				destroyed += calculations.get(0).getClient().getTeam().getColor() + calculations.get(0).getClient().getPlayer().getName();
				destroyed += ChatColor.GRAY + " (" + calculations.get(0).getPercentage() + "%)";
			} else if(calculations.size() >= 2) {
				/*
				 * index 0 should prefix ""
				 * index 1 to (max index - 1) should prefix ", "
				 * index max index should prefix " and "
				 */
				
				int index = 0;
				while(index < calculations.size()) {
					if(index > 0) {
						destroyed += ChatColor.GRAY;
						if(index == (calculations.size() -1))
							destroyed += " and ";
						else destroyed += ", ";
					}
					
					destroyed += calculations.get(index).getClient().getTeam().getColor() + calculations.get(index).getClient().getPlayer().getName();
					destroyed += ChatColor.GRAY + " (" + calculations.get(index).getPercentage() + "%)";
					index++;
				}
			}
			
			String message = teamandname + destroyedby + destroyed;
			Scrimmage.broadcast(message);
		}
		
		setComplete(isDestroyed(), breaker.getTeam());
		return true;
	}
	
	public boolean isDestroyed() {
		List<MonumentBlock> broken = new ArrayList<MonumentBlock>();
		for(MonumentBlock block : blocks)
			if(block.isBroken())
				broken.add(block);
		
		return blocks.size() == broken.size();
	}
	
}
