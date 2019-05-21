/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntityDrone;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MovingSoundOctocopterLow
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityDrone entityOctocopter;
/*    */   private int motorSound;
/*    */   
/*    */   public MovingSoundOctocopterLow(EntityDrone rcOcto, int motor)
/*    */   {
/* 21 */     super(ModSoundEvents.motorlow, SoundCategory.MASTER);
/* 22 */     this.entityOctocopter = rcOcto;
/* 23 */     this.repeat = true;
/* 24 */     this.repeatDelay = 0;
/* 25 */     this.motorSound = motor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void update()
/*    */   {
/* 32 */     if ((this.entityOctocopter == null) || (this.entityOctocopter.isDead))
/*    */     {
/* 34 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 38 */       this.xPosF = ((float)this.entityOctocopter.posX);
/* 39 */       this.yPosF = ((float)this.entityOctocopter.posY);
/* 40 */       this.zPosF = ((float)this.entityOctocopter.posZ);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundOctocopterLow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */