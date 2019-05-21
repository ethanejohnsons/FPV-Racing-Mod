/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Entities.EntityCar;
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import RCM.Models.Model;
/*     */ import RCM.RCM_Main;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
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
/*     */ public class RenderCar
/*     */   extends GlobalRender
/*     */ {
/*  29 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/carskin.png");
/*     */   
/*  31 */   public Model rearWing = null;
/*  32 */   public Model rightWheel = null;
/*  33 */   public Model leftWheel = null;
/*  34 */   public Model suspension = null;
/*     */   
/*     */ 
/*     */   public RenderCar()
/*     */   {
/*  39 */     this.shadowSize = 0.2F;
/*  40 */     String filePath = RCM_Main.modelFilePath + "car.rcm";
/*     */     
/*     */     try
/*     */     {
/*  44 */       this.mainBody = new Model();
/*  45 */       this.rearWing = new Model();
/*  46 */       this.rightWheel = new Model();
/*  47 */       this.leftWheel = new Model();
/*  48 */       this.suspension = new Model();
/*     */       
/*  50 */       this.mainBody.loadModel(filePath, "MainBody");
/*  51 */       this.rearWing.loadModel(filePath, "RearWing");
/*  52 */       this.leftWheel.loadModel(filePath, "LeftWheel");
/*  53 */       this.rightWheel.loadModel(filePath, "RightWheel");
/*  54 */       this.suspension.loadModel(filePath, "Suspension");
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*  59 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  63 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderExtras(GlobalEntity rcentity, float timeStep)
/*     */   {
/*  71 */     EntityCar carEntity = (EntityCar)rcentity;
/*     */     
/*     */ 
/*  74 */     GL11.glPushMatrix();
/*     */     
/*  76 */     float dis = (carEntity.prevState[6] + (carEntity.state[6] - carEntity.prevState[6]) * timeStep) * 0.125F;
/*  77 */     float def = -(float)(Math.asin(dis / 0.10942101F) / 3.141592653589793D) * 180.0F;
/*     */     
/*  79 */     GL11.glTranslatef(0.03F, -0.00616F, 0.1771F);
/*  80 */     GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  81 */     GL11.glRotatef(def, 0.0F, 0.0F, 1.0F);
/*     */     
/*  83 */     GL11.glEnable(3042);
/*  84 */     GL11.glBlendFunc(770, 771);
/*  85 */     GL11.glBegin(4);
/*     */     
/*  87 */     this.suspension.draw();
/*     */     
/*  89 */     GL11.glEnd();
/*  90 */     GL11.glDisable(3042);
/*     */     
/*  92 */     GL11.glPushMatrix();
/*     */     
/*     */ 
/*  95 */     GL11.glTranslatef(-0.10942101F, -0.02765F, 0.0F);
/*  96 */     GL11.glRotatef(carEntity.prevState[1] + (carEntity.state[1] - carEntity.prevState[1]) * timeStep, 0.0F, 1.0F, 0.0F);
/*     */     
/*     */ 
/*  99 */     GL11.glPushMatrix();
/*     */     
/* 101 */     GL11.glRotatef(carEntity.prevState[2] + (carEntity.state[2] - carEntity.prevState[2]) * timeStep, -1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 104 */     GL11.glEnable(3042);
/* 105 */     GL11.glBlendFunc(770, 771);
/* 106 */     GL11.glBegin(4);
/*     */     
/* 108 */     this.leftWheel.draw();
/*     */     
/* 110 */     GL11.glEnd();
/* 111 */     GL11.glDisable(3042);
/*     */     
/* 113 */     GL11.glPopMatrix();
/*     */     
/* 115 */     GL11.glPopMatrix();
/*     */     
/* 117 */     GL11.glPopMatrix();
/*     */     
/*     */ 
/* 120 */     GL11.glPushMatrix();
/*     */     
/* 122 */     dis = (carEntity.prevState[7] + (carEntity.state[7] - carEntity.prevState[7]) * timeStep) * 0.125F;
/* 123 */     def = -(float)(Math.asin(dis / 0.10942101F) / 3.141592653589793D) * 180.0F;
/*     */     
/* 125 */     GL11.glTranslatef(-0.03F, -0.00616F, 0.1771F);
/* 126 */     GL11.glRotatef(def, 0.0F, 0.0F, 1.0F);
/*     */     
/* 128 */     GL11.glEnable(3042);
/* 129 */     GL11.glBlendFunc(770, 771);
/* 130 */     GL11.glBegin(4);
/*     */     
/* 132 */     this.suspension.draw();
/*     */     
/* 134 */     GL11.glEnd();
/* 135 */     GL11.glDisable(3042);
/*     */     
/* 137 */     GL11.glPushMatrix();
/*     */     
/* 139 */     GL11.glTranslatef(-0.10942101F, -0.02765F, 0.0F);
/* 140 */     GL11.glRotatef(carEntity.prevState[1] + (carEntity.state[1] - carEntity.prevState[1]) * timeStep, 0.0F, 1.0F, 0.0F);
/*     */     
/*     */ 
/* 143 */     GL11.glPushMatrix();
/*     */     
/* 145 */     GL11.glRotatef(carEntity.prevState[3] + (carEntity.state[3] - carEntity.prevState[3]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 148 */     GL11.glEnable(3042);
/* 149 */     GL11.glBlendFunc(770, 771);
/* 150 */     GL11.glBegin(4);
/*     */     
/* 152 */     this.rightWheel.draw();
/*     */     
/* 154 */     GL11.glEnd();
/* 155 */     GL11.glDisable(3042);
/*     */     
/* 157 */     GL11.glPopMatrix();
/*     */     
/* 159 */     GL11.glPopMatrix();
/*     */     
/* 161 */     GL11.glPopMatrix();
/*     */     
/*     */ 
/* 164 */     GL11.glPushMatrix();
/*     */     
/* 166 */     dis = (carEntity.prevState[8] + (carEntity.state[8] - carEntity.prevState[8]) * timeStep) * 0.125F;
/* 167 */     def = -(float)(Math.asin(dis / 0.10942101F) / 3.141592653589793D) * 180.0F;
/*     */     
/* 169 */     GL11.glTranslatef(0.03F, -0.00616F, -0.16F);
/* 170 */     GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 171 */     GL11.glRotatef(def, 0.0F, 0.0F, 1.0F);
/*     */     
/* 173 */     GL11.glEnable(3042);
/* 174 */     GL11.glBlendFunc(770, 771);
/* 175 */     GL11.glBegin(4);
/*     */     
/* 177 */     this.suspension.draw();
/*     */     
/* 179 */     GL11.glEnd();
/* 180 */     GL11.glDisable(3042);
/*     */     
/* 182 */     GL11.glPushMatrix();
/*     */     
/* 184 */     GL11.glTranslatef(-0.10942101F, -0.02765F, 0.0F);
/* 185 */     GL11.glRotatef(carEntity.prevState[4] + (carEntity.state[4] - carEntity.prevState[4]) * timeStep, -1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 188 */     GL11.glEnable(3042);
/* 189 */     GL11.glBlendFunc(770, 771);
/* 190 */     GL11.glBegin(4);
/*     */     
/* 192 */     this.leftWheel.draw();
/*     */     
/* 194 */     GL11.glEnd();
/* 195 */     GL11.glDisable(3042);
/*     */     
/* 197 */     GL11.glPopMatrix();
/*     */     
/* 199 */     GL11.glPopMatrix();
/*     */     
/*     */ 
/* 202 */     GL11.glPushMatrix();
/*     */     
/* 204 */     dis = (carEntity.prevState[9] + (carEntity.state[9] - carEntity.prevState[9]) * timeStep) * 0.125F;
/* 205 */     def = -(float)(Math.asin(dis / 0.10942101F) / 3.141592653589793D) * 180.0F;
/*     */     
/* 207 */     GL11.glTranslatef(-0.03F, -0.00616F, -0.16F);
/* 208 */     GL11.glRotatef(def, 0.0F, 0.0F, 1.0F);
/*     */     
/* 210 */     GL11.glEnable(3042);
/* 211 */     GL11.glBlendFunc(770, 771);
/* 212 */     GL11.glBegin(4);
/*     */     
/* 214 */     this.suspension.draw();
/*     */     
/* 216 */     GL11.glEnd();
/* 217 */     GL11.glDisable(3042);
/*     */     
/* 219 */     GL11.glPushMatrix();
/*     */     
/* 221 */     GL11.glTranslatef(-0.10942101F, -0.02765F, 0.0F);
/* 222 */     GL11.glRotatef(carEntity.prevState[5] + (carEntity.state[5] - carEntity.prevState[5]) * timeStep, 1.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 225 */     GL11.glEnable(3042);
/* 226 */     GL11.glBlendFunc(770, 771);
/* 227 */     GL11.glBegin(4);
/*     */     
/* 229 */     this.rightWheel.draw();
/*     */     
/* 231 */     GL11.glEnd();
/* 232 */     GL11.glDisable(3042);
/*     */     
/* 234 */     GL11.glPopMatrix();
/*     */     
/* 236 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 243 */     return this.textureLocation;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderCar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */