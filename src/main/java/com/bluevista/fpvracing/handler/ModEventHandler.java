package com.bluevista.fpvracing.handler;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.items.ItemDrone;

import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

@Mod.EventBusSubscriber(modid=FPVRacingMod.MODID)
public class ModEventHandler {
				
	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) {
		if(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemDrone) {
			EntityDrone drone = new EntityDrone(event.getWorld());
			drone.setLocationAndAngles(event.getPos().getX(), event.getPos().getY()+1, event.getPos().getZ(), 0, 0);
		    if (!event.getWorld().isRemote) {
	            event.getWorld().spawnEntity(drone);
	        }
		}		
	}
	
	@SubscribeEvent
	public static void onRightClick(RightClickItem event) {
//		if(event.getItemStack().getItem() instanceof ItemGoggles) {
//			
//		}
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
//		if(FPVRacingMod.drone_toggle.isPressed()) {
//			DroneHelper.isPlayerDrone = !DroneHelper.isPlayerDrone;
//			DroneHelper.init();
//			Minecraft.getMinecraft().player.setNoGravity(!Minecraft.getMinecraft().player.hasNoGravity());
//		}
	}
}