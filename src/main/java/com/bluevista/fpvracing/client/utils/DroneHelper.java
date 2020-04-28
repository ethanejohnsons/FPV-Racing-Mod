package com.bluevista.fpvracing.client.utils;

import net.minecraft.client.renderer.Quaternion;

//import com.bluevista.fpvracing.FPVRacingMod;
//import com.bluevista.fpvracing.client.controls.Controller;
import com.bluevista.fpvracing.client.math.QuaternionHelper;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;

import javax.vecmath.Vector3f;

public class DroneHelper {
	
	public static boolean isPlayerDrone = false;
	
	public static Quaternion orientation;
    public static double throttle;
    public static int camera_angle;
    
    private static double axis[] = new double[4]; // input
	
    public static void init() {        
    	camera_angle = 0; // degrees
        orientation = QuaternionHelper.rotateZ(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f), camera_angle);
    }
    
    public static void update(PlayerEntity player) {
		updateInputs();
		control();
		doPhysics(player);
    }
    
	public static void doPhysics(PlayerEntity e) {
//		if(e.getMotion().getY() >= -1.5) // terminal velocity
//			e.setMotion() -= 0.06D; // Gravity
			// TODO do gravity

		// Drag
//		if(e.onGround) {
//			e.setMotion() -= e.motionX*0.18;
//			e.motionZ -= e.motionZ*0.18;
//		} else { // in air
//			if(e.motionX > 0)
//				e.motionX -= 0.02D;
//			else if(e.motionX < 0)
//				e.motionX += 0.02D;
//
//			if(e.motionZ > 0)
//				e.motionZ -= 0.02D;
//			else if(e.motionZ < 0)
//				e.motionZ += 0.02D;
			
			//this.motionY -= 0.02D;
			// TODO all physics
//		}

		Vector3f d = QuaternionHelper.rotationMatrixToVector(
				QuaternionHelper.quatToMatrix(
				QuaternionHelper.rotateX(orientation, (-90) - camera_angle)));

		e.setVelocity(d.getX() * throttle, d.getY() * throttle, d.getZ() * throttle);
        e.move(MoverType.SELF, e.getMotion());
	}
		
	public static void control() {
		if(isPlayerDrone) {
			changePitch((float) axis[0]);
			changeYaw((float) axis[1]);
			changeRoll((float) axis[2]);
			setThrottle((float) axis[3]);
		}
	}
	
	public static void changePitch(float angle) {
		orientation = QuaternionHelper.rotateX(orientation, angle);
	}
	
	public static void changeRoll(float angle) {
		orientation = QuaternionHelper.rotateZ(orientation, angle);
	}
	
	public static void changeYaw(float angle) {
		orientation = QuaternionHelper.rotateY(orientation, angle);
	}
	
	public static void setThrottle(float t) {
		throttle = t;
	}
	
//	@SideOnly(Side.CLIENT)
    public static void updateInputs() {
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
    	
//		Controller t = FPVRacingMod.transmitter;
//		axis[0] = -t.getFilteredAxis(order[0], 1.0f, 0.5f, 0.65f); // pitch
//		axis[1] = -t.getFilteredAxis(order[1], 1.0f, 0.5f, 0.65f); // yaw
//		axis[2] = -t.getFilteredAxis(order[2], 1.0f, 0.5f, 0.65f); // roll
//		axis[3] = (t.getRawAxis(	 order[3]) + 1) / 15;		   // throttle
    }
}
