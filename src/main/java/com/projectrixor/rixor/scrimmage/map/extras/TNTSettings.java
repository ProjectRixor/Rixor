package com.projectrixor.rixor.scrimmage.map.extras;

/**
 * @author MasterEjay
 */
public class TNTSettings {

	boolean instantIgnite = false;
	boolean blockDamage = true;

	public boolean isInstantIgnite(){
		return this.instantIgnite;
	}

	public boolean isBlockDamage(){
		return this.blockDamage;
	}

	public void setInstantIgnite(boolean instantIgnite){
		this.instantIgnite=instantIgnite;
	}

	public void setBlockDamage(boolean blockDamage){
		this.blockDamage=blockDamage;
	}
}
