/*     */ package RCM.PropertiesLoader;
/*     */ 
/*     */ import RCM.RCM_Main;
/*     */ import com.bulletphysics.collision.shapes.BoxShape;
/*     */ import com.bulletphysics.collision.shapes.CollisionShape;
/*     */ import com.bulletphysics.collision.shapes.CompoundShape;
/*     */ import com.bulletphysics.collision.shapes.CylinderShape;
/*     */ import com.bulletphysics.linearmath.Transform;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.vecmath.AxisAngle4f;
/*     */ import javax.vecmath.Quat4f;
/*     */ import javax.vecmath.Vector3f;
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
/*     */ public class PropertiesLoader
/*     */ {
/*     */   private CompoundShape entityShape;
/*     */   private CompoundShape dynamicShape;
/*  37 */   private float[] propteries = new float[10];
/*     */   private String fileName;
/*  39 */   private List<MotorProperties> motors = new ArrayList();
/*  40 */   private List<WheelProperties> wheels = new ArrayList();
/*  41 */   private List<WingProperties> wings = new ArrayList();
/*  42 */   private List<RotaryWingProperties> rotaryWings = new ArrayList();
/*  43 */   private List<AutoControlProperties> sensors = new ArrayList();
/*  44 */   private List<AttachmentProperties> attachments = new ArrayList();
/*  45 */   private List<RocketMotorProperties> rocketMotors = new ArrayList();
/*  46 */   private List<FloatsProperties> floats = new ArrayList();
/*     */   private float dragFactor;
/*     */   private float inertiaScaleFactor;
/*     */   
/*     */   public void init(String name) throws FileNotFoundException, IOException
/*     */   {
/*  52 */     this.fileName = name;
/*  53 */     this.entityShape = new CompoundShape();
/*  54 */     this.dynamicShape = new CompoundShape();
/*     */     
/*  56 */     URL url = getClass().getResource(RCM_Main.propertiesFilePath + name);
/*  57 */     InputStream inputstream = url.openStream();
/*  58 */     BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */     
/*     */     String line;
/*     */     
/*  62 */     while ((line = reader.readLine()) != null)
/*     */     {
/*  64 */       if (line.startsWith("Total_mass: "))
/*     */       {
/*  66 */         this.propteries[0] = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */       }
/*  68 */       else if (line.startsWith("Drag_factor: "))
/*     */       {
/*  70 */         this.dragFactor = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */       }
/*  72 */       else if (line.startsWith("Inertia_scale_factor: "))
/*     */       {
/*  74 */         this.inertiaScaleFactor = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */       }
/*  76 */       else if (line.startsWith("Collision_shapes: "))
/*     */       {
/*  78 */         this.propteries[1] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/*  80 */       else if (line.startsWith("Motor_number: "))
/*     */       {
/*  82 */         this.propteries[2] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/*  84 */       else if (line.startsWith("Wheel_number: "))
/*     */       {
/*  86 */         this.propteries[3] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/*  88 */       else if (line.startsWith("Wing_number: "))
/*     */       {
/*  90 */         this.propteries[4] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/*  92 */       else if (line.startsWith("Propeller_number: "))
/*     */       {
/*  94 */         this.propteries[5] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/*  96 */       else if (line.startsWith("Sensor_number: "))
/*     */       {
/*  98 */         this.propteries[6] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/* 100 */       else if (line.startsWith("Attachment_number: "))
/*     */       {
/* 102 */         this.propteries[7] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/* 104 */       else if (line.startsWith("Rocket_motor_number: "))
/*     */       {
/* 106 */         this.propteries[8] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/* 108 */       else if (line.startsWith("Floats_number: "))
/*     */       {
/* 110 */         this.propteries[9] = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */       }
/*     */     }
/*     */     
/* 114 */     reader.close();
/*     */     
/* 116 */     loadCollisionModel();
/* 117 */     loadMotors();
/* 118 */     loadWheels();
/* 119 */     loadWings();
/* 120 */     loadRotaryWings();
/* 121 */     loadAutoControllers();
/* 122 */     loadMissiles();
/* 123 */     loadRocketMotors();
/* 124 */     loadFloats();
/*     */   }
/*     */   
/*     */   private void loadCollisionModel()
/*     */     throws FileNotFoundException, IOException
/*     */   {
/* 130 */     List<CollisionShape> dynShape = new ArrayList();
/* 131 */     List<Transform> dynShapeTrans = new ArrayList();
/*     */     
/*     */ 
/* 134 */     CollisionShape shape = null;
/*     */     
/* 136 */     Transform shapeTrans = new Transform();
/*     */     
/* 138 */     URL url = null;
/* 139 */     InputStream inputstream = null;
/* 140 */     BufferedReader reader = null;
/*     */     
/*     */ 
/*     */ 
/* 144 */     for (int i = 1; i <= this.propteries[1]; i++)
/*     */     {
/* 146 */       url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 147 */       inputstream = url.openStream();
/* 148 */       reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */       
/* 150 */       shapeTrans.setIdentity();
/*     */       
/* 152 */       int shapeType = 1;
/*     */       String line;
/* 154 */       while ((line = reader.readLine()) != null)
/*     */       {
/* 156 */         if (line.startsWith("ShapeB_" + i + ": "))
/*     */         {
/* 158 */           float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 159 */           float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 160 */           float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */           
/* 162 */           Vector3f sShape = new Vector3f(x, y, z);
/* 163 */           shape = new BoxShape(sShape);
/*     */         }
/* 165 */         else if (line.startsWith("ShapeC_" + i + ": "))
/*     */         {
/* 167 */           float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 168 */           float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 169 */           float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */           
/* 171 */           Vector3f sShape = new Vector3f(x, y, z);
/* 172 */           shape = new CylinderShape(sShape);
/*     */ 
/*     */         }
/* 175 */         else if (line.startsWith("ShapeDC_" + i + ": "))
/*     */         {
/* 177 */           float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 178 */           float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 179 */           float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */           
/* 181 */           Vector3f sShape = new Vector3f(x, y, z);
/* 182 */           shape = new CylinderShape(sShape);
/*     */           
/* 184 */           shapeType = 2;
/*     */         }
/* 186 */         else if (line.startsWith("Shape_rotation_" + i + ": "))
/*     */         {
/* 188 */           float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 189 */           float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 190 */           float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 191 */           float w = Float.valueOf(line.split(" ")[4]).floatValue();
/*     */           
/* 193 */           shapeTrans.setRotation(new Quat4f(x, y, z, w));
/*     */         }
/* 195 */         else if (line.startsWith("Shape_position_" + i + ": "))
/*     */         {
/* 197 */           float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 198 */           float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 199 */           float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */           
/* 201 */           Vector3f sOrigin = new Vector3f(x, y, z);
/* 202 */           shapeTrans.origin.set(sOrigin);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 207 */       reader.close();
/*     */       
/* 209 */       if (shapeType == 1)
/*     */       {
/* 211 */         this.entityShape.addChildShape(shapeTrans, shape);
/*     */       }
/*     */       else
/*     */       {
/* 215 */         this.dynamicShape.addChildShape(shapeTrans, shape);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadMotors()
/*     */     throws FileNotFoundException, IOException
/*     */   {
/* 223 */     if (this.propteries[2] > 0.0F)
/*     */     {
/* 225 */       URL url = null;
/* 226 */       InputStream inputstream = null;
/* 227 */       BufferedReader reader = null;
/*     */       
/*     */ 
/* 230 */       float motorMass = 0.0F;
/* 231 */       float motorDiameter = 0.0F;
/* 232 */       float motorPower = 0.0F;
/* 233 */       float motorKVConstant = 0.0F;
/* 234 */       float motorInputVolts = 0.0F;
/* 235 */       float gearRatio = 1.0F;
/* 236 */       int controlChannel = 0;
/* 237 */       int motorSensor = 0;
/* 238 */       int motorID = 0;
/*     */       
/* 240 */       for (int i = 1; i <= this.propteries[2]; i++)
/*     */       {
/* 242 */         url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 243 */         inputstream = url.openStream();
/* 244 */         reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */         String line;
/* 246 */         while ((line = reader.readLine()) != null)
/*     */         {
/* 248 */           if (line.startsWith("Motor_ID_" + i + ": "))
/*     */           {
/* 250 */             motorID = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 252 */           else if (line.startsWith("Motor_mass_" + i + ": "))
/*     */           {
/* 254 */             motorMass = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 256 */           else if (line.startsWith("Motor_diameter_" + i + ": "))
/*     */           {
/* 258 */             motorDiameter = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 260 */           else if (line.startsWith("Motor_power_" + i + ": "))
/*     */           {
/* 262 */             motorPower = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 264 */           else if (line.startsWith("Motor_kV_" + i + ": "))
/*     */           {
/* 266 */             motorKVConstant = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 268 */           else if (line.startsWith("Motor_input_volt_" + i + ": "))
/*     */           {
/* 270 */             motorInputVolts = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 272 */           else if (line.startsWith("Motor_gear_ratio_" + i + ": "))
/*     */           {
/* 274 */             gearRatio = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 276 */           else if (line.startsWith("Motor_sensor_ID_" + i + ": "))
/*     */           {
/* 278 */             motorSensor = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 280 */           else if (line.startsWith("Motor_control_channel_" + i + ": "))
/*     */           {
/* 282 */             controlChannel = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 287 */         this.motors.add(new MotorProperties(motorID, motorMass, motorDiameter, motorPower, motorKVConstant, motorInputVolts, gearRatio, motorSensor, controlChannel));
/*     */         
/* 289 */         reader.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadWheels() throws FileNotFoundException, IOException
/*     */   {
/* 296 */     if (this.propteries[3] > 0.0F)
/*     */     {
/*     */ 
/* 299 */       Vector3f wheelDirection = null;
/* 300 */       Vector3f wheelAxle = null;
/* 301 */       Vector3f connectionPoint = null;
/*     */       
/* 303 */       int wheelID = 0;
/* 304 */       int controlChannel = 0;
/*     */       
/* 306 */       float wheelOffset = 0.0F;
/* 307 */       float radius = 0.0F;
/* 308 */       float frictionSlip = 0.0F;
/* 309 */       float brakeForce = 0.0F;
/* 310 */       boolean canTurn = false;
/*     */       
/* 312 */       float rollInfluence = 0.0F;
/* 313 */       float suspensionStiffness = 0.0F;
/* 314 */       float suspensionDamping = 0.0F;
/* 315 */       float suspensionCompression = 0.0F;
/* 316 */       float suspensionRestLength = 0.0F;
/* 317 */       float wheelTurn = 0.0F;
/*     */       
/* 319 */       for (int i = 1; i <= this.propteries[3]; i++)
/*     */       {
/* 321 */         URL url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 322 */         InputStream inputstream = url.openStream();
/* 323 */         BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */         
/*     */         String line;
/*     */         
/* 327 */         while ((line = reader.readLine()) != null)
/*     */         {
/* 329 */           if (line.startsWith("Wheel_ID_" + i + ": "))
/*     */           {
/* 331 */             wheelID = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 333 */           else if (line.startsWith("Wheel_direction_" + i + ": "))
/*     */           {
/* 335 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 336 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 337 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 338 */             wheelDirection = new Vector3f(x, y, z);
/*     */           }
/* 340 */           else if (line.startsWith("Wheel_axle_" + i + ": "))
/*     */           {
/* 342 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 343 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 344 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 345 */             wheelAxle = new Vector3f(x, y, z);
/*     */           }
/* 347 */           else if (line.startsWith("Wheel_position_" + i + ": "))
/*     */           {
/* 349 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 350 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 351 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 352 */             connectionPoint = new Vector3f(x, y, z);
/*     */           }
/* 354 */           else if (line.startsWith("Wheel_offset_" + i + ": "))
/*     */           {
/* 356 */             wheelOffset = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 358 */           else if (line.startsWith("Wheel_radius_" + i + ": "))
/*     */           {
/* 360 */             radius = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 362 */           else if (line.startsWith("Wheel_steerable_" + i + ": "))
/*     */           {
/* 364 */             canTurn = Boolean.valueOf(line.split(" ")[1]).booleanValue();
/*     */           }
/* 366 */           else if (line.startsWith("Wheel_friction_" + i + ": "))
/*     */           {
/* 368 */             frictionSlip = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 370 */           else if (line.startsWith("Wheel_brake_" + i + ": "))
/*     */           {
/* 372 */             brakeForce = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 374 */           else if (line.startsWith("Wheel_roll_influence_" + i + ": "))
/*     */           {
/* 376 */             rollInfluence = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 378 */           else if (line.startsWith("Wheel_suspension_stiffness_" + i + ": "))
/*     */           {
/* 380 */             suspensionStiffness = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 382 */           else if (line.startsWith("Wheel_suspension_damping_" + i + ": "))
/*     */           {
/* 384 */             suspensionDamping = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 386 */           else if (line.startsWith("Wheel_suspension_compression_" + i + ": "))
/*     */           {
/* 388 */             suspensionCompression = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 390 */           else if (line.startsWith("Wheel_suspension_rest_length_" + i + ": "))
/*     */           {
/* 392 */             suspensionRestLength = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 394 */           else if (line.startsWith("Wheel_max_turn_" + i + ": "))
/*     */           {
/* 396 */             wheelTurn = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 398 */           else if (line.startsWith("Wheel_control_channel_" + i + ": "))
/*     */           {
/* 400 */             controlChannel = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 405 */         this.wheels.add(new WheelProperties(wheelID, connectionPoint, wheelDirection, wheelAxle, wheelOffset, suspensionRestLength, radius, frictionSlip, rollInfluence, suspensionStiffness, suspensionDamping, suspensionCompression, brakeForce, canTurn, wheelTurn, controlChannel));
/*     */         
/*     */ 
/*     */ 
/* 409 */         reader.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadWings() throws FileNotFoundException, IOException
/*     */   {
/* 416 */     if (this.propteries[4] > 0.0F)
/*     */     {
/* 418 */       Vector3f spanVec = null;
/* 419 */       Vector3f liftVec = null;
/* 420 */       Vector3f chordVec = null;
/* 421 */       Vector3f position = null;
/*     */       
/* 423 */       int wingID = 0;
/* 424 */       float area = 0.0F;
/* 425 */       float span = 0.0F;
/* 426 */       float root = 0.0F;
/* 427 */       float tip = 0.0F;
/* 428 */       float def = 0.0F;
/* 429 */       float defOffset = 0.0F;
/* 430 */       int profileType = 0;
/* 431 */       int channel = 0;
/* 432 */       int sensorID = 0;
/* 433 */       int sections = 1;
/*     */       
/* 435 */       URL url = null;
/* 436 */       InputStream inputstream = null;
/* 437 */       BufferedReader reader = null;
/*     */       
/* 439 */       for (int i = 1; i <= this.propteries[4]; i++)
/*     */       {
/* 441 */         url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 442 */         inputstream = url.openStream();
/* 443 */         reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */         
/*     */         String line;
/* 446 */         while ((line = reader.readLine()) != null)
/*     */         {
/* 448 */           if (line.startsWith("Wing_ID_" + i + ": "))
/*     */           {
/* 450 */             wingID = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 452 */           else if (line.startsWith("Wing_span_vect_" + i + ": "))
/*     */           {
/* 454 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 455 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 456 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */             
/* 458 */             spanVec = new Vector3f(x, y, z);
/*     */           }
/* 460 */           else if (line.startsWith("Wing_lift_vect_" + i + ": "))
/*     */           {
/* 462 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 463 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 464 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */             
/* 466 */             liftVec = new Vector3f(x, y, z);
/*     */           }
/* 468 */           else if (line.startsWith("Wing_chord_vect_" + i + ": "))
/*     */           {
/* 470 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 471 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 472 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */             
/* 474 */             chordVec = new Vector3f(x, y, z);
/*     */           }
/* 476 */           else if (line.startsWith("Wing_position_" + i + ": "))
/*     */           {
/* 478 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 479 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 480 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 481 */             position = new Vector3f(x, y, z);
/*     */           }
/* 483 */           else if (line.startsWith("Wing_area_" + i + ": "))
/*     */           {
/* 485 */             area = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 487 */           else if (line.startsWith("Wing_span_" + i + ": "))
/*     */           {
/* 489 */             span = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 491 */           else if (line.startsWith("Wing_chord_root_" + i + ": "))
/*     */           {
/* 493 */             root = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 495 */           else if (line.startsWith("Wing_chord_tip_" + i + ": "))
/*     */           {
/* 497 */             tip = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 499 */           else if (line.startsWith("Wing_deflection_" + i + ": "))
/*     */           {
/* 501 */             def = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 503 */           else if (line.startsWith("Wing_deflection_offset_" + i + ": "))
/*     */           {
/* 505 */             defOffset = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 507 */           else if (line.startsWith("Wing_profile_" + i + ": "))
/*     */           {
/* 509 */             profileType = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 511 */           else if (line.startsWith("Wing_sensor_ID_" + i + ": "))
/*     */           {
/* 513 */             sensorID = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 515 */           else if (line.startsWith("Wing_sections_" + i + ": "))
/*     */           {
/* 517 */             sections = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 519 */           else if (line.startsWith("Wing_control_channel_" + i + ": "))
/*     */           {
/* 521 */             channel = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 526 */         this.wings.add(new WingProperties(wingID, spanVec, liftVec, chordVec, position, area, span, root, tip, def, defOffset, profileType, sensorID, sections, channel));
/*     */         
/*     */ 
/*     */ 
/* 530 */         reader.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadRotaryWings() throws FileNotFoundException, IOException
/*     */   {
/* 537 */     if (this.propteries[5] > 0.0F)
/*     */     {
/*     */ 
/* 540 */       Vector3f spanVec = null;
/* 541 */       Vector3f liftVec = null;
/* 542 */       Vector3f chordVec = null;
/* 543 */       Vector3f position = null;
/*     */       
/* 545 */       int propID = 0;
/* 546 */       float mass = 0.0F;
/* 547 */       float area = 0.0F;
/* 548 */       float span = 0.0F;
/* 549 */       float root = 0.0F;
/* 550 */       float tip = 0.0F;
/* 551 */       float hubOffset = 0.0F;
/* 552 */       float def = 0.0F;
/* 553 */       float defOffset = 0.0F;
/* 554 */       float ratio = 1.0F;
/* 555 */       float eqFactor = 1.0F;
/* 556 */       int profileType = 0;
/* 557 */       int channel = 0;
/* 558 */       int sensorID = 0;
/* 559 */       int sections = 1;
/*     */       
/* 561 */       URL url = null;
/* 562 */       InputStream inputstream = null;
/* 563 */       BufferedReader reader = null;
/*     */       
/* 565 */       for (int i = 1; i <= this.propteries[5]; i++)
/*     */       {
/* 567 */         url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 568 */         inputstream = url.openStream();
/* 569 */         reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */         
/*     */         String line;
/* 572 */         while ((line = reader.readLine()) != null)
/*     */         {
/*     */ 
/* 575 */           if (line.startsWith("Prop_ID_" + i + ": "))
/*     */           {
/* 577 */             propID = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 579 */           else if (line.startsWith("Prop_mass_" + i + ": "))
/*     */           {
/* 581 */             mass = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 583 */           else if (line.startsWith("Prop_gear_ratio_" + i + ": "))
/*     */           {
/* 585 */             ratio = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 587 */           else if (line.startsWith("Prop_Prop_equivalent_factor_" + i + ": "))
/*     */           {
/* 589 */             eqFactor = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 591 */           else if (line.startsWith("Prop_span_vect_" + i + ": "))
/*     */           {
/* 593 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 594 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 595 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */             
/* 597 */             spanVec = new Vector3f(x, y, z);
/*     */           }
/* 599 */           else if (line.startsWith("Prop_lift_vect_" + i + ": "))
/*     */           {
/* 601 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 602 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 603 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */             
/* 605 */             liftVec = new Vector3f(x, y, z);
/*     */           }
/* 607 */           else if (line.startsWith("Prop_chord_vect_" + i + ": "))
/*     */           {
/* 609 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 610 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 611 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */             
/* 613 */             chordVec = new Vector3f(x, y, z);
/*     */           }
/* 615 */           else if (line.startsWith("Prop_position_" + i + ": "))
/*     */           {
/* 617 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 618 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 619 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */             
/* 621 */             position = new Vector3f(x, y, z);
/*     */           }
/* 623 */           else if (line.startsWith("Prop_offset_" + i + ": "))
/*     */           {
/* 625 */             hubOffset = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 627 */           else if (line.startsWith("Prop_area_" + i + ": "))
/*     */           {
/* 629 */             area = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 631 */           else if (line.startsWith("Prop_span_" + i + ": "))
/*     */           {
/* 633 */             span = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 635 */           else if (line.startsWith("Prop_chord_root_" + i + ": "))
/*     */           {
/* 637 */             root = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 639 */           else if (line.startsWith("Prop_chord_tip_" + i + ": "))
/*     */           {
/* 641 */             tip = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 643 */           else if (line.startsWith("Prop_deflection_" + i + ": "))
/*     */           {
/* 645 */             def = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 647 */           else if (line.startsWith("Prop_deflection_offset_" + i + ": "))
/*     */           {
/* 649 */             defOffset = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 651 */           else if (line.startsWith("Prop_profile_" + i + ": "))
/*     */           {
/* 653 */             profileType = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 655 */           else if (line.startsWith("Prop_sensor_ID_" + i + ": "))
/*     */           {
/* 657 */             sensorID = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 659 */           else if (line.startsWith("Prop_sections_" + i + ": "))
/*     */           {
/* 661 */             sections = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 663 */           else if (line.startsWith("Prop_control_channel_" + i + ": "))
/*     */           {
/* 665 */             channel = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 670 */         this.rotaryWings.add(new RotaryWingProperties(propID, mass, ratio, eqFactor, spanVec, liftVec, chordVec, position, hubOffset, area, span, root, tip, def, defOffset, profileType, sensorID, sections, channel));
/*     */         
/*     */ 
/* 673 */         reader.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadAutoControllers() throws FileNotFoundException, IOException
/*     */   {
/* 680 */     if (this.propteries[6] > 0.0F)
/*     */     {
/* 682 */       Vector3f sensorPoint = null;
/*     */       
/* 684 */       float linGain = 0.0F;
/* 685 */       float linLimit = 0.0F;
/* 686 */       float angGain = 0.0F;
/* 687 */       float angLimit = 0.0F;
/* 688 */       int sensorID = 0;
/*     */       
/* 690 */       URL url = null;
/* 691 */       InputStream inputstream = null;
/* 692 */       BufferedReader reader = null;
/*     */       
/* 694 */       for (int i = 1; i <= this.propteries[6]; i++)
/*     */       {
/* 696 */         url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 697 */         inputstream = url.openStream();
/* 698 */         reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */         
/*     */         String line;
/* 701 */         while ((line = reader.readLine()) != null)
/*     */         {
/* 703 */           if (line.startsWith("Sensor_ID_" + i + ": "))
/*     */           {
/* 705 */             sensorID = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/* 707 */           else if (line.startsWith("Sensor_point_" + i + ": "))
/*     */           {
/* 709 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 710 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 711 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */             
/* 713 */             sensorPoint = new Vector3f(x, y, z);
/*     */           }
/* 715 */           else if (line.startsWith("Sensor_linear_gain_" + i + ": "))
/*     */           {
/* 717 */             linGain = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 719 */           else if (line.startsWith("Sensor_linear_limit_" + i + ": "))
/*     */           {
/* 721 */             linLimit = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 723 */           else if (line.startsWith("Sensor_angular_gain_" + i + ": "))
/*     */           {
/* 725 */             angGain = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 727 */           else if (line.startsWith("Sensor_angular_limit_" + i + ": "))
/*     */           {
/* 729 */             angLimit = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 734 */         this.sensors.add(new AutoControlProperties(sensorID, sensorPoint, linGain, linLimit, angGain, angLimit));
/*     */         
/*     */ 
/* 737 */         reader.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadMissiles() throws FileNotFoundException, IOException
/*     */   {
/* 744 */     if (this.propteries[7] > 0.0F)
/*     */     {
/*     */ 
/* 747 */       Vector3f position = null;
/* 748 */       AxisAngle4f rotate = null;
/* 749 */       int type = 0;
/*     */       
/* 751 */       for (int i = 1; i <= this.propteries[7]; i++)
/*     */       {
/* 753 */         URL url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 754 */         InputStream inputstream = url.openStream();
/* 755 */         BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */         
/*     */         String line;
/*     */         
/* 759 */         while ((line = reader.readLine()) != null)
/*     */         {
/* 761 */           if (line.startsWith("Attachment_position_" + i + ": "))
/*     */           {
/* 763 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 764 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 765 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 766 */             position = new Vector3f(x, y, z);
/*     */           }
/* 768 */           else if (line.startsWith("Attachment_orientation_" + i + ": "))
/*     */           {
/* 770 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 771 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 772 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 773 */             float w = Float.valueOf(line.split(" ")[4]).floatValue();
/* 774 */             rotate = new AxisAngle4f(x, y, z, w * -0.017453292F);
/*     */           }
/* 776 */           else if (line.startsWith("Attachment_type_" + i + ": "))
/*     */           {
/* 778 */             type = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/*     */         }
/*     */         
/* 782 */         this.attachments.add(new AttachmentProperties(position, rotate, type));
/*     */         
/* 784 */         reader.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadRocketMotors() throws FileNotFoundException, IOException
/*     */   {
/* 791 */     if (this.propteries[8] > 0.0F)
/*     */     {
/*     */ 
/* 794 */       float thrust = 0.0F;
/* 795 */       float burnTime = 0.0F;
/*     */       
/* 797 */       for (int i = 1; i <= this.propteries[8]; i++)
/*     */       {
/* 799 */         URL url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 800 */         InputStream inputstream = url.openStream();
/* 801 */         BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */         
/*     */         String line;
/*     */         
/* 805 */         while ((line = reader.readLine()) != null)
/*     */         {
/* 807 */           if (line.startsWith("Rocket_motor_thrust_" + i + ": "))
/*     */           {
/* 809 */             thrust = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */ 
/*     */           }
/* 812 */           else if (line.startsWith("Rocket_burn_time_" + i + ": "))
/*     */           {
/* 814 */             burnTime = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 819 */         this.rocketMotors.add(new RocketMotorProperties(thrust, burnTime));
/*     */         
/* 821 */         reader.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadFloats() throws FileNotFoundException, IOException
/*     */   {
/* 828 */     if (this.propteries[9] > 0.0F)
/*     */     {
/* 830 */       Vector3f position = null;
/* 831 */       Vector3f spanVec = null;
/* 832 */       float dragCoef = 0.0F;
/* 833 */       float radius = 0.0F;
/* 834 */       int sects = 0;
/*     */       
/* 836 */       for (int i = 1; i <= this.propteries[9]; i++)
/*     */       {
/* 838 */         URL url = getClass().getResource(RCM_Main.propertiesFilePath + this.fileName);
/* 839 */         InputStream inputstream = url.openStream();
/* 840 */         BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */         
/*     */         String line;
/*     */         
/* 844 */         while ((line = reader.readLine()) != null)
/*     */         {
/* 846 */           if (line.startsWith("Floats_position_" + i + ": "))
/*     */           {
/* 848 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 849 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 850 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 851 */             position = new Vector3f(x, y, z);
/*     */ 
/*     */           }
/* 854 */           else if (line.startsWith("Floats_span_vec_" + i + ": "))
/*     */           {
/* 856 */             float x = Float.valueOf(line.split(" ")[1]).floatValue();
/* 857 */             float y = Float.valueOf(line.split(" ")[2]).floatValue();
/* 858 */             float z = Float.valueOf(line.split(" ")[3]).floatValue();
/* 859 */             spanVec = new Vector3f(x, y, z);
/*     */           }
/* 861 */           else if (line.startsWith("Floats_drag_coeff_" + i + ": "))
/*     */           {
/* 863 */             dragCoef = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 865 */           else if (line.startsWith("Floats_radius_" + i + ": "))
/*     */           {
/* 867 */             radius = Float.valueOf(line.split(" ")[1]).floatValue();
/*     */           }
/* 869 */           else if (line.startsWith("Floats_sections_" + i + ": "))
/*     */           {
/* 871 */             sects = Integer.valueOf(line.split(" ")[1]).intValue();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 876 */         this.floats.add(new FloatsProperties(position, spanVec, dragCoef, radius, sects));
/*     */         
/* 878 */         reader.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public float[] getProperties()
/*     */   {
/* 885 */     return this.propteries;
/*     */   }
/*     */   
/*     */   public CompoundShape getCollisionShapes()
/*     */   {
/* 890 */     return this.entityShape;
/*     */   }
/*     */   
/*     */   public CompoundShape getDynamicShapes()
/*     */   {
/* 895 */     return this.dynamicShape;
/*     */   }
/*     */   
/*     */   public float getInertiaScaleFactor()
/*     */   {
/* 900 */     return this.inertiaScaleFactor;
/*     */   }
/*     */   
/*     */   public List<MotorProperties> getMotorProperties()
/*     */   {
/* 905 */     return this.motors;
/*     */   }
/*     */   
/*     */   public List<WheelProperties> getWheelProperties()
/*     */   {
/* 910 */     return this.wheels;
/*     */   }
/*     */   
/*     */   public List<WingProperties> getWingProperties()
/*     */   {
/* 915 */     return this.wings;
/*     */   }
/*     */   
/*     */   public List<RotaryWingProperties> getRotaryWingProperties()
/*     */   {
/* 920 */     return this.rotaryWings;
/*     */   }
/*     */   
/*     */   public List<AutoControlProperties> getAutoControlProperties()
/*     */   {
/* 925 */     return this.sensors;
/*     */   }
/*     */   
/*     */   public List<AttachmentProperties> getAttachments()
/*     */   {
/* 930 */     return this.attachments;
/*     */   }
/*     */   
/*     */   public List<RocketMotorProperties> getRocketMotors()
/*     */   {
/* 935 */     return this.rocketMotors;
/*     */   }
/*     */   
/*     */   public List<FloatsProperties> getFloats()
/*     */   {
/* 940 */     return this.floats;
/*     */   }
/*     */   
/*     */   public float getDragFactor()
/*     */   {
/* 945 */     return this.dragFactor;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/PropertiesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */