/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.Audio.MovingSoundMissile;
/*     */ import RCM.CommonProxy;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Packets.MessageSpawnWeapon;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import RCM.RCM_Main;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
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
/*     */ 
/*     */ 
/*     */ public class EntityMissile
/*     */   extends GlobalEntity
/*     */ {
/*  46 */   private int attachmentID = 0;
/*  47 */   private int rcentityID = 0;
/*     */   private int attachedTimer;
/*     */   public boolean rocketMotorActive;
/*     */   
/*     */   public EntityMissile(World world)
/*     */   {
/*  53 */     super(world);
/*  54 */     this.preventEntitySpawning = true;
/*  55 */     setSize(0.001F, 0.001F);
/*  56 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntityMissile(World world, double par2, double par4, double par6)
/*     */   {
/*  61 */     this(world);
/*  62 */     setPosition(par2, par4, par6);
/*  63 */     this.prevPosX = par2;
/*  64 */     this.prevPosY = par4;
/*  65 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getID()
/*     */   {
/*  71 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/*  77 */     double d = 50.0D;
/*  78 */     return par1 < d * d;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float par2)
/*     */   {
/*  84 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  90 */     super.entityInit();
/*  91 */     this.attachedTimer = 2;
/*     */   }
/*     */   
/*     */   public void setAttachmentArg(int par1, int par2)
/*     */   {
/*  96 */     this.rcentityID = par1;
/*  97 */     this.attachmentID = par2;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 103 */     super.onUpdate();
/*     */     
/* 105 */     if (this.physicsWorld != null)
/*     */     {
/* 107 */       this.rocketMotorActive = this.physicsWorld.getRocketMotorActive();
/*     */     }
/*     */     
/* 110 */     if (this.attachedTimer >= 0)
/*     */     {
/* 112 */       this.attachedTimer -= 1;
/*     */     }
/*     */     
/* 115 */     if ((!this.world.isRemote) && (this.thePlayer != null) && (this.attachedTimer == 0))
/*     */     {
/* 117 */       MessageHandler.handler.sendTo(new MessageSpawnWeapon(this.rcentityID, this.attachmentID, getEntityId()), (EntityPlayerMP)this.thePlayer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
/*     */   {
/* 124 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox()
/*     */   {
/* 130 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 136 */     return false;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSounds()
/*     */   {
/* 142 */     RCM_Main.proxy.getSoundHandler().playSound(new MovingSoundMissile(this));
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spawnParticles()
/*     */   {
/* 149 */     if (this.rocketMotorActive)
/*     */     {
/* 151 */       for (int i1 = 0; i1 < 2.0D; i1++)
/*     */       {
/* 153 */         Vector3f pos = new Vector3f(this.Forward);
/* 154 */         pos.scale(-2.5F);
/* 155 */         Vector3f vel = new Vector3f(this.Forward);
/* 156 */         vel.scale(-1.5F);
/* 157 */         this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + pos.x, this.posY + pos.y, this.posZ + pos.z, vel.x, vel.y, vel.z, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/EntityMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */