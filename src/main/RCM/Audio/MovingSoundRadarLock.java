/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.GlobalEntity;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ 
/*    */ public class MovingSoundRadarLock extends MovingSound
/*    */ {
/*    */   private final GlobalEntity rcentity;
/* 10 */   private float distance = 0.0F;
/*    */   
/*    */   public MovingSoundRadarLock(GlobalEntity entity)
/*    */   {
/* 14 */     super(ModSoundEvents.lock, SoundCategory.MASTER);
/* 15 */     this.rcentity = entity;
/* 16 */     this.repeat = true;
/* 17 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void update()
/*    */   {
/* 24 */     if ((this.rcentity == null) || (this.rcentity.isDead))
/*    */     {
/* 26 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 30 */       this.xPosF = ((float)this.rcentity.posX);
/* 31 */       this.yPosF = ((float)this.rcentity.posY);
/* 32 */       this.zPosF = ((float)this.rcentity.posZ);
/*    */       
/* 34 */       if ((this.rcentity.weaponLock) && (this.rcentity.lockProgress == 1.0F))
/*    */       {
/* 36 */         this.pitch = 1.0F;
/* 37 */         this.volume = 0.3F;
/*    */       }
/*    */       else
/*    */       {
/* 41 */         this.pitch = 0.0F;
/* 42 */         this.volume = 0.0F;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundRadarLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */