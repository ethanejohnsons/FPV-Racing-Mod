/*     */ package RCM.Renders;
/*     */ 
/*     */ import RCM.Entities.EntityMissile;
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import RCM.Models.Model;
/*     */ import RCM.Physics.FloatsHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.RotaryWingHandler;
/*     */ import RCM.Physics.WingHandler;
/*     */ import java.util.List;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL15;
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
/*     */ public class GlobalRender
/*     */   extends Render
/*     */ {
/*  43 */   public Model mainBody = null;
/*     */   
/*     */   private int shaderProgram;
/*  46 */   private final String VERTEX_SHADER_LOCATION = "/assets/thercmod/shaders/pixel_phong_lighting.vs";
/*  47 */   private final String FRAGMENT_SHADER_LOCATION = "/assets/thercmod/shaders/pixel_phong_lighting.fs";
/*     */   
/*     */   private boolean canLoadVBO;
/*     */   
/*     */   public GlobalRender()
/*     */   {
/*  53 */     super(Minecraft.getMinecraft().getRenderManager());
/*  54 */     this.canLoadVBO = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderRcEntity(GlobalEntity rcentity, double par2, double par4, double par6, float par8, float par9)
/*     */   {
/*  62 */     if ((!this.canLoadVBO) && (this.renderManager.options.useVbo))
/*     */     {
/*  64 */       loadVBO();
/*  65 */       this.canLoadVBO = true;
/*     */     }
/*     */     
/*  68 */     if (this.renderManager.options.useVbo)
/*     */     {
/*  70 */       GL11.glEnableClientState(32888);
/*  71 */       GL11.glEnableClientState(32885);
/*  72 */       GL11.glEnableClientState(32884);
/*     */     }
/*     */     
/*  75 */     GL11.glPushMatrix();
/*     */     
/*  77 */     bindEntityTexture(rcentity);
/*     */     
/*  79 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/*     */     
/*  81 */     if ((rcentity.timer < 4) || ((rcentity.rotationYaw - rcentity.prevRotationYaw > 45.0F) && (rcentity.timer >= 4)) || ((rcentity.rotationYaw - rcentity.prevRotationYaw < -45.0F) && (rcentity.timer >= 4)))
/*     */     {
/*  83 */       GL11.glRotatef(-rcentity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  84 */       rcentity.timer += 1;
/*     */     }
/*     */     else
/*     */     {
/*  88 */       GL11.glRotatef(-par8, 0.0F, 1.0F, 0.0F);
/*     */     }
/*     */     
/*  91 */     GL11.glRotatef(-rcentity.prevRotationPitch - (rcentity.rotationPitch - rcentity.prevRotationPitch) * par9, 1.0F, 0.0F, 0.0F);
/*     */     
/*  93 */     if ((rcentity.rotationRoll - rcentity.prevRotationRoll > 45.0F) || (rcentity.rotationRoll - rcentity.prevRotationRoll < -45.0F))
/*     */     {
/*  95 */       GL11.glRotatef(rcentity.rotationRoll, 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */     else
/*     */     {
/*  99 */       GL11.glRotatef(rcentity.prevRotationRoll + (rcentity.rotationRoll - rcentity.prevRotationRoll) * par9, 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 110 */     GL11.glEnable(3042);
/* 111 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 113 */     if ((this.renderManager.options.useVbo) && (rcentity.canRender))
/*     */     {
/* 115 */       int vboVertexHandle = this.mainBody.getVBO()[0];
/* 116 */       int vboNormalHandle = this.mainBody.getVBO()[1];
/* 117 */       int vboTextureHandler = this.mainBody.getVBO()[2];
/*     */       
/* 119 */       GL15.glBindBuffer(34962, vboTextureHandler);
/* 120 */       GL11.glTexCoordPointer(2, 5126, 0, 0L);
/* 121 */       GL15.glBindBuffer(34962, vboNormalHandle);
/* 122 */       GL11.glNormalPointer(5126, 0, 0L);
/* 123 */       GL15.glBindBuffer(34962, vboVertexHandle);
/* 124 */       GL11.glVertexPointer(3, 5126, 0, 0L);
/*     */       
/* 126 */       GL11.glDrawArrays(4, 0, this.mainBody.getFaces().size() * 3);
/*     */       
/* 128 */       GL15.glBindBuffer(34962, 0);
/*     */     }
/* 130 */     else if (rcentity.canRender)
/*     */     {
/* 132 */       if (this.canLoadVBO)
/*     */       {
/* 134 */         GL15.glDeleteBuffers(this.mainBody.getVBO()[0]);
/* 135 */         GL15.glDeleteBuffers(this.mainBody.getVBO()[1]);
/* 136 */         GL15.glDeleteBuffers(this.mainBody.getVBO()[2]);
/*     */         
/* 138 */         this.canLoadVBO = false;
/*     */       }
/*     */       
/* 141 */       GL11.glBegin(4);
/*     */       
/* 143 */       this.mainBody.draw();
/*     */       
/* 145 */       GL11.glEnd();
/*     */     }
/*     */     
/* 148 */     GL11.glDisable(3042);
/*     */     
/* 150 */     if (rcentity.canRender)
/*     */     {
/* 152 */       renderExtras(rcentity, par9);
/*     */     }
/*     */     
/* 155 */     GL11.glPopMatrix();
/*     */     
/* 157 */     if (this.renderManager.options.useVbo)
/*     */     {
/* 159 */       GL11.glDisableClientState(32888);
/* 160 */       GL11.glDisableClientState(32885);
/* 161 */       GL11.glDisableClientState(32884);
/*     */     }
/*     */     
/* 164 */     if ((rcentity.thePlayerName != null) && (!(rcentity instanceof EntityMissile)))
/*     */     {
/* 166 */       renderName(rcentity, par2, par4, par6);
/*     */     }
/*     */     
/* 169 */     if (this.renderManager.options.showDebugInfo)
/*     */     {
/* 171 */       drawForces(rcentity, par2, par4, par6);
/*     */     }
/*     */     
/* 174 */     if (rcentity.canRender)
/*     */     {
/* 176 */       renderExtras2(rcentity, par2, par4, par6, par9);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderExtras(GlobalEntity rcentity, float timeStep) {}
/*     */   
/*     */ 
/*     */   public void renderExtras2(GlobalEntity rcentity, double par2, double par4, double par6, float timeStep) {}
/*     */   
/*     */   private void renderName(GlobalEntity rcEntity, double posX, double posY, double posZ)
/*     */   {
/* 188 */     double range = rcEntity.getDistanceSq(this.renderManager.renderViewEntity);
/*     */     
/* 190 */     if (range < 600.0D)
/*     */     {
/* 192 */       String name = rcEntity.thePlayerName;
/*     */       
/* 194 */       FontRenderer fontRenderer = getFontRendererFromRenderManager();
/*     */       
/* 196 */       float scale = 0.02666667F;
/*     */       
/* 198 */       GL11.glPushMatrix();
/*     */       
/* 200 */       GL11.glTranslatef((float)posX, (float)posY + rcEntity.height + 0.75F, (float)posZ);
/* 201 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 202 */       GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 203 */       GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 204 */       GL11.glScalef(-scale, -scale, scale);
/* 205 */       GL11.glDisable(2896);
/* 206 */       GL11.glDepthMask(false);
/* 207 */       GL11.glDisable(2929);
/* 208 */       GL11.glEnable(3042);
/* 209 */       GL11.glBlendFunc(770, 771);
/* 210 */       GL11.glDisable(3553);
/*     */       
/* 212 */       Tessellator tessellator = Tessellator.getInstance();
/* 213 */       BufferBuilder vertexbuffer = tessellator.getBuffer();
/* 214 */       int stringSize = fontRenderer.getStringWidth(name) / 2;
/* 215 */       vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 216 */       vertexbuffer.pos(-stringSize - 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 217 */       vertexbuffer.pos(-stringSize - 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 218 */       vertexbuffer.pos(stringSize + 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 219 */       vertexbuffer.pos(stringSize + 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 220 */       tessellator.draw();
/*     */       
/* 222 */       GL11.glEnable(3553);
/*     */       
/* 224 */       if (name.equals("d4delf"))
/*     */       {
/* 226 */         name = "Skerp";
/*     */       }
/*     */       
/* 229 */       fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, 553648127);
/* 230 */       GL11.glEnable(2929);
/* 231 */       GL11.glDepthMask(true);
/*     */       
/* 233 */       if (name.equals("Skerp"))
/*     */       {
/* 235 */         fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, 16766720);
/*     */       }
/*     */       else
/*     */       {
/* 239 */         fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, -1);
/*     */       }
/*     */       
/* 242 */       GL11.glEnable(2896);
/* 243 */       GL11.glDisable(3042);
/* 244 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 246 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawForces(GlobalEntity rcentity, double posX, double posY, double posZ)
/*     */   {
/* 252 */     GL11.glPushMatrix();
/*     */     
/* 254 */     GL11.glTranslated(posX, posY, posZ);
/* 255 */     GL11.glLineWidth(1.0F);
/*     */     
/* 257 */     GL11.glDisable(2896);
/* 258 */     GL11.glDisable(3553);
/*     */     
/* 260 */     if (rcentity.physicsWorld != null)
/*     */     {
/* 262 */       for (RotaryWingHandler rotWing : rcentity.physicsWorld.rotaryWings)
/*     */       {
/* 264 */         for (int i = 0; i < rotWing.getSections(); i++)
/*     */         {
/*     */ 
/* 267 */           GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
/* 268 */           GL11.glBegin(1);
/*     */           
/* 270 */           GL11.glVertex3f(rotWing.getPosition(i).x, rotWing.getPosition(i).y, rotWing.getPosition(i).z);
/* 271 */           GL11.glVertex3f(rotWing.getLift(i).x / 2.0F + rotWing.getPosition(i).x, rotWing.getLift(i).y / 2.0F + rotWing.getPosition(i).y, rotWing.getLift(i).z / 2.0F + rotWing.getPosition(i).z);
/*     */           
/* 273 */           GL11.glEnd();
/*     */           
/* 275 */           GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
/* 276 */           GL11.glBegin(1);
/*     */           
/* 278 */           GL11.glVertex3f(rotWing.getPosition(i).x, rotWing.getPosition(i).y, rotWing.getPosition(i).z);
/* 279 */           GL11.glVertex3f(rotWing.getDrag(i).x * 2.0F + rotWing.getPosition(i).x, rotWing.getDrag(i).y * 2.0F + rotWing.getPosition(i).y, rotWing.getDrag(i).z * 2.0F + rotWing.getPosition(i).z);
/*     */           
/* 281 */           GL11.glEnd();
/*     */           
/* 283 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       }
/*     */       
/* 287 */       for (WingHandler wing : rcentity.physicsWorld.wings)
/*     */       {
/* 289 */         for (int i = 0; i < wing.getSections(); i++)
/*     */         {
/*     */ 
/* 292 */           GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
/* 293 */           GL11.glBegin(1);
/*     */           
/* 295 */           GL11.glVertex3f(wing.getPosition(i).x, wing.getPosition(i).y, wing.getPosition(i).z);
/* 296 */           GL11.glVertex3f(wing.getLift(i).x / 2.0F + wing.getPosition(i).x, wing.getLift(i).y / 2.0F + wing.getPosition(i).y, wing.getLift(i).z / 2.0F + wing.getPosition(i).z);
/*     */           
/* 298 */           GL11.glEnd();
/*     */           
/* 300 */           GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
/* 301 */           GL11.glBegin(1);
/*     */           
/* 303 */           GL11.glVertex3f(wing.getPosition(i).x, wing.getPosition(i).y, wing.getPosition(i).z);
/* 304 */           GL11.glVertex3f(wing.getDrag(i).x * 2.0F + wing.getPosition(i).x, wing.getDrag(i).y * 2.0F + wing.getPosition(i).y, wing.getDrag(i).z * 2.0F + wing.getPosition(i).z);
/*     */           
/* 306 */           GL11.glEnd();
/*     */           
/* 308 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       }
/*     */       
/* 312 */       for (FloatsHandler floats : rcentity.physicsWorld.floats)
/*     */       {
/* 314 */         for (int i = 0; i < floats.getSections(); i++)
/*     */         {
/*     */ 
/* 317 */           GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
/* 318 */           GL11.glBegin(1);
/*     */           
/* 320 */           GL11.glVertex3f(floats.getPosition(i).x, floats.getPosition(i).y, floats.getPosition(i).z);
/* 321 */           GL11.glVertex3f(floats.getBouyancy(i).x / 2.0F + floats.getPosition(i).x, floats.getBouyancy(i).y / 2.0F + floats.getPosition(i).y, floats.getBouyancy(i).z / 2.0F + floats.getPosition(i).z);
/*     */           
/* 323 */           GL11.glEnd();
/*     */           
/* 325 */           GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
/* 326 */           GL11.glBegin(1);
/*     */           
/* 328 */           GL11.glVertex3f(floats.getPosition(i).x, floats.getPosition(i).y, floats.getPosition(i).z);
/* 329 */           GL11.glVertex3f(floats.getDrag(i).x * 2.0F + floats.getPosition(i).x, floats.getDrag(i).y * 2.0F + floats.getPosition(i).y, floats.getDrag(i).z * 2.0F + floats.getPosition(i).z);
/*     */           
/* 331 */           GL11.glEnd();
/*     */           
/* 333 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 338 */     GL11.glEnable(2896);
/* 339 */     GL11.glEnable(3553);
/*     */     
/* 341 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderRadarBox(GlobalEntity rcEntity, double posX, double posY, double posZ, ResourceLocation texLocation, float alpha, float scale)
/*     */   {
/* 347 */     int color = 13434675;
/* 348 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 349 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 350 */     float blue = (color & 0xFF) / 255.0F;
/*     */     
/* 352 */     float x = 1.5F;
/* 353 */     float y = 1.5F;
/*     */     
/* 355 */     GL11.glPushMatrix();
/*     */     
/* 357 */     this.renderManager.renderEngine.bindTexture(texLocation);
/*     */     
/* 359 */     GL11.glTranslatef((float)posX, (float)posY, (float)posZ);
/* 360 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 361 */     GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 362 */     GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 363 */     GL11.glScalef(scale, scale, scale);
/* 364 */     GL11.glDisable(2896);
/* 365 */     GL11.glDepthMask(false);
/* 366 */     GL11.glDisable(2929);
/* 367 */     GL11.glEnable(3042);
/*     */     
/* 369 */     GL11.glBlendFunc(1, 1);
/* 370 */     int i = 61680;
/* 371 */     int j = i % 65536;
/* 372 */     int k = i / 65536;
/* 373 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/*     */     
/*     */ 
/* 376 */     GL11.glColor4f(red, green, blue, alpha);
/*     */     
/* 378 */     Tessellator tessellator = Tessellator.getInstance();
/* 379 */     BufferBuilder vertexbuffer = tessellator.getBuffer();
/* 380 */     vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 381 */     vertexbuffer.pos(-x, y, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 382 */     vertexbuffer.pos(x, y, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 383 */     vertexbuffer.pos(x, -y, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 384 */     vertexbuffer.pos(-x, -y, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 385 */     tessellator.draw();
/*     */     
/* 387 */     GL11.glEnable(2929);
/* 388 */     GL11.glDepthMask(true);
/* 389 */     GL11.glEnable(2896);
/* 390 */     GL11.glDisable(3042);
/*     */     
/* 392 */     GL11.glBlendFunc(770, 771);
/* 393 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 395 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void loadVBO()
/*     */   {
/* 400 */     this.mainBody.createVBO();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
/*     */   {
/* 407 */     renderRcEntity((GlobalEntity)entity, par2, par4, par6, par8, par9);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 413 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Renders/GlobalRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */