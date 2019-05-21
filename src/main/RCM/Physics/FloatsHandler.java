/*     */ package RCM.Physics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.vecmath.Quat4f;
/*     */ import javax.vecmath.Tuple3f;
/*     */ import javax.vecmath.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FloatsHandler
/*     */ {
/*     */   private PhysicsHelper helper;
/*     */   private int sections;
/*     */   private float radius;
/*     */   private float dragCoef;
/*     */   private float totalVolume;
/*     */   private float totalArea;
/*     */   private float[] sectionPos;
/*     */   private float[] sectionVel;
/*  22 */   private List<Vector3f> sectionVelVect = new ArrayList();
/*  23 */   private List<Vector3f> sectionBouy = new ArrayList();
/*  24 */   private List<Vector3f> sectionDrag = new ArrayList();
/*  25 */   private List<Vector3f> sectionPosVect = new ArrayList();
/*     */   private Vector3f globalSpanVect;
/*     */   private Vector3f globalPosition;
/*     */   private Vector3f localSpanVect;
/*     */   private Vector3f localPositionVect;
/*     */   
/*     */   public FloatsHandler(Vector3f pos, Vector3f spanV, float drgC, float rad, int sect) {
/*  32 */     this.helper = new PhysicsHelper();
/*     */     
/*  34 */     this.sections = sect;
/*  35 */     this.radius = rad;
/*  36 */     this.dragCoef = drgC;
/*  37 */     this.totalVolume = (1.3333334F * (float)(3.141592653589793D * Math.pow(rad, 3.0D)));
/*  38 */     this.totalArea = (4.0F * (float)(3.141592653589793D * Math.pow(rad, 2.0D)));
/*     */     
/*  40 */     this.globalSpanVect = new Vector3f();
/*  41 */     this.globalPosition = new Vector3f();
/*     */     
/*  43 */     this.localSpanVect = new Vector3f(spanV);
/*  44 */     this.localPositionVect = new Vector3f(pos);
/*     */     
/*  46 */     this.sectionPos = new float[this.sections];
/*  47 */     this.sectionVel = new float[this.sections];
/*     */     
/*  49 */     calculateFloatSectionProperties();
/*     */   }
/*     */   
/*     */   private void calculateFloatSectionProperties()
/*     */   {
/*  54 */     for (int i = 0; i < this.sections; i++)
/*     */     {
/*  56 */       this.sectionPos[i] = (this.radius * 2.0F * i);
/*     */       
/*  58 */       this.sectionVelVect.add(new Vector3f());
/*  59 */       this.sectionBouy.add(new Vector3f());
/*  60 */       this.sectionDrag.add(new Vector3f());
/*  61 */       this.sectionPosVect.add(new Vector3f());
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(Vector3f pos, Quat4f localQuat, Vector3f linearVel, Vector3f rotationalVel, float floatReference, float density)
/*     */   {
/*  67 */     this.globalSpanVect.set(this.helper.rotateVector(localQuat, this.localSpanVect));
/*  68 */     this.globalPosition.set(this.helper.rotateVector(localQuat, this.localPositionVect));
/*     */     
/*  70 */     Vector3f dragVect = new Vector3f();
/*  71 */     Vector3f surfVelVec = new Vector3f();
/*     */     
/*  73 */     for (int i = 0; i < this.sections; i++)
/*     */     {
/*  75 */       Vector3f sectionVect = new Vector3f(this.globalSpanVect);
/*     */       
/*  77 */       sectionVect.scale(this.sectionPos[i]);
/*  78 */       sectionVect.add(this.globalPosition);
/*     */       
/*  80 */       ((Vector3f)this.sectionPosVect.get(i)).set(sectionVect);
/*  81 */       ((Vector3f)this.sectionVelVect.get(i)).set(this.helper.getVelocityAtPoint(linearVel, rotationalVel, sectionVect));
/*  82 */       ((Vector3f)this.sectionVelVect.get(i)).scale(-1.0F);
/*     */       
/*  84 */       this.sectionVel[i] = ((Vector3f)this.sectionVelVect.get(i)).length();
/*  85 */       surfVelVec.set(((Vector3f)this.sectionVelVect.get(i)).x, 0.0F, ((Vector3f)this.sectionVelVect.get(i)).z);
/*  86 */       float surfaceVel = surfVelVec.length();
/*     */       
/*  88 */       float volume = 0.0F;
/*  89 */       float area = 0.0F;
/*  90 */       float lift = 0.0F;
/*     */       
/*  92 */       if (pos.y + sectionVect.y + this.radius <= floatReference)
/*     */       {
/*  94 */         volume = this.totalVolume;
/*  95 */         area = this.totalArea;
/*     */       }
/*  97 */       else if (pos.y + sectionVect.y <= floatReference)
/*     */       {
/*  99 */         float capHeight = floatReference - (sectionVect.y + pos.y);
/* 100 */         float capVolume = (3.0F * this.radius - capHeight) * (this.helper.PI * capHeight * capHeight) / 3.0F;
/* 101 */         volume = this.totalVolume - capVolume;
/*     */         
/* 103 */         float capArea = 2.0F * this.helper.PI * this.radius * capHeight;
/* 104 */         area = this.totalArea - capArea;
/*     */ 
/*     */       }
/* 107 */       else if (pos.y + sectionVect.y - this.radius <= floatReference)
/*     */       {
/* 109 */         float capHeight = floatReference - (sectionVect.y - this.radius + pos.y);
/* 110 */         volume = (3.0F * this.radius - capHeight) * (this.helper.PI * capHeight * capHeight) / 3.0F;
/* 111 */         area = 2.0F * this.helper.PI * this.radius * capHeight;
/*     */         
/* 113 */         lift = 0.25F * density * surfaceVel * surfaceVel * this.dragCoef * area;
/*     */       }
/*     */       
/* 116 */       float bouyancy = density * 9.81F * volume + lift;
/* 117 */       float drag = 0.5F * density * this.sectionVel[i] * this.dragCoef * area;
/*     */       
/* 119 */       dragVect.set((Tuple3f)this.sectionVelVect.get(i));
/*     */       
/* 121 */       if (dragVect.length() > 0.0F)
/*     */       {
/* 123 */         dragVect.normalize();
/*     */       }
/*     */       
/* 126 */       dragVect.scale(drag);
/*     */       
/* 128 */       ((Vector3f)this.sectionBouy.get(i)).setY(bouyancy);
/* 129 */       ((Vector3f)this.sectionDrag.get(i)).set(dragVect);
/*     */     }
/*     */   }
/*     */   
/*     */   public Vector3f getPosition(int i)
/*     */   {
/* 135 */     return (Vector3f)this.sectionPosVect.get(i);
/*     */   }
/*     */   
/*     */   public Vector3f getBouyancy(int i)
/*     */   {
/* 140 */     return (Vector3f)this.sectionBouy.get(i);
/*     */   }
/*     */   
/*     */   public int getSections()
/*     */   {
/* 145 */     return this.sections;
/*     */   }
/*     */   
/*     */   public Vector3f getDrag(int i)
/*     */   {
/* 150 */     return (Vector3f)this.sectionDrag.get(i);
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/FloatsHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */