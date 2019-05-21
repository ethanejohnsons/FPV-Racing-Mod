/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Entities.EntitySubmarine;
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
/*     */ 
/*     */ public class RenderSubmarine
/*     */   extends GlobalRender
/*     */ {
/*  30 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/submarineskin.png");
/*     */   
/*  32 */   public Model rearWing = null;
/*  33 */   public Model hRudder = null;
/*  34 */   public Model vRudder = null;
/*  35 */   public Model rotor = null;
/*     */   
/*     */ 
/*     */   public RenderSubmarine()
/*     */   {
/*  40 */     this.shadowSize = 0.2F;
/*  41 */     String filePath = RCM_Main.modelFilePath + "submarine.rcm";
/*     */     
/*     */     try
/*     */     {
/*  45 */       this.mainBody = new Model();
/*  46 */       this.hRudder = new Model();
/*  47 */       this.vRudder = new Model();
/*  48 */       this.rotor = new Model();
/*     */       
/*  50 */       this.mainBody.loadModel(filePath, "MainBody");
/*  51 */       this.hRudder.loadModel(filePath, "HorizontalRudder");
/*  52 */       this.vRudder.loadModel(filePath, "VerticalRudder");
/*  53 */       this.rotor.loadModel(filePath, "Rotor");
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*     */ 
/*  59 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  63 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderExtras(GlobalEntity rcentity, float timeStep)
/*     */   {
/*  70 */     EntitySubmarine sub = (EntitySubmarine)rcentity;
/*     */     
/*  72 */     if (rcentity.physicsWorld != null)
/*     */     {
/*  74 */       if (rcentity.physicsWorld != null)
/*     */       {
/*     */ 
/*  77 */         GL11.glPushMatrix();
/*     */         
/*  79 */         GL11.glTranslatef(0.0F, 0.0F, -1.055F);
/*  80 */         GL11.glRotatef((sub.prevState[3] + (sub.state[3] - sub.prevState[3]) * timeStep) * 180.0F / 3.1415927F, 1.0F, 0.0F, 0.0F);
/*     */         
/*     */ 
/*  83 */         GL11.glEnable(3042);
/*  84 */         GL11.glBlendFunc(770, 771);
/*  85 */         GL11.glBegin(4);
/*     */         
/*  87 */         this.hRudder.draw();
/*     */         
/*  89 */         GL11.glEnd();
/*  90 */         GL11.glDisable(3042);
/*     */         
/*  92 */         GL11.glPopMatrix();
/*     */         
/*  94 */         GL11.glPushMatrix();
/*     */         
/*  96 */         GL11.glTranslatef(0.0F, 0.0F, -1.055F);
/*  97 */         GL11.glRotatef((sub.prevState[4] + (sub.state[4] - sub.prevState[4]) * timeStep) * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
/*     */         
/*     */ 
/* 100 */         GL11.glEnable(3042);
/* 101 */         GL11.glBlendFunc(770, 771);
/* 102 */         GL11.glBegin(4);
/*     */         
/* 104 */         this.vRudder.draw();
/*     */         
/* 106 */         GL11.glEnd();
/* 107 */         GL11.glDisable(3042);
/*     */         
/* 109 */         GL11.glPopMatrix();
/*     */         
/* 111 */         GL11.glPushMatrix();
/*     */         
/* 113 */         GL11.glRotatef(sub.prevState[0] + (sub.state[0] - sub.prevState[0]) * timeStep, 0.0F, 0.0F, 1.0F);
/*     */         
/*     */ 
/* 116 */         GL11.glEnable(3042);
/* 117 */         GL11.glBlendFunc(770, 771);
/* 118 */         GL11.glBegin(4);
/*     */         
/* 120 */         this.rotor.draw();
/*     */         
/* 122 */         GL11.glEnd();
/* 123 */         GL11.glDisable(3042);
/*     */         
/* 125 */         GL11.glPopMatrix();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 133 */     return this.textureLocation;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderSubmarine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */