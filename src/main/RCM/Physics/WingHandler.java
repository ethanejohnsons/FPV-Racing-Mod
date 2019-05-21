/*     */ package RCM.Physics;
/*     */ 
/*     */ import javax.vecmath.Vector3f;
/*     */ 
/*     */ public class WingHandler { public PhysicsHelper helper;
/*     */   public Vector3f localSpanVect;
/*     */   public Vector3f localLiftVect;
/*     */   public Vector3f localChordVect;
/*     */   public Vector3f localPositionVect;
/*     */   public Vector3f propellerVel;
/*     */   public Vector3f globalLiftVect;
/*     */   public Vector3f globalSpanVect;
/*     */   public Vector3f globalChordVect;
/*     */   public Vector3f globalPosition;
/*     */   private float spanOffset;
/*     */   private float span;
/*     */   private float forcePos;
/*     */   private float profileType;
/*     */   private float def;
/*     */   private float defOffset;
/*     */   private float forceLimit;
/*     */   private float bladeTorque;
/*     */   private float sectionSpan;
/*     */   private float propAngle;
/*     */   private boolean waterHandle;
/*     */   public float angularVelocity;
/*     */   public float eqFactor;
/*     */   private int sections;
/*     */   private int profile;
/*     */   private int channel;
/*     */   private int ID;
/*     */   private int sensorID;
/*  33 */   private float[] sectionAreas; private float[] sectionChord; private float[] sectionPos; private float[] sectionVel; private float[] sectionVelSq; private float[] sectionDownWash; private float[] sectionCoeffLift; private float[] sectionGeoAoA; private java.util.List<Vector3f> sectionVelVect = new java.util.ArrayList();
/*  34 */   private java.util.List<Vector3f> sectionLift = new java.util.ArrayList();
/*  35 */   private java.util.List<Vector3f> sectionDrag = new java.util.ArrayList();
/*  36 */   private java.util.List<Vector3f> sectionMoment = new java.util.ArrayList();
/*  37 */   private java.util.List<Vector3f> sectionPosVect = new java.util.ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */   public WingHandler(int wingID2, Vector3f spanVec2, Vector3f liftVec2, Vector3f chordVec2, Vector3f pos2, float spanOffset2, float wingArea2, float wingSpan2, float rootChord2, float tipChord2, float def2, float defOffset2, int profileType, int channel2, int sect, float eqFactor2, int sensorID2)
/*     */   {
/*  43 */     this.helper = new PhysicsHelper();
/*     */     
/*  45 */     this.ID = wingID2;
/*     */     
/*  47 */     this.sections = sect;
/*     */     
/*  49 */     this.globalSpanVect = new Vector3f();
/*  50 */     this.globalLiftVect = new Vector3f();
/*  51 */     this.globalChordVect = new Vector3f();
/*  52 */     this.globalPosition = new Vector3f();
/*     */     
/*  54 */     this.localSpanVect = new Vector3f(spanVec2);
/*  55 */     this.localLiftVect = new Vector3f(liftVec2);
/*  56 */     this.localChordVect = new Vector3f(chordVec2);
/*  57 */     this.localPositionVect = new Vector3f(pos2);
/*     */     
/*  59 */     this.spanOffset = spanOffset2;
/*  60 */     this.span = wingSpan2;
/*  61 */     this.def = (def2 * this.helper.degToRad);
/*  62 */     this.defOffset = (defOffset2 * this.helper.degToRad);
/*  63 */     this.sectionSpan = ((wingSpan2 - this.spanOffset) / this.sections);
/*  64 */     this.sectionAreas = new float[this.sections];
/*  65 */     this.sectionChord = new float[this.sections];
/*  66 */     this.sectionPos = new float[this.sections];
/*  67 */     this.sectionVelSq = new float[this.sections];
/*  68 */     this.sectionVel = new float[this.sections];
/*  69 */     this.sectionDownWash = new float[this.sections];
/*  70 */     this.sectionCoeffLift = new float[this.sections];
/*  71 */     this.sectionGeoAoA = new float[this.sections];
/*  72 */     this.profile = profileType;
/*  73 */     this.channel = channel2;
/*  74 */     this.eqFactor = eqFactor2;
/*  75 */     this.sensorID = sensorID2;
/*     */     
/*  77 */     this.forceLimit = 100.0F;
/*     */     
/*  79 */     calculateWingSectionProperties(wingArea2, rootChord2, tipChord2);
/*     */   }
/*     */   
/*     */ 
/*     */   private void calculateWingSectionProperties(float totalArea, float root, float tip)
/*     */   {
/*  85 */     float sectionSpanPos = this.sectionSpan / 2.0F;
/*  86 */     float sectionChordSum = 0.0F;
/*     */     
/*  88 */     for (int i = 0; i < this.sections; i++)
/*     */     {
/*  90 */       this.sectionPos[i] = (sectionSpanPos + this.spanOffset);
/*  91 */       float sectChord = root - (root - tip) * sectionSpanPos / (this.span - this.spanOffset);
/*  92 */       this.sectionChord[i] = sectChord;
/*  93 */       sectionChordSum += sectChord;
/*  94 */       sectionSpanPos += this.sectionSpan;
/*     */       
/*  96 */       this.sectionVelVect.add(new Vector3f());
/*  97 */       this.sectionLift.add(new Vector3f());
/*  98 */       this.sectionDrag.add(new Vector3f());
/*  99 */       this.sectionPosVect.add(new Vector3f());
/* 100 */       this.sectionMoment.add(new Vector3f());
/*     */     }
/*     */     
/* 103 */     for (int i = 0; i < this.sections; i++)
/*     */     {
/* 105 */       this.sectionAreas[i] = (totalArea * this.sectionChord[i] / sectionChordSum);
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(javax.vecmath.Quat4f localQuat, Vector3f linearVel, Vector3f rotationalVel, float posY, float density, float floatReference, float angle, boolean waterAllow)
/*     */   {
/* 111 */     this.globalSpanVect.set(this.helper.rotateVector(localQuat, this.localSpanVect));
/* 112 */     this.globalLiftVect.set(this.helper.rotateVector(localQuat, this.localLiftVect));
/* 113 */     this.globalChordVect.set(this.helper.rotateVector(localQuat, this.localChordVect));
/* 114 */     this.globalPosition.set(this.helper.rotateVector(localQuat, this.localPositionVect));
/*     */     
/* 116 */     javax.vecmath.Quat4f deflection = new javax.vecmath.Quat4f();
/*     */     
/* 118 */     this.propAngle = (this.defOffset + angle * this.def);
/* 119 */     deflection.set(new javax.vecmath.AxisAngle4f(this.globalSpanVect, this.propAngle));
/*     */     
/* 121 */     Vector3f deflLift = new Vector3f(this.helper.rotateVector(deflection, this.globalLiftVect));
/* 122 */     Vector3f deflChord = new Vector3f(this.globalSpanVect);
/* 123 */     deflChord.cross(deflChord, deflLift);
/*     */     
/* 125 */     if (deflChord.dot(this.globalChordVect) < 0.0F)
/*     */     {
/* 127 */       deflChord.scale(-1.0F);
/*     */     }
/*     */     
/* 130 */     Vector3f downWashVect = new Vector3f();
/* 131 */     Vector3f liftVect = new Vector3f();
/* 132 */     Vector3f dragVect = new Vector3f();
/* 133 */     Vector3f momentVect = new Vector3f();
/*     */     
/* 135 */     for (int i = 0; i < this.sections; i++)
/*     */     {
/* 137 */       Vector3f sectionVect = new Vector3f(this.globalSpanVect);
/*     */       
/* 139 */       sectionVect.scale(this.sectionPos[i]);
/* 140 */       sectionVect.add(this.globalPosition);
/* 141 */       ((Vector3f)this.sectionPosVect.get(i)).set(sectionVect);
/*     */       
/* 143 */       float propRotVel = this.sectionPos[i] * -this.angularVelocity;
/*     */       
/* 145 */       ((Vector3f)this.sectionVelVect.get(i)).set(this.globalChordVect);
/* 146 */       ((Vector3f)this.sectionVelVect.get(i)).scale(propRotVel);
/*     */       
/* 148 */       ((Vector3f)this.sectionVelVect.get(i)).add(this.helper.getVelocityAtPoint(linearVel, rotationalVel, sectionVect));
/* 149 */       ((Vector3f)this.sectionVelVect.get(i)).scale(-1.0F);
/*     */       
/* 151 */       Vector3f spanVel = new Vector3f(this.globalSpanVect);
/* 152 */       float spanVelComp = ((Vector3f)this.sectionVelVect.get(i)).dot(spanVel);
/* 153 */       spanVel.scale(spanVelComp);
/* 154 */       ((Vector3f)this.sectionVelVect.get(i)).sub(spanVel);
/*     */       
/* 156 */       this.sectionVel[i] = ((Vector3f)this.sectionVelVect.get(i)).length();
/* 157 */       this.sectionVelSq[i] = (this.sectionVel[i] * this.sectionVel[i]);
/*     */       
/* 159 */       this.sectionGeoAoA[i] = this.helper.getAoA(deflChord, deflLift, (Vector3f)this.sectionVelVect.get(i));
/*     */       
/*     */ 
/*     */ 
/* 163 */       float spanLoc = this.sectionPos[i] / this.span;
/* 164 */       float effectiveAoA = 0.0F;
/* 165 */       float zeroLiftOffset = 0.0F;
/*     */       
/* 167 */       if (this.profile == 3)
/*     */       {
/* 169 */         zeroLiftOffset = 5.27F * this.helper.degToRad;
/*     */       }
/*     */       
/* 172 */       float AoA = this.sectionGeoAoA[i] + zeroLiftOffset;
/* 173 */       float inducedAoA = 0.0F;
/*     */       
/* 175 */       if (this.sectionGeoAoA[i] > 1.570796F)
/*     */       {
/* 177 */         AoA = 3.1415927F - this.sectionGeoAoA[i] - zeroLiftOffset;
/*     */       }
/* 179 */       else if (this.sectionGeoAoA[i] < -1.570796F)
/*     */       {
/* 181 */         AoA = -3.1415927F - this.sectionGeoAoA[i] - zeroLiftOffset;
/*     */       }
/*     */       
/* 184 */       if (spanLoc <= 0.4183F)
/*     */       {
/* 186 */         effectiveAoA = AoA * (1.0F - 0.3782F * spanLoc);
/*     */       }
/*     */       else
/*     */       {
/* 190 */         effectiveAoA = AoA * (float)Math.sqrt(0.81F - (spanLoc - 0.1F) * (spanLoc - 0.1F));
/*     */       }
/*     */       
/* 193 */       inducedAoA = AoA - effectiveAoA;
/* 194 */       this.sectionDownWash[i] = (this.sectionVel[i] * (float)Math.tan(inducedAoA));
/*     */       
/* 196 */       if ((this.sectionGeoAoA[i] > 1.570796F) || (this.sectionGeoAoA[i] < -1.570796F))
/*     */       {
/* 198 */         inducedAoA *= -1.0F;
/*     */       }
/*     */       
/* 201 */       effectiveAoA = (this.sectionGeoAoA[i] - inducedAoA) * this.helper.radToDeg;
/*     */       
/* 203 */       if (effectiveAoA > 180.0F)
/*     */       {
/* 205 */         effectiveAoA -= 360.0F;
/*     */       }
/*     */       
/* 208 */       if (effectiveAoA < -180.0F)
/*     */       {
/* 210 */         effectiveAoA += 360.0F;
/*     */       }
/*     */       
/* 213 */       float wingLiftCoef = 0.0F;
/*     */       
/* 215 */       switch (this.profile)
/*     */       {
/*     */       case 1: 
/* 218 */         wingLiftCoef = RCM.RCM_Main.aerofoilProperties.getThinPlateCl(effectiveAoA);
/* 219 */         break;
/*     */       case 2: 
/* 221 */         wingLiftCoef = RCM.RCM_Main.aerofoilProperties.getNaca12Cl(effectiveAoA);
/* 222 */         break;
/*     */       case 3: 
/* 224 */         wingLiftCoef = RCM.RCM_Main.aerofoilProperties.getClarkYCl(effectiveAoA);
/* 225 */         break;
/*     */       }
/*     */       
/*     */       
/*     */ 
/* 230 */       boolean inWater = posY + ((Vector3f)this.sectionPosVect.get(i)).y <= floatReference;
/*     */       
/* 232 */       float lift = 0.5F * density * this.sectionVelSq[i] * wingLiftCoef * this.sectionAreas[i];
/*     */       
/* 234 */       if ((inWater) && (waterAllow))
/*     */       {
/* 236 */         density = 997.0F;
/* 237 */         lift = 0.5F * density * this.sectionVel[i] * wingLiftCoef * this.sectionAreas[i];
/*     */       }
/*     */       
/* 240 */       downWashVect.cross((Vector3f)this.sectionVelVect.get(i), this.globalSpanVect);
/*     */       
/* 242 */       if (downWashVect.dot(deflLift) > 0.0F)
/*     */       {
/* 244 */         downWashVect.scale(-1.0F);
/*     */       }
/*     */       
/* 247 */       if (downWashVect.length() > 0.0F)
/*     */       {
/* 249 */         downWashVect.normalize();
/*     */       }
/*     */       
/* 252 */       downWashVect.scale(this.sectionDownWash[i]);
/*     */       
/* 254 */       liftVect.set((javax.vecmath.Tuple3f)this.sectionVelVect.get(i));
/* 255 */       liftVect.add(downWashVect);
/*     */       
/* 257 */       if (liftVect.length() > 0.0F)
/*     */       {
/* 259 */         liftVect.normalize();
/*     */       }
/*     */       
/* 262 */       ((Vector3f)this.sectionLift.get(i)).cross(liftVect, this.globalSpanVect);
/*     */       
/* 264 */       if ((this.sectionGeoAoA[i] <= 1.570796F) && (this.sectionGeoAoA[i] >= -1.570796F) && (((Vector3f)this.sectionLift.get(i)).dot(deflLift) < 0.0F))
/*     */       {
/* 266 */         ((Vector3f)this.sectionLift.get(i)).scale(-1.0F);
/*     */       }
/* 268 */       else if (((this.sectionGeoAoA[i] > 1.570796F) || (this.sectionGeoAoA[i] < -1.570796F)) && (((Vector3f)this.sectionLift.get(i)).dot(deflLift) > 0.0F))
/*     */       {
/* 270 */         ((Vector3f)this.sectionLift.get(i)).scale(-1.0F);
/*     */       }
/*     */       
/* 273 */       if (((Vector3f)this.sectionLift.get(i)).length() > 0.0F)
/*     */       {
/* 275 */         ((Vector3f)this.sectionLift.get(i)).normalize();
/*     */       }
/*     */       
/* 278 */       ((Vector3f)this.sectionLift.get(i)).scale(lift * this.eqFactor);
/*     */       
/* 280 */       float profileDragCoef = 0.0F;
/* 281 */       float profileMoment = 0.0F;
/*     */       
/* 283 */       float AoA2 = this.sectionGeoAoA[i] * this.helper.radToDeg;
/*     */       
/* 285 */       switch (this.profile)
/*     */       {
/*     */       case 1: 
/* 288 */         profileDragCoef = RCM.RCM_Main.aerofoilProperties.getThinPlateCd(AoA2);
/* 289 */         profileMoment = RCM.RCM_Main.aerofoilProperties.getThinPlateCm(AoA2);
/* 290 */         break;
/*     */       case 2: 
/* 292 */         profileDragCoef = RCM.RCM_Main.aerofoilProperties.getNaca12Cd(AoA2);
/* 293 */         profileMoment = RCM.RCM_Main.aerofoilProperties.getNaca12Cm(AoA2);
/* 294 */         break;
/*     */       case 3: 
/* 296 */         profileDragCoef = RCM.RCM_Main.aerofoilProperties.getClarkYCd(AoA2);
/* 297 */         profileMoment = RCM.RCM_Main.aerofoilProperties.getClarkYCm(AoA2);
/* 298 */         break;
/*     */       }
/*     */       
/*     */       
/*     */ 
/* 303 */       density = 1.225F;
/*     */       
/* 305 */       float drag = 0.5F * density * this.sectionVelSq[i] * profileDragCoef * this.sectionAreas[i];
/* 306 */       float moment = 0.5F * density * this.sectionVelSq[i] * profileMoment * this.sectionAreas[i];
/*     */       
/* 308 */       if ((inWater) && (waterAllow))
/*     */       {
/* 310 */         drag = 0.5F * density * this.sectionVel[i] * profileDragCoef * this.sectionAreas[i];
/* 311 */         moment = 0.5F * density * this.sectionVel[i] * profileMoment * this.sectionAreas[i];
/*     */       }
/*     */       
/* 314 */       if ((this.sectionGeoAoA[i] > 1.570796F) || (this.sectionGeoAoA[i] < -1.570796F))
/*     */       {
/* 316 */         moment *= -1.0F;
/*     */       }
/*     */       
/* 319 */       dragVect.set((javax.vecmath.Tuple3f)this.sectionVelVect.get(i));
/* 320 */       momentVect.cross(deflChord, deflLift);
/*     */       
/* 322 */       if (dragVect.length() > 0.0F)
/*     */       {
/* 324 */         dragVect.normalize();
/*     */       }
/*     */       
/* 327 */       dragVect.scale(drag * this.eqFactor);
/* 328 */       momentVect.scale(moment * this.eqFactor);
/*     */       
/* 330 */       ((Vector3f)this.sectionDrag.get(i)).set(dragVect);
/* 331 */       ((Vector3f)this.sectionMoment.get(i)).set(momentVect);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public float getPropAngle()
/*     */   {
/* 338 */     return this.propAngle;
/*     */   }
/*     */   
/*     */ 
/*     */   public Vector3f getLift(int i)
/*     */   {
/* 344 */     return (Vector3f)this.sectionLift.get(i);
/*     */   }
/*     */   
/*     */   public Vector3f getDrag(int i)
/*     */   {
/* 349 */     return (Vector3f)this.sectionDrag.get(i);
/*     */   }
/*     */   
/*     */   public Vector3f getMoment(int i)
/*     */   {
/* 354 */     return (Vector3f)this.sectionMoment.get(i);
/*     */   }
/*     */   
/*     */ 
/*     */   public Vector3f getPosition(int i)
/*     */   {
/* 360 */     return (Vector3f)this.sectionPosVect.get(i);
/*     */   }
/*     */   
/*     */   public float getAngularVelocity()
/*     */   {
/* 365 */     return this.angularVelocity;
/*     */   }
/*     */   
/*     */   public int getChannel()
/*     */   {
/* 370 */     return this.channel;
/*     */   }
/*     */   
/*     */   public int getSections()
/*     */   {
/* 375 */     return this.sections;
/*     */   }
/*     */   
/*     */   public int getID()
/*     */   {
/* 380 */     return this.ID;
/*     */   }
/*     */   
/*     */   public Vector3f getPosition()
/*     */   {
/* 385 */     return this.localPositionVect;
/*     */   }
/*     */   
/*     */   public int getSensorID()
/*     */   {
/* 390 */     return this.sensorID;
/*     */   }
/*     */   
/*     */   public float getTipAoA()
/*     */   {
/* 395 */     return this.sectionGeoAoA[(this.sections - 1)] * this.helper.radToDeg;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/WingHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */