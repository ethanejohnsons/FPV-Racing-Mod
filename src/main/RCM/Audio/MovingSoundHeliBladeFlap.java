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
/*    */ public class MovingSoundHeliBladeFlap
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityHeli entityHeli;
/* 16 */   private float distance = 0.0F;
/* 17 */   private float preMotorSpeed = 0.0F;
/* 18 */   private int ticks = 5;
/* 19 */   private float lastPitch = 0.0F;
/* 20 */   private float lastVol = 0.0F;
/*    */   
/*    */   public MovingSoundHeliBladeFlap(EntityHeli rcplane)
/*    */   {
/* 24 */     super(ModSoundEvents.helibladeflap, SoundCategory.MASTER);
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
/* 43 */       float motorSpeed = this.entityHeli.state[0] / 90.0F;
/* 44 */       float bladeFlap = this.entityHeli.state[1] / 4.0F;
/*    */       
/* 46 */       float pitch = 0.2F + 0.7F * motorSpeed;
/*    */       
/* 48 */       float vol = 0.0F;
/*    */       
/* 50 */       if (motorSpeed > 1.5F)
/*    */       {
/* 52 */         vol = 0.5F * bladeFlap * bladeFlap - 0.2F;
/*    */       }
/*    */       
/* 55 */       vol = Math.min(0.4F, vol);
/*    */       
/* 57 */       if (motorSpeed > 0.1F)
/*    */       {
/* 59 */         this.pitch = pitch;
/* 60 */         this.volume = vol;
/* 61 */         this.ticks = 0;
/*    */         
/* 63 */         this.lastPitch = pitch;
/* 64 */         this.lastVol = vol;
/*    */       }
/* 66 */       else if (this.ticks < 3)
/*    */       {
/* 68 */         this.pitch = this.lastPitch;
/* 69 */         this.volume = this.lastVol;
/* 70 */         this.ticks += 1;
/*    */       }
/*    */       else
/*    */       {
/* 74 */         this.pitch = 0.0F;
/* 75 */         this.volume = 0.0F;
/*    */       }
/*    */       
/* 78 */       this.preMotorSpeed = motorSpeed;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundHeliBladeFlap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */