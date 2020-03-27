package com.bluevista.fpvracing.client.events;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.server.entities.DroneEntity;
import com.bluevista.fpvracing.server.entities.ViewHandler;
import com.bluevista.fpvracing.server.items.ItemGoggles;
import com.bluevista.fpvracing.server.math.QuaternionHelper;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.registries.ObjectHolder;
import org.lwjgl.opengl.GL11;

@ObjectHolder(FPVRacingMod.MODID)
@OnlyIn(Dist.CLIENT)
public class RenderEvents {

	public static DroneEntity currentDrone;
	public static ViewHandler view;

	@SubscribeEvent
	public static void onRenderHand(final RenderHandEvent event) {
		/*
		 * If the player is currently flying a drone (i.e. looking through a ViewHandler object),
		 * the hand of the player is not rendered.
		 */
		if(Minecraft.getInstance().getRenderViewEntity() instanceof ViewHandler) event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRenderTick(final TickEvent.RenderTickEvent event) {
		Minecraft mc = Minecraft.getInstance();

		if (mc.player != null) {
			if (mc.player.getHeldItemMainhand().getItem() instanceof ItemGoggles &&
					DroneEntity.getNearestDroneTo(mc.player) != null) { // if the player is holding goggles...

				if(view != null) view.clientTick(event.renderTickTime); // ...update the ViewHandler...

				if (!(mc.getRenderViewEntity() instanceof ViewHandler)) { // ...and if a ViewHandler doesn't exist, create one
					currentDrone = DroneEntity.getNearestDroneTo(mc.player);
					view = new ViewHandler(mc.world, currentDrone);
					mc.setRenderViewEntity(view);
				}

			} else if(mc.getRenderViewEntity() instanceof ViewHandler) {
				view = null;
				mc.setRenderViewEntity(mc.player); // switch back to player
			}
		}
	}
	
	/*
	 * If the player's view is on a ViewHandler and the ViewHandler's
	 * target is an instance of EntityDrone, the sketchy quaternion screen 
	 * rotation code is used.
	 */
	@SubscribeEvent
	public static void cameraUpdate(final EntityViewRenderEvent.CameraSetup event) {
		Entity currentViewEntity = Minecraft.getInstance().getRenderViewEntity();
		if(currentViewEntity instanceof ViewHandler) {
			if(((ViewHandler)currentViewEntity).getTarget() instanceof DroneEntity) {
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
}
