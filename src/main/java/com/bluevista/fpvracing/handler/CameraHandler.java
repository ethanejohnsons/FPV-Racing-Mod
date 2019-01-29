package com.bluevista.fpvracing.handler;

import org.lwjgl.opengl.GL11;

import com.bluevista.fpvracing.DroneHelper;
import com.bluevista.fpvracing.math.QuaternionHelper;

import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CameraHandler {
	
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		if(DroneHelper.isPlayerDrone)
			event.setCanceled(true); // No more hand :)
	}
	
	@SubscribeEvent
	public static void cameraUpdate(EntityViewRenderEvent.CameraSetup event) {				
		if(DroneHelper.isPlayerDrone) {
			GL11.glMultMatrix(
					QuaternionHelper.toBuffer(
				    QuaternionHelper.quatToMatrix(DroneHelper.orientation))); // Applies to screen
		} else {
			GL11.glRotated(0, 1f, 1f, 1f);
		}
	}
}
