package com.projectrixor.rixor.scrimmage.player;

import java.util.HashMap;

public class StatTracker {
	public static HashMap<String, Integer> totalKills = new HashMap<String, Integer>();
	public static HashMap<String, Integer> totalDeaths = new HashMap<String, Integer>();
	
	public static void gainKill(String name) {
		int kills = 0;
		if(totalKills.containsKey(name)) {
			totalKills.remove(name);
			kills = totalKills.get(name);
		}
		totalKills.put(name, kills);
	}
	
	public static void gainDeath(String name) {
		int deaths = 0;
		if(totalDeaths.containsKey(name)) {
			totalDeaths.remove(name);
			deaths = totalDeaths.get(name);
		}
		totalDeaths.put(name, deaths);
	}
	
	public static double getKDRatio(String name) {
		double ratio = 0.0;
		if(totalKills.containsKey(name)) {
			if (totalDeaths.containsKey(name)) {
				ratio = totalKills.get(name) / totalDeaths.get(name);
			} else {
				ratio = totalKills.get(name);
			}
		}
		return ratio;
	}
}
