package com.bluevista.fpvracing;

import com.bluevista.fpvracing.client.RendererRegistry;
import com.bluevista.fpvracing.client.events.RenderEvents;
import com.bluevista.fpvracing.server.EntityRegistry;
import com.bluevista.fpvracing.server.events.PlayerEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = FPVRacingMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(FPVRacingMod.MODID)
public class FPVRacingMod {

    public static final String MODID = "fpvracingmod";
    private static final Logger LOGGER = LogManager.getLogger();

    public FPVRacingMod() {
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityRegistry.ENTITY_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(RenderEvents.class);
        MinecraftForge.EVENT_BUS.register(PlayerEvents.class);
    }

    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
        RendererRegistry.registerEntityRenderers();
    }
}
