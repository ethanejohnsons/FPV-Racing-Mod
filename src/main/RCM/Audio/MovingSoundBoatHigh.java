/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntityBoat;
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
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MovingSoundBoatHigh
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityBoat entityBoat;
/* 21 */   private float distance = 0.0F;
/* 22 */   private float preMotorSpeed = 0.0F;
/* 23 */   private int ticks = 5;
/* 24 */   private float lastPitch = 0.0F;
/* 25 */   private float lastVol = 0.0F;
/*    */   
/*    */   private int motor;
/*    */   
/*    */   public MovingSoundBoatHigh(EntityBoat rcboat)
/*    */   {
/* 31 */     super(ModSoundEvents.motorhigh, SoundCategory.MASTER);
/* 32 */     this.entityBoat = rcboat;
/* 33 */     this.repeat = true;
/* 34 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void update()
/*    */   {
/* 40 */     if ((this.entityBoat == null) || (this.entityBoat.isDead))
/*    */     {
/* 42 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 46 */       this.xPosF = ((float)this.entityBoat.posX);
/* 47 */       this.yPosF = ((float)this.entityBoat.posY);
/* 48 */       this.zPosF = ((float)this.entityBoat.posZ);
/*    */       
/* 50 */       float motorSpeed = this.entityBoat.state[0] / 1600.0F;
/*    */       
/* 52 */       Random rand = new Random();
/* 53 */       float diff = rand.nextFloat();
/*    */       
/* 55 */       float pitch = 0.2F + 0.7F * motorSpeed;
/*    */       
/* 57 */       float vol = 0.8F * motorSpeed * motorSpeed - 0.1F;
/* 58 */       vol = Math.min(0.3F, vol);
/*    */       
/* 60 */       if (motorSpeed > 0.1F)
/*    */       {
/* 62 */         this.pitch = (pitch + 0.05F * diff);
/* 63 */         this.volume = vol;
/* 64 */         this.ticks = 0;
/*    */         
/* 66 */         this.lastPitch = (pitch + 0.05F * diff);
/* 67 */         this.lastVol = vol;
/*    */       }
/* 69 */       else if (this.ticks < 3)
/*    */       {
/* 71 */         this.pitch = this.lastPitch;
/* 72 */         this.volume = this.lastVol;
/* 73 */         this.ticks += 1;
/*    */       }
/*    */       else
/*    */       {
/* 77 */         this.pitch = 0.0F;
/* 78 */         this.volume = 0.0F;
/*    */       }
/*    */       
/* 81 */       this.preMotorSpeed = motorSpeed;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundBoatHigh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */