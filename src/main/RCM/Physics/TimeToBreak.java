/*    */ package RCM.Physics;
/*    */ 
/*    */ 
/*    */ public class TimeToBreak
/*    */ {
/*    */   private float damageTime;
/*    */   
/*    */   public TimeToBreak(float dt)
/*    */   {
/* 10 */     this.damageTime = dt;
/*    */   }
/*    */   
/*    */   public float update(float deltaTime)
/*    */   {
/* 15 */     this.damageTime -= deltaTime;
/*    */     
/* 17 */     return this.damageTime;
/*    */   }
/*    */   
/*    */   public void setDamageTime(float dt)
/*    */   {
/* 22 */     this.damageTime = dt;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/TimeToBreak.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */