/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Items.ItemRemoteControl;
/*     */ import RCM.Models.Model;
/*     */ import RCM.Models.ModelFace;
/*     */ import RCM.RCM_Main;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import javax.vecmath.Vector2f;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.FMLLog;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class RenderRemoteControl
/*     */   extends TileEntityItemStackRenderer
/*     */ {
/*  28 */   private ResourceLocation textureLocation = new ResourceLocation("thercmod:textures/models/remotecontrolskin.png");
/*  29 */   private ResourceLocation textureLocationLight = new ResourceLocation("thercmod:textures/items/item_remotecontrolon.png");
/*  30 */   private ResourceLocation textureTrainer = new ResourceLocation("thercmod:textures/items/item_trainer.png");
/*     */   
/*  32 */   private Model remoteModel = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public RenderRemoteControl()
/*     */   {
/*  38 */     String filePath = RCM_Main.modelFilePath + "remotecontrol.rcm";
/*     */     
/*     */     try
/*     */     {
/*  42 */       this.remoteModel = new Model();
/*  43 */       this.remoteModel.loadModel(filePath, "RemoteControl");
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*  48 */       FMLLog.log(Level.ERROR, e, "Remote Control model file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  52 */       FMLLog.log(Level.ERROR, e, "Remote Control model file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderByItem(ItemStack itemstack, float partialTicks)
/*     */   {
/*  59 */     Minecraft.getMinecraft().renderEngine.bindTexture(this.textureLocation);
/*     */     
/*  61 */     GL11.glPushMatrix();
/*  62 */     GL11.glEnable(3042);
/*  63 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  65 */     GL11.glBegin(4);
/*     */     
/*  67 */     drawObject(this.remoteModel);
/*     */     
/*  69 */     GL11.glEnd();
/*     */     
/*  71 */     GL11.glDisable(3042);
/*  72 */     GL11.glPopMatrix();
/*     */     
/*  74 */     ItemRemoteControl remote = (ItemRemoteControl)itemstack.getItem();
/*     */     
/*  76 */     if (remote.state)
/*     */     {
/*  78 */       Minecraft.getMinecraft().renderEngine.bindTexture(this.textureLocationLight);
/*     */       
/*  80 */       GL11.glPushMatrix();
/*     */       
/*  82 */       int x = 32;
/*  83 */       int y = 32;
/*     */       
/*  85 */       float f5 = 0.002F;
/*     */       
/*  87 */       GL11.glScalef(f5, f5, f5);
/*     */       
/*  89 */       GL11.glTranslatef(0.0F, 60.8F, 1.43F);
/*  90 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  91 */       GL11.glRotatef(6.1F, 1.0F, 0.0F, 0.0F);
/*     */       
/*  93 */       Tessellator tessellator = Tessellator.getInstance();
/*  94 */       BufferBuilder vertexbuffer = tessellator.getBuffer();
/*  95 */       vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  96 */       vertexbuffer.pos(-x, y, 0.0D).tex(0.0D, 1.0D).endVertex();
/*  97 */       vertexbuffer.pos(x, y, 0.0D).tex(1.0D, 1.0D).endVertex();
/*  98 */       vertexbuffer.pos(x, -y, 0.0D).tex(1.0D, 0.0D).endVertex();
/*  99 */       vertexbuffer.pos(-x, -y, 0.0D).tex(0.0D, 0.0D).endVertex();
/*     */       
/* 101 */       GL11.glEnable(3042);
/* 102 */       GL11.glBlendFunc(770, 771);
/* 103 */       GL11.glDisable(2896);
/*     */       
/* 105 */       tessellator.draw();
/*     */       
/* 107 */       GL11.glEnable(2896);
/* 108 */       GL11.glDisable(3042);
/*     */       
/* 110 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawObject(Model model)
/*     */   {
/* 302 */     for (ModelFace face : model.getFaces())
/*     */     {
/*     */ 
/* 305 */       Vector2f t1 = (Vector2f)model.getTextureCoordinates().get(face.getTextureCoordinateIndices()[0] - 1);
/* 306 */       GL11.glTexCoord2f(t1.x, t1.y);
/* 307 */       Vector3f n1 = (Vector3f)model.getNormals().get(face.getNormalIndices()[0] - 1);
/* 308 */       GL11.glNormal3f(n1.x, n1.y, n1.z);
/* 309 */       Vector3f v1 = (Vector3f)model.getVertices().get(face.getVertexIndices()[0] - 1);
/* 310 */       GL11.glVertex3f(v1.x, v1.y, v1.z);
/* 311 */       Vector2f t2 = (Vector2f)model.getTextureCoordinates().get(face.getTextureCoordinateIndices()[1] - 1);
/* 312 */       GL11.glTexCoord2f(t2.x, t2.y);
/* 313 */       Vector3f n2 = (Vector3f)model.getNormals().get(face.getNormalIndices()[1] - 1);
/* 314 */       GL11.glNormal3f(n2.x, n2.y, n2.z);
/* 315 */       Vector3f v2 = (Vector3f)model.getVertices().get(face.getVertexIndices()[1] - 1);
/* 316 */       GL11.glVertex3f(v2.x, v2.y, v2.z);
/* 317 */       Vector2f t3 = (Vector2f)model.getTextureCoordinates().get(face.getTextureCoordinateIndices()[2] - 1);
/* 318 */       GL11.glTexCoord2f(t3.x, t3.y);
/* 319 */       Vector3f n3 = (Vector3f)model.getNormals().get(face.getNormalIndices()[2] - 1);
/* 320 */       GL11.glNormal3f(n3.x, n3.y, n3.z);
/* 321 */       Vector3f v3 = (Vector3f)model.getVertices().get(face.getVertexIndices()[2] - 1);
/* 322 */       GL11.glVertex3f(v3.x, v3.y, v3.z);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/RenderRemoteControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */