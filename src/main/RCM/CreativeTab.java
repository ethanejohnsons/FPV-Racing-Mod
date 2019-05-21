/*    */ package RCM;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreativeTab
/*    */   extends CreativeTabs
/*    */ {
/*    */   public CreativeTab(String label)
/*    */   {
/* 15 */     super(label);
/*    */   }
/*    */   
/*    */ 
/*    */   public ItemStack createIcon()
/*    */   {
/* 21 */     return new ItemStack(RCM_Main.item_trainerplane);
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public String getTranslationKey()
/*    */   {
/* 28 */     return getTabLabel();
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/CreativeTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */