/*    */ package RCM.PropertiesLoader;
/*    */ 
/*    */ import javax.vecmath.Vector3f;
/*    */ 
/*    */ public class WingProperties
/*    */ {
/*    */   public Vector3f spanVec;
/*    */   public Vector3f liftVec;
/*    */   public Vector3f chordVec;
/*    */   public Vector3f position;
/*    */   public float span;
/*    */   public float area;
/*    */   public float root;
/*    */   
/*    */   public WingProperties(int wingID2, Vector3f spanVec2, Vector3f liftVec2, Vector3f chordVec2, Vector3f position2, float area2, float span2, float root2, float tip2, float def2, float defOffset2, int profileType2, int sensorID2, int sect, int channel2)
/*    */   {
/* 17 */     this.wingID = wingID2;
/* 18 */     this.spanVec = spanVec2;
/* 19 */     this.liftVec = liftVec2;
/* 20 */     this.chordVec = chordVec2;
/* 21 */     this.position = position2;
/* 22 */     this.area = area2;
/* 23 */     this.span = span2;
/* 24 */     this.root = root2;
/* 25 */     this.tip = tip2;
/* 26 */     this.def = def2;
/* 27 */     this.defOffset = defOffset2;
/* 28 */     this.profileType = profileType2;
/* 29 */     this.sensorID = sensorID2;
/* 30 */     this.controlChannel = channel2;
/* 31 */     this.sections = sect;
/*    */   }
/*    */   
/*    */   public float tip;
/*    */   public float def;
/*    */   public float defOffset;
/*    */   public int wingID;
/*    */   public int profileType;
/*    */   public int controlChannel;
/*    */   public int sensorID;
/*    */   public int sections;
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/WingProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */