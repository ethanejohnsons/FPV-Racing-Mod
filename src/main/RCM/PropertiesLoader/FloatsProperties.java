/*    */ package RCM.PropertiesLoader;
/*    */ 
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ public class FloatsProperties
/*    */ {
/*    */   public Vector3f position;
/*    */   public Vector3f spanVec;
/*    */   public float dragCoef;
/*    */   public float radius;
/*    */   public int sections;
/*    */   
/*    */   public FloatsProperties(Vector3f position2, Vector3f spanVec2, float drgC, float rad, int sect)
/*    */   {
/* 15 */     this.position = position2;
/* 16 */     this.sections = sect;
/* 17 */     this.dragCoef = drgC;
/* 18 */     this.radius = rad;
/* 19 */     this.spanVec = spanVec2;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/FloatsProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */