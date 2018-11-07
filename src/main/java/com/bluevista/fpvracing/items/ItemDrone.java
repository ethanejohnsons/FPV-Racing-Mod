package com.bluevista.fpvracing.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemDrone extends Item {

	public ItemDrone() {
		super();
		this.setUnlocalizedName("drone_spawner");
		this.setRegistryName("drone_spawner");
		this.setCreativeTab(CreativeTabs.COMBAT); 
	}
	
}

