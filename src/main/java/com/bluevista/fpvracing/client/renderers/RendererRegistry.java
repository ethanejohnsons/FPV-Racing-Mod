package com.bluevista.fpvracing.client.renderers;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.server.entities.DroneEntity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid= FPVRacingMod.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(FPVRacingMod.MODID)
public class RendererRegistry {
    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Renderer Registry");

    public void registerEntityRenderers(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(DroneEntity.class, DroneRenderer::new);
        LOGGER.debug("Registered entity renderers");
    }
}
