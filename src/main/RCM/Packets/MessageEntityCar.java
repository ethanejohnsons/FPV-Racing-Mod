/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.EntityCar;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageEntityCar implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageEntityCar, IMessage>
/*    */ {
/*    */   public int ID;
/* 16 */   private float[] netState = new float[10];
/*    */   
/*    */   public MessageEntityCar() {}
/*    */   
/*    */   public MessageEntityCar(int entityID, float[] state)
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
/* 32 */     buf.writeFloat(this.netState[2]);
/* 33 */     buf.writeFloat(this.netState[3]);
/* 34 */     buf.writeFloat(this.netState[4]);
/* 35 */     buf.writeFloat(this.netState[5]);
/* 36 */     buf.writeFloat(this.netState[6]);
/* 37 */     buf.writeFloat(this.netState[7]);
/* 38 */     buf.writeFloat(this.netState[8]);
/* 39 */     buf.writeFloat(this.netState[9]);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 45 */     this.ID = buf.readInt();
/* 46 */     this.netState[0] = buf.readFloat();
/* 47 */     this.netState[1] = buf.readFloat();
/* 48 */     this.netState[2] = buf.readFloat();
/* 49 */     this.netState[3] = buf.readFloat();
/* 50 */     this.netState[4] = buf.readFloat();
/* 51 */     this.netState[5] = buf.readFloat();
/* 52 */     this.netState[6] = buf.readFloat();
/* 53 */     this.netState[7] = buf.readFloat();
/* 54 */     this.netState[8] = buf.readFloat();
/* 55 */     this.netState[9] = buf.readFloat();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntityCar message, MessageContext ctx)
/*    */   {
/* 61 */     List entityList = null;
/*    */     
/*    */ 
/* 64 */     if (ctx.side.isClient())
/*    */     {
/* 66 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     }
/* 68 */     else if (ctx.side.isServer())
/*    */     {
/* 70 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 73 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 75 */       Entity entity = (Entity)entityList.get(i);
/*    */       
/* 77 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 79 */         EntityCar pentity = (EntityCar)entity;
/* 80 */         pentity.additionalInfoUpdate(message.netState);
/*    */       }
/*    */     }
/*    */     
/* 84 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntityCar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */