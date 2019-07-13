package com.bluevista.fpvracing.events;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.entities.DroneEntity;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class KeyEvents {
				
	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
//		if(FPVRacingMod.unmount.isPressed()) { // the get off button
//			DroneEntity.stopUsing();
//		}
//		if(FPVRacingMod.drone_toggle.isPressed()) {
//			DroneHelper.isPlayerDrone = !DroneHelper.isPlayerDrone;
//			DroneHelper.init();
//			Minecraft.getMinecraft().player.setNoGravity(!Minecraft.getMinecraft().player.hasNoGravity());
//		}
	}
}