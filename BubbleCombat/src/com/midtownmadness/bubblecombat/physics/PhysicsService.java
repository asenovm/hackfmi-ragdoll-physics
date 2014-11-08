package com.midtownmadness.bubblecombat.physics;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class PhysicsService implements Runnable {

	private static final Vec2 GRAVITY = new Vec2(0, 0);

	private static final int VELOCITY_ITERATIONS = 4;

	private static final int POSITION_ITERATIONS = 4;

	private static final long ITERATION_TIME = 16;

	private World world;
	private BlockingQueue<PhysicsRequest> requestQueue = new ArrayBlockingQueue<PhysicsRequest>(
			32);

	private boolean paused;

	private boolean running;

	@Override
	public void run() {
		world = new World(GRAVITY);
		
		while (running) {
			if (!paused) {
				long startTime = System.currentTimeMillis();
				doPhysicsStep();
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

	public void doPhysicsStep() {
		while (!requestQueue.isEmpty()) {
			PhysicsRequest request = requestQueue.poll();
			request.applyRequest(world);
		}
		world.step(0.016666666f/3, VELOCITY_ITERATIONS,
				POSITION_ITERATIONS);
		world.step(0.016666666f/3, VELOCITY_ITERATIONS,
				POSITION_ITERATIONS);
		world.step(0.016666666f/3, VELOCITY_ITERATIONS,
				POSITION_ITERATIONS);
		world.clearForces();
	}
	
	public void startService() {
		running = true;
		world = new World(GRAVITY);
	}

	public void stop() {
		running = false;
	}

	public void pause() {
		paused = true;
	}

	public void unpause() {
		paused = false;
	}

	public void createBody(BodyCreationRequest request) {
		requestQueue.add(request);
	}

	public void createBody(BodyDef bodyDef, FixtureDef fixtureDef) {
		BodyCreationRequest entry = new BodyCreationRequest(bodyDef, fixtureDef);
		requestQueue.offer(entry);
	}

	public void applyMovement(Body body, Vec2 movement) {
		requestQueue.add(new MovementRequest(body, movement, null));
	}
}
