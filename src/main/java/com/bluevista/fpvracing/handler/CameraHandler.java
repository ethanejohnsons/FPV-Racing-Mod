package com.bluevista.fpvracing.handler;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.items.ItemGoggles;
import com.bluevista.fpvracing.math.QuaternionHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CameraHandler {

	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		if(Minecraft.getMinecraft().player.getRidingEntity() instanceof EntityDrone)
			event.setCanceled(true); // No more hand :)
		
		if(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemGoggles) {
			if(((ItemGoggles) (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem())).getChannel() == 0) {
				EntityPlayer player = Minecraft.getMinecraft().player;
				
				List<EntityDrone> drones = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityDrone.class, player.getEntityBoundingBox().expand(3, 3, 3));
				
				EntityDrone nearest = drones.get(0);
				Minecraft.getMinecraft().setRenderViewEntity(nearest);
	     		Minecraft.getMinecraft().getRenderViewEntity().setPositionAndRotation(nearest.posX, nearest.posY, nearest.posZ, 0, 0);
			}
		}
	}
	
	@SubscribeEvent
	public static void cameraUpdate(EntityViewRenderEvent.CameraSetup event) {
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
