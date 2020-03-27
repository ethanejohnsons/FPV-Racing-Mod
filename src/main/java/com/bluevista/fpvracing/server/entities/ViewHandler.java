package com.bluevista.fpvracing.server.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class ViewHandler extends Entity {

    private AxisAlignedBB nullAABB;
    private Entity target;

    private double CamPosX;
    private double CamPosZ;
    private double CamPosY;

    public ViewHandler(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.noClip = true;
        this.nullAABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public ViewHandler(FMLPlayMessages.SpawnEntity packet, World worldIn) {
        this(EntityType.PLAYER, worldIn);
    }

    public ViewHandler(World worldIn, Entity target) {
        this(EntityType.PLAYER, worldIn);
        this.setTarget(target);

        this.rotationYaw = target.rotationYaw;
        this.prevRotationYaw = target.rotationYaw;
        this.rotationPitch = 0.0F;
        this.prevRotationPitch = 0.0F;
        this.setPosition(target.posX, target.posY, target.posZ);
        this.prevPosX = target.prevPosX;
        this.prevPosY = target.prevPosY;
        this.prevPosZ = target.prevPosZ;
    }

    public void clientTick(float delta) {
        if(target != null) {

            double deltaPosX = this.target.prevPosX + (this.target.posX - this.target.prevPosX) * (double)delta;
            double deltaPosY = this.target.prevPosY + (this.target.posY - this.target.prevPosY) * (double)delta;
            double deltaPosZ = this.target.prevPosZ + (this.target.posZ - this.target.prevPosZ) * (double)delta;

            this.CamPosX = deltaPosX;
            this.CamPosZ = deltaPosZ;
            this.CamPosY = deltaPosY;

            this.setPosition(this.CamPosX, this.CamPosY, this.CamPosZ);

            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
        }
    }

    public Entity getTarget() {
        return this.target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }
    public AxisAlignedBB getBoundingBox() {
        return this.nullAABB;
    }
    public boolean canBeCollidedWith() {
        return false;
    }
    public boolean isSneaking() {
        return false;
    }
    public boolean isSpectator() {
        return false;
    }
    protected void registerData() { }
    protected void readAdditional(CompoundNBT compound) { }
    protected void writeAdditional(CompoundNBT compound) { }
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
