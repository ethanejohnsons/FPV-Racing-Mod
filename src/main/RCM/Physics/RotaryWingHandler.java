/*    */ package RCM.Physics;
/*    */ 
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RotaryWingHandler
/*    */   extends WingHandler
/*    */ {
/*    */   private float inertia;
/*    */   private float rotationalVel;
/*    */   private float rotationAngle;
/*    */   private float gearRatio;
/*    */   
/*    */   public RotaryWingHandler(int propID2, float mass2, float ratio2, float eqFactor2, Vector3f spanVec2, Vector3f liftVec2, Vector3f chordVec2, Vector3f pos2, float spanOffset2, float wingArea2, float wingSpan2, float rootChord2, float tipChord2, float def2, float defOffset2, int profileType, int channel, int sect, int sensorID)
/*    */   {
/* 18 */     super(propID2, spanVec2, liftVec2, chordVec2, pos2, spanOffset2, wingArea2, wingSpan2, rootChord2, tipChord2, def2, defOffset2, profileType, channel, sect, eqFactor2, sensorID);
/*    */     
/*    */ 
/* 21 */     this.inertia = (mass2 * wingSpan2 * wingSpan2 / 3.0F);
/* 22 */     this.gearRatio = ratio2;
/*    */   }
/*    */   
/*    */ 
/*    */   public float getTroque()
/*    */   {
/* 28 */     float torque = 0.0F;
/*    */     
/* 30 */     Vector3f forceSum = new Vector3f();
/* 31 */     Vector3f torqueVect = new Vector3f();
/* 32 */     Vector3f torqueDir = new Vector3f();
/* 33 */     torqueDir.cross(this.globalSpanVect, this.globalChordVect);
/*    */     
/* 35 */     for (int i = 0; i < getSections(); i++)
/*    */     {
/* 37 */       forceSum.set(getLift(i));
/* 38 */       forceSum.add(getDrag(i));
/* 39 */       torqueVect.cross(forceSum, getPosition(i));
/* 40 */       torque += torqueVect.dot(torqueDir);
/*    */     }
/*    */     
/* 43 */     return torque * this.gearRatio;
/*    */   }
/*    */   
/*    */   public float getInertia()
/*    */   {
/* 48 */     return this.inertia * this.gearRatio * this.gearRatio * this.eqFactor;
/*    */   }
/*    */   
/*    */   public void setAngularVelocity(float angularVel)
/*    */   {
/* 53 */     this.angularVelocity = (angularVel * this.gearRatio);
/*    */   }
/*    */   
/*    */   public float getAngularVelocity()
/*    */   {
/* 58 */     return this.angularVelocity;
/*    */   }
/*    */   
/*    */   public void setRotationAngle(float deltaTime)
/*    */   {
/* 63 */     this.rotationAngle += this.angularVelocity * deltaTime * this.helper.radToDeg;
/*    */     
/* 65 */     if (this.rotationAngle > 1.0E31D)
/*    */     {
/* 67 */       this.rotationAngle = 0.0F;
/*    */     }
/*    */   }
/*    */   
/*    */   public float getRotationAngle()
/*    */   {
/* 73 */     return this.rotationAngle;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/RotaryWingHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */