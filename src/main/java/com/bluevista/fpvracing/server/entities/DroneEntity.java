package com.bluevista.fpvracing.server.entities;

import com.bluevista.fpvracing.client.controls.Controller;
import com.bluevista.fpvracing.server.EntityRegistry;
import com.bluevista.fpvracing.client.math.QuaternionHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import net.minecraft.client.renderer.Quaternion;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class DroneEntity extends Entity {

	private CompoundNBT properties;
	private Quaternion orientation;

	public DroneEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		orientation = QuaternionHelper.rotateX(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f), 0);
		properties = new CompoundNBT();
		properties.putInt("channel", 0);
		// TODO nbt tags - channel, camera_angle, etc.
	}

	public DroneEntity(FMLPlayMessages.SpawnEntity packet, World worldIn) {
		this(EntityRegistry.DRONE, worldIn);
	}

	@Override
	public void tick() {
		super.tick();
		controllerInput();
//		Vector3f d = QuaternionHelper.rotationMatrixToVector(QuaternionHelper.quatToMatrix(QuaternionHelper.rotateX(this.getOrientation(), (-90) - 20)));
//		this.addVelocity(-d.getX() * 0, d.getY() * 0, -d.getZ() * 0.0);
//		this.move(MoverType.PLAYER, this.getMotion());
	}

//	@Override
//	public void tick() {
//		super.tick();
//
//		if(isPlayerUsing(this)) {
//			updateInputs();
//			control();
//		}
//
//		doPhysics();
//
//        this.move(MoverType.SELF, this.getMotion());
//        this.setNoGravity(false);
//	}

	public void controllerInput() {
		changePitch(100 * Controller.getAxis(1));
		changeYaw(100 * Controller.getAxis(2));
		changeRoll(100 * Controller.getAxis(3));
//		this.setThrottle((float) Controller.getAxis(3));
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

	public void setChannel(int channel) {
		this.properties.putInt("channel", channel);
	}

	public int getChannel() {
		return this.properties.getInt("channel");
	}

    public Quaternion getOrientation() {
    	return orientation;
    }

    @Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand) {
		return true;
	}

    @Override
    public boolean canBePushed() {
        return true;
    }
	
	@Override
	protected void registerData() { }

	@Override
	protected void readAdditional(CompoundNBT compound) { }

	@Override
	protected void writeAdditional(CompoundNBT compound) { }

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public EntityType<?> getType() {
		return EntityRegistry.DRONE;
	}

	public static DroneEntity getNearestDroneTo(Entity entity) {
		World world = entity.getEntityWorld();
		List<DroneEntity> drones = world.getEntitiesWithinAABB(DroneEntity.class,
				new AxisAlignedBB(entity.posX-100, entity.posY-100, entity.posZ-100,
						entity.posX+100, entity.posY+100, entity.posZ+100));
		if(drones.size() > 0) return drones.get(0);
		else return null;
	}
}