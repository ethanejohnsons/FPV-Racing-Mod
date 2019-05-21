/*    */ package RCM.PropertiesLoader;
/*    */ 
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ public class RotaryWingProperties { public Vector3f spanVec;
/*    */   public Vector3f liftVec;
/*    */   public Vector3f chordVec;
/*    */   public Vector3f position;
/*    */   public float span;
/*    */   public float area;
/*    */   public float root;
/*    */   public float tip;
/*    */   public float def;
/*    */   
/* 15 */   public RotaryWingProperties(int propID2, float mass2, float ratio2, float eqFactor2, Vector3f spanVec2, Vector3f liftVec2, Vector3f chordVec2, Vector3f position2, float hubOffset2, float area2, float span2, float root2, float tip2, float def2, float defOffset2, int profileType2, int sensorID2, int sect, int channel2) { this.propID = propID2;
/* 16 */     this.mass = mass2;
/* 17 */     this.ratio = ratio2;
/* 18 */     this.eqFactor = eqFactor2;
/* 19 */     this.spanVec = spanVec2;
/* 20 */     this.liftVec = liftVec2;
/* 21 */     this.chordVec = chordVec2;
/* 22 */     this.position = position2;
/* 23 */     this.hubOffset = hubOffset2;
/* 24 */     this.area = area2;
/* 25 */     this.span = span2;
/* 26 */     this.root = root2;
/* 27 */     this.tip = tip2;
/* 28 */     this.def = def2;
/* 29 */     this.defOffset = defOffset2;
/* 30 */     this.profileType = profileType2;
/* 31 */     this.sensorID = sensorID2;
/* 32 */     this.controlChannel = channel2;
/* 33 */     this.sections = sect;
/*    */   }
/*    */   
/*    */   public float defOffset;
/*    */   public float mass;
/*    */   public float ratio;
/*    */   public float hubOffset;
/*    */   public float eqFactor;
/*    */   public int propID;
/*    */   public int profileType;
/*    */   public int controlChannel;
/*    */   public int sensorID;
/*    */   public int sections;
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/RotaryWingProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */