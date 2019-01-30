package com.bluevista.fpvracing.entities;

import javax.annotation.Nullable;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import com.bluevista.fpvracing.FPVRacingMod;
import com.bluevista.fpvracing.OSValidator;
import com.bluevista.fpvracing.controls.Transmitter;
import com.bluevista.fpvracing.math.QuaternionHelper;

import net.minecraft.client.Minecraft;
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
    
    private int channel;
    private int camera_angle;
    private int fov;
        
    private double axis[] = new double[4]; // input
                
	public EntityDrone(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		this.setSize(0.6F, 0.5625F);
                        
        this.channel = 0; // r band of course :)
    	this.camera_angle = 0; // degrees
    	this.fov = 100;
    	    	
        this.orientation = QuaternionHelper.rotateX(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f), camera_angle);    
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		this.orientation.normalise();
		this.updateInputs();
		this.control();
		doPhysics(this);
//		if(this.isBeingRidden()) {
//			Entity e = this.getControllingPassenger();
//			this.setPosition(e.posX, e.posY, e.posZ);
//		}
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	}
    
	public void doPhysics(EntityDrone e) {
		if(e.motionY >= -1.5) // terminal velocity
			e.motionY -= 0.06D; // Gravity
		
		// Drag
		if(e.onGround) {
			e.motionX -= e.motionX*0.18;
			e.motionZ -= e.motionZ*0.18;
		} else { // in air
			if(e.motionX > 0)
				e.motionX -= 0.02D;
			else if(e.motionX < 0)
				e.motionX += 0.02D;

			if(e.motionZ > 0)
				e.motionZ -= 0.02D;
			else if(e.motionZ < 0)
				e.motionZ += 0.02D;
			
			//this.motionY -= 0.02D;
		}

		Vector3f d = QuaternionHelper.rotationMatrixToVector(
				QuaternionHelper.quatToMatrix(
				QuaternionHelper.rotateX(e.getOrientation(), (-90) - 20)));
		e.addVelocity(-d.getX() * e.getThrottle(), d.getY() * e.getThrottle(), -d.getZ() * e.getThrottle());
        e.move(MoverType.SELF, e.motionX, e.motionY, e.motionZ);
	}
		
	public void control() {

		if(this.isBeingRidden()) {
			this.changePitch((float) axis[0]);
			this.changeYaw((float) axis[1]);
			this.changeRoll((float) axis[2]);
			this.setThrottle((float) axis[3]);
		}
		
	}
	
	public void changePitch(float angle) {
		orientation = QuaternionHelper.rotateX(orientation, angle);
	}
	
	public void changeRoll(float angle) {
		orientation = QuaternionHelper.rotateZ(orientation, angle);
	}
	
	public void changeYaw(float angle) {
		orientation = QuaternionHelper.rotateY(orientation, angle);
	}
	
	public void setThrottle(float throttle) {
		this.throttle = throttle;
	}
	
	public int getChannel() {
		return channel;
	}
	
	// Right Click on the entity
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    	 if (!this.world.isRemote) {
            Minecraft.getMinecraft().setRenderViewEntity(this);
     		//Minecraft.getMinecraft().getRenderViewEntity().setPositionAndRotation(posX, posY, posZ, 0, 0);
            player.startRiding(this);
         }
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
	protected void readEntityFromNBT(NBTTagCompound compound) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		
	}
	
	public static boolean isPlayerFlyingDrone() {
		return Minecraft.getMinecraft().player.isRiding() && Minecraft.getMinecraft().player.getRidingEntity() instanceof EntityDrone;
	}
		    
}