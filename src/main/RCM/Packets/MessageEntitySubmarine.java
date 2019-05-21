/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.EntitySubmarine;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageEntitySubmarine implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageEntitySubmarine, IMessage>
/*    */ {
/* 16 */   private float[] netState = new float[5];
/*    */   private int ID;
/*    */   
/*    */   public MessageEntitySubmarine() {}
/*    */   
/*    */   public MessageEntitySubmarine(int entityID, float[] newState)
/*    */   {
/* 23 */     this.ID = entityID;
/* 24 */     this.netState = newState;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 30 */     buf.writeInt(this.ID);
/* 31 */     buf.writeFloat(this.netState[0]);
/* 32 */     buf.writeFloat(this.netState[1]);
/* 33 */     buf.writeFloat(this.netState[2]);
/* 34 */     buf.writeFloat(this.netState[3]);
/* 35 */     buf.writeFloat(this.netState[4]);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 41 */     this.ID = buf.readInt();
/* 42 */     this.netState[0] = buf.readFloat();
/* 43 */     this.netState[1] = buf.readFloat();
/* 44 */     this.netState[2] = buf.readFloat();
/* 45 */     this.netState[3] = buf.readFloat();
/* 46 */     this.netState[4] = buf.readFloat();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntitySubmarine message, MessageContext ctx)
/*    */   {
/* 52 */     List entityList = null;
/*    */     
/*    */ 
/* 55 */     if (ctx.side.isClient())
/*    */     {
/* 57 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     }
/* 59 */     else if (ctx.side.isServer())
/*    */     {
/* 61 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 64 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 66 */       Entity entity = (Entity)entityList.get(i);
/*    */       
/* 68 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 70 */         EntitySubmarine pentity = (EntitySubmarine)entity;
/* 71 */         pentity.additionalInfoUpdate(message.netState);
/*    */       }
/*    */     }
/*    */     
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntitySubmarine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */