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
		this.noClip = true;
		setSize(0.0F, 0.0F);
	}
	
	public ViewHandler(World worldIn, Entity target) {
		this(worldIn, FMLClientHandler.instance().getClient());
		this.target = target;
	}
	
    public void onUpdate() {
    	super.onUpdate();
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
