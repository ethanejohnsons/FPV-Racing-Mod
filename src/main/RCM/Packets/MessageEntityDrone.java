/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.EntityDrone;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageEntityDrone implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageEntityDrone, IMessage>
/*    */ {
/*    */   public int ID;
/* 17 */   public float[] netState = new float[8];
/*    */   
/*    */   public MessageEntityDrone() {}
/*    */   
/*    */   public MessageEntityDrone(int entityID, float[] newState)
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
/* 38 */     buf.writeFloat(this.netState[7]);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 44 */     this.ID = buf.readInt();
/* 45 */     this.netState[0] = buf.readFloat();
/* 46 */     this.netState[1] = buf.readFloat();
/* 47 */     this.netState[2] = buf.readFloat();
/* 48 */     this.netState[3] = buf.readFloat();
/* 49 */     this.netState[4] = buf.readFloat();
/* 50 */     this.netState[5] = buf.readFloat();
/* 51 */     this.netState[6] = buf.readFloat();
/* 52 */     this.netState[7] = buf.readFloat();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntityDrone message, MessageContext ctx)
/*    */   {
/* 58 */     List entityList = null;
/*    */     
/*    */ 
/* 61 */     if (ctx.side.isClient())
/*    */     {
/* 63 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     }
/* 65 */     else if (ctx.side.isServer())
/*    */     {
/* 67 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 70 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 72 */       Entity entity = (Entity)entityList.get(i);
/*    */       
/* 74 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 76 */         EntityDrone oentity = (EntityDrone)entity;
/* 77 */         oentity.additionalInfoUpdate(message.netState);
/*    */       }
/*    */     }
/* 80 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntityDrone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */