/*    */ package RCM.Items;
/*    */ 
/*    */ import RCM.Entities.EntityDrone;
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
/*    */ public class ItemDrone extends GlobalItem
/*    */ {
/*    */   public ItemDrone()
/*    */   {
/* 18 */     setRegistryName("item_drone");
/* 19 */     ForgeRegistries.ITEMS.register(this);
/* 20 */     setTranslationKey("item_drone");
/* 21 */     setCreativeTab(RCM_Main.tab);
/* 22 */     this.maxStackSize = 1;
/* 23 */     this.hasSubtypes = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean spawnEntity(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, BlockPos blockpos)
/*    */   {
/* 29 */     if (!worldIn.isRemote)
/*    */     {
/* 31 */       EntityDrone rcentity = new EntityDrone(worldIn, blockpos.getX() + 0.5F, blockpos.getY() + 1.3F, blockpos.getZ() + 0.5F);
/* 32 */       rcentity.rotationYaw = (playerIn.rotationYaw % 360.0F);
/* 33 */       rcentity.thePlayer = playerIn;
/*    */       
/* 35 */       if (!worldIn.getCollisionBoxes(rcentity, rcentity.getEntityBoundingBox().expand(1.0D, -0.1D, 1.0D)).isEmpty())
/*    */       {
/* 37 */         return false;
/*    */       }
/*    */       
/* 40 */       worldIn.spawnEntity(rcentity);
/*    */     }
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Items/ItemDrone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */