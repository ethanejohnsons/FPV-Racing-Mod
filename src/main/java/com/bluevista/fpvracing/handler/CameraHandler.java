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
		
		if(event.getEntity() instanceof EntityDrone) {
			EntityDrone drone = (EntityDrone) event.getEntity();	
			
			GL11.glMultMatrix(
					QuaternionHelper.toBuffer(
				    QuaternionHelper.quatToMatrix(
				    		drone.getOrientation()))); // Applies to screen
		} else {
			GL11.glRotated(0, 1f, 1f, 1f);
		}

//		EntityPlayer player = Minecraft.getMinecraft().player;
//		if(player.getHeldItemMainhand().getItem() instanceof ItemGoggles) {
//			if(event.getEntity() instanceof EntityPlayer) {
//				List<EntityDrone> drones = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityDrone.class, player.getEntityBoundingBox().expand(6, 6, 6));
//				
//				if(drones.size() > 0) {	
//					for(int i = 0; i < drones.size(); i++) {
//						if(((ItemGoggles) (player.getHeldItemMainhand().getItem())).getChannel() == drones.get(i).getChannel()) {
//							EntityDrone nearest = drones.get(i);
//							player.startRiding(nearest);
//	//						Minecraft.getMinecraft().setRenderViewEntity(nearest);
//							//Minecraft.getMinecraft().getRenderViewEntity().setPositionAndRotation(nearest.posX, nearest.posY, nearest.posZ, 0, 0);
//							break;
//						}
//					}
//					
//				}	
//			}
//		} else if(player.getRidingEntity() instanceof EntityDrone) {
//			player.dismountRidingEntity();
//		} //else if(event.getEntity() instanceof EntityDrone) {
////			Minecraft.getMinecraft().setRenderViewEntity(player);
////			//Minecraft.getMinecraft().getRenderViewEntity().setPositionAndRotation(player.posX, player.posY, player.posZ, 0, 0);
////		}
		
		
	}
}
