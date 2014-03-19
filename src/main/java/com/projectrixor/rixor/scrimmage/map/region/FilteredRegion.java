package com.projectrixor.rixor.scrimmage.map.region;

import com.projectrixor.rixor.scrimmage.map.filter.Filter;

import java.util.List;

public class FilteredRegion {
	
	List<RegionGroup> regions;
	List<Filter> filters;
	
	public FilteredRegion(List<RegionGroup> regions, List<Filter> filters) {
		this.regions = regions;
		this.filters = filters;
	}

	public List<RegionGroup> getRegions(){
		return this.regions;
	}

	public List<Filter> getFilters(){
		return this.filters;
	}
}
