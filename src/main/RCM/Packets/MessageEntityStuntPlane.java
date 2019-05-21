/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.EntityStuntPlane;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageEntityStuntPlane implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageEntityStuntPlane, IMessage>
/*    */ {
/*    */   public float throttle;
/*    */   public float rudderAngle;
/*    */   public float elevatorAngle;
/*    */   public float aileronAngle;
/*    */   public float netVelocity;
/*    */   public int ID;
/*    */   
/*    */   public MessageEntityStuntPlane() {}
/*    */   
/*    */   public MessageEntityStuntPlane(int entityID, float entityThrottle, float entityRudderAngle, float entityElevatorAngle, float entityAileronAngle, float nVelcoty)
/*    */   {
/* 26 */     this.ID = entityID;
/* 27 */     this.throttle = entityThrottle;
/* 28 */     this.rudderAngle = entityRudderAngle;
/* 29 */     this.elevatorAngle = entityElevatorAngle;
/* 30 */     this.aileronAngle = entityAileronAngle;
/* 31 */     this.netVelocity = nVelcoty;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 37 */     buf.writeInt(this.ID);
/* 38 */     buf.writeFloat(this.throttle);
/* 39 */     buf.writeFloat(this.rudderAngle);
/* 40 */     buf.writeFloat(this.elevatorAngle);
/* 41 */     buf.writeFloat(this.aileronAngle);
/* 42 */     buf.writeFloat(this.netVelocity);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 48 */     this.ID = buf.readInt();
/* 49 */     this.throttle = buf.readFloat();
/* 50 */     this.rudderAngle = buf.readFloat();
/* 51 */     this.elevatorAngle = buf.readFloat();
/* 52 */     this.aileronAngle = buf.readFloat();
/* 53 */     this.netVelocity = buf.readFloat();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntityStuntPlane message, MessageContext ctx)
/*    */   {
/* 59 */     List entityList = null;
/*    */     
/*    */ 
/* 62 */     if (ctx.side.isClient())
/*    */     {
/* 64 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     }
/* 66 */     else if (ctx.side.isServer())
/*    */     {
/* 68 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 71 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 73 */       Entity entity = (Entity)entityList.get(i);
/*    */       
/* 75 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 77 */         EntityStuntPlane pentity = (EntityStuntPlane)entity;
/* 78 */         pentity.additionalInfoUpdate(message.throttle, message.rudderAngle, message.elevatorAngle, message.aileronAngle, message.netVelocity);
/*    */       }
/*    */     }
/*    */     
/* 82 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntityStuntPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */