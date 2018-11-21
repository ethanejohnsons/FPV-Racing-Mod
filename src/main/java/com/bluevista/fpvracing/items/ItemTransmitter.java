package com.bluevista.fpvracing.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemTransmitter extends Item {
	
	public ItemTransmitter() {
		super();
		this.setUnlocalizedName("drone_transmitter");
		this.setRegistryName("drone_transmitter");
		this.setCreativeTab(CreativeTabs.COMBAT); 
	}
	
}

