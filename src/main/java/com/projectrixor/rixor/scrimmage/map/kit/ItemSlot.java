package com.projectrixor.rixor.scrimmage.map.kit;

import com.projectrixor.rixor.scrimmage.player.Client;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;

public class ItemSlot {
	
	@Getter int slot;
	@Getter ItemStack item;
	
	public ItemSlot(int slot, ItemStack item) {
		this.slot = slot;
		this.item = item;
	}
	
	public boolean give(Client client) {
		/*
		 * Helmet: -1
		 * Chestplate: -2
		 * Leggings: -3
		 * Boots: -4
		 * 
		 * Seems like this system should work pretty effectively, right?
		 */
		
		if(slot == -1) {
			client.getPlayer().getInventory().setHelmet(item);
			return true;
		} else if(slot == -2) {
			client.getPlayer().getInventory().setChestplate(item);
			return true;
		} else if(slot == -3) {
			client.getPlayer().getInventory().setLeggings(item);
			return true;
		} else if(slot == -4) {
			client.getPlayer().getInventory().setBoots(item);
			return true;
		}
		
		if(slot >= 0 && slot <= 35) {
			client.getPlayer().getInventory().setItem(slot, item);
			return true;
		}
		
		return false;
	}
	
}
