package com.bluevista.fpvracing;

import org.lwjgl.opengl.GL11;

import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.items.ItemDrone;
import com.bluevista.fpvracing.math.QuaternionHelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

@Mod.EventBusSubscriber(modid=FPVRacingMod.MODID)
public class ModEventHandler {
				
	@SubscribeEvent
	public static void onRightClick(RightClickBlock event) {
		if(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemDrone) {
			EntityDrone drone = new EntityDrone(event.getWorld());
		    drone.setLocationAndAngles(event.getPos().getX(), event.getPos().getY()+1, event.getPos().getZ(), 0, 0);
		    if (!event.getWorld().isRemote) {
	            event.getWorld().spawnEntity(drone);
	        }
		}
		
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
		if(FPVRacingMod.leaveDrone.isKeyDown()) {
			// Nothin'
		}
	}
	
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		if(Minecraft.getMinecraft().player.getRidingEntity() instanceof EntityDrone) {
			event.setCanceled(true); // No more hand :)
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
	
//	@SubscribeEvent
//	public static void onEntityInteract(EntityInteract event) {
//		if(event.getTarget() instanceof EntityDrone) {
//			//((EntityDrone) event.getTarget()).connect();
//			event.getEntityPlayer().startRiding(event.getTarget());
//		}
//		System.out.println("INTERACTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//	}
   
}