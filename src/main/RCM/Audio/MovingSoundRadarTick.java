/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.GlobalEntity;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ 
/*    */ public class MovingSoundRadarTick
/*    */   extends MovingSound
/*    */ {
/*    */   private final GlobalEntity rcentity;
/* 11 */   private float distance = 0.0F;
/*    */   
/*    */   public MovingSoundRadarTick(GlobalEntity entity)
/*    */   {
/* 15 */     super(ModSoundEvents.tick, SoundCategory.MASTER);
/* 16 */     this.rcentity = entity;
/* 17 */     this.repeat = true;
/* 18 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void update()
/*    */   {
/* 25 */     if ((this.rcentity == null) || (this.rcentity.isDead))
/*    */     {
/* 27 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 31 */       this.xPosF = ((float)this.rcentity.posX);
/* 32 */       this.yPosF = ((float)this.rcentity.posY);
/* 33 */       this.zPosF = ((float)this.rcentity.posZ);
/*    */       
/* 35 */       if ((this.rcentity.weaponLock) && (this.rcentity.lockProgress < 1.0F))
/*    */       {
/* 37 */         this.pitch = 1.0F;
/* 38 */         this.volume = 0.3F;
/*    */       }
/*    */       else
/*    */       {
/* 42 */         this.pitch = 0.0F;
/* 43 */         this.volume = 0.0F;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundRadarTick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */