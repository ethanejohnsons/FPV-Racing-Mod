package com.bluevista.fpvracing.client.events;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.client.controls.Controller;
import com.bluevista.fpvracing.client.math.QuaternionHelper;
import com.bluevista.fpvracing.server.entities.DroneEntity;
import com.bluevista.fpvracing.server.entities.ViewHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(FPVRacingMod.MODID)
@OnlyIn(Dist.CLIENT)
public class InputEvents {
    private static Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void on(TickEvent.ClientTickEvent event) {

        Entity currentViewEntity = mc.getRenderViewEntity();
        if(currentViewEntity instanceof ViewHandler) {
            if (((ViewHandler) currentViewEntity).getTarget() instanceof DroneEntity) {
                DroneEntity drone = (DroneEntity) ((ViewHandler) currentViewEntity).getTarget();
                drone.setOrientation(QuaternionHelper.rotateX(drone.getOrientation(), -Controller.getAxis(2) * 10));
                drone.setOrientation(QuaternionHelper.rotateY(drone.getOrientation(), -Controller.getAxis(3) * 10));
                drone.setOrientation(QuaternionHelper.rotateZ(drone.getOrientation(), -Controller.getAxis(1) * 10));
                drone.setThrottle(Controller.getAxis(0) + 1);
            }
        }
    }

// 	@SubscribeEvent
//	public static void onKeyInput(InputEvent.KeyInputEvent event) {
//		if(FPVRacingMod.unmount.isPressed()) { // the get off button
//			DroneEntity.stopUsing();
//		}
//		if(FPVRacingMod.drone_toggle.isPressed()) {
//			DroneHelper.isPlayerDrone = !DroneHelper.isPlayerDrone;
//			DroneHelper.init();
//			Minecraft.getMinecraft().player.setNoGravity(!Minecraft.getMinecraft().player.hasNoGravity());
//		}
//	}

}
