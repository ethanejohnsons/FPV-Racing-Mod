/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntityDrone;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MovingSoundDroneHigh
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityDrone entityDrone;
/* 19 */   private float preMotorSpeed = 0.0F;
/*    */   private int motorSound;
/* 21 */   private int ticks = 5;
/* 22 */   private float lastPitch = 0.0F;
/* 23 */   private float lastVol = 0.0F;
/*    */   
/*    */   public MovingSoundDroneHigh(EntityDrone rcOcto, int motor)
/*    */   {
/* 27 */     super(ModSoundEvents.motorhigh1, SoundCategory.MASTER);
/* 28 */     this.entityDrone = rcOcto;
/* 29 */     this.repeat = true;
/* 30 */     this.repeatDelay = 0;
/* 31 */     this.motorSound = motor;
/*    */   }
/*    */   
/*    */ 
/*    */   public void update()
/*    */   {
/* 37 */     if (this.entityDrone.isDead)
/*    */     {
/* 39 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 43 */       float motorSpeed = 0.0F;
/*    */       
/* 45 */       float devide = 180.0F;
/*    */       
/* 47 */       switch (this.motorSound)
/*    */       {
/*    */ 
/*    */       case 0: 
/* 51 */         motorSpeed = this.entityDrone.state[4] / devide;
/* 52 */         break;
/*    */       case 1: 
/* 54 */         motorSpeed = this.entityDrone.state[5] / devide + 0.015F;
/* 55 */         break;
/*    */       case 2: 
/* 57 */         motorSpeed = this.entityDrone.state[6] / devide + 0.01F;
/* 58 */         break;
/*    */       case 3: 
/* 60 */         motorSpeed = this.entityDrone.state[7] / devide + 0.005F;
/*    */       }
/*    */       
/*    */       
/* 64 */       Random rand = new Random();
/* 65 */       float diff = rand.nextFloat();
/*    */       
/* 67 */       this.xPosF = ((float)this.entityDrone.posX);
/* 68 */       this.yPosF = ((float)this.entityDrone.posY);
/* 69 */       this.zPosF = ((float)this.entityDrone.posZ);
/*    */       
/* 71 */       float pitch = 0.2F + 0.6F * motorSpeed;
/*    */       
/* 73 */       float vol = 0.15F * motorSpeed * motorSpeed;
/* 74 */       vol = Math.min(0.3F, vol);
/*    */       
/* 76 */       if (motorSpeed > 0.1F)
/*    */       {
/* 78 */         this.pitch = (pitch + this.motorSound * 0.01F * diff);
/* 79 */         this.volume = vol;
/* 80 */         this.ticks = 0;
/*    */         
/* 82 */         this.lastPitch = (pitch + this.motorSound * 0.01F * diff);
/* 83 */         this.lastVol = vol;
/*    */       }
/* 85 */       else if (this.ticks < 3)
/*    */       {
/* 87 */         this.pitch = this.lastPitch;
/* 88 */         this.volume = this.lastVol;
/* 89 */         this.ticks += 1;
/*    */       }
/*    */       else
/*    */       {
/* 93 */         this.pitch = 0.0F;
/* 94 */         this.volume = 0.0F;
/*    */       }
/*    */       
/* 97 */       this.preMotorSpeed = motorSpeed;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundDroneHigh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */