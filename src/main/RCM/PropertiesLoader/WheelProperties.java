/*    */ package RCM.PropertiesLoader;
/*    */ 
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ public class WheelProperties { public Vector3f connectionPoint;
/*    */   public Vector3f wheelDirection;
/*    */   public Vector3f wheelAxle;
/*    */   public float suspensionRestLength;
/*    */   public float radius;
/*    */   public float suspensionCompression;
/*    */   public float suspensionDamping;
/*    */   public float suspensionStiffness;
/*    */   public float rollInfluence;
/*    */   public float frictionSlip;
/*    */   public float breakForce;
/*    */   public float wheelMaxTurn;
/*    */   public float offSet;
/*    */   public boolean canTurn;
/*    */   public int channel;
/*    */   public int ID;
/*    */   
/* 22 */   public WheelProperties(int wheelID, Vector3f connectionPoint, Vector3f wheelDirection, Vector3f wheelAxle, float offset, float suspensionRestLength, float radius, float frictionSlip, float rollInfluence, float suspensionStiffness, float suspensionDamping, float suspensionCompression, float breakForce, boolean canTurn, float wheelTurn, int channel) { this.ID = wheelID;
/* 23 */     this.connectionPoint = new Vector3f(connectionPoint);
/* 24 */     this.connectionPoint.y += suspensionRestLength;
/* 25 */     this.connectionPoint.x += offset;
/* 26 */     this.wheelDirection = new Vector3f(wheelDirection);
/* 27 */     this.wheelAxle = new Vector3f(wheelAxle);
/* 28 */     this.suspensionRestLength = suspensionRestLength;
/* 29 */     this.radius = radius;
/* 30 */     this.frictionSlip = frictionSlip;
/* 31 */     this.rollInfluence = rollInfluence;
/* 32 */     this.suspensionStiffness = suspensionStiffness;
/* 33 */     this.suspensionDamping = suspensionDamping;
/* 34 */     this.suspensionCompression = suspensionCompression;
/* 35 */     this.breakForce = breakForce;
/* 36 */     this.offSet = offset;
/* 37 */     this.canTurn = canTurn;
/* 38 */     this.wheelMaxTurn = wheelTurn;
/* 39 */     this.channel = channel;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/WheelProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */