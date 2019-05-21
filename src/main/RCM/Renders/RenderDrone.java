/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Entities.EntityDrone;
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import RCM.Models.Model;
/*     */ import RCM.RCM_Main;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
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
/*     */ public class RenderDrone
/*     */   extends GlobalRender
/*     */ {
/*  27 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/droneskin.png");
/*  28 */   private ResourceLocation textureLocation2 = new ResourceLocation("thercmod:textures/models/dronepropblur.png");
/*     */   
/*  30 */   public Model propellerCW = null;
/*  31 */   public Model propellerBlurCW = null;
/*  32 */   public Model propellerBlurCCW = null;
/*  33 */   public Model propellerCCW = null;
/*     */   
/*     */ 
/*     */   public RenderDrone()
/*     */   {
/*  38 */     this.shadowSize = 0.0F;
/*  39 */     String filePath = RCM_Main.modelFilePath + "drone.rcm";
/*     */     
/*     */     try
/*     */     {
/*  43 */       this.mainBody = new Model();
/*  44 */       this.propellerCW = new Model();
/*  45 */       this.propellerCCW = new Model();
/*  46 */       this.propellerBlurCW = new Model();
/*  47 */       this.propellerBlurCCW = new Model();
/*     */       
/*  49 */       this.mainBody.loadModel(filePath, "MainBody");
/*  50 */       this.propellerCW.loadModel(filePath, "PropellerCW");
/*  51 */       this.propellerCCW.loadModel(filePath, "PropellerCCW");
/*  52 */       this.propellerBlurCW.loadModel(filePath, "PropellerBlurCW");
/*  53 */       this.propellerBlurCCW.loadModel(filePath, "PropellerBlurCCW");
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*  58 */       FMLLog.log(Level.ERROR, e, "RC Drone model file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  62 */       FMLLog.log(Level.ERROR, e, "RC Drone model file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderExtras(GlobalEntity rcentity, float timeStep)
/*     */   {
/*  72 */     EntityDrone drone = (EntityDrone)rcentity;
/*     */     
/*  74 */     if (rcentity.physicsWorld != null)
/*     */     {
/*  76 */       Vector3f localTrans = new Vector3f();
/*  77 */       Vector3f rotateAxis = new Vector3f();
/*     */       
/*  79 */       localTrans.set(0.3F, 0.0F, -0.3F);
/*  80 */       rotateAxis = new Vector3f(0.0F, 1.0F, 0.0F);
/*     */       
/*  82 */       GL11.glPushMatrix();
/*     */       
/*  84 */       GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/*  85 */       GL11.glRotatef(drone.prevState[0] + (drone.state[0] - drone.prevState[0]) * timeStep, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */       
/*     */ 
/*  88 */       this.renderManager.renderEngine.bindTexture(this.textureLocation);
/*     */       
/*  90 */       GL11.glEnable(3042);
/*  91 */       GL11.glBlendFunc(770, 771);
/*  92 */       GL11.glBegin(4);
/*     */       
/*  94 */       this.propellerCCW.draw();
/*     */       
/*  96 */       GL11.glEnd();
/*  97 */       GL11.glDisable(3042);
/*     */       
/*  99 */       this.renderManager.renderEngine.bindTexture(this.textureLocation2);
/*     */       
/* 101 */       GL11.glEnable(3042);
/* 102 */       GL11.glDepthMask(false);
/* 103 */       GL11.glBlendFunc(770, 771);
/* 104 */       GL11.glAlphaFunc(516, 0.01F);
/* 105 */       GL11.glBegin(4);
/*     */       
/* 107 */       float visiblility = 1.0F;
/*     */       
/* 109 */       float par1 = drone.prevState[4] + (drone.state[4] - drone.prevState[4]) * timeStep;
/*     */       
/* 111 */       if (Math.abs(par1) < 500.0F)
/*     */       {
/* 113 */         visiblility = Math.abs(par1 / 500.0F);
/*     */       }
/*     */       
/* 116 */       if (visiblility > 1.0F)
/*     */       {
/* 118 */         visiblility = 1.0F;
/*     */       }
/* 120 */       else if (visiblility < 0.0F)
/*     */       {
/* 122 */         visiblility = 0.0F;
/*     */       }
/*     */       
/* 125 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, visiblility);
/*     */       
/* 127 */       this.propellerBlurCW.draw();
/*     */       
/* 129 */       GL11.glEnd();
/* 130 */       GL11.glDepthMask(true);
/* 131 */       GL11.glDisable(3042);
/* 132 */       GL11.glAlphaFunc(516, 0.1F);
/* 133 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 135 */       GL11.glPopMatrix();
/*     */       
/* 137 */       localTrans.set(-0.3F, 0.0F, -0.3F);
/* 138 */       rotateAxis = new Vector3f(0.0F, -1.0F, 0.0F);
/*     */       
/* 140 */       GL11.glPushMatrix();
/*     */       
/* 142 */       GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 143 */       GL11.glRotatef(drone.prevState[1] + (drone.state[1] - drone.prevState[1]) * timeStep, rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */       
/*     */ 
/* 146 */       this.renderManager.renderEngine.bindTexture(this.textureLocation);
/*     */       
/* 148 */       GL11.glEnable(3042);
/* 149 */       GL11.glBlendFunc(770, 771);
/* 150 */       GL11.glBegin(4);
/*     */       
/* 152 */       this.propellerCW.draw();
/*     */       
/* 154 */       GL11.glEnd();
/* 155 */       GL11.glDisable(3042);
/*     */       
/* 157 */       this.renderManager.renderEngine.bindTexture(this.textureLocation2);
/*     */       
/* 159 */       GL11.glEnable(3042);
/* 160 */       GL11.glDepthMask(false);
/* 161 */       GL11.glBlendFunc(770, 771);
/* 162 */       GL11.glAlphaFunc(516, 0.01F);
/* 163 */       GL11.glBegin(4);
/*     */       
/* 165 */       visiblility = 1.0F;
/*     */       
/* 167 */       par1 = drone.prevState[5] + (drone.state[5] - drone.prevState[5]) * timeStep;
/*     */       
/* 169 */       if (Math.abs(par1) < 500.0F)
/*     */       {
/* 171 */         visiblility = Math.abs(par1 / 500.0F);
/*     */       }
/*     */       
/* 174 */       if (visiblility > 1.0F)
/*     */       {
/* 176 */         visiblility = 1.0F;
/*     */       }
/* 178 */       else if (visiblility < 0.0F)
/*     */       {
/* 180 */         visiblility = 0.0F;
/*     */       }
/*     */       
/* 183 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, visiblility);
/*     */       
/* 185 */       this.propellerBlurCCW.draw();
/*     */       
/* 187 */       GL11.glEnd();
/* 188 */       GL11.glDepthMask(true);
/* 189 */       GL11.glDisable(3042);
/* 190 */       GL11.glAlphaFunc(516, 0.1F);
/* 191 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 193 */       GL11.glPopMatrix();
/*     */       
/* 195 */       localTrans.set(0.3F, 0.0F, 0.3F);
/* 196 */       rotateAxis = new Vector3f(0.0F, 1.0F, 0.0F);
/*     */       
/* 198 */       GL11.glPushMatrix();
/*     */       
/* 200 */       GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 201 */       GL11.glRotatef(-(drone.prevState[2] + (drone.state[2] - drone.prevState[2]) * timeStep), rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */       
/*     */ 
/* 204 */       this.renderManager.renderEngine.bindTexture(this.textureLocation);
/*     */       
/* 206 */       GL11.glEnable(3042);
/* 207 */       GL11.glBlendFunc(770, 771);
/* 208 */       GL11.glBegin(4);
/*     */       
/* 210 */       this.propellerCW.draw();
/*     */       
/* 212 */       GL11.glEnd();
/* 213 */       GL11.glDisable(3042);
/*     */       
/* 215 */       this.renderManager.renderEngine.bindTexture(this.textureLocation2);
/*     */       
/* 217 */       GL11.glEnable(3042);
/* 218 */       GL11.glDepthMask(false);
/* 219 */       GL11.glBlendFunc(770, 771);
/* 220 */       GL11.glAlphaFunc(516, 0.01F);
/* 221 */       GL11.glBegin(4);
/*     */       
/* 223 */       visiblility = 1.0F;
/*     */       
/* 225 */       par1 = drone.prevState[6] + (drone.state[6] - drone.prevState[6]) * timeStep;
/*     */       
/* 227 */       if (Math.abs(par1) < 500.0F)
/*     */       {
/* 229 */         visiblility = Math.abs(par1 / 500.0F);
/*     */       }
/*     */       
/* 232 */       if (visiblility > 1.0F)
/*     */       {
/* 234 */         visiblility = 1.0F;
/*     */       }
/* 236 */       else if (visiblility < 0.0F)
/*     */       {
/* 238 */         visiblility = 0.0F;
/*     */       }
/*     */       
/* 241 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, visiblility);
/*     */       
/* 243 */       this.propellerBlurCCW.draw();
/*     */       
/* 245 */       GL11.glEnd();
/* 246 */       GL11.glDepthMask(true);
/* 247 */       GL11.glDisable(3042);
/* 248 */       GL11.glAlphaFunc(516, 0.1F);
/* 249 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 251 */       GL11.glPopMatrix();
/*     */       
/* 253 */       localTrans.set(-0.3F, 0.0F, 0.3F);
/* 254 */       rotateAxis = new Vector3f(0.0F, -1.0F, 0.0F);
/*     */       
/* 256 */       GL11.glPushMatrix();
/*     */       
/* 258 */       GL11.glTranslatef(localTrans.x, localTrans.y, localTrans.z);
/* 259 */       GL11.glRotatef(-(drone.prevState[3] + (drone.state[3] - drone.prevState[3]) * timeStep), rotateAxis.x, rotateAxis.y, rotateAxis.z);
/*     */       
/*     */ 
/* 262 */       this.renderManager.renderEngine.bindTexture(this.textureLocation);
/*     */       
/* 264 */       GL11.glEnable(3042);
/* 265 */       GL11.glBlendFunc(770, 771);
/* 266 */       GL11.glBegin(4);
/*     */       
/* 268 */       this.propellerCCW.draw();
/*     */       
/* 270 */       GL11.glEnd();
/* 271 */       GL11.glDisable(3042);
/*     */       
/* 273 */       this.renderManager.renderEngine.bindTexture(this.textureLocation2);
/*     */       
/* 275 */       GL11.glEnable(3042);
/* 276 */       GL11.glDepthMask(false);
/* 277 */       GL11.glBlendFunc(770, 771);
/* 278 */       GL11.glAlphaFunc(516, 0.01F);
/* 279 */       GL11.glBegin(4);
/*     */       
/* 281 */       visiblility = 1.0F;
/*     */       
/* 283 */       par1 = drone.prevState[7] + (drone.state[7] - drone.prevState[7]) * timeStep;
/*     */       
/* 285 */       if (Math.abs(par1) < 500.0F)
/*     */       {
/* 287 */         visiblility = Math.abs(par1 / 500.0F);
/*     */       }
/*     */       
/* 290 */       if (visiblility > 1.0F)
/*     */       {
/* 292 */         visiblility = 1.0F;
/*     */       }
/* 294 */       else if (visiblility < 0.0F)
/*     */       {
/* 296 */         visiblility = 0.0F;
/*     */       }
/*     */       
/* 299 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, visiblility);
/*     */       
/* 301 */       this.propellerBlurCW.draw();
/*     */       
/* 303 */       GL11.glEnd();
/* 304 */       GL11.glDepthMask(true);
/* 305 */       GL11.glDisable(3042);
/* 306 */       GL11.glAlphaFunc(516, 0.1F);
/* 307 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 309 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 316 */     return this.textureLocation;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderDrone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */