package com.projectrixor.rixor.scrimmage.map.objective;

import org.bukkit.Material;

import lombok.Getter;

public enum CoreStage {

	GLASS(20*60, Material.GLASS),
	GOLD(GLASS, 15*60, Material.GOLD_BLOCK),
	OBSIDIAN(GOLD, Material.OBSIDIAN),
	OTHER();
	
	@Getter int time;
	@Getter Material material;
	@Getter CoreStage next;
	CoreStage() {
		this(null, 0, null);
	}
	
	CoreStage(int time) {
		this(null, time, null);
	}
	
	CoreStage(CoreStage next, Material material) {
		this(next, 0, material);
	}
	
	CoreStage(int time, Material material) {
		this(null, time, material);
	}
	
	CoreStage(CoreStage next, int time, Material material) {
		this.next = next;
		this.time = time;
		this.material = material;
	}
	
	public boolean hasNext() {
		return next != null;
	}
	
}
