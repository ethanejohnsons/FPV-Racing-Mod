package com.bluevista.fpvracing.registry;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
	
    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        // register a new block here
//        FPVRacingMod.LOGGER.info("HELLO from Register Block");
    }
   
}