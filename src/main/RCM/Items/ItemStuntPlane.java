/*    */ package RCM.Items;
/*    */ 
/*    */ import RCM.RCM_Main;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.registries.IForgeRegistry;
/*    */ 
/*    */ 
/*    */ public class ItemStuntPlane
/*    */   extends GlobalItem
/*    */ {
/*    */   public ItemStuntPlane()
/*    */   {
/* 17 */     setRegistryName("item_stuntplane");
/* 18 */     ForgeRegistries.ITEMS.register(this);
/* 19 */     setTranslationKey("item_stuntplane");
/* 20 */     setCreativeTab(RCM_Main.tab);
/* 21 */     this.maxStackSize = 1;
/* 22 */     this.hasSubtypes = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean spawnEntity(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, BlockPos blockpos)
/*    */   {
/* 28 */     if (!worldIn.isRemote) {}
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Items/ItemStuntPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */