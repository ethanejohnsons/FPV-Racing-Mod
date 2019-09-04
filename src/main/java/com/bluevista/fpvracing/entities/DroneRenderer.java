package com.bluevista.fpvracing.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;


public class DroneRenderer extends EntityRenderer<DroneEntity> {
    protected ResourceLocation droneTexture;

    public DroneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(DroneEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.bindEntityTexture(entity);
//        if (!this.renderOutlines) {
//            this.renderName(entity, x, y, z);
//        }

    }

//    @Override
//    protected void preRenderCallback(DroneEntity drone, float f) {
        // some people do some G11 transformations or blends here, like you can do
        // GL11.glScalef(2F, 2F, 2F); to scale up the entity
        // which is used for Slime entities.  I suggest having the entity cast to
        // your custom type to make it easier to access fields from your
        // custom entity, eg. GL11.glScalef(entity.scaleFactor, entity.scaleFactor,
        // entity.scaleFactor);
//    }

//    protected void setEntityTexture() {
//        droneTexture = new ResourceLocation(FPVRacingMod.MODID+":textures/entity/drones/default.png");
//    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called
     * unless you call Render.bindEntityTexture.
     */
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(DroneEntity drone) {
        return droneTexture;
    }
}