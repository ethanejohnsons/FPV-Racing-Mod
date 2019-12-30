package com.bluevista.fpvracing.client.events;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.server.entities.DroneEntity;
import com.bluevista.fpvracing.server.items.GogglesItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import com.bluevista.fpvracing.server.entities.ViewHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(FPVRacingMod.MODID)
@OnlyIn(Dist.CLIENT)
public class CameraEvents {

	static DroneEntity currentDrone;

	@SubscribeEvent
	public static void onRenderHand(final RenderHandEvent event) {
		/*
		 * If the player is currently flying a drone (i.e. looking through a ViewHandler object),
		 * the hand of the player is not rendered.
		 */
		if(Minecraft.getInstance().getRenderViewEntity() instanceof DroneEntity) event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRenderTick(final TickEvent.RenderTickEvent event) {
		Minecraft mc = Minecraft.getInstance();

		if (mc.player != null) {
			if (mc.player.getHeldItemMainhand().getItem() instanceof GogglesItem) {
				if (!(mc.getRenderViewEntity() instanceof DroneEntity)) {
					currentDrone = DroneEntity.getNearestDroneTo(mc.player);
					currentDrone.createNewView();
				}
			} else if(mc.getRenderViewEntity() instanceof DroneEntity) {
				mc.setRenderViewEntity(mc.player);
			}
		}

		if(currentDrone != null) {
			currentDrone.playerTick();
		}
	}
	
	/*
	 * If the player's view is on a ViewHandler and the ViewHandler's
	 * target is an instance of EntityDrone, the sketchy quaternion screen 
	 * rotation code is used.
	 */
	@SubscribeEvent
	public static void cameraUpdate(final EntityViewRenderEvent.CameraSetup event) { // TODO make separate funcitnon to find out if player is using drone
//		Entity currentViewEntity = Minecraft.getInstance().getRenderViewEntity();
//		if(currentViewEntity instanceof ViewHandler) {
//			if(((ViewHandler)currentViewEntity).getTarget() instanceof DroneEntity) {
//				DroneEntity drone = (DroneEntity)((ViewHandler)currentViewEntity).getTarget();
//				GL11.glMultMatrixf(
//				   QuaternionHelper.toBuffer(
//				   QuaternionHelper.quatToMatrix(
//				      drone.getOrientation()))); // Applies to screen
//			}
//		} else {
//			GL11.glRotated(0, 1f, 1f, 1f);
//		}
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
	
//	public static void spawnViewHandler(World world, ViewHandler view) {
//        int i = MathHelper.floor(view.posX / 16.0D);
//        int j = MathHelper.floor(view.posZ / 16.0D);
//
//        world.getChunkFromChunkCoords(i, j).addEntity(view);
//        world.loadedEntityList.add(view);
//        world.onEntityAdded(view);
//    }
}
