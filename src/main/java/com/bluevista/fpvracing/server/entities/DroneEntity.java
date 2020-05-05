package com.bluevista.fpvracing.server.entities;

import com.bluevista.fpvracing.client.controls.Controller;
import com.bluevista.fpvracing.server.EntityRegistry;
import com.bluevista.fpvracing.client.math.QuaternionHelper;

import net.minecraft.client.renderer.Quaternion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class DroneEntity extends Entity {

	private CompoundNBT properties;
	private Quaternion orientation;

	public DroneEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		orientation = QuaternionHelper.rotateX(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f), 90);
		properties = new CompoundNBT();
		properties.putInt("channel", 0);
		// TODO nbt tags - channel, camera_angle, etc.
	}

	public DroneEntity(FMLPlayMessages.SpawnEntity packet, World worldIn) {
		this(EntityRegistry.DRONE.get(), worldIn);
	}

	@Override
	public void tick() {
		super.tick();
		controllerInput();
//		Vector3f d = QuaternionHelper.rotationMatrixToVector(QuaternionHelper.quatToMatrix(QuaternionHelper.rotateX(this.getOrientation(), (-90) - 20)));
//		this.addVelocity(-d.getX() * 0, d.getY() * 0, -d.getZ() * 0.0);
//		this.move(MoverType.PLAYER, this.getMotion());
	}

	public void controllerInput() {
		orientation = QuaternionHelper.rotateX(orientation, Controller.getAxis(1)*100);
		orientation = QuaternionHelper.rotateY(orientation, Controller.getAxis(2)*100);
		orientation = QuaternionHelper.rotateZ(orientation, Controller.getAxis(3)*100);
		System.out.println(orientation.getX());
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
		return EntityRegistry.DRONE.get();
	}

	public static DroneEntity getNearestDroneTo(Entity entity) {
		World world = entity.getEntityWorld();
		List<DroneEntity> drones = world.getEntitiesWithinAABB(DroneEntity.class,
				new AxisAlignedBB(entity.getPosition().getX()-100, entity.getPosition().getY()-100, entity.getPosition().getZ()-100,
						entity.getPosition().getX()+100, entity.getPosition().getY()+100, entity.getPosition().getZ()+100));
		if(drones.size() > 0) return drones.get(0);
		else return null;
	}
}