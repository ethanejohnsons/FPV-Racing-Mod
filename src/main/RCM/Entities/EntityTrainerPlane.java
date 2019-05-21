/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.Audio.MovingSoundTrainerPlaneHigh;
/*     */ import RCM.Audio.MovingSoundTrainerPlaneLow;
/*     */ import RCM.CommonProxy;
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageEntityTrainerPlane;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.PhysicsHelper;
/*     */ import RCM.Physics.RotaryWingHandler;
/*     */ import RCM.RCM_Main;
/*     */ import com.bulletphysics.dynamics.vehicle.RaycastVehicle;
/*     */ import com.bulletphysics.dynamics.vehicle.WheelInfo;
/*     */ import com.bulletphysics.util.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.entity.Entity;
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
/*     */ public class EntityTrainerPlane extends GlobalEntity
/*     */ {
/*  36 */   public float[] state = new float[12];
/*  37 */   public float[] prevState = new float[12];
/*  38 */   public float[] netState = new float[12];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EntityTrainerPlane(World world)
/*     */   {
/*  48 */     super(world);
/*  49 */     this.preventEntitySpawning = true;
/*  50 */     setSize(0.5F, 0.5F);
/*  51 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntityTrainerPlane(World world, double par2, double par4, double par6)
/*     */   {
/*  56 */     this(world);
/*  57 */     setPosition(par2, par4, par6);
/*  58 */     this.prevPosX = par2;
/*  59 */     this.prevPosY = par4;
/*  60 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getID()
/*     */   {
/*  66 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  72 */     double d = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  73 */     d *= 64.0D;
/*  74 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  81 */     Entity entity = damageSource.getImmediateSource();
/*     */     
/*  83 */     if ((!this.world.isRemote) && (!this.isDead) && ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || ((this.thePlayer != null) && (entity != null) && (entity.getUniqueID() != this.thePlayer.getUniqueID())) || (entity == null)))
/*     */     {
/*  85 */       spawnItems();
/*     */       
/*  87 */       setDead();
/*  88 */       this.activated = false;
/*  89 */       return true;
/*     */     }
/*     */     
/*  92 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  98 */     super.entityInit();
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics()
/*     */   {
/* 104 */     this.prevState = ((float[])this.state.clone());
/*     */     
/* 106 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)))
/*     */     {
/*     */ 
/* 109 */       this.state[3] = (-KeyHandler.yawMovement);
/*     */       
/*     */ 
/* 112 */       this.state[4] = (-KeyHandler.pitchMovement);
/*     */       
/*     */ 
/* 115 */       this.state[2] = KeyHandler.rollMovement;
/*     */     }
/*     */     
/* 118 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)) && (!this.damaged))
/*     */     {
/* 120 */       this.power = (KeyHandler.absPowerMovement * 100.0F);
/*     */     }
/*     */     else
/*     */     {
/* 124 */       this.power = 0.0F;
/*     */     }
/*     */     
/* 127 */     this.state[0] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getAngularVelocity();
/* 128 */     this.state[1] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getRotationAngle();
/*     */     
/* 130 */     this.state[5] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).steering * this.helper.radToDeg);
/* 131 */     this.state[6] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).rotation * this.helper.radToDeg);
/* 132 */     this.state[7] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).rotation * this.helper.radToDeg);
/* 133 */     this.state[8] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).rotation * this.helper.radToDeg);
/*     */     
/* 135 */     this.state[9] = 
/* 136 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).suspensionStiffness);
/* 137 */     this.state[10] = 
/* 138 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).suspensionStiffness);
/* 139 */     this.state[11] = 
/* 140 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).suspensionStiffness);
/*     */     
/* 142 */     this.physicsWorld.setControlChannel(1, -this.state[2]);
/* 143 */     this.physicsWorld.setControlChannel(2, -this.state[2]);
/* 144 */     this.physicsWorld.setControlChannel(3, -this.state[4]);
/* 145 */     this.physicsWorld.setControlChannel(4, this.state[4]);
/* 146 */     this.physicsWorld.setControlChannel(5, -this.state[3]);
/* 147 */     this.physicsWorld.setControlChannel(9, this.state[3]);
/* 148 */     this.physicsWorld.setControlChannel(11, this.power / 100.0F);
/*     */     
/* 150 */     sendAdditionalPacket();
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
/* 164 */     if (!this.damaged)
/*     */     {
/* 166 */       dropItem(RCM_Main.item_trainerplane, 1);
/*     */     }
/*     */     else
/*     */     {
/* 170 */       dropCraftItem(RCM_Main.item_battery, 0.8D);
/* 171 */       dropCraftItem(RCM_Main.item_receivermodule, 0.8D);
/* 172 */       dropCraftItem(RCM_Main.item_speed_controller, 0.8D);
/* 173 */       dropCraftItem(RCM_Main.item_electricmotor, 0.8D);
/* 174 */       dropCraftItem(RCM_Main.item_trainerplane_body, 0.8D);
/* 175 */       dropCraftItem(RCM_Main.item_narrowwheel, 0.8D);
/* 176 */       dropCraftItem(RCM_Main.item_servo, 0.8D);
/* 177 */       dropCraftItem(RCM_Main.item_propeller_high, 0.8D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 185 */     super.registerSounds();
/*     */     
/* 187 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundTrainerPlaneHigh(this));
/* 188 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundTrainerPlaneLow(this));
/*     */   }
/*     */   
/*     */ 
/*     */   public void sendAdditionalPacket()
/*     */   {
/* 194 */     if (!this.world.isRemote)
/*     */     {
/* 196 */       MessageHandler.handler.sendToDimension(new MessageEntityTrainerPlane(getEntityId(), this.state), this.world.provider.getDimension());
/*     */     }
/* 198 */     else if (this.world.isRemote)
/*     */     {
/* 200 */       MessageHandler.handler.sendToServer(new MessageEntityTrainerPlane(getEntityId(), this.state));
/*     */     }
/*     */   }
/*     */   
/*     */   public void additionalInfoUpdate(float[] newState)
/*     */   {
/* 206 */     this.netState = newState;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAdditionalInfo()
/*     */   {
/* 212 */     this.prevState = ((float[])this.state.clone());
/* 213 */     this.state = ((float[])this.netState.clone());
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticles()
/*     */   {
/* 220 */     super.spawnParticles();
/*     */     
/* 222 */     if (this.power > 25.0F)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 228 */       int a = -1;
/*     */       BlockPos blockPos;
/*     */       IBlockState iblockstate;
/*     */       Block block;
/* 232 */       do { a++;
/*     */         
/* 234 */         blockPos = new BlockPos(this.posX, this.posY - 0.5D - a, this.posZ);
/* 235 */         iblockstate = this.world.getBlockState(blockPos);
/* 236 */         block = iblockstate.getBlock();
/*     */       }
/* 238 */       while ((block == Blocks.AIR) && (a != 1));
/*     */       
/* 240 */       if (block == Blocks.SNOW_LAYER)
/*     */       {
/* 242 */         blockPos.add(0, -1, 0);
/*     */       }
/*     */       
/* 245 */       for (int i1 = 0; i1 < 2.0D; i1++)
/*     */       {
/* 247 */         double d1 = this.rand.nextFloat() * -1.0F - 1.0F;
/* 248 */         double d2 = this.rand.nextFloat() * -0.5D + 0.25D;
/*     */         
/* 250 */         if (this.rand.nextBoolean())
/*     */         {
/* 252 */           double d3 = this.posX + d1 * this.Forward.x + d2 * this.Left.x;
/* 253 */           double d4 = this.posZ + d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/* 255 */           double d5 = d1 * this.Forward.x + d2 * this.Left.x;
/* 256 */           double d6 = d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/* 258 */           if (block != Blocks.AIR)
/*     */           {
/*     */ 
/* 261 */             if (iblockstate.getMaterial() != net.minecraft.block.material.Material.AIR)
/*     */             {
/* 263 */               this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d3, blockPos.getY() + 1.125D, d4, d5 * 10.0D, 0.0D, d6 * 10.0D, new int[] { Block.getStateId(iblockstate) });
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
/* 275 */     return "RC Trainer Plane";
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntityTrainerPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */