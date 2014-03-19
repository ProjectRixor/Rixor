package com.projectrixor.rixor.scrimmage.map.objective;

import org.bukkit.Material;

public enum CoreStage {

	GLASS(20*60, Material.GLASS),
	GOLD(GLASS, 15*60, Material.GOLD_BLOCK),
	OBSIDIAN(GOLD, Material.OBSIDIAN),
	OTHER();
	
	int time;
	Material material;
	CoreStage next;
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

	public int getTime(){
		return this.time;
	}

	public Material getMaterial(){
		return this.material;
	}

	public CoreStage getNext(){
		return this.next;
	}
}
