package com.bluevista.fpvracing;

//import org.lwjgl.input.Keyboard;

import com.bluevista.fpvracing.events.*;

import com.bluevista.fpvracing.registry.BlockRegistry;
import com.bluevista.fpvracing.registry.ItemRegistry;
//import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(FPVRacingMod.MODID)
public class FPVRacingMod {

    public static final String MODID = "fpvracingmod";
    private static final Logger LOGGER = LogManager.getLogger();

//    public static KeyBinding unmount;

    public FPVRacingMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(CameraEvents.class);
        MinecraftForge.EVENT_BUS.register(ClickEvents.class);
        MinecraftForge.EVENT_BUS.register(KeyEvents.class);
        MinecraftForge.EVENT_BUS.register(PlayerEvents.class);

        MinecraftForge.EVENT_BUS.register(ItemRegistry.class);
        MinecraftForge.EVENT_BUS.register(BlockRegistry.class);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");

//        EntityRegistry.registerModEntity(new ResourceLocation(FPVRacingMod.MODID, "entitydrone"),
//                DroneEntity.class, "drone", 1, this, 200, 1, false);
        //unmount = new KeyBinding("key.unmount.desc", Keyboard.KEY_LSHIFT, "key.drone.category");
        //ClientRegistry.registerKeyBinding(unmount);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(this.MODID, "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
}
