/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.Audio.MovingSoundDroneHigh;
/*     */ import RCM.CommonProxy;
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageEntityDrone;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsHandler;
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
/*     */ 
/*     */ public class EntityDrone
/*     */   extends GlobalEntity
/*     */ {
/*  36 */   public float[] state = new float[8];
/*  37 */   public float[] prevState = new float[8];
/*  38 */   private float[] netState = new float[8];
/*     */   private float aileronAngle;
/*     */   private float elevatorAngle;
/*     */   private float rudderAngle;
/*     */   
/*     */   public EntityDrone(World worldIn)
/*     */   {
/*  45 */     super(worldIn);
/*  46 */     this.preventEntitySpawning = true;
/*  47 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityDrone(World worldIn, double par2, double par4, double par6)
/*     */   {
/*  52 */     this(worldIn);
/*  53 */     setPosition(par2, par4, par6);
/*  54 */     this.prevPosX = par2;
/*  55 */     this.prevPosY = par4;
/*  56 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getID()
/*     */   {
/*  63 */     return 2;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  69 */     double d = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  70 */     d *= 64.0D;
/*  71 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  78 */     Entity entity = damageSource.getImmediateSource();
/*     */     
/*  80 */     if ((!this.world.isRemote) && (!this.isDead) && ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || ((this.thePlayer != null) && (entity != null) && (entity.getUniqueID() != this.thePlayer.getUniqueID())) || (entity == null)))
/*     */     {
/*  82 */       spawnItems();
/*     */       
/*  84 */       setDead();
/*  85 */       this.activated = false;
/*  86 */       return true;
/*     */     }
/*     */     
/*  89 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  95 */     super.entityInit();
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics()
/*     */   {
/* 101 */     this.prevState = ((float[])this.state.clone());
/*     */     
/* 103 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)))
/*     */     {
/*     */ 
/* 106 */       this.rudderAngle = KeyHandler.yawMovement;
/*     */       
/*     */ 
/* 109 */       this.elevatorAngle = KeyHandler.pitchMovement;
/*     */       
/*     */ 
/* 112 */       this.aileronAngle = KeyHandler.rollMovement;
/*     */     }
/*     */     
/* 115 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)) && (!this.damaged))
/*     */     {
/* 117 */       this.power = (KeyHandler.absPowerMovement * 100.0F);
/*     */     }
/*     */     else
/*     */     {
/* 121 */       this.power = 0.0F;
/*     */     }
/*     */     
/* 124 */     this.state[0] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getRotationAngle();
/* 125 */     this.state[1] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(4)).getRotationAngle();
/* 126 */     this.state[2] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(8)).getRotationAngle();
/* 127 */     this.state[3] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(12)).getRotationAngle();
/* 128 */     this.state[4] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getAngularVelocity();
/* 129 */     this.state[5] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(4)).getAngularVelocity();
/* 130 */     this.state[6] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(8)).getAngularVelocity();
/* 131 */     this.state[7] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(12)).getAngularVelocity();
/*     */     
/* 133 */     this.physicsWorld.setControlChannel(11, this.power / 100.0F * 0.75F + 0.1F * this.rudderAngle);
/* 134 */     this.physicsWorld.setControlChannel(12, this.power / 100.0F * 0.75F - 0.1F * this.rudderAngle);
/* 135 */     this.physicsWorld.setControlChannel(13, this.power / 100.0F * 0.75F - 0.1F * this.rudderAngle);
/* 136 */     this.physicsWorld.setControlChannel(14, this.power / 100.0F * 0.75F + 0.1F * this.rudderAngle);
/*     */     
/* 138 */     sendAdditionalPacket();
/*     */   }
/*     */   
/*     */   public void spawnItems()
/*     */   {
/* 143 */     if (!this.damaged)
/*     */     {
/* 145 */       dropItem(RCM_Main.item_drone, 1);
/*     */     }
/*     */     else
/*     */     {
/* 149 */       dropCraftItem(RCM_Main.item_battery, 0.8D);
/* 150 */       dropCraftItem(RCM_Main.item_receivermodule, 0.8D);
/* 151 */       dropCraftItem(RCM_Main.item_flight_controller, 0.8D);
/* 152 */       dropCraftItem(RCM_Main.item_speed_controller, 0.8D);
/* 153 */       dropCraftItem(RCM_Main.item_electricmotor, 0.8D);
/* 154 */       dropCraftItem(RCM_Main.item_drone_body, 0.8D);
/* 155 */       dropCraftItem(RCM_Main.item_servo, 0.8D);
/* 156 */       dropCraftItem(RCM_Main.item_propeller_low, 0.8D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 164 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundDroneHigh(this, 0));
/* 165 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundDroneHigh(this, 1));
/* 166 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundDroneHigh(this, 2));
/* 167 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundDroneHigh(this, 3));
/*     */   }
/*     */   
/*     */ 
/*     */   public void sendAdditionalPacket()
/*     */   {
/* 173 */     if (!this.world.isRemote)
/*     */     {
/* 175 */       MessageHandler.handler.sendToDimension(new MessageEntityDrone(getEntityId(), this.state), this.world.provider.getDimension());
/*     */     }
/* 177 */     else if (this.world.isRemote)
/*     */     {
/* 179 */       MessageHandler.handler.sendToServer(new MessageEntityDrone(getEntityId(), this.state));
/*     */     }
/*     */   }
/*     */   
/*     */   public void additionalInfoUpdate(float[] newState)
/*     */   {
/* 185 */     this.netState = newState;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAdditionalInfo()
/*     */   {
/* 191 */     this.prevState = ((float[])this.state.clone());
/* 192 */     this.state = ((float[])this.netState.clone());
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticles()
/*     */   {
/* 199 */     super.spawnParticles();
/*     */     
/* 201 */     if (this.power > 50.0F)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 207 */       int a = -1;
/*     */       BlockPos blockPos;
/*     */       IBlockState iblockstate;
/*     */       Block block;
/* 211 */       do { a++;
/*     */         
/* 213 */         blockPos = new BlockPos(this.posX, this.posY - 0.5D - a, this.posZ);
/* 214 */         iblockstate = this.world.getBlockState(blockPos);
/* 215 */         block = iblockstate.getBlock();
/*     */       }
/* 217 */       while ((block == Blocks.AIR) && (a != 3));
/*     */       
/* 219 */       if (block == Blocks.SNOW_LAYER)
/*     */       {
/* 221 */         blockPos.add(0, -1, 0);
/*     */       }
/*     */       
/* 224 */       for (int i1 = 0; i1 < 2.0D; i1++)
/*     */       {
/* 226 */         double d16 = this.rand.nextFloat() * 3.0D - 1.5D;
/* 227 */         double d17 = this.rand.nextFloat() * 3.0D - 1.5D;
/*     */         
/* 229 */         if (this.rand.nextBoolean())
/*     */         {
/* 231 */           double d21 = this.posX + d16;
/* 232 */           double d23 = this.posZ + d17;
/*     */           
/* 234 */           if (block != Blocks.AIR)
/*     */           {
/*     */ 
/* 237 */             if (iblockstate.getMaterial() != Material.AIR)
/*     */             {
/* 239 */               this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d21, blockPos.getY() + 1.125D, d23, d16, 0.0D, d17, new int[] { Block.getStateId(iblockstate) });
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 250 */     return "RC Drone";
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntityDrone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */