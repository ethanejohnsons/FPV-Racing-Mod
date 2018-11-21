package com.bluevista.fpvracing;

import org.lwjgl.input.Controllers;

import com.bluevista.fpvracing.controls.ControllerTaranis;
import com.bluevista.fpvracing.entities.EntityDrone;
import com.bluevista.fpvracing.handler.CameraHandler;
import com.bluevista.fpvracing.handler.ModEventHandler;
import com.bluevista.fpvracing.handler.RegistrationHandler;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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
        
    public static ControllerTaranis transmitter;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(ModEventHandler.class);
        MinecraftForge.EVENT_BUS.register(CameraHandler.class);
        MinecraftForge.EVENT_BUS.register(RegistrationHandler.class);
        
        initKeybinds();
        
        EntityRegistry.registerModEntity(new ResourceLocation(FPVRacingMod.MODID, "entitydrone"), EntityDrone.class, "drone", 0, this, 100, 3, true);
        
        transmitter = new ControllerTaranis();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	System.out.println("Controllers: ");
		for(int i = 0; i < Controllers.getControllerCount(); i++)
			System.out.println(Controllers.getController(i).getName() + ", " + Controllers.getController(i).getAxisCount());
		
    }
    
    public void initKeybinds() {

    }
}