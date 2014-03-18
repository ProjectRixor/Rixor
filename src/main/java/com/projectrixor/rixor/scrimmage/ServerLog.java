package com.projectrixor.rixor.scrimmage;

public class ServerLog {
	
	public static void info(String message) {
		Scrimmage.getInstance().getLogger().info(message);
	}
	
}
