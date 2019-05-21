/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.Audio.MovingSoundF22High;
/*     */ import RCM.Audio.MovingSoundRadarLock;
/*     */ import RCM.Audio.MovingSoundRadarTick;
/*     */ import RCM.CommonProxy;
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageEntityF22;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.PhysicsHelper;
/*     */ import RCM.Physics.RotaryWingHandler;
/*     */ import RCM.Physics.WingHandler;
/*     */ import RCM.RCM_Main;
/*     */ import com.bulletphysics.dynamics.vehicle.RaycastVehicle;
/*     */ import com.bulletphysics.dynamics.vehicle.WheelInfo;
/*     */ import com.bulletphysics.util.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.vecmath.Vector3f;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityF22
/*     */   extends GlobalEntity
/*     */ {
/*  50 */   public float[] state = new float[14];
/*  51 */   public float[] prevState = new float[14];
/*  52 */   public float[] netState = new float[14];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private double groundHight;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EntityF22(World world)
/*     */   {
/*  65 */     super(world);
/*  66 */     this.preventEntitySpawning = true;
/*  67 */     setSize(0.5F, 0.5F);
/*  68 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntityF22(World world, double par2, double par4, double par6)
/*     */   {
/*  73 */     this(world);
/*  74 */     setPosition(par2, par4, par6);
/*  75 */     this.prevPosX = par2;
/*  76 */     this.prevPosY = par4;
/*  77 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getID()
/*     */   {
/*  83 */     return 4;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  89 */     double d = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  90 */     d *= 64.0D;
/*  91 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  98 */     Entity entity = damageSource.getImmediateSource();
/*     */     
/* 100 */     if ((!this.world.isRemote) && (!this.isDead) && ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || ((this.thePlayer != null) && (entity != null) && (entity.getUniqueID() != this.thePlayer.getUniqueID())) || (entity == null)))
/*     */     {
/* 102 */       spawnItems();
/*     */       
/* 104 */       setDead();
/* 105 */       this.activated = false;
/* 106 */       return true;
/*     */     }
/*     */     
/* 109 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/* 115 */     super.entityInit();
/*     */     
/* 117 */     this.groundHight = this.posY;
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics()
/*     */   {
/* 123 */     this.prevState = ((float[])this.state.clone());
/*     */     
/* 125 */     this.physicsWorld.getVisibleEntities(this);
/*     */     
/* 127 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)))
/*     */     {
/*     */ 
/* 130 */       this.state[3] = (-KeyHandler.yawMovement);
/*     */       
/*     */ 
/* 133 */       this.state[2] = KeyHandler.rollMovement;
/*     */     }
/*     */     
/*     */ 
/* 137 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)) && (!this.damaged))
/*     */     {
/* 139 */       this.power = (KeyHandler.absPowerMovement * 100.0F);
/*     */     }
/*     */     else
/*     */     {
/* 143 */       this.power = 0.0F;
/*     */     }
/*     */     
/* 146 */     if (this.posY - this.groundHight >= 3.0D)
/*     */     {
/* 148 */       this.state[13] += 0.1F;
/* 149 */       this.state[13] = Math.min(1.0F, this.state[13]);
/*     */     }
/*     */     else
/*     */     {
/* 153 */       this.state[13] -= 0.1F;
/* 154 */       this.state[13] = Math.max(0.0F, this.state[13]);
/*     */     }
/*     */     
/* 157 */     this.state[0] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getAngularVelocity();
/* 158 */     this.state[1] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(4)).getAngularVelocity();
/* 159 */     this.state[4] = ((WingHandler)this.physicsWorld.wings.get(2)).getPropAngle();
/* 160 */     this.state[5] = (-((WingHandler)this.physicsWorld.wings.get(3)).getPropAngle());
/*     */     
/* 162 */     this.state[6] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).steering * this.helper.radToDeg);
/* 163 */     this.state[7] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).rotation * this.helper.radToDeg);
/* 164 */     this.state[8] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).rotation * this.helper.radToDeg);
/* 165 */     this.state[9] = (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).rotation * this.helper.radToDeg);
/*     */     
/* 167 */     this.state[10] = 
/* 168 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(0)).suspensionStiffness);
/* 169 */     this.state[11] = 
/* 170 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(1)).suspensionStiffness);
/* 171 */     this.state[12] = 
/* 172 */       (((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).wheelsSuspensionForce / ((WheelInfo)this.physicsWorld.vehicle.wheelInfo.get(2)).suspensionStiffness);
/*     */     
/* 174 */     this.physicsWorld.setControlChannel(1, -this.state[2]);
/* 175 */     this.physicsWorld.setControlChannel(2, -this.state[2]);
/* 176 */     this.physicsWorld.setControlChannel(3, -this.state[2] * 0.4F);
/* 177 */     this.physicsWorld.setControlChannel(4, this.state[2] * 0.4F);
/* 178 */     this.physicsWorld.setControlChannel(5, -this.state[3]);
/* 179 */     this.physicsWorld.setControlChannel(6, -this.state[3]);
/* 180 */     this.physicsWorld.setControlChannel(9, this.state[3]);
/* 181 */     this.physicsWorld.setControlChannel(11, this.power / 100.0F);
/*     */     
/* 183 */     sendAdditionalPacket();
/*     */   }
/*     */   
/*     */ 
/*     */   public void spawnItems()
/*     */   {
/* 189 */     if (!this.damaged)
/*     */     {
/* 191 */       dropItem(RCM_Main.item_f22, 1);
/*     */     }
/*     */     else
/*     */     {
/* 195 */       dropCraftItem(RCM_Main.item_missile, 0.8D);
/* 196 */       dropCraftItem(RCM_Main.item_battery, 0.8D);
/* 197 */       dropCraftItem(RCM_Main.item_receivermodule, 0.8D);
/* 198 */       dropCraftItem(RCM_Main.item_flight_controller, 0.8D);
/* 199 */       dropCraftItem(RCM_Main.item_speed_controller, 0.8D);
/* 200 */       dropCraftItem(RCM_Main.item_electricmotor, 0.8D);
/* 201 */       dropCraftItem(RCM_Main.item_f22_body, 0.8D);
/* 202 */       dropCraftItem(RCM_Main.item_narrowwheel, 0.8D);
/* 203 */       dropCraftItem(RCM_Main.item_servo, 0.8D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 211 */     super.registerSounds();
/*     */     
/* 213 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundF22High(this, 0));
/* 214 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundF22High(this, 1));
/* 215 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundRadarLock(this));
/* 216 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundRadarTick(this));
/*     */   }
/*     */   
/*     */ 
/*     */   public void sendAdditionalPacket()
/*     */   {
/* 222 */     if (!this.world.isRemote)
/*     */     {
/* 224 */       MessageHandler.handler.sendToDimension(new MessageEntityF22(getEntityId(), this.state), this.world.provider.getDimension());
/*     */     }
/* 226 */     else if (this.world.isRemote)
/*     */     {
/* 228 */       MessageHandler.handler.sendToServer(new MessageEntityF22(getEntityId(), this.state));
/*     */     }
/*     */   }
/*     */   
/*     */   public void additionalInfoUpdate(float[] newState)
/*     */   {
/* 234 */     this.netState = newState;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAdditionalInfo()
/*     */   {
/* 240 */     this.prevState = ((float[])this.state.clone());
/* 241 */     this.state = ((float[])this.netState.clone());
/*     */   }
/*     */   
/*     */ 
/*     */   public void spawnWeapon(int par)
/*     */   {
/* 247 */     EntityMissile mentity = new EntityMissile(this.world, this.posX, this.posY, this.posZ);
/* 248 */     mentity.rotationYaw = (this.rotationYaw % 360.0F);
/* 249 */     mentity.thePlayer = this.thePlayer;
/* 250 */     mentity.activated = false;
/* 251 */     mentity.setAttachmentArg(getEntityId(), par);
/*     */     
/* 253 */     this.world.spawnEntity(mentity);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticles()
/*     */   {
/* 260 */     super.spawnParticles();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 266 */     int a = -1;
/*     */     BlockPos blockPos;
/*     */     IBlockState iblockstate;
/*     */     Block block;
/* 270 */     do { a++;
/*     */       
/* 272 */       blockPos = new BlockPos(this.posX, this.posY - 0.5D - a, this.posZ);
/* 273 */       iblockstate = this.world.getBlockState(blockPos);
/* 274 */       block = iblockstate.getBlock();
/*     */     }
/* 276 */     while ((block == Blocks.AIR) && (a != 1));
/*     */     
/* 278 */     if (block != Blocks.AIR)
/*     */     {
/* 280 */       this.groundHight = blockPos.getY();
/*     */     }
/*     */     else
/*     */     {
/* 284 */       this.groundHight = (this.posY - 5.0D);
/*     */     }
/*     */     
/* 287 */     if (block == Blocks.SNOW_LAYER)
/*     */     {
/* 289 */       blockPos.add(0, -1, 0);
/*     */     }
/*     */     
/* 292 */     if (this.power > 25.0F)
/*     */     {
/* 294 */       for (int i1 = 0; i1 < 2.0D; i1++)
/*     */       {
/* 296 */         double d1 = this.rand.nextFloat() * -1.0F - 1.0F;
/* 297 */         double d2 = this.rand.nextFloat() * -0.5D + 0.25D;
/*     */         
/* 299 */         if (this.rand.nextBoolean())
/*     */         {
/* 301 */           double d3 = this.posX + d1 * this.Forward.x + d2 * this.Left.x;
/* 302 */           double d4 = this.posZ + d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/* 304 */           double d5 = d1 * this.Forward.x + d2 * this.Left.x;
/* 305 */           double d6 = d1 * this.Forward.z + d2 * this.Left.z;
/*     */           
/* 307 */           if (block != Blocks.AIR)
/*     */           {
/* 309 */             if (iblockstate.getMaterial() != Material.AIR)
/*     */             {
/* 311 */               this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d3, blockPos.getY() + 1.125D, d4, d5 * 10.0D, 0.0D, d6 * 10.0D, new int[] { Block.getStateId(iblockstate) });
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
/* 322 */     return "RC F-22";
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntityF22.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */