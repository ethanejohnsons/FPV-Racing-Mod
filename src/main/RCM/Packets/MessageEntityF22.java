/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.EntityF22;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageEntityF22 implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageEntityF22, IMessage>
/*    */ {
/* 16 */   private float[] netState = new float[14];
/*    */   private int ID;
/*    */   
/*    */   public MessageEntityF22() {}
/*    */   
/*    */   public MessageEntityF22(int entityID, float[] state)
/*    */   {
/* 23 */     this.ID = entityID;
/* 24 */     this.netState = state;
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
/* 39 */     buf.writeFloat(this.netState[8]);
/* 40 */     buf.writeFloat(this.netState[9]);
/* 41 */     buf.writeFloat(this.netState[10]);
/* 42 */     buf.writeFloat(this.netState[11]);
/* 43 */     buf.writeFloat(this.netState[12]);
/* 44 */     buf.writeFloat(this.netState[13]);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 50 */     this.ID = buf.readInt();
/* 51 */     this.netState[0] = buf.readFloat();
/* 52 */     this.netState[1] = buf.readFloat();
/* 53 */     this.netState[2] = buf.readFloat();
/* 54 */     this.netState[3] = buf.readFloat();
/* 55 */     this.netState[4] = buf.readFloat();
/* 56 */     this.netState[5] = buf.readFloat();
/* 57 */     this.netState[6] = buf.readFloat();
/* 58 */     this.netState[7] = buf.readFloat();
/* 59 */     this.netState[8] = buf.readFloat();
/* 60 */     this.netState[9] = buf.readFloat();
/* 61 */     this.netState[10] = buf.readFloat();
/* 62 */     this.netState[11] = buf.readFloat();
/* 63 */     this.netState[12] = buf.readFloat();
/* 64 */     this.netState[13] = buf.readFloat();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageEntityF22 message, MessageContext ctx)
/*    */   {
/* 70 */     List entityList = null;
/*    */     
/*    */ 
/* 73 */     if (ctx.side.isClient())
/*    */     {
/* 75 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */     }
/* 77 */     else if (ctx.side.isServer())
/*    */     {
/* 79 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */     }
/*    */     
/* 82 */     for (int i = 0; i < entityList.size(); i++)
/*    */     {
/* 84 */       Entity entity = (Entity)entityList.get(i);
/*    */       
/* 86 */       if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */       {
/* 88 */         EntityF22 pentity = (EntityF22)entity;
/* 89 */         pentity.additionalInfoUpdate(message.netState);
/*    */       }
/*    */     }
/*    */     
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageEntityF22.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */