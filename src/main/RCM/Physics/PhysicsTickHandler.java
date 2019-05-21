/*     */ package RCM.Physics;
/*     */ 
/*     */ import RCM.CommonProxy;
/*     */ import RCM.Entities.EntityMissile;
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import RCM.RCM_Main;
/*     */ import com.bulletphysics.linearmath.Clock;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PhysicsTickHandler
/*     */ {
/*  21 */   protected Clock clock = new Clock();
/*  22 */   private List<GlobalEntity> entity = new ArrayList();
/*  23 */   private static List<GlobalEntity> register = new ArrayList();
/*  24 */   private static List<GlobalEntity> deRegister = new ArrayList();
/*     */   
/*     */   @SubscribeEvent
/*     */   public void tick(TickEvent.RenderTickEvent event)
/*     */   {
/*  29 */     if ((RCM_Main.physicsWorld != null) && (event.phase == TickEvent.Phase.START))
/*     */     {
/*  31 */       float deltaTime = getDeltaTimeMicroseconds() * 1.0E-6F;
/*  32 */       int maxSimSubSteps = 15;
/*     */       
/*  34 */       if (RCM_Main.proxy.getClientWorld() == null)
/*     */       {
/*  36 */         this.entity.clear();
/*  37 */         RCM_Main.physicsWorld.removeAll();
/*     */       }
/*     */       
/*  40 */       if (!FMLClientHandler.instance().getClient().isGamePaused())
/*     */       {
/*     */ 
/*  43 */         for (GlobalEntity rcentity : register)
/*     */         {
/*  45 */           this.entity.add(rcentity);
/*     */         }
/*     */         
/*  48 */         register.clear();
/*     */         
/*     */ 
/*  51 */         for (GlobalEntity rcentity : deRegister)
/*     */         {
/*  53 */           this.entity.remove(rcentity);
/*     */         }
/*     */         
/*  56 */         deRegister.clear();
/*     */         
/*  58 */         int size = this.entity.size();
/*     */         
/*  60 */         for (int i = 0; i < size; i++)
/*     */         {
/*  62 */           GlobalEntity rcentity = (GlobalEntity)this.entity.get(i);
/*     */           
/*  64 */           if ((rcentity.physicsWorld != null) && (rcentity.physicsWorld.requestCollisionShapes) && (!(this.entity instanceof EntityMissile)))
/*     */           {
/*  66 */             RCM_Main.physicsWorld.unloadCollisionShapes(rcentity.world, this.entity);
/*  67 */             RCM_Main.physicsWorld.loadCollisionShapes(rcentity, rcentity.world, rcentity.position);
/*  68 */             rcentity.physicsWorld.requestCollisionShapes = false;
/*     */           }
/*     */           
/*  71 */           size = this.entity.size();
/*     */         }
/*     */         
/*     */ 
/*  75 */         if (deltaTime > 0.016666668F)
/*     */         {
/*     */ 
/*  78 */           if (deltaTime > 0.05F)
/*     */           {
/*  80 */             deltaTime = 0.05F;
/*     */           }
/*     */           
/*     */           do
/*     */           {
/*  85 */             float stepTime = 0.016666668F;
/*     */             
/*  87 */             if (stepTime > deltaTime)
/*     */             {
/*  89 */               stepTime = deltaTime;
/*     */             }
/*     */             
/*  92 */             for (int i = 0; i < size; i++)
/*     */             {
/*  94 */               GlobalEntity rcentity = (GlobalEntity)this.entity.get(i);
/*     */               
/*  96 */               if ((rcentity.physicsWorld != null) && (rcentity != null) && (!rcentity.isDead) && 
/*  97 */                 (!Minecraft.getMinecraft().isGamePaused()))
/*     */               {
/*  99 */                 rcentity.physicsWorld.update(rcentity, stepTime);
/*     */               }
/*     */               
/* 102 */               size = this.entity.size();
/*     */             }
/*     */             
/* 105 */             RCM_Main.physicsWorld.stepSimulation(stepTime, maxSimSubSteps);
/* 106 */             deltaTime -= stepTime;
/*     */           }
/* 108 */           while (deltaTime > 0.0F);
/*     */         }
/*     */         else
/*     */         {
/* 112 */           for (int i = 0; i < size; i++)
/*     */           {
/* 114 */             GlobalEntity rcentity = (GlobalEntity)this.entity.get(i);
/*     */             
/* 116 */             if ((rcentity.physicsWorld != null) && (rcentity != null) && (!rcentity.isDead) && 
/* 117 */               (!Minecraft.getMinecraft().isGamePaused()))
/*     */             {
/* 119 */               rcentity.physicsWorld.update(rcentity, deltaTime);
/*     */             }
/*     */             
/* 122 */             size = this.entity.size();
/*     */           }
/*     */           
/* 125 */           RCM_Main.physicsWorld.stepSimulation(deltaTime, maxSimSubSteps);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void register(GlobalEntity rcentity)
/*     */   {
/* 133 */     boolean flag = false;
/*     */     
/* 135 */     for (GlobalEntity en : register)
/*     */     {
/* 137 */       if (en == rcentity)
/*     */       {
/* 139 */         flag = true;
/*     */       }
/*     */     }
/*     */     
/* 143 */     if (!flag)
/*     */     {
/* 145 */       register.add(rcentity);
/*     */     }
/*     */   }
/*     */   
/*     */   public void deRegister(GlobalEntity rcentity)
/*     */   {
/* 151 */     boolean flag = false;
/*     */     
/* 153 */     for (GlobalEntity en : deRegister)
/*     */     {
/* 155 */       if (en == rcentity)
/*     */       {
/* 157 */         flag = true;
/*     */       }
/*     */     }
/*     */     
/* 161 */     if (!flag)
/*     */     {
/* 163 */       deRegister.add(rcentity);
/*     */     }
/*     */   }
/*     */   
/*     */   private float getDeltaTimeMicroseconds()
/*     */   {
/* 169 */     float dt = (float)this.clock.getTimeMicroseconds();
/* 170 */     this.clock.reset();
/*     */     
/* 172 */     return dt;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/PhysicsTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */