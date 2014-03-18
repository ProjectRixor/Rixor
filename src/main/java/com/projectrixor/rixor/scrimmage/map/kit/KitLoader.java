package com.projectrixor.rixor.scrimmage.map.kit;

import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.ServerLog;
import com.projectrixor.rixor.scrimmage.map.MapLoader;
import com.projectrixor.rixor.scrimmage.utils.ConversionUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.dom4j.Element;

import lombok.Getter;
import com.projectrixor.rixor.scrimmage.map.Map;

public class KitLoader {
	
	@Getter Map map;
	@Getter Element element;
	
	public KitLoader(Map map, Element element) {
		this.map = map;
		this.element = element;
	}
	
	public ItemKit load() {
		String name = this.element.attributeValue("name").replaceAll(" ", "_");
		List<ItemSlot> slots = new ArrayList<ItemSlot>();
		List<ItemKit> parents = new ArrayList<ItemKit>();
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		
		String[] types = new String[]{"item", "helmet", "chestplate", "leggings", "boots"};
		for(String search : types)
			for(Element element : MapLoader.getElements(this.element,search)) {
				ItemSlot slot = compileItem(element, search);
				if(slot != null)
					slots.add(slot);
			}
		
		String sparents = this.element.attributeValue("parents");
		if(sparents != null) {
			String[] values = sparents.split(" ");
			for(String parent : values)
				if(getMap().getKit(parent) != null)
					parents.add(getMap().getKit(parent));
		}
		
		/*
		 * Load Potion Effects!
		 * <potion duration="5" amplifier="1">heal</potion>
		 */
		
		for(Element element : MapLoader.getElements(this.element, "potion")) {
			PotionEffectType type = null;
			int duration = 0;
			int amplifier = 0;
			
			try {
				if(element.attributeValue("duration").equalsIgnoreCase("oo"))
					duration = Integer.MAX_VALUE;
				else duration = Integer.parseInt(element.attributeValue("duration"));
			} catch(Exception e) {
				duration = -1;
			}
			
			try {
				if(element.attributeValue("duration").equalsIgnoreCase("oo"))
					duration = Integer.MAX_VALUE;
				else duration = Integer.parseInt(element.attributeValue("duration"));
			} catch(Exception e) {
				// ignore, use default amplifier
			}
			
			type = ConversionUtil.convertStringToPotionEffectType(element.getText());
			
			if(duration > 0 && type != null)
				new PotionEffect(type, duration, amplifier);
		}
		
		if(effects.size() == 0)
			return new ItemKit(name, slots, parents);
		else return new ItemKit(name, slots, parents, effects);
	}
	
	public ItemSlot compileItem(Element element, String name) {
		/*
		 * Load the Items with their Item Slawt and sheeeeeeeeet!
		 * Example: <item slot="0">iron sword</item>
		 */
		
		try {
			int slot = 0;
			if(name.equalsIgnoreCase("item")) slot = Integer.parseInt(element.attributeValue("slot"));
			else if(name.equalsIgnoreCase("helmet")) slot = -1;
			else if(name.equalsIgnoreCase("chestplate")) slot = -2;
			else if(name.equalsIgnoreCase("leggings")) slot = -3;
			else if(name.equalsIgnoreCase("boots")) slot = -4;
			
			Material material = ConversionUtil.convertStringToMaterial(element.getText());
			ItemStack stack = new ItemStack(material, ConversionUtil.convertStringToInteger(element.attributeValue("amount"), 1));
			
			if(element.attributeValue("damage") != null)
				try { stack.setDurability(Short.parseShort(element.attributeValue("damage"))); } catch(NumberFormatException e) { }
			
			if(element.attributeValue("name") != null) {
				String display = element.attributeValue("name").replaceAll("`", "§");
				ItemMeta meta = stack.getItemMeta();
				meta.setDisplayName(display);
				stack.setItemMeta(meta);
			}
			
			if(element.attributeValue("lore") != null) {
				String lS = element.attributeValue("lore");
				List<String> loreStrings = new ArrayList<String>();
				
				if(!lS.contains("|")) loreStrings.add(lS);
				else
					for(String lore : lS.split("|"))
						loreStrings.add(lore);
				
				List<String> lore = new ArrayList<String>();
				for(String loreItem : loreStrings)
					lore.add(loreItem.replaceAll("`", "§"));
				
				ItemMeta meta = stack.getItemMeta();
				meta.setLore(lore);
				stack.setItemMeta(meta);
			}
			
			String enString = element.attributeValue("enchantment");
			if(enString != null) {
				List<String> enchParse = new ArrayList<String>();
				if(enString.contains(";"))
					for(String enchant : enString.split(";"))
						enchParse.add(enchant);
				else enchParse.add(enString);
				
				for(String enchant : enchParse) {
					String title = "";
					int level = 1;
					if(enchant.contains(":")) {
						title = enchant.split(":")[0];
						
						try {
							level = Integer.parseInt(enchant.split(":")[0]);
						} catch(NumberFormatException e) {
							// ignore, leave level as 1.
						}
					}
					
					Enchantment enchantment = ConversionUtil.convertStringToEnchantment(title);
					if(enchantment != null) stack.addEnchantment(enchantment, level);
				}
			}
			
			if(isLeather(material) && element.attributeValue("color") != null) {
				String colorString = element.attributeValue("color");
				if(!colorString.startsWith("#")) colorString = "#" + colorString;
				java.awt.Color awt = ConversionUtil.convertHexStringToColor(colorString);
				Color color = Color.fromRGB(awt.getRed(), awt.getGreen(), awt.getBlue());
				LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
				meta.setColor(color);
				stack.setItemMeta(meta);
			}

			if(material == Material.AIR)
				ServerLog.info("Failed to load an item because '"+element.getText()+"' does not exist (or the item was air)!");
			else return new ItemSlot(slot, stack);
		} catch(Exception e) {
			ServerLog.info("Failed to load an item because it threw an exception");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isLeather(Material material) {
		return material == Material.LEATHER_BOOTS || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_HELMET;
	}
	
}
