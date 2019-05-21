/*     */ package RCM.Physics;
/*     */ 
/*     */ import javax.vecmath.Vector3f;
/*     */ 
/*     */ public class AutoControlHandler {
/*     */   private PhysicsHelper helper;
/*     */   private Vector3f sensePoint;
/*     */   private int ID;
/*     */   private float angGain;
/*     */   private float linGain;
/*     */   private float angLimit;
/*     */   private float linLimit;
/*     */   private float output;
/*     */   
/*     */   public AutoControlHandler(int ID2, Vector3f sensePoint2, float linGain2, float linLimit2, float angGain2, float angLimit2) {
/*  16 */     this.helper = new PhysicsHelper();
/*     */     
/*  18 */     this.ID = ID2;
/*  19 */     this.sensePoint = new Vector3f(sensePoint2);
/*  20 */     this.linGain = linGain2;
/*  21 */     this.linLimit = linLimit2;
/*  22 */     this.angGain = angGain2;
/*  23 */     this.angLimit = angLimit2;
/*     */   }
/*     */   
/*     */ 
/*     */   public void update(javax.vecmath.Quat4f localQuat, Vector3f linearVelocity, Vector3f virtualFlyBar, Vector3f virtualTailBar, Vector3f virtualFlightBar)
/*     */   {
/*  29 */     Vector3f globalSensePoint = this.helper.rotateVector(localQuat, this.sensePoint);
/*     */     
/*     */ 
/*  32 */     if ((this.ID > 30) && (virtualFlightBar.length() > 0.0F))
/*     */     {
/*  34 */       Vector3f difference = new Vector3f(virtualFlightBar);
/*  35 */       Vector3f dir = this.helper.rotateVector(localQuat, new Vector3f(this.sensePoint.y, -this.sensePoint.x, this.sensePoint.x));
/*     */       
/*  37 */       float diff = difference.dot(dir);
/*  38 */       dir.scale(diff);
/*     */       
/*  40 */       difference.sub(dir);
/*     */       
/*  42 */       float angle = difference.angle(globalSensePoint) - 90.0F * this.helper.degToRad;
/*     */       
/*  44 */       float angOutput = this.angGain * angle;
/*     */       
/*  46 */       if (angOutput > this.angLimit)
/*     */       {
/*  48 */         angOutput = this.angLimit;
/*     */       }
/*  50 */       else if (angOutput < -this.angLimit)
/*     */       {
/*  52 */         angOutput = -this.angLimit;
/*     */       }
/*     */       
/*  55 */       this.output = angOutput;
/*     */     }
/*  57 */     else if (this.ID > 20)
/*     */     {
/*  59 */       Vector3f difference = new Vector3f(virtualTailBar);
/*  60 */       Vector3f dir = this.helper.rotateVector(localQuat, new Vector3f(1.0F, 0.0F, 0.0F));
/*     */       
/*  62 */       difference.sub(globalSensePoint);
/*     */       
/*  64 */       if (difference.length() > 0.0F)
/*     */       {
/*  66 */         difference.normalize();
/*     */       }
/*     */       
/*  69 */       float angle = virtualTailBar.angle(globalSensePoint) * dir.dot(difference);
/*     */       
/*  71 */       float angOutput = this.angGain * angle;
/*     */       
/*  73 */       if (angOutput > this.angLimit)
/*     */       {
/*  75 */         angOutput = this.angLimit;
/*     */       }
/*  77 */       else if (angOutput < -this.angLimit)
/*     */       {
/*  79 */         angOutput = -this.angLimit;
/*     */       }
/*     */       
/*  82 */       this.output = angOutput;
/*     */     }
/*  84 */     else if (this.ID > 10)
/*     */     {
/*  86 */       float angle = virtualFlyBar.angle(globalSensePoint) - 90.0F * this.helper.degToRad;
/*     */       
/*  88 */       float angOutput = this.angGain * angle;
/*     */       
/*  90 */       if (angOutput > this.angLimit)
/*     */       {
/*  92 */         angOutput = this.angLimit;
/*     */       }
/*  94 */       else if (angOutput < -this.angLimit)
/*     */       {
/*  96 */         angOutput = -this.angLimit;
/*     */       }
/*     */       
/*  99 */       this.output = angOutput;
/*     */     }
/*     */   }
/*     */   
/*     */   public float getResponse()
/*     */   {
/* 105 */     return this.output;
/*     */   }
/*     */   
/*     */ 
/*     */   public float getMotorResponse(float power)
/*     */   {
/* 111 */     if (power == 0.0F)
/*     */     {
/* 113 */       return 0.0F;
/*     */     }
/*     */     
/* 116 */     return this.output;
/*     */   }
/*     */   
/*     */   public int getID()
/*     */   {
/* 121 */     return this.ID;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/AutoControlHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */