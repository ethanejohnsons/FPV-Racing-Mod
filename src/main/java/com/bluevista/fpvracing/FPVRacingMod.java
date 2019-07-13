package com.bluevista.fpvracing;

//import org.lwjgl.input.Controllers;  - these no longer work :(
//import org.lwjgl.input.Keyboard;

//import com.bluevista.fpvracing.controls.Transmitter;
//import com.bluevista.fpvracing.entities.ViewHandler;
import com.bluevista.fpvracing.entities.DroneEntity;
import com.bluevista.fpvracing.handler.CameraHandler;
import com.bluevista.fpvracing.handler.ModEventHandler;
import com.bluevista.fpvracing.handler.PlayerHandler;
import com.bluevista.fpvracing.handler.RegistrationHandler;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FPVRacingMod.MODID)
public class FPVRacingMod {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "fpvracingmod";
    public static KeyBinding unmount;

    public FPVRacingMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(ModEventHandler.class);
        MinecraftForge.EVENT_BUS.register(new CameraHandler());
        MinecraftForge.EVENT_BUS.register(PlayerHandler.class);
        MinecraftForge.EVENT_BUS.register(RegistrationHandler.class);
//        initKeybinds();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

//        EntityRegistry.registerModEntity(new ResourceLocation(FPVRacingMod.MODID, "entitydrone"), DroneEntity.class, "drone", 1, this, 200, 1, false);

        //		for(int i = 0; i < Controllers.getControllerCount(); i++)
		//	System.out.println(Controllers.getController(i).getName() + ", " + Controllers.getController(i).getAxisCount());
    }

//    public void initKeybinds() {
//        unmount = new KeyBinding("key.unmount.desc", Keyboard.KEY_LSHIFT, "key.drone.category");
//        ClientRegistry.registerKeyBinding(unmount);
//    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
