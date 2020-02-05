package com.bluevista.fpvracing.client.events;

import com.bluevista.fpvracing.FPVRacingMod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FPVRacingMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InputEvents {

    /*
     * When the player right clicks the ground or any block
     * with the drone item, a drone will spawn with a ViewHandler
     * attached to it.
     */
//    @SubscribeEvent
//    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//        if(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemDroneSpawner && !event.getWorld().isRemote && event.getHand() == event.getEntityPlayer().getActiveHand()) {
//            DroneEntity drone = new DroneEntity(event.getWorld());
//            drone.setLocationAndAngles(event.getPos().getX(), event.getPos().getY()+1, event.getPos().getZ(), 0, 0);
//            event.getWorld().addEntity(drone);
//        }
//    }

//	@SubscribeEvent
//	public static void onRightClick(RightClickItem event) {
//		if(event.getItemStack().getItem() instanceof ItemGoggles) {
//
//		}
//	}

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
