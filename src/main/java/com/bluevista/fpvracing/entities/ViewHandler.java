package com.bluevista.fpvracing.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ViewHandler extends EntityPlayer {
	
	private Entity target;
	
	public ViewHandler(World worldIn, Minecraft mc) {
		super(worldIn, mc.player.getGameProfile());
	}
	
	public ViewHandler(World worldIn, Entity target) {
		this(worldIn, FMLClientHandler.instance().getClient());
		this.target = target;
		this.noClip = true;
		this.setSize(0, 0);
	}
	
    public void onUpdate() {
    	super.onUpdate();
    	this.setPosition(target.posX, target.posY-1, target.posZ);
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
