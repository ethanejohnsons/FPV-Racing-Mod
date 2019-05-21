/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.Audio.MovingSoundHeliBladeFlap;
/*     */ import RCM.Audio.MovingSoundHeliHigh;
/*     */ import RCM.CommonProxy;
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageEntityHeli;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.PhysicsHelper;
/*     */ import RCM.Physics.RotaryWingHandler;
/*     */ import RCM.RCM_Main;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityHeli
/*     */   extends GlobalEntity
/*     */ {
/*     */   private float cyclicAngle;
/*  38 */   public float[] state = new float[7];
/*  39 */   public float[] prevState = new float[7];
/*  40 */   private float[] netState = new float[7];
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean stopMotor;
/*     */   
/*     */ 
/*     */ 
/*     */   private int stopMotorTimer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EntityHeli(World world)
/*     */   {
/*  55 */     super(world);
/*  56 */     this.preventEntitySpawning = true;
/*  57 */     setSize(0.5F, 0.5F);
/*  58 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntityHeli(World world, double par2, double par4, double par6)
/*     */   {
/*  63 */     this(world);
/*  64 */     setPosition(par2, par4, par6);
/*  65 */     this.prevPosX = par2;
/*  66 */     this.prevPosY = par4;
/*  67 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getID()
/*     */   {
/*  73 */     return 5;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  79 */     double d = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  80 */     d *= 64.0D;
/*  81 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  88 */     Entity entity = damageSource.getImmediateSource();
/*     */     
/*  90 */     if ((!this.world.isRemote) && (!this.isDead) && ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || ((this.thePlayer != null) && (entity != null) && (entity.getUniqueID() != this.thePlayer.getUniqueID())) || (entity == null)))
/*     */     {
/*  92 */       spawnItems();
/*     */       
/*  94 */       setDead();
/*  95 */       this.activated = false;
/*  96 */       return true;
/*     */     }
/*     */     
/*  99 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/* 105 */     super.entityInit();
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics()
/*     */   {
/* 111 */     this.prevState = ((float[])this.state.clone());
/*     */     
/* 113 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)))
/*     */     {
/*     */ 
/* 116 */       this.cyclicAngle = (-KeyHandler.powerMovement);
/*     */     }
/*     */     
/* 119 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)) && (!this.damaged))
/*     */     {
/* 121 */       this.power += 1.0F;
/*     */       
/* 123 */       if (this.power > 80.0F)
/*     */       {
/* 125 */         this.power = 80.0F;
/*     */       }
/* 127 */       else if (this.power < 0.0F)
/*     */       {
/* 129 */         this.power = 0.0F;
/*     */       }
/*     */     }
/*     */     
/* 133 */     if (this.power == 80.0F)
/*     */     {
/* 135 */       this.power += Math.abs(this.cyclicAngle) * 20.0F;
/*     */     }
/*     */     
/* 138 */     if ((KeyHandler.motorKill) && (this.stopMotorTimer == 0))
/*     */     {
/* 140 */       this.stopMotor = (!this.stopMotor);
/* 141 */       this.stopMotorTimer = 20;
/*     */       
/* 143 */       if (this.stopMotor)
/*     */       {
/* 145 */         showPlayerText(getEntityName() + ": ", "Motor Off", 2);
/*     */       }
/*     */       else
/*     */       {
/* 149 */         showPlayerText(getEntityName() + ": ", "Motor On", 2);
/*     */       }
/*     */     }
/*     */     
/* 153 */     if (this.stopMotorTimer > 0)
/*     */     {
/* 155 */       this.stopMotorTimer -= 1;
/*     */     }
/*     */     
/* 158 */     if ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || (this.damaged) || (this.stopMotor))
/*     */     {
/* 160 */       this.power = 0.0F;
/*     */     }
/*     */     
/* 163 */     this.state[0] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getAngularVelocity();
/* 164 */     this.state[1] = 
/*     */     
/*     */ 
/* 167 */       ((((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getTipAoA() + ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(1)).getTipAoA() + ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(4)).getTipAoA() + ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(5)).getTipAoA()) / 4.0F);
/* 168 */     this.state[2] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getRotationAngle();
/* 169 */     this.state[3] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(2)).getRotationAngle();
/* 170 */     this.state[4] = (this.cyclicAngle * 10.0F);
/* 171 */     this.state[5] = (((RotaryWingHandler)this.physicsWorld.rotaryWings.get(2)).getPropAngle() * this.helper.radToDeg);
/* 172 */     this.state[6] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(2)).getAngularVelocity();
/*     */     
/* 174 */     this.physicsWorld.setControlChannel(11, this.power / 100.0F);
/* 175 */     this.physicsWorld.setControlChannel(19, this.cyclicAngle * 0.5F - 0.04F);
/* 176 */     this.physicsWorld.setControlChannel(20, this.cyclicAngle * 0.5F - 0.04F);
/* 177 */     this.physicsWorld.setControlChannel(21, this.cyclicAngle * 0.5F - 0.04F);
/* 178 */     this.physicsWorld.setControlChannel(22, this.cyclicAngle * 0.5F - 0.04F);
/*     */     
/* 180 */     sendAdditionalPacket();
/*     */   }
/*     */   
/*     */ 
/*     */   public void spawnItems()
/*     */   {
/* 186 */     if (!this.damaged)
/*     */     {
/* 188 */       dropItem(RCM_Main.item_heli, 1);
/*     */     }
/*     */     else
/*     */     {
/* 192 */       dropCraftItem(RCM_Main.item_battery, 0.8D);
/* 193 */       dropCraftItem(RCM_Main.item_receivermodule, 0.8D);
/* 194 */       dropCraftItem(RCM_Main.item_flight_controller, 0.8D);
/* 195 */       dropCraftItem(RCM_Main.item_speed_controller, 0.8D);
/* 196 */       dropCraftItem(RCM_Main.item_electricmotor, 0.8D);
/* 197 */       dropCraftItem(RCM_Main.item_heli_body, 0.8D);
/* 198 */       dropCraftItem(RCM_Main.item_servo, 0.8D);
/* 199 */       dropCraftItem(RCM_Main.item_rotorblades, 0.8D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 207 */     super.registerSounds();
/*     */     
/* 209 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundHeliHigh(this));
/* 210 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundHeliBladeFlap(this));
/*     */   }
/*     */   
/*     */ 
/*     */   public void sendAdditionalPacket()
/*     */   {
/* 216 */     if (!this.world.isRemote)
/*     */     {
/* 218 */       MessageHandler.handler.sendToDimension(new MessageEntityHeli(getEntityId(), this.state), this.world.provider.getDimension());
/*     */     }
/* 220 */     else if (this.world.isRemote)
/*     */     {
/* 222 */       MessageHandler.handler.sendToServer(new MessageEntityHeli(getEntityId(), this.state));
/*     */     }
/*     */   }
/*     */   
/*     */   public void additionalInfoUpdate(float[] newState)
/*     */   {
/* 228 */     this.netState = newState;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAdditionalInfo()
/*     */   {
/* 234 */     this.prevState = ((float[])this.state.clone());
/* 235 */     this.state = ((float[])this.netState.clone());
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticles()
/*     */   {
/* 242 */     super.spawnParticles();
/*     */     
/* 244 */     if (this.power > 50.0F)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 250 */       int a = -1;
/*     */       BlockPos blockPos;
/*     */       IBlockState iblockstate;
/*     */       Block block;
/* 254 */       do { a++;
/*     */         
/* 256 */         blockPos = new BlockPos(this.posX, this.posY - 0.5D - a, this.posZ);
/* 257 */         iblockstate = this.world.getBlockState(blockPos);
/* 258 */         block = iblockstate.getBlock();
/*     */       }
/* 260 */       while ((block == Blocks.AIR) && (a != 3));
/*     */       
/* 262 */       if (block == Blocks.SNOW_LAYER)
/*     */       {
/* 264 */         blockPos.add(0, -1, 0);
/*     */       }
/*     */       
/* 267 */       for (int i1 = 0; i1 < 2.0D; i1++)
/*     */       {
/* 269 */         double d16 = this.rand.nextFloat() * 3.0D - 1.5D;
/* 270 */         double d17 = this.rand.nextFloat() * 3.0D - 1.5D;
/*     */         
/* 272 */         if (this.rand.nextBoolean())
/*     */         {
/* 274 */           double d21 = this.posX + d16;
/* 275 */           double d23 = this.posZ + d17;
/*     */           
/* 277 */           if (block != Blocks.AIR)
/*     */           {
/*     */ 
/* 280 */             if (iblockstate.getMaterial() != Material.AIR)
/*     */             {
/* 282 */               this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d21, blockPos.getY() + 1.125D, d23, d16, 0.0D, d17, new int[] { Block.getStateId(iblockstate) });
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
/* 294 */     return "RC Heli";
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntityHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */