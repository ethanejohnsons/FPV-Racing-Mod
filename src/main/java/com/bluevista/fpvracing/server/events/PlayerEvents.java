package com.bluevista.fpvracing.server.events;

import com.bluevista.fpvracing.FPVRacingMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(FPVRacingMod.MODID)
public class PlayerEvents {

    @SubscribeEvent
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {

    }

}
