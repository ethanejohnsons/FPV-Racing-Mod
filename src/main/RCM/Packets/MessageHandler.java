/*    */ package RCM.Packets;
/*    */ 
/*    */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ public class MessageHandler
/*    */ {
/* 10 */   public static final SimpleNetworkWrapper handler = NetworkRegistry.INSTANCE.newSimpleChannel("thercmod".toLowerCase());
/*    */   
/*    */   public static void init()
/*    */   {
/* 14 */     handler.registerMessage(MessageGlobalEntity.class, MessageGlobalEntity.class, 1, Side.CLIENT);
/* 15 */     handler.registerMessage(MessageGlobalEntity.class, MessageGlobalEntity.class, 2, Side.SERVER);
/* 16 */     handler.registerMessage(MessageEntityState.class, MessageEntityState.class, 3, Side.CLIENT);
/* 17 */     handler.registerMessage(MessageEntityTrainerPlane.class, MessageEntityTrainerPlane.class, 4, Side.CLIENT);
/* 18 */     handler.registerMessage(MessageEntityTrainerPlane.class, MessageEntityTrainerPlane.class, 5, Side.SERVER);
/* 19 */     handler.registerMessage(MessageEntityCar.class, MessageEntityCar.class, 6, Side.CLIENT);
/* 20 */     handler.registerMessage(MessageEntityCar.class, MessageEntityCar.class, 7, Side.SERVER);
/* 21 */     handler.registerMessage(MessageEntityF22.class, MessageEntityF22.class, 8, Side.CLIENT);
/* 22 */     handler.registerMessage(MessageEntityF22.class, MessageEntityF22.class, 9, Side.SERVER);
/* 23 */     handler.registerMessage(MessageEntityHeli.class, MessageEntityHeli.class, 10, Side.CLIENT);
/* 24 */     handler.registerMessage(MessageEntityHeli.class, MessageEntityHeli.class, 11, Side.SERVER);
/* 25 */     handler.registerMessage(MessageEntityDrone.class, MessageEntityDrone.class, 12, Side.CLIENT);
/* 26 */     handler.registerMessage(MessageEntityDrone.class, MessageEntityDrone.class, 13, Side.SERVER);
/* 27 */     handler.registerMessage(MessageCanTeleportPlayer.class, MessageCanTeleportPlayer.class, 14, Side.SERVER);
/* 28 */     handler.registerMessage(MessageTeleportPlayer.class, MessageTeleportPlayer.class, 15, Side.SERVER);
/* 29 */     handler.registerMessage(MessageSpawnWeapon.class, MessageSpawnWeapon.class, 16, Side.CLIENT);
/* 30 */     handler.registerMessage(MessageSpawnWeapon.class, MessageSpawnWeapon.class, 17, Side.SERVER);
/* 31 */     handler.registerMessage(MessageEntityBoat.class, MessageEntityBoat.class, 18, Side.CLIENT);
/* 32 */     handler.registerMessage(MessageEntityBoat.class, MessageEntityBoat.class, 19, Side.SERVER);
/* 33 */     handler.registerMessage(MessageEntitySubmarine.class, MessageEntitySubmarine.class, 20, Side.CLIENT);
/* 34 */     handler.registerMessage(MessageEntitySubmarine.class, MessageEntitySubmarine.class, 21, Side.SERVER);
/* 35 */     handler.registerMessage(MessageEntityMissle.class, MessageEntityMissle.class, 22, Side.SERVER);
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */