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
/*    */ public class MovingSoundTrainerPlaneLow
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityTrainerPlane entityPlane;
/* 16 */   private float distance = 0.0F;
/*    */   
/*    */   public MovingSoundTrainerPlaneLow(EntityTrainerPlane rcplane)
/*    */   {
/* 20 */     super(ModSoundEvents.motorlow, SoundCategory.MASTER);
/* 21 */     this.entityPlane = rcplane;
/* 22 */     this.repeat = true;
/* 23 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void update()
/*    */   {
/* 30 */     if ((this.entityPlane == null) || (this.entityPlane.isDead))
/*    */     {
/* 32 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 36 */       this.xPosF = ((float)this.entityPlane.posX);
/* 37 */       this.yPosF = ((float)this.entityPlane.posY);
/* 38 */       this.zPosF = ((float)this.entityPlane.posZ);
/*    */       
/* 40 */       float motorSpeed = this.entityPlane.state[0] / 1400.0F;
/*    */       
/* 42 */       if ((motorSpeed < 0.4F) && (motorSpeed > 0.001D))
/*    */       {
/* 44 */         this.pitch = 0.9F;
/* 45 */         this.volume = 0.4F;
/*    */       }
/*    */       else
/*    */       {
/* 49 */         this.pitch = 0.0F;
/* 50 */         this.volume = 0.0F;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundTrainerPlaneLow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */