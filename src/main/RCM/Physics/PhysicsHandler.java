/*      */ package RCM.Physics;
/*      */ 
/*      */ import RCM.Entities.CameraHandler;
/*      */ import RCM.Entities.EntityBoat;
/*      */ import RCM.Entities.EntityDrone;
/*      */ import RCM.Entities.EntityHeli;
/*      */ import RCM.Entities.EntityMissile;
/*      */ import RCM.Entities.EntitySubmarine;
/*      */ import RCM.Entities.EntityTrainerPlane;
/*      */ import RCM.Entities.GlobalEntity;
/*      */ import RCM.KeyHandler;
/*      */ import RCM.Packets.MessageEntityMissle;
/*      */ import RCM.Packets.MessageHandler;
/*      */ import RCM.PropertiesLoader.AttachmentProperties;
/*      */ import RCM.PropertiesLoader.AutoControlProperties;
/*      */ import RCM.PropertiesLoader.FloatsProperties;
/*      */ import RCM.PropertiesLoader.MotorProperties;
/*      */ import RCM.PropertiesLoader.PropertiesLoader;
/*      */ import RCM.PropertiesLoader.RocketMotorProperties;
/*      */ import RCM.PropertiesLoader.RotaryWingProperties;
/*      */ import RCM.PropertiesLoader.WheelProperties;
/*      */ import RCM.PropertiesLoader.WingProperties;
/*      */ import RCM.RCM_Main;
/*      */ import com.bulletphysics.collision.shapes.CollisionShape;
/*      */ import com.bulletphysics.collision.shapes.CompoundShape;
/*      */ import com.bulletphysics.dynamics.DynamicsWorld;
/*      */ import com.bulletphysics.dynamics.RigidBody;
/*      */ import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
/*      */ import com.bulletphysics.dynamics.constraintsolver.Generic6DofConstraint;
/*      */ import com.bulletphysics.dynamics.constraintsolver.TypedConstraint;
/*      */ import com.bulletphysics.dynamics.vehicle.DefaultVehicleRaycaster;
/*      */ import com.bulletphysics.dynamics.vehicle.RaycastVehicle;
/*      */ import com.bulletphysics.dynamics.vehicle.VehicleTuning;
/*      */ import com.bulletphysics.dynamics.vehicle.WheelInfo;
/*      */ import com.bulletphysics.linearmath.DefaultMotionState;
/*      */ import com.bulletphysics.linearmath.MotionState;
/*      */ import com.bulletphysics.linearmath.Transform;
/*      */ import com.bulletphysics.util.ObjectArrayList;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import javax.vecmath.AxisAngle4f;
/*      */ import javax.vecmath.Matrix4f;
/*      */ import javax.vecmath.Quat4f;
/*      */ import javax.vecmath.Vector3f;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PhysicsHandler
/*      */ {
/*   82 */   public List<MotorHandler> motors = new ArrayList();
/*   83 */   public List<WheelHandler> wheels = new ArrayList();
/*   84 */   public List<WingHandler> wings = new ArrayList();
/*   85 */   public List<RotaryWingHandler> rotaryWings = new ArrayList();
/*   86 */   public List<AutoControlHandler> sensors = new ArrayList();
/*   87 */   public List<AttachmentHandler> attachments = new ArrayList();
/*   88 */   public List<RocketMotorHandler> rocketMotors = new ArrayList();
/*   89 */   public List<FloatsHandler> floats = new ArrayList();
/*   90 */   public List<Force> forces = new ArrayList();
/*   91 */   public List<Vector3f> torques = new ArrayList();
/*      */   public RigidBody entityBody;
/*      */   public RaycastVehicle vehicle;
/*      */   private PhysicsHelper helper;
/*      */   public VirtualReferenceHandler virtualReference;
/*   96 */   public List<Entity> visibleEntities = Lists.newArrayList();
/*   97 */   public List<Entity> inRadarEntities = Lists.newArrayList();
/*      */   
/*      */   public Entity lockedEntity;
/*      */   private int lockedEntityID;
/*      */   private float unlockCooldown;
/*  102 */   public boolean requestCollisionShapes = false;
/*      */   private boolean jump;
/*      */   private Vector3f linearVel;
/*      */   private Vector3f rotationalVel;
/*      */   private Vector3f position;
/*      */   private Vector3f frontPath;
/*      */   private Vector3f targetPos;
/*      */   private Quat4f localQuat;
/*      */   private float powerReq;
/*      */   private PropertiesLoader loader;
/*      */   private float density;
/*  113 */   private float dragFactor; public float pitchAngle; public float rollAngle; public float yawAngle; private float[] controlChannels = new float[27];
/*      */   
/*      */ 
/*      */   private boolean motorActive;
/*      */   
/*      */ 
/*      */   public boolean weaponsMode;
/*      */   
/*      */   private boolean canChangeMode;
/*      */   
/*      */   private boolean canFireMissile;
/*      */   
/*      */   private boolean releaseWeapons;
/*      */   
/*      */   private float releaseWeaponTimer;
/*      */   
/*      */   private float altitude;
/*      */   
/*      */   private float prevDist;
/*      */   
/*      */   private int weaponCount;
/*      */   
/*      */   private float floatDensity;
/*      */   
/*      */   private Vector3f Forward;
/*      */   
/*      */   private Vector3f Up;
/*      */   
/*      */   private Vector3f Left;
/*      */   
/*      */ 
/*      */   public PhysicsHandler(int ID, Vector3f pos, float spawnRotation, GlobalEntity rcentity)
/*      */   {
/*  146 */     this.localQuat = new Quat4f();
/*  147 */     this.linearVel = new Vector3f();
/*  148 */     this.rotationalVel = new Vector3f();
/*  149 */     this.position = new Vector3f(pos);
/*  150 */     this.frontPath = new Vector3f();
/*  151 */     this.virtualReference = new VirtualReferenceHandler();
/*      */     
/*  153 */     setEntity(ID);
/*  154 */     setCollisionModel(this.position, spawnRotation, rcentity);
/*  155 */     setMotors();
/*  156 */     setWheels();
/*  157 */     setWings();
/*  158 */     setRotaryWings();
/*  159 */     setAutoControllers();
/*  160 */     setAttachments(rcentity);
/*  161 */     setRocketMotors();
/*  162 */     setFloats();
/*      */     
/*  164 */     this.helper = new PhysicsHelper();
/*  165 */     this.dragFactor = this.loader.getDragFactor();
/*  166 */     this.motorActive = true;
/*  167 */     this.canChangeMode = true;
/*  168 */     this.releaseWeaponTimer = 0.0F;
/*  169 */     this.unlockCooldown = 5.0F;
/*  170 */     this.floatDensity = 997.0F;
/*      */   }
/*      */   
/*      */   private void setEntity(int ID)
/*      */   {
/*  175 */     switch (ID)
/*      */     {
/*      */     case 1: 
/*  178 */       this.loader = RCM_Main.planeProperies;
/*  179 */       break;
/*      */     case 2: 
/*  181 */       this.loader = RCM_Main.droneProperies;
/*  182 */       break;
/*      */     case 3: 
/*  184 */       this.loader = RCM_Main.carProperies;
/*  185 */       break;
/*      */     case 4: 
/*  187 */       this.loader = RCM_Main.f22Properies;
/*  188 */       break;
/*      */     case 5: 
/*  190 */       this.loader = RCM_Main.heliProperies;
/*  191 */       break;
/*      */     case 6: 
/*  193 */       this.loader = RCM_Main.missleProperies;
/*  194 */       break;
/*      */     case 7: 
/*  196 */       this.loader = RCM_Main.boatProperies;
/*  197 */       break;
/*      */     case 8: 
/*  199 */       this.loader = RCM_Main.submarineProperies;
/*  200 */       break;
/*      */     case 9: 
/*  202 */       this.loader = RCM_Main.stuntplaneProperies;
/*  203 */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */   private void setCollisionModel(Vector3f position, float spawnRotation, GlobalEntity entity)
/*      */   {
/*  211 */     AxisAngle4f initRotate = new AxisAngle4f(0.0F, 1.0F, 0.0F, spawnRotation);
/*  212 */     Quat4f localQuat = new Quat4f();
/*  213 */     localQuat.set(initRotate);
/*      */     
/*  215 */     Transform trans = new Transform(new Matrix4f(localQuat, position, 1.0F));
/*      */     
/*  217 */     this.entityBody = createRigidBody(this.loader.getProperties()[0], this.loader.getInertiaScaleFactor(), trans, this.loader.getCollisionShapes(), entity instanceof EntityMissile);
/*      */     
/*  219 */     if (this.loader.getDynamicShapes().getNumChildShapes() > 0)
/*      */     {
/*  221 */       RigidBody dynBody = createRigidBody(0.05F, this.loader.getInertiaScaleFactor(), trans, this.loader.getDynamicShapes(), false);
/*  222 */       dynBody.setUserPointer(new TimeToBreak(0.125F));
/*      */       
/*  224 */       Transform local = new Transform();
/*  225 */       local.setIdentity();
/*  226 */       local.origin.set(0.0F, 0.0F, 0.0F);
/*      */       
/*  228 */       Generic6DofConstraint contraint = new Generic6DofConstraint(this.entityBody, dynBody, local, local, true);
/*      */       
/*  230 */       float LIFT_EPS = 1.0E-7F;
/*      */       
/*  232 */       contraint.setLimit(0, -LIFT_EPS, LIFT_EPS);
/*  233 */       contraint.setLimit(1, -LIFT_EPS, LIFT_EPS);
/*  234 */       contraint.setLimit(2, -LIFT_EPS, LIFT_EPS);
/*  235 */       contraint.setLimit(3, -LIFT_EPS, LIFT_EPS);
/*  236 */       contraint.setLimit(4, -LIFT_EPS, LIFT_EPS);
/*  237 */       contraint.setLimit(5, -LIFT_EPS, LIFT_EPS);
/*      */       
/*  239 */       contraint.setUserConstraintId(0);
/*      */       
/*  241 */       RCM_Main.physicsWorld.getDynamicsWorld().addConstraint(contraint, true);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private RigidBody createRigidBody(float mass, float inertiaScaleFactor, Transform trans, CollisionShape colShape, boolean isMissile)
/*      */   {
/*  250 */     Vector3f inertia = new Vector3f(0.0F, 0.0F, 0.0F);
/*  251 */     colShape.calculateLocalInertia(mass, inertia);
/*      */     
/*  253 */     inertia.scale(inertiaScaleFactor);
/*      */     
/*  255 */     MotionState motionState = new DefaultMotionState(trans);
/*  256 */     RigidBodyConstructionInfo entityConstructionInfo = new RigidBodyConstructionInfo(mass, motionState, colShape, inertia);
/*      */     
/*  258 */     entityConstructionInfo.angularDamping = 0.95F;
/*  259 */     entityConstructionInfo.restitution = 0.0F;
/*  260 */     entityConstructionInfo.friction = 0.5F;
/*      */     
/*  262 */     RigidBody rb = new RigidBody(entityConstructionInfo);
/*      */     
/*  264 */     if (isMissile)
/*      */     {
/*  266 */       rb.setCollisionFlags(4);
/*      */     }
/*      */     
/*  269 */     RCM_Main.physicsWorld.addRigidBody(rb);
/*      */     
/*  271 */     return rb;
/*      */   }
/*      */   
/*      */   private void setMotors()
/*      */   {
/*  276 */     if (this.loader.getProperties()[2] > 0.0F)
/*      */     {
/*  278 */       for (int i = 0; i < this.loader.getProperties()[2]; i++)
/*      */       {
/*  280 */         this.motors.add(new MotorHandler(((MotorProperties)this.loader.getMotorProperties().get(i)).motorID, 
/*  281 */           ((MotorProperties)this.loader.getMotorProperties().get(i)).mass, 
/*  282 */           ((MotorProperties)this.loader.getMotorProperties().get(i)).diameter, 
/*  283 */           ((MotorProperties)this.loader.getMotorProperties().get(i)).motorPower, 
/*  284 */           ((MotorProperties)this.loader.getMotorProperties().get(i)).motorKVConstant, 
/*  285 */           ((MotorProperties)this.loader.getMotorProperties().get(i)).motorInputVolts, 
/*  286 */           ((MotorProperties)this.loader.getMotorProperties().get(i)).gearRatio, 
/*  287 */           ((MotorProperties)this.loader.getMotorProperties().get(i)).sensorID, 
/*  288 */           ((MotorProperties)this.loader.getMotorProperties().get(i)).controlChannel));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setWheels()
/*      */   {
/*  295 */     if (this.loader.getProperties()[3] > 0.0F)
/*      */     {
/*  297 */       VehicleTuning tuning = new VehicleTuning();
/*  298 */       DefaultVehicleRaycaster vehicleRayCaster = new DefaultVehicleRaycaster(RCM_Main.physicsWorld.getDynamicsWorld());
/*  299 */       this.vehicle = new RaycastVehicle(tuning, this.entityBody, vehicleRayCaster);
/*  300 */       RCM_Main.physicsWorld.addVehicle(this.vehicle);
/*  301 */       this.vehicle.setCoordinateSystem(0, 1, 2);
/*      */       
/*  303 */       for (int i = 0; i < this.loader.getProperties()[3]; i++)
/*      */       {
/*      */ 
/*  306 */         this.vehicle.addWheel(((WheelProperties)this.loader.getWheelProperties().get(i)).connectionPoint, 
/*  307 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).wheelDirection, 
/*  308 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).wheelAxle, 
/*  309 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).suspensionRestLength, 
/*  310 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).radius, tuning, 
/*      */           
/*  312 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).canTurn);
/*      */         
/*  314 */         WheelInfo wheel = this.vehicle.getWheelInfo(i);
/*  315 */         wheel.suspensionStiffness = ((WheelProperties)this.loader.getWheelProperties().get(i)).suspensionStiffness;
/*  316 */         wheel.wheelsDampingRelaxation = ((WheelProperties)this.loader.getWheelProperties().get(i)).suspensionDamping;
/*  317 */         wheel.wheelsDampingCompression = ((WheelProperties)this.loader.getWheelProperties().get(i)).suspensionCompression;
/*  318 */         wheel.rollInfluence = ((WheelProperties)this.loader.getWheelProperties().get(i)).rollInfluence;
/*  319 */         wheel.frictionSlip = ((WheelProperties)this.loader.getWheelProperties().get(i)).frictionSlip;
/*  320 */         wheel.brake = ((WheelProperties)this.loader.getWheelProperties().get(i)).breakForce;
/*      */         
/*  322 */         this.wheels.add(new WheelHandler(((WheelProperties)this.loader.getWheelProperties().get(i)).ID, 
/*  323 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).wheelMaxTurn, 
/*  324 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).canTurn, 
/*  325 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).offSet, 
/*  326 */           ((WheelProperties)this.loader.getWheelProperties().get(i)).channel));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setWings()
/*      */   {
/*  333 */     if (this.loader.getProperties()[4] > 0.0F)
/*      */     {
/*  335 */       for (int i = 0; i < this.loader.getProperties()[4]; i++)
/*      */       {
/*  337 */         this.wings.add(new WingHandler(((WingProperties)this.loader.getWingProperties().get(i)).wingID, 
/*  338 */           ((WingProperties)this.loader.getWingProperties().get(i)).spanVec, 
/*  339 */           ((WingProperties)this.loader.getWingProperties().get(i)).liftVec, 
/*  340 */           ((WingProperties)this.loader.getWingProperties().get(i)).chordVec, 
/*  341 */           ((WingProperties)this.loader.getWingProperties().get(i)).position, 0.0F, 
/*  342 */           ((WingProperties)this.loader.getWingProperties().get(i)).area, 
/*  343 */           ((WingProperties)this.loader.getWingProperties().get(i)).span, 
/*  344 */           ((WingProperties)this.loader.getWingProperties().get(i)).root, 
/*  345 */           ((WingProperties)this.loader.getWingProperties().get(i)).tip, 
/*  346 */           ((WingProperties)this.loader.getWingProperties().get(i)).def, 
/*  347 */           ((WingProperties)this.loader.getWingProperties().get(i)).defOffset, 
/*  348 */           ((WingProperties)this.loader.getWingProperties().get(i)).profileType, 
/*  349 */           ((WingProperties)this.loader.getWingProperties().get(i)).controlChannel, 
/*  350 */           ((WingProperties)this.loader.getWingProperties().get(i)).sections, 1.0F, 
/*      */           
/*  352 */           ((WingProperties)this.loader.getWingProperties().get(i)).sensorID));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setRotaryWings()
/*      */   {
/*  359 */     if (this.loader.getProperties()[5] > 0.0F)
/*      */     {
/*  361 */       for (int i = 0; i < this.loader.getProperties()[5]; i++)
/*      */       {
/*  363 */         this.rotaryWings.add(new RotaryWingHandler(((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).propID, 
/*  364 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).mass, 
/*  365 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).ratio, 
/*  366 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).eqFactor, 
/*  367 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).spanVec, 
/*  368 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).liftVec, 
/*  369 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).chordVec, 
/*  370 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).position, 
/*  371 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).hubOffset, 
/*  372 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).area, 
/*  373 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).span, 
/*  374 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).root, 
/*  375 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).tip, 
/*  376 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).def, 
/*  377 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).defOffset, 
/*  378 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).profileType, 
/*  379 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).controlChannel, 
/*  380 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).sections, 
/*  381 */           ((RotaryWingProperties)this.loader.getRotaryWingProperties().get(i)).sensorID));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setAutoControllers()
/*      */   {
/*  388 */     if (this.loader.getProperties()[6] > 0.0F)
/*      */     {
/*  390 */       for (int i = 0; i < this.loader.getProperties()[6]; i++)
/*      */       {
/*  392 */         this.sensors.add(new AutoControlHandler(((AutoControlProperties)this.loader.getAutoControlProperties().get(i)).ID, 
/*  393 */           ((AutoControlProperties)this.loader.getAutoControlProperties().get(i)).sensorPoint, 
/*  394 */           ((AutoControlProperties)this.loader.getAutoControlProperties().get(i)).linGain, 
/*  395 */           ((AutoControlProperties)this.loader.getAutoControlProperties().get(i)).linLimit, 
/*  396 */           ((AutoControlProperties)this.loader.getAutoControlProperties().get(i)).angGain, 
/*  397 */           ((AutoControlProperties)this.loader.getAutoControlProperties().get(i)).angLimit));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setAttachments(GlobalEntity rcentity)
/*      */   {
/*  404 */     if (this.loader.getProperties()[7] > 0.0F)
/*      */     {
/*      */ 
/*      */ 
/*  408 */       for (int i = 0; i < this.loader.getProperties()[7]; i++)
/*      */       {
/*  410 */         this.attachments.add(new AttachmentHandler(((AttachmentProperties)this.loader.getAttachments().get(i)).position, 
/*  411 */           ((AttachmentProperties)this.loader.getAttachments().get(i)).rotate, 
/*  412 */           ((AttachmentProperties)this.loader.getAttachments().get(i)).type, i, rcentity));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void setRocketMotors()
/*      */   {
/*  420 */     if (this.loader.getProperties()[8] > 0.0F)
/*      */     {
/*  422 */       for (int i = 0; i < this.loader.getProperties()[8]; i++)
/*      */       {
/*  424 */         this.rocketMotors.add(new RocketMotorHandler(((RocketMotorProperties)this.loader.getRocketMotors().get(i)).thurst, 
/*  425 */           ((RocketMotorProperties)this.loader.getRocketMotors().get(i)).burnTime));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setFloats()
/*      */   {
/*  432 */     if (this.loader.getProperties()[9] > 0.0F)
/*      */     {
/*  434 */       for (int i = 0; i < this.loader.getProperties()[9]; i++)
/*      */       {
/*  436 */         this.floats.add(new FloatsHandler(((FloatsProperties)this.loader.getFloats().get(i)).position, 
/*  437 */           ((FloatsProperties)this.loader.getFloats().get(i)).spanVec, 
/*  438 */           ((FloatsProperties)this.loader.getFloats().get(i)).dragCoef, 
/*  439 */           ((FloatsProperties)this.loader.getFloats().get(i)).radius, 
/*  440 */           ((FloatsProperties)this.loader.getFloats().get(i)).sections));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void update(GlobalEntity entity, float deltaTime)
/*      */   {
/*  448 */     this.entityBody.getOrientation(this.localQuat);
/*  449 */     this.entityBody.getLinearVelocity(this.linearVel);
/*  450 */     this.entityBody.getAngularVelocity(this.rotationalVel);
/*  451 */     this.position = this.entityBody.getMotionState().getWorldTransform(new Transform()).origin;
/*      */     
/*  453 */     this.Forward = this.helper.rotateVector(this.localQuat, new Vector3f(0.0F, 0.0F, 1.0F));
/*  454 */     this.Up = this.helper.rotateVector(this.localQuat, new Vector3f(0.0F, 1.0F, 0.0F));
/*  455 */     this.Left = this.helper.rotateVector(this.localQuat, new Vector3f(1.0F, 0.0F, 0.0F));
/*      */     
/*  457 */     updateControls(entity, deltaTime);
/*      */     
/*  459 */     boolean waterEntity = ((entity instanceof EntityBoat)) || ((entity instanceof EntitySubmarine));
/*      */     
/*  461 */     int wCount = 0;
/*      */     
/*  463 */     for (AttachmentHandler mh : this.attachments)
/*      */     {
/*      */ 
/*  466 */       mh.setMotionState(this.localQuat, this.linearVel, this.position, deltaTime, entity.getEntityId());
/*      */       
/*  468 */       if ((this.releaseWeapons) && (!mh.isEmpty()))
/*      */       {
/*  470 */         mh.release(this.lockedEntity, this.targetPos);
/*  471 */         this.releaseWeapons = false;
/*  472 */         this.releaseWeaponTimer = 0.5F;
/*      */       }
/*      */       
/*  475 */       if (!mh.isEmpty())
/*      */       {
/*  477 */         wCount++;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  482 */     if (this.releaseWeapons == true)
/*      */     {
/*  484 */       this.releaseWeapons = false;
/*      */     }
/*      */     
/*  487 */     this.weaponCount = wCount;
/*      */     
/*  489 */     if (this.releaseWeaponTimer > 0.0F)
/*      */     {
/*  491 */       this.releaseWeaponTimer -= deltaTime;
/*      */     }
/*      */     
/*  494 */     for (int i = 0; i < this.wheels.size(); i++)
/*      */     {
/*  496 */       if ((((WheelHandler)this.wheels.get(i)).isSteerable()) && (this.vehicle != null))
/*      */       {
/*  498 */         this.vehicle.setSteeringValue(this.controlChannels[((WheelHandler)this.wheels.get(i)).getChannel()] * ((WheelHandler)this.wheels.get(i)).getMaxSteering(), i);
/*      */       }
/*      */       
/*  501 */       if (((WheelInfo)this.vehicle.wheelInfo.get(i)).wheelsSuspensionForce <= 0.0F)
/*      */       {
/*  503 */         this.jump = false;
/*      */       }
/*      */     }
/*      */     
/*  507 */     if (this.jump)
/*      */     {
/*  509 */       Vector3f jumpVect = new Vector3f(entity.Up);
/*  510 */       jumpVect.scale(1300.0F);
/*  511 */       this.forces.add(new Force(jumpVect, new Vector3f()));
/*      */     }
/*      */     
/*  514 */     if ((entity instanceof EntityMissile))
/*      */     {
/*      */ 
/*  517 */       if (!getRocketMotorActive())
/*      */       {
/*  519 */         this.frontPath = this.Forward;
/*      */       }
/*      */       
/*  522 */       this.virtualReference.updateFlyPath(this.Left, this.Up, this.Forward, 
/*  523 */         getTragetPath(entity.getEntityId(), this.linearVel), this.frontPath);
/*      */     }
/*  525 */     else if (this.sensors.size() > 0)
/*      */     {
/*      */ 
/*  528 */       if ((entity instanceof EntityDrone))
/*      */       {
/*  530 */         this.virtualReference.resetFlybars(this.Forward);
/*  531 */         this.virtualReference.updateSafeBars(this.Left, this.Up, this.Forward, this.pitchAngle, this.yawAngle, this.rollAngle, deltaTime);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  536 */         this.virtualReference.updateBars(this.Left, this.Up, this.Forward, this.pitchAngle, this.yawAngle, this.rollAngle, deltaTime);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  541 */     int constraints = this.entityBody.getNumConstraintRefs();
/*      */     
/*      */ 
/*  544 */     boolean inLiquid = false;
/*  545 */     int finalOffSet = 0;
/*      */     
/*  547 */     for (int offSet = -1; offSet <= 2; offSet++)
/*      */     {
/*  549 */       BlockPos bp = new BlockPos(this.position.x, this.position.y + offSet, this.position.z);
/*      */       
/*  551 */       if (entity.getEntityWorld().getBlockState(bp).getMaterial().isLiquid())
/*      */       {
/*  553 */         finalOffSet = offSet;
/*  554 */         inLiquid = true;
/*      */       }
/*      */     }
/*      */     
/*  558 */     float floatReference = 0.0F;
/*      */     
/*  560 */     if (inLiquid)
/*      */     {
/*  562 */       BlockPos bp = new BlockPos(this.position.x, this.position.y + finalOffSet, this.position.z);
/*  563 */       floatReference = (float)entity.getEntityWorld().getBlockState(bp).getSelectedBoundingBox(entity.getEntityWorld(), bp).maxY - 0.125F;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  569 */     if ((constraints == 0) && (((entity instanceof EntityTrainerPlane)) || ((entity instanceof EntityHeli)) || ((entity instanceof EntityDrone))) && (!entity.damaged))
/*      */     {
/*  571 */       entity.damaged = true;
/*      */     }
/*      */     
/*  574 */     if ((!(entity instanceof EntityBoat)) && (!(entity instanceof EntitySubmarine)) && (!(entity instanceof EntityMissile)) && (entity.isInWater()))
/*      */     {
/*  576 */       entity.damaged = true;
/*      */     }
/*      */     
/*  579 */     entity.prevVeloc.sub(this.linearVel);
/*      */     
/*  581 */     if ((entity.prevVeloc.length() > 20.0F) && (!(entity instanceof EntityMissile)))
/*      */     {
/*  583 */       entity.damaged = true;
/*      */     }
/*      */     
/*  586 */     for (MotorHandler motor : this.motors)
/*      */     {
/*      */ 
/*  589 */       if (entity.damaged)
/*      */       {
/*  591 */         motor.setDmged(true);
/*      */       }
/*      */       
/*  594 */       int wheelsConnected = 0;
/*      */       
/*  596 */       for (WheelHandler wheel : this.wheels)
/*      */       {
/*  598 */         if (wheel.getID() == motor.getID())
/*      */         {
/*  600 */           wheelsConnected++;
/*      */         }
/*      */       }
/*      */       int i;
/*  604 */       if (wheelsConnected > 0)
/*      */       {
/*  606 */         float wheelRotation = 0.0F;
/*  607 */         int wheelCounter = 0;
/*      */         
/*  609 */         for (i = 0; i < this.wheels.size(); i++)
/*      */         {
/*  611 */           if (motor.getID() == ((WheelHandler)this.wheels.get(i)).getID())
/*      */           {
/*  613 */             if ((entity.forwardVelocity > 0.0F) && (motor.getTorque() < 0.0F))
/*      */             {
/*  615 */               this.vehicle.applyEngineForce(motor.getTorque() * 100.0F, i);
/*      */             }
/*      */             else
/*      */             {
/*  619 */               this.vehicle.applyEngineForce(motor.getTorque(), i);
/*      */             }
/*      */             
/*  622 */             wheelRotation += this.vehicle.getWheelInfo(i).deltaRotation * 60.0F;
/*  623 */             wheelCounter++;
/*      */           }
/*      */         }
/*      */         
/*  627 */         if (wheelCounter != 0)
/*      */         {
/*  629 */           wheelRotation /= wheelCounter;
/*      */         }
/*      */         
/*      */ 
/*  633 */         motor.setOmega(wheelRotation);
/*  634 */         motor.update(0.0F, 0.0F, this.controlChannels[motor.getChannel()], wheelsConnected, true, 0.0F, this.altitude);
/*      */       }
/*      */       else
/*      */       {
/*  638 */         totalResistanceTorque = 0.0F;
/*  639 */         float totalInertia = 0.0F;
/*      */         
/*  641 */         for (i = this.rotaryWings.iterator(); i.hasNext();) { rotWing = (RotaryWingHandler)i.next();
/*      */           
/*  643 */           if (rotWing.getID() == motor.getID())
/*      */           {
/*  645 */             float response = 0.0F;
/*      */             
/*  647 */             if (rotWing.getSensorID() != 0)
/*      */             {
/*  649 */               for (AutoControlHandler sensor : this.sensors)
/*      */               {
/*  651 */                 if (rotWing.getSensorID() == sensor.getID())
/*      */                 {
/*  653 */                   sensor.update(this.localQuat, this.linearVel, this.virtualReference
/*  654 */                     .getVirtualFlyBar(), this.virtualReference
/*  655 */                     .getVirtualTailBar(), this.virtualReference
/*  656 */                     .getVirtualFlightPath());
/*  657 */                   response = sensor.getResponse();
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*  662 */             rotWing.setAngularVelocity(motor.getRotationalVel());
/*  663 */             rotWing.setRotationAngle(deltaTime);
/*      */             
/*  665 */             rotWing.update(this.localQuat, this.linearVel, this.rotationalVel, this.position.y, 1.225F, floatReference, this.controlChannels[rotWing.getChannel()] + response, waterEntity);
/*      */             
/*      */ 
/*  668 */             for (int i = 0; i < rotWing.getSections(); i++)
/*      */             {
/*  670 */               Vector3f forcePos = new Vector3f(rotWing.getPosition(i));
/*  671 */               Vector3f forceLift = new Vector3f(rotWing.getLift(i));
/*  672 */               Vector3f forceDrag = new Vector3f(rotWing.getDrag(i));
/*      */               
/*  674 */               this.forces.add(new Force(forceLift, forcePos));
/*  675 */               this.forces.add(new Force(forceDrag, forcePos));
/*  676 */               this.torques.add(rotWing.getMoment(i));
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  681 */             totalResistanceTorque += rotWing.getTroque();
/*  682 */             totalInertia += rotWing.getInertia();
/*      */           }
/*      */         }
/*      */         RotaryWingHandler rotWing;
/*  686 */         if ((motor.getRotationalVel() <= 200.0F) && (constraints > 0) && (!entity.damaged))
/*      */         {
/*  688 */           TimeToBreak dTime = (TimeToBreak)this.entityBody.getConstraintRef(0).getRigidBodyB().getUserPointer();
/*  689 */           dTime.setDamageTime(0.125F);
/*  690 */           this.entityBody.getConstraintRef(0).getRigidBodyB().setCollisionFlags(4);
/*      */         }
/*  692 */         else if (constraints > 0)
/*      */         {
/*  694 */           this.entityBody.getConstraintRef(0).getRigidBodyB().setCollisionFlags(8);
/*      */         }
/*      */         
/*      */ 
/*  698 */         float response = 0.0F;
/*      */         
/*  700 */         if (motor.getSensorID() != 0)
/*      */         {
/*  702 */           for (AutoControlHandler sensor : this.sensors)
/*      */           {
/*  704 */             if (motor.getSensorID() == sensor.getID())
/*      */             {
/*  706 */               sensor.update(this.localQuat, this.linearVel, this.virtualReference
/*  707 */                 .getVirtualFlyBar(), this.virtualReference
/*  708 */                 .getVirtualTailBar(), this.virtualReference
/*  709 */                 .getVirtualFlightPath());
/*  710 */               response = sensor.getMotorResponse(this.controlChannels[motor.getChannel()]);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  715 */         motor.update(totalResistanceTorque, totalInertia, this.controlChannels[motor.getChannel()] + response, 1, false, deltaTime, this.altitude);
/*      */       }
/*      */     }
/*      */     float totalResistanceTorque;
/*  719 */     if ((!entity.isInWater()) || (!(entity instanceof EntityMissile)))
/*      */     {
/*  721 */       for (WingHandler wing : this.wings)
/*      */       {
/*  723 */         float response = 0.0F;
/*      */         
/*  725 */         if (wing.getSensorID() != 0)
/*      */         {
/*  727 */           for (AutoControlHandler sensor : this.sensors)
/*      */           {
/*  729 */             if (wing.getSensorID() == sensor.getID())
/*      */             {
/*  731 */               sensor.update(this.localQuat, this.linearVel, this.virtualReference
/*  732 */                 .getVirtualFlyBar(), this.virtualReference
/*  733 */                 .getVirtualTailBar(), this.virtualReference
/*  734 */                 .getVirtualFlightPath());
/*  735 */               response = sensor.getResponse();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  740 */         wing.update(this.localQuat, this.linearVel, this.rotationalVel, this.position.y, 1.225F, floatReference, this.controlChannels[wing.getChannel()] + response, waterEntity);
/*      */         
/*  742 */         for (int i = 0; i < wing.getSections(); i++)
/*      */         {
/*  744 */           Vector3f forcePos = new Vector3f(wing.getPosition(i));
/*  745 */           Vector3f forceLift = new Vector3f(wing.getLift(i));
/*  746 */           Vector3f forceDrag = new Vector3f(wing.getDrag(i));
/*      */           
/*  748 */           this.forces.add(new Force(forceLift, forcePos));
/*  749 */           this.forces.add(new Force(forceDrag, forcePos));
/*  750 */           this.torques.add(wing.getMoment(i));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  756 */     for (RocketMotorHandler rocketMotor : this.rocketMotors)
/*      */     {
/*  758 */       if (this.targetPos != null)
/*      */       {
/*  760 */         Vector3f rocketThrust = rocketMotor.getThurstVect(this.Forward, this.linearVel.length(), deltaTime, this.position.y - this.targetPos.y, this.linearVel.y);
/*  761 */         this.forces.add(new Force(rocketThrust, new Vector3f()));
/*      */       }
/*      */       else
/*      */       {
/*  765 */         Vector3f rocketThrust = rocketMotor.getThurstVect(this.Forward, this.linearVel.length(), deltaTime, 0.0F, this.linearVel.y);
/*  766 */         this.forces.add(new Force(rocketThrust, new Vector3f()));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  772 */     if (inLiquid)
/*      */     {
/*  774 */       for (FloatsHandler flts : this.floats)
/*      */       {
/*  776 */         flts.update(this.position, this.localQuat, this.linearVel, this.rotationalVel, floatReference, this.floatDensity);
/*      */         
/*  778 */         for (int i = 0; i < flts.getSections(); i++)
/*      */         {
/*  780 */           Vector3f forcePos = new Vector3f(flts.getPosition(i));
/*  781 */           Vector3f forceBouy = new Vector3f(flts.getBouyancy(i));
/*  782 */           Vector3f forceDrag = new Vector3f(flts.getDrag(i));
/*      */           
/*  784 */           this.forces.add(new Force(forceBouy, forcePos));
/*  785 */           this.forces.add(new Force(forceDrag, forcePos));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  790 */     if (((entity instanceof EntitySubmarine)) && (entity.isInWater()))
/*      */     {
/*  792 */       float pitchCorrection = (float)Math.sin(this.Forward.y) * 50.0F;
/*  793 */       float rollCorrection = -(float)Math.sin(this.Left.y) * 10.0F;
/*  794 */       Vector3f torqVec = this.helper.rotateVector(this.localQuat, new Vector3f(pitchCorrection, 0.0F, rollCorrection));
/*  795 */       this.torques.add(torqVec);
/*      */     }
/*      */     
/*  798 */     if ((entity instanceof EntityDrone))
/*      */     {
/*  800 */       Vector3f upVec = new Vector3f(this.Up);
/*  801 */       upVec.scale(-KeyHandler.yawMovement * entity.power / 100.0F * 0.8F);
/*  802 */       Vector3f torqVec = this.helper.rotateVector(this.localQuat, upVec);
/*  803 */       this.torques.add(torqVec);
/*      */     }
/*      */     
/*  806 */     if (!entity.isInWater())
/*      */     {
/*  808 */       Vector3f dragForce = entity.helper.getAirDrag(this.linearVel, this.dragFactor);
/*  809 */       this.forces.add(new Force(dragForce, new Vector3f()));
/*      */     }
/*  811 */     else if ((!(entity instanceof EntitySubmarine)) && (!(entity instanceof EntityBoat)))
/*      */     {
/*  813 */       dragForce = entity.helper.getWaterDrag(this.linearVel, this.dragFactor);
/*  814 */       this.forces.add(new Force((Vector3f)dragForce, new Vector3f()));
/*      */     }
/*      */     
/*  817 */     entity.prevVeloc.set(this.linearVel);
/*      */     
/*  819 */     for (Object dragForce = this.forces.iterator(); ((Iterator)dragForce).hasNext();) { Force fs = (Force)((Iterator)dragForce).next();
/*      */       
/*  821 */       this.entityBody.applyForce(fs.getForce(), fs.getPosition());
/*      */     }
/*      */     
/*  824 */     for (dragForce = this.torques.iterator(); ((Iterator)dragForce).hasNext();) { Vector3f ts = (Vector3f)((Iterator)dragForce).next();
/*      */       
/*  826 */       this.entityBody.applyTorque(ts);
/*      */     }
/*      */     
/*  829 */     this.forces.clear();
/*  830 */     this.torques.clear();
/*      */   }
/*      */   
/*      */   public void getVisibleEntities(GlobalEntity en)
/*      */   {
/*  835 */     List entityList = en.world.loadedEntityList;
/*  836 */     this.visibleEntities.clear();
/*  837 */     this.inRadarEntities.clear();
/*  838 */     en.weaponLock = false;
/*  839 */     en.prevLockProgress = en.lockProgress;
/*      */     
/*  841 */     float angle = 0.0F;
/*  842 */     float nearAngle = 0.0F;
/*      */     
/*      */ 
/*  845 */     Vector3f eVec = new Vector3f();
/*      */     
/*  847 */     if (this.weaponsMode) {
/*      */       Vec3d rcentityVec;
/*  849 */       if (this.lockedEntity != null)
/*      */       {
/*  851 */         rcentityVec = new Vec3d(en.posX, en.posY, en.posZ);
/*  852 */         Vec3d entityVec = new Vec3d(this.lockedEntity.posX, this.lockedEntity.posY + this.lockedEntity.getEyeHeight() / 2.0F, this.lockedEntity.posZ);
/*      */         
/*  854 */         eVec.set((float)(this.lockedEntity.posX - en.posX), (float)(this.lockedEntity.posY + this.lockedEntity.getEyeHeight() / 2.0F - en.posY), (float)(this.lockedEntity.posZ - en.posZ));
/*      */         
/*  856 */         angle = eVec.angle(en.Forward);
/*  857 */         nearAngle = angle;
/*      */         
/*  859 */         if ((en.world.rayTraceBlocks(rcentityVec, entityVec, false) != null) || (angle > 1.047198F))
/*      */         {
/*  861 */           this.lockedEntity = null;
/*      */         }
/*      */       }
/*      */       
/*  865 */       for (Object obj : entityList)
/*      */       {
/*  867 */         Entity entity = (Entity)obj;
/*      */         
/*  869 */         if ((entity != en) && (entity != en.thePlayer) && (!(entity instanceof CameraHandler)) && (!(entity instanceof EntityItem)) && (!(entity instanceof EntityMissile)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  874 */           Vec3d rcentityVec = new Vec3d(en.posX, en.posY, en.posZ);
/*  875 */           Vec3d entityVec = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight() / 2.0F, entity.posZ);
/*      */           
/*  877 */           eVec.set((float)(entity.posX - en.posX), (float)(entity.posY + entity.getEyeHeight() / 2.0F - en.posY), (float)(entity.posZ - en.posZ));
/*      */           
/*  879 */           angle = eVec.angle(en.Forward);
/*      */           
/*  881 */           if ((angle <= 1.047198F) && 
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  886 */             (en.world.rayTraceBlocks(rcentityVec, entityVec, false) == null))
/*      */           {
/*  888 */             this.visibleEntities.add(entity);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  895 */             if (angle <= 0.43633232F)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  900 */               if (this.lockedEntity == null)
/*      */               {
/*  902 */                 this.lockedEntity = entity;
/*  903 */                 this.unlockCooldown = 2.0F;
/*      */               }
/*  905 */               else if ((angle < nearAngle) && (this.unlockCooldown <= 0.0F))
/*      */               {
/*  907 */                 this.lockedEntity = entity;
/*  908 */                 this.unlockCooldown = 2.0F;
/*      */               } }
/*      */           }
/*      */         } }
/*  912 */       if (this.lockedEntity != null)
/*      */       {
/*  914 */         en.weaponLock = true;
/*  915 */         this.unlockCooldown -= 0.05F;
/*      */         
/*  917 */         if (this.lockedEntity.getEntityId() != this.lockedEntityID)
/*      */         {
/*  919 */           en.lockProgress = 0.0F;
/*  920 */           en.prevLockProgress = 0.0F;
/*  921 */           en.weaponLock = false;
/*      */         }
/*  923 */         else if ((this.lockedEntity.getEntityId() == this.lockedEntityID) && (en.lockProgress < 1.0F))
/*      */         {
/*  925 */           en.lockProgress += 0.05F;
/*      */         }
/*      */         
/*  928 */         en.lockProgress = Math.min(1.0F, en.lockProgress);
/*      */         
/*  930 */         this.lockedEntityID = this.lockedEntity.getEntityId();
/*      */       }
/*      */       else
/*      */       {
/*  934 */         en.lockProgress = 0.0F;
/*  935 */         en.prevLockProgress = 0.0F;
/*  936 */         en.weaponLock = false;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  941 */       this.lockedEntity = null;
/*  942 */       en.lockProgress = 0.0F;
/*  943 */       en.prevLockProgress = 0.0F;
/*  944 */       en.weaponLock = false;
/*      */     }
/*      */   }
/*      */   
/*      */   public Vector3f getTragetPath(int entityID, Vector3f linearMotion)
/*      */   {
/*  950 */     if ((this.lockedEntity != null) && (!this.lockedEntity.isDead))
/*      */     {
/*  952 */       Vector3f distanceToGo = new Vector3f((float)this.lockedEntity.posX, (float)(this.lockedEntity.posY + this.lockedEntity.getEyeHeight()), (float)this.lockedEntity.posZ);
/*  953 */       Vector3f motionVect = new Vector3f(linearMotion);
/*  954 */       Vector3f entityMotion = new Vector3f((float)this.lockedEntity.motionX, (float)this.lockedEntity.motionY, (float)this.lockedEntity.motionZ);
/*  955 */       Vector3f normDistToGo = new Vector3f();
/*  956 */       Vector3f downDriftOffSet = new Vector3f(0.0F, 3.0F, 0.0F);
/*      */       
/*  958 */       distanceToGo.add(downDriftOffSet);
/*  959 */       distanceToGo.sub(this.position);
/*  960 */       entityMotion.scale(20.0F);
/*      */       
/*  962 */       float distanceCheck = distanceToGo.length();
/*      */       
/*  964 */       if (distanceCheck < 30.0F)
/*      */       {
/*  966 */         downDriftOffSet.set(0.0F, 3.0F - distanceCheck / 10.0F, 0.0F);
/*  967 */         distanceToGo.sub(downDriftOffSet);
/*      */       }
/*      */       
/*  970 */       Vector3f relVel = new Vector3f(motionVect);
/*  971 */       relVel.sub(entityMotion);
/*      */       
/*  973 */       float distanceToGoMag = distanceToGo.length();
/*  974 */       float motionVectMag = motionVect.length();
/*      */       
/*  976 */       normDistToGo.set(distanceToGo);
/*      */       
/*  978 */       if (distanceToGoMag > 0.0F)
/*      */       {
/*  980 */         normDistToGo.normalize();
/*      */       }
/*      */       
/*  983 */       if (motionVectMag > 0.0F)
/*      */       {
/*  985 */         motionVect.normalize();
/*      */       }
/*      */       
/*  988 */       float closingVel = relVel.dot(normDistToGo);
/*  989 */       float timeToTarget = 0.0F;
/*      */       
/*  991 */       if (closingVel != 0.0F)
/*      */       {
/*  993 */         timeToTarget = distanceToGoMag / closingVel;
/*      */       }
/*      */       
/*  996 */       if (timeToTarget > 0.0F)
/*      */       {
/*  998 */         entityMotion.scale(timeToTarget);
/*  999 */         distanceToGo.add(entityMotion);
/*      */       }
/*      */       
/* 1002 */       if (distanceToGo.length() > 0.0F)
/*      */       {
/* 1004 */         distanceToGo.normalize();
/*      */       }
/*      */       
/* 1007 */       float projection = distanceToGo.dot(motionVect) * 0.1F;
/*      */       
/* 1009 */       motionVect.scale(projection);
/* 1010 */       distanceToGo.sub(motionVect);
/* 1011 */       distanceToGo.scale(-1.0F);
/*      */       
/* 1013 */       if ((distanceToGoMag <= 1.5F) && (this.prevDist <= distanceToGoMag))
/*      */       {
/* 1015 */         MessageHandler.handler.sendToServer(new MessageEntityMissle(entityID, this.position.x, this.position.y, this.position.z));
/*      */       }
/*      */       
/* 1018 */       this.prevDist = distanceToGoMag;
/*      */       
/* 1020 */       return distanceToGo;
/*      */     }
/* 1022 */     if ((this.targetPos != null) && ((this.position.y - this.targetPos.y >= 15.0F) || (this.linearVel.y < 0.0F)))
/*      */     {
/* 1024 */       Vector3f distanceToGo = new Vector3f(this.targetPos.x, this.targetPos.y, this.targetPos.z);
/* 1025 */       Vector3f normDistToGo = new Vector3f();
/* 1026 */       Vector3f downDriftOffSet = new Vector3f(0.0F, 6.0F, 0.0F);
/*      */       
/* 1028 */       distanceToGo.add(downDriftOffSet);
/* 1029 */       distanceToGo.sub(this.position);
/*      */       
/* 1031 */       float distanceCheck = distanceToGo.length();
/*      */       
/* 1033 */       if (distanceCheck < 30.0F)
/*      */       {
/* 1035 */         downDriftOffSet.set(0.0F, 6.0F - distanceCheck / 5.0F, 0.0F);
/* 1036 */         distanceToGo.sub(downDriftOffSet);
/*      */       }
/*      */       
/* 1039 */       float distanceToGoMag = distanceToGo.length();
/*      */       
/* 1041 */       normDistToGo.set(distanceToGo);
/*      */       
/* 1043 */       if (distanceToGoMag > 0.0F)
/*      */       {
/* 1045 */         normDistToGo.normalize();
/*      */       }
/*      */       
/* 1048 */       if (distanceToGo.length() > 0.0F)
/*      */       {
/* 1050 */         distanceToGo.normalize();
/*      */       }
/* 1052 */       distanceToGo.scale(-1.0F);
/*      */       
/* 1054 */       if ((distanceToGoMag <= 1.5F) && (this.prevDist <= distanceToGoMag))
/*      */       {
/* 1056 */         MessageHandler.handler.sendToServer(new MessageEntityMissle(entityID, this.position.x, this.position.y, this.position.z));
/*      */       }
/*      */       
/* 1059 */       this.prevDist = distanceToGoMag;
/*      */       
/* 1061 */       return distanceToGo;
/*      */     }
/*      */     
/* 1064 */     return null;
/*      */   }
/*      */   
/*      */   public void updateControls(GlobalEntity entity, float deltaTime)
/*      */   {
/* 1069 */     if ((entity.activated) && (entity.holdingremotecontrol(entity.thePlayer)))
/*      */     {
/*      */ 
/* 1072 */       this.yawAngle = (-KeyHandler.yawMovement);
/*      */       
/*      */ 
/* 1075 */       this.pitchAngle = (-KeyHandler.pitchMovement);
/*      */       
/*      */ 
/* 1078 */       this.rollAngle = KeyHandler.rollMovement;
/*      */     }
/*      */     
/* 1081 */     if ((this.canChangeMode) && (KeyHandler.weaponsMode))
/*      */     {
/* 1083 */       this.weaponsMode = (!this.weaponsMode);
/* 1084 */       this.canChangeMode = false;
/*      */     }
/* 1086 */     else if ((!this.canChangeMode) && (!KeyHandler.weaponsMode))
/*      */     {
/* 1088 */       this.canChangeMode = true;
/*      */     }
/*      */     
/* 1091 */     if ((KeyHandler.shoot) && (this.canFireMissile) && (this.weaponsMode))
/*      */     {
/* 1093 */       this.releaseWeapons = true;
/* 1094 */       this.canFireMissile = false;
/*      */     }
/* 1096 */     else if (this.releaseWeaponTimer <= 0.0F)
/*      */     {
/* 1098 */       this.canFireMissile = true;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setControlChannel(int place, float value)
/*      */   {
/* 1104 */     this.controlChannels[place] = value;
/*      */   }
/*      */   
/*      */   public void jump()
/*      */   {
/* 1109 */     this.jump = true;
/*      */   }
/*      */   
/*      */   public Quat4f getLocalQuad()
/*      */   {
/* 1114 */     return this.localQuat;
/*      */   }
/*      */   
/*      */   public Vector3f getLinearVel()
/*      */   {
/* 1119 */     return this.linearVel;
/*      */   }
/*      */   
/*      */   public Vector3f getRotationalVel()
/*      */   {
/* 1124 */     return this.rotationalVel;
/*      */   }
/*      */   
/*      */   public Vector3f getPosition()
/*      */   {
/* 1129 */     return this.position;
/*      */   }
/*      */   
/*      */   public void setEntityLinearVelocity(Vector3f vel)
/*      */   {
/* 1134 */     this.entityBody.setLinearVelocity(vel);
/*      */   }
/*      */   
/*      */   public void setEntityMotionState(Quat4f quad, Vector3f pos, float par)
/*      */   {
/* 1139 */     this.entityBody.setMotionState(new DefaultMotionState(new Transform(new Matrix4f(quad, pos, par))));
/*      */   }
/*      */   
/*      */ 
/*      */   public void getEntityAABB(Vector3f minAABB, Vector3f maxAABB)
/*      */   {
/* 1145 */     this.entityBody.getAabb(minAABB, maxAABB);
/*      */   }
/*      */   
/*      */   public void removePhysicsEntity()
/*      */   {
/* 1150 */     if (this.vehicle != null)
/*      */     {
/* 1152 */       RCM_Main.physicsWorld.removeVehicle(this.vehicle);
/*      */     }
/*      */     
/* 1155 */     if (this.entityBody != null)
/*      */     {
/* 1157 */       int constraints = this.entityBody.getNumConstraintRefs();
/*      */       
/* 1159 */       if (constraints > 0)
/*      */       {
/* 1161 */         RigidBody dynBody = this.entityBody.getConstraintRef(0).getRigidBodyB();
/* 1162 */         RCM_Main.physicsWorld.removeConstraint(this.entityBody.getConstraintRef(0));
/* 1163 */         RCM_Main.physicsWorld.removeRigidBody(dynBody);
/*      */       }
/*      */       
/* 1166 */       RCM_Main.physicsWorld.removeRigidBody(this.entityBody);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setRocketMotorActive()
/*      */   {
/* 1172 */     for (RocketMotorHandler rmh : this.rocketMotors)
/*      */     {
/* 1174 */       rmh.setActive();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean getRocketMotorActive()
/*      */   {
/* 1180 */     Iterator localIterator = this.rocketMotors.iterator(); if (localIterator.hasNext()) { RocketMotorHandler rmh = (RocketMotorHandler)localIterator.next();
/*      */       
/* 1182 */       return rmh.isActive();
/*      */     }
/*      */     
/* 1185 */     return false;
/*      */   }
/*      */   
/*      */   public void attachWeapon(int place, GlobalEntity entity)
/*      */   {
/* 1190 */     ((AttachmentHandler)this.attachments.get(place)).attach(entity);
/*      */   }
/*      */   
/*      */   public int getWeaponCount()
/*      */   {
/* 1195 */     return this.weaponCount;
/*      */   }
/*      */   
/*      */   public boolean canExplode()
/*      */   {
/* 1200 */     return this.motorActive;
/*      */   }
/*      */   
/*      */   public void setLockedTarger(@Nullable Entity lockedTarget)
/*      */   {
/* 1205 */     this.lockedEntity = lockedTarget;
/*      */   }
/*      */   
/*      */   public void setFloatDensity(float par)
/*      */   {
/* 1210 */     this.floatDensity = (997.0F * (0.1155F + 0.1F * (par + 1.0F) / 2.0F));
/*      */   }
/*      */   
/*      */   public void setTargetPosition(@Nullable Vector3f targetPosition)
/*      */   {
/* 1215 */     this.targetPos = targetPosition;
/*      */   }
/*      */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/PhysicsHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */