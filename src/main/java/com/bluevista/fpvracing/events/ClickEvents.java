package com.bluevista.fpvracing.events;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.entities.DroneEntity;
import com.bluevista.fpvracing.items.ItemDrone;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClickEvents {

    /*
     * When the player right clicks the ground or any block
     * with the drone item, a drone will spawn with a ViewHandler
     * attached to it.
     */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemDrone && !event.getWorld().isRemote && event.getHand() == event.getEntityPlayer().getActiveHand()) {
            DroneEntity drone = new DroneEntity(event.getWorld());
            drone.setLocationAndAngles(event.getPos().getX(), event.getPos().getY()+1, event.getPos().getZ(), 0, 0);
            event.getWorld().addEntity(drone);
        }
    }

//	@SubscribeEvent
//	public static void onRightClick(RightClickItem event) {
//		if(event.getItemStack().getItem() instanceof ItemGoggles) {
//
//		}
//	}

}
