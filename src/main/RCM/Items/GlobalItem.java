/*     */ package RCM.Items;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.RayTraceResult.Type;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class GlobalItem extends Item
/*     */ {
/*     */   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
/*     */   {
/*  27 */     ItemStack itemstack = playerIn.getHeldItem(hand);
/*  28 */     float f = 1.0F;
/*  29 */     float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * 1.0F;
/*  30 */     float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * 1.0F;
/*  31 */     double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * 1.0D;
/*  32 */     double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * 1.0D + playerIn.getEyeHeight();
/*  33 */     double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * 1.0D;
/*  34 */     Vec3d vec3d = new Vec3d(d0, d1, d2);
/*  35 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  36 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  37 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  38 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  39 */     float f7 = f4 * f5;
/*  40 */     float f8 = f3 * f5;
/*  41 */     double d3 = 5.0D;
/*  42 */     Vec3d vec3d1 = vec3d.add(f7 * 5.0D, f6 * 5.0D, f8 * 5.0D);
/*  43 */     RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec3d, vec3d1, true);
/*     */     
/*  45 */     if (raytraceresult == null)
/*     */     {
/*  47 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */     
/*     */ 
/*  51 */     Vec3d vec3d2 = playerIn.getLook(1.0F);
/*  52 */     boolean flag = false;
/*  53 */     List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().expand(vec3d2.x * 5.0D, vec3d2.y * 5.0D, vec3d2.z * 5.0D).grow(1.0D));
/*     */     
/*  55 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/*  57 */       Entity entity = (Entity)list.get(i);
/*     */       
/*  59 */       if (entity.canBeCollidedWith())
/*     */       {
/*  61 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(entity.getCollisionBorderSize());
/*     */         
/*  63 */         if (axisalignedbb.contains(vec3d))
/*     */         {
/*  65 */           flag = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  70 */     if (flag)
/*     */     {
/*  72 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */     
/*     */ 
/*  76 */     if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
/*     */     {
/*  78 */       BlockPos blockpos = raytraceresult.getBlockPos();
/*     */       
/*  80 */       if (worldIn.getBlockState(blockpos).getBlock() == Blocks.SNOW_LAYER)
/*     */       {
/*  82 */         blockpos = blockpos.down();
/*     */       }
/*     */       
/*  85 */       if (!spawnEntity(itemstack, worldIn, playerIn, blockpos))
/*     */       {
/*  87 */         return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */       }
/*     */       
/*  90 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/*  92 */         itemstack.grow(-1);
/*     */       }
/*     */       
/*  95 */       playerIn.addStat(StatList.getObjectUseStats(this));
/*     */     }
/*     */     
/*  98 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean spawnEntity(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, BlockPos blockpos)
/*     */   {
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Items/GlobalItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */