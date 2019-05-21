/*    */ package RCM.Physics;
/*    */ 
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Force
/*    */ {
/*    */   private Vector3f position;
/*    */   private Vector3f force;
/*    */   
/*    */   public Force(Vector3f forc, Vector3f pos)
/*    */   {
/* 14 */     this.position = pos;
/* 15 */     this.force = forc;
/*    */   }
/*    */   
/*    */   public Vector3f getForce()
/*    */   {
/* 20 */     return this.force;
/*    */   }
/*    */   
/*    */   public Vector3f getPosition()
/*    */   {
/* 25 */     return this.position;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/Force.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */