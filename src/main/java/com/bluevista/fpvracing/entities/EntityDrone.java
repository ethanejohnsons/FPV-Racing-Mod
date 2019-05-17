package com.bluevista.fpvracing.entities;

import javax.annotation.Nullable;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.controls.Transmitter;
import com.bluevista.fpvracing.handler.CameraHandler;
import com.bluevista.fpvracing.math.QuaternionHelper;
import com.bluevista.fpvracing.util.OSValidator;

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
    private boolean wasMountedLast = false;
    
    private World world;
    
    private int camera_angle;
        
    private double axis[] = new double[4]; // input
    
	public EntityDrone(World worldIn) {
		super(worldIn);
		this.world = worldIn;
	}

	@Override
	protected void entityInit() {
		this.setSize(1F, 0.25F);
                        
    	this.camera_angle = 0; // degrees
    	    	
        this.orientation = QuaternionHelper.rotateX(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f), camera_angle);    
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		this.orientation.normalise();
		this.updateInputs();
		this.control();
		this.doPhysics();
		
//		if(!this.isBeingRidden() && wasMountedLast) {
//			CameraHandler.returnView();
//			wasMountedLast = false;
//		}
		
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	}
    
	public void doPhysics() {
		if(this.motionY >= -1.5) // terminal velocity
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
				QuaternionHelper.rotateX(this.getOrientation(), (-90) - 20)));
		this.addVelocity(-d.getX() * this.getThrottle(), d.getY() * this.getThrottle(), -d.getZ() * this.getThrottle());
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	}
		
	public void control() {
		if(this.isBeingRidden()) {
			this.changePitch((float) axis[0]);
			this.changeYaw((float) axis[1]);
			this.changeRoll((float) axis[2]);
			this.setThrottle((float) axis[3]);
		}
	}
	
	/*
	 * Change pitch in degrees
	 */
	public void changePitch(float angle) {
		orientation = QuaternionHelper.rotateX(orientation, angle);
	}
	
	/*
	 * Change roll in degrees
	 */
	public void changeRoll(float angle) {
		orientation = QuaternionHelper.rotateZ(orientation, angle);
	}
	
	/*
	 * Change yaw in degrees
	 */
	public void changeYaw(float angle) {
		orientation = QuaternionHelper.rotateY(orientation, angle);
	}
	
	public void setThrottle(float throttle) {
		this.throttle = throttle;
	}
	
	/*
	 * When the drone is right clicked by a player, the
	 * setNewView method is called from CameraHandler in
	 * order to attach a new ViewHandler to the drone and
	 * the player.
	 */
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
//    	CameraHandler.nextTarget = this;
    	CameraHandler.setNewView(this.world, player, this);
        player.startRiding(this);
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateInputs() {
    	int[] order = new int[4];
		if(OSValidator.isMac()) {
			order[0] = 2;
			order[1] = 3;
			order[2] = 1;
			order[3] = 0;
		} else {
			order[0] = 1;
			order[1] = 0;
			order[2] = 2;
			order[3] = 3;
		}
    	
		Transmitter t = FPVRacingMod.transmitter;
		axis[0] = -t.getFilteredAxis(order[0], 1.0f, 0.5f, 0.65f); // pitch
		axis[1] = -t.getFilteredAxis(order[1], 1.0f, 0.5f, 0.65f); // yaw
		axis[2] = -t.getFilteredAxis(order[2], 1.0f, 0.5f, 0.65f); // roll
		axis[3] = (t.getRawAxis(	 order[3]) + 1) / 15;		   // throttle
    }

    public Quaternion getOrientation() {
    	return orientation;
    }
    
    public double getThrottle() {
    	return throttle;
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
        //passenger.setPosition(this.posX, this.posY, this.posZ);
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		
	}
		    
}