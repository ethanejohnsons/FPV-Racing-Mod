package com.bluevista.fpvracing.items;

import com.bluevista.fpvracing.entities.DroneEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DroneItem extends Item {

	public DroneItem() {
		super(new Item.Properties());
		this.setRegistryName("drone");
	}

	/**
	 * Called when this item is used when targeting a Block
	 */
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity playerentity = context.getPlayer();
		World world = context.getWorld();
		if (!world.isRemote && playerentity != null) {
			BlockPos blockpos = context.getPos();

			DroneEntity drone = new DroneEntity(world);
			drone.setLocationAndAngles(blockpos.getX(), blockpos.getY()+1, blockpos.getZ(), 0, 0);
			drone.rotationYaw = playerentity.rotationYaw;
			world.addEntity(drone);

//			this.handleClick(playerentity, world.getBlockState(blockpos), world, blockpos, true, context.getItem());
		}

		return ActionResultType.SUCCESS;
	}
	
}

