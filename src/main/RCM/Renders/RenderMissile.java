/*    */ package RCM.Renders;
/*    */ 
/*    */ import RCM.Models.Model;
/*    */ import RCM.RCM_Main;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.common.FMLLog;
/*    */ import org.apache.logging.log4j.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderMissile
/*    */   extends GlobalRender
/*    */ {
/* 28 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/missileskin.png");
/*    */   
/*    */ 
/*    */   public RenderMissile()
/*    */   {
/* 33 */     this.shadowSize = 0.0F;
/* 34 */     String filePath = RCM_Main.modelFilePath + "missle.rcm";
/*    */     
/*    */     try
/*    */     {
/* 38 */       this.mainBody = new Model();
/* 39 */       this.mainBody.loadModel(filePath, "MainBody");
/*    */       
/* 41 */       this.mainBody.createVBO();
/*    */ 
/*    */     }
/*    */     catch (FileNotFoundException e)
/*    */     {
/* 46 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file is missing!!", new Object[0]);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 50 */       FMLLog.log(Level.ERROR, e, "RC Trainer model file loaded incorrectly!!", new Object[0]);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 57 */     return this.textureLocation;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */