/*    */ package RCM.Audio;
/*    */ 
/*    */ import RCM.Entities.EntitySubmarine;
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
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MovingSoundSubPing
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntitySubmarine entitySub;
/* 21 */   private float distance = 0.0F;
/*    */   
/*    */ 
/*    */   public MovingSoundSubPing(EntitySubmarine rcsub)
/*    */   {
/* 26 */     super(ModSoundEvents.subping, SoundCategory.MASTER);
/* 27 */     this.entitySub = rcsub;
/* 28 */     this.repeat = true;
/* 29 */     this.repeatDelay = 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void update()
/*    */   {
/* 35 */     if ((this.entitySub == null) || (this.entitySub.isDead))
/*    */     {
/* 37 */       this.donePlaying = true;
/*    */     }
/*    */     else
/*    */     {
/* 41 */       this.xPosF = ((float)this.entitySub.posX);
/* 42 */       this.yPosF = ((float)this.entitySub.posY);
/* 43 */       this.zPosF = ((float)this.entitySub.posZ);
/*    */       
/* 45 */       if ((this.entitySub.isInWater()) && (this.entitySub.activated) && (this.entitySub.power > 0.0F))
/*    */       {
/* 47 */         this.pitch = 1.0F;
/* 48 */         this.volume = 0.5F;
/*    */       }
/*    */       else
/*    */       {
/* 52 */         this.pitch = 0.0F;
/* 53 */         this.volume = 0.0F;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/MovingSoundSubPing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */