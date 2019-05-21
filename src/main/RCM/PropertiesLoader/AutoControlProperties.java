/*    */ package RCM.PropertiesLoader;
/*    */ 
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ public class AutoControlProperties
/*    */ {
/*    */   public int ID;
/*    */   public float linLimit;
/*    */   public float linGain;
/*    */   public float angGain;
/*    */   public float angLimit;
/*    */   public Vector3f sensorPoint;
/*    */   
/*    */   public AutoControlProperties(int sensorID2, Vector3f sensorPoint2, float linLimit2, float linGain2, float angGain2, float angLimit2) {
/* 15 */     this.ID = sensorID2;
/* 16 */     this.sensorPoint = sensorPoint2;
/* 17 */     this.linLimit = linLimit2;
/* 18 */     this.linGain = linGain2;
/* 19 */     this.angGain = angGain2;
/* 20 */     this.angLimit = angLimit2;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/AutoControlProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */