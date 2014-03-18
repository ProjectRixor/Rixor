package com.projectrixor.rixor.scrimmage.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.server.v1_7_R1.SharedConstants;

public class Characters {

	public static String raquo = "\u00BB";
	public static String laquo = "\u00AB";
	public static String x = "\u2715";
	public static String check = "\u2714";
	public static String wool = "\u2610";
	
	public static void AllowCharacters(String allow) {
		try {
			Field allowed = SharedConstants.class.getDeclaredField("allowedCharacters");
			allowed.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(allowed, allowed.getModifiers() &~ Modifier.FINAL);
			String oldallowedchars = (String)allowed.get(null);
			StringBuilder sb = new StringBuilder();
			sb.append(oldallowedchars);
			sb.append(allow);
			allowed.set(null, sb.toString());
		} catch(Exception ex) {
			//
		}
	}

}
