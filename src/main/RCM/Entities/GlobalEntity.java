/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.Audio.ModSoundEvents;
/*     */ import RCM.Audio.MovingSoundAir;
/*     */ import RCM.CommonProxy;
/*     */ import RCM.Items.ItemRemoteControl;
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageEntityState;
/*     */ import RCM.Packets.MessageGlobalEntity;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.PhysicsHelper;
/*     */ import RCM.Physics.PhysicsTickHandler;
/*     */ import RCM.RCM_Main;
/*     */ import RCM.TickHandler;
/*     */ import com.bulletphysics.dynamics.RigidBody;
/*     */ import java.util.List;
/*     */ import javax.vecmath.AxisAngle4f;
/*     */ import javax.vecmath.Quat4f;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.Style;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class GlobalEntity
/*     */   extends Entity
/*     */ {
/*     */   public EntityPlayer thePlayer;
/*     */   public String thePlayerName;
/*     */   public PhysicsHelper helper;
/*     */   public Vector3f Up;
/*     */   public Vector3f Forward;
/*     */   public Vector3f Left;
/*     */   public Vector3f drag;
/*     */   public Vector3f position;
/*     */   public Vector3f prevVeloc;
/*     */   private Quat4f netQuat;
/*     */   public double netX;
/*     */   public double netY;
/*     */   public double netZ;
/*     */   public float netIncRot;
/*     */   public float rotationRoll;
/*     */   public float prevRotationRoll;
/*     */   public float forwardVelocity;
/*     */   public float rotationY;
/*     */   public float altitude;
/*     */   public boolean activated;
/*     */   public boolean damaged;
/*     */   public boolean netDamaged;
/*     */   public boolean inTeleportMode;
/*     */   public float power;
/*     */   public float netPower;
/*     */   private long startTime;
/*     */   private long endTime;
/*     */   public int timer;
/*     */   private long msgTimer;
/*     */   private long restTimer;
/*     */   private long tickTimer;
/*     */   public boolean canRender;
/*     */   public boolean packetReceived;
/*     */   public float lockProgress;
/*     */   public float prevLockProgress;
/*     */   public boolean weaponLock;
/*     */   private boolean isRegistered;
/*     */   private int[] spawnRegister;
/*     */   private boolean hitPlayed;
/*     */   private int coolDownCounter;
/*     */   public boolean weaponsMode;
/*     */   public PhysicsHandler physicsWorld;
/*     */   private boolean requestExplosion;
/*     */   private double explodeX;
/*     */   private double explodeY;
/*     */   private double explodeZ;
/*     */   
/*     */   public GlobalEntity(World world)
/*     */   {
/* 104 */     super(world);
/*     */   }
/*     */   
/*     */   public int getID()
/*     */   {
/* 109 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/* 115 */     return 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 121 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 127 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBox(Entity entity)
/*     */   {
/* 133 */     return entity.getEntityBoundingBox();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBePushed()
/*     */   {
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   public void packetUpdate(double x, double y, double z, Quat4f localQuat, float entityPower, boolean entityDamaged)
/*     */   {
/* 144 */     this.netX = x;
/* 145 */     this.netY = y;
/* 146 */     this.netZ = z;
/*     */     
/* 148 */     this.netQuat = localQuat;
/* 149 */     this.netPower = entityPower;
/* 150 */     this.netDamaged = entityDamaged;
/*     */     
/* 152 */     this.packetReceived = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAdditionalInfo() {}
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void packetSetState(int ID, boolean active)
/*     */   {
/* 161 */     this.activated = active;
/*     */     
/* 163 */     if (FMLClientHandler.instance().getClient().player.getEntityId() == ID)
/*     */     {
/* 165 */       this.thePlayer = FMLClientHandler.instance().getClient().player;
/*     */     }
/*     */     else
/*     */     {
/* 169 */       List playerList = RCM_Main.proxy.getClientWorld().playerEntities;
/*     */       
/* 171 */       for (int i = 0; i < playerList.size(); i++)
/*     */       {
/* 173 */         EntityPlayer entityplayer = (EntityPlayer)playerList.get(i);
/*     */         
/* 175 */         if (entityplayer.getEntityId() == ID)
/*     */         {
/* 177 */           this.thePlayerName = entityplayer.getDisplayName().getFormattedText();
/* 178 */           this.thePlayer = entityplayer;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void entityInit()
/*     */   {
/* 194 */     if (this.world.isRemote)
/*     */     {
/* 196 */       registerSounds();
/*     */     }
/*     */     
/* 199 */     this.coolDownCounter = 0;
/* 200 */     this.startTime = System.currentTimeMillis();
/* 201 */     this.timer = 0;
/*     */     
/* 203 */     this.Forward = new Vector3f(0.0F, 0.0F, 1.0F);
/* 204 */     this.Up = new Vector3f(0.0F, 1.0F, 0.0F);
/* 205 */     this.Left = new Vector3f(1.0F, 0.0F, 0.0F);
/* 206 */     this.drag = new Vector3f(0.0F, 0.0F, 0.0F);
/* 207 */     this.netQuat = new Quat4f();
/* 208 */     this.helper = new PhysicsHelper();
/* 209 */     this.prevVeloc = new Vector3f();
/* 210 */     this.damaged = false;
/* 211 */     this.inTeleportMode = false;
/* 212 */     this.canRender = false;
/* 213 */     this.packetReceived = false;
/* 214 */     this.hitPlayed = false;
/*     */     
/* 216 */     this.spawnRegister = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
/*     */   }
/*     */   
/*     */ 
/*     */   private void alignRotation(Quat4f localQuat)
/*     */   {
/* 222 */     this.Forward = this.helper.rotateVector(localQuat, new Vector3f(0.0F, 0.0F, 1.0F));
/* 223 */     this.Up = this.helper.rotateVector(localQuat, new Vector3f(0.0F, 1.0F, 0.0F));
/* 224 */     this.Left = this.helper.rotateVector(localQuat, new Vector3f(1.0F, 0.0F, 0.0F));
/*     */     
/* 226 */     this.rotationPitch = getPitch(this.Forward);
/* 227 */     this.rotationY = getYaw(this.Forward);
/* 228 */     this.rotationRoll = getRoll(this.Left, this.Forward);
/*     */     
/* 230 */     float prevRotationY = this.prevRotationYaw % 360.0F;
/*     */     
/* 232 */     if (this.rotationY - prevRotationY <= -270.0F)
/*     */     {
/* 234 */       this.rotationYaw += 360.0F + (this.rotationY - prevRotationY);
/*     */     }
/* 236 */     else if (this.rotationY - prevRotationY >= 270.0F)
/*     */     {
/* 238 */       this.rotationYaw += this.rotationY - prevRotationY - 360.0F;
/*     */     }
/*     */     else
/*     */     {
/* 242 */       this.rotationYaw += this.rotationY - prevRotationY;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 249 */     super.onUpdate();
/*     */     
/* 251 */     this.prevRotationYaw = this.rotationYaw;
/* 252 */     this.prevRotationPitch = this.rotationPitch;
/* 253 */     this.prevRotationRoll = this.rotationRoll;
/*     */     
/* 255 */     if ((this.damaged) && (!this.hitPlayed))
/*     */     {
/* 257 */       playSound(ModSoundEvents.prophit, 1.2F, 1.2F);
/* 258 */       this.hitPlayed = true;
/*     */     }
/*     */     
/*     */ 
/* 262 */     this.endTime = System.currentTimeMillis();
/*     */     
/* 264 */     if ((this.endTime - this.startTime > 1800000L) || ((!this.world.isRemote) && (this.thePlayer == null)))
/*     */     {
/* 266 */       setDead();
/*     */     }
/*     */     
/* 269 */     if ((this.world.isRemote) && (this.physicsWorld == null))
/*     */     {
/* 271 */       this.position = new Vector3f((float)this.posX, (float)this.posY, (float)this.posZ);
/* 272 */       float spawnRotation = -this.rotationYaw * 3.1415927F / 180.0F;
/* 273 */       this.physicsWorld = new PhysicsHandler(getID(), this.position, spawnRotation, this);
/*     */     }
/*     */     
/* 276 */     if ((!this.world.isRemote) && (this.thePlayer != null))
/*     */     {
/* 278 */       MessageHandler.handler.sendToDimension(new MessageEntityState(getEntityId(), this.thePlayer.getEntityId(), this.activated), this.world.provider.getDimension());
/*     */     }
/*     */     
/* 281 */     if (this.coolDownCounter > 0)
/*     */     {
/* 283 */       this.coolDownCounter -= 1;
/*     */     }
/*     */     
/*     */ 
/* 287 */     if ((this.world.isRemote) && (this.thePlayer == FMLClientHandler.instance().getClient().player))
/*     */     {
/*     */ 
/* 290 */       this.position.set(this.physicsWorld.getPosition());
/* 291 */       this.physicsWorld.requestCollisionShapes = true;
/*     */       
/* 293 */       if (!this.isRegistered)
/*     */       {
/* 295 */         RCM_Main.tickHandler.register(this);
/* 296 */         this.isRegistered = true;
/*     */       }
/*     */       
/* 299 */       if ((TickHandler.rcEntity == null) && (!this.activated))
/*     */       {
/* 301 */         TickHandler.rcEntity = this;
/*     */       }
/* 303 */       else if ((TickHandler.rcEntity == this) && (!this.activated) && (this.endTime - this.tickTimer > 2000L))
/*     */       {
/* 305 */         TickHandler.rcEntity = null;
/*     */       }
/*     */       
/* 308 */       if ((TickHandler.rcEntity == this) && (TickHandler.thirdPersonView))
/*     */       {
/* 310 */         this.weaponsMode = this.physicsWorld.weaponsMode;
/*     */       }
/*     */       else
/*     */       {
/* 314 */         this.physicsWorld.weaponsMode = false;
/* 315 */         this.weaponsMode = false;
/*     */       }
/*     */       
/* 318 */       if (this.thePlayerName != null)
/*     */       {
/* 320 */         this.thePlayerName = null;
/*     */       }
/*     */       
/* 323 */       double playerToEntityRange = getDistance(this.thePlayer.posX, this.posY, this.thePlayer.posZ);
/*     */       
/* 325 */       if ((this.thePlayer != null) && (this.activated) && (!holdingremotecontrol(this.thePlayer)) && (KeyHandler.retrieve) && (playerToEntityRange <= 100.0D) && (TickHandler.rcEntity == this))
/*     */       {
/*     */ 
/* 328 */         this.physicsWorld.entityBody.activate(false);
/*     */         
/* 330 */         AxisAngle4f initRotate = new AxisAngle4f(0.0F, 1.0F, 0.0F, -this.thePlayer.rotationYaw / 180.0F * 3.1415927F);
/* 331 */         Quat4f resetQuat = new Quat4f();
/* 332 */         resetQuat.set(initRotate);
/*     */         
/* 334 */         Vector3f resetPosition = new Vector3f(this.helper.rotateVector(resetQuat, new Vector3f(0.0F, 0.0F, 2.5F)));
/* 335 */         Vector3f playerPos = new Vector3f((float)this.thePlayer.posX, (float)this.thePlayer.posY + 1.0F, (float)this.thePlayer.posZ);
/*     */         
/* 337 */         resetPosition.add(playerPos);
/*     */         
/* 339 */         this.physicsWorld.setEntityLinearVelocity(new Vector3f(0.0F, 0.0F, 0.0F));
/* 340 */         this.physicsWorld.setEntityMotionState(resetQuat, resetPosition, 1.0F);
/*     */         
/* 342 */         Vector3f maxAABB = new Vector3f();
/* 343 */         Vector3f minAABB = new Vector3f();
/* 344 */         this.physicsWorld.getEntityAABB(minAABB, maxAABB);
/*     */         
/* 346 */         AxisAlignedBB boundingBox = new AxisAlignedBB(minAABB.x, minAABB.y - 1.0F, minAABB.z, maxAABB.x, maxAABB.y, maxAABB.z);
/*     */         
/* 348 */         float step = -0.75F;
/*     */         
/* 350 */         while (this.world.getCollisionBoxes(this, boundingBox).size() > 0)
/*     */         {
/* 352 */           step += 0.25F;
/* 353 */           boundingBox = new AxisAlignedBB(minAABB.x, minAABB.y + step, minAABB.z, maxAABB.x, maxAABB.y + step, maxAABB.z);
/*     */         }
/*     */         
/* 356 */         resetPosition.y += step;
/*     */         
/* 358 */         this.physicsWorld.setEntityMotionState(resetQuat, resetPosition, 1.0F);
/*     */         
/* 360 */         this.prevVeloc.set(0.0F, 0.0F, 0.0F);
/*     */       }
/* 362 */       else if ((this.thePlayer != null) && (!holdingremotecontrol(this.thePlayer)) && (KeyHandler.retrieve) && (TickHandler.rcEntity == this) && (playerToEntityRange > 100.0D) && (this.endTime - this.msgTimer > 5000L))
/*     */       {
/*     */ 
/* 365 */         showPlayerText("WARNING: ", "The " + getEntityName() + " is outside the retrievable range.", 1);
/* 366 */         this.msgTimer = this.endTime;
/*     */       }
/* 368 */       else if (((this instanceof EntityCar)) && (KeyHandler.retrieve) && (TickHandler.thirdPersonView) && (TickHandler.rcEntity == this) && (this.endTime - this.restTimer > 2000L) && 
/* 369 */         (Math.abs(this.forwardVelocity) < 0.01F))
/*     */       {
/* 371 */         AxisAngle4f initRotate = new AxisAngle4f(0.0F, 1.0F, 0.0F, -this.rotationYaw / 180.0F * 3.1415927F);
/* 372 */         Quat4f resetQuat = new Quat4f();
/* 373 */         resetQuat.set(initRotate);
/*     */         
/* 375 */         Vector3f entityPos = new Vector3f((float)this.posX, (float)this.posY + 0.5F, (float)this.posZ);
/*     */         
/* 377 */         this.physicsWorld.setEntityLinearVelocity(new Vector3f(0.0F, 0.0F, 0.0F));
/* 378 */         this.physicsWorld.setEntityMotionState(resetQuat, entityPos, 1.0F);
/*     */         
/* 380 */         this.prevVeloc.set(0.0F, 0.0F, 0.0F);
/* 381 */         this.restTimer = this.endTime;
/*     */       }
/*     */       else
/*     */       {
/* 385 */         this.physicsWorld.entityBody.activate(true);
/*     */       }
/*     */       
/* 388 */       calculatePhysics();
/*     */       
/* 390 */       this.forwardVelocity = getForwardVelocity(this.Forward, this.physicsWorld.getLinearVel());
/*     */       
/* 392 */       this.posX = this.physicsWorld.getPosition().x;
/* 393 */       this.posY = this.physicsWorld.getPosition().y;
/* 394 */       this.posZ = this.physicsWorld.getPosition().z;
/*     */       
/* 396 */       this.motionX = (this.posX - this.prevPosX);
/* 397 */       this.motionY = (this.posY - this.prevPosY);
/* 398 */       this.motionZ = (this.posZ - this.prevPosZ);
/*     */       
/* 400 */       move(MoverType.PLAYER, this.motionX, this.motionY, this.motionZ);
/* 401 */       alignRotation(this.physicsWorld.getLocalQuad());
/*     */       
/* 403 */       this.netX = this.posX;
/* 404 */       this.netY = this.posY;
/* 405 */       this.netZ = this.posZ;
/* 406 */       this.netQuat = this.physicsWorld.getLocalQuad();
/*     */       
/* 408 */       this.altitude = ((float)(this.posY - this.world.provider.getHorizon()));
/*     */       
/* 410 */       spawnParticles();
/*     */       
/* 412 */       MessageHandler.handler.sendToServer(new MessageGlobalEntity(getEntityId(), (float)this.posX, (float)this.posY, (float)this.posZ, this.physicsWorld
/*     */       
/* 414 */         .getLocalQuad(), this.power, this.damaged));
/*     */       
/* 416 */       if ((this.endTime - this.startTime > 150L) && (!(this instanceof EntityMissile)))
/*     */       {
/* 418 */         this.canRender = true;
/*     */       }
/* 420 */       else if (this.endTime - this.startTime > 300L)
/*     */       {
/* 422 */         this.canRender = true;
/*     */       }
/*     */       
/*     */     }
/* 426 */     else if ((this.world.isRemote) && (this.packetReceived))
/*     */     {
/*     */ 
/* 429 */       if (this.isRegistered)
/*     */       {
/* 431 */         RCM_Main.tickHandler.deRegister(this);
/* 432 */         this.isRegistered = false;
/*     */       }
/*     */       
/* 435 */       if ((TickHandler.rcEntity != null) && (TickHandler.rcEntity == this))
/*     */       {
/* 437 */         TickHandler.rcEntity = null;
/*     */       }
/*     */       
/* 440 */       this.physicsWorld.entityBody.activate(false);
/*     */       
/* 442 */       if (this.physicsWorld != null)
/*     */       {
/* 444 */         this.physicsWorld.setEntityMotionState(this.netQuat, new Vector3f((float)this.netX, (float)this.netY, (float)this.netZ), 1.0F);
/*     */       }
/*     */       
/* 447 */       this.motionX = (this.netX - this.posX);
/* 448 */       this.motionY = (this.netY - this.posY);
/* 449 */       this.motionZ = (this.netZ - this.posZ);
/*     */       
/* 451 */       move(MoverType.PLAYER, this.motionX, this.motionY, this.motionZ);
/*     */       
/* 453 */       alignRotation(this.netQuat);
/* 454 */       updateAdditionalInfo();
/*     */       
/* 456 */       this.power = this.netPower;
/* 457 */       this.damaged = this.netDamaged;
/*     */       
/* 459 */       spawnParticles();
/*     */       
/* 461 */       if (this.endTime - this.startTime > 150L)
/*     */       {
/* 463 */         this.canRender = true;
/*     */       }
/*     */       
/*     */     }
/* 467 */     else if ((!this.world.isRemote) && (this.packetReceived))
/*     */     {
/*     */ 
/* 470 */       double distToPlayer = getDistance(this.thePlayer.posX, this.posY, this.thePlayer.posZ);
/*     */       
/* 472 */       for (int i = 0; i < this.spawnRegister.length; i++)
/*     */       {
/* 474 */         if (this.spawnRegister[i] != -1)
/*     */         {
/* 476 */           spawnWeapon(this.spawnRegister[i]);
/* 477 */           this.spawnRegister[i] = -1;
/*     */         }
/*     */       }
/*     */       
/* 481 */       if (((distToPlayer > 50.0D) || (distToPlayer < 20.0D)) && (this.inTeleportMode))
/*     */       {
/* 483 */         EntityPlayerMP playerMP = (EntityPlayerMP)this.thePlayer;
/*     */         
/* 485 */         double yOffset = 150.0D;
/*     */         
/* 487 */         boolean isBlock = false;
/*     */         
/* 489 */         Vector3f normForward = new Vector3f(this.Forward);
/* 490 */         normForward.y = 0.0F;
/* 491 */         normForward.normalize();
/*     */         
/*     */ 
/*     */         do
/*     */         {
/* 496 */           BlockPos teleportPos = new BlockPos(Math.round(this.posX + normForward.x * 40.0F), yOffset, Math.round(this.posZ + normForward.z * 40.0F));
/* 497 */           IBlockState bs = this.world.getBlockState(teleportPos);
/*     */           
/* 499 */           if (bs.getBlock().isAir(bs, this.world, teleportPos))
/*     */           {
/* 501 */             yOffset -= 1.0D;
/*     */           }
/*     */           else
/*     */           {
/* 505 */             isBlock = true;
/*     */           }
/*     */           
/* 508 */         } while ((!isBlock) && (yOffset > 1.0D));
/*     */         
/* 510 */         playerMP.connection.setPlayerLocation((float)Math.round(this.posX + normForward.x * 40.0F) + 0.5F, yOffset + 1.0D, (float)Math.round(this.posZ + normForward.z * 40.0F) + 0.5F, playerMP.rotationYaw, playerMP.rotationPitch);
/*     */         
/* 512 */         if (!playerMP.capabilities.isCreativeMode)
/*     */         {
/* 514 */           playerMP.capabilities.allowFlying = true;
/*     */         }
/*     */       }
/*     */       
/* 518 */       MessageHandler.handler.sendToDimension(new MessageGlobalEntity(getEntityId(), (float)this.posX, (float)this.posY, (float)this.posZ, this.netQuat, this.netPower, this.netDamaged), this.world.provider
/*     */       
/* 520 */         .getDimension());
/*     */       
/* 522 */       sendAdditionalPacket();
/*     */       
/* 524 */       this.motionX = (this.netX - this.posX);
/* 525 */       this.motionY = (this.netY - this.posY);
/* 526 */       this.motionZ = (this.netZ - this.posZ);
/*     */       
/* 528 */       this.posX = this.netX;
/* 529 */       this.posY = this.netY;
/* 530 */       this.posZ = this.netZ;
/*     */       
/* 532 */       updateAdditionalInfo();
/* 533 */       setPosition(this.posX, this.posY, this.posZ);
/* 534 */       alignRotation(this.netQuat);
/*     */       
/* 536 */       explosionCheck();
/*     */       
/* 538 */       BlockPos bp = new BlockPos(this);
/* 539 */       Block bk = this.world.getBlockState(bp).getBlock();
/*     */       
/* 541 */       if ((bk.isPassable(this.world, bp)) && (bk != Blocks.SNOW_LAYER) && (bk != Blocks.AIR) && (bk != Blocks.WATER) && (bk != Blocks.WOODEN_PRESSURE_PLATE) && (bk != Blocks.STONE_PRESSURE_PLATE) && (bk != Blocks.RAIL) && (bk != Blocks.ACTIVATOR_RAIL) && (bk != Blocks.DETECTOR_RAIL) && (bk != Blocks.GOLDEN_RAIL) && (bk != Blocks.FLOWING_WATER))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 546 */         this.world.destroyBlock(bp, false);
/*     */       }
/*     */       
/* 549 */       if (getDistance(this.thePlayer) > 150.0F)
/*     */       {
/* 551 */         this.activated = false;
/*     */       }
/*     */       
/* 554 */       if ((this.inWater) && (!(this instanceof EntityBoat)) && (!(this instanceof EntitySubmarine)) && (!(this instanceof EntityMissile)))
/*     */       {
/* 556 */         this.activated = false;
/*     */       }
/*     */       
/* 559 */       this.power = this.netPower;
/* 560 */       this.damaged = this.netDamaged;
/*     */       
/* 562 */       this.packetReceived = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void spawnItems() {}
/*     */   
/*     */   public void explosionCheck()
/*     */   {
/* 571 */     if (((this instanceof EntityMissile)) && (!this.isDead))
/*     */     {
/*     */ 
/* 574 */       BlockPos blockPos = new BlockPos(this.posX, this.posY, this.posZ);
/* 575 */       IBlockState iblockstate = this.world.getBlockState(blockPos);
/* 576 */       Block block = iblockstate.getBlock();
/*     */       
/* 578 */       if (this.requestExplosion)
/*     */       {
/* 580 */         setDead();
/*     */         
/* 582 */         this.world.createExplosion(null, this.explodeX, this.explodeY, this.explodeZ, 1.5F, true);
/* 583 */         this.requestExplosion = false;
/*     */       }
/* 585 */       else if (!block.isPassable(this.world, blockPos))
/*     */       {
/*     */ 
/* 588 */         double velocity = Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/*     */         
/* 590 */         setDead();
/*     */         
/* 592 */         if (velocity > 0.5D)
/*     */         {
/* 594 */           this.world.createExplosion(null, this.posX, this.posY, this.posZ, 1.5F, true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 603 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundAir(this));
/*     */   }
/*     */   
/*     */   public void spawnParticles()
/*     */   {
/* 608 */     if ((this.damaged) && (!this.inWater))
/*     */     {
/* 610 */       for (int i1 = 0; i1 < 2.0D; i1++)
/*     */       {
/* 612 */         this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.1D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean holdingremotecontrol(EntityPlayer entityplayer)
/*     */   {
/* 619 */     if (entityplayer != null)
/*     */     {
/* 621 */       ItemStack itemstack = entityplayer.inventory.getCurrentItem();
/*     */       
/* 623 */       if (itemstack == null)
/*     */       {
/* 625 */         return false;
/*     */       }
/* 627 */       if ((itemstack != null) && (itemstack.getItem() == RCM_Main.item_remotecontrol))
/*     */       {
/* 629 */         ItemRemoteControl itemRC = (ItemRemoteControl)itemstack.getItem();
/*     */         
/* 631 */         if (itemRC.state)
/*     */         {
/* 633 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 638 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics() {}
/*     */   
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
/*     */   {
/* 646 */     if (this.coolDownCounter == 0)
/*     */     {
/* 648 */       this.coolDownCounter = 15;
/*     */       
/* 650 */       ItemStack itemstack = player.inventory.getCurrentItem();
/*     */       
/* 652 */       if (itemstack == null)
/*     */       {
/* 654 */         if (!this.activated)
/*     */         {
/* 656 */           this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + this.Up.x * 0.3D, this.posY + this.Up.y * 0.3D, this.posZ + this.Up.z * 0.3D, 1.0D, 0.0D, 0.0D, new int[0]);
/* 657 */           playSound(ModSoundEvents.escsync, 0.2F, 1.0F);
/*     */           
/* 659 */           if (this.world.isRemote)
/*     */           {
/* 661 */             TickHandler.rcEntity = this;
/* 662 */             this.tickTimer = this.endTime;
/* 663 */             showPlayerText(getEntityName() + ": ", "Power On", 2);
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 668 */             this.activated = true;
/*     */           }
/*     */           
/*     */         }
/* 672 */         else if (this.activated)
/*     */         {
/* 674 */           this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + this.Up.x * 0.3D, this.posY + this.Up.y * 0.3D, this.posZ + this.Up.z * 0.3D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           
/* 676 */           if (this.world.isRemote)
/*     */           {
/* 678 */             showPlayerText(getEntityName() + ": ", "Power Off", 2);
/*     */           }
/*     */           else
/*     */           {
/* 682 */             this.activated = false;
/*     */           }
/*     */         }
/*     */       }
/* 686 */       else if ((!this.activated) && (itemstack.getItem() != RCM_Main.item_remotecontrol))
/*     */       {
/* 688 */         this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + this.Up.x * 0.3D, this.posY + this.Up.y * 0.3D, this.posZ + this.Up.z * 0.3D, 1.0D, 0.0D, 0.0D, new int[0]);
/* 689 */         playSound(ModSoundEvents.escsync, 0.2F, 1.0F);
/*     */         
/* 691 */         if (this.world.isRemote)
/*     */         {
/* 693 */           TickHandler.rcEntity = this;
/* 694 */           this.tickTimer = this.endTime;
/* 695 */           showPlayerText(getEntityName() + ": ", "Power On", 2);
/*     */         }
/*     */         else
/*     */         {
/* 699 */           this.activated = true;
/*     */         }
/*     */       }
/* 702 */       else if ((this.activated) && (itemstack.getItem() != RCM_Main.item_remotecontrol))
/*     */       {
/* 704 */         this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + this.Up.x * 0.3D, this.posY + this.Up.y * 0.3D, this.posZ + this.Up.z * 0.3D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         
/* 706 */         if (this.world.isRemote)
/*     */         {
/* 708 */           showPlayerText(getEntityName() + ": ", "Power Off", 2);
/*     */         }
/*     */         else
/*     */         {
/* 712 */           this.activated = false;
/*     */         }
/*     */       }
/*     */     }
/* 716 */     return true;
/*     */   }
/*     */   
/*     */   public void sendAdditionalPacket() {}
/*     */   
/*     */   private float getYaw(Vector3f forwardVect)
/*     */   {
/* 723 */     Vector3f forwardVect1 = new Vector3f(forwardVect.x, 0.0F, forwardVect.z);
/* 724 */     Vector3f referanceVect = new Vector3f(1.0F, 0.0F, 0.0F);
/* 725 */     float yaw = referanceVect.angle(forwardVect1) + 1.5707964F;
/*     */     
/* 727 */     referanceVect.cross(referanceVect, forwardVect1);
/*     */     
/* 729 */     if (referanceVect.y < 0.0F)
/*     */     {
/* 731 */       yaw = 3.1415927F - yaw;
/*     */     }
/*     */     
/* 734 */     return -yaw * 180.0F / 3.1415927F;
/*     */   }
/*     */   
/*     */   private float getPitch(Vector3f forwardVect)
/*     */   {
/* 739 */     Vector3f referanceVect = new Vector3f(0.0F, 1.0F, 0.0F);
/* 740 */     float pitch = 1.5707964F - referanceVect.angle(forwardVect);
/*     */     
/* 742 */     return pitch * 180.0F / 3.1415927F;
/*     */   }
/*     */   
/*     */   private float getRoll(Vector3f leftVect, Vector3f forwardVect)
/*     */   {
/* 747 */     Vector3f upVect = new Vector3f(0.0F, 1.0F, 0.0F);
/* 748 */     Vector3f referanceVect = new Vector3f();
/* 749 */     referanceVect.cross(upVect, forwardVect);
/* 750 */     float roll = -referanceVect.angle(leftVect);
/*     */     
/* 752 */     if (this.Left.y > 0.0F)
/*     */     {
/* 754 */       roll = -roll;
/*     */     }
/*     */     
/* 757 */     return roll * 180.0F / 3.1415927F;
/*     */   }
/*     */   
/*     */   public float getForwardVelocity(Vector3f forwardVect, Vector3f referanceVect)
/*     */   {
/* 762 */     float forwardvelocity = forwardVect.dot(referanceVect);
/* 763 */     return forwardvelocity;
/*     */   }
/*     */   
/*     */   public float getVelocity()
/*     */   {
/* 768 */     if (this.physicsWorld == null)
/*     */     {
/* 770 */       return 0.0F;
/*     */     }
/*     */     
/* 773 */     return this.physicsWorld.getLinearVel().length();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDead()
/*     */   {
/* 779 */     super.setDead();
/*     */     
/* 781 */     if ((this.world.isRemote) && (TickHandler.rcEntity == this))
/*     */     {
/* 783 */       TickHandler.rcEntity = null;
/*     */     }
/*     */     
/* 786 */     RCM_Main.tickHandler.deRegister(this);
/*     */     
/* 788 */     if (this.physicsWorld != null)
/*     */     {
/* 790 */       this.physicsWorld.removePhysicsEntity();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void registerSpawn(int par)
/*     */   {
/* 797 */     for (int i = 0; i < this.spawnRegister.length; i++)
/*     */     {
/* 799 */       if (this.spawnRegister[i] == -1)
/*     */       {
/* 801 */         this.spawnRegister[i] = par;
/* 802 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerExplosion(double parX, double parY, double parZ)
/*     */   {
/* 809 */     this.requestExplosion = true;
/* 810 */     this.explodeX = parX;
/* 811 */     this.explodeY = parY;
/* 812 */     this.explodeZ = parZ;
/*     */   }
/*     */   
/*     */   public void dropCraftItem(Item item, double d)
/*     */   {
/* 817 */     Double rand = Double.valueOf(Math.random());
/*     */     
/* 819 */     if (rand.doubleValue() <= d)
/*     */     {
/* 821 */       dropItem(item, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void showPlayerText(String headingText, String body, int color)
/*     */   {
/* 827 */     TextComponentTranslation HeadingText = new TextComponentTranslation(headingText, new Object[0]);
/*     */     
/* 829 */     switch (color)
/*     */     {
/*     */     case 1: 
/* 832 */       HeadingText.setStyle(new Style().setColor(TextFormatting.YELLOW));
/* 833 */       break;
/*     */     case 2: 
/* 835 */       HeadingText.setStyle(new Style().setColor(TextFormatting.GREEN));
/*     */     }
/*     */     
/*     */     
/* 839 */     TextComponentTranslation BodyText = new TextComponentTranslation(body, new Object[0]);
/* 840 */     TextComponentTranslation textMsg = new TextComponentTranslation("", new Object[0]);
/* 841 */     textMsg.appendSibling(HeadingText).appendSibling(BodyText);
/*     */     
/* 843 */     this.thePlayer.sendMessage(textMsg);
/*     */   }
/*     */   
/*     */   public String getEntityName()
/*     */   {
/* 848 */     return "Unknown";
/*     */   }
/*     */   
/*     */   public void spawnWeapon(int par) {}
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/GlobalEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */