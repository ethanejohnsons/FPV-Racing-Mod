package com.bluevista.fpvracing.server.entities;

import com.bluevista.fpvracing.server.EntityRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class ViewHandler extends Entity {

    private Entity target;

    public ViewHandler(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public ViewHandler(FMLPlayMessages.SpawnEntity packet, World worldIn) {
        this(EntityRegistry.VIEW, worldIn);
    }

    public ViewHandler(World worldIn, Entity target) {
        this(EntityRegistry.VIEW, worldIn);
        this.setTarget(target);
    }

    public void viewTick(float delta) {
        if(target != null) {
//            this.setPosition(target.posX, target.posY, target.posZ);
        }
    }

    public Entity getTarget() {
        return this.target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    protected void registerData() {
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
