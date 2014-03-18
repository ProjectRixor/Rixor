package com.projectrixor.rixor.scrimmage.utils;

/*import java.lang.reflect.Field;*/
import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;

public class Particle {
	
	@Getter String packetName;
	@Getter ParticleType type;
	@Getter @Setter Location location;
	
	@Getter int id;
	@Getter int data;

	@Getter @Setter int speed;	
	@Getter @Setter int count;
	
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
