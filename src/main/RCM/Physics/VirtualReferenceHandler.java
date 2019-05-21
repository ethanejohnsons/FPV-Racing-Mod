/*     */ package RCM.Physics;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import javax.vecmath.AxisAngle4f;
/*     */ import javax.vecmath.Quat4f;
/*     */ import javax.vecmath.Vector3f;
/*     */ 
/*     */ public class VirtualReferenceHandler
/*     */ {
/*     */   private Vector3f flyBar;
/*     */   private Vector3f tailBar;
/*     */   private Vector3f flightPath;
/*     */   private float scaleFactor;
/*     */   private PhysicsHelper helper;
/*     */   private float limit;
/*     */   
/*     */   public VirtualReferenceHandler()
/*     */   {
/*  19 */     this.flyBar = new Vector3f(0.0F, 1.0F, 0.0F);
/*  20 */     this.tailBar = new Vector3f(0.0F, 0.0F, -1.0F);
/*  21 */     this.flightPath = new Vector3f();
/*  22 */     this.scaleFactor = 2.3F;
/*  23 */     this.helper = new PhysicsHelper();
/*  24 */     this.limit = 0.2617994F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateBars(Vector3f pitchAxis, Vector3f yawAxis, Vector3f rollAxis, float pitchControl, float yawControl, float rollControl, float deltaTime)
/*     */   {
/*  34 */     float pitchDeltAngle = virtualBarLimit(this.flyBar, rollAxis, pitchAxis, pitchControl * (float)Math.sqrt(Math.abs(pitchControl)) * deltaTime * this.scaleFactor);
/*  35 */     float rollDeltAngle = virtualBarLimit2(this.flyBar, pitchAxis, rollAxis, rollControl * (float)Math.sqrt(Math.abs(rollControl)) * deltaTime * this.scaleFactor);
/*  36 */     float yawDeltAngle = virtualBarLimit(this.tailBar, pitchAxis, yawAxis, yawControl * (float)Math.sqrt(Math.abs(yawControl)) * deltaTime * this.scaleFactor);
/*     */     
/*  38 */     AxisAngle4f pitchRot = new AxisAngle4f(pitchAxis.x, pitchAxis.y, pitchAxis.z, pitchDeltAngle);
/*  39 */     AxisAngle4f rollRot = new AxisAngle4f(rollAxis.x, rollAxis.y, rollAxis.z, rollDeltAngle);
/*  40 */     AxisAngle4f yawRot = new AxisAngle4f(yawAxis.x, yawAxis.y, yawAxis.z, yawDeltAngle);
/*     */     
/*  42 */     Quat4f rollQuat = new Quat4f();
/*  43 */     Quat4f pitchQuat = new Quat4f();
/*  44 */     Quat4f yawQuat = new Quat4f();
/*     */     
/*  46 */     rollQuat.set(rollRot);
/*  47 */     pitchQuat.set(pitchRot);
/*  48 */     yawQuat.set(yawRot);
/*     */     
/*  50 */     rollQuat.mul(pitchQuat);
/*  51 */     rollQuat.mul(yawQuat);
/*     */     
/*  53 */     this.flyBar = this.helper.rotateVector(rollQuat, this.flyBar);
/*  54 */     this.tailBar = this.helper.rotateVector(rollQuat, this.tailBar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateSafeBars(Vector3f pitchAxis, Vector3f yawAxis, Vector3f rollAxis, float pitchControl, float yawControl, float rollControl, float deltaTime)
/*     */   {
/*  64 */     float pitchDeltAngle = pitchControl * 0.349066F;
/*  65 */     float rollDeltAngle = rollControl * 0.349066F;
/*  66 */     float yawDeltAngle = virtualBarLimit(this.tailBar, pitchAxis, yawAxis, yawControl * (float)Math.sqrt(Math.abs(yawControl)) * deltaTime * this.scaleFactor);
/*     */     
/*  68 */     AxisAngle4f pitchRot = new AxisAngle4f(pitchAxis.x, pitchAxis.y, pitchAxis.z, pitchDeltAngle);
/*  69 */     AxisAngle4f rollRot = new AxisAngle4f(rollAxis.x, rollAxis.y, rollAxis.z, rollDeltAngle);
/*  70 */     AxisAngle4f yawRot = new AxisAngle4f(yawAxis.x, yawAxis.y, yawAxis.z, yawDeltAngle);
/*     */     
/*  72 */     Quat4f rollQuat = new Quat4f();
/*  73 */     Quat4f pitchQuat = new Quat4f();
/*  74 */     Quat4f yawQuat = new Quat4f();
/*     */     
/*  76 */     rollQuat.set(rollRot);
/*  77 */     pitchQuat.set(pitchRot);
/*  78 */     yawQuat.set(yawRot);
/*     */     
/*  80 */     rollQuat.mul(pitchQuat);
/*  81 */     rollQuat.mul(yawQuat);
/*     */     
/*  83 */     this.flyBar = this.helper.rotateVector(rollQuat, this.flyBar);
/*  84 */     this.tailBar = this.helper.rotateVector(rollQuat, this.tailBar);
/*     */   }
/*     */   
/*     */   public void resetFlybars(Vector3f Forward)
/*     */   {
/*  89 */     this.flyBar.set(0.0F, 1.0F, 0.0F);
/*  90 */     this.tailBar.set(Forward);
/*     */   }
/*     */   
/*     */   public void updateFlyPath(Vector3f pitchAxis, Vector3f yawAxis, Vector3f rollAxis, @Nullable Vector3f targetPath, Vector3f frontPath)
/*     */   {
/*  95 */     if (targetPath == null)
/*     */     {
/*  97 */       this.flightPath.set(frontPath);
/*  98 */       this.flightPath.scale(-1.0F);
/*     */     }
/*     */     else
/*     */     {
/* 102 */       this.flightPath.set(targetPath);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public float virtualBarLimit(Vector3f bar, Vector3f refAxis, Vector3f subAxis, float deltaAngle)
/*     */   {
/* 109 */     Vector3f subtractAxis = new Vector3f(subAxis);
/* 110 */     float subtractQty = subtractAxis.dot(bar);
/* 111 */     subtractAxis.scale(subtractQty);
/*     */     
/* 113 */     Vector3f refBar = new Vector3f(bar);
/* 114 */     refBar.sub(subtractAxis);
/*     */     
/* 116 */     float diffAngle = refBar.angle(refAxis) - this.helper.degToRad * 90.0F;
/*     */     
/* 118 */     float unitAngle = 0.0F;
/*     */     
/* 120 */     if (Math.abs(diffAngle) > 0.0F)
/*     */     {
/* 122 */       unitAngle = diffAngle / Math.abs(diffAngle);
/*     */     }
/*     */     
/* 125 */     if (Math.abs(diffAngle - deltaAngle) > this.limit)
/*     */     {
/* 127 */       if (Math.abs(diffAngle) > this.limit)
/*     */       {
/* 129 */         return diffAngle - this.limit * unitAngle;
/*     */       }
/*     */       
/*     */ 
/* 133 */       return this.limit * unitAngle - diffAngle;
/*     */     }
/*     */     
/* 136 */     return deltaAngle;
/*     */   }
/*     */   
/*     */   public float virtualBarLimit2(Vector3f bar, Vector3f refAxis, Vector3f subAxis, float deltaAngle)
/*     */   {
/* 141 */     Vector3f subtractAxis = new Vector3f(subAxis);
/* 142 */     float subtractQty = subtractAxis.dot(bar);
/* 143 */     subtractAxis.scale(subtractQty);
/*     */     
/* 145 */     Vector3f refBar = new Vector3f(bar);
/* 146 */     refBar.sub(subtractAxis);
/*     */     
/* 148 */     float diffAngle = refBar.angle(refAxis) - this.helper.degToRad * 90.0F;
/*     */     
/* 150 */     float unitAngle = 0.0F;
/*     */     
/* 152 */     if (Math.abs(diffAngle) > 0.0F)
/*     */     {
/* 154 */       unitAngle = diffAngle / Math.abs(diffAngle);
/*     */     }
/*     */     
/* 157 */     if (Math.abs(diffAngle + deltaAngle) > this.limit)
/*     */     {
/* 159 */       if (Math.abs(diffAngle) > this.limit)
/*     */       {
/* 161 */         return this.limit * unitAngle - diffAngle;
/*     */       }
/*     */       
/*     */ 
/* 165 */       return diffAngle - this.limit * unitAngle;
/*     */     }
/*     */     
/* 168 */     return deltaAngle;
/*     */   }
/*     */   
/*     */   public Vector3f getVirtualFlyBar()
/*     */   {
/* 173 */     return this.flyBar;
/*     */   }
/*     */   
/*     */   public Vector3f getVirtualTailBar()
/*     */   {
/* 178 */     return this.tailBar;
/*     */   }
/*     */   
/*     */   public Vector3f getVirtualFlightPath()
/*     */   {
/* 183 */     return this.flightPath;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/VirtualReferenceHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */