/*    */ package RCM.Items;
/*    */ 
/*    */ import RCM.RCM_Main;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.registries.IForgeRegistry;
/*    */ 
/*    */ public class ItemMissile extends Item
/*    */ {
/*    */   public ItemMissile()
/*    */   {
/* 12 */     setRegistryName("item_missile");
/* 13 */     ForgeRegistries.ITEMS.register(this);
/* 14 */     setTranslationKey("item_missile");
/* 15 */     setCreativeTab(RCM_Main.tab);
/* 16 */     this.maxStackSize = 64;
/* 17 */     this.hasSubtypes = false;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Items/ItemMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */