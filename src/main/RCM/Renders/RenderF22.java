/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Entities.EntityF22;
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import RCM.Models.Model;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.PhysicsHelper;
/*     */ import RCM.RCM_Main;
/*     */ import RCM.TickHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderF22
/*     */   extends GlobalRender
/*     */ {
/*  36 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/f22skin.png");
/*  37 */   private ResourceLocation textureLocationRadar = new ResourceLocation("thercmod:textures/overlay/radar.png");
/*  38 */   private ResourceLocation textureLocationLock = new ResourceLocation("thercmod:textures/overlay/lock.png");
/*     */   
/*  40 */   public Model elevatorLeft = null;
/*  41 */   public Model elevatorRight = null;
/*  42 */   public Model canopy = null;
/*  43 */   public Model leftAileron = null;
/*  44 */   public Model rightAileron = null;
/*  45 */   public Model leftRudder = null;
/*  46 */   public Model rightRudder = null;
/*  47 */   public Model frontGear = null;
/*  48 */   public Model rearLeftGear = null;
/*  49 */   public Model rearRightGear = null;
/*  50 */   public Model frontWheel = null;
/*  51 */   public Model rearWheel = null;
/*  52 */   public Model gearFlapLeft1 = null;
/*  53 */   public Model gearFlapRight1 = null;
/*  54 */   public Model gearFlapLeft2 = null;
/*  55 */   public Model gearFlapRight2 = null;
/*  56 */   public Model gearFlapLeft3 = null;
/*  57 */   public Model gearFlapRight3 = null;
/*     */   
/*     */ 
/*     */   public RenderF22()
/*     */   {
/*  62 */     this.shadowSize = 0.2F;
/*  63 */     String filePath = RCM_Main.modelFilePath + "f22raptor.rcm";
/*     */     
/*     */     try
/*     */     {
/*  67 */       this.mainBody = new Model();
/*  68 */       this.canopy = new Model();
/*  69 */       this.elevatorLeft = new Model();
/*  70 */       this.elevatorRight = new Model();
/*  71 */       this.frontWheel = new Model();
/*  72 */       this.rearWheel = new Model();
/*  73 */       this.frontGear = new Model();
/*  74 */       this.rearLeftGear = new Model();
/*  75 */       this.rearRightGear = new Model();
/*  76 */       this.leftAileron = new Model();
/*  77 */       this.rightAileron = new Model();
/*  78 */       this.leftRudder = new Model();
/*  79 */       this.rightRudder = new Model();
/*  80 */       this.gearFlapLeft1 = new Model();
/*  81 */       this.gearFlapRight1 = new Model();
/*  82 */       this.gearFlapLeft2 = new Model();
/*  83 */       this.gearFlapRight2 = new Model();
/*  84 */       this.gearFlapLeft3 = new Model();
/*  85 */       this.gearFlapRight3 = new Model();
/*     */       
/*  87 */       this.mainBody.loadModel(filePath, "MainBody");
/*  88 */       this.canopy.loadModel(filePath, "Canopy");
/*  89 */       this.elevatorLeft.loadModel(filePath, "ElevatorLeft");
/*  90 */       this.elevatorRight.loadModel(filePath, "ElevatorRight");
/*  91 */       this.frontWheel.loadModel(filePath, "FrontWheel");
/*  92 */       this.rearWheel.loadModel(filePath, "RearWheel");
/*  93 */       this.frontGear.loadModel(filePath, "FrontGear");
/*  94 */       this.rearLeftGear.loadModel(filePath, "RearLeftGear");
/*  95 */       this.rearRightGear.loadModel(filePath, "RearRightGear");
/*  96 */       this.leftAileron.loadModel(filePath, "LeftAileron");
/*  97 */       this.rightAileron.loadModel(filePath, "RightAileron");
/*  98 */       this.leftRudder.loadModel(filePath, "LeftRudder");
/*  99 */       this.rightRudder.loadModel(filePath, "RightRudder");
/* 100 */       this.gearFlapLeft1.loadModel(filePath, "GearFlapLeft1");
/* 101 */       this.gearFlapRight1.loadModel(filePath, "GearFlapRight1");
/* 102 */       this.gearFlapLeft2.loadModel(filePath, "GearFlapLeft2");
/* 103 */       this.gearFlapRight2.loadModel(filePath, "GearFlapRight2");
/* 104 */       this.gearFlapLeft3.loadModel(filePath, "GearFlapLeft3");
/* 105 */       this.gearFlapRight3.loadModel(filePath, "GearFlapRight3");
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/* 109 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 113 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderExtras(GlobalEntity rcentity, float timeStep)
/*     */   {
/* 121 */     GL11.glEnable(3042);
/* 122 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 124 */     GL11.glBegin(4);
/* 125 */     this.canopy.draw();
/* 126 */     GL11.glEnd();
/*     */     
/* 128 */     GL11.glDisable(3042);
/*     */     
/* 130 */     EntityF22 f22enity = (EntityF22)rcentity;
/*     */     
/* 132 */     float gearState = f22enity.prevState[13] + (f22enity.state[13] - f22enity.prevState[13]) * timeStep;
/*     */     
/* 134 */     Vector3f localTrans = new Vector3f();
/* 135 */     Vector3f rotateAxis = new Vector3f();
/*     */     
/* 137 */     localTrans.set(0.0F, -0.052756F, 0.447236F);
/*     */     
/* 139 */     GL11.glPushMatrix();
/*     */     
/* 141 */     GL11.glTranslatef(localTrans.x, localTrans.y + (f22enity.prevState[10] + (f22enity.state[10] - f22enity.prevState[10]) * timeStep) * 0.5F, localTrans.z);
/* 142 */     GL11.glRotatef(gearState * 110.0F, -1.0F, 0.0F, 0.0F);
/*     */     
/* 144 */     GL11.glRotatef(f22enity.prevState[6] + (f22enity.state[6] - f22enity.prevState[6]) * timeStep, 0.0F, 1.0F, 0.0F);
/*     */     
/*     */ 
/* 147 */     GL11.glEnable(3042);
/* 148 */     GL11.glBlendFunc(770, 771);
/* 149 */     GL11.glBegin(4);
/*     */     
/* 151 */     this.frontGear.draw();
/*     */     
/* 153 */     GL11.glEnd();
/* 154 */     GL11.glDisable(3042);
/*     */     
/* 156 */     GL11.glPushMatrix();
/*     */     
/* 158 */     GL11.glTranslatef(0.0F, -0.078939006F, 0.0F);
/* 159 */     GL11.glRotatef(f22enity.prevState[7] + (f22enity.state[7] - f22enity.prevState[7]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 162 */     GL11.glEnable(3042);
/* 163 */     GL11.glBlendFunc(770, 771);
/* 164 */     GL11.glBegin(4);
/*     */     
/* 166 */     this.frontWheel.draw();
/*     */     
/* 168 */     GL11.glEnd();
/* 169 */     GL11.glDisable(3042);
/*     */     
/* 171 */     GL11.glPopMatrix();
/*     */     
/* 173 */     GL11.glPopMatrix();
/*     */     
/* 175 */     localTrans.set(0.07F, -0.043911F, -0.033672F);
/*     */     
/* 177 */     GL11.glPushMatrix();
/*     */     
/* 179 */     GL11.glTranslatef(localTrans.x, localTrans.y + (f22enity.prevState[11] + (f22enity.state[11] - f22enity.prevState[11]) * timeStep) * 0.5F, localTrans.z);
/* 180 */     GL11.glRotatef(gearState * 92.0F, 0.0F, 0.0F, 1.0F);
/*     */     
/* 182 */     GL11.glEnable(3042);
/* 183 */     GL11.glBlendFunc(770, 771);
/* 184 */     GL11.glBegin(4);
/*     */     
/* 186 */     this.rearLeftGear.draw();
/*     */     
/* 188 */     GL11.glEnd();
/* 189 */     GL11.glDisable(3042);
/*     */     
/* 191 */     GL11.glPushMatrix();
/*     */     
/* 193 */     GL11.glTranslatef(0.048F, -0.08244401F, -0.016378F);
/* 194 */     GL11.glRotatef(f22enity.prevState[9] + (f22enity.state[9] - f22enity.prevState[9]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 197 */     GL11.glEnable(3042);
/* 198 */     GL11.glBlendFunc(770, 771);
/* 199 */     GL11.glBegin(4);
/*     */     
/* 201 */     this.rearWheel.draw();
/*     */     
/* 203 */     GL11.glEnd();
/* 204 */     GL11.glDisable(3042);
/*     */     
/* 206 */     GL11.glPopMatrix();
/*     */     
/* 208 */     GL11.glPopMatrix();
/*     */     
/* 210 */     localTrans.set(-0.07F, -0.043911F, -0.033672F);
/*     */     
/* 212 */     GL11.glPushMatrix();
/*     */     
/* 214 */     GL11.glTranslatef(localTrans.x, localTrans.y + (f22enity.prevState[12] + (f22enity.state[12] - f22enity.prevState[12]) * timeStep) * 0.5F, localTrans.z);
/* 215 */     GL11.glRotatef(gearState * 92.0F, 0.0F, 0.0F, -1.0F);
/*     */     
/* 217 */     GL11.glEnable(3042);
/* 218 */     GL11.glBlendFunc(770, 771);
/* 219 */     GL11.glBegin(4);
/*     */     
/* 221 */     this.rearRightGear.draw();
/*     */     
/* 223 */     GL11.glEnd();
/* 224 */     GL11.glDisable(3042);
/*     */     
/* 226 */     GL11.glPushMatrix();
/*     */     
/* 228 */     GL11.glTranslatef(-0.048F, -0.08244401F, -0.016378F);
/* 229 */     GL11.glRotatef(f22enity.prevState[8] + (f22enity.state[8] - f22enity.prevState[8]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 232 */     GL11.glEnable(3042);
/* 233 */     GL11.glBlendFunc(770, 771);
/* 234 */     GL11.glBegin(4);
/*     */     
/* 236 */     this.rearWheel.draw();
/*     */     
/* 238 */     GL11.glEnd();
/* 239 */     GL11.glDisable(3042);
/*     */     
/* 241 */     GL11.glPopMatrix();
/*     */     
/* 243 */     GL11.glPopMatrix();
/*     */     
/* 245 */     localTrans.set(-0.359351F, 0.005771F, -0.25325F);
/* 246 */     rotateAxis.set(f22enity.helper.rotateVector(new Quat4f(-0.005317F, 0.146485F, 0.035883F, 0.988548F), new Vector3f(1.0F, 0.0F, 0.0F)));
/*     */     
/* 248 */     GL11.glPushMatrix();
/*     */     
/* 250 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 251 */     GL11.glRotatef((f22enity.prevState[2] + (f22enity.state[2] - f22enity.prevState[2]) * timeStep) * 40.0F / 3.1415927F, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 254 */     GL11.glEnable(3042);
/* 255 */     GL11.glBlendFunc(770, 771);
/* 256 */     GL11.glBegin(4);
/*     */     
/* 258 */     this.rightAileron.draw();
/*     */     
/* 260 */     GL11.glEnd();
/* 261 */     GL11.glDisable(3042);
/*     */     
/* 263 */     GL11.glPopMatrix();
/*     */     
/* 265 */     localTrans.set(0.359351F, 0.005771F, -0.25325F);
/* 266 */     rotateAxis.set(f22enity.helper.rotateVector(new Quat4f(-0.005317F, -0.146485F, -0.035883F, 0.988548F), new Vector3f(1.0F, 0.0F, 0.0F)));
/*     */     
/* 268 */     GL11.glPushMatrix();
/*     */     
/* 270 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 271 */     GL11.glRotatef((-f22enity.prevState[2] - (f22enity.state[2] - f22enity.prevState[2]) * timeStep) * 40.0F / 3.1415927F, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 274 */     GL11.glEnable(3042);
/* 275 */     GL11.glBlendFunc(770, 771);
/* 276 */     GL11.glBegin(4);
/*     */     
/* 278 */     this.leftAileron.draw();
/*     */     
/* 280 */     GL11.glEnd();
/* 281 */     GL11.glDisable(3042);
/*     */     
/* 283 */     GL11.glPopMatrix();
/*     */     
/* 285 */     localTrans.set(0.12055F, 0.030309F, -0.393814F);
/* 286 */     rotateAxis.set(f22enity.helper.rotateVector(new Quat4f(0.210019F, 0.056312F, -0.213068F, 0.952535F), new Vector3f(0.0F, 1.0F, 0.0F)));
/*     */     
/* 288 */     GL11.glPushMatrix();
/*     */     
/* 290 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 291 */     GL11.glRotatef((-f22enity.prevState[3] - (f22enity.state[3] - f22enity.prevState[3]) * timeStep) * 90.0F / 3.1415927F, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 294 */     GL11.glEnable(3042);
/* 295 */     GL11.glBlendFunc(770, 771);
/* 296 */     GL11.glBegin(4);
/*     */     
/* 298 */     this.leftRudder.draw();
/*     */     
/* 300 */     GL11.glEnd();
/* 301 */     GL11.glDisable(3042);
/*     */     
/* 303 */     GL11.glPopMatrix();
/*     */     
/* 305 */     localTrans.set(-0.12055F, 0.030309F, -0.393814F);
/* 306 */     rotateAxis.set(f22enity.helper.rotateVector(new Quat4f(0.210019F, -0.056312F, 0.213068F, 0.952535F), new Vector3f(0.0F, 1.0F, 0.0F)));
/*     */     
/* 308 */     GL11.glPushMatrix();
/*     */     
/* 310 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 311 */     GL11.glRotatef((-f22enity.prevState[3] - (f22enity.state[3] - f22enity.prevState[3]) * timeStep) * 90.0F / 3.1415927F, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */     
/*     */ 
/* 314 */     GL11.glEnable(3042);
/* 315 */     GL11.glBlendFunc(770, 771);
/* 316 */     GL11.glBegin(4);
/*     */     
/* 318 */     this.rightRudder.draw();
/*     */     
/* 320 */     GL11.glEnd();
/* 321 */     GL11.glDisable(3042);
/*     */     
/* 323 */     GL11.glPopMatrix();
/*     */     
/* 325 */     localTrans.set(0.15168F, 0.00683F, -0.427F);
/*     */     
/* 327 */     GL11.glPushMatrix();
/*     */     
/* 329 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 330 */     GL11.glRotatef((-f22enity.prevState[4] - (f22enity.state[4] - f22enity.prevState[4]) * timeStep) * 180.0F / 3.1415927F, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 333 */     GL11.glEnable(3042);
/* 334 */     GL11.glBlendFunc(770, 771);
/* 335 */     GL11.glBegin(4);
/*     */     
/* 337 */     this.elevatorLeft.draw();
/*     */     
/* 339 */     GL11.glEnd();
/* 340 */     GL11.glDisable(3042);
/*     */     
/* 342 */     GL11.glPopMatrix();
/*     */     
/* 344 */     localTrans.set(-0.15168F, 0.00683F, -0.427F);
/*     */     
/* 346 */     GL11.glPushMatrix();
/*     */     
/* 348 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 349 */     GL11.glRotatef((-f22enity.prevState[5] - (f22enity.state[5] - f22enity.prevState[5]) * timeStep) * 180.0F / 3.1415927F, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 352 */     GL11.glEnable(3042);
/* 353 */     GL11.glBlendFunc(770, 771);
/* 354 */     GL11.glBegin(4);
/*     */     
/* 356 */     this.elevatorRight.draw();
/*     */     
/* 358 */     GL11.glEnd();
/* 359 */     GL11.glDisable(3042);
/*     */     
/* 361 */     GL11.glPopMatrix();
/*     */     
/* 363 */     localTrans.set(0.194894F, -0.004407F, -0.009647F);
/*     */     
/* 365 */     float delayState = 0.0F;
/*     */     
/* 367 */     if (gearState > 0.85D)
/*     */     {
/* 369 */       delayState = (gearState - 0.85F) / 0.15F;
/*     */     }
/*     */     
/* 372 */     GL11.glPushMatrix();
/*     */     
/* 374 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 375 */     GL11.glRotatef(delayState * 87.0F, 0.0F, 0.0F, -1.0F);
/*     */     
/* 377 */     GL11.glEnable(3042);
/* 378 */     GL11.glBlendFunc(770, 771);
/* 379 */     GL11.glBegin(4);
/*     */     
/* 381 */     this.gearFlapLeft1.draw();
/*     */     
/* 383 */     GL11.glEnd();
/* 384 */     GL11.glDisable(3042);
/*     */     
/* 386 */     GL11.glPopMatrix();
/*     */     
/* 388 */     GL11.glPushMatrix();
/*     */     
/* 390 */     GL11.glTranslatef(-localTrans.x, localTrans.y, localTrans.z);
/* 391 */     GL11.glRotatef(delayState * 87.0F, 0.0F, 0.0F, 1.0F);
/*     */     
/* 393 */     GL11.glEnable(3042);
/* 394 */     GL11.glBlendFunc(770, 771);
/* 395 */     GL11.glBegin(4);
/*     */     
/* 397 */     this.gearFlapRight1.draw();
/*     */     
/* 399 */     GL11.glEnd();
/* 400 */     GL11.glDisable(3042);
/*     */     
/* 402 */     GL11.glPopMatrix();
/*     */     
/* 404 */     localTrans.set(0.084299F, -0.055769F, -0.002551F);
/*     */     
/* 406 */     GL11.glPushMatrix();
/*     */     
/* 408 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 409 */     GL11.glRotatef(gearState * 107.0F, 0.0F, 0.0F, 1.0F);
/*     */     
/* 411 */     GL11.glEnable(3042);
/* 412 */     GL11.glBlendFunc(770, 771);
/* 413 */     GL11.glBegin(4);
/*     */     
/* 415 */     this.gearFlapLeft2.draw();
/*     */     
/* 417 */     GL11.glEnd();
/* 418 */     GL11.glDisable(3042);
/*     */     
/* 420 */     GL11.glPopMatrix();
/*     */     
/* 422 */     GL11.glPushMatrix();
/*     */     
/* 424 */     GL11.glTranslatef(-localTrans.x, localTrans.y, localTrans.z);
/* 425 */     GL11.glRotatef(gearState * 107.0F, 0.0F, 0.0F, -1.0F);
/*     */     
/* 427 */     GL11.glEnable(3042);
/* 428 */     GL11.glBlendFunc(770, 771);
/* 429 */     GL11.glBegin(4);
/*     */     
/* 431 */     this.gearFlapRight2.draw();
/*     */     
/* 433 */     GL11.glEnd();
/* 434 */     GL11.glDisable(3042);
/*     */     
/* 436 */     GL11.glPopMatrix();
/*     */     
/* 438 */     localTrans.set(0.026418F, -0.063002F, 0.439372F);
/*     */     
/* 440 */     GL11.glPushMatrix();
/*     */     
/* 442 */     GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 443 */     GL11.glRotatef(-delayState * 80.0F, -0.041762255F, 0.12617448F, 0.9911286F);
/*     */     
/* 445 */     GL11.glEnable(3042);
/* 446 */     GL11.glBlendFunc(770, 771);
/* 447 */     GL11.glBegin(4);
/*     */     
/* 449 */     this.gearFlapLeft3.draw();
/*     */     
/* 451 */     GL11.glEnd();
/* 452 */     GL11.glDisable(3042);
/*     */     
/* 454 */     GL11.glPopMatrix();
/*     */     
/* 456 */     GL11.glPushMatrix();
/*     */     
/* 458 */     GL11.glTranslatef(-localTrans.x, localTrans.y, localTrans.z);
/* 459 */     GL11.glRotatef(delayState * 80.0F, 0.041762255F, 0.12617448F, 0.9911286F);
/*     */     
/* 461 */     GL11.glEnable(3042);
/* 462 */     GL11.glBlendFunc(770, 771);
/* 463 */     GL11.glBegin(4);
/*     */     
/* 465 */     this.gearFlapRight3.draw();
/*     */     
/* 467 */     GL11.glEnd();
/* 468 */     GL11.glDisable(3042);
/*     */     
/* 470 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderExtras2(GlobalEntity rcentity, double posX, double posY, double posZ, float timeStep)
/*     */   {
/* 477 */     if ((rcentity.physicsWorld != null) && (TickHandler.rcEntity != null) && (TickHandler.rcEntity == rcentity) && (TickHandler.thirdPersonView) && (rcentity.weaponsMode))
/*     */     {
/* 479 */       EntityF22 f22Entity = (EntityF22)rcentity;
/* 480 */       Entity entity = null;
/*     */       
/* 482 */       for (Object obj : f22Entity.physicsWorld.visibleEntities)
/*     */       {
/* 484 */         entity = (Entity)obj;
/*     */         
/* 486 */         double eyeHeight = entity.getEyeHeight() / 2.0D;
/*     */         
/* 488 */         double lockPosX = entity.prevPosX + (entity.posX - entity.prevPosX) * timeStep - (rcentity.prevPosX + (rcentity.posX - rcentity.prevPosX) * timeStep) + posX;
/*     */         
/* 490 */         double lockPosY = entity.prevPosY + eyeHeight + (entity.posY + eyeHeight - (entity.prevPosY + eyeHeight)) * timeStep - (rcentity.prevPosY + (rcentity.posY - rcentity.prevPosY) * timeStep) + posY;
/*     */         
/* 492 */         double lockPosZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * timeStep - (rcentity.prevPosZ + (rcentity.posZ - rcentity.prevPosZ) * timeStep) + posZ;
/*     */         
/*     */ 
/* 495 */         renderRadarBox(rcentity, lockPosX, lockPosY, lockPosZ, this.textureLocationRadar, 1.0F, 1.0F);
/*     */       }
/*     */       
/* 498 */       if (f22Entity.physicsWorld.lockedEntity != null)
/*     */       {
/* 500 */         entity = f22Entity.physicsWorld.lockedEntity;
/*     */         
/* 502 */         double eyeHeight = entity.getEyeHeight() / 2.0D;
/*     */         
/* 504 */         double lockPosX = entity.prevPosX + (entity.posX - entity.prevPosX) * timeStep - (rcentity.prevPosX + (rcentity.posX - rcentity.prevPosX) * timeStep) + posX;
/*     */         
/* 506 */         double lockPosY = entity.prevPosY + eyeHeight + (entity.posY + eyeHeight - (entity.prevPosY + eyeHeight)) * timeStep - (rcentity.prevPosY + (rcentity.posY - rcentity.prevPosY) * timeStep) + posY;
/*     */         
/* 508 */         double lockPosZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * timeStep - (rcentity.prevPosZ + (rcentity.posZ - rcentity.prevPosZ) * timeStep) + posZ;
/*     */         
/*     */ 
/* 511 */         float lockScale = rcentity.prevLockProgress + (rcentity.lockProgress - rcentity.prevLockProgress) * timeStep;
/* 512 */         renderRadarBox(rcentity, lockPosX, lockPosY, lockPosZ, this.textureLocationLock, lockScale, 2.0F - lockScale);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 520 */     return this.textureLocation;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderF22.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */