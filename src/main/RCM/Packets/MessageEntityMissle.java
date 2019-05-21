/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.EntityMissile;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageEntityMissle implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageEntityMissle, IMessage>
/*    */ {
/*    */   public int ID;
/*    */   public float posX;
/*    */   public float posY;
/*    */   public float posZ;
/*    */   
/*    */   public MessageEntityMissle() {}
/*    */   
/*    */   public MessageEntityMissle(int entityID, float x, float y, float z)
/*    */   {
/* 24 */     this.ID = entityID;
/* 25 */     this.posX = x;
/* 26 */     this.posY = y;
/* 27 */     this.posZ = z;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 33 */     buf.writeInt(this.ID);
/* 34 */     buf.writeFloat(this.posX);
/* 35 */     buf.writeFloat(this.posY);
/* 36 */     buf.writeFloat(this.posZ);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 42 */     this.ID = buf.readInt();
/* 43 */     this.posX = buf.readFloat();
/* 44 */     this.posY = buf.readFloat();
/* 45 */     this.posZ = buf.readFloat();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntityMissle message, MessageContext ctx)
/*    */   {
/* 51 */     List entityList = null;
/*    */     
/*    */ 
/* 54 */     if (ctx.side.isServer())
/*    */     {
/* 56 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 59 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 61 */       Entity entity = (Entity)entityList.get(i);
/*    */       
/* 63 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 65 */         EntityMissile pentity = (EntityMissile)entity;
/*    */         
/* 67 */         pentity.registerExplosion(message.posX, message.posY, message.posZ);
/*    */       }
/*    */     }
/*    */     
/* 71 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntityMissle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */