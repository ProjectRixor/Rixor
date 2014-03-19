package com.projectrixor.rixor.scrimmage.map.extras;

public class Contributor {
	
	String name;
	String contribution;
	
	public Contributor(String name, String contribution) {
		this.name = name;
		this.contribution = contribution;
	}

	public String getName(){
		return this.name;
	}

	public String getContribution(){
		return this.contribution;
	}
}
