package com.bluevista.fpvracing.client.events;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.client.math.QuaternionHelper;
import com.bluevista.fpvracing.server.entities.DroneEntity;
import com.bluevista.fpvracing.server.entities.ViewHandler;
import com.bluevista.fpvracing.server.items.ItemGoggles;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.Entity;
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

	public static DroneEntity currentDrone;
	public static ViewHandler view;

	@SubscribeEvent
	public static void on(final RenderHandEvent event) {
		/*
		 * If the player is currently flying a drone (i.e. looking through a ViewHandler object),
		 * the hand of the player is not rendered.
		 */
//		if(Minecraft.getInstance().getRenderViewEntity() instanceof ViewHandler) event.setCanceled(true);
	}

	@SubscribeEvent
	public static void on(final TickEvent.RenderTickEvent event) {
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

	@SubscribeEvent
	public static void on(final EntityViewRenderEvent.CameraSetup event) {
//		event.getRenderer().getActiveRenderInfo().func_227995_f_().multiply(new Quaternion(400, 400, 500, 400));
//		ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
//		Vec3d projectedView = renderInfo.getProjectedView();
//		GlStateManager.func_227689_c_(renderInfo.getPitch(), 1, 0, 0); // Fixes camera rotation.
//		GlStateManager.func_227689_c_(renderInfo.getYaw() + 180, 0, 1, 0); // Fixes camera rotation.
//		GlStateManager.func_227670_b_(-projectedView.x, -projectedView.y, -projectedView.z);
//		GlStateManager.func_227689_c_(60, 0, 0, 0);

//		event.getMatrixStack().func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(60));
//		event.getRenderer().getActiveRenderInfo().func_227995_f_().multiply(new Quaternion(50, 50, 50, 50));
//		event.getContext().func_228426_a_(event.getMatrixStack(), p_228378_1_, p_228378_2_, flag, event.getContext().func_228425_a_();, this, this.lightmapTexture, matrix4f);


		Entity currentViewEntity = Minecraft.getInstance().getRenderViewEntity();
		if(currentViewEntity instanceof ViewHandler) {
			if (((ViewHandler) currentViewEntity).getTarget() instanceof DroneEntity) {
				DroneEntity drone = (DroneEntity) ((ViewHandler) currentViewEntity).getTarget();
				Quaternion q = drone.getOrientation();
				GlStateManager.multMatrix(QuaternionHelper.quatToMatrix(q));
//				GlStateManager.rotatef(50, 1, 0, 0);
			}
		}

//				GameRenderer gr = Minecraft.getInstance().gameRenderer;
//				WorldRenderer wr = event.getContext();
//				wr.func_228426_a_(event.getMatrixStack(), event.getPartialTicks(), Util.nanoTime(), true, gr.getActiveRenderInfo(), gr, new LightTexture(gr, Minecraft.getInstance()), new Matrix4f());
//				event.getRenderer().getActiveRenderInfo().func_227995_f_().multiply(o);
//				event.getRenderer().getActiveRenderInfo().func_227995_f_().func_227066_a_(o.getX(), o.getY(), o.getZ(), o.getW());
//				BufferBuilder buffer = Tessellator.getInstance().getBuffer();
//				IRenderTypeBuffer.Impl r = IRenderTypeBuffer.func_228455_a_(buffer);
//				RenderSystem.multMatrix(QuaternionHelper.quatToMatrix(drone.getOrientation()));
//				r.func_228461_a_();
//				GlStateManager.func_227626_N_();
//				RenderSystem.multMatrix(QuaternionHelper.quatToMatrix(drone.getOrientation()));
//				event.getMatrixStack().func_227863_a_(drone.getOrientation());
//				GlStateManager.func_227665_a_(
//						QuaternionHelper.toBuffer(
//								QuaternionHelper.quatToMatrix(
//										drone.getOrientation()
//								)
//						)
//				); // Applies to screen
//				GlStateManager.func_227627_O_();
//			}
//		} else {
//			GL11.glRotated(0, 1f, 1f, 1f);
//		}
	}
}
