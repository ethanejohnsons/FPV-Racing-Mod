/*     */ package RCM.Entities;
/*     */ 
/*     */ import RCM.KeyHandler;
/*     */ import RCM.Physics.PhysicsHandler;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CameraHandler
/*     */   extends EntityPlayer
/*     */ {
/*     */   private GlobalEntity rcentity;
/*     */   private GlobalEntity backUpEntity;
/*  29 */   private float offSet = 0.05F;
/*  30 */   private AxisAlignedBB nullAABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   
/*     */   private double CamPosX;
/*     */   private double CamPosZ;
/*     */   private double CamPosY;
/*     */   private double CamRadialXZ;
/*     */   private double CamRadialY;
/*     */   private double radial;
/*     */   private float yawOffset;
/*     */   private float heightOffset;
/*     */   private float zoomOffset;
/*     */   
/*     */   public CameraHandler(World worldIn, Minecraft mc)
/*     */   {
/*  44 */     super(worldIn, mc.player.getGameProfile());
/*  45 */     setSize(0.0F, 0.0F);
/*  46 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public CameraHandler(World world, GlobalEntity entity)
/*     */   {
/*  51 */     this(world, FMLClientHandler.instance().getClient());
/*  52 */     this.rcentity = entity;
/*  53 */     this.rotationYaw = this.rcentity.rotationYaw;
/*  54 */     this.rotationPitch = 0.0F;
/*     */     
/*  56 */     this.yawOffset = 0.0F;
/*  57 */     this.heightOffset = getEntityHeightOffset();
/*  58 */     this.zoomOffset = 3.3F;
/*     */     
/*  60 */     setPosition(this.rcentity.posX, this.rcentity.posY + this.heightOffset, this.rcentity.posZ);
/*     */   }
/*     */   
/*     */   public float getEntityHeightOffset()
/*     */   {
/*  65 */     if ((this.rcentity instanceof EntityCar))
/*     */     {
/*  67 */       return 0.75F - getEyeHeight();
/*     */     }
/*     */     
/*  70 */     return 0.5F - getEyeHeight();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  76 */     super.onUpdate();
/*     */     
/*  78 */     Minecraft mc = FMLClientHandler.instance().getClient();
/*     */     
/*  80 */     double distance = 0.3D;
/*  81 */     int blockID = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */     setValues();
/*     */     
/*  89 */     this.CamRadialXZ = (-(this.rcentity.rotationYaw + this.yawOffset) / 180.0D * 3.141592653589793D);
/*     */     
/*     */     boolean collideCeck;
/*     */     do
/*     */     {
/*  94 */       if (((this.rcentity instanceof EntityCar)) || ((this.rcentity instanceof EntityDrone)) || ((this.rcentity instanceof EntityHeli)))
/*     */       {
/*  96 */         this.CamRadialY = 0.0D;
/*     */       }
/*     */       else
/*     */       {
/* 100 */         this.CamRadialY = (this.rcentity.rotationPitch / 180.0D * 3.141592653589793D);
/*     */       }
/*     */       
/* 103 */       this.CamPosX = (this.rcentity.posX - Math.sin(this.CamRadialXZ) * Math.cos(this.CamRadialY) * distance);
/* 104 */       this.CamPosZ = (this.rcentity.posZ - Math.cos(this.CamRadialXZ) * Math.cos(this.CamRadialY) * distance);
/* 105 */       this.CamPosY = (this.rcentity.posY - Math.sin(this.CamRadialY) * distance);
/*     */       
/* 107 */       BlockPos bp = new BlockPos(this.CamPosX, this.CamPosY + this.heightOffset + getEyeHeight() - 0.05000000074505806D, this.CamPosZ);
/* 108 */       Block bk = this.world.getBlockState(bp).getBlock();
/*     */       
/* 110 */       collideCeck = bk.isPassable(this.world, bp);
/*     */       
/* 112 */       if ((!collideCeck) || (distance >= this.zoomOffset))
/*     */       {
/* 114 */         distance -= 0.3D;
/*     */         
/* 116 */         if (((this.rcentity instanceof EntityCar)) || ((this.rcentity instanceof EntityDrone)))
/*     */         {
/* 118 */           this.CamRadialY = 0.0D;
/*     */         }
/*     */         else
/*     */         {
/* 122 */           this.CamRadialY = (this.rcentity.rotationPitch / 180.0D * 3.141592653589793D);
/*     */         }
/*     */         
/* 125 */         this.CamPosX = (this.rcentity.posX - Math.sin(this.CamRadialXZ) * Math.cos(this.CamRadialY) * distance);
/* 126 */         this.CamPosZ = (this.rcentity.posZ - Math.cos(this.CamRadialXZ) * Math.cos(this.CamRadialY) * distance);
/* 127 */         this.CamPosY = (this.rcentity.posY - Math.sin(this.CamRadialY) * distance);
/*     */       }
/*     */       else
/*     */       {
/* 131 */         distance += 0.005D;
/*     */       }
/*     */       
/* 134 */     } while ((collideCeck) && (distance <= this.zoomOffset - 0.3F));
/*     */     
/* 136 */     if ((!(this.rcentity instanceof EntityCar)) && (!(this.rcentity instanceof EntityDrone)) && (!(this.rcentity instanceof EntityHeli)) && ((!this.rcentity.weaponsMode) || (!(this.rcentity instanceof EntitySubmarine))))
/*     */     {
/* 138 */       this.rotationPitch = (-this.rcentity.rotationPitch);
/* 139 */       mc.player.rotationPitch = this.rotationPitch;
/*     */     }
/*     */     
/* 142 */     if ((this.rcentity.weaponsMode) && (mc.gameSettings.thirdPersonView <= 1) && ((this.rcentity instanceof EntitySubmarine)))
/*     */     {
/*     */ 
/* 145 */       distance = 10.0D;
/*     */       
/* 147 */       this.rotationYaw = mc.player.rotationYaw;
/*     */       
/* 149 */       this.radial = (-this.rotationYaw / 180.0D * 3.141592654D);
/*     */       
/* 151 */       if (mc.player.rotationPitch < 5.0F)
/*     */       {
/* 153 */         mc.player.rotationPitch = 5.0F;
/*     */       }
/* 155 */       else if (mc.player.rotationPitch > 40.0F)
/*     */       {
/* 157 */         mc.player.rotationPitch = 40.0F;
/*     */       }
/*     */       
/* 160 */       this.rotationPitch = mc.player.rotationPitch;
/*     */       
/* 162 */       this.CamPosX = (this.rcentity.posX - Math.sin(this.radial) * distance);
/* 163 */       this.CamPosZ = (this.rcentity.posZ - Math.cos(this.radial) * distance);
/*     */       
/* 165 */       if (this.world.getHeight() / 4 <= this.CamPosY)
/*     */       {
/* 167 */         setPosition(this.CamPosX, this.CamPosY + 20.0D, this.CamPosZ);
/*     */       }
/* 169 */       else if (this.world.getHeight() / 4 > this.CamPosY)
/*     */       {
/* 171 */         setPosition(this.CamPosX, this.world.getHorizon() + 20.0D, this.CamPosZ);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 176 */       setPosition(this.CamPosX, this.CamPosY + this.heightOffset, this.CamPosZ);
/* 177 */       this.rotationYaw = (this.rcentity.rotationYaw + this.yawOffset);
/* 178 */       mc.player.rotationYaw = this.rotationYaw;
/*     */     }
/*     */     
/* 181 */     if (((this.rcentity instanceof EntitySubmarine)) && (this.rcentity.weaponsMode))
/*     */     {
/* 183 */       getRaytracePos();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setValues()
/*     */   {
/* 189 */     this.yawOffset += KeyHandler.camYaw * 5.0F;
/* 190 */     this.heightOffset += KeyHandler.camHeight * 0.1F;
/* 191 */     this.zoomOffset -= KeyHandler.camZoom * 0.1F;
/*     */     
/* 193 */     this.heightOffset = Math.max(-1.52F, this.heightOffset);
/* 194 */     this.zoomOffset = Math.max(3.3F, this.zoomOffset);
/*     */     
/* 196 */     this.heightOffset = Math.min(-0.22F, this.heightOffset);
/* 197 */     this.zoomOffset = Math.min(6.3F, this.zoomOffset);
/*     */     
/* 199 */     if (KeyHandler.camReset)
/*     */     {
/* 201 */       this.yawOffset = 0.0F;
/* 202 */       this.heightOffset = getEntityHeightOffset();
/* 203 */       this.zoomOffset = 3.3F;
/*     */     }
/*     */   }
/*     */   
/*     */   private void getRaytracePos()
/*     */   {
/* 209 */     float f1 = this.rotationPitch;
/* 210 */     float f2 = this.rotationYaw;
/* 211 */     double d0 = this.posX;
/* 212 */     double d1 = this.posY + getEyeHeight();
/* 213 */     double d2 = this.posZ;
/* 214 */     Vec3d pos = new Vec3d(d0, d1, d2);
/* 215 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 216 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 217 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 218 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 219 */     float f7 = f4 * f5;
/* 220 */     float f8 = f3 * f5;
/* 221 */     double d3 = 150.0D;
/* 222 */     Vec3d vec3d1 = pos.add(f7 * d3, f6 * d3, f8 * d3);
/* 223 */     RayTraceResult raytraceresult = this.world.rayTraceBlocks(pos, vec3d1, true);
/*     */     
/* 225 */     if (raytraceresult != null)
/*     */     {
/* 227 */       BlockPos bp = raytraceresult.getBlockPos();
/* 228 */       this.rcentity.physicsWorld.setTargetPosition(new Vector3f(bp.getX() + 0.5F, bp.getY() + 0.5F, bp.getZ() + 0.5F));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox()
/*     */   {
/* 235 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public AxisAlignedBB getEntityBoundingBox()
/*     */   {
/* 242 */     return this.nullAABB;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 248 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onLivingUpdate() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isSneaking()
/*     */   {
/* 259 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isSpectator()
/*     */   {
/* 265 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isCreative()
/*     */   {
/* 271 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Entities/CameraHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */