/*    */ package RCM;
/*    */ 
/*    */ import net.minecraft.client.audio.SoundHandler;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import net.minecraftforge.fml.server.FMLServerHandler;
/*    */ 
/*    */ public class CommonProxy
/*    */ {
/*    */   public void registerRenderInformation() {}
/*    */   
/*    */   public void registerTick() {}
/*    */   
/*    */   public void registerKeyHandler() {}
/*    */   
/*    */   public void registerItemRenders() {}
/*    */   
/*    */   public World getClientWorld()
/*    */   {
/* 22 */     return null;
/*    */   }
/*    */   
/*    */   public World getServerWorld()
/*    */   {
/* 27 */     return FMLServerHandler.instance().getServer().getEntityWorld();
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public SoundHandler getSoundHandler()
/*    */   {
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   public String getPropertiesFilePath()
/*    */   {
/* 38 */     return FMLServerHandler.instance().getServer().getFile("").getAbsolutePath();
/*    */   }
/*    */   
/*    */   public void loadItemModels() {}
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/CommonProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */