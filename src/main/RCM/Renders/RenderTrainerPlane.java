/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Entities.EntityTrainerPlane;
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import RCM.Models.Model;
/*     */ import RCM.Physics.PhysicsHelper;
/*     */ import RCM.RCM_Main;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import javax.vecmath.Quat4f;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.FMLLog;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderTrainerPlane
/*     */   extends GlobalRender
/*     */ {
/*  27 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/trainerplaneskin.png");
/*     */   
/*  29 */   public Model rightAileron = null;
/*  30 */   public Model leftAileron = null;
/*  31 */   public Model rudder = null;
/*  32 */   public Model elevator = null;
/*  33 */   public Model propeller = null;
/*  34 */   public Model propellerBlur = null;
/*  35 */   public Model frontGear = null;
/*  36 */   public Model wheel = null;
/*  37 */   public Model mainGear = null;
/*  38 */   public Model mainGearLeft = null;
/*     */   
/*     */ 
/*     */   public RenderTrainerPlane()
/*     */   {
/*  43 */     this.shadowSize = 0.25F;
/*  44 */     String filePath = RCM_Main.modelFilePath + "trainerplane.rcm";
/*     */     
/*     */     try
/*     */     {
/*  48 */       this.mainBody = new Model();
/*  49 */       this.rightAileron = new Model();
/*  50 */       this.leftAileron = new Model();
/*  51 */       this.rudder = new Model();
/*  52 */       this.elevator = new Model();
/*  53 */       this.propeller = new Model();
/*  54 */       this.propellerBlur = new Model();
/*  55 */       this.frontGear = new Model();
/*  56 */       this.wheel = new Model();
/*  57 */       this.mainGear = new Model();
/*  58 */       this.mainGearLeft = new Model();
/*     */       
/*  60 */       this.mainBody.loadModel(filePath, "MainBody");
/*  61 */       this.rightAileron.loadModel(filePath, "RightAileron");
/*  62 */       this.leftAileron.loadModel(filePath, "LeftAileron");
/*  63 */       this.rudder.loadModel(filePath, "Rudder");
/*  64 */       this.elevator.loadModel(filePath, "Elevator");
/*  65 */       this.propeller.loadModel(filePath, "Propeller");
/*  66 */       this.propellerBlur.loadModel(filePath, "PropellerBlur");
/*  67 */       this.frontGear.loadModel(filePath, "FrontGear");
/*  68 */       this.wheel.loadModel(filePath, "Wheel");
/*  69 */       this.mainGear.loadModel(filePath, "MainGear");
/*  70 */       this.mainGearLeft.loadModel(filePath, "MainGearLeft");
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*  75 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  79 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderExtras(GlobalEntity rcentity, float timeStep)
/*     */   {
/*  87 */     EntityTrainerPlane trainerenity = (EntityTrainerPlane)rcentity;
/*     */     
/*  89 */     GL11.glPushMatrix();
/*     */     
/*  91 */     float dis = (trainerenity.prevState[10] + (trainerenity.state[10] - trainerenity.prevState[10]) * timeStep) * 0.5F;
/*  92 */     float def = -(float)(Math.asin(dis / 0.18511F) / 3.141592653589793D) * 180.0F;
/*     */     
/*  94 */     GL11.glTranslatef(-0.043F, -0.09252F, -0.10614F);
/*  95 */     GL11.glRotatef(def, 0.0F, 0.0F, 1.0F);
/*     */     
/*  97 */     GL11.glEnable(3042);
/*  98 */     GL11.glBlendFunc(770, 771);
/*  99 */     GL11.glBegin(4);
/*     */     
/* 101 */     this.mainGear.draw();
/*     */     
/* 103 */     GL11.glEnd();
/* 104 */     GL11.glDisable(3042);
/*     */     
/* 106 */     GL11.glPushMatrix();
/*     */     
/* 108 */     GL11.glTranslatef(-0.18511F, -0.091704F, -0.03213F);
/* 109 */     GL11.glRotatef(trainerenity.prevState[7] + (trainerenity.state[7] - trainerenity.prevState[7]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 112 */     GL11.glEnable(3042);
/* 113 */     GL11.glBlendFunc(770, 771);
/* 114 */     GL11.glBegin(4);
/*     */     
/* 116 */     this.wheel.draw();
/*     */     
/* 118 */     GL11.glEnd();
/* 119 */     GL11.glDisable(3042);
/*     */     
/* 121 */     GL11.glPopMatrix();
/*     */     
/* 123 */     GL11.glPopMatrix();
/*     */     
/* 125 */     GL11.glPushMatrix();
/*     */     
/* 127 */     dis = (trainerenity.prevState[11] + (trainerenity.state[11] - trainerenity.prevState[11]) * timeStep) * 0.5F;
/* 128 */     def = (float)(Math.asin(dis / 0.18511F) / 3.141592653589793D) * 180.0F;
/*     */     
/* 130 */     GL11.glTranslatef(0.043F, -0.09252F, -0.10614F);
/* 131 */     GL11.glRotatef(def, 0.0F, 0.0F, 1.0F);
/*     */     
/* 133 */     GL11.glEnable(3042);
/* 134 */     GL11.glBlendFunc(770, 771);
/* 135 */     GL11.glBegin(4);
/*     */     
/* 137 */     this.mainGearLeft.draw();
/*     */     
/* 139 */     GL11.glEnd();
/* 140 */     GL11.glDisable(3042);
/*     */     
/* 142 */     GL11.glPushMatrix();
/*     */     
/* 144 */     GL11.glTranslatef(0.18511F, -0.091704F, -0.03213F);
/* 145 */     GL11.glRotatef(trainerenity.prevState[8] + (trainerenity.state[8] - trainerenity.prevState[8]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 148 */     GL11.glEnable(3042);
/* 149 */     GL11.glBlendFunc(770, 771);
/* 150 */     GL11.glBegin(4);
/*     */     
/* 152 */     this.wheel.draw();
/*     */     
/* 154 */     GL11.glEnd();
/* 155 */     GL11.glDisable(3042);
/*     */     
/* 157 */     GL11.glPopMatrix();
/*     */     
/* 159 */     GL11.glPopMatrix();
/*     */     
/* 161 */     Vector3f localTrans = new Vector3f();
/* 162 */     Vector3f rotateAxis = new Vector3f();
/*     */     
/* 164 */     localTrans.set(0.0F, -0.15517F, 0.20623F);
/* 165 */     rotateAxis.set(trainerenity.helper.rotateVector(new Quat4f(-0.093F, 0.0F, 0.0F, 0.996F), new Vector3f(0.0F, 1.0F, 0.0F)));
/*     */     
/* 167 */     GL11.glPushMatrix();
/*     */     
/* 169 */     GL11.glTranslatef(localTrans.x, localTrans.y + (trainerenity.prevState[9] + (trainerenity.state[9] - trainerenity.prevState[9]) * timeStep), localTrans.z);
/*     */     
/* 171 */     GL11.glRotatef(trainerenity.prevState[5] + (trainerenity.state[5] - trainerenity.prevState[5]) * timeStep, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 174 */     GL11.glEnable(3042);
/* 175 */     GL11.glBlendFunc(770, 771);
/* 176 */     GL11.glBegin(4);
/*     */     
/* 178 */     this.frontGear.draw();
/*     */     
/* 180 */     GL11.glEnd();
/* 181 */     GL11.glDisable(3042);
/*     */     
/* 183 */     GL11.glPushMatrix();
/*     */     
/* 185 */     GL11.glTranslatef(0.0F, -0.03135F, 0.0058380067F);
/* 186 */     GL11.glRotatef(trainerenity.prevState[6] + (trainerenity.state[6] - trainerenity.prevState[6]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 189 */     GL11.glEnable(3042);
/* 190 */     GL11.glBlendFunc(770, 771);
/* 191 */     GL11.glBegin(4);
/*     */     
/* 193 */     this.wheel.draw();
/*     */     
/* 195 */     GL11.glEnd();
/* 196 */     GL11.glDisable(3042);
/*     */     
/* 198 */     GL11.glPopMatrix();
/* 199 */     GL11.glPopMatrix();
/*     */     
/* 201 */     localTrans.set(0.0F, -0.0077F, 0.31713F);
/* 202 */     rotateAxis = new Vector3f(0.0F, 0.0F, 1.0F);
/*     */     
/* 204 */     GL11.glPushMatrix();
/*     */     
/* 206 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 207 */     GL11.glRotatef(trainerenity.prevState[1] + (trainerenity.state[1] - trainerenity.prevState[1]) * timeStep * timeStep, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 210 */     GL11.glEnable(3042);
/* 211 */     GL11.glBlendFunc(770, 771);
/* 212 */     GL11.glBegin(4);
/*     */     
/* 214 */     this.propeller.draw();
/*     */     
/* 216 */     GL11.glEnd();
/* 217 */     GL11.glDisable(3042);
/*     */     
/* 219 */     GL11.glPopMatrix();
/*     */     
/* 221 */     GL11.glPushMatrix();
/*     */     
/* 223 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 224 */     GL11.glRotatef(trainerenity.prevState[1] + (trainerenity.state[1] - trainerenity.prevState[1]) * timeStep * timeStep, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 227 */     GL11.glEnable(3042);
/* 228 */     GL11.glDepthMask(false);
/* 229 */     GL11.glBlendFunc(770, 771);
/* 230 */     GL11.glAlphaFunc(516, 0.01F);
/* 231 */     GL11.glBegin(4);
/*     */     
/* 233 */     float visiblility = 1.0F;
/*     */     
/* 235 */     float par1 = trainerenity.prevState[0] + (trainerenity.state[0] - trainerenity.prevState[0]) * timeStep;
/*     */     
/* 237 */     if (Math.abs(par1) < 300.0F)
/*     */     {
/* 239 */       visiblility = Math.abs(par1 / 300.0F);
/*     */     }
/*     */     
/* 242 */     if (visiblility > 1.0F)
/*     */     {
/* 244 */       visiblility = 1.0F;
/*     */     }
/* 246 */     else if (visiblility < 0.0F)
/*     */     {
/* 248 */       visiblility = 0.0F;
/*     */     }
/*     */     
/* 251 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, visiblility);
/*     */     
/* 253 */     this.propellerBlur.draw();
/*     */     
/* 255 */     GL11.glEnd();
/* 256 */     GL11.glDepthMask(true);
/* 257 */     GL11.glDisable(3042);
/* 258 */     GL11.glAlphaFunc(516, 0.1F);
/* 259 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 261 */     GL11.glPopMatrix();
/*     */     
/*     */ 
/* 264 */     localTrans.set(-0.45133F, 0.09713F, -0.22248F);
/* 265 */     rotateAxis.set(trainerenity.helper.rotateVector(new Quat4f(0.0F, 0.0F, -0.035F, 0.999F), new Vector3f(1.0F, 0.0F, 0.0F)));
/*     */     
/* 267 */     GL11.glPushMatrix();
/*     */     
/* 269 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 270 */     GL11.glRotatef((trainerenity.prevState[2] + (trainerenity.state[2] - trainerenity.prevState[2]) * timeStep) * 20.0F, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 273 */     GL11.glEnable(3042);
/* 274 */     GL11.glBlendFunc(770, 771);
/* 275 */     GL11.glBegin(4);
/*     */     
/* 277 */     this.rightAileron.draw();
/*     */     
/* 279 */     GL11.glEnd();
/* 280 */     GL11.glDisable(3042);
/*     */     
/* 282 */     GL11.glPopMatrix();
/*     */     
/* 284 */     localTrans.set(0.45133F, 0.09713F, -0.22248F);
/* 285 */     rotateAxis.set(trainerenity.helper.rotateVector(new Quat4f(0.0F, 0.0F, 0.035F, 0.999F), new Vector3f(1.0F, 0.0F, 0.0F)));
/*     */     
/* 287 */     GL11.glPushMatrix();
/*     */     
/* 289 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 290 */     GL11.glRotatef(-(trainerenity.prevState[2] + (trainerenity.state[2] - trainerenity.prevState[2]) * timeStep) * 20.0F, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 293 */     GL11.glEnable(3042);
/* 294 */     GL11.glBlendFunc(770, 771);
/* 295 */     GL11.glBegin(4);
/*     */     
/* 297 */     this.leftAileron.draw();
/*     */     
/* 299 */     GL11.glEnd();
/* 300 */     GL11.glDisable(3042);
/*     */     
/* 302 */     GL11.glPopMatrix();
/*     */     
/* 304 */     localTrans.set(0.0F, 0.08955F, -0.85616F);
/* 305 */     rotateAxis.set(trainerenity.helper.rotateVector(new Quat4f(-0.112F, 0.0F, 0.0F, 0.994F), new Vector3f(0.0F, 1.0F, 0.0F)));
/*     */     
/* 307 */     GL11.glPushMatrix();
/*     */     
/* 309 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 310 */     GL11.glRotatef(-(trainerenity.prevState[3] + (trainerenity.state[3] - trainerenity.prevState[3]) * timeStep) * 20.0F, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 313 */     GL11.glEnable(3042);
/* 314 */     GL11.glBlendFunc(770, 771);
/* 315 */     GL11.glBegin(4);
/*     */     
/* 317 */     this.rudder.draw();
/*     */     
/* 319 */     GL11.glEnd();
/* 320 */     GL11.glDisable(3042);
/*     */     
/* 322 */     GL11.glPopMatrix();
/*     */     
/* 324 */     localTrans.set(0.0F, -0.01398F, -0.83274F);
/*     */     
/* 326 */     GL11.glPushMatrix();
/*     */     
/* 328 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 329 */     GL11.glRotatef(-(trainerenity.prevState[4] + (trainerenity.state[4] - trainerenity.prevState[4]) * timeStep) * 20.0F, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 332 */     GL11.glEnable(3042);
/* 333 */     GL11.glBlendFunc(770, 771);
/* 334 */     GL11.glBegin(4);
/*     */     
/* 336 */     this.elevator.draw();
/*     */     
/* 338 */     GL11.glEnd();
/* 339 */     GL11.glDisable(3042);
/*     */     
/* 341 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 348 */     return this.textureLocation;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderTrainerPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */