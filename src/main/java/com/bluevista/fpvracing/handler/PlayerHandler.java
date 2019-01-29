package com.bluevista.fpvracing.handler;

import com.bluevista.fpvracing.DroneHelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerHandler {
		
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if(DroneHelper.isPlayerDrone) {
			DroneHelper.update(event.player);
			event.player.setNoGravity(true);
		} else {
			event.player.setNoGravity(false);
		}
	}
	
	@SubscribeEvent
	public static void mouseEvent(MouseEvent event) {
		
		// Prevents mouse movement while the player is a drone
		if(DroneHelper.isPlayerDrone) {
			Minecraft.getMinecraft().player.rotationPitch = 0;
			Minecraft.getMinecraft().player.rotationYaw = 0;
			System.out.println("canceling");
		}
		
	}
	
}
