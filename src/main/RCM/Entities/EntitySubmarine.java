/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.Audio.MovingSoundSubPing;
/*     */ import RCM.CommonProxy;
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Packets.MessageEntitySubmarine;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.Physics.RotaryWingHandler;
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
/*     */ public class EntitySubmarine
/*     */   extends GlobalEntity
/*     */ {
/*  37 */   public float[] state = new float[5];
/*  38 */   public float[] prevState = new float[5];
/*  39 */   public float[] netState = new float[5];
/*     */   
/*     */   public EntitySubmarine(World world)
/*     */   {
/*  43 */     super(world);
/*  44 */     this.preventEntitySpawning = true;
/*  45 */     setSize(0.5F, 0.5F);
/*  46 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntitySubmarine(World world, double par2, double par4, double par6)
/*     */   {
/*  51 */     this(world);
/*  52 */     setPosition(par2, par4, par6);
/*  53 */     this.prevPosX = par2;
/*  54 */     this.prevPosY = par4;
/*  55 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getID()
/*     */   {
/*  61 */     return 8;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  67 */     double d = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  68 */     d *= 64.0D;
/*  69 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  76 */     Entity entity = damageSource.getImmediateSource();
/*     */     
/*  78 */     if ((!this.world.isRemote) && (!this.isDead) && ((!this.activated) || (!holdingremotecontrol(this.thePlayer)) || ((this.thePlayer != null) && (entity != null) && (entity.getUniqueID() != this.thePlayer.getUniqueID())) || (entity == null)))
/*     */     {
/*  80 */       spawnItems();
/*     */       
/*  82 */       setDead();
/*  83 */       this.activated = false;
/*  84 */       return true;
/*     */     }
/*     */     
/*  87 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  93 */     super.entityInit();
/*     */   }
/*     */   
/*     */ 
/*     */   public void calculatePhysics()
/*     */   {
/*  99 */     this.prevState = ((float[])this.state.clone());
/*     */     
/* 101 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)))
/*     */     {
/* 103 */       this.state[1] = KeyHandler.turnMovement;
/* 104 */       this.state[2] = KeyHandler.pitchMovement;
/*     */     }
/*     */     
/* 107 */     if ((this.activated) && (holdingremotecontrol(this.thePlayer)) && (!this.damaged))
/*     */     {
/* 109 */       if (KeyHandler.powerMovement >= 0.0F)
/*     */       {
/* 111 */         this.power = (KeyHandler.powerMovement * 100.0F);
/*     */       }
/*     */       else
/*     */       {
/* 115 */         this.power = (KeyHandler.powerMovement * 25.0F);
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 120 */       this.power = 0.0F;
/*     */     }
/*     */     
/* 123 */     this.state[0] = ((RotaryWingHandler)this.physicsWorld.rotaryWings.get(0)).getRotationAngle();
/* 124 */     this.state[3] = ((WingHandler)this.physicsWorld.wings.get(0)).getPropAngle();
/* 125 */     this.state[4] = ((WingHandler)this.physicsWorld.wings.get(2)).getPropAngle();
/*     */     
/* 127 */     this.physicsWorld.setFloatDensity(this.state[2]);
/* 128 */     this.physicsWorld.setControlChannel(3, -this.state[1]);
/* 129 */     this.physicsWorld.setControlChannel(4, this.state[1]);
/* 130 */     this.physicsWorld.setControlChannel(5, -this.state[2]);
/* 131 */     this.physicsWorld.setControlChannel(6, this.state[2]);
/* 132 */     this.physicsWorld.setControlChannel(11, this.power / 100.0F);
/*     */     
/* 134 */     sendAdditionalPacket();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void sendAdditionalPacket()
/*     */   {
/* 141 */     if (!this.world.isRemote)
/*     */     {
/* 143 */       MessageHandler.handler.sendToDimension(new MessageEntitySubmarine(getEntityId(), this.state), this.world.provider.getDimension());
/*     */     }
/* 145 */     else if (this.world.isRemote)
/*     */     {
/* 147 */       MessageHandler.handler.sendToServer(new MessageEntitySubmarine(getEntityId(), this.state));
/*     */     }
/*     */   }
/*     */   
/*     */   public void additionalInfoUpdate(float[] newState)
/*     */   {
/* 153 */     this.netState = newState;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAdditionalInfo()
/*     */   {
/* 159 */     this.prevState = ((float[])this.state.clone());
/* 160 */     this.state = ((float[])this.netState.clone());
/*     */   }
/*     */   
/*     */ 
/*     */   public void spawnWeapon(int par)
/*     */   {
/* 166 */     EntityMissile mentity = new EntitySubMissile(this.world, this.posX, this.posY, this.posZ);
/* 167 */     mentity.rotationYaw = (this.rotationYaw % 360.0F);
/* 168 */     mentity.thePlayer = this.thePlayer;
/* 169 */     mentity.activated = false;
/* 170 */     mentity.setAttachmentArg(getEntityId(), par);
/*     */     
/* 172 */     this.world.spawnEntity(mentity);
/*     */   }
/*     */   
/*     */ 
/*     */   public void spawnItems()
/*     */   {
/* 178 */     if (!this.damaged)
/*     */     {
/* 180 */       dropItem(RCM_Main.item_submarine, 1);
/*     */     }
/*     */     else
/*     */     {
/* 184 */       dropCraftItem(RCM_Main.item_missile, 0.8D);
/* 185 */       dropCraftItem(RCM_Main.item_battery, 0.8D);
/* 186 */       dropCraftItem(RCM_Main.item_receivermodule, 0.8D);
/* 187 */       dropCraftItem(RCM_Main.item_speed_controller, 0.8D);
/* 188 */       dropCraftItem(RCM_Main.item_electricmotor, 0.8D);
/* 189 */       dropCraftItem(RCM_Main.item_submarine_body, 0.8D);
/* 190 */       dropCraftItem(RCM_Main.item_servo, 0.8D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 198 */     super.registerSounds();
/*     */     
/* 200 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundSubPing(this));
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticles()
/*     */   {
/* 207 */     super.spawnParticles();
/*     */     
/* 209 */     if (this.power > 25.0F)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 215 */       int a = 0;
/*     */       
/* 217 */       BlockPos blockPos = new BlockPos(this.posX, this.posY - 0.5D - a, this.posZ);
/* 218 */       IBlockState iblockstate = this.world.getBlockState(blockPos);
/* 219 */       Block block = iblockstate.getBlock();
/*     */       
/* 221 */       double d1 = -1.3D;
/*     */       
/* 223 */       if ((this.rand.nextBoolean()) && (this.rand.nextBoolean()))
/*     */       {
/* 225 */         double d3 = this.posX + d1 * this.Forward.x;
/* 226 */         double d4 = this.posZ + d1 * this.Forward.z;
/* 227 */         double d7 = this.posY + d1 * this.Forward.y;
/*     */         
/* 229 */         double d5 = d1 * this.Forward.x;
/* 230 */         double d6 = d1 * this.Forward.z;
/* 231 */         double d8 = d1 * this.Forward.y;
/*     */         
/* 233 */         if (iblockstate.getMaterial() == Material.WATER)
/*     */         {
/* 235 */           this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d3, d7, d4, 0.0D, 0.0D, 0.0D, new int[] { Block.getStateId(iblockstate) });
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 244 */     return "RC Submarine";
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntitySubmarine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */