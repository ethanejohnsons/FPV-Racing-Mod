package com.bluevista.fpvracing.server.events;

//import net.minecraftforge.client.event.MouseEvent;
import com.bluevista.fpvracing.FPVRacingMod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FPVRacingMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerEvents {
			
//	@SubscribeEvent
//	public static void onPlayerTick(PlayerTickEvent event) {
//		if(DroneHelper.isPlayerDrone) {
//			DroneHelper.update(event.player);
//		} else {
//			event.player.setNoGravity(false);
//		}
//	}
//
//	@SubscribeEvent
//	public static void mouseEvent(MouseEvent event) {
//		
//		// Prevents mouse movement while the player is a drone
//		if(DroneHelper.isPlayerDrone) {
////			Minecraft.getMinecraft().player.rotationPitch = 0;
////			Minecraft.getMinecraft().player.rotationYaw = 0;
//			System.out.println("canceling");
//		}
//
//	}

}
