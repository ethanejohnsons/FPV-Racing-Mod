/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.entity.player.PlayerCapabilities;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ 
/*    */ public class MessageTeleportPlayer implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageTeleportPlayer, IMessage>
/*    */ {
/*    */   public int ID;
/*    */   public double posX;
/*    */   public double posY;
/*    */   public double posZ;
/*    */   
/*    */   public MessageTeleportPlayer() {}
/*    */   
/*    */   public MessageTeleportPlayer(int entityID, double pX, double pY, double pZ)
/*    */   {
/* 23 */     this.ID = entityID;
/* 24 */     this.posX = pX;
/* 25 */     this.posY = pY;
/* 26 */     this.posZ = pZ;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 32 */     buf.writeInt(this.ID);
/* 33 */     buf.writeDouble(this.posX);
/* 34 */     buf.writeDouble(this.posY);
/* 35 */     buf.writeDouble(this.posZ);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 41 */     this.ID = buf.readInt();
/* 42 */     this.posX = buf.readDouble();
/* 43 */     this.posY = buf.readDouble();
/* 44 */     this.posZ = buf.readDouble();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageTeleportPlayer message, MessageContext ctx)
/*    */   {
/* 50 */     List entityList = null;
/* 51 */     Entity entity = null;
/* 52 */     EntityPlayerMP entityMP = null;
/*    */     
/* 54 */     entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     
/* 56 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 58 */       entity = (Entity)entityList.get(i);
/*    */       
/* 60 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 62 */         entityMP = (EntityPlayerMP)entity;
/* 63 */         entityMP.connection.setPlayerLocation(message.posX, message.posY, message.posZ, entityMP.rotationYaw, entityMP.rotationPitch);
/*    */         
/* 65 */         if (!entityMP.capabilities.isCreativeMode)
/*    */         {
/* 67 */           entityMP.capabilities.allowFlying = false;
/*    */         }
/*    */       }
/*    */     }
/* 71 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageTeleportPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */