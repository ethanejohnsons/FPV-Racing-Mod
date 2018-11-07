package com.bluevista.fpvracing.math;

import java.nio.FloatBuffer;

import javax.vecmath.AxisAngle4f;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class QuaternionHelper {
	
	public static AxisAngle4f toAxisAngle(Quaternion quat, AxisAngle4f angle) {
		float divisor = (float) Math.sqrt(1 - quat.getW() * quat.getW());
		angle.setAngle((float) (2 * Math.acos(quat.getW())));
		angle.setX((float) (quat.getX() / divisor));
		angle.setY((float) (quat.getY() / divisor));
		angle.setZ((float) (quat.getZ() / divisor));
		
		return angle;
	}
	
	public static Quaternion negateRotation(Quaternion quat, Quaternion rot) {
		return Quaternion.mul(quat, Quaternion.negate(rot, new Quaternion()), new Quaternion());
	}
	
	// Old:
	// return Quaternion.mul(Quaternion.mul(rot, quat, new Quaternion()), Quaternion.negate(rot, new Quaternion()), new Quaternion());

	public static Quaternion rotateX(Quaternion quat, float amount) {	    
	    double radHalfAngle = Math.toRadians((double) amount) / 2.0;
	    Quaternion rot = new Quaternion((float) Math.sin(radHalfAngle), 0.0f, 0.0f, (float) Math.cos(radHalfAngle));
		return Quaternion.mul(quat, rot, new Quaternion());
	}
	
	public static Quaternion rotateY(Quaternion quat, float amount) {	    
	    double radHalfAngle = Math.toRadians((double) amount) / 2.0;
	    Quaternion rot = new Quaternion(0.0f, (float) Math.sin(radHalfAngle), 0.0f, (float) Math.cos(radHalfAngle));
		return Quaternion.mul(quat, rot, new Quaternion());
	}
	
	public static Quaternion rotateZ(Quaternion quat, float amount) {
	    double radHalfAngle = Math.toRadians((double) amount) / 2.0;
	    Quaternion rot = new Quaternion(0.0f, 0.0f, (float) Math.sin(radHalfAngle), (float) Math.cos(radHalfAngle));
		return Quaternion.mul(quat, rot, new Quaternion());
	}
	
	public static Vector3f rotationMatrixToVector(Matrix4f mat) {
		return new Vector3f(mat.m02, mat.m12, mat.m22);
	}
	
    public static FloatBuffer toBuffer(Matrix4f mat)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        
        buffer.put(mat.m00);
        buffer.put(mat.m01);
        buffer.put(mat.m02);
        buffer.put(mat.m03);
        buffer.put(mat.m10);
        buffer.put(mat.m11);
        buffer.put(mat.m12);
        buffer.put(mat.m13);
        buffer.put(mat.m20);
        buffer.put(mat.m21);
        buffer.put(mat.m22);
        buffer.put(mat.m23);
        buffer.put(mat.m30);
        buffer.put(mat.m31);
        buffer.put(mat.m32);
        buffer.put(mat.m33);
 
        buffer.flip();
        
        return buffer;
    }
	
	public static Matrix4f quatToMatrix(Quaternion q) {
		Matrix4f mat = new Matrix4f();
		
	    double sqw = q.w*q.w;
	    double sqx = q.x*q.x;
	    double sqy = q.y*q.y;
	    double sqz = q.z*q.z;

	    // invs (inverse square length) is only required if quaternion is not already normalised
	    double invs = 1 / (sqx + sqy + sqz + sqw);
	    mat.m00 = (float) (( sqx - sqy - sqz + sqw)*invs) ; // since sqw + sqx + sqy + sqz =1/invs*invs
	    mat.m11 = (float) ((-sqx + sqy - sqz + sqw)*invs) ;
	    mat.m22 = (float) ((-sqx - sqy + sqz + sqw)*invs) ;
	    
	    double tmp1 = q.x*q.y;
	    double tmp2 = q.z*q.w;
	    mat.m10 = (float) (2.0 * (tmp1 + tmp2)*invs) ;
	    mat.m01 = (float) (2.0 * (tmp1 - tmp2)*invs) ;
	    
	    tmp1 = q.x*q.z;
	    tmp2 = q.y*q.w;
	    mat.m20 = (float) (2.0 * (tmp1 - tmp2)*invs) ;
	    mat.m02 = (float) (2.0 * (tmp1 + tmp2)*invs) ;
	    tmp1 = q.y*q.z;
	    tmp2 = q.x*q.w;
	    mat.m21 = (float) (2.0 * (tmp1 + tmp2)*invs) ;
	    mat.m12 = (float) (2.0 * (tmp1 - tmp2)*invs) ;      
	    
	    return mat;
	}

}
