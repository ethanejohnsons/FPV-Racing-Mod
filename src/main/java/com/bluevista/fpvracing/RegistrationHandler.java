package com.bluevista.fpvracing;

import com.bluevista.fpvracing.items.ItemDrone;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=FPVRacingMod.MODID)
public class RegistrationHandler {
	
    public static final ItemDrone DRONE_SPAWNER = new ItemDrone();
	
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(DRONE_SPAWNER);
    }   
   
}