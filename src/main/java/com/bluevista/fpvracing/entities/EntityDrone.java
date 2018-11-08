package com.bluevista.fpvracing.entities;

import javax.annotation.Nullable;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.OSValidator;
import com.bluevista.fpvracing.controls.GenericTransmitter;
import com.bluevista.fpvracing.math.QuaternionHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDrone extends Entity {
    
    private Quaternion orientation;
    private double throttle;
    private int camera_angle;
    
    private double terminalVelocity;
                
	public EntityDrone(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		//Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
        this.setSize(0.6F, 0.5625F);
                
        this.terminalVelocity = 1.5;
        
    	this.camera_angle = 15; // 30 degree angle
        this.orientation = QuaternionHelper.rotateX(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f), camera_angle);    
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		this.dismountCheck();				
		
		this.updateInputs();
		this.control();
		
		//this.getRidingEntity().setPosition(this.posX, this.posY, this.posZ);
		
		//this.enforceHeightLimit(200);
		this.doPhysics();
		
//		System.out.println(FPVRacingMod.transmitter.getRawAxis(0) + ", " + FPVRacingMod.transmitter.getRawAxis(1) + ", " + FPVRacingMod.transmitter.getRawAxis(2) + ", " + FPVRacingMod.transmitter.getRawAxis(3));
				
		this.orientation.normalise();
		
//      if(this.isBeingRidden() && Minecraft.getMinecraft().getRenderViewEntity() == this) {
//    	 	Minecraft.getMinecraft().player.setPosition(posX, posY + 1, posZ);
//      }
		
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	}
	
	public void dismountCheck() {
		// If the player isn't 'riding' the drone but the camera is still fixed on it,
		// change it back to the player 
		if(!this.world.isRemote)
	        if(!this.isBeingRidden() && Minecraft.getMinecraft().getRenderViewEntity() == this)
		    	Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player);
	}
	
	public void enforceHeightLimit(int limit) {
		if(this.posY > limit) {
			this.throttle = 0;
		}
	}
	
	public void doPhysics() {
		if(this.motionY >= -terminalVelocity)
			this.motionY -= 0.06D; // Gravity
		
		// Drag
		if(this.onGround) {
			this.motionX -= this.motionX*0.18;
			this.motionZ -= this.motionZ*0.18;
		} else { // in air
			if(this.motionX > 0)
				this.motionX -= 0.02D;
			else if(this.motionX < 0)
				this.motionX += 0.02D;

			if(this.motionZ > 0)
				this.motionZ -= 0.02D;
			else if(this.motionZ < 0)
				this.motionZ += 0.02D;
			
			//this.motionY -= 0.02D;
		}

		Vector3f d = QuaternionHelper.rotationMatrixToVector(
				QuaternionHelper.quatToMatrix(
				QuaternionHelper.rotateX(orientation, (-90) - camera_angle)));
		this.addVelocity(-d.getX() * this.throttle, d.getY() * this.throttle, -d.getZ() * this.throttle);
	}
		
	public void control() {

		if(this.isBeingRidden()) {
			GenericTransmitter t = FPVRacingMod.transmitter;

			if(OSValidator.isMac()) {
				this.changePitch(-t.getFilteredAxis(2, 1.0f, 0.5f, 0.65f));
				this.changeYaw(-t.getFilteredAxis(3, 1.0f, 0.5f, 0.65f));
				this.changeRoll(-t.getFilteredAxis(1, 1.0f, 0.5f, 0.65f));
				this.throttle = (t.getRawAxis(0) + 1) / 8;
			} else if(OSValidator.isWindows()) {	
				this.changePitch(-t.getFilteredAxis(1, 1.0f, 0.5f, 0.65f));
				this.changeYaw(-t.getFilteredAxis(0, 1.0f, 0.5f, 0.65f));
				this.changeRoll(-t.getFilteredAxis(2, 1.0f, 0.5f, 0.65f));
				this.throttle = (t.getRawAxis(3) + 1) / 8;
			}
			
		}
		
	}
	
	// Negative is down, Positive is up
	public void changePitch(float angle) {
		orientation = QuaternionHelper.rotateX(orientation, angle);
	}
	
	// Negative is right, Positive is left
	public void changeRoll(float angle) {
		orientation = QuaternionHelper.rotateZ(orientation, angle);
	}
	
	// Negative is right, Positive is left
	public void changeYaw(float angle) {
		orientation = QuaternionHelper.rotateY(orientation, angle);
	}
	
	// Right Click on the entity
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    	 if (!this.world.isRemote) {
            Minecraft.getMinecraft().setRenderViewEntity(this);
     		Minecraft.getMinecraft().getRenderViewEntity().setPositionAndRotation(posX, posY, posZ, 0, 0);
            player.startRiding(this);
         }

         return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateInputs() {
    	// Keys go here
    }

    public Quaternion getOrientation() {
    	return orientation;
    }
    
    
    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }
	
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.getEntityBoundingBox();
    }

    public boolean canBePushed()
    {
        return true;
    }
	
	public void applyEntityCollision(Entity entityIn)
    {
        if (entityIn instanceof EntityBoat)
        {
            if (entityIn.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
            {
                super.applyEntityCollision(entityIn);
            }
        }
        else if (entityIn.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY)
        {
            super.applyEntityCollision(entityIn);
        }
    }
	
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        passenger.setPosition(this.posX, this.posY, this.posZ);
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		
	}
		    
}