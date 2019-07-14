package com.bluevista.fpvracing.events;

import com.bluevista.fpvracing.FPVRacingMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.bluevista.fpvracing.entities.DroneEntity;
import com.bluevista.fpvracing.entities.ViewHandler;
import net.minecraft.entity.Entity;

import com.bluevista.fpvracing.math.QuaternionHelper;
import org.lwjgl.opengl.GL11;


@Mod.EventBusSubscriber(modid = FPVRacingMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CameraEvents {
	
	private static ViewHandler viewEntity;
	private static Entity target;
	
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		if(Minecraft.getInstance().getRenderViewEntity() instanceof ViewHandler)
			event.setCanceled(true); // no more hand
	}
	
	/*
	 * If the player's view is on a ViewHandler and the ViewHandler's
	 * target is an instance of EntityDrone, the sketchy quaternion screen 
	 * rotation code is used.
	 */
	@SubscribeEvent
	public static void cameraUpdate(EntityViewRenderEvent.CameraSetup event) { // TODO make separate funcitnon to find out if player is using drone
		Entity currentViewEntity = Minecraft.getInstance().getRenderViewEntity();
		if(currentViewEntity instanceof ViewHandler) {
			if( ((ViewHandler)currentViewEntity).getTarget() instanceof DroneEntity) {
				DroneEntity drone = (DroneEntity)((ViewHandler)currentViewEntity).getTarget();
				GL11.glMultMatrixf(
				   QuaternionHelper.toBuffer(
				   QuaternionHelper.quatToMatrix(
				      drone.getOrientation()))); // Applies to screen
			}
		} else {
			GL11.glRotated(0, 1f, 1f, 1f);
		}
	}
	
//	@SubscribeEvent
//	public static void tick(TickEvent.RenderTickEvent event) {
//		Minecraft mc = Minecraft.getInstance();
//		if(target == null && viewEntity != null) {
//			System.out.println("Destroying ViewHandler..................................");
//		    mc.setRenderViewEntity(mc.player);
//			mc.gameSettings.hideGUI = false;
//			viewEntity = null;
//		} else if(target != null && viewEntity == null) {
//			System.out.println("Creating ViewHandler....................................");
//			viewEntity = new ViewHandler(mc.world, target);
//			viewEntity.setLocationAndAngles(target.posX, target.posY, target.posZ, 0, 0);
//
//			mc.world.addEntity(viewEntity);
//
//	    	mc.setRenderViewEntity(viewEntity);
//		    mc.gameSettings.hideGUI = true;
//		    mc.gameSettings.thirdPersonView = 0;
//		}
//	}
	
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
