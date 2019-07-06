package com.bluevista.fpvracing.items;

//import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemDrone extends Item {

	public ItemDrone() {
		super(new Item.Properties());
//		this.setUnlocalizedName("drone_spawner");
		this.setRegistryName("drone_spawner");
//		this.setCreativeTab(CreativeTabs.COMBAT); TODO figure out creative tabs
	}
	
}

