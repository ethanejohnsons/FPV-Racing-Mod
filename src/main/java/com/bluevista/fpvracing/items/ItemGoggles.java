package com.bluevista.fpvracing.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemGoggles extends Item {

	private int channel;
	
	public ItemGoggles() {
		super();
		this.setUnlocalizedName("drone_goggles");
		this.setRegistryName("drone_goggles");
		this.setCreativeTab(CreativeTabs.COMBAT); 
	
		this.channel = 0;
	}
	
	public int getChannel() {
		return channel;
	}
	
	public void setChannel(int channel) {
		this.channel = channel;
	}
	
}

