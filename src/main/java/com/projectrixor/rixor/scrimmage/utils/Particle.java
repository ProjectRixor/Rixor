package com.projectrixor.rixor.scrimmage.utils;

/*import java.lang.reflect.Field;*/

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Particle {
	
	String packetName;
	ParticleType type;
	Location location;
	
	int id;
	int data;

	int speed;
	int count;
	
	public Particle(ParticleType type, Location location, int speed, int count) {
		this.type = type;
		this.location = location;
		this.speed = speed;
		this.count = count;
		
		this.packetName = type.getPacketName();
	}
	
	public Particle(ParticleType type, Location location) {
		this(type, location, 0, 0);
	}
	
	public void setID(int id) {
		this.id = id;
		
		if(this.packetName.contains("%id%"))
			this.packetName.replace("%id%", "" + id);
	}
	
	public void setData(int data) {
		this.data = data;
		
		if(this.packetName.contains("%data%"))
			this.packetName.replace("%data%", "" + data);
	}
	
	
	public boolean sendParticle(int radius) {
		List<Client> clients = new ArrayList<Client>();
		for(Entity entity : location.getWorld().getEntities())
			if(entity instanceof Player && entity.getLocation().distance(location) < radius)
    				clients.add(Client.getClient((Player) entity));
		return false;
	}

	public String getPacketName(){
		return this.packetName;
	}

	public ParticleType getType(){
		return this.type;
	}

	public Location getLocation(){
		return this.location;
	}

	public int getId(){
		return this.id;
	}

	public int getData(){
		return this.data;
	}

	public int getSpeed(){
		return this.speed;
	}

	public int getCount(){
		return this.count;
	}

	public void setLocation(Location location){
		this.location=location;
	}

	public void setSpeed(int speed){
		this.speed=speed;
	}

	public void setCount(int count){
		this.count=count;
	}


	/**
	 * Reflection to set the values of the packet
	 * @param instance
	 * @param fieldName
	 * @param value
	 * @throws Exception
	 */
	/*private static void setValue(Object instance, String fieldName, Object value) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, value);
	}*/
	
}
