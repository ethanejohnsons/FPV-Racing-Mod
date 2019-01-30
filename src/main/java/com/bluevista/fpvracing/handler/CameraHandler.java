package com.bluevista.fpvracing.handler;

import org.lwjgl.opengl.GL11;

import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.math.QuaternionHelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CameraHandler {
	
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		if(Minecraft.getMinecraft().player.getRidingEntity() instanceof EntityDrone)
			event.setCanceled(true); // No more hand :)
	}
	
	@SubscribeEvent
	public static void cameraUpdate(EntityViewRenderEvent.CameraSetup event) {				
//		if(DroneHelper.isPlayerDrone) {
		if(event.getEntity() instanceof EntityDrone) {
			EntityDrone drone = (EntityDrone) event.getEntity();	
			GL11.glMultMatrix(
					QuaternionHelper.toBuffer(
				    QuaternionHelper.quatToMatrix(
				    		drone.getOrientation()))); // Applies to screen
		} else {
			GL11.glRotated(0, 1f, 1f, 1f);
		}
	}
}
