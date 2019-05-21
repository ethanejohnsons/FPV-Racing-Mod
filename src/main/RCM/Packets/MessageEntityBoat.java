/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.EntityBoat;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageEntityBoat implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageEntityBoat, IMessage>
/*    */ {
/* 15 */   private float[] netState = new float[2];
/*    */   private int ID;
/*    */   
/*    */   public MessageEntityBoat() {}
/*    */   
/*    */   public MessageEntityBoat(int entityID, float[] state)
/*    */   {
/* 22 */     this.ID = entityID;
/* 23 */     this.netState = state;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 29 */     buf.writeInt(this.ID);
/* 30 */     buf.writeFloat(this.netState[0]);
/* 31 */     buf.writeFloat(this.netState[1]);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 37 */     this.ID = buf.readInt();
/* 38 */     this.netState[0] = buf.readFloat();
/* 39 */     this.netState[1] = buf.readFloat();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntityBoat message, MessageContext ctx)
/*    */   {
/* 45 */     List entityList = null;
/*    */     
/*    */ 
/* 48 */     if (ctx.side.isClient())
/*    */     {
/* 50 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     }
/* 52 */     else if (ctx.side.isServer())
/*    */     {
/* 54 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 57 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 59 */       Entity entity = (Entity)entityList.get(i);
/*    */       
/* 61 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 63 */         EntityBoat pentity = (EntityBoat)entity;
/* 64 */         pentity.additionalInfoUpdate(message.netState);
/*    */       }
/*    */     }
/*    */     
/* 68 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntityBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */