package com.projectrixor.rixor.scrimmage.map.extras;

import lombok.Getter;

public class Contributor {
	
	@Getter String name;
	@Getter String contribution;
	
	public Contributor(String name, String contribution) {
		this.name = name;
		this.contribution = contribution;
	}
	
}
