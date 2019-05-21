/*    */ package RCM.Items;
/*    */ 
/*    */ import RCM.RCM_Main;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.registries.IForgeRegistry;
/*    */ 
/*    */ public class ItemHeliBody extends Item
/*    */ {
/*    */   public ItemHeliBody()
/*    */   {
/* 12 */     setRegistryName("item_heli_body");
/* 13 */     ForgeRegistries.ITEMS.register(this);
/* 14 */     setTranslationKey("item_heli_body");
/* 15 */     setCreativeTab(RCM_Main.tab);
/* 16 */     this.maxStackSize = 1;
/* 17 */     this.hasSubtypes = false;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Items/ItemHeliBody.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */