package com.bluevista.fpvracing.handler;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.items.ItemDrone;

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
		// if key down
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