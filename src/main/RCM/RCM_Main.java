/*     */ package RCM;
/*     */ 
/*     */ import RCM.Audio.ModSoundEvents;
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
/*     */ import RCM.Items.ItemBattery;
/*     */ import RCM.Items.ItemBoat;
/*     */ import RCM.Items.ItemBoatBody;
/*     */ import RCM.Items.ItemCar;
/*     */ import RCM.Items.ItemCarBody;
/*     */ import RCM.Items.ItemCircuitBoard;
/*     */ import RCM.Items.ItemDrone;
/*     */ import RCM.Items.ItemDroneBody;
/*     */ import RCM.Items.ItemElectricMotor;
/*     */ import RCM.Items.ItemF22;
/*     */ import RCM.Items.ItemF22Body;
/*     */ import RCM.Items.ItemFlightController;
/*     */ import RCM.Items.ItemHeli;
/*     */ import RCM.Items.ItemHeliBody;
/*     */ import RCM.Items.ItemMissile;
/*     */ import RCM.Items.ItemNarrowWheel;
/*     */ import RCM.Items.ItemPropellerHigh;
/*     */ import RCM.Items.ItemPropellerLow;
/*     */ import RCM.Items.ItemReceiverModule;
/*     */ import RCM.Items.ItemRemoteControl;
/*     */ import RCM.Items.ItemRotorBlades;
/*     */ import RCM.Items.ItemServo;
/*     */ import RCM.Items.ItemSpeedController;
/*     */ import RCM.Items.ItemStuntPlane;
/*     */ import RCM.Items.ItemStuntPlaneBody;
/*     */ import RCM.Items.ItemSubmarine;
/*     */ import RCM.Items.ItemSubmarineBody;
/*     */ import RCM.Items.ItemTrainerPlane;
/*     */ import RCM.Items.ItemTrainerPlaneBody;
/*     */ import RCM.Items.ItemWideWheel;
/*     */ import RCM.Items.MaterialCarbonFiber;
/*     */ import RCM.Items.MaterialHardenedRubber;
/*     */ import RCM.Items.MaterialLightIronPowder;
/*     */ import RCM.Items.MaterialLightMetal;
/*     */ import RCM.Items.MaterialPlastic;
/*     */ import RCM.Items.MaterialPlywood;
/*     */ import RCM.Items.MaterialSoftRubber;
/*     */ import RCM.Items.MaterialStickyCarbon;
/*     */ import RCM.Packets.MessageHandler;
/*     */ import RCM.Physics.PhysicsTickHandler;
/*     */ import RCM.Physics.PhysicsWorld;
/*     */ import RCM.PropertiesLoader.AerofoilProperties;
/*     */ import RCM.PropertiesLoader.PropertiesLoader;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.FMLLog;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*     */ import net.minecraftforge.fml.common.Mod.Instance;
/*     */ import net.minecraftforge.fml.common.SidedProxy;
/*     */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/*     */ import net.minecraftforge.fml.common.registry.EntityRegistry;
/*     */ import net.minecraftforge.fml.common.registry.GameRegistry;
/*     */ import org.apache.logging.log4j.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mod(modid="thercmod", version="3.2.1.0", acceptedMinecraftVersions="[1.12.2]")
/*     */ public class RCM_Main
/*     */ {
/*     */   public static final String MODID = "thercmod";
/*     */   public static final String VERSION = "3.2.1.0";
/*     */   @Mod.Instance("thercmod")
/*     */   public static RCM_Main instance;
/*     */   @SidedProxy(clientSide="RCM.ClientProxy", serverSide="RCM.CommonProxy")
/*     */   public static CommonProxy proxy;
/* 114 */   public static CreativeTab tab = new CreativeTab("creativetab.name");
/* 115 */   public static PropertiesLoader planeProperies = new PropertiesLoader();
/* 116 */   public static PropertiesLoader carProperies = new PropertiesLoader();
/* 117 */   public static PropertiesLoader boatProperies = new PropertiesLoader();
/* 118 */   public static PropertiesLoader heliProperies = new PropertiesLoader();
/* 119 */   public static PropertiesLoader droneProperies = new PropertiesLoader();
/* 120 */   public static PropertiesLoader f22Properies = new PropertiesLoader();
/* 121 */   public static PropertiesLoader submarineProperies = new PropertiesLoader();
/* 122 */   public static PropertiesLoader stuntplaneProperies = new PropertiesLoader();
/* 123 */   public static PropertiesLoader missleProperies = new PropertiesLoader();
/* 124 */   public static AerofoilProperties aerofoilProperties = new AerofoilProperties();
/*     */   
/*     */   public static Item item_remotecontrol;
/*     */   
/*     */   public static Item item_trainerplane;
/*     */   
/*     */   public static Item item_car;
/*     */   
/*     */   public static Item item_boat;
/*     */   
/*     */   public static Item item_heli;
/*     */   
/*     */   public static Item item_drone;
/*     */   
/*     */   public static Item item_f22;
/*     */   public static Item item_submarine;
/*     */   public static Item item_stuntplane;
/*     */   public static Item item_trainerplane_body;
/*     */   public static Item item_car_body;
/*     */   public static Item item_boat_body;
/*     */   public static Item item_heli_body;
/*     */   public static Item item_drone_body;
/*     */   public static Item item_f22_body;
/*     */   public static Item item_submarine_body;
/*     */   public static Item item_stuntplane_body;
/*     */   public static Item item_missile;
/*     */   public static Item item_battery;
/*     */   public static Item item_electricmotor;
/*     */   public static Item item_narrowwheel;
/*     */   public static Item item_widewheel;
/*     */   public static Item item_receivermodule;
/*     */   public static Item item_circuitboard;
/*     */   public static Item item_speed_controller;
/*     */   public static Item item_servo;
/*     */   public static Item item_flight_controller;
/*     */   public static Item item_rotorblades;
/*     */   public static Item item_propeller_high;
/*     */   public static Item item_propeller_low;
/*     */   public static Item material_stickycarbon;
/*     */   public static Item material_carbonfiber;
/*     */   public static Item material_lightironpowder;
/*     */   public static Item material_lightmetal;
/*     */   public static Item material_plastic;
/*     */   public static Item material_plywood;
/*     */   public static Item material_softrubber;
/*     */   public static Item material_hardenedrubber;
/*     */   public static String propertiesFilePath;
/*     */   public static String modelFilePath;
/*     */   public static int blockRenderID;
/*     */   public static PhysicsWorld physicsWorld;
/*     */   public static PhysicsTickHandler tickHandler;
/*     */   public static String newVersion;
/*     */   
/*     */   @Mod.EventHandler
/*     */   public void preInit(FMLPreInitializationEvent event)
/*     */   {
/* 180 */     propertiesFilePath = "/assets/thercmod/properties/";
/* 181 */     modelFilePath = "/assets/thercmod/models/";
/* 182 */     checkVersion();
/* 183 */     MessageHandler.init();
/* 184 */     loadItems(event);
/* 185 */     addRecipe();
/* 186 */     loadEntities();
/* 187 */     proxy.loadItemModels();
/* 188 */     ModSoundEvents.registerSounds();
/*     */   }
/*     */   
/*     */   @Mod.EventHandler
/*     */   public void init(FMLInitializationEvent event)
/*     */   {
/* 194 */     proxy.registerItemRenders();
/* 195 */     proxy.registerRenderInformation();
/* 196 */     tickHandler = new PhysicsTickHandler();
/* 197 */     proxy.registerTick();
/* 198 */     proxy.registerKeyHandler();
/*     */   }
/*     */   
/*     */ 
/*     */   @Mod.EventHandler
/*     */   public void postInit(FMLInitializationEvent event)
/*     */   {
/* 205 */     physicsWorld = new PhysicsWorld();
/*     */   }
/*     */   
/*     */   private void addRecipe()
/*     */   {
/* 210 */     GameRegistry.addSmelting(material_stickycarbon, new ItemStack(material_carbonfiber, 1), 0.0F);
/* 211 */     GameRegistry.addSmelting(material_softrubber, new ItemStack(material_hardenedrubber, 1), 0.0F);
/* 212 */     GameRegistry.addSmelting(material_lightironpowder, new ItemStack(material_lightmetal, 1), 0.0F);
/*     */   }
/*     */   
/*     */   private void loadItems(FMLPreInitializationEvent event)
/*     */   {
/* 217 */     item_remotecontrol = new ItemRemoteControl();
/* 218 */     item_trainerplane = new ItemTrainerPlane();
/* 219 */     item_car = new ItemCar();
/* 220 */     item_boat = new ItemBoat();
/* 221 */     item_heli = new ItemHeli();
/* 222 */     item_drone = new ItemDrone();
/* 223 */     item_f22 = new ItemF22();
/* 224 */     item_submarine = new ItemSubmarine();
/* 225 */     item_stuntplane = new ItemStuntPlane();
/*     */     
/* 227 */     item_trainerplane_body = new ItemTrainerPlaneBody();
/* 228 */     item_car_body = new ItemCarBody();
/* 229 */     item_boat_body = new ItemBoatBody();
/* 230 */     item_heli_body = new ItemHeliBody();
/* 231 */     item_drone_body = new ItemDroneBody();
/* 232 */     item_f22_body = new ItemF22Body();
/* 233 */     item_submarine_body = new ItemSubmarineBody();
/* 234 */     item_stuntplane_body = new ItemStuntPlaneBody();
/* 235 */     item_missile = new ItemMissile();
/*     */     
/* 237 */     item_narrowwheel = new ItemNarrowWheel();
/* 238 */     item_widewheel = new ItemWideWheel();
/* 239 */     item_battery = new ItemBattery();
/* 240 */     item_electricmotor = new ItemElectricMotor();
/* 241 */     item_circuitboard = new ItemCircuitBoard();
/* 242 */     item_receivermodule = new ItemReceiverModule();
/* 243 */     item_servo = new ItemServo();
/* 244 */     item_speed_controller = new ItemSpeedController();
/* 245 */     item_flight_controller = new ItemFlightController();
/* 246 */     item_rotorblades = new ItemRotorBlades();
/* 247 */     item_propeller_high = new ItemPropellerHigh();
/* 248 */     item_propeller_low = new ItemPropellerLow();
/*     */     
/* 250 */     material_stickycarbon = new MaterialStickyCarbon();
/* 251 */     material_lightironpowder = new MaterialLightIronPowder();
/* 252 */     material_softrubber = new MaterialSoftRubber();
/* 253 */     material_plywood = new MaterialPlywood();
/* 254 */     material_carbonfiber = new MaterialCarbonFiber();
/* 255 */     material_lightmetal = new MaterialLightMetal();
/* 256 */     material_hardenedrubber = new MaterialHardenedRubber();
/* 257 */     material_plastic = new MaterialPlastic();
/*     */   }
/*     */   
/*     */   private void loadEntities()
/*     */   {
/*     */     try
/*     */     {
/* 264 */       planeProperies.init("planeproperties.cfg");
/* 265 */       droneProperies.init("droneproperties.cfg");
/* 266 */       carProperies.init("carproperties.cfg");
/* 267 */       f22Properies.init("f22properties.cfg");
/* 268 */       heliProperies.init("heliproperties.cfg");
/* 269 */       boatProperies.init("boatproperties.cfg");
/* 270 */       submarineProperies.init("submarineproperties.cfg");
/* 271 */       stuntplaneProperies.init("stuntplaneproperties.cfg");
/* 272 */       missleProperies.init("missileproperties.cfg");
/* 273 */       aerofoilProperties.init("thinplate.afl", "naca0012.afl", "clarky.afl");
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/* 277 */       FMLLog.log(Level.ERROR, e, "Properties file is missing!!", new Object[0]);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 281 */       FMLLog.log(Level.ERROR, e, "Properties file loaded incorrectly!!", new Object[0]);
/*     */     }
/*     */     
/* 284 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntityTrainerPlane.class, "entity_trainerplane", 1, this, 200, 1, false);
/* 285 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntityDrone.class, "entity_drone", 2, this, 200, 1, false);
/* 286 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntityCar.class, "entity_car", 3, this, 200, 1, false);
/* 287 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntityF22.class, "entity_f22", 4, this, 200, 1, false);
/* 288 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntityHeli.class, "entity_heli", 5, this, 200, 1, false);
/* 289 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntityBoat.class, "entity_boat", 6, this, 200, 1, false);
/* 290 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntitySubmarine.class, "entity_submarine", 7, this, 200, 1, false);
/* 291 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntityStuntPlane.class, "entity_stuntplane", 8, this, 200, 1, false);
/* 292 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntityMissile.class, "entity_missle", 9, this, 200, 1, false);
/* 293 */     EntityRegistry.registerModEntity(new ResourceLocation("thercmod"), EntitySubMissile.class, "entity_submissle", 10, this, 200, 1, false);
/*     */   }
/*     */   
/*     */   private void checkVersion()
/*     */   {
/* 298 */     newVersion = "3.2.1.0";
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 303 */       String mcversion = Minecraft.getMinecraft().getVersion();
/*     */       
/* 305 */       String post_data = URLEncoder.encode("rcm_version", "UTF-8") + "=" + URLEncoder.encode("3.2.1.0", "UTF-8") + "&" + URLEncoder.encode("mc_version", "UTF-8") + "=" + URLEncoder.encode(mcversion, "UTF-8");
/*     */       
/* 307 */       URL url = new URL("http://www.eldercodes.net/thercmod_db/versioncheck.php");
/* 308 */       HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
/* 309 */       httpURLConnection.setRequestMethod("POST");
/* 310 */       httpURLConnection.setDoOutput(true);
/* 311 */       httpURLConnection.setDoInput(true);
/* 312 */       OutputStream outputStream = httpURLConnection.getOutputStream();
/* 313 */       BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
/* 314 */       bufferedWriter.write(post_data);
/* 315 */       bufferedWriter.close();
/* 316 */       outputStream.close();
/*     */       
/* 318 */       InputStream inputStream = httpURLConnection.getInputStream();
/* 319 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
/* 320 */       StringBuilder result = new StringBuilder();
/*     */       
/*     */       String inputLine;
/* 323 */       if ((inputLine = bufferedReader.readLine()) != null)
/*     */       {
/* 325 */         newVersion = inputLine;
/*     */       }
/*     */       
/* 328 */       bufferedReader.close();
/* 329 */       inputStream.close();
/* 330 */       httpURLConnection.disconnect();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 334 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/RCM_Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */