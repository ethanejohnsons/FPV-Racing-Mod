/*    */ package RCM.Items;
/*    */ 
/*    */ import RCM.Audio.ModSoundEvents;
/*    */ import RCM.KeyHandler;
/*    */ import RCM.RCM_Main;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.registries.IForgeRegistry;
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
/*    */ public class ItemRemoteControl
/*    */   extends Item
/*    */ {
/*    */   public boolean state;
/*    */   
/*    */   public ItemRemoteControl()
/*    */   {
/* 33 */     setRegistryName("item_remotecontrol");
/* 34 */     ForgeRegistries.ITEMS.register(this);
/* 35 */     setTranslationKey("item_remotecontrol");
/* 36 */     setCreativeTab(RCM_Main.tab);
/* 37 */     this.maxStackSize = 1;
/* 38 */     this.hasSubtypes = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand)
/*    */   {
/* 44 */     if (world.isRemote)
/*    */     {
/* 46 */       if (!this.state)
/*    */       {
/* 48 */         entityplayer.playSound(ModSoundEvents.remoteon, 0.8F, 1.0F);
/* 49 */         KeyHandler.resetControls();
/* 50 */         this.state = true;
/*    */       }
/*    */       else
/*    */       {
/* 54 */         entityplayer.playSound(ModSoundEvents.remoteoff, 0.8F, 1.0F);
/* 55 */         this.state = false;
/*    */       }
/*    */     }
/*    */     
/* 59 */     return new ActionResult(EnumActionResult.PASS, entityplayer.getHeldItem(hand));
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Items/ItemRemoteControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */