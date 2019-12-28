package com.bluevista.fpvracing.server.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ViewHandler extends PlayerEntity {
	
	private Entity target;
	
	public ViewHandler(World worldIn, Minecraft mc) {
		super(worldIn, mc.player.getGameProfile());
		this.noClip = true;
//		setSize(0.0F, 0.0F);
	}
	
	public ViewHandler(World worldIn, Entity target) {
		this(worldIn, Minecraft.getInstance());
		this.target = target;
	}
	
    public void tick() {
    	super.tick();
    	if(target != null) setPosition(target.posX, target.posY-1, this.posZ + 0.5);
    }
    
    public Entity getTarget() {
    	return target;
    }

	@Override
	public boolean isSpectator() {
		return false;
	}

	@Override
	public boolean isCreative() {
		return false;
	}
	
}
