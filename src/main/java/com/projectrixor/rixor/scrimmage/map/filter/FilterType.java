package com.projectrixor.rixor.scrimmage.map.filter;

import lombok.Getter;

public enum FilterType {
	
	ALLOW_PLAYERS(),
	DENY_PLAYERS(),
	
	ALLOW_PLACE(),
	DENY_PLACE(),
	
	ALLOW_BREAK(),
	DENY_BREAK(),
	
	ALLOW_BLOCKS(new FilterType[]{ALLOW_PLACE, ALLOW_BREAK}),
	DENY_BLOCKS(new FilterType[]{DENY_PLACE, DENY_BREAK}),
	
	ALLOW_INTERACT(),
	DENY_INTERACT(),
	
	ALLOW_WORLD(),
	DENY_WORLD(),
	
	ALLOW_SPAWNS(),
	DENY_SPAWNS(),
	
	ALLOW_ENTITIES(),
	DENY_ENTITIES(),
	
	ALLOW_MOBS(),
	DENY_MOBS(),
	
	ALLOW_ALL(new FilterType[]{ALLOW_PLAYERS, ALLOW_PLACE, ALLOW_BLOCKS, ALLOW_SPAWNS, ALLOW_ENTITIES, ALLOW_MOBS, ALLOW_INTERACT}),
	DENY_ALL(new FilterType[]{DENY_PLAYERS, DENY_PLACE, DENY_BLOCKS, DENY_SPAWNS, DENY_ENTITIES, DENY_MOBS, DENY_INTERACT});
	
	@Getter FilterType[] children;
	
	FilterType() {
		this(new FilterType[0]);
	}
	
	FilterType(FilterType[] types) {
		children = types;
	}
	
	public static FilterType getBySplitAttribute(String attr) {
		for(FilterType type : values())
			if(type.name().replaceAll("_", "-").equalsIgnoreCase(attr))
				return type;
		
		return null;
	}
	
}
