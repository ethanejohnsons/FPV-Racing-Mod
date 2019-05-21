/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntityCar;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MovingSoundCarHigh
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityCar entityCar;
/* 16 */   private float distance = 0.0F;
/* 17 */   private float preMotorSpeed = 0.0F;
/* 18 */   private int ticks = 5;
/* 19 */   private float lastPitch = 0.0F;
/* 20 */   private float lastVol = 0.0F;
/*    */   
/*    */   public MovingSoundCarHigh(EntityCar rccar)
/*    */   {
/* 24 */     super(ModSoundEvents.motorcar, SoundCategory.MASTER);
/* 25 */     this.entityCar = rccar;
/* 26 */     this.repeat = true;
/* 27 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void update()
/*    */   {
/* 33 */     if ((this.entityCar == null) || (this.entityCar.isDead))
/*    */     {
/* 35 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 39 */       this.xPosF = ((float)this.entityCar.posX);
/* 40 */       this.yPosF = ((float)this.entityCar.posY);
/* 41 */       this.zPosF = ((float)this.entityCar.posZ);
/*    */       
/* 43 */       float motorSpeed = Math.abs(this.entityCar.state[0]) / 60.0F;
/*    */       
/* 45 */       float pitch = 0.5F + 0.5F * motorSpeed;
/*    */       
/* 47 */       float vol = 2.1F * motorSpeed * motorSpeed - 0.1F;
/*    */       
/* 49 */       vol = Math.min(0.4F, vol);
/*    */       
/* 51 */       if (motorSpeed > 0.01F)
/*    */       {
/* 53 */         this.pitch = pitch;
/* 54 */         this.volume = vol;
/* 55 */         this.ticks = 0;
/*    */         
/* 57 */         this.lastPitch = pitch;
/* 58 */         this.lastVol = vol;
/*    */       }
/* 60 */       else if (this.ticks < 3)
/*    */       {
/* 62 */         this.pitch = this.lastPitch;
/* 63 */         this.volume = this.lastVol;
/* 64 */         this.ticks += 1;
/*    */       }
/*    */       else
/*    */       {
/* 68 */         this.pitch = 0.0F;
/* 69 */         this.volume = 0.0F;
/*    */       }
/*    */       
/* 72 */       this.preMotorSpeed = motorSpeed;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundCarHigh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */