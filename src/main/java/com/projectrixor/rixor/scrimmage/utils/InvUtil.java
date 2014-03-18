package com.projectrixor.rixor.scrimmage.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InvUtil {
 
    public static Inventory obsInvPreview(final Player player, final PlayerInventory inventory){
        if (player == null) {
            return null;
        }
        final Player holder = (Player)inventory.getHolder();
        final Inventory preview = Bukkit.getServer().createInventory((InventoryHolder)player, 45, holder.getDisplayName());
        
        for (int i = 0; i <= 35; ++i) {
            preview.setItem(getInventoryPreviewSlot(i), inventory.getItem(i));
        }
        preview.setItem(7, new ItemStack(Material.SPECKLED_MELON, holder.getFoodLevel(), (short) 59));
        preview.setItem(8, new ItemStack(Material.POTION, (int) holder.getHealth(), (short) 16389));
        preview.setItem(0, inventory.getHelmet());
        preview.setItem(1, inventory.getChestplate());
        preview.setItem(2, inventory.getLeggings());
        preview.setItem(3, inventory.getBoots());
        return preview;
    }
    
    public static int getInventoryPreviewSlot(final int inventorySlot) {
        if (inventorySlot < 9) {
            return inventorySlot + 36;
        }
        if (inventorySlot < 36) {
            return inventorySlot;
        }
        return inventorySlot;
        }
	
}
