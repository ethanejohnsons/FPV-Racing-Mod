/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.GlobalEntity;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import javax.vecmath.Quat4f;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ 
/*    */ public class MessageGlobalEntity implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageGlobalEntity, IMessage>
/*    */ {
/*    */   public int ID;
/*    */   public Quat4f rotateQuat;
/*    */   public double posX;
/*    */   public double posY;
/*    */   public double posZ;
/*    */   public float throttle;
/*    */   public boolean damaged;
/*    */   
/*    */   public MessageGlobalEntity() {}
/*    */   
/*    */   public MessageGlobalEntity(int entityID, double pX, double pY, double pZ, Quat4f quat, float entityThrottle, boolean damage)
/*    */   {
/* 27 */     this.ID = entityID;
/* 28 */     this.posX = pX;
/* 29 */     this.posY = pY;
/* 30 */     this.posZ = pZ;
/* 31 */     this.rotateQuat = new Quat4f(quat);
/* 32 */     this.throttle = entityThrottle;
/* 33 */     this.damaged = damage;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 39 */     buf.writeInt(this.ID);
/* 40 */     buf.writeDouble(this.posX);
/* 41 */     buf.writeDouble(this.posY);
/* 42 */     buf.writeDouble(this.posZ);
/* 43 */     buf.writeFloat(this.rotateQuat.x);
/* 44 */     buf.writeFloat(this.rotateQuat.y);
/* 45 */     buf.writeFloat(this.rotateQuat.z);
/* 46 */     buf.writeFloat(this.rotateQuat.w);
/* 47 */     buf.writeFloat(this.throttle);
/* 48 */     buf.writeBoolean(this.damaged);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 54 */     this.ID = buf.readInt();
/* 55 */     this.posX = buf.readDouble();
/* 56 */     this.posY = buf.readDouble();
/* 57 */     this.posZ = buf.readDouble();
/* 58 */     this.rotateQuat = new Quat4f(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
/* 59 */     this.throttle = buf.readFloat();
/* 60 */     this.damaged = buf.readBoolean();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageGlobalEntity message, MessageContext ctx)
/*    */   {
/* 66 */     List entityList = null;
/* 67 */     Entity entity = null;
/* 68 */     GlobalEntity nwEntity = null;
/*    */     
/* 70 */     if (ctx.side.isClient())
/*    */     {
/* 72 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     }
/* 74 */     else if (ctx.side.isServer())
/*    */     {
/* 76 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 79 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 81 */       entity = (Entity)entityList.get(i);
/*    */       
/* 83 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 85 */         nwEntity = (GlobalEntity)entity;
/* 86 */         nwEntity.packetUpdate(message.posX, message.posY, message.posZ, message.rotateQuat, message.throttle, message.damaged);
/*    */       }
/*    */     }
/* 89 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageGlobalEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */