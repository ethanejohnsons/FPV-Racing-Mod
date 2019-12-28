package com.bluevista.fpvracing.server.entities;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.client.renderers.DroneRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid=FPVRacingMod.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(FPVRacingMod.MODID)
public class EntityRegistry {

    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Entity Registry");
    public static final EntityType<DroneEntity> DRONE = null;

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
       event.getRegistry().registerAll(
               EntityType.Builder.create(DroneEntity::new, EntityClassification.MISC)
                       .setCustomClientFactory(DroneEntity::new)
                       .setShouldReceiveVelocityUpdates(true)
                       .setTrackingRange(64)
                       .setUpdateInterval(60)
                       .build("drone")
                       .setRegistryName(FPVRacingMod.MODID, "drone")
       );

        RenderingRegistry.registerEntityRenderingHandler(DroneEntity.class, DroneRenderer::new);
        LOGGER.debug("Registered entities");
    }

}