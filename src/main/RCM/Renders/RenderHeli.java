/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Entities.EntityHeli;
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import RCM.Models.Model;
/*     */ import RCM.RCM_Main;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderHeli
/*     */   extends GlobalRender
/*     */ {
/*  29 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/heliskin.png");
/*  30 */   private ResourceLocation textureLocation2 = new ResourceLocation("thercmod:textures/models/heliRotorBlur.png");
/*     */   
/*  32 */   public Model mainRotor = null;
/*  33 */   public Model mainBlade = null;
/*  34 */   public Model tailRotor = null;
/*  35 */   public Model tailBlade = null;
/*  36 */   public Model mainRotorBlur = null;
/*  37 */   public Model tailRotorBlur = null;
/*     */   
/*     */ 
/*     */   public RenderHeli()
/*     */   {
/*  42 */     this.shadowSize = 0.175F;
/*  43 */     String filePath = RCM_Main.modelFilePath + "heli.rcm";
/*     */     
/*     */     try
/*     */     {
/*  47 */       this.mainBody = new Model();
/*  48 */       this.mainRotor = new Model();
/*  49 */       this.mainBlade = new Model();
/*  50 */       this.tailRotor = new Model();
/*  51 */       this.tailBlade = new Model();
/*  52 */       this.mainRotorBlur = new Model();
/*  53 */       this.tailRotorBlur = new Model();
/*     */       
/*  55 */       this.mainBody.loadModel(filePath, "MainBody");
/*  56 */       this.mainRotor.loadModel(filePath, "MainRotor");
/*  57 */       this.mainBlade.loadModel(filePath, "MainBlade");
/*  58 */       this.tailRotor.loadModel(filePath, "TailRotor");
/*  59 */       this.tailBlade.loadModel(filePath, "TailBlade");
/*  60 */       this.mainRotorBlur.loadModel(filePath, "MainRotorBlur");
/*  61 */       this.tailRotorBlur.loadModel(filePath, "TailRotorBlur");
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*     */ 
/*  68 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  72 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderExtras(GlobalEntity rcentity, float timeStep)
/*     */   {
/*  79 */     EntityHeli heli = (EntityHeli)rcentity;
/*     */     
/*  81 */     if (rcentity.physicsWorld != null)
/*     */     {
/*     */ 
/*  84 */       GL11.glPushMatrix();
/*     */       
/*  86 */       GL11.glRotatef(-(heli.prevState[2] + (heli.state[2] - heli.prevState[2]) * timeStep), 0.0F, 1.0F, 0.0F);
/*     */       
/*     */ 
/*     */ 
/*  90 */       GL11.glEnable(3042);
/*  91 */       GL11.glBlendFunc(770, 771);
/*  92 */       GL11.glBegin(4);
/*     */       
/*  94 */       this.mainRotor.draw();
/*     */       
/*  96 */       GL11.glEnd();
/*  97 */       GL11.glDisable(3042);
/*     */       
/*  99 */       GL11.glPushMatrix();
/*     */       
/* 101 */       GL11.glTranslatef(0.0F, 0.18497F, 0.0F);
/* 102 */       GL11.glRotatef(heli.prevState[4] + (heli.state[4] - heli.prevState[4]) * timeStep, 0.0F, 0.0F, 1.0F);
/*     */       
/*     */ 
/* 105 */       GL11.glEnable(3042);
/* 106 */       GL11.glBlendFunc(770, 771);
/* 107 */       GL11.glBegin(4);
/*     */       
/* 109 */       this.mainBlade.draw();
/*     */       
/* 111 */       GL11.glEnd();
/* 112 */       GL11.glDisable(3042);
/*     */       
/* 114 */       GL11.glPopMatrix();
/*     */       
/* 116 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*     */       
/*     */ 
/* 119 */       GL11.glPushMatrix();
/*     */       
/* 121 */       GL11.glTranslatef(0.0F, 0.18497F, 0.0F);
/* 122 */       GL11.glRotatef(heli.prevState[4] + (heli.state[4] - heli.prevState[4]) * timeStep, 0.0F, 0.0F, 1.0F);
/*     */       
/*     */ 
/* 125 */       GL11.glEnable(3042);
/* 126 */       GL11.glBlendFunc(770, 771);
/* 127 */       GL11.glBegin(4);
/*     */       
/* 129 */       this.mainBlade.draw();
/*     */       
/* 131 */       GL11.glEnd();
/* 132 */       GL11.glDisable(3042);
/*     */       
/* 134 */       GL11.glPopMatrix();
/*     */       
/* 136 */       this.renderManager.renderEngine.bindTexture(this.textureLocation2);
/*     */       
/* 138 */       GL11.glEnable(3042);
/* 139 */       GL11.glDepthMask(false);
/* 140 */       GL11.glBlendFunc(770, 771);
/* 141 */       GL11.glAlphaFunc(516, 0.01F);
/* 142 */       GL11.glBegin(4);
/*     */       
/* 144 */       float visiblility = 1.0F;
/*     */       
/* 146 */       float par1 = heli.prevState[0] + (heli.state[0] - heli.prevState[0]) * timeStep;
/*     */       
/* 148 */       if (Math.abs(par1) < 200.0F)
/*     */       {
/* 150 */         visiblility = Math.abs(par1 / 100.0F);
/*     */       }
/*     */       
/* 153 */       if (visiblility > 1.0F)
/*     */       {
/* 155 */         visiblility = 1.0F;
/*     */       }
/* 157 */       else if (visiblility < 0.0F)
/*     */       {
/* 159 */         visiblility = 0.0F;
/*     */       }
/*     */       
/* 162 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, visiblility);
/*     */       
/* 164 */       this.mainRotorBlur.draw();
/*     */       
/* 166 */       GL11.glEnd();
/* 167 */       GL11.glDepthMask(true);
/* 168 */       GL11.glDisable(3042);
/* 169 */       GL11.glAlphaFunc(516, 0.1F);
/* 170 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*     */ 
/* 173 */       GL11.glPopMatrix();
/*     */       
/* 175 */       this.renderManager.renderEngine.bindTexture(this.textureLocation);
/*     */       
/* 177 */       GL11.glPushMatrix();
/*     */       
/* 179 */       GL11.glTranslatef(0.0F, 0.0F, -0.79019F);
/* 180 */       GL11.glRotatef(heli.prevState[3] + (heli.state[3] - heli.prevState[3]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */       
/*     */ 
/* 183 */       GL11.glEnable(3042);
/* 184 */       GL11.glBlendFunc(770, 771);
/* 185 */       GL11.glBegin(4);
/*     */       
/* 187 */       this.tailRotor.draw();
/*     */       
/* 189 */       GL11.glEnd();
/* 190 */       GL11.glDisable(3042);
/*     */       
/* 192 */       GL11.glPushMatrix();
/*     */       
/* 194 */       GL11.glTranslatef(-0.02358F, 0.0F, 0.0F);
/* 195 */       GL11.glRotatef(heli.prevState[5] + (heli.state[5] - heli.prevState[5]) * timeStep, 0.0F, 1.0F, 0.0F);
/*     */       
/*     */ 
/* 198 */       GL11.glEnable(3042);
/* 199 */       GL11.glBlendFunc(770, 771);
/* 200 */       GL11.glBegin(4);
/*     */       
/* 202 */       this.tailBlade.draw();
/*     */       
/* 204 */       GL11.glEnd();
/* 205 */       GL11.glDisable(3042);
/*     */       
/* 207 */       GL11.glPopMatrix();
/*     */       
/* 209 */       GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/*     */       
/*     */ 
/* 212 */       GL11.glPushMatrix();
/*     */       
/* 214 */       GL11.glTranslatef(-0.02358F, 0.0F, 0.0F);
/* 215 */       GL11.glRotatef(heli.prevState[5] + (heli.state[5] - heli.prevState[5]) * timeStep, 0.0F, 1.0F, 0.0F);
/*     */       
/*     */ 
/* 218 */       GL11.glEnable(3042);
/* 219 */       GL11.glBlendFunc(770, 771);
/* 220 */       GL11.glBegin(4);
/*     */       
/* 222 */       this.tailBlade.draw();
/*     */       
/* 224 */       GL11.glEnd();
/* 225 */       GL11.glDisable(3042);
/*     */       
/* 227 */       GL11.glPopMatrix();
/*     */       
/* 229 */       this.renderManager.renderEngine.bindTexture(this.textureLocation2);
/*     */       
/* 231 */       GL11.glEnable(3042);
/* 232 */       GL11.glDepthMask(false);
/* 233 */       GL11.glBlendFunc(770, 771);
/* 234 */       GL11.glAlphaFunc(516, 0.01F);
/* 235 */       GL11.glBegin(4);
/*     */       
/* 237 */       visiblility = 1.0F;
/*     */       
/* 239 */       par1 = heli.prevState[6] + (heli.state[6] - heli.prevState[6]) * timeStep;
/*     */       
/* 241 */       if (Math.abs(par1) < 300.0F)
/*     */       {
/* 243 */         visiblility = Math.abs(par1 / 300.0F);
/*     */       }
/*     */       
/* 246 */       if (visiblility > 1.0F)
/*     */       {
/* 248 */         visiblility = 1.0F;
/*     */       }
/* 250 */       else if (visiblility < 0.0F)
/*     */       {
/* 252 */         visiblility = 0.0F;
/*     */       }
/*     */       
/* 255 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, visiblility);
/*     */       
/* 257 */       this.tailRotorBlur.draw();
/*     */       
/* 259 */       GL11.glEnd();
/* 260 */       GL11.glDepthMask(true);
/* 261 */       GL11.glDisable(3042);
/* 262 */       GL11.glAlphaFunc(516, 0.1F);
/* 263 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*     */ 
/* 266 */       this.renderManager.renderEngine.bindTexture(this.textureLocation);
/*     */       
/* 268 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 276 */     return this.textureLocation;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */