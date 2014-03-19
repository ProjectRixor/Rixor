package com.projectrixor.rixor.scrimmage.rotation;

import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapLoader;
import com.projectrixor.rixor.scrimmage.match.Match;

public class RotationSlot {
	
	Map map;
	MapLoader loader;
	Match match;
	
	public RotationSlot(MapLoader loader) {
		this.loader = loader;
	}
	
	public Match load(int length) {
		map = loader.getMap(this);
		match = new Match(this, length);
		return match;
	}
	
	public Match load() {
		// return load(45*60);
		return load(-1);
	}

	public Map getMap(){
		return this.map;
	}

	public MapLoader getLoader(){
		return this.loader;
	}

	public Match getMatch(){
		return this.match;
	}
}
