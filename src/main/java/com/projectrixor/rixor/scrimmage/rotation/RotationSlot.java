package com.projectrixor.rixor.scrimmage.rotation;

import com.projectrixor.rixor.scrimmage.map.MapLoader;
import lombok.Getter;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.match.Match;

public class RotationSlot {
	
	@Getter Map map;
	@Getter
	MapLoader loader;
	@Getter Match match;
	
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
	
}
