/*    */ package RCM.Physics;
/*    */ 
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ public class RocketMotorHandler
/*    */ {
/*    */   private float maxThrust;
/*    */   private float burnTime;
/*    */   private float speedReference;
/*    */   private boolean activated;
/*    */   
/*    */   public RocketMotorHandler(float mThrust, float bt)
/*    */   {
/* 14 */     this.maxThrust = mThrust;
/* 15 */     this.burnTime = bt;
/* 16 */     this.speedReference = 60.0F;
/* 17 */     this.activated = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public Vector3f getThurstVect(Vector3f forward, float velocity, float bt, float targetRef, float decentVel)
/*    */   {
/* 23 */     if ((this.activated) && (targetRef >= 17.0F) && (decentVel >= 0.0F))
/*    */     {
/* 25 */       this.activated = false;
/*    */     }
/* 27 */     else if ((decentVel < -5.0F) && (targetRef >= 15.0F))
/*    */     {
/* 29 */       this.activated = true;
/*    */     }
/*    */     
/* 32 */     if ((this.burnTime > 0.0F) && (this.activated))
/*    */     {
/* 34 */       this.burnTime -= bt;
/* 35 */       Vector3f thrust = new Vector3f(forward);
/* 36 */       thrust.scale(this.maxThrust * ((this.speedReference - velocity) / this.speedReference));
/* 37 */       return thrust;
/*    */     }
/*    */     
/*    */ 
/* 41 */     return new Vector3f();
/*    */   }
/*    */   
/*    */ 
/*    */   public void setActive()
/*    */   {
/* 47 */     this.activated = true;
/*    */   }
/*    */   
/*    */   public boolean isActive()
/*    */   {
/* 52 */     if ((this.burnTime > 0.0F) && (this.activated))
/*    */     {
/* 54 */       return true;
/*    */     }
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/RocketMotorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */