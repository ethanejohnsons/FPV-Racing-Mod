/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.GlobalEntity;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovingSoundWater
/*    */   extends MovingSound
/*    */ {
/*    */   private final GlobalEntity rcentity;
/* 15 */   private float distance = 0.0F;
/* 16 */   private float preThrottle = 0.0F;
/* 17 */   private int ticks = 5;
/* 18 */   private float lastPitch = 0.0F;
/* 19 */   private float lastVol = 0.0F;
/*    */   
/*    */   public MovingSoundWater(GlobalEntity entity)
/*    */   {
/* 23 */     super(ModSoundEvents.waterflow, SoundCategory.MASTER);
/* 24 */     this.rcentity = entity;
/* 25 */     this.repeat = true;
/* 26 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void update()
/*    */   {
/* 33 */     if ((this.rcentity == null) || (this.rcentity.isDead))
/*    */     {
/* 35 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 39 */       this.xPosF = ((float)this.rcentity.posX);
/* 40 */       this.yPosF = ((float)this.rcentity.posY);
/* 41 */       this.zPosF = ((float)this.rcentity.posZ);
/*    */       
/* 43 */       float airSpeed = this.rcentity.getVelocity() / 15.0F;
/*    */       
/* 45 */       float pitch = 0.1F + 0.7F * airSpeed;
/*    */       
/* 47 */       float vol = 1.1F * airSpeed * airSpeed - 0.1F;
/* 48 */       vol = Math.min(1.0F, vol);
/*    */       
/* 50 */       if (airSpeed > 0.1F)
/*    */       {
/* 52 */         this.pitch = pitch;
/* 53 */         this.volume = vol;
/* 54 */         this.ticks = 0;
/*    */         
/* 56 */         this.lastPitch = pitch;
/* 57 */         this.lastVol = vol;
/*    */       }
/* 59 */       else if (this.ticks < 3)
/*    */       {
/* 61 */         this.pitch = this.lastPitch;
/* 62 */         this.volume = this.lastVol;
/* 63 */         this.ticks += 1;
/*    */       }
/*    */       else
/*    */       {
/* 67 */         this.pitch = 0.0F;
/* 68 */         this.volume = 0.0F;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundWater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */