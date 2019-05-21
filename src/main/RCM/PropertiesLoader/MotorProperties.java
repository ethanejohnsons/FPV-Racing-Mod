/*    */ package RCM.PropertiesLoader;
/*    */ 
/*    */ public class MotorProperties
/*    */ {
/*    */   public int motorID;
/*    */   public int controlChannel;
/*    */   public int sensorID;
/*    */   public float motorPower;
/*    */   
/*    */   public MotorProperties(int motorID, float motorMass, float motorDiameter, float motorPower, float kVConstant, float inputVolts, float gearR, int senID, int controlChannel) {
/* 11 */     this.motorID = motorID;
/* 12 */     this.motorPower = motorPower;
/* 13 */     this.motorKVConstant = kVConstant;
/* 14 */     this.motorInputVolts = inputVolts;
/* 15 */     this.mass = motorMass;
/* 16 */     this.diameter = motorDiameter;
/* 17 */     this.controlChannel = controlChannel;
/* 18 */     this.gearRatio = gearR;
/* 19 */     this.sensorID = senID;
/*    */   }
/*    */   
/*    */   public float motorKVConstant;
/*    */   public float motorInputVolts;
/*    */   public float mass;
/*    */   public float diameter;
/*    */   public float gearRatio;
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/MotorProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */