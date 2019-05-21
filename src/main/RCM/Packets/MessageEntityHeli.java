/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.EntityHeli;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageEntityHeli implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageEntityHeli, IMessage>
/*    */ {
/*    */   private int ID;
/* 17 */   private float[] netState = new float[7];
/*    */   
/*    */   public MessageEntityHeli() {}
/*    */   
/*    */   public MessageEntityHeli(int entityID, float[] newState)
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
/* 36 */     buf.writeFloat(this.netState[5]);
/* 37 */     buf.writeFloat(this.netState[6]);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 43 */     this.ID = buf.readInt();
/* 44 */     this.netState[0] = buf.readFloat();
/* 45 */     this.netState[1] = buf.readFloat();
/* 46 */     this.netState[2] = buf.readFloat();
/* 47 */     this.netState[3] = buf.readFloat();
/* 48 */     this.netState[4] = buf.readFloat();
/* 49 */     this.netState[5] = buf.readFloat();
/* 50 */     this.netState[6] = buf.readFloat();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntityHeli message, MessageContext ctx)
/*    */   {
/* 56 */     List entityList = null;
/*    */     
/*    */ 
/* 59 */     if (ctx.side.isClient())
/*    */     {
/* 61 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     }
/* 63 */     else if (ctx.side.isServer())
/*    */     {
/* 65 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 68 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 70 */       Entity entity = (Entity)entityList.get(i);
/*    */       
/* 72 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 74 */         EntityHeli pentity = (EntityHeli)entity;
/* 75 */         pentity.additionalInfoUpdate(message.netState);
/*    */       }
/*    */     }
/*    */     
/* 79 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntityHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */