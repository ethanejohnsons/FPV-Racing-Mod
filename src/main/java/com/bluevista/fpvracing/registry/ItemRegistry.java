package com.bluevista.fpvracing.registry;

import com.bluevista.fpvracing.FPVRacingMod;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.bluevista.fpvracing.items.ItemDrone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Item Registry");

    public static final ItemDrone DRONE_SPAWNER = new ItemDrone(); // TODO DroneItem?
//    public static final ItemGoggles DRONE_GOGGLES = new ItemGoggles();
//    public static final ItemTransmitter DRONE_TRANSMITTER = new ItemTransmitter();
	
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(DRONE_SPAWNER);
//        event.getRegistry().register(DRONE_GOGGLES);
//        event.getRegistry().register(DRONE_TRANSMITTER);
    }   
   
}