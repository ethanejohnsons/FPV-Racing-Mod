/*    */ package RCM.Audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.registries.IForgeRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModSoundEvents
/*    */ {
/*    */   public static SoundEvent remoteon;
/*    */   public static SoundEvent remoteoff;
/*    */   public static SoundEvent escsync;
/*    */   public static SoundEvent motorhigh;
/*    */   public static SoundEvent motorhigh1;
/*    */   public static SoundEvent motorlow;
/*    */   public static SoundEvent motorcar;
/*    */   public static SoundEvent airflow;
/*    */   public static SoundEvent motorhelihigh;
/*    */   public static SoundEvent helibladeflap;
/*    */   public static SoundEvent tick;
/*    */   public static SoundEvent lock;
/*    */   public static SoundEvent missile;
/*    */   public static SoundEvent prophit;
/*    */   public static SoundEvent waterflow;
/*    */   public static SoundEvent subping;
/*    */   
/*    */   public static void registerSounds()
/*    */   {
/* 32 */     remoteon = registerSound("remoteon");
/* 33 */     remoteoff = registerSound("remoteoff");
/* 34 */     escsync = registerSound("escsync");
/* 35 */     motorhigh = registerSound("motorhigh");
/* 36 */     motorhigh1 = registerSound("motorhigh1");
/* 37 */     motorlow = registerSound("motorlow");
/* 38 */     motorcar = registerSound("motorcar");
/* 39 */     airflow = registerSound("airflow");
/* 40 */     motorhelihigh = registerSound("motorhelihigh");
/* 41 */     helibladeflap = registerSound("helibladeflap");
/* 42 */     tick = registerSound("tick");
/* 43 */     lock = registerSound("lock");
/* 44 */     missile = registerSound("missile");
/* 45 */     prophit = registerSound("prophit");
/* 46 */     waterflow = vanillaRegisterSound("minecraft:block.water.ambient");
/* 47 */     subping = registerSound("subping");
/*    */   }
/*    */   
/*    */   public static SoundEvent registerSound(String soundName)
/*    */   {
/* 52 */     ResourceLocation soundID = new ResourceLocation("thercmod", soundName);
/* 53 */     SoundEvent soundEvent = new SoundEvent(soundID);
/* 54 */     ForgeRegistries.SOUND_EVENTS.register(soundEvent.setRegistryName(soundID));
/* 55 */     return soundEvent;
/*    */   }
/*    */   
/*    */   public static SoundEvent vanillaRegisterSound(String soundName)
/*    */   {
/* 60 */     ResourceLocation soundID = new ResourceLocation(soundName);
/* 61 */     SoundEvent soundEvent = new SoundEvent(soundID);
/* 62 */     ForgeRegistries.SOUND_EVENTS.register(soundEvent.setRegistryName(soundID));
/* 63 */     return soundEvent;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Audio/ModSoundEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */