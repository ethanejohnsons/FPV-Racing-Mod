/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.GlobalEntity;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ 
/*    */ public class MessageCanTeleportPlayer implements IMessage, IMessageHandler<MessageCanTeleportPlayer, IMessage>
/*    */ {
/*    */   public boolean teleport;
/*    */   public int ID;
/*    */   
/*    */   public MessageCanTeleportPlayer() {}
/*    */   
/*    */   public MessageCanTeleportPlayer(int entityID, boolean tp)
/*    */   {
/* 22 */     this.ID = entityID;
/* 23 */     this.teleport = tp;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 29 */     buf.writeInt(this.ID);
/* 30 */     buf.writeBoolean(this.teleport);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 36 */     this.ID = buf.readInt();
/* 37 */     this.teleport = buf.readBoolean();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageCanTeleportPlayer message, MessageContext ctx)
/*    */   {
/* 43 */     List entityList = null;
/* 44 */     Entity entity = null;
/* 45 */     GlobalEntity nwEntity = null;
/*    */     
/* 47 */     entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     
/* 49 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 51 */       entity = (Entity)entityList.get(i);
/*    */       
/* 53 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 55 */         nwEntity = (GlobalEntity)entity;
/* 56 */         nwEntity.inTeleportMode = message.teleport;
/*    */       }
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageCanTeleportPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */