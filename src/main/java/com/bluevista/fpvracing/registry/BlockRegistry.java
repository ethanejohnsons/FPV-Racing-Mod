package com.bluevista.fpvracing.registry;

import com.bluevista.fpvracing.FPVRacingMod;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {

    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Block Registry");

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        // register a new block here
//        FPVRacingMod.LOGGER.info("HELLO from Register Block");
    }
   
}