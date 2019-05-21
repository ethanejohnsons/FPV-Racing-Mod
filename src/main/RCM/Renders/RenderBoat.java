/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Entities.EntityBoat;
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
/*     */ public class RenderBoat
/*     */   extends GlobalRender
/*     */ {
/*  30 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/boatskin.png");
/*     */   
/*  32 */   public Model rudder = null;
/*     */   
/*     */ 
/*     */   public RenderBoat()
/*     */   {
/*  37 */     this.shadowSize = 0.2F;
/*  38 */     String filePath = RCM_Main.modelFilePath + "boat.rcm";
/*     */     
/*     */     try
/*     */     {
/*  42 */       this.mainBody = new Model();
/*  43 */       this.rudder = new Model();
/*     */       
/*  45 */       this.mainBody.loadModel(filePath, "MainBody");
/*  46 */       this.rudder.loadModel(filePath, "Rudder");
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*     */ 
/*  53 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  57 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderExtras(GlobalEntity rcentity, float timeStep)
/*     */   {
/*  65 */     EntityBoat boat = (EntityBoat)rcentity;
/*     */     
/*  67 */     if (rcentity.physicsWorld != null)
/*     */     {
/*     */ 
/*  70 */       GL11.glPushMatrix();
/*     */       
/*  72 */       GL11.glTranslatef(-0.081124F, -0.045286F, -0.32237F);
/*  73 */       GL11.glRotatef((boat.prevState[1] + (boat.state[1] - boat.prevState[1]) * timeStep) * 180.0F / 3.1415927F, 0.0F, -1.0F, 0.0F);
/*     */       
/*     */ 
/*  76 */       GL11.glEnable(3042);
/*  77 */       GL11.glBlendFunc(770, 771);
/*  78 */       GL11.glBegin(4);
/*     */       
/*  80 */       this.rudder.draw();
/*     */       
/*  82 */       GL11.glEnd();
/*  83 */       GL11.glDisable(3042);
/*     */       
/*  85 */       GL11.glPopMatrix();
/*     */       
/*  87 */       GL11.glPushMatrix();
/*     */       
/*  89 */       GL11.glTranslatef(0.081124F, -0.045286F, -0.32237F);
/*  90 */       GL11.glRotatef((boat.prevState[1] + (boat.state[1] - boat.prevState[1]) * timeStep) * 180.0F / 3.1415927F, 0.0F, -1.0F, 0.0F);
/*     */       
/*     */ 
/*  93 */       GL11.glEnable(3042);
/*  94 */       GL11.glBlendFunc(770, 771);
/*  95 */       GL11.glBegin(4);
/*     */       
/*  97 */       this.rudder.draw();
/*     */       
/*  99 */       GL11.glEnd();
/* 100 */       GL11.glDisable(3042);
/*     */       
/* 102 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 110 */     return this.textureLocation;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */