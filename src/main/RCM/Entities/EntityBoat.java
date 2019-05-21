/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.Audio.MovingSoundBoatHigh;
/*     */ import RCM.Audio.MovingSoundWater;
/*     */ import RCM.CommonProxy;
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageEntityBoat;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.MotorHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.WingHandler;
/*     */ import RCM.RCM_Main;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ 
/*     */ public class EntityBoat
/*     */   extends GlobalEntity
/*     */ {
/*  39 */   public float[] state = new float[2];
/*  40 */   public float[] prevState = new float[2];
/*  41 */   private float[] netState = new float[2];
/*     */   
/*     */   private float inputAngle;
/*     */   
/*     */   public EntityBoat(World world)
/*     */   {
/*  47 */     super(world);
/*  48 */     this.preventEntitySpawning = true;
/*  49 */     setSize(0.5F, 0.5F);
/*  50 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntityBoat(World world, double par2, double par4, double par6)
/*     */   {
/*  55 */     this(world);
/*  56 */     setPosition(par2, par4, par6);
/*  57 */     this.prevPosX = par2;
/*  58 */     this.prevPosY = par4;
/*  59 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getID()
/*     */   {
/*  65 */     return 7;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  71 */     double d = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  72 */     d *= 64.0D;
/*  73 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  80 */     Entity entity = damageSource.getImmediateSource();
/*     */     
/*  82 */     if ((!this.world.isRemote) && (!this.isDead) && ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || ((this.thePlayer != null) && (entity != null) && (entity.getUniqueID() != this.thePlayer.getUniqueID())) || (entity == null)))
/*     */     {
/*  84 */       spawnItems();
/*     */       
/*  86 */       setDead();
/*  87 */       this.activated = false;
/*  88 */       return true;
/*     */     }
/*     */     
/*  91 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  97 */     super.entityInit();
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics()
/*     */   {
/* 103 */     this.prevState = ((float[])this.state.clone());
/*     */     
/* 105 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)))
/*     */     {
/* 107 */       this.inputAngle = KeyHandler.turnMovement;
/*     */     }
/*     */     
/* 110 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)) && (!this.damaged))
/*     */     {
/* 112 */       if (KeyHandler.powerMovement >= 0.0F)
/*     */       {
/* 114 */         this.power = (KeyHandler.powerMovement * 100.0F);
/*     */       }
/*     */       else
/*     */       {
/* 118 */         this.power = (KeyHandler.powerMovement * 25.0F);
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 123 */       this.power = 0.0F;
/*     */     }
/*     */     
/* 126 */     this.state[0] = ((MotorHandler)this.physicsWorld.motors.get(0)).getRotationalVel();
/* 127 */     this.state[1] = ((WingHandler)this.physicsWorld.wings.get(3)).getPropAngle();
/*     */     
/* 129 */     this.physicsWorld.setControlChannel(11, this.power / 100.0F);
/* 130 */     this.physicsWorld.setControlChannel(12, this.power / 100.0F);
/* 131 */     this.physicsWorld.setControlChannel(5, -this.inputAngle);
/*     */     
/* 133 */     sendAdditionalPacket();
/*     */   }
/*     */   
/*     */ 
/*     */   public void sendAdditionalPacket()
/*     */   {
/* 139 */     if (!this.world.isRemote)
/*     */     {
/* 141 */       MessageHandler.handler.sendToDimension(new MessageEntityBoat(getEntityId(), this.state), this.world.provider.getDimension());
/*     */     }
/* 143 */     else if (this.world.isRemote)
/*     */     {
/* 145 */       MessageHandler.handler.sendToServer(new MessageEntityBoat(getEntityId(), this.state));
/*     */     }
/*     */   }
/*     */   
/*     */   public void additionalInfoUpdate(float[] newState)
/*     */   {
/* 151 */     this.netState = newState;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAdditionalInfo()
/*     */   {
/* 157 */     this.prevState = ((float[])this.state.clone());
/* 158 */     this.state = ((float[])this.netState.clone());
/*     */   }
/*     */   
/*     */ 
/*     */   public void spawnItems()
/*     */   {
/* 164 */     if (!this.damaged)
/*     */     {
/* 166 */       dropItem(RCM_Main.item_boat, 1);
/*     */     }
/*     */     else
/*     */     {
/* 170 */       dropCraftItem(RCM_Main.item_battery, 0.8D);
/* 171 */       dropCraftItem(RCM_Main.item_receivermodule, 0.8D);
/* 172 */       dropCraftItem(RCM_Main.item_speed_controller, 0.8D);
/* 173 */       dropCraftItem(RCM_Main.item_electricmotor, 0.8D);
/* 174 */       dropCraftItem(RCM_Main.item_boat_body, 0.8D);
/* 175 */       dropCraftItem(RCM_Main.item_servo, 0.8D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 183 */     super.registerSounds();
/*     */     
/* 185 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundBoatHigh(this));
/* 186 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundWater(this));
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticles()
/*     */   {
/* 193 */     super.spawnParticles();
/*     */     
/* 195 */     if (this.power > 25.0F)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */       int a = 0;
/*     */       
/* 203 */       BlockPos blockPos = new BlockPos(this.posX, this.posY - 0.5D - a, this.posZ);
/* 204 */       IBlockState iblockstate = this.world.getBlockState(blockPos);
/* 205 */       Block block = iblockstate.getBlock();
/*     */       
/* 207 */       for (int i1 = 0; i1 < 20; i1++)
/*     */       {
/* 209 */         double d1 = -0.6D;
/* 210 */         double d2 = this.rand.nextFloat() * -0.5D + 0.25D;
/*     */         
/* 212 */         if (this.rand.nextBoolean())
/*     */         {
/* 214 */           double d3 = this.posX + d1 * this.Forward.x + d2 * this.Left.x * 0.5D;
/* 215 */           double d4 = this.posZ + d1 * this.Forward.z + d2 * this.Left.z * 0.5D;
/*     */           
/* 217 */           double d5 = d1 * this.Forward.x + d2 * this.Left.x;
/* 218 */           double d6 = d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/* 220 */           if (iblockstate.getMaterial() == Material.WATER)
/*     */           {
/* 222 */             this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, d3, blockPos.getY() + 1, d4, d5, 20.0F * this.power, d6, new int[] { Block.getStateId(iblockstate) });
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 232 */     return "RC Boat";
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntityBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */