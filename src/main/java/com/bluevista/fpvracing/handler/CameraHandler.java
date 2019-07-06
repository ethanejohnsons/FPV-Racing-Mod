package com.bluevista.fpvracing.handler;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.entities.ViewHandler;
import com.bluevista.fpvracing.math.QuaternionHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CameraHandler {
	
	private ViewHandler viewEntity;
	private static Entity target;
	
	@SubscribeEvent
	public void onRenderHand(RenderHandEvent event) {
		if(Minecraft.getInstance().getRenderViewEntity() instanceof ViewHandler)
			event.setCanceled(true); // no more hand
	}
	
	/*
	 * If the player's view is on a ViewHandler and the ViewHandler's
	 * target is an instance of EntityDrone, the sketchy quaternion screen 
	 * rotation code is used.
	 */
	@SubscribeEvent
	public void cameraUpdate(EntityViewRenderEvent.CameraSetup event) {
		Entity currentViewEntity = Minecraft.getInstance().getRenderViewEntity();
		if(currentViewEntity instanceof ViewHandler) {
			if( ((ViewHandler)currentViewEntity).getTarget() instanceof EntityDrone ) {
				EntityDrone drone = (EntityDrone)((ViewHandler)currentViewEntity).getTarget();
				GL11.glMultMatrixf(
				   QuaternionHelper.toBuffer(
				   QuaternionHelper.quatToMatrix(
				      drone.getOrientation()))); // Applies to screen
			}
		} else {
			GL11.glRotated(0, 1f, 1f, 1f);
		}
	}
	
	@SubscribeEvent
	public void tick(TickEvent.RenderTickEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if(target == null && viewEntity != null) {
			System.out.println("Destroying ViewHandler..................................");
		    mc.setRenderViewEntity(mc.player);
			mc.gameSettings.hideGUI = false;
			viewEntity = null;
		} else if(target != null && viewEntity == null) {
			System.out.println("Creating ViewHandler....................................");
			viewEntity = new ViewHandler(mc.world, target);
			viewEntity.setLocationAndAngles(target.posX, target.posY, target.posZ, 0, 0);
					
			mc.world.addEntity(viewEntity);
	    	
	    	mc.setRenderViewEntity(viewEntity);
		    mc.gameSettings.hideGUI = true;
		    //mc.gameSettings.thirdPersonView = 0;
		}
	}
	
	public static void setTarget(Entity e) {
		target = e;
	}
	
	public static Entity getTarget() {
		return target;
	}
	
//	public static void spawnViewHandler(World world, ViewHandler view) {
//        int i = MathHelper.floor(view.posX / 16.0D);
//        int j = MathHelper.floor(view.posZ / 16.0D);
//
//        world.getChunkFromChunkCoords(i, j).addEntity(view);
//        world.loadedEntityList.add(view);
//        world.onEntityAdded(view);
//    }
}
