package com.projectrixor.rixor.scrimmage.map.kit;

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ItemKit {
	
	List<ItemKit> parents;
	
	String name;
	List<ItemSlot> items;
	List<PotionEffect> effects;
	
	public ItemKit(String name, List<ItemSlot> items, List<ItemKit> parents) {
		this(name, items, parents, new ArrayList<PotionEffect>());
	}
	
	public ItemKit(String name, List<ItemSlot> items, List<ItemKit> parents, List<PotionEffect> effects) {
		this.parents = parents;
		this.name = name;
		this.items = items;
		this.effects = effects;
	}
	
	public void load(Client client) {
		for(ItemKit parent : parents)
			parent.load(client);
		
		for(ItemSlot slot : items)
			slot.give(client);
		
		for(PotionEffect effect : effects)
			client.getPlayer().addPotionEffect(effect);
	}

	public List<ItemKit> getParents(){
		return this.parents;
	}

	public String getName(){
		return this.name;
	}

	public List<ItemSlot> getItems(){
		return this.items;
	}

	public List<PotionEffect> getEffects(){
		return this.effects;
	}
}
