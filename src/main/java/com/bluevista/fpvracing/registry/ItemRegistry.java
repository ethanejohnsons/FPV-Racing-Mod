package com.bluevista.fpvracing.registry;

import com.bluevista.fpvracing.FPVRacingMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.bluevista.fpvracing.items.DroneItem;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(modid = FPVRacingMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(FPVRacingMod.MODID)
public class ItemRegistry {

    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Item Registry");

    // Items
    public static final DroneItem drone_spawner = null;
//    public static final ItemGoggles DRONE_GOGGLES = new ItemGoggles();
//    public static final ItemTransmitter DRONE_TRANSMITTER = new ItemTransmitter();
	
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(

                new Item(new Item.Properties()
                        .maxStackSize(32)
                        .group(ItemGroup.MISC))
                        .setRegistryName(FPVRacingMod.MODID, "drone_spawner")

        );

        LOGGER.debug("Registered items");
    }
   
}