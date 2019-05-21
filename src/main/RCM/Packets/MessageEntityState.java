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
/*    */ public class MessageEntityState implements IMessage, IMessageHandler<MessageEntityState, IMessage>
/*    */ {
/*    */   public int ID;
/*    */   public int playerID;
/*    */   public boolean activate;
/*    */   
/*    */   public MessageEntityState() {}
/*    */   
/*    */   public MessageEntityState(int entityID, int playerEntityID, boolean state)
/*    */   {
/* 23 */     this.ID = entityID;
/* 24 */     this.playerID = playerEntityID;
/* 25 */     this.activate = state;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 31 */     buf.writeInt(this.ID);
/* 32 */     buf.writeInt(this.playerID);
/* 33 */     buf.writeBoolean(this.activate);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 39 */     this.ID = buf.readInt();
/* 40 */     this.playerID = buf.readInt();
/* 41 */     this.activate = buf.readBoolean();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntityState message, MessageContext ctx)
/*    */   {
/* 47 */     List entityList = null;
/* 48 */     Entity entity = null;
/* 49 */     GlobalEntity nwEntity = null;
/*    */     
/* 51 */     entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     
/* 53 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 55 */       entity = (Entity)entityList.get(i);
/*    */       
/* 57 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 59 */         nwEntity = (GlobalEntity)entity;
/* 60 */         nwEntity.packetSetState(message.playerID, message.activate);
/*    */       }
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntityState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */