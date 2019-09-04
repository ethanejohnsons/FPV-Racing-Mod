package com.bluevista.fpvracing.items;

import com.bluevista.fpvracing.FPVRacingMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(FPVRacingMod.MODID)
public class ItemRegistry {

    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Item Registry");

    // Items
    public static final DroneSpawnerItem drone_spawner = null;
    public static final GogglesItem drone_goggles = null;
    public static final TransmitterItem drone_transmitter = null;
	
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(

                new Item(new Item.Properties()
                        .maxStackSize(64)
                        .group(ItemGroup.MISC))
                        .setRegistryName(FPVRacingMod.MODID, "drone_spawner"),

                new Item(new Item.Properties()
                    .maxStackSize(1)
                    .group(ItemGroup.MISC))
                    .setRegistryName(FPVRacingMod.MODID, "drone_goggles"),

                new Item(new Item.Properties()
                    .maxStackSize(1)
                    .group(ItemGroup.MISC))
                    .setRegistryName(FPVRacingMod.MODID, "drone_transmitter")

        );

        LOGGER.debug("Registered items");
    }
   
}