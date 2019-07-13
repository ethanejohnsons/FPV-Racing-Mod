package com.bluevista.fpvracing.registry;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.entities.DroneEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {

    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Entity Registry");
    public static final EntityType<DroneEntity> drone = null;

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {

        event.getRegistry().registerAll(
                EntityType.Builder.create(DroneEntity::new, EntityClassification.MISC)
                        .setShouldReceiveVelocityUpdates(true).setTrackingRange(24).setUpdateInterval(60)
                        .build("drone").setRegistryName(FPVRacingMod.MODID, "drone")
        );

        LOGGER.debug("Registered entities");

    }

}