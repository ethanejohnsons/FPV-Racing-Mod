/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntityMissile;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovingSoundMissile
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityMissile rcentity;
/* 18 */   private float distance = 0.0F;
/* 19 */   private float preThrottle = 0.0F;
/* 20 */   private int ticks = 5;
/* 21 */   private float lastPitch = 0.0F;
/* 22 */   private float lastVol = 0.0F;
/*    */   
/*    */   public MovingSoundMissile(EntityMissile entity)
/*    */   {
/* 26 */     super(ModSoundEvents.missile, SoundCategory.MASTER);
/* 27 */     this.rcentity = entity;
/* 28 */     this.repeat = true;
/* 29 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void update()
/*    */   {
/* 36 */     if ((this.rcentity == null) || (this.rcentity.isDead))
/*    */     {
/* 38 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 42 */       this.xPosF = ((float)this.rcentity.posX);
/* 43 */       this.yPosF = ((float)this.rcentity.posY);
/* 44 */       this.zPosF = ((float)this.rcentity.posZ);
/*    */       
/*    */ 
/* 47 */       if (this.rcentity.rocketMotorActive == true)
/*    */       {
/* 49 */         this.pitch = 1.25F;
/* 50 */         this.volume = 1.0F;
/*    */       }
/*    */       else
/*    */       {
/* 54 */         this.pitch = 0.0F;
/* 55 */         this.volume = 0.0F;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */