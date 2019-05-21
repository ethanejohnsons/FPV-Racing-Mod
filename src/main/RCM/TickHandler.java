/*     */ package RCM;
/*     */
/*     */ import RCM.Entities.CameraHandler;
/*     */ import RCM.Entities.EntityF22;
/*     */ import RCM.Entities.EntitySubmarine;
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import RCM.Packets.MessageCanTeleportPlayer;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Packets.MessageTeleportPlayer;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiIngame;
/*     */ import net.minecraft.client.gui.GuiNewChat;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.Style;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.event.ClickEvent;
/*     */ import net.minecraft.util.text.event.ClickEvent.Action;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
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
/*     */ public class TickHandler
/*     */ {
/*  52 */   private ResourceLocation textureLocationCompass = new ResourceLocation("thercmod:textures/overlay/compass.png");
/*  53 */   private ResourceLocation textureLocationArrow = new ResourceLocation("thercmod:textures/overlay/arrow.png");
/*  54 */   private ResourceLocation textureLocationRemoteScreen = new ResourceLocation("thercmod:textures/overlay/remotescreen.png");
/*  55 */   private ResourceLocation textureLocationCrosshair = new ResourceLocation("thercmod:textures/overlay/crosshair.png");
/*     */   public static GlobalEntity rcEntity;
/*     */   private CameraHandler camEntity;
/*     */   public double posX;
/*     */   public double posY;
/*     */   public double posZ;
/*  61 */   private boolean setPos = false;
/*     */
/*     */   private float fistLookYaw;
/*     */
/*     */   private float lookYaw;
/*     */
/*     */   private float prevLookYaw;
/*     */   private int camMode;
/*     */   private int oldCamMode;
/*     */   public static boolean thirdPersonView;
/*     */   private boolean canResetPos;
/*     */   private long counter;
/*     */   private boolean notificationSent;
/*     */   private long endTime;
/*     */
/*     */   @SubscribeEvent
/*     */   public void tick(TickEvent.RenderTickEvent event)
/*     */   {
/*  79 */     Minecraft mc = FMLClientHandler.instance().getClient();
/*  80 */     this.endTime = System.currentTimeMillis();
/*     */
/*  82 */     if (!"3.2.1.0".equals(RCM_Main.newVersion))
/*     */     {
/*  84 */       if ((!this.notificationSent) && (mc.inGameHasFocus))
/*     */       {
/*  86 */         this.notificationSent = true;
/*     */
/*  88 */         TextComponentTranslation finalMsg = new TextComponentTranslation("", new Object[0]);
/*     */
/*  90 */         TextComponentTranslation text = new TextComponentTranslation("The RC Mod ", new Object[0]);
/*  91 */         text.setStyle(new Style().setColor(TextFormatting.BLUE));
/*  92 */         finalMsg.appendSibling(text);
/*     */
/*  94 */         text = new TextComponentTranslation("v" + RCM_Main.newVersion, new Object[0]);
/*  95 */         text.setStyle(new Style().setColor(TextFormatting.GREEN));
/*  96 */         finalMsg.appendSibling(text);
/*     */
/*  98 */         text = new TextComponentTranslation(" is now available.", new Object[0]);
/*  99 */         text.setStyle(new Style().setColor(TextFormatting.WHITE));
/* 100 */         finalMsg.appendSibling(text);
/*     */
/* 102 */         mc.player.sendMessage(finalMsg);
/*     */
/* 104 */         finalMsg = new TextComponentTranslation("Download it at ", new Object[0]);
/*     */
/* 106 */         Style linkStyle = new Style();
/* 107 */         linkStyle.setUnderlined(Boolean.valueOf(true));
/* 108 */         linkStyle.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://www.eldercodes.net/downloads"));
/* 109 */         linkStyle.setColor(TextFormatting.LIGHT_PURPLE);
/*     */
/* 111 */         text = new TextComponentTranslation("www.eldercodes.net", new Object[0]);
/* 112 */         text.setStyle(linkStyle);
/* 113 */         finalMsg.appendSibling(text);
/*     */
/* 115 */         mc.player.sendMessage(finalMsg);
/*     */       }
/*     */     }
/*     */
/* 119 */     ScaledResolution scaledresolution = new ScaledResolution(mc);
/* 120 */     int i = scaledresolution.getScaledWidth();
/* 121 */     int j = scaledresolution.getScaledHeight();
/*     */
/* 123 */     if ((thirdPersonView) && (this.camEntity != null))
/*     */     {
/* 125 */       RCM_Main.proxy.getSoundHandler().setListener(this.camEntity, event.renderTickTime);
/*     */     }
/*     */
/* 128 */     if ((thirdPersonView) && (mc.inGameHasFocus) && (rcEntity != null))
/*     */     {
/* 130 */       drawCompass(mc, i, 30, event.renderTickTime);
/* 131 */       drawRemoteScreen(mc, i, j, event.renderTickTime);
/* 132 */       drawRemoteScreenText(mc, i, j, event.renderTickTime);
/* 133 */       drawChat(mc, i, j);
/*     */
/* 135 */       if ((rcEntity.weaponsMode) && ((rcEntity instanceof EntitySubmarine)))
/*     */       {
/* 137 */         drawCrosshair(mc, i, j);
/*     */       }
/*     */     }
/*     */
/* 141 */     ItemStack itemstack = null;
/*     */
/* 143 */     if (mc.player != null)
/*     */     {
/* 145 */       itemstack = mc.player.inventory.getCurrentItem();
/*     */     }
/*     */
/* 148 */     if ((itemstack != null) && (rcEntity != null))
/*     */     {
/*     */
/* 151 */       if ((rcEntity.activated) && (rcEntity.holdingremotecontrol(mc.player)))
/*     */       {
/*     */
/* 154 */         this.camMode = mc.gameSettings.thirdPersonView;
/* 155 */         setCamLock(mc, itemstack);
/*     */
/* 157 */         if ((!thirdPersonView) && (this.oldCamMode - this.camMode == 2))
/*     */         {
/* 159 */           setRCCam(mc.world, mc, rcEntity);
/*     */         }
/* 161 */         else if ((thirdPersonView) && (this.camMode == 1))
/*     */         {
/* 163 */           resetRCCam(mc);
/* 164 */           this.camMode = 0;
/* 165 */           this.setPos = false;
/*     */         }
/*     */
/* 168 */         this.oldCamMode = this.camMode;
/*     */       }
/*     */       else
/*     */       {
/* 172 */         if (thirdPersonView)
/*     */         {
/* 174 */           resetRCCam(mc);
/* 175 */           this.camMode = 0;
/*     */         }
/* 177 */         this.setPos = false;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 182 */       if (thirdPersonView)
/*     */       {
/* 184 */         resetRCCam(mc);
/* 185 */         this.camMode = 0;
/*     */       }
/* 187 */       this.setPos = false;
/*     */     }
/*     */   }
/*     */
/*     */   private void setCamLock(Minecraft mc, ItemStack itemstack)
/*     */   {
/* 193 */     if (!this.setPos)
/*     */     {
/* 195 */       this.posX = mc.player.posX;
/* 196 */       this.posY = mc.player.posY;
/* 197 */       this.posZ = mc.player.posZ;
/* 198 */       this.fistLookYaw = (mc.player.rotationYaw / 180.0F * 3.1415927F);
/* 199 */       this.setPos = true;
/*     */
/* 201 */       while (this.fistLookYaw >= 6.2831855F) this.fistLookYaw -= 6.2831855F;
/* 202 */       while (this.fistLookYaw < -6.2831855F) { this.fistLookYaw += 6.2831855F;
/*     */       }
/*     */     }
/* 205 */     Vector3f pitchLookVec = new Vector3f((float)(rcEntity.posX - this.posX), (float)(rcEntity.posY - (this.posY + mc.player.eyeHeight)), (float)(rcEntity.posZ - this.posZ));
/* 206 */     Vector3f pitchReferenceVec = new Vector3f(0.0F, 1.0F, 0.0F);
/* 207 */     Vector3f yawLookVec = new Vector3f((float)(rcEntity.posX - this.posX), 0.0F, (float)(rcEntity.posZ - this.posZ));
/* 208 */     Vector3f prevYawLookVec = new Vector3f((float)(rcEntity.prevPosX - this.posX), 0.0F, (float)(rcEntity.prevPosZ - this.posZ));
/* 209 */     Vector3f yawReferenceVec = new Vector3f(0.0F, 0.0F, 1.0F);
/*     */
/* 211 */     float lookPitch = pitchLookVec.angle(pitchReferenceVec) - 1.5707964F;
/*     */
/* 213 */     if (pitchLookVec.x < 0.0F)
/*     */     {
/* 215 */       this.lookYaw = yawLookVec.angle(yawReferenceVec);
/* 216 */       this.prevLookYaw = prevYawLookVec.angle(yawReferenceVec);
/*     */     }
/*     */     else
/*     */     {
/* 220 */       this.lookYaw = (-yawLookVec.angle(yawReferenceVec));
/* 221 */       this.prevLookYaw = (-prevYawLookVec.angle(yawReferenceVec));
/*     */     }
/*     */
/*     */     float lookYawdiff;
/*     */     float lookYawdiff;
/* 226 */     if (this.lookYaw - this.fistLookYaw > 3.1415927F)
/*     */     {
/* 228 */       lookYawdiff = this.lookYaw - this.fistLookYaw - 6.2831855F;
/*     */     } else { float lookYawdiff;
/* 230 */       if (this.lookYaw - this.fistLookYaw < -3.1415927F)
/*     */       {
/* 232 */         lookYawdiff = this.lookYaw - this.fistLookYaw + 6.2831855F;
/*     */       }
/*     */       else
/*     */       {
/* 236 */         lookYawdiff = this.lookYaw - this.fistLookYaw;
/*     */       }
/*     */     }
/* 239 */     if ((mc.gameSettings.thirdPersonView == 0) && (!thirdPersonView))
/*     */     {
/* 241 */       mc.player.rotationPitch = (lookPitch * 180.0F / 3.1415927F + 5.0F);
/*     */     }
/* 243 */     else if ((mc.gameSettings.thirdPersonView == 1) && (!thirdPersonView))
/*     */     {
/* 245 */       mc.player.rotationPitch = (lookPitch * 180.0F / 3.1415927F + 15.0F);
/*     */     }
/* 247 */     else if (!thirdPersonView)
/*     */     {
/* 249 */       mc.player.rotationPitch = (lookPitch * 180.0F / 3.1415927F);
/*     */     }
/*     */
/* 252 */     if (!thirdPersonView)
/*     */     {
/* 254 */       mc.player.rotationYaw += lookYawdiff * 180.0F / 3.1415927F;
/*     */     }
/*     */
/* 257 */     double distToEntity = rcEntity.getDistance(this.posX, rcEntity.posY, this.posZ);
/*     */
/* 259 */     if (((distToEntity <= 200.0D) && (!thirdPersonView)) || ((distToEntity <= 50.0D) && (thirdPersonView) && (!this.canResetPos)))
/*     */     {
/*     */
/* 262 */       mc.player.move(MoverType.PLAYER, this.posX - mc.player.posX, this.posY - mc.player.posY, this.posZ - mc.player.posZ);
/*     */     }
/* 264 */     else if ((thirdPersonView) && (distToEntity > 50.0D) && (rcEntity.activated))
/*     */     {
/* 266 */       MessageHandler.handler.sendToServer(new MessageCanTeleportPlayer(rcEntity.getEntityId(), true));
/* 267 */       this.canResetPos = true;
/*     */     }
/* 269 */     else if (distToEntity <= 50.0D)
/*     */     {
/* 271 */       resetPlayerPos(mc.player);
/*     */     }
/*     */
/* 274 */     mc.player.motionX = 0.0D;
/* 275 */     mc.player.motionY = 0.0D;
/* 276 */     mc.player.motionZ = 0.0D;
/* 277 */     this.fistLookYaw = this.lookYaw;
/*     */   }
/*     */
/*     */   public void setRCCam(World world, Minecraft mc, GlobalEntity entity)
/*     */   {
/* 282 */     this.camEntity = new CameraHandler(world, entity);
/* 283 */     world.spawnEntity(this.camEntity);
/*     */
/* 285 */     mc.setRenderViewEntity(this.camEntity);
/* 286 */     mc.gameSettings.hideGUI = true;
/* 287 */     mc.gameSettings.thirdPersonView = 0;
/* 288 */     thirdPersonView = true;
/*     */   }
/*     */
/*     */   public void resetRCCam(Minecraft mc)
/*     */   {
/* 293 */     if (this.camEntity != null)
/*     */     {
/* 295 */       this.camEntity.setDead();
/*     */     }
/*     */
/* 298 */     if (mc.player != null)
/*     */     {
/* 300 */       mc.player.move(MoverType.PLAYER, this.posX - mc.player.posX, this.posY - mc.player.posY, this.posZ - mc.player.posZ);
/* 301 */       mc.setRenderViewEntity(mc.player);
/* 302 */       mc.gameSettings.hideGUI = false;
/* 303 */       mc.gameSettings.thirdPersonView = 0;
/*     */
/* 305 */       if (this.canResetPos)
/*     */       {
/* 307 */         resetPlayerPos(mc.player);
/*     */       }
/*     */     }
/*     */
/* 311 */     thirdPersonView = false;
/*     */   }
/*     */
/*     */   private void resetPlayerPos(EntityPlayer thePlayer)
/*     */   {
/* 316 */     if (rcEntity != null)
/*     */     {
/* 318 */       MessageHandler.handler.sendToServer(new MessageCanTeleportPlayer(rcEntity.getEntityId(), false));
/*     */     }
/*     */
/* 321 */     MessageHandler.handler.sendToServer(new MessageTeleportPlayer(thePlayer.getEntityId(), this.posX, this.posY, this.posZ));
/* 322 */     this.canResetPos = false;
/*     */   }
/*     */
/*     */
/*     */   public void drawCompass(Minecraft mc, int i, int j, float renderTicks)
/*     */   {
/* 328 */     mc.entityRenderer.setupOverlayRendering();
/*     */
/* 330 */     int color = 13434675;
/* 331 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 332 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 333 */     float blue = (color & 0xFF) / 255.0F;
/*     */
/* 335 */     int x = 256;
/* 336 */     int y = 256;
/*     */
/* 338 */     float compassRotation = 180.0F - rcEntity.prevRotationYaw - (rcEntity.rotationYaw - rcEntity.prevRotationYaw) * renderTicks;
/* 339 */     float arrowDirc = (this.prevLookYaw + (this.lookYaw - this.prevLookYaw) * renderTicks) * 180.0F / 3.1415927F;
/*     */
/* 341 */     float scale = 0.1F;
/*     */
/* 343 */     GL11.glPushMatrix();
/*     */
/* 345 */     mc.renderEngine.bindTexture(this.textureLocationCompass);
/*     */
/* 347 */     GL11.glTranslatef(i / 2.0F, j, 0.0F);
/* 348 */     GL11.glRotatef(compassRotation, 0.0F, 0.0F, 1.0F);
/*     */
/*     */
/* 351 */     GL11.glScalef(scale, scale, scale);
/*     */
/* 353 */     GL11.glColor3f(red, green, blue);
/*     */
/* 355 */     Tessellator tessellator = Tessellator.getInstance();
/* 356 */     BufferBuilder vertexbuffer = tessellator.getBuffer();
/* 357 */     vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 358 */     vertexbuffer.pos(-x + 1, y, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 359 */     vertexbuffer.pos(x, y, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 360 */     vertexbuffer.pos(x, -y, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 361 */     vertexbuffer.pos(-x + 1, -y, 0.0D).tex(0.0D, 0.0D).endVertex();
/*     */
/* 363 */     GL11.glEnable(3042);
/* 364 */     GL11.glBlendFunc(770, 771);
/*     */
/* 366 */     tessellator.draw();
/*     */
/* 368 */     GL11.glDisable(3042);
/*     */
/* 370 */     GL11.glPopMatrix();
/*     */
/* 372 */     GL11.glPushMatrix();
/*     */
/* 374 */     mc.renderEngine.bindTexture(this.textureLocationArrow);
/*     */
/* 376 */     GL11.glTranslatef(i / 2.0F, j, 0.0F);
/* 377 */     GL11.glRotatef(arrowDirc + compassRotation, 0.0F, 0.0F, 1.0F);
/*     */
/*     */
/* 380 */     GL11.glScalef(scale, scale, scale);
/* 381 */     GL11.glColor3f(red, green, blue);
/*     */
/* 383 */     Tessellator tessellator2 = Tessellator.getInstance();
/* 384 */     BufferBuilder vertexbuffer2 = tessellator2.getBuffer();
/* 385 */     vertexbuffer2.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 386 */     vertexbuffer2.pos(-x + 1, y, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 387 */     vertexbuffer2.pos(x, y, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 388 */     vertexbuffer2.pos(x, -y, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 389 */     vertexbuffer2.pos(-x + 1, -y, 0.0D).tex(0.0D, 0.0D).endVertex();
/*     */
/* 391 */     GL11.glEnable(3042);
/* 392 */     GL11.glBlendFunc(770, 771);
/*     */
/* 394 */     tessellator2.draw();
/*     */
/* 396 */     GL11.glDisable(3042);
/*     */
/* 398 */     GL11.glPopMatrix();
/*     */   }
/*     */
/*     */
/*     */   public void drawRemoteScreen(Minecraft mc, int i, int j, float renderTicks)
/*     */   {
/* 404 */     mc.entityRenderer.setupOverlayRendering();
/*     */
/* 406 */     int x = 417;
/* 407 */     int y = 233;
/*     */
/* 409 */     float scale = 0.115F;
/*     */
/* 411 */     GL11.glPushMatrix();
/*     */
/* 413 */     mc.renderEngine.bindTexture(this.textureLocationRemoteScreen);
/* 414 */     GL11.glTranslatef(5.0F + x * scale, j - 5 - y * scale, 0.0F);
/*     */
/* 416 */     GL11.glScalef(scale, scale, scale);
/* 417 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*     */
/* 419 */     Tessellator tessellator = Tessellator.getInstance();
/* 420 */     BufferBuilder vertexbuffer = tessellator.getBuffer();
/* 421 */     vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 422 */     vertexbuffer.pos(-x + 1, y, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 423 */     vertexbuffer.pos(x, y, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 424 */     vertexbuffer.pos(x, -y, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 425 */     vertexbuffer.pos(-x + 1, -y, 0.0D).tex(0.0D, 0.0D).endVertex();
/*     */
/* 427 */     GL11.glEnable(3042);
/* 428 */     GL11.glBlendFunc(770, 771);
/*     */
/* 430 */     tessellator.draw();
/*     */
/* 432 */     GL11.glDisable(3042);
/*     */
/* 434 */     GL11.glPopMatrix();
/*     */
/* 436 */     GL11.glPushMatrix();
/*     */
/* 438 */     mc.renderEngine.bindTexture(this.textureLocationRemoteScreen);
/* 439 */     GL11.glTranslatef(i - 5 - x * scale, j - 5 - y * scale, 0.0F);
/*     */
/* 441 */     GL11.glScalef(scale, scale, scale);
/* 442 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*     */
/* 444 */     Tessellator tessellator2 = Tessellator.getInstance();
/* 445 */     BufferBuilder vertexbuffer2 = tessellator2.getBuffer();
/* 446 */     vertexbuffer2.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 447 */     vertexbuffer2.pos(-x + 1, y, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 448 */     vertexbuffer2.pos(x, y, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 449 */     vertexbuffer2.pos(x, -y, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 450 */     vertexbuffer2.pos(-x + 1, -y, 0.0D).tex(0.0D, 0.0D).endVertex();
/*     */
/* 452 */     GL11.glEnable(3042);
/* 453 */     GL11.glBlendFunc(770, 771);
/*     */
/* 455 */     tessellator.draw();
/*     */
/* 457 */     GL11.glDisable(3042);
/*     */
/* 459 */     GL11.glPopMatrix();
/*     */   }
/*     */
/*     */   public void drawRemoteScreenText(Minecraft mc, int i, int j, float renderTicks)
/*     */   {
/* 464 */     mc.entityRenderer.setupOverlayRendering();
/*     */
/* 466 */     GL11.glPushMatrix();
/*     */
/* 468 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */
/* 470 */     String speed = String.format("%.1f", new Object[] { Float.valueOf(rcEntity.forwardVelocity) });
/* 471 */     String height = String.format("%.1f", new Object[] { Float.valueOf(rcEntity.altitude) });
/* 472 */     String range = String.format("%.1f", new Object[] { Double.valueOf(rcEntity.getDistance(this.posX, this.posY, this.posZ)) });
/*     */
/* 474 */     mc.fontRenderer.drawString("Speed: " + speed + "m/s", i - 92, j - 51, 6737151);
/* 475 */     mc.fontRenderer.drawString("Height: " + height + "m", i - 92, j - 36, 6737151);
/* 476 */     mc.fontRenderer.drawString("Range: " + range + "m", i - 92, j - 21, 6737151);
/*     */
/* 478 */     String throttle = String.format("%.0f", new Object[] { Float.valueOf(rcEntity.power) });
/* 479 */     String type = "N/A";
/*     */
/* 481 */     int wCount = rcEntity.physicsWorld.getWeaponCount();
/*     */
/* 483 */     if (((rcEntity instanceof EntityF22)) || ((rcEntity instanceof EntitySubmarine)))
/*     */     {
/* 485 */       if (rcEntity.weaponsMode)
/*     */       {
/* 487 */         type = "";
/* 488 */         mc.fontRenderer.drawString("Weapon Qty:", 13, j - 21, 6737151);
/* 489 */         mc.fontRenderer.drawString(wCount + "M", 75, j - 21, 6737151);
/*     */       }
/*     */
/* 492 */       if ((wCount != 0) && (rcEntity.weaponsMode))
/*     */       {
/* 494 */         mc.fontRenderer.drawString("Armed", 55, j - 36, 16724787);
/*     */       }
/* 496 */       else if ((rcEntity.weaponsMode) && (this.endTime - this.counter <= 500L))
/*     */       {
/* 498 */         mc.fontRenderer.drawString("Loading", 55, j - 36, 16763904);
/*     */       }
/* 500 */       else if ((rcEntity.weaponsMode) && (this.endTime - this.counter > 1000L))
/*     */       {
/* 502 */         this.counter = this.endTime;
/*     */       }
/* 504 */       else if (!rcEntity.weaponsMode)
/*     */       {
/* 506 */         type = "";
/* 507 */         mc.fontRenderer.drawString("Safe", 55, j - 36, 6736896);
/*     */       }
/*     */     }
/*     */
/* 511 */     mc.fontRenderer.drawString("Power: " + throttle + "%", 13, j - 51, 6737151);
/* 512 */     mc.fontRenderer.drawString("Weapon: " + type, 13, j - 36, 6737151);
/*     */
/* 514 */     GL11.glPopMatrix();
/*     */   }
/*     */
/*     */   private void drawCrosshair(Minecraft mc, int i, int j)
/*     */   {
/* 519 */     mc.entityRenderer.setupOverlayRendering();
/*     */
/* 521 */     int x = 8;
/* 522 */     int y = 8;
/*     */
/* 524 */     GL11.glPushMatrix();
/*     */
/* 526 */     mc.renderEngine.bindTexture(this.textureLocationCrosshair);
/* 527 */     GL11.glTranslatef(i / 2.0F + 0.5F, j / 2 + 0.5F, 0.0F);
/*     */
/* 529 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*     */
/* 531 */     Tessellator tessellator = Tessellator.getInstance();
/* 532 */     BufferBuilder vertexbuffer = tessellator.getBuffer();
/* 533 */     vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 534 */     vertexbuffer.pos(-x, y, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 535 */     vertexbuffer.pos(x, y, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 536 */     vertexbuffer.pos(x, -y, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 537 */     vertexbuffer.pos(-x, -y, 0.0D).tex(0.0D, 0.0D).endVertex();
/*     */
/* 539 */     GL11.glEnable(3042);
/* 540 */     GL11.glBlendFunc(770, 771);
/*     */
/* 542 */     tessellator.draw();
/*     */
/* 544 */     GL11.glDisable(3042);
/*     */
/* 546 */     GL11.glPopMatrix();
/*     */   }
/*     */
/*     */   private void drawChat(Minecraft mc, int i, int j)
/*     */   {
/* 551 */     GL11.glTranslatef(0.0F, j - 83.0F, 0.0F);
/* 552 */     mc.ingameGUI.getChatGUI().drawChat(mc.ingameGUI.getUpdateCounter());
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/TickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
