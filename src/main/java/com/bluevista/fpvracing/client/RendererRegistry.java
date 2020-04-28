package com.bluevista.fpvracing.client;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.client.renderers.DroneRenderer;
import com.bluevista.fpvracing.server.EntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ObjectHolder(FPVRacingMod.MODID)
@Mod.EventBusSubscriber(modid=FPVRacingMod.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class RendererRegistry {
    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Renderer Registry");

    public static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.DRONE.get(), DroneRenderer::new);
        LOGGER.debug("Registered entity renderers================================================================================================================");
    }
}
