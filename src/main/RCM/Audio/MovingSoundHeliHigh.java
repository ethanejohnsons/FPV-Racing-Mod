/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntityHeli;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MovingSoundHeliHigh
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityHeli entityHeli;
/* 16 */   private float distance = 0.0F;
/* 17 */   private float preMotorSpeed = 0.0F;
/* 18 */   private int ticks = 5;
/* 19 */   private float lastPitch = 0.0F;
/* 20 */   private float lastVol = 0.0F;
/*    */   
/*    */   public MovingSoundHeliHigh(EntityHeli rcplane)
/*    */   {
/* 24 */     super(ModSoundEvents.motorhelihigh, SoundCategory.MASTER);
/* 25 */     this.entityHeli = rcplane;
/* 26 */     this.repeat = true;
/* 27 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void update()
/*    */   {
/* 33 */     if ((this.entityHeli == null) || (this.entityHeli.isDead))
/*    */     {
/* 35 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 39 */       this.xPosF = ((float)this.entityHeli.posX);
/* 40 */       this.yPosF = ((float)this.entityHeli.posY);
/* 41 */       this.zPosF = ((float)this.entityHeli.posZ);
/*    */       
/* 43 */       float motorSpeed = this.entityHeli.state[0] / 70.0F;
/*    */       
/* 45 */       float pitch = 0.2F + 0.7F * motorSpeed;
/*    */       
/* 47 */       float vol = 1.1F * motorSpeed * motorSpeed - 0.1F;
/* 48 */       vol = Math.min(0.6F, vol);
/*    */       
/* 50 */       if (motorSpeed > 0.1F)
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
/*    */       
/* 71 */       this.preMotorSpeed = motorSpeed;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundHeliHigh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */