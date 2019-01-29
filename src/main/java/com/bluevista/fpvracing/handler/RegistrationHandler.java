package com.bluevista.fpvracing.handler;

import com.bluevista.fpvracing.FPVRacingMod;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=FPVRacingMod.MODID)
public class RegistrationHandler {
	
//    public static final ItemDrone DRONE_SPAWNER = new ItemDrone();
//    public static final ItemGoggles DRONE_GOGGLES = new ItemGoggles();
//    public static final ItemTransmitter DRONE_TRANSMITTER = new ItemTransmitter();
	
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
//        event.getRegistry().register(DRONE_SPAWNER);
//        event.getRegistry().register(DRONE_GOGGLES);
//        event.getRegistry().register(DRONE_TRANSMITTER);
    }   
   
}