/*    */ package RCM.PropertiesLoader;
/*    */ 
/*    */ import javax.vecmath.AxisAngle4f;
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ 
/*    */ public class AttachmentProperties
/*    */ {
/*    */   public Vector3f position;
/*    */   public AxisAngle4f rotate;
/*    */   public int type;
/*    */   
/*    */   public AttachmentProperties(Vector3f position2, AxisAngle4f rotate2, int type2)
/*    */   {
/* 15 */     this.position = position2;
/* 16 */     this.rotate = rotate2;
/* 17 */     this.type = type2;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/AttachmentProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */