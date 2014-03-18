package com.projectrixor.rixor.scrimmage;

public class ServerLog {
	
	public static void info(String message) {
		Rixor.getInstance().getLogger().info(message);
	}
	
}
