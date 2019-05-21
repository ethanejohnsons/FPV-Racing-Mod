/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntityCar;
/*    */ import RCM.Physics.PhysicsHandler;
/*    */ import com.bulletphysics.dynamics.vehicle.RaycastVehicle;
/*    */ import com.bulletphysics.dynamics.vehicle.WheelInfo;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MovingSoundCarWheel extends MovingSound
/*    */ {
/*    */   private final EntityCar entityCar;
/* 16 */   private float distance = 0.0F;
/* 17 */   private float preMotorSpeed = 0.0F;
/* 18 */   private int ticks = 5;
/* 19 */   private float lastPitch = 0.0F;
/* 20 */   private float lastVol = 0.0F;
/*    */   
/*    */   public MovingSoundCarWheel(EntityCar rccar)
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
/* 45 */       float pitch = 0.7F + 0.3F * motorSpeed;
/*    */       
/* 47 */       float vol = 2.1F * motorSpeed - 0.1F;
/* 48 */       vol = Math.min(0.2F, vol);
/*    */       
/* 50 */       boolean wheelContact = false;
/*    */       
/* 52 */       if (this.entityCar.physicsWorld != null)
/*    */       {
/* 54 */         for (int i = 0; i < this.entityCar.physicsWorld.wheels.size(); i++)
/*    */         {
/* 56 */           if (((WheelInfo)this.entityCar.physicsWorld.vehicle.wheelInfo.get(0)).wheelsSuspensionForce > 0.0F)
/*    */           {
/* 58 */             wheelContact = true;
/*    */           }
/*    */         }
/*    */       }
/*    */       
/* 63 */       if (!wheelContact)
/*    */       {
/* 65 */         vol = 0.0F;
/*    */       }
/*    */       
/* 68 */       if (motorSpeed > 0.01F)
/*    */       {
/* 70 */         this.pitch = pitch;
/* 71 */         this.volume = vol;
/* 72 */         this.ticks = 0;
/*    */         
/* 74 */         this.lastPitch = pitch;
/* 75 */         this.lastVol = vol;
/*    */       }
/* 77 */       else if (this.ticks < 3)
/*    */       {
/* 79 */         this.pitch = this.lastPitch;
/* 80 */         this.volume = this.lastVol;
/* 81 */         this.ticks += 1;
/*    */       }
/*    */       else
/*    */       {
/* 85 */         this.pitch = 0.0F;
/* 86 */         this.volume = 0.0F;
/*    */       }
/*    */       
/* 89 */       this.preMotorSpeed = motorSpeed;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundCarWheel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */