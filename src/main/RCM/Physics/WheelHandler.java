/*    */ package RCM.Physics;
/*    */ 
/*    */ public class WheelHandler {
/*    */   private int wheelID;
/*    */   private int channel;
/*    */   private float maxSteering;
/*    */   private float offset;
/*    */   private boolean isSteerable;
/*    */   
/*    */   public WheelHandler(int wheelID2, float maxSteering2, boolean isSteerable2, float offset2, int channel2) {
/* 11 */     this.wheelID = wheelID2;
/* 12 */     this.maxSteering = (maxSteering2 * 3.1415927F / 180.0F);
/* 13 */     this.isSteerable = isSteerable2;
/* 14 */     this.offset = offset2;
/* 15 */     this.channel = channel2;
/*    */   }
/*    */   
/*    */   public int getChannel()
/*    */   {
/* 20 */     return this.channel;
/*    */   }
/*    */   
/*    */   public int getID()
/*    */   {
/* 25 */     return this.wheelID;
/*    */   }
/*    */   
/*    */   public float getMaxSteering()
/*    */   {
/* 30 */     return this.maxSteering;
/*    */   }
/*    */   
/*    */   public boolean isSteerable()
/*    */   {
/* 35 */     return this.isSteerable;
/*    */   }
/*    */   
/*    */   public float getOffset()
/*    */   {
/* 40 */     return this.offset;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/WheelHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */