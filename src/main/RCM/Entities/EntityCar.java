/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.PhysicsHelper;
/*     */ import RCM.RCM_Main;
/*     */ import com.bulletphysics.dynamics.vehicle.RaycastVehicle;
/*     */ import com.bulletphysics.dynamics.vehicle.WheelInfo;
/*     */ import com.bulletphysics.util.ObjectArrayList;
/*     */ import java.util.Random;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ 
/*     */ public class EntityCar extends GlobalEntity
/*     */ {
/*  22 */   public float[] state = new float[10];
/*  23 */   public float[] prevState = new float[10];
/*  24 */   private float[] netState = new float[10];
/*     */   private float steerAngle;
/*     */   
/*     */   public EntityCar(World world)
/*     */   {
/*  29 */     super(world);
/*  30 */     this.preventEntitySpawning = true;
/*  31 */     setSize(0.5F, 0.5F);
/*  32 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntityCar(World world, double par2, double par4, double par6)
/*     */   {
/*  37 */     this(world);
/*  38 */     setPosition(par2, par4, par6);
/*  39 */     this.prevPosX = par2;
/*  40 */     this.prevPosY = par4;
/*  41 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getID()
/*     */   {
/*  47 */     return 3;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  53 */     double d = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  54 */     d *= 64.0D;
/*  55 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  62 */     net.minecraft.entity.Entity entity = damageSource.getImmediateSource();
/*     */     
/*  64 */     if ((!this.world.isRemote) && (!this.isDead) && ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || ((this.thePlayer != null) && (entity != null) && (entity.getUniqueID() != this.thePlayer.getUniqueID())) || (entity == null)))
/*     */     {
/*  66 */       spawnItems();
/*     */       
/*  68 */       setDead();
/*  69 */       this.activated = false;
/*  70 */       return true;
/*     */     }
/*     */     
/*  73 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  79 */     super.entityInit();
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics()
/*     */   {
/*  85 */     this.prevState = ((float[])this.state.clone());
/*     */     
/*  87 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)))
/*     */     {
/*  89 */       this.steerAngle = (-KeyHandler.turnMovement);
/*     */       
/*  91 */       if (KeyHandler.jump)
/*     */       {
/*  93 */         this.physicsWorld.jump();
/*     */       }
/*     */     }
/*     */     
/*  97 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)) && (!this.damaged))
/*     */     {
/*  99 */       if (KeyHandler.powerMovement >= 0.0F)
/*     */       {
/* 101 */         this.power = (KeyHandler.powerMovement * 100.0F);
/*     */       }
/*     */       else
/*     */       {
/* 105 */         this.power = (KeyHandler.powerMovement * 25.0F);
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 110 */       this.power = 0.0F;
/*     */     }
/*     */     
/* 113 */     this.state[0] = ((RCM.Physics.MotorHandler)this.physicsWorld.motors.get(0)).getRotationalVel();
/* 114 */     this.state[1] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).steering * this.helper.radToDeg);
/* 115 */     this.state[2] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).rotation * this.helper.radToDeg);
/* 116 */     this.state[3] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).rotation * this.helper.radToDeg);
/* 117 */     this.state[4] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).rotation * this.helper.radToDeg);
/* 118 */     this.state[5] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(3)).rotation * this.helper.radToDeg);
/*     */     
/* 120 */     this.state[6] = 
/* 121 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).suspensionStiffness);
/* 122 */     this.state[7] = 
/* 123 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).suspensionStiffness);
/* 124 */     this.state[8] = 
/* 125 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).suspensionStiffness);
/* 126 */     this.state[9] = 
/* 127 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(3)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(3)).suspensionStiffness);
/*     */     
/* 129 */     this.physicsWorld.setControlChannel(11, this.power / 100.0F);
/* 130 */     this.physicsWorld.setControlChannel(9, this.steerAngle);
/*     */     
/*     */ 
/* 133 */     sendAdditionalPacket();
/*     */   }
/*     */   
/*     */ 
/*     */   public void sendAdditionalPacket()
/*     */   {
/* 139 */     if (!this.world.isRemote)
/*     */     {
/* 141 */       MessageHandler.handler.sendToDimension(new RCM.Packets.MessageEntityCar(getEntityId(), this.state), this.world.provider.getDimension());
/*     */     }
/* 143 */     else if (this.world.isRemote)
/*     */     {
/* 145 */       MessageHandler.handler.sendToServer(new RCM.Packets.MessageEntityCar(getEntityId(), this.state));
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
/* 166 */       dropItem(RCM_Main.item_car, 1);
/*     */     }
/*     */     else
/*     */     {
/* 170 */       dropCraftItem(RCM_Main.item_battery, 0.8D);
/* 171 */       dropCraftItem(RCM_Main.item_receivermodule, 0.8D);
/* 172 */       dropCraftItem(RCM_Main.item_speed_controller, 0.8D);
/* 173 */       dropCraftItem(RCM_Main.item_electricmotor, 0.8D);
/* 174 */       dropCraftItem(RCM_Main.item_car_body, 0.8D);
/* 175 */       dropCraftItem(RCM_Main.item_servo, 0.8D);
/* 176 */       dropCraftItem(RCM_Main.item_widewheel, 0.8D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 184 */     super.registerSounds();
/*     */     
/* 186 */     RCM_Main.proxy.getSoundHandler().playSound(new RCM.Audio.MovingSoundCarHigh(this));
/*     */   }
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
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
/* 207 */       if (block == net.minecraft.init.Blocks.SNOW_LAYER)
/*     */       {
/* 209 */         blockPos.add(0, -1, 0);
/*     */       }
/*     */       
/* 212 */       for (int i1 = 0; i1 < 2.0D; i1++)
/*     */       {
/* 214 */         double d1 = this.rand.nextFloat() * -2.0F;
/* 215 */         double d2 = this.rand.nextFloat() * -0.5D + 0.25D;
/*     */         
/* 217 */         if (this.rand.nextBoolean())
/*     */         {
/* 219 */           double d3 = this.posX + d1 * this.Forward.x + d2 * this.Left.x;
/* 220 */           double d4 = this.posZ + d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/* 222 */           double d5 = d1 * this.Forward.x + d2 * this.Left.x;
/* 223 */           double d6 = d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/*     */ 
/* 226 */           if (block != net.minecraft.init.Blocks.AIR)
/*     */           {
/*     */ 
/* 229 */             if (iblockstate.getMaterial() != net.minecraft.block.material.Material.AIR)
/*     */             {
/* 231 */               this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.BLOCK_CRACK, d3, blockPos.getY() + 1.125D, d4, d5, 0.0D, d6, new int[] { Block.getStateId(iblockstate) });
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
/* 243 */     return "RC Car";
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntityCar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */