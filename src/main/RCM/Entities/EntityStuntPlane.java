/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageEntityStuntPlane;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.RCM_Main;
/*     */ import java.util.Random;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class EntityStuntPlane extends GlobalEntity
/*     */ {
/*     */   public float rudderAngle;
/*     */   public float elevatorAngle;
/*     */   public float aileronAngle;
/*     */   public float prevRudderAngle;
/*     */   public float prevElevatorAngle;
/*     */   public float prevAileronAngle;
/*     */   public float nvelocity;
/*     */   private float maxRudderAngle;
/*     */   private float maxElevatorAngle;
/*     */   private float maxAileronAngle;
/*     */   public float motorSpeed;
/*     */   public float netMotorSpeed;
/*     */   private float netRudderAngle;
/*     */   private float netElevatorAngle;
/*     */   private float netAileronAngle;
/*     */   private float netVelocity;
/*     */   
/*     */   public EntityStuntPlane(World world)
/*     */   {
/*  42 */     super(world);
/*  43 */     this.preventEntitySpawning = true;
/*  44 */     setSize(0.5F, 0.5F);
/*  45 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntityStuntPlane(World world, double par2, double par4, double par6)
/*     */   {
/*  50 */     this(world);
/*  51 */     setPosition(par2, par4, par6);
/*  52 */     this.prevPosX = par2;
/*  53 */     this.prevPosY = par4;
/*  54 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getID()
/*     */   {
/*  60 */     return 9;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  66 */     double d = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  67 */     d *= 64.0D;
/*  68 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  75 */     Entity entity = damageSource.getImmediateSource();
/*     */     
/*  77 */     if ((!this.world.isRemote) && (!this.isDead) && ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || ((this.thePlayer != null) && (entity != null) && (entity.getUniqueID() != this.thePlayer.getUniqueID())) || (entity == null)))
/*     */     {
/*  79 */       spawnItems();
/*     */       
/*  81 */       setDead();
/*  82 */       this.activated = false;
/*  83 */       return true;
/*     */     }
/*     */     
/*  86 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  92 */     super.entityInit();
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics()
/*     */   {
/*  98 */     this.prevRudderAngle = this.rudderAngle;
/*  99 */     this.prevElevatorAngle = this.elevatorAngle;
/* 100 */     this.prevAileronAngle = this.aileronAngle;
/*     */     
/* 102 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)))
/*     */     {
/*     */ 
/* 105 */       if (KeyHandler.turnMovement != 0.0F)
/*     */       {
/* 107 */         this.rudderAngle -= 0.2F * KeyHandler.turnMovement;
/*     */         
/* 109 */         if (this.rudderAngle > 1.0F)
/*     */         {
/* 111 */           this.rudderAngle = 1.0F;
/*     */         }
/*     */         
/* 114 */         if (this.rudderAngle < -1.0F)
/*     */         {
/* 116 */           this.rudderAngle = -1.0F;
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 121 */       else if (this.rudderAngle > 0.0F)
/*     */       {
/* 123 */         this.rudderAngle -= 0.1F;
/*     */         
/* 125 */         if (this.rudderAngle < 0.1F)
/*     */         {
/* 127 */           this.rudderAngle = 0.0F;
/*     */         }
/*     */       }
/* 130 */       else if (this.rudderAngle < 0.0F)
/*     */       {
/* 132 */         this.rudderAngle += 0.1F;
/*     */         
/* 134 */         if (this.rudderAngle > -0.1F)
/*     */         {
/* 136 */           this.rudderAngle = 0.0F;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 142 */       if (KeyHandler.pitchMovement != 0.0F)
/*     */       {
/* 144 */         this.elevatorAngle -= 0.2F * KeyHandler.pitchMovement;
/*     */         
/* 146 */         if (this.elevatorAngle > 1.0F)
/*     */         {
/* 148 */           this.elevatorAngle = 1.0F;
/*     */         }
/*     */         
/* 151 */         if (this.elevatorAngle < -1.0F)
/*     */         {
/* 153 */           this.elevatorAngle = -1.0F;
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 158 */       else if (this.elevatorAngle > 0.0F)
/*     */       {
/* 160 */         this.elevatorAngle = ((float)(this.elevatorAngle - 0.2D));
/*     */         
/* 162 */         if (this.elevatorAngle < 0.2F)
/*     */         {
/* 164 */           this.elevatorAngle = 0.0F;
/*     */         }
/*     */       }
/* 167 */       else if (this.elevatorAngle < 0.0F)
/*     */       {
/* 169 */         this.elevatorAngle = ((float)(this.elevatorAngle + 0.2D));
/*     */         
/* 171 */         if (this.elevatorAngle > -0.2F)
/*     */         {
/* 173 */           this.elevatorAngle = 0.0F;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 179 */       if (KeyHandler.rollMovement != 0.0F)
/*     */       {
/* 181 */         this.aileronAngle += 0.2F * KeyHandler.rollMovement;
/*     */         
/* 183 */         if (this.aileronAngle > 1.0F)
/*     */         {
/* 185 */           this.aileronAngle = 1.0F;
/*     */         }
/*     */         
/* 188 */         if (this.aileronAngle < -1.0F)
/*     */         {
/* 190 */           this.aileronAngle = -1.0F;
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 195 */       else if (this.aileronAngle > 0.0F)
/*     */       {
/* 197 */         this.aileronAngle -= 0.2F;
/*     */         
/* 199 */         if (this.aileronAngle < 0.2F)
/*     */         {
/* 201 */           this.aileronAngle = 0.0F;
/*     */         }
/*     */       }
/* 204 */       else if (this.aileronAngle < 0.0F)
/*     */       {
/* 206 */         this.aileronAngle += 0.2F;
/*     */         
/* 208 */         if (this.aileronAngle > -0.2F)
/*     */         {
/* 210 */           this.aileronAngle = 0.0F;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 216 */     if ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || (this.damaged))
/*     */     {
/* 218 */       this.power = 0.0F;
/*     */     }
/*     */     
/* 221 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)) && (!this.damaged))
/*     */     {
/* 223 */       this.power += KeyHandler.powerMovement;
/*     */       
/* 225 */       if (this.power > 100.0F)
/*     */       {
/* 227 */         this.power = 100.0F;
/*     */       }
/* 229 */       else if (this.power < 0.0F)
/*     */       {
/* 231 */         this.power = 0.0F;
/*     */       }
/*     */     }
/*     */     
/* 235 */     this.motorSpeed = ((RCM.Physics.RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getAngularVelocity();
/*     */     
/* 237 */     this.physicsWorld.setControlChannel(1, -this.aileronAngle);
/* 238 */     this.physicsWorld.setControlChannel(2, -this.aileronAngle);
/* 239 */     this.physicsWorld.setControlChannel(3, -this.elevatorAngle);
/* 240 */     this.physicsWorld.setControlChannel(4, this.elevatorAngle);
/* 241 */     this.physicsWorld.setControlChannel(5, -this.rudderAngle);
/* 242 */     this.physicsWorld.setControlChannel(9, this.rudderAngle);
/* 243 */     this.physicsWorld.setControlChannel(11, this.power / 100.0F);
/*     */     
/* 245 */     sendAdditionalPacket();
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
/*     */   public void spawnItems()
/*     */   {
/* 259 */     if (!this.damaged)
/*     */     {
/* 261 */       dropItem(RCM_Main.item_stuntplane, 1);
/*     */     }
/*     */     else
/*     */     {
/* 265 */       dropCraftItem(RCM_Main.item_battery, 0.8D);
/* 266 */       dropCraftItem(RCM_Main.item_receivermodule, 0.8D);
/* 267 */       dropCraftItem(RCM_Main.item_speed_controller, 0.8D);
/* 268 */       dropCraftItem(RCM_Main.item_electricmotor, 0.8D);
/* 269 */       dropCraftItem(RCM_Main.item_trainerplane_body, 0.8D);
/* 270 */       dropCraftItem(RCM_Main.item_narrowwheel, 0.8D);
/* 271 */       dropCraftItem(RCM_Main.item_servo, 0.8D);
/* 272 */       dropCraftItem(RCM_Main.item_propeller_high, 0.8D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 280 */     super.registerSounds();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sendAdditionalPacket()
/*     */   {
/* 289 */     if (!this.world.isRemote)
/*     */     {
/* 291 */       MessageHandler.handler.sendToDimension(new MessageEntityStuntPlane(getEntityId(), this.motorSpeed, this.rudderAngle, this.elevatorAngle, this.aileronAngle, this.nvelocity), this.world.provider
/* 292 */         .getDimension());
/*     */     }
/* 294 */     else if (this.world.isRemote)
/*     */     {
/* 296 */       MessageHandler.handler.sendToServer(new MessageEntityStuntPlane(getEntityId(), this.motorSpeed, this.rudderAngle, this.elevatorAngle, this.aileronAngle, this.nvelocity));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void additionalInfoUpdate(float netMotorSpeed, float netRudderAngle, float netElevatorAngle, float netAileronAngle, float netVelocity)
/*     */   {
/* 303 */     this.netMotorSpeed = netMotorSpeed;
/* 304 */     this.netRudderAngle = netRudderAngle;
/* 305 */     this.netElevatorAngle = netElevatorAngle;
/* 306 */     this.netAileronAngle = netAileronAngle;
/* 307 */     this.netVelocity = netVelocity;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAdditionalInfo()
/*     */   {
/* 313 */     this.motorSpeed = this.netMotorSpeed;
/* 314 */     this.prevRudderAngle = this.rudderAngle;
/* 315 */     this.prevElevatorAngle = this.elevatorAngle;
/* 316 */     this.prevAileronAngle = this.aileronAngle;
/*     */     
/* 318 */     this.rudderAngle = this.netRudderAngle;
/* 319 */     this.elevatorAngle = this.netElevatorAngle;
/* 320 */     this.aileronAngle = this.netAileronAngle;
/* 321 */     this.nvelocity = this.netVelocity;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticles()
/*     */   {
/* 328 */     super.spawnParticles();
/*     */     
/* 330 */     if (this.power > 25.0F)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 336 */       int a = -1;
/*     */       BlockPos blockPos;
/*     */       IBlockState iblockstate;
/*     */       Block block;
/* 340 */       do { a++;
/*     */         
/* 342 */         blockPos = new BlockPos(this.posX, this.posY - 0.5D - a, this.posZ);
/* 343 */         iblockstate = this.world.getBlockState(blockPos);
/* 344 */         block = iblockstate.getBlock();
/*     */       }
/* 346 */       while ((block == Blocks.AIR) && (a != 1));
/*     */       
/* 348 */       if (block == Blocks.SNOW_LAYER)
/*     */       {
/* 350 */         blockPos.add(0, -1, 0);
/*     */       }
/*     */       
/* 353 */       for (int i1 = 0; i1 < 2.0D; i1++)
/*     */       {
/* 355 */         double d1 = this.rand.nextFloat() * -1.0F - 1.0F;
/* 356 */         double d2 = this.rand.nextFloat() * -0.5D + 0.25D;
/*     */         
/* 358 */         if (this.rand.nextBoolean())
/*     */         {
/* 360 */           double d3 = this.posX + d1 * this.Forward.x + d2 * this.Left.x;
/* 361 */           double d4 = this.posZ + d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/* 363 */           double d5 = d1 * this.Forward.x + d2 * this.Left.x;
/* 364 */           double d6 = d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/* 366 */           if (block != Blocks.AIR)
/*     */           {
/*     */ 
/* 369 */             if (iblockstate.getMaterial() != net.minecraft.block.material.Material.AIR)
/*     */             {
/* 371 */               this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.BLOCK_CRACK, d3, blockPos.getY() + 1.125D, d4, d5 * 10.0D, 0.0D, d6 * 10.0D, new int[] { Block.getStateId(iblockstate) });
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 383 */     return "RC Stunt Plane";
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntityStuntPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */