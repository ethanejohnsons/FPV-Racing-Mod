package com.bluevista.fpvracing.handler;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.items.ItemDrone;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid=FPVRacingMod.MODID)
public class ModEventHandler {
				
	/*
	 * When the player right clicks the ground or any block
	 * with the drone item, a drone will spawn with a ViewHandler
	 * attached to it.
	 */
	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) {
		if(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemDrone && !event.getWorld().isRemote && event.getHand() == event.getEntityPlayer().getActiveHand()) {
			EntityDrone drone = new EntityDrone(event.getWorld());
			drone.setLocationAndAngles(event.getPos().getX(), event.getPos().getY()+1, event.getPos().getZ(), 0, 0);
	    	event.getWorld().addEntity(drone);
		}
	}


//  Unused event
//
//	@SubscribeEvent
//	public static void onRightClick(RightClickItem event) {
//		if(event.getItemStack().getItem() instanceof ItemGoggles) {
//
//		}
//	}

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		if(FPVRacingMod.unmount.isPressed()) { // the get off button
			EntityDrone.stopUsing();
		}
//		if(FPVRacingMod.drone_toggle.isPressed()) {
//			DroneHelper.isPlayerDrone = !DroneHelper.isPlayerDrone;
//			DroneHelper.init();
//			Minecraft.getMinecraft().player.setNoGravity(!Minecraft.getMinecraft().player.hasNoGravity());
//		}
	}
}