/*    */ package RCM.Physics;
/*    */ 
/*    */ import javax.vecmath.Matrix4f;
/*    */ import javax.vecmath.Quat4f;
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PhysicsHelper
/*    */ {
/* 12 */   public float degToRad = 0.017453292F;
/* 13 */   public float radToDeg = 57.295776F;
/* 14 */   public float PI = 3.1415927F;
/*    */   
/*    */   public Vector3f getVelocityAtPoint(Vector3f linearVel, Vector3f rotationalVel, Vector3f point)
/*    */   {
/* 18 */     Vector3f velocityAtPoint = new Vector3f();
/* 19 */     velocityAtPoint.cross(rotationalVel, point);
/* 20 */     velocityAtPoint.add(linearVel);
/*    */     
/* 22 */     return velocityAtPoint;
/*    */   }
/*    */   
/*    */ 
/*    */   public float getAoA(Vector3f refVec1, Vector3f refVec2, Vector3f airVect)
/*    */   {
/* 28 */     float angleOfAtt = 0.0F;
/*    */     
/* 30 */     if (airVect.length() > 0.0F)
/*    */     {
/* 32 */       angleOfAtt = refVec1.angle(airVect);
/*    */     }
/* 34 */     if (refVec2.dot(airVect) <= 0.0F)
/*    */     {
/* 36 */       angleOfAtt *= -1.0F;
/*    */     }
/*    */     
/* 39 */     return angleOfAtt;
/*    */   }
/*    */   
/*    */   public Vector3f getAirDrag(Vector3f airflow, float dragFactor)
/*    */   {
/* 44 */     Vector3f drag = new Vector3f(airflow);
/* 45 */     float density = 1.225F;
/*    */     
/* 47 */     if (airflow.length() > 0.0F)
/*    */     {
/* 49 */       drag.normalize();
/* 50 */       drag.scale(-1.0F);
/*    */     }
/*    */     
/* 53 */     float airFlowMag = airflow.length();
/* 54 */     float dragForceMag = dragFactor * 0.5F * density * airFlowMag * airFlowMag;
/*    */     
/* 56 */     drag.scale(dragForceMag);
/*    */     
/* 58 */     return drag;
/*    */   }
/*    */   
/*    */   public Vector3f getWaterDrag(Vector3f airflow, float dragFactor)
/*    */   {
/* 63 */     Vector3f drag = new Vector3f(airflow);
/* 64 */     float density = 997.0F;
/*    */     
/* 66 */     if (airflow.length() > 0.0F)
/*    */     {
/* 68 */       drag.normalize();
/* 69 */       drag.scale(-1.0F);
/*    */     }
/*    */     
/* 72 */     float airFlowMag = airflow.length();
/* 73 */     float dragForceMag = dragFactor * 0.5F * density * airFlowMag;
/*    */     
/* 75 */     drag.scale(dragForceMag);
/*    */     
/* 77 */     return drag;
/*    */   }
/*    */   
/*    */ 
/*    */   public Vector3f rotateVector(Quat4f LocalQuat, Vector3f vect)
/*    */   {
/* 83 */     Matrix4f quatMatrix = new Matrix4f();
/* 84 */     quatMatrix.set(LocalQuat);
/*    */     
/* 86 */     Vector3f vert = new Vector3f();
/*    */     
/* 88 */     vert.x = (quatMatrix.m00 * vect.x + quatMatrix.m01 * vect.y + quatMatrix.m02 * vect.z);
/* 89 */     vert.y = (quatMatrix.m10 * vect.x + quatMatrix.m11 * vect.y + quatMatrix.m12 * vect.z);
/* 90 */     vert.z = (quatMatrix.m20 * vect.x + quatMatrix.m21 * vect.y + quatMatrix.m22 * vect.z);
/*    */     
/* 92 */     return vert;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/PhysicsHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */