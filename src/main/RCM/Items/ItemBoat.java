/*    */ package RCM.Items;
/*    */ 
/*    */ import RCM.Entities.EntityBoat;
/*    */ import RCM.RCM_Main;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.registries.IForgeRegistry;
/*    */ 
/*    */ public class ItemBoat extends GlobalItem
/*    */ {
/*    */   public ItemBoat()
/*    */   {
/* 17 */     setRegistryName("item_boat");
/* 18 */     ForgeRegistries.ITEMS.register(this);
/* 19 */     setTranslationKey("item_boat");
/* 20 */     setCreativeTab(RCM_Main.tab);
/* 21 */     this.maxStackSize = 1;
/* 22 */     this.hasSubtypes = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean spawnEntity(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, BlockPos blockpos)
/*    */   {
/* 28 */     if (!worldIn.isRemote)
/*    */     {
/* 30 */       EntityBoat rcentity = new EntityBoat(worldIn, blockpos.getX() + 0.5F, blockpos.getY() + 1.3F, blockpos.getZ() + 0.5F);
/* 31 */       rcentity.rotationYaw = (playerIn.rotationYaw % 360.0F);
/* 32 */       rcentity.thePlayer = playerIn;
/*    */       
/* 34 */       if (!worldIn.getCollisionBoxes(rcentity, rcentity.getEntityBoundingBox().expand(1.0D, -0.1D, 1.0D)).isEmpty())
/*    */       {
/* 36 */         return false;
/*    */       }
/*    */       
/* 39 */       worldIn.spawnEntity(rcentity);
/*    */     }
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Items/ItemBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */