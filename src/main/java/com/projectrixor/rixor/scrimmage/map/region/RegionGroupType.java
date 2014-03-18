package com.projectrixor.rixor.scrimmage.map.region;

public enum RegionGroupType {
	
	NEGATIVE(),
	UNION(),
	COMPLEMENT(),
	INTERSECT(),
	APPLY();
	
	public static RegionGroupType getByElementName(String name) {
		for(RegionGroupType type : values())
			if(type.name().equalsIgnoreCase(name))
				return type;
		
		return null;
	}
	
}
