/*     */ package RCM;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Controller;
/*     */ import org.lwjgl.input.Controllers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyHandler
/*     */ {
/*  31 */   private KeyBinding key_powerup = new KeyBinding("key.powerup", 17, "key.categories.thercmod");
/*  32 */   private KeyBinding key_powerdown = new KeyBinding("key.powerdown", 31, "key.categories.thercmod");
/*  33 */   private KeyBinding key_turnleft = new KeyBinding("key.turnleft", 30, "key.categories.thercmod");
/*  34 */   private KeyBinding key_turnright = new KeyBinding("key.turnright", 32, "key.categories.thercmod");
/*  35 */   private KeyBinding key_rollleft = new KeyBinding("key.rollleft", 203, "key.categories.thercmod");
/*  36 */   private KeyBinding key_rollright = new KeyBinding("key.rollright", 205, "key.categories.thercmod");
/*  37 */   private KeyBinding key_pitchdown = new KeyBinding("key.pitchdown", 200, "key.categories.thercmod");
/*  38 */   private KeyBinding key_pitchup = new KeyBinding("key.pitchup", 208, "key.categories.thercmod");
/*  39 */   private KeyBinding key_retrieve = new KeyBinding("key.retrieve", 19, "key.categories.thercmod");
/*  40 */   private KeyBinding key_weaponsmode = new KeyBinding("key.weaponsmode", 46, "key.categories.thercmod");
/*  41 */   private KeyBinding key_motorkill = new KeyBinding("key.motorkill", 37, "key.categories.thercmod");
/*  42 */   private KeyBinding key_camright = new KeyBinding("key.camright", 77, "key.categories.thercmod");
/*  43 */   private KeyBinding key_camleft = new KeyBinding("key.camleft", 75, "key.categories.thercmod");
/*  44 */   private KeyBinding key_camup = new KeyBinding("key.camup", 72, "key.categories.thercmod");
/*  45 */   private KeyBinding key_camdown = new KeyBinding("key.camdown", 80, "key.categories.thercmod");
/*  46 */   private KeyBinding key_camin = new KeyBinding("key.camin", 78, "key.categories.thercmod");
/*  47 */   private KeyBinding key_camout = new KeyBinding("key.camout", 74, "key.categories.thercmod");
/*  48 */   private KeyBinding key_camreset = new KeyBinding("key.camreset", 76, "key.categories.thercmod");
/*  49 */   private KeyBinding key_fire = new KeyBinding("key.fire", 57, "key.categories.thercmod");
/*  50 */   private KeyBinding key_jump = new KeyBinding("key.powerjump", 200, "key.categories.thercmod");
/*     */   
/*     */   private boolean controllerEnabled;
/*     */   
/*     */   private boolean pitchControllerEnabled;
/*     */   
/*     */   private int pitchControllerIndex;
/*     */   
/*     */   private int pitchAxisIndex;
/*     */   
/*     */   private int pitchAxisInvert;
/*     */   
/*     */   private boolean rollControllerEnabled;
/*     */   private int rollControllerIndex;
/*     */   private int rollAxisIndex;
/*     */   private int rollAxisInvert;
/*     */   private boolean yawControllerEnabled;
/*     */   private int yawControllerIndex;
/*     */   private int yawAxisIndex;
/*     */   private int yawAxisInvert;
/*     */   private boolean powerControllerEnabled;
/*     */   private int powerControllerIndex;
/*     */   private int powerAxisIndex;
/*     */   private int powerAxisInvert;
/*     */   private boolean turnControllerEnabled;
/*     */   private int turnControllerIndex;
/*     */   private int turnAxisIndex;
/*     */   private int turnAxisInvert;
/*     */   private int shootControllerIndex;
/*     */   private int shootButtonIndex;
/*     */   private int wModeControllerIndex;
/*     */   private int wModeButtonIndex;
/*     */   private int resetControllerIndex;
/*     */   private int resetButtonIndex;
/*     */   private int jumpControllerIndex;
/*     */   private int jumpButtonIndex;
/*     */   public static float absPowerMovement;
/*     */   public static float powerMovement;
/*     */   public static float yawMovement;
/*     */   public static float turnMovement;
/*     */   public static float rollMovement;
/*     */   public static float pitchMovement;
/*     */   public static boolean retrieve;
/*     */   public static boolean weaponsMode;
/*     */   public static boolean motorKill;
/*     */   public static boolean jump;
/*     */   public static boolean shoot;
/*     */   public static int camZoom;
/*     */   public static int camYaw;
/*     */   public static int camHeight;
/*     */   public static boolean camReset;
/*     */   private Minecraft mc;
/*     */   
/*     */   public KeyHandler(Minecraft minecraft)
/*     */   {
/* 105 */     this.mc = minecraft;
/*     */     
/* 107 */     ClientRegistry.registerKeyBinding(this.key_powerup);
/* 108 */     ClientRegistry.registerKeyBinding(this.key_powerdown);
/* 109 */     ClientRegistry.registerKeyBinding(this.key_turnleft);
/* 110 */     ClientRegistry.registerKeyBinding(this.key_turnright);
/* 111 */     ClientRegistry.registerKeyBinding(this.key_rollleft);
/* 112 */     ClientRegistry.registerKeyBinding(this.key_rollright);
/* 113 */     ClientRegistry.registerKeyBinding(this.key_pitchup);
/* 114 */     ClientRegistry.registerKeyBinding(this.key_pitchdown);
/* 115 */     ClientRegistry.registerKeyBinding(this.key_retrieve);
/* 116 */     ClientRegistry.registerKeyBinding(this.key_weaponsmode);
/* 117 */     ClientRegistry.registerKeyBinding(this.key_motorkill);
/* 118 */     ClientRegistry.registerKeyBinding(this.key_camright);
/* 119 */     ClientRegistry.registerKeyBinding(this.key_camleft);
/* 120 */     ClientRegistry.registerKeyBinding(this.key_camup);
/* 121 */     ClientRegistry.registerKeyBinding(this.key_camdown);
/* 122 */     ClientRegistry.registerKeyBinding(this.key_camin);
/* 123 */     ClientRegistry.registerKeyBinding(this.key_camout);
/* 124 */     ClientRegistry.registerKeyBinding(this.key_fire);
/* 125 */     ClientRegistry.registerKeyBinding(this.key_jump);
/*     */     
/* 127 */     discoverControllers();
/* 128 */     initControllers();
/*     */     
/* 130 */     absPowerMovement = 0.0F;
/* 131 */     powerMovement = 0.0F;
/* 132 */     yawMovement = 0.0F;
/* 133 */     turnMovement = 0.0F;
/* 134 */     rollMovement = 0.0F;
/* 135 */     pitchMovement = 0.0F;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void tick(TickEvent.ClientTickEvent event)
/*     */   {
/* 141 */     if ((this.mc.inGameHasFocus) && (event.phase == TickEvent.Phase.START))
/*     */     {
/*     */ 
/* 144 */       Controllers.poll();
/*     */       
/* 146 */       retrieve = false;
/* 147 */       weaponsMode = false;
/* 148 */       shoot = false;
/* 149 */       jump = false;
/* 150 */       motorKill = false;
/* 151 */       camYaw = 0;
/* 152 */       camHeight = 0;
/* 153 */       camZoom = 0;
/* 154 */       camReset = false;
/*     */       
/* 156 */       if (this.key_powerup.isKeyDown())
/*     */       {
/* 158 */         powerMovement = offSetValue(powerMovement, 1);
/*     */       }
/* 160 */       else if (this.key_powerdown.isKeyDown())
/*     */       {
/* 162 */         powerMovement = offSetValue(powerMovement, -1);
/*     */       }
/* 164 */       else if ((this.powerControllerEnabled) && (this.controllerEnabled))
/*     */       {
/* 166 */         powerMovement = -getAxisValue(this.powerControllerIndex, this.powerAxisIndex, this.powerAxisInvert);
/*     */       }
/*     */       else
/*     */       {
/* 170 */         powerMovement = zeroValue(powerMovement);
/*     */       }
/*     */       
/* 173 */       if (this.key_powerup.isKeyDown())
/*     */       {
/* 175 */         absPowerMovement = powerOffSetValue(absPowerMovement, 1);
/*     */       }
/* 177 */       else if (this.key_powerdown.isKeyDown())
/*     */       {
/* 179 */         absPowerMovement = powerOffSetValue(absPowerMovement, -1);
/*     */       }
/* 181 */       else if ((this.powerControllerEnabled) && (this.controllerEnabled))
/*     */       {
/* 183 */         absPowerMovement = Math.abs((getAxisValue(this.powerControllerIndex, this.powerAxisIndex, this.powerAxisInvert) - 1.0F) / 2.0F);
/*     */       }
/*     */       
/* 186 */       if (this.key_turnright.isKeyDown())
/*     */       {
/* 188 */         yawMovement = offSetValue(yawMovement, 1);
/*     */       }
/* 190 */       else if (this.key_turnleft.isKeyDown())
/*     */       {
/* 192 */         yawMovement = offSetValue(yawMovement, -1);
/*     */       }
/* 194 */       else if ((this.yawControllerEnabled) && (this.controllerEnabled))
/*     */       {
/* 196 */         yawMovement = getAxisValue(this.yawControllerIndex, this.yawAxisIndex, this.yawAxisInvert);
/*     */       }
/*     */       else
/*     */       {
/* 200 */         yawMovement = zeroValue(yawMovement);
/*     */       }
/*     */       
/* 203 */       if (this.key_turnright.isKeyDown())
/*     */       {
/* 205 */         turnMovement = offSetValue(turnMovement, 1);
/*     */       }
/* 207 */       else if (this.key_turnleft.isKeyDown())
/*     */       {
/* 209 */         turnMovement = offSetValue(turnMovement, -1);
/*     */       }
/* 211 */       else if ((this.turnControllerEnabled) && (this.controllerEnabled))
/*     */       {
/* 213 */         turnMovement = getAxisValue(this.turnControllerIndex, this.turnAxisIndex, this.turnAxisInvert);
/*     */       }
/*     */       else
/*     */       {
/* 217 */         turnMovement = zeroValue(turnMovement);
/*     */       }
/*     */       
/* 220 */       if (this.key_rollright.isKeyDown())
/*     */       {
/* 222 */         rollMovement = offSetValue(rollMovement, 1);
/*     */       }
/* 224 */       else if (this.key_rollleft.isKeyDown())
/*     */       {
/* 226 */         rollMovement = offSetValue(rollMovement, -1);
/*     */       }
/* 228 */       else if ((this.rollControllerEnabled) && (this.controllerEnabled))
/*     */       {
/* 230 */         rollMovement = getAxisValue(this.rollControllerIndex, this.rollAxisIndex, this.rollAxisInvert);
/*     */       }
/*     */       else
/*     */       {
/* 234 */         rollMovement = zeroValue(rollMovement);
/*     */       }
/*     */       
/* 237 */       if (this.key_pitchup.isKeyDown())
/*     */       {
/* 239 */         pitchMovement = offSetValue(pitchMovement, 1);
/*     */       }
/* 241 */       else if (this.key_pitchdown.isKeyDown())
/*     */       {
/* 243 */         pitchMovement = offSetValue(pitchMovement, -1);
/*     */       }
/* 245 */       else if ((this.pitchControllerEnabled) && (this.controllerEnabled))
/*     */       {
/* 247 */         pitchMovement = getAxisValue(this.pitchControllerIndex, this.pitchAxisIndex, this.pitchAxisInvert);
/*     */       }
/*     */       else
/*     */       {
/* 251 */         pitchMovement = zeroValue(pitchMovement);
/*     */       }
/*     */       
/* 254 */       if ((this.key_retrieve.isKeyDown()) || (getButtonState(this.resetControllerIndex, this.resetButtonIndex)))
/*     */       {
/* 256 */         retrieve = true;
/*     */       }
/*     */       
/*     */ 
/* 260 */       if ((this.key_weaponsmode.isKeyDown()) || (getButtonState(this.wModeControllerIndex, this.wModeButtonIndex)))
/*     */       {
/* 262 */         weaponsMode = true;
/*     */       }
/*     */       
/* 265 */       if ((this.key_fire.isKeyDown()) || (this.mc.gameSettings.keyBindAttack.isKeyDown()) || (getButtonState(this.shootControllerIndex, this.shootButtonIndex)))
/*     */       {
/* 267 */         shoot = true;
/*     */       }
/*     */       
/* 270 */       if (this.key_motorkill.isKeyDown())
/*     */       {
/* 272 */         motorKill = true;
/*     */       }
/*     */       
/* 275 */       if ((this.key_jump.isKeyDown()) || (getButtonState(this.jumpControllerIndex, this.jumpButtonIndex)))
/*     */       {
/* 277 */         jump = true;
/*     */       }
/*     */       
/* 280 */       if (this.key_camright.isKeyDown())
/*     */       {
/* 282 */         camYaw = 1;
/*     */       }
/* 284 */       else if (this.key_camleft.isKeyDown())
/*     */       {
/* 286 */         camYaw = -1;
/*     */       }
/*     */       
/* 289 */       if (this.key_camup.isKeyDown())
/*     */       {
/* 291 */         camHeight = 1;
/*     */       }
/* 293 */       else if (this.key_camdown.isKeyDown())
/*     */       {
/* 295 */         camHeight = -1;
/*     */       }
/*     */       
/* 298 */       if (this.key_camin.isKeyDown())
/*     */       {
/* 300 */         camZoom = 1;
/*     */       }
/* 302 */       else if (this.key_camout.isKeyDown())
/*     */       {
/* 304 */         camZoom = -1;
/*     */       }
/*     */       
/* 307 */       if (this.key_camreset.isKeyDown())
/*     */       {
/* 309 */         camReset = true;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void discoverControllers()
/*     */   {
/* 317 */     Minecraft mc = Minecraft.getMinecraft();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 322 */       Controllers.create();
/* 323 */       Controllers.poll();
/*     */       
/* 325 */       File dir = new File(mc.gameDir + "/thercmod");
/* 326 */       dir.mkdirs();
/*     */       
/* 328 */       File file = new File(dir, "controllerInfo.txt");
/* 329 */       File file2 = new File(dir, "controllerConfig.ini");
/*     */       
/* 331 */       if ((!file.createNewFile()) && (!file2.createNewFile()))
/*     */       {
/* 333 */         return;
/*     */       }
/*     */       
/* 336 */       FileOutputStream fileoutputstream = new FileOutputStream(file);
/* 337 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileoutputstream));
/*     */       
/* 339 */       for (int i = 0; i < Controllers.getControllerCount(); i++)
/*     */       {
/* 341 */         if (Controllers.getController(i).getAxisCount() > 0)
/*     */         {
/* 343 */           writer.write("Controller_name:" + Controllers.getController(i).getName() + "; Index:" + i + "\n");
/*     */           
/* 345 */           for (int j = 0; j < Controllers.getController(i).getAxisCount(); j++)
/*     */           {
/* 347 */             writer.write("Axis_name:" + Controllers.getController(i).getAxisName(j) + "; Index:" + j + "\n");
/*     */           }
/*     */           
/* 350 */           for (int j = 0; j < Controllers.getController(i).getButtonCount(); j++)
/*     */           {
/* 352 */             writer.write("Button_name:" + Controllers.getController(i).getButtonName(j) + "; Index:" + j + "\n");
/*     */           }
/*     */           
/* 355 */           writer.write("\n");
/*     */         }
/*     */       }
/*     */       
/* 359 */       writer.close();
/* 360 */       fileoutputstream.close();
/*     */       
/* 362 */       fileoutputstream = new FileOutputStream(file2);
/* 363 */       writer = new BufferedWriter(new OutputStreamWriter(fileoutputstream));
/*     */       
/* 365 */       writer.write("Please read the controllerInfo.txt before assigning anything!\n\n");
/* 366 */       writer.write("Controller_enabled=false\n\n");
/* 367 */       writer.write("1_Axis_enabled=false\n");
/* 368 */       writer.write("1_Controller_index=-1\n");
/* 369 */       writer.write("1_Axis_pitch_index=-1\n");
/* 370 */       writer.write("1_Invert_axis=1\n\n");
/* 371 */       writer.write("2_Axis_enabled=false\n");
/* 372 */       writer.write("2_Controller_index=-1\n");
/* 373 */       writer.write("2_Axis_roll_index=-1\n");
/* 374 */       writer.write("2_Invert_axis=1\n\n");
/* 375 */       writer.write("3_Axis_enabled=false\n");
/* 376 */       writer.write("3_Controller_index=-1\n");
/* 377 */       writer.write("3_Axis_yaw_index=-1\n");
/* 378 */       writer.write("3_Invert_axis=1\n\n");
/* 379 */       writer.write("4_Axis_enabled=false\n");
/* 380 */       writer.write("4_Controller_index=-1\n");
/* 381 */       writer.write("4_Axis_power_index=-1\n");
/* 382 */       writer.write("4_Invert_axis=1\n\n");
/* 383 */       writer.write("5_Axis_enabled=false\n");
/* 384 */       writer.write("5_Controller_index=-1\n");
/* 385 */       writer.write("5_Axis_turn_index=-1\n");
/* 386 */       writer.write("5_Invert_axis=1\n\n");
/*     */       
/* 388 */       writer.write("6_Controller_index=-1\n");
/* 389 */       writer.write("6_Button_shoot_index=-1\n\n");
/* 390 */       writer.write("7_Controller_index=-1\n");
/* 391 */       writer.write("7_Button_weapon_mode_index=-1\n\n");
/* 392 */       writer.write("8_Controller_index=-1\n");
/* 393 */       writer.write("8_Button_reset_index=-1\n");
/* 394 */       writer.write("9_Controller_index=-1\n");
/* 395 */       writer.write("9_Button_jump_index=-1\n");
/*     */       
/* 397 */       writer.close();
/* 398 */       fileoutputstream.close();
/*     */     }
/*     */     catch (LWJGLException|IOException e)
/*     */     {
/* 402 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void initControllers()
/*     */   {
/* 408 */     Minecraft mc = Minecraft.getMinecraft();
/*     */     
/*     */     try
/*     */     {
/* 412 */       File file = new File(mc.gameDir, "/thercmod/controllerConfig.ini");
/*     */       
/* 414 */       FileInputStream inputstream = new FileInputStream(file);
/* 415 */       BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */       
/*     */       String line;
/*     */       
/* 419 */       while ((line = reader.readLine()) != null)
/*     */       {
/* 421 */         if (line.startsWith("Controller_enabled"))
/*     */         {
/* 423 */           this.controllerEnabled = Boolean.valueOf(line.split("=")[1]).booleanValue();
/*     */ 
/*     */         }
/* 426 */         else if (line.startsWith("1_Axis_enabled"))
/*     */         {
/* 428 */           this.pitchControllerEnabled = Boolean.valueOf(line.split("=")[1]).booleanValue();
/*     */ 
/*     */         }
/* 431 */         else if (line.startsWith("1_Controller_index"))
/*     */         {
/* 433 */           this.pitchControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 436 */         else if (line.startsWith("1_Axis_pitch_index"))
/*     */         {
/* 438 */           this.pitchAxisIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 441 */         else if (line.startsWith("1_Invert_axis"))
/*     */         {
/* 443 */           this.pitchAxisInvert = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 446 */         else if (line.startsWith("2_Axis_enabled"))
/*     */         {
/* 448 */           this.rollControllerEnabled = Boolean.valueOf(line.split("=")[1]).booleanValue();
/*     */ 
/*     */         }
/* 451 */         else if (line.startsWith("2_Controller_index"))
/*     */         {
/* 453 */           this.rollControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 456 */         else if (line.startsWith("2_Axis_roll_index"))
/*     */         {
/* 458 */           this.rollAxisIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 461 */         else if (line.startsWith("2_Invert_axis"))
/*     */         {
/* 463 */           this.rollAxisInvert = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 466 */         else if (line.startsWith("3_Axis_enabled"))
/*     */         {
/* 468 */           this.yawControllerEnabled = Boolean.valueOf(line.split("=")[1]).booleanValue();
/*     */ 
/*     */         }
/* 471 */         else if (line.startsWith("3_Controller_index"))
/*     */         {
/* 473 */           this.yawControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 476 */         else if (line.startsWith("3_Axis_yaw_index"))
/*     */         {
/* 478 */           this.yawAxisIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 481 */         else if (line.startsWith("3_Invert_axis"))
/*     */         {
/* 483 */           this.yawAxisInvert = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 486 */         else if (line.startsWith("4_Axis_enabled"))
/*     */         {
/* 488 */           this.powerControllerEnabled = Boolean.valueOf(line.split("=")[1]).booleanValue();
/*     */ 
/*     */         }
/* 491 */         else if (line.startsWith("4_Controller_index"))
/*     */         {
/* 493 */           this.powerControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 496 */         else if (line.startsWith("4_Axis_power_index"))
/*     */         {
/* 498 */           this.powerAxisIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 501 */         else if (line.startsWith("4_Invert_axis"))
/*     */         {
/* 503 */           this.powerAxisInvert = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 506 */         else if (line.startsWith("5_Axis_enabled"))
/*     */         {
/* 508 */           this.turnControllerEnabled = Boolean.valueOf(line.split("=")[1]).booleanValue();
/*     */ 
/*     */         }
/* 511 */         else if (line.startsWith("5_Controller_index"))
/*     */         {
/* 513 */           this.turnControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 516 */         else if (line.startsWith("5_Axis_turn_index"))
/*     */         {
/* 518 */           this.turnAxisIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 521 */         else if (line.startsWith("5_Invert_axis"))
/*     */         {
/* 523 */           this.turnAxisInvert = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 526 */         else if (line.startsWith("6_Controller_index"))
/*     */         {
/* 528 */           this.shootControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 531 */         else if (line.startsWith("6_Button_shoot_index"))
/*     */         {
/* 533 */           this.shootButtonIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 536 */         else if (line.startsWith("7_Controller_index"))
/*     */         {
/* 538 */           this.wModeControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 541 */         else if (line.startsWith("7_Button_weapon_mode_index"))
/*     */         {
/* 543 */           this.wModeButtonIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 546 */         else if (line.startsWith("8_Controller_index"))
/*     */         {
/* 548 */           this.resetControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 551 */         else if (line.startsWith("8_Button_reset_index"))
/*     */         {
/* 553 */           this.resetButtonIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */ 
/*     */         }
/* 557 */         else if (line.startsWith("9_Controller_index"))
/*     */         {
/* 559 */           this.jumpControllerIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */ 
/*     */         }
/* 562 */         else if (line.startsWith("9_Button_jump_index"))
/*     */         {
/* 564 */           this.jumpButtonIndex = Integer.valueOf(line.split("=")[1]).intValue();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 569 */       reader.close();
/* 570 */       inputstream.close();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 574 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean getButtonState(int controllerIndex, int buttonIndex)
/*     */   {
/* 580 */     if ((controllerIndex == -1) || (buttonIndex == -1) || (!this.controllerEnabled))
/*     */     {
/* 582 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 586 */     return Controllers.getController(controllerIndex).isButtonPressed(buttonIndex);
/*     */   }
/*     */   
/*     */ 
/*     */   private float getAxisValue(int controllerIndex, int axisIndex, int invertAxis)
/*     */   {
/* 592 */     if ((controllerIndex == -1) || (axisIndex == -1))
/*     */     {
/* 594 */       return 0.0F;
/*     */     }
/*     */     
/*     */ 
/* 598 */     return Controllers.getController(controllerIndex).getAxisValue(axisIndex) * invertAxis;
/*     */   }
/*     */   
/*     */ 
/*     */   private float offSetValue(float value, int dir)
/*     */   {
/* 604 */     if (value == 0.0F)
/*     */     {
/* 606 */       return value + 0.2F * dir;
/*     */     }
/* 608 */     if (value > 0.0F)
/*     */     {
/* 610 */       return Math.min(value + 0.2F * dir, 1.0F);
/*     */     }
/*     */     
/*     */ 
/* 614 */     return Math.max(value + 0.2F * dir, -1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   private float zeroValue(float value)
/*     */   {
/* 620 */     if (value > 0.0F)
/*     */     {
/* 622 */       return Math.max(value - 0.2F, 0.0F);
/*     */     }
/* 624 */     if (value < 0.0F)
/*     */     {
/* 626 */       return Math.min(value + 0.2F, 0.0F);
/*     */     }
/*     */     
/* 629 */     return 0.0F;
/*     */   }
/*     */   
/*     */   private float powerOffSetValue(float value, int dir)
/*     */   {
/* 634 */     return Math.min(Math.max(value + 0.04F * dir, 0.0F), 1.0F);
/*     */   }
/*     */   
/*     */   public static void resetControls()
/*     */   {
/* 639 */     absPowerMovement = 0.0F;
/* 640 */     powerMovement = 0.0F;
/* 641 */     yawMovement = 0.0F;
/* 642 */     turnMovement = 0.0F;
/* 643 */     rollMovement = 0.0F;
/* 644 */     pitchMovement = 0.0F;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/KeyHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */