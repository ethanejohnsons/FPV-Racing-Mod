/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntityTrainerPlane;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MovingSoundTrainerPlaneHigh
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityTrainerPlane entityPlane;
/* 16 */   private float distance = 0.0F;
/* 17 */   private float preMotorSpeed = 0.0F;
/* 18 */   private int ticks = 5;
/* 19 */   private float lastPitch = 0.0F;
/* 20 */   private float lastVol = 0.0F;
/*    */   
/*    */ 
/*    */   public MovingSoundTrainerPlaneHigh(EntityTrainerPlane rcplane)
/*    */   {
/* 25 */     super(ModSoundEvents.motorhigh, SoundCategory.MASTER);
/* 26 */     this.entityPlane = rcplane;
/* 27 */     this.repeat = true;
/* 28 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void update()
/*    */   {
/* 34 */     if ((this.entityPlane == null) || (this.entityPlane.isDead))
/*    */     {
/* 36 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 40 */       this.xPosF = ((float)this.entityPlane.posX);
/* 41 */       this.yPosF = ((float)this.entityPlane.posY);
/* 42 */       this.zPosF = ((float)this.entityPlane.posZ);
/*    */       
/* 44 */       float motorSpeed = this.entityPlane.state[0] / 1050.0F;
/*    */       
/* 46 */       float pitch = 0.2F + 0.7F * motorSpeed;
/*    */       
/* 48 */       float vol = 1.1F * motorSpeed * motorSpeed - 0.1F;
/* 49 */       vol = Math.min(0.6F, vol);
/*    */       
/* 51 */       if (motorSpeed > 0.1F)
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


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundTrainerPlaneHigh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */