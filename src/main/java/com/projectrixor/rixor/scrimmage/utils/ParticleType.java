package com.projectrixor.rixor.scrimmage.utils;

import lombok.Getter;

public enum ParticleType {

	/**
	 * Each ParticleEffect has the packet name, and the environment in which it
	 * will be successfully displayed.
	 */
	HUGE_EXPLOSION("hugeexplosion", Environment.ANY), // works in any block type
	LARGE_EXPLODE("largeexplode", Environment.ANY),
	FIREWORK_SPARK("fireworksSpark", Environment.ANY),
	TOWN_AURA("townaura", Environment.ANY),
	CRIT("crit", Environment.ANY),
	MAGIC_CRIT("magicCrit", Environment.ANY),
	SMOKE("smoke", Environment.ANY),
	MOB_SPELL("mobSpell", Environment.ANY),
	MOB_SPELL_AMBIENT("mobSpellAmbient",Environment.ANY),
	SPELL("spell", Environment.ANY),
	INSTANT_SPELL("instantSpell", Environment.ANY),
	WITCH_MAGIC("witchMagic",Environment.ANY),
	NOTE("note", Environment.ANY),
	PORTAL("portal", Environment.ANY),
	ENCHANTMENT_TABLE("enchantmenttable", Environment.ANY),
	EXPLODE("explode", Environment.ANY),
	FLAME("flame", Environment.ANY),
	LAVA("lava", Environment.ANY),
	FOOTSTEP("footstep", Environment.ANY),
	LARGE_SMOKE("largesmoke", Environment.ANY),
	CLOUD("cloud", Environment.ANY),
	RED_DUST("reddust", Environment.ANY),
	SNOWBALL_POOF("snowballpoof", Environment.ANY),
	DRIP_WATER("dripWater", Environment.ANY),
	DRIP_LAVA("dripLava", Environment.ANY),
	SNOW_SHOVEL("snowshovel", Environment.ANY),
	SLIME("slime", Environment.ANY),
	HEART("heart",Environment.ANY),
	ANGRY_VILLAGER("angryVillager", Environment.ANY),
	HAPPY_VILLAGER("happerVillager", Environment.ANY),
	
	/*
	 * ICONCRACK is not reliable and should not be added to the API, across
	 * different sized texture packs it displays a different item)
	 * ICONCRACK("iconcrack_%id%", Environment.UKNOWN), //Guessing it is any,
	 * but didnt test
	*/
	TILECRACK("tilecrack_%id%_%data%", Environment.UKNOWN), // Guessing it is any, but didnt test
	SPLASH("splash", Environment.AIR), // only works in air
	BUBBLE("bubble", Environment.IN_WATER), // only works in water
	SUSPEND("suspend", Environment.UKNOWN), // Can't figure out what this does
	DEPTH_SUSPEND("depthSuspend", Environment.UKNOWN); // Can't figure out what this does

	@Getter String packetName;
	@Getter Environment environment;

	/**
	 * Each particle effect has a packet name, and an environment for developers
	 * 
	 * @param packetName
	 * @param environment
	 */
	ParticleType(String packetName, Environment environment) {
		this.packetName = packetName;
		this.environment = environment;
	}
	
	/**
	 * 
	 * Enum that dipicts in what envirnments a particle effect will be seen
	 * 
	*/
	public enum Environment {
		ANY,
		AIR,
		IN_WATER,
		UKNOWN;
	}

}
