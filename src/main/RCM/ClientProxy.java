/*     */ package RCM;
/*     */ 
/*     */ import RCM.Entities.EntityBoat;
/*     */ import RCM.Entities.EntityCar;
/*     */ import RCM.Entities.EntityDrone;
/*     */ import RCM.Entities.EntityF22;
/*     */ import RCM.Entities.EntityHeli;
/*     */ import RCM.Entities.EntityMissile;
/*     */ import RCM.Entities.EntityStuntPlane;
/*     */ import RCM.Entities.EntitySubMissile;
/*     */ import RCM.Entities.EntitySubmarine;
/*     */ import RCM.Entities.EntityTrainerPlane;
/*     */ import RCM.Renders.RenderBoat;
/*     */ import RCM.Renders.RenderCar;
/*     */ import RCM.Renders.RenderDrone;
/*     */ import RCM.Renders.RenderF22;
/*     */ import RCM.Renders.RenderHeli;
/*     */ import RCM.Renders.RenderMissile;
/*     */ import RCM.Renders.RenderRemoteControl;
/*     */ import RCM.Renders.RenderStuntPlane;
/*     */ import RCM.Renders.RenderSubMissile;
/*     */ import RCM.Renders.RenderSubmarine;
/*     */ import RCM.Renders.RenderTrainerPlane;
/*     */ import java.io.File;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.ItemModelMesher;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.client.registry.RenderingRegistry;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventBus;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientProxy
/*     */   extends CommonProxy
/*     */ {
/*     */   public void registerRenderInformation()
/*     */   {
/*  51 */     RenderingRegistry.registerEntityRenderingHandler(EntityTrainerPlane.class, new RenderTrainerPlane());
/*  52 */     RenderingRegistry.registerEntityRenderingHandler(EntityDrone.class, new RenderDrone());
/*  53 */     RenderingRegistry.registerEntityRenderingHandler(EntityCar.class, new RenderCar());
/*  54 */     RenderingRegistry.registerEntityRenderingHandler(EntityF22.class, new RenderF22());
/*  55 */     RenderingRegistry.registerEntityRenderingHandler(EntityHeli.class, new RenderHeli());
/*  56 */     RenderingRegistry.registerEntityRenderingHandler(EntityBoat.class, new RenderBoat());
/*  57 */     RenderingRegistry.registerEntityRenderingHandler(EntitySubmarine.class, new RenderSubmarine());
/*  58 */     RenderingRegistry.registerEntityRenderingHandler(EntityStuntPlane.class, new RenderStuntPlane());
/*  59 */     RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class, new RenderMissile());
/*  60 */     RenderingRegistry.registerEntityRenderingHandler(EntitySubMissile.class, new RenderSubMissile());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void loadItemModels()
/*     */   {
/*  68 */     ModelResourceLocation mRL = new ModelResourceLocation("thercmod".toLowerCase() + ":item_remotecontrol", "inventory");
/*     */     
/*  70 */     RCM_Main.item_remotecontrol.setTileEntityItemStackRenderer(new RenderRemoteControl());
/*  71 */     ModelLoader.setCustomModelResourceLocation(RCM_Main.item_remotecontrol, 0, mRL);
/*  72 */     ModelBakery.registerItemVariants(RCM_Main.item_remotecontrol, new ResourceLocation[] { mRL });
/*     */   }
/*     */   
/*     */ 
/*     */   public void registerItemRenders()
/*     */   {
/*  78 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_trainerplane, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_trainerplane", "inventory"));
/*  79 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_car, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_car", "inventory"));
/*  80 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_boat, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_boat", "inventory"));
/*  81 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_heli, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_heli", "inventory"));
/*  82 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_drone, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_drone", "inventory"));
/*  83 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_f22, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_f22", "inventory"));
/*  84 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_submarine, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_submarine", "inventory"));
/*  85 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_stuntplane, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_stuntplane", "inventory"));
/*     */     
/*  87 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_trainerplane_body, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_trainerplane_body", "inventory"));
/*  88 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_car_body, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_car_body", "inventory"));
/*  89 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_boat_body, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_boat_body", "inventory"));
/*  90 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_heli_body, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_heli_body", "inventory"));
/*  91 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_drone_body, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_drone_body", "inventory"));
/*  92 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_f22_body, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_f22_body", "inventory"));
/*  93 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_submarine_body, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_submarine_body", "inventory"));
/*  94 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_stuntplane_body, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_stuntplane_body", "inventory"));
/*     */     
/*  96 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_missile, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_missile", "inventory"));
/*  97 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_narrowwheel, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_narrowwheel", "inventory"));
/*  98 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_widewheel, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_widewheel", "inventory"));
/*  99 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_battery, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_battery", "inventory"));
/* 100 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_electricmotor, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_electricmotor", "inventory"));
/* 101 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_circuitboard, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_circuitboard", "inventory"));
/* 102 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_receivermodule, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_receivermodule", "inventory"));
/* 103 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_servo, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_servo", "inventory"));
/* 104 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_speed_controller, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_speed_controller", "inventory"));
/* 105 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_flight_controller, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_flight_controller", "inventory"));
/* 106 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_rotorblades, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_rotorblades", "inventory"));
/* 107 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_propeller_high, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_propeller_high", "inventory"));
/* 108 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.item_propeller_low, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":item_propeller_low", "inventory"));
/*     */     
/* 110 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.material_stickycarbon, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":material_stickycarbon", "inventory"));
/* 111 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.material_lightironpowder, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":material_lightironpowder", "inventory"));
/* 112 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.material_softrubber, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":material_softrubber", "inventory"));
/* 113 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.material_carbonfiber, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":material_carbonfiber", "inventory"));
/* 114 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.material_plywood, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":material_plywood", "inventory"));
/* 115 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.material_hardenedrubber, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":material_hardenedrubber", "inventory"));
/* 116 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.material_lightmetal, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":material_lightmetal", "inventory"));
/* 117 */     Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RCM_Main.material_plastic, 0, new ModelResourceLocation("thercmod".toLowerCase() + ":material_plastic", "inventory"));
/*     */   }
/*     */   
/*     */ 
/*     */   public void registerTick()
/*     */   {
/* 123 */     MinecraftForge.EVENT_BUS.register(RCM_Main.tickHandler);
/* 124 */     MinecraftForge.EVENT_BUS.register(new TickHandler());
/* 125 */     MinecraftForge.EVENT_BUS.register(new RenderRemoteControl());
/*     */   }
/*     */   
/*     */ 
/*     */   public void registerKeyHandler()
/*     */   {
/* 131 */     MinecraftForge.EVENT_BUS.register(new KeyHandler(FMLClientHandler.instance().getClient()));
/*     */   }
/*     */   
/*     */ 
/*     */   public World getClientWorld()
/*     */   {
/* 137 */     return FMLClientHandler.instance().getClient().world;
/*     */   }
/*     */   
/*     */ 
/*     */   public World getServerWorld()
/*     */   {
/* 143 */     return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public SoundHandler getSoundHandler()
/*     */   {
/* 150 */     return FMLClientHandler.instance().getClient().getSoundHandler();
/*     */   }
/*     */   
/*     */   public String getPropertiesFilePath()
/*     */   {
/* 155 */     return FMLClientHandler.instance().getClient().gameDir.getAbsolutePath();
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/ClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */