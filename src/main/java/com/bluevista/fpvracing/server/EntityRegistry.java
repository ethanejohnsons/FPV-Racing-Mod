package com.bluevista.fpvracing.server;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.server.entities.DroneEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid=FPVRacingMod.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(FPVRacingMod.MODID)
public class EntityRegistry {

    private static final Logger LOGGER = LogManager.getLogger(FPVRacingMod.MODID + " Entity Registry");
    public static EntityType<?> DRONE = EntityType.Builder.<DroneEntity>create(DroneEntity::new, EntityClassification.MISC).setCustomClientFactory(DroneEntity::new).build(FPVRacingMod.MODID + ":drone").setRegistryName(FPVRacingMod.MODID, "drone");
//    public static EntityType<?> VIEW = EntityType.Builder.<ViewHandler>create(ViewHandler::new, EntityClassification.MISC).setCustomClientFactory(ViewHandler::new).build(FPVRacingMod.MODID + ":view").setRegistryName(FPVRacingMod.MODID, "view");

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(DRONE);

       LOGGER.debug("Registered entities");
    }

}