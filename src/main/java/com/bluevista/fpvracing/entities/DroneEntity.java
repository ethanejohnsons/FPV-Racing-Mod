package com.bluevista.fpvracing.entities;

//import com.bluevista.fpvracing.controls.Transmitter;
import com.bluevista.fpvracing.events.CameraEvents;
import com.bluevista.fpvracing.math.QuaternionHelper;
import com.bluevista.fpvracing.util.OSValidator;

import net.minecraft.entity.Entity;
		import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumHand;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
		import net.minecraft.world.World;

import net.minecraft.client.renderer.Quaternion;
import net.minecraftforge.fml.network.NetworkHooks;

public class DroneEntity extends Entity {

    private static PlayerEntity playerUsing;
    private static DroneEntity droneBeingUsed;

	private Quaternion orientation;

    private double throttle;
    private double axis[] = new double[4]; // input
    
	public DroneEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		// TODO nbt tags - channel, camera_angle, etc.
	}

	public DroneEntity(World worldIn) {
		this(EntityRegistry.drone, worldIn);
		this.world = worldIn;
		this.orientation = QuaternionHelper.rotateX(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f), 0);
	}

	@Override
	public void tick() {
		super.tick();
		
		if(isPlayerUsing(this)) {
			updateInputs();
			control();
		}
		
		doPhysics();
				
        this.move(MoverType.SELF, this.getMotion());
	}
    
	public void doPhysics() {
//		if(this.motionY >= -1.5) // terminal velocity
//			this.motionY -= 0.06D; // Gravity
//
//		 Drag
//		if(this.onGround) {
//			this.motionX -= this.motionX*0.18;
//			this.motionZ -= this.motionZ*0.18;
//		} else { // in air
//			if(this.motionX > 0)
//				this.motionX -= 0.02D;
//			else if(this.motionX < 0)
//				this.motionX += 0.02D;
//
//			if(this.motionZ > 0)
//				this.motionZ -= 0.02D;
//			else if(this.motionZ < 0)
//				this.motionZ += 0.02D;
//
//			this.motionY -= 0.02D;
//		}
//
//		Vector3f d = QuaternionHelper.rotationMatrixToVector(
//				QuaternionHelper.quatToMatrix(
//				QuaternionHelper.rotateX(this.getOrientation(), (-90) - 20)));
//		this.addVelocity(-d.getX() * this.getThrottle(), d.getY() * this.getThrottle(), -d.getZ() * this.getThrottle());
//		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	}
		
	public void control() {
		this.changePitch((float) axis[0]);
		this.changeYaw((float) axis[1]);
		this.changeRoll((float) axis[2]);
		this.setThrottle((float) axis[3]);
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
	
//    public boolean processInitialInteract(PlayerEntity player, EnumHand hand) {
//        setPlayerUsing(player, this);
//        return true;
//    }

//    @SideOnly(Side.CLIENT)
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
    	
//		Transmitter t = FPVRacingMod.transmitter;
//		axis[0] = -t.getFilteredAxis(order[0], 1.0f, 0.5f, 0.65f); // pitch
//		axis[1] = -t.getFilteredAxis(order[1], 1.0f, 0.5f, 0.65f); // yaw
//		axis[2] = -t.getFilteredAxis(order[2], 1.0f, 0.5f, 0.65f); // roll
//		axis[3] = (t.getRawAxis(	 order[3]) + 1) / 15;		   // throttle
    }
    
//    public static void setPlayerUsing(PlayerEntity p, DroneEntity d) {
//    	playerUsing = p;
//    	droneBeingUsed = d;
//    }
    
    public static boolean isPlayerUsing(DroneEntity d) {
    	return playerUsing != null && droneBeingUsed.equals(d);
    }
    
    public static void stopUsing() {
    	playerUsing = null;
    	droneBeingUsed = null;
		CameraEvents.setTarget(null);
    }

    public Quaternion getOrientation() {
    	return orientation;
    }
    
//    @Nullable
//    public AxisAlignedBB getCollisionBox(Entity entityIn)
//    {
//        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
//    }
//
//    @Nullable
//    public AxisAlignedBB getCollisionBoundingBox()
//    {
//        return this.getEntityBoundingBox();
//    }

    public boolean canBePushed()
    {
        return true;
    }
	
//	public void applyEntityCollision(Entity entityIn)
//    {
//        if (entityIn instanceof EntityBoat)
//        {
//            if (entityIn.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
//            {
//                super.applyEntityCollision(entityIn);
//            }
//        }
//        else if (entityIn.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY)
//        {
//            super.applyEntityCollision(entityIn);
//        }
//    }
	
//    @Override
//    public void updatePassenger(Entity passenger)
//    {
//        passenger.setPosition(this.posX, this.posY, this.posZ);
//    }

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