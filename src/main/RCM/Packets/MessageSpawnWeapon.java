/*    */ package RCM.Packets;
/*    */ 
/*    */ import RCM.CommonProxy;
/*    */ import RCM.Entities.GlobalEntity;
/*    */ import RCM.Physics.PhysicsHandler;
/*    */ import RCM.RCM_Main;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MessageSpawnWeapon implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<MessageSpawnWeapon, IMessage>
/*    */ {
/*    */   public int ID;
/*    */   public int aID;
/*    */   public int wID;
/*    */   
/*    */   public MessageSpawnWeapon() {}
/*    */   
/*    */   public MessageSpawnWeapon(int entityID, int attachmentID, int weaponID)
/*    */   {
/* 25 */     this.ID = entityID;
/* 26 */     this.aID = attachmentID;
/* 27 */     this.wID = weaponID;
/*    */   }
/*    */   
/*    */ 
/*    */   public void toBytes(ByteBuf buf)
/*    */   {
/* 33 */     buf.writeInt(this.ID);
/* 34 */     buf.writeInt(this.aID);
/* 35 */     buf.writeInt(this.wID);
/*    */   }
/*    */   
/*    */ 
/*    */   public void fromBytes(ByteBuf buf)
/*    */   {
/* 41 */     this.ID = buf.readInt();
/* 42 */     this.aID = buf.readInt();
/* 43 */     this.wID = buf.readInt();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(MessageSpawnWeapon message, MessageContext ctx)
/*    */   {
/* 49 */     List entityList = null;
/* 50 */     Entity entity = null;
/* 51 */     GlobalEntity nwEntity = null;
/* 52 */     GlobalEntity nwEntity2 = null;
/*    */     
/* 54 */     if (ctx.side.isClient())
/*    */     {
/* 56 */       entityList = RCM_Main.proxy.getClientWorld().loadedEntityList;
/*    */       
/* 58 */       for (int i = 0; i < entityList.size(); i++)
/*    */       {
/* 60 */         entity = (Entity)entityList.get(i);
/*    */         
/* 62 */         if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */         {
/* 64 */           nwEntity = (GlobalEntity)entity;
/*    */         }
/* 66 */         else if ((entity != null) && (entity.getEntityId() == message.wID))
/*    */         {
/* 68 */           nwEntity2 = (GlobalEntity)entity;
/*    */         }
/*    */       }
/*    */       
/* 72 */       if ((nwEntity != null) && (nwEntity2 != null))
/*    */       {
/* 74 */         nwEntity.physicsWorld.attachWeapon(message.aID, nwEntity2);
/*    */       }
/*    */     }
/* 77 */     else if (ctx.side.isServer())
/*    */     {
/* 79 */       entityList = RCM_Main.proxy.getServerWorld().loadedEntityList;
/*    */       
/* 81 */       for (int i = 0; i < entityList.size(); i++)
/*    */       {
/* 83 */         entity = (Entity)entityList.get(i);
/*    */         
/* 85 */         if ((entity != null) && (entity.getEntityId() == message.ID))
/*    */         {
/* 87 */           nwEntity = (GlobalEntity)entity;
/* 88 */           nwEntity.registerSpawn(message.aID);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Packets/MessageSpawnWeapon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */