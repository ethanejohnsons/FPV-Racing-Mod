package com.bluevista.fpvracing.client.events;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.client.math.QuaternionHelper;
import com.bluevista.fpvracing.server.entities.DroneEntity;
import com.bluevista.fpvracing.server.entities.ViewHandler;
import com.bluevista.fpvracing.server.items.ItemGoggles;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(FPVRacingMod.MODID)
@OnlyIn(Dist.CLIENT)
public class RenderEvents {

	public static Minecraft mc = Minecraft.getInstance();
	public static DroneEntity currentDrone;
	public static ViewHandler view;
	public static Vec3d playerPos;

	@SubscribeEvent
	public static void on(final RenderHandEvent event) {
		/*
		 * If the player is currently flying a drone (i.e. looking through a ViewHandler object),
		 * the hand of the player is not rendered.
		 */
		if(mc.getRenderViewEntity() instanceof ViewHandler) event.setCanceled(true);
	}

	@SubscribeEvent
	public static void on(final TickEvent.RenderTickEvent event) {
		if (mc.player != null) {
			if (mc.player.getHeldItemMainhand().getItem() instanceof ItemGoggles) { // if the player is holding the goggles
				if (view != null) {
					view.clientTick(event.renderTickTime); // ...update the ViewHandler...
//					mc.player.move(MoverType.PLAYER, new Vec3d(playerPos.x - (mc.player.getPositionVec()).x, playerPos.y - (mc.player.getPositionVec()).y, playerPos.z - (mc.player.getPositionVec()).z));
				}

				if (!(mc.getRenderViewEntity() instanceof ViewHandler)) { // ...and if a ViewHandler doesn't exist, create one
					currentDrone = DroneEntity.getNearestTo(mc.player);
					if(currentDrone != null) {
						playerPos = mc.player.getPositionVec();
						view = new ViewHandler(mc.world, currentDrone);
						mc.setRenderViewEntity(view);
					}
				}
			} else if(mc.getRenderViewEntity() instanceof ViewHandler) {
				view = null;
				mc.setRenderViewEntity(mc.player); // switch back to player
				mc.player.setPosition(playerPos.x, playerPos.y, playerPos.z);
			}
		}
	}

	@SubscribeEvent
	public static void on(final EntityViewRenderEvent.CameraSetup event) {
		Entity currentViewEntity = mc.getRenderViewEntity();
		if(currentViewEntity instanceof ViewHandler) {
			if (((ViewHandler) currentViewEntity).getTarget() instanceof DroneEntity) {
				DroneEntity drone = (DroneEntity) ((ViewHandler) currentViewEntity).getTarget();
				QuaternionHelper.applyRotQuat(drone.getOrientation());
			}
		}
	}

	public static boolean isPlayerViewingDrone() {
		return view != null;
	}

	public static ClientPlayerEntity getPlayer() {
		return mc.player;
	}
}
