package com.bluevista.fpvracing.server.items;

import com.bluevista.fpvracing.server.EntityRegistry;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DroneSpawnerItem extends Item {
	public DroneSpawnerItem(Item.Properties builder) {
		super(builder);
	}

	/**
	 * Called when this item is used while targeting a Block
	 */
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		if (!world.isRemote) {
			BlockPos pos = context.getPos().add(0, 1, 0);
			EntityRegistry.DRONE.spawn(world, context.getItem(), context.getPlayer(), pos, SpawnReason.SPAWNER, false, false);
//			this.handleClick(playerentity, world.getBlockState(blockpos), world, blockpos, true, context.getItem());
		}

		return ActionResultType.SUCCESS;
	}
	
}

