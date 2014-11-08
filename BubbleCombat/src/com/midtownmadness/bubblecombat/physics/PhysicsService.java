package com.midtownmadness.bubblecombat.physics;

import java.util.ArrayDeque;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.JointType;

public class PhysicsService implements Runnable {
	public interface Callback {
		void callback(Body body);
	}

	private class BodyCreationRequest {
		public BodyDef bodyDef;
		public Callback callback;
		public FixtureDef fixtureDef;
	}

	public static final String SERVICE_NAME = "PhysicsService";

	private static final Vec2 GRAVITY = new Vec2(0, 0);

	private static final int VELOCITY_ITERATIONS = 1;

	private static final int POSITION_ITERATIONS = 1;

	private static final long ITERATION_TIME = 16;

	private Thread thread;
	private World world;
	private ArrayDeque<BodyCreationRequest> bodyCreationQueue = new ArrayDeque<BodyCreationRequest>();

	private boolean paused;

	public PhysicsService() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		world = new World(GRAVITY);

		boolean running = true;
		while (running) {
			if (!paused) {
				long startTime = System.currentTimeMillis();
				while (!bodyCreationQueue.isEmpty()) {
					BodyCreationRequest request = bodyCreationQueue.poll();
					Body newBody = world.createBody(request.bodyDef);
					newBody.createFixture(request.fixtureDef);
					if (request.callback != null)
						request.callback.callback(newBody);
				}
				world.step(0.016666666f, VELOCITY_ITERATIONS,
						POSITION_ITERATIONS);
				world.clearForces();
				long stepTime = System.currentTimeMillis() - startTime;
				if (stepTime < ITERATION_TIME) {
					try {
						Thread.sleep(ITERATION_TIME - stepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
						running = false;
					}
				}
			}
		}
	}

	public void pause() {
		paused = true;
	}

	public void unpause() {
		paused = false;
	}

	public void createBody(BodyDef bodyDef, FixtureDef fixtureDef,
			Callback callback) {
		BodyCreationRequest entry = new BodyCreationRequest();
		entry.bodyDef = bodyDef;
		entry.callback = callback;
		entry.fixtureDef = fixtureDef;
		bodyCreationQueue.push(entry);
	}
	
	private void createJoint(JointDef joint, Callback callback) {
		
	}
	
	public void createMoveAnchor(Body body) {
		
	}
	
	public void destroyMoveAnchor(Body body) {
		
	}
	
	public void applyMovement(Body body) {
		
	}
}
