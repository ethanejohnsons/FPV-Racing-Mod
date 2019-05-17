package com.bluevista.fpvracing.handler;

import org.lwjgl.opengl.GL11;

import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.entities.ViewHandler;
import com.bluevista.fpvracing.math.QuaternionHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CameraHandler {
	
//	public static Entity nextTarget;
	
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		if(Minecraft.getMinecraft().getRenderViewEntity() instanceof ViewHandler)
			event.setCanceled(true); // No more hand :)
	}
	
	@SubscribeEvent
	public static void tick(TickEvent.RenderTickEvent event) {
//		if(nextTarget != null) {
//			Minecraft mc = FMLClientHandler.instance().getClient();
//			setNewView(mc.world, mc.player, nextTarget);
//			nextTarget = null;
//		}
	}
	
	/*
	 * If the player's view is on a ViewHandler and the ViewHandler's
	 * target is an instance of EntityDrone, the sketchy quaternion screen 
	 * rotation code is used.
	 */
	@SubscribeEvent
	public static void cameraUpdate(EntityViewRenderEvent.CameraSetup event) {		
		if(event.getEntity() instanceof ViewHandler) {
			if(((ViewHandler)event.getEntity()).getTarget() instanceof EntityDrone) {
				EntityDrone drone = (EntityDrone)((ViewHandler)event.getEntity()).getTarget();
				GL11.glMultMatrix(
						QuaternionHelper.toBuffer(
					    QuaternionHelper.quatToMatrix(
					    		drone.getOrientation()))); // Applies to screen
			}
		} else {
			GL11.glRotated(0, 1f, 1f, 1f);
		}
	}
	
	/*
	 * Creates a new ViewHandler that is attached to the
	 * given target entity. The player's view is then transferred
	 * to the ViewHandler's.
	 */
	public static void setNewView(World world, EntityPlayer player, Entity target) {
		ViewHandler view = new ViewHandler(world, target);
		view.setLocationAndAngles(target.posX, target.posY, target.posZ, 0, 0);
				
    	world.spawnEntity(view);
	    
	    Minecraft.getMinecraft().setRenderViewEntity(view);
	    Minecraft.getMinecraft().gameSettings.hideGUI = true;
	}
	
	/*
	 * Returns the view back to the original player
	 */
	public static void returnView() {
		Minecraft.getMinecraft().getRenderViewEntity().setDead();
	    Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player);
		Minecraft.getMinecraft().gameSettings.hideGUI = false;
	}
	
	public static void spawnViewHandler(World world, ViewHandler view) {
        int i = MathHelper.floor(view.posX / 16.0D);
        int j = MathHelper.floor(view.posZ / 16.0D);

        world.getChunkFromChunkCoords(i, j).addEntity(view);
        world.loadedEntityList.add(view);
        world.onEntityAdded(view);
    }
}
