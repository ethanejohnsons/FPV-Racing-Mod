package com.bluevista.fpvracing;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.bluevista.fpvracing.controls.GenericTransmitter;
import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.handler.ModEventHandler;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = FPVRacingMod.MODID, name = FPVRacingMod.NAME, version = FPVRacingMod.VERSION)
public class FPVRacingMod {
    public static final String MODID = "fpvracing";
    public static final String NAME = "FPV Racing Mod";
    public static final String VERSION = "1.0";
    
    private static Logger logger;
    
    public static GenericTransmitter transmitter;
   
    public static KeyBinding leaveDrone;
    
    public static KeyBinding dronePitchForward;
    public static KeyBinding dronePitchBack;
    public static KeyBinding droneRollLeft;
    public static KeyBinding droneRollRight;
    public static KeyBinding droneYawLeft;
    public static KeyBinding droneYawRight;
    public static KeyBinding droneThrottleUp;
    public static KeyBinding droneThrottleDown;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(ModEventHandler.class);
        
        initKeybinds();
        
        EntityRegistry.registerModEntity(new ResourceLocation(FPVRacingMod.MODID, "entitydrone"), EntityDrone.class, "drone", 0, this, 100, 1, true);
        
        transmitter = new GenericTransmitter();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    
    public void initKeybinds() {
        leaveDrone = new KeyBinding("Become a Human", Keyboard.KEY_O, "key.categories.misc");
        
        dronePitchForward = new KeyBinding("Pitch Drone Forward", Keyboard.KEY_I, "key.categories.misc");
        dronePitchBack = new KeyBinding("Pitch Drone Back", Keyboard.KEY_K, "key.categories.misc");
        droneRollLeft = new KeyBinding("Roll Drone Left", Keyboard.KEY_J, "key.categories.misc");
        droneRollRight = new KeyBinding("Roll Drone Right", Keyboard.KEY_L, "key.categories.misc");
        droneYawLeft = new KeyBinding("Yaw Drone Left", Keyboard.KEY_U, "key.categories.misc");
        droneYawRight = new KeyBinding("Yaw Drone Right", Keyboard.KEY_O, "key.categories.misc");
       
        droneThrottleUp = new KeyBinding("Throttle Drone Up", Keyboard.KEY_Y, "key.categories.misc");
        droneThrottleDown = new KeyBinding("Throttle Drone Down", Keyboard.KEY_H, "key.categories.misc");

        ClientRegistry.registerKeyBinding(leaveDrone);
        
        ClientRegistry.registerKeyBinding(dronePitchForward);
        ClientRegistry.registerKeyBinding(dronePitchBack);
        ClientRegistry.registerKeyBinding(droneRollLeft);
        ClientRegistry.registerKeyBinding(droneRollRight);
        ClientRegistry.registerKeyBinding(droneYawLeft);
        ClientRegistry.registerKeyBinding(droneYawRight);

    }
}