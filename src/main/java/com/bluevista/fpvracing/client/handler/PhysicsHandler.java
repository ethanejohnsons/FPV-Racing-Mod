package com.bluevista.fpvracing.client.handler;

import com.bluevista.fpvracing.server.entities.DroneEntity;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

import javax.vecmath.Vector3f;

public class PhysicsHandler {

    private BroadphaseInterface broadphase;
    private CollisionDispatcher dispatcher;
    private ConstraintSolver solver;
    private DiscreteDynamicsWorld dynamicsWorld;

    public PhysicsHandler() {
        DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        dynamicsWorld = new DiscreteDynamicsWorld(new CollisionDispatcher(collisionConfiguration), new DbvtBroadphase(), new SequentialImpulseConstraintSolver(), collisionConfiguration);

        Vector3f gravity = new Vector3f(0, -1, 0);
        gravity.scale(10f);
        dynamicsWorld.setGravity(gravity);
    }

    public void addDroneToWorld(DroneEntity drone) {
//        dynamicsWorld.addRigidBody(new RigidBody());
    }
	
}
