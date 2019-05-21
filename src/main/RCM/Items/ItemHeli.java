/*    */ package RCM.Items;
/*    */ 
/*    */ import RCM.Entities.EntityHeli;
/*    */ import RCM.RCM_Main;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.registries.IForgeRegistry;
/*    */ 
/*    */ public class ItemHeli
/*    */   extends GlobalItem
/*    */ {
/*    */   public ItemHeli()
/*    */   {
/* 19 */     setRegistryName("item_heli");
/* 20 */     ForgeRegistries.ITEMS.register(this);
/* 21 */     setTranslationKey("item_heli");
/* 22 */     setCreativeTab(RCM_Main.tab);
/* 23 */     this.maxStackSize = 1;
/* 24 */     this.hasSubtypes = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean spawnEntity(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, BlockPos blockpos)
/*    */   {
/* 30 */     if (!worldIn.isRemote)
/*    */     {
/* 32 */       EntityHeli rcentity = new EntityHeli(worldIn, blockpos.getX() + 0.5F, blockpos.getY() + 1.3F, blockpos.getZ() + 0.5F);
/* 33 */       rcentity.rotationYaw = (playerIn.rotationYaw % 360.0F);
/* 34 */       rcentity.thePlayer = playerIn;
/*    */       
/* 36 */       if (!worldIn.getCollisionBoxes(rcentity, rcentity.getEntityBoundingBox().expand(1.0D, -0.1D, 1.0D)).isEmpty())
/*    */       {
/* 38 */         return false;
/*    */       }
/*    */       
/* 41 */       worldIn.spawnEntity(rcentity);
/*    */     }
/*    */     
/* 44 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Items/ItemHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */