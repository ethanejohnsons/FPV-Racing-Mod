/*    */ package RCM.Physics;
/*    */ 
/*    */ import RCM.Entities.GlobalEntity;
/*    */ import RCM.Packets.MessageHandler;
/*    */ import RCM.Packets.MessageSpawnWeapon;
/*    */ import javax.annotation.Nullable;
/*    */ import javax.vecmath.AxisAngle4f;
/*    */ import javax.vecmath.Quat4f;
/*    */ import javax.vecmath.Vector3f;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttachmentHandler
/*    */ {
/*    */   private Vector3f position;
/*    */   private boolean isEmpty;
/*    */   private GlobalEntity aentity;
/*    */   private GlobalEntity ventity;
/*    */   private PhysicsHelper helper;
/*    */   private int pylonNumber;
/*    */   private float deltaTime;
/*    */   private AxisAngle4f rotate;
/*    */   
/*    */   public AttachmentHandler(Vector3f pos, AxisAngle4f rot, int type, int place, GlobalEntity gEntity)
/*    */   {
/* 32 */     this.helper = new PhysicsHelper();
/*    */     
/* 34 */     this.position = pos;
/* 35 */     this.isEmpty = true;
/* 36 */     this.pylonNumber = place;
/* 37 */     this.ventity = gEntity;
/* 38 */     this.deltaTime = 0.0F;
/*    */     
/* 40 */     this.rotate = new AxisAngle4f(rot);
/*    */   }
/*    */   
/*    */   public void setMotionState(Quat4f localQuat, Vector3f linearVelocity, Vector3f pos, float par, int ID)
/*    */   {
/* 45 */     Vector3f gPos = this.helper.rotateVector(localQuat, this.position);
/* 46 */     gPos.add(pos);
/*    */     
/* 48 */     if ((this.isEmpty) && (this.deltaTime <= 0.0F))
/*    */     {
/*    */ 
/* 51 */       MessageHandler.handler.sendToServer(new MessageSpawnWeapon(ID, this.pylonNumber, 0));
/* 52 */       this.deltaTime = 10.0F;
/*    */ 
/*    */     }
/* 55 */     else if (this.isEmpty)
/*    */     {
/* 57 */       this.deltaTime -= par;
/*    */     }
/*    */     
/* 60 */     if ((this.aentity != null) && (this.aentity.physicsWorld != null))
/*    */     {
/* 62 */       Quat4f localRotate = new Quat4f();
/* 63 */       Quat4f finalRotate = new Quat4f();
/*    */       
/* 65 */       localRotate.set(this.rotate);
/* 66 */       finalRotate.mul(localQuat, localRotate);
/*    */       
/* 68 */       this.aentity.physicsWorld.setEntityLinearVelocity(linearVelocity);
/* 69 */       this.aentity.physicsWorld.setEntityMotionState(finalRotate, gPos, 1.0F);
/* 70 */       this.isEmpty = false;
/*    */     }
/*    */   }
/*    */   
/*    */   public void release(@Nullable Entity lockedEntity, @Nullable Vector3f targetLoc)
/*    */   {
/* 76 */     if (this.aentity != null)
/*    */     {
/* 78 */       this.aentity.physicsWorld.setRocketMotorActive();
/* 79 */       this.aentity.physicsWorld.setLockedTarger(lockedEntity);
/* 80 */       this.aentity.physicsWorld.setTargetPosition(targetLoc);
/*    */       
/* 82 */       this.isEmpty = true;
/* 83 */       this.aentity = null;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isEmpty()
/*    */   {
/* 89 */     return this.isEmpty;
/*    */   }
/*    */   
/*    */   public void attach(GlobalEntity gEntity)
/*    */   {
/* 94 */     this.aentity = gEntity;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/AttachmentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */