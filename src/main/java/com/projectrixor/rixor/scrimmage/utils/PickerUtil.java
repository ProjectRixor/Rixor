package com.projectrixor.rixor.scrimmage.utils;

import java.util.Arrays;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import com.projectrixor.rixor.scrimmage.map.MapTeam;
import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.match.Match;

public class PickerUtil {
	@Getter Map map;
    public static Inventory obsInvPreview(String string) {
    	final Inventory preview = Bukkit.getServer().createInventory(null, 27, ChatColor.RED + string);
    	
    	//Setup Aesthetic Team Picker Items ( I'm so inefficient :( )
    	preview.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE));
    	
    	preview.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(2, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(4, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(6, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(9, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(17, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(19, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(20, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(21, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(22, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(23, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(24, new ItemStack(Material.STAINED_GLASS_PANE));
    	preview.setItem(25, new ItemStack(Material.STAINED_GLASS_PANE));
    	
    	//Setup Observers Wool
    	Wool obs = new Wool(DyeColor.LIGHT_BLUE);
    	ItemStack obsItem = obs.toItemStack(Scrimmage.getMap().getObservers().getTeam().getPlayers().size());
    	ItemMeta obsItemMeta = obsItem.getItemMeta();
    	obsItemMeta.setDisplayName(ChatColor.AQUA + "Observers");
    	obsItemMeta.setLore(Arrays.asList(ChatColor.BLUE + "Join the" + ChatColor.AQUA + " Observers" + ChatColor.BLUE + "!"));
    	obsItem.setItemMeta(obsItemMeta);
    	
    	preview.setItem(26, obsItem);
    	
    	//Setup Eye of Ender Close Item
    	ItemStack closer = new ItemStack(Material.EYE_OF_ENDER);
    	ItemMeta closerMeta = closer.getItemMeta();
    	closerMeta.setDisplayName(ChatColor.RED + "Close");
    	closerMeta.setLore(Arrays.asList(ChatColor.BLUE + "Close the picker!"));
    	closer.setItemMeta(closerMeta);
    	
    	preview.setItem(18, closer);
    	
    	for (MapTeam teams : Scrimmage.getMap().getTeams()) {
    		int i = 10;
    		if (i >= 7) {
    		DyeColor dye = DyeColor.WHITE;
    		dye = ConversionUtil.convertTeamColorToDyeColor(teams);
        	Wool wool = new Wool(dye);
        	int size = 0;
        	if (teams.getTeam().getSize() > 64) {
        		size = 64;
        	} else {
        		size = teams.getTeam().getSize();
        	}
        	ItemStack stack = wool.toItemStack(size);
        	
        	ItemMeta meta = stack.getItemMeta();
        	meta.setDisplayName(teams.getColor() + teams.getName());
        	meta.setLore(Arrays.asList(ChatColor.GREEN + "Join the" + teams.getColor() + " " + teams.getDisplayName() + ChatColor.GREEN + "!"));
        	stack.setItemMeta(meta);
        	//preview.addItem(stack);
        	preview.addItem(stack);
        	i = i + 1;
    		}
    	}
    	return preview;
    }	
}
