/*     */ package RCM.Physics;
/*     */ 
/*     */ import RCM.Entities.GlobalEntity;
/*     */ import com.bulletphysics.collision.broadphase.BroadphaseInterface;
/*     */ import com.bulletphysics.collision.broadphase.DbvtBroadphase;
/*     */ import com.bulletphysics.collision.broadphase.Dispatcher;
/*     */ import com.bulletphysics.collision.dispatch.CollisionConfiguration;
/*     */ import com.bulletphysics.collision.dispatch.CollisionDispatcher;
/*     */ import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
/*     */ import com.bulletphysics.collision.narrowphase.PersistentManifold;
/*     */ import com.bulletphysics.collision.shapes.BoxShape;
/*     */ import com.bulletphysics.collision.shapes.CollisionShape;
/*     */ import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
/*     */ import com.bulletphysics.dynamics.DynamicsWorld;
/*     */ import com.bulletphysics.dynamics.RigidBody;
/*     */ import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
/*     */ import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
/*     */ import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
/*     */ import com.bulletphysics.dynamics.constraintsolver.TypedConstraint;
/*     */ import com.bulletphysics.dynamics.vehicle.RaycastVehicle;
/*     */ import com.bulletphysics.linearmath.DefaultMotionState;
/*     */ import com.bulletphysics.linearmath.MotionState;
/*     */ import com.bulletphysics.linearmath.Transform;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PhysicsWorld
/*     */ {
/*     */   private DynamicsWorld dynamicsWorld;
/*  43 */   private Set<RigidBody> collisionBlocks = new HashSet();
/*  44 */   private Set<RigidBody> collisionEntities = new HashSet();
/*  45 */   private Set<RigidBody> rigidbodies = new HashSet();
/*  46 */   private Set<RigidBody> bodyRemoveList = new HashSet();
/*  47 */   private Set<RaycastVehicle> vehicles = new HashSet();
/*     */   
/*     */   private Vector3f vSampleSize;
/*     */   private int sampleSize;
/*     */   
/*     */   public PhysicsWorld()
/*     */   {
/*  54 */     this.vSampleSize = new Vector3f(1.0F, 1.0F, 1.0F);
/*  55 */     this.sampleSize = 1;
/*     */     
/*  57 */     float gravity = -9.81F;
/*     */     
/*  59 */     BroadphaseInterface broadphase = new DbvtBroadphase();
/*  60 */     CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
/*  61 */     CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
/*  62 */     ConstraintSolver solver = new SequentialImpulseConstraintSolver();
/*  63 */     this.dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
/*  64 */     this.dynamicsWorld.setGravity(new Vector3f(0.0F, gravity, 0.0F));
/*     */   }
/*     */   
/*     */   public void stepSimulation(float deltaTime, int maxSubSteps)
/*     */   {
/*  69 */     if (this.dynamicsWorld != null)
/*     */     {
/*  71 */       this.dynamicsWorld.stepSimulation(deltaTime, maxSubSteps);
/*     */       
/*  73 */       int numManifolds = this.dynamicsWorld.getDispatcher().getNumManifolds();
/*     */       
/*  75 */       for (int i = 0; i < numManifolds; i++)
/*     */       {
/*  77 */         PersistentManifold contactManifold = this.dynamicsWorld.getDispatcher().getManifoldByIndexInternal(i);
/*     */         
/*  79 */         RigidBody collidingBody = (RigidBody)contactManifold.getBody0();
/*     */         
/*  81 */         if ((collidingBody.getUserPointer() != null) && (contactManifold.getNumContacts() > 0))
/*     */         {
/*  83 */           TimeToBreak dTime = (TimeToBreak)collidingBody.getUserPointer();
/*     */           
/*  85 */           if (dTime.update(deltaTime) < 0.0F)
/*     */           {
/*  87 */             this.bodyRemoveList.add(collidingBody);
/*  88 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  93 */       for (RigidBody rb : this.bodyRemoveList)
/*     */       {
/*  95 */         removeConstraint(rb.getConstraintRef(0));
/*  96 */         removeRigidBody(rb);
/*     */       }
/*     */       
/*  99 */       this.bodyRemoveList.clear();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void addRigidBody(RigidBody entityBody)
/*     */   {
/* 106 */     this.dynamicsWorld.addRigidBody(entityBody);
/* 107 */     this.rigidbodies.add(entityBody);
/*     */   }
/*     */   
/*     */   public void removeRigidBody(RigidBody entityBody)
/*     */   {
/* 112 */     this.dynamicsWorld.removeRigidBody(entityBody);
/* 113 */     this.rigidbodies.remove(entityBody);
/*     */   }
/*     */   
/*     */   public void removeConstraint(TypedConstraint ct)
/*     */   {
/* 118 */     this.dynamicsWorld.removeConstraint(ct);
/*     */   }
/*     */   
/*     */   public void addVehicle(RaycastVehicle vehicle)
/*     */   {
/* 123 */     this.dynamicsWorld.addVehicle(vehicle);
/* 124 */     this.vehicles.add(vehicle);
/*     */   }
/*     */   
/*     */   public void removeVehicle(RaycastVehicle vehicle)
/*     */   {
/* 129 */     this.dynamicsWorld.removeVehicle(vehicle);
/* 130 */     this.vehicles.remove(vehicle);
/*     */   }
/*     */   
/*     */   public DynamicsWorld getDynamicsWorld()
/*     */   {
/* 135 */     return this.dynamicsWorld;
/*     */   }
/*     */   
/*     */   public void removeAll()
/*     */   {
/* 140 */     for (RaycastVehicle rv : this.vehicles)
/*     */     {
/* 142 */       this.dynamicsWorld.removeVehicle(rv);
/*     */     }
/*     */     
/* 145 */     for (RigidBody rb : this.rigidbodies)
/*     */     {
/* 147 */       this.dynamicsWorld.removeRigidBody(rb);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void loadCollisionShapes(GlobalEntity entity, World world, Vector3f position)
/*     */   {
/* 154 */     boolean flag = false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */     Vector3f minAABB = new Vector3f(position);
/* 161 */     Vector3f maxAABB = new Vector3f(position);
/*     */     
/* 163 */     minAABB.sub(this.vSampleSize);
/* 164 */     maxAABB.add(this.vSampleSize);
/*     */     
/* 166 */     AxisAlignedBB aabb = new AxisAlignedBB(minAABB.x, minAABB.y, minAABB.z, maxAABB.x, maxAABB.y, maxAABB.z);
/* 167 */     List<AxisAlignedBB> blocks = world.getCollisionBoxes(entity, aabb);
/*     */     
/* 169 */     for (AxisAlignedBB boxAABB : blocks)
/*     */     {
/*     */ 
/* 172 */       Vector3f newBoxPos = new Vector3f(((float)boxAABB.maxX + (float)boxAABB.minX) / 2.0F, ((float)boxAABB.maxY + (float)boxAABB.minY) / 2.0F, ((float)boxAABB.maxZ + (float)boxAABB.minZ) / 2.0F);
/*     */       
/*     */ 
/*     */ 
/* 176 */       if (!isSideBlock(world, newBoxPos))
/*     */       {
/* 178 */         flag = true;
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 183 */         for (Iterator localIterator2 = this.collisionBlocks.iterator(); localIterator2.hasNext();) { collisionBox = (RigidBody)localIterator2.next();
/*     */           
/* 185 */           Vector3f boxPosition = collisionBox.getMotionState().getWorldTransform(new Transform()).origin;
/*     */           
/* 187 */           if ((boxPosition.x == newBoxPos.x) && (boxPosition.y == newBoxPos.y) && (boxPosition.z == newBoxPos.z))
/*     */           {
/* 189 */             flag = true;
/*     */           }
/*     */         }
/*     */         RigidBody collisionBox;
/* 193 */         Object entityList = world.getEntitiesWithinAABBExcludingEntity(entity, boxAABB);
/*     */         
/* 195 */         for (Entity gEntity : (List)entityList)
/*     */         {
/* 197 */           if ((gEntity instanceof GlobalEntity))
/*     */           {
/* 199 */             flag = true;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 204 */         if (world.getEntitiesWithinAABB(EntityItem.class, boxAABB).size() > 0)
/*     */         {
/* 206 */           flag = true;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 211 */       if (!flag)
/*     */       {
/* 213 */         Vector3f boxSize = new Vector3f(((float)boxAABB.maxX - (float)boxAABB.minX) / 2.0F, ((float)boxAABB.maxY - (float)boxAABB.minY) / 2.0F, ((float)boxAABB.maxZ - (float)boxAABB.minZ) / 2.0F);
/*     */         
/*     */ 
/*     */ 
/* 217 */         CollisionShape groundBlock = new BoxShape(boxSize);
/*     */         
/* 219 */         Transform startTransform = new Transform();
/* 220 */         startTransform.setIdentity();
/* 221 */         startTransform.origin.set(newBoxPos);
/*     */         
/* 223 */         DefaultMotionState groundMotionState = new DefaultMotionState(startTransform);
/*     */         
/* 225 */         RigidBodyConstructionInfo groundBodyConstructionInfo = new RigidBodyConstructionInfo(0.0F, groundMotionState, groundBlock, new Vector3f(0.0F, 0.0F, 0.0F));
/* 226 */         RigidBody groundRigidBody = new RigidBody(groundBodyConstructionInfo);
/* 227 */         this.dynamicsWorld.addRigidBody(groundRigidBody);
/* 228 */         this.collisionBlocks.add(groundRigidBody);
/*     */       }
/*     */       
/* 231 */       flag = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isSideBlock(World world, Vector3f pos)
/*     */   {
/* 237 */     BlockPos bp = new BlockPos(pos.x - 0.5F + 1.0F, pos.y - 0.5F, pos.z - 0.5F);
/*     */     
/*     */ 
/* 240 */     for (int i = 0; i < 6; i++)
/*     */     {
/* 242 */       switch (i)
/*     */       {
/*     */       case 1: 
/* 245 */         bp = new BlockPos(pos.x - 0.5F - 1.0F, pos.y - 0.5F, pos.z - 0.5F);
/* 246 */         break;
/*     */       case 2: 
/* 248 */         bp = new BlockPos(pos.x - 0.5F, pos.y - 0.5F + 1.0F, pos.z - 0.5F);
/* 249 */         break;
/*     */       case 3: 
/* 251 */         bp = new BlockPos(pos.x - 0.5F, pos.y - 0.5F - 1.0F, pos.z - 0.5F);
/* 252 */         break;
/*     */       case 4: 
/* 254 */         bp = new BlockPos(pos.x - 0.5F, pos.y - 0.5F, pos.z - 0.5F + 1.0F);
/* 255 */         break;
/*     */       case 5: 
/* 257 */         bp = new BlockPos(pos.x - 0.5F, pos.y - 0.5F, pos.z - 0.5F - 1.0F);
/* 258 */         break;
/*     */       }
/*     */       
/*     */       
/*     */ 
/* 263 */       IBlockState bs = world.getBlockState(bp);
/*     */       
/* 265 */       if (!bs.getBlock().getMaterial(bs).isSolid())
/*     */       {
/* 267 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 271 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void unloadCollisionShapes(World world, List<GlobalEntity> entityList)
/*     */   {
/* 277 */     Set<RigidBody> toRemoveColBox = new HashSet();
/*     */     
/* 279 */     for (RigidBody collisionBks : this.collisionBlocks)
/*     */     {
/* 281 */       boolean flag = false;
/* 282 */       Vector3f boxPosition = collisionBks.getMotionState().getWorldTransform(new Transform()).origin;
/*     */       
/* 284 */       for (GlobalEntity entity : entityList)
/*     */       {
/* 286 */         Vector3f dist = new Vector3f(entity.position);
/* 287 */         dist.sub(boxPosition);
/*     */         
/* 289 */         if (dist.length() <= this.sampleSize + 1)
/*     */         {
/* 291 */           flag = true;
/*     */           
/* 293 */           BlockPos bp = new BlockPos(boxPosition.x, boxPosition.y, boxPosition.z);
/* 294 */           IBlockState bs = world.getBlockState(bp);
/* 295 */           Block block = bs.getBlock();
/*     */           
/* 297 */           flag = block.canCollideCheck(bs, false);
/*     */         }
/*     */       }
/*     */       
/* 301 */       if (!flag)
/*     */       {
/* 303 */         toRemoveColBox.add(collisionBks);
/*     */       }
/*     */     }
/*     */     
/* 307 */     for (RigidBody colBox : toRemoveColBox)
/*     */     {
/* 309 */       this.dynamicsWorld.removeRigidBody(colBox);
/* 310 */       this.collisionBlocks.remove(colBox);
/*     */     }
/*     */     
/* 313 */     toRemoveColBox.clear();
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/PhysicsWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */