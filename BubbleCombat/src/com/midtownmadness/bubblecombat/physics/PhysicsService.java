package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class PhysicsService implements Runnable {
	public interface Callback {
		void callback(Body body);
	}

	public static final String SERVICE_NAME = "PhysicsService";

	private static final Vec2 GRAVITY = new Vec2(0, 0);

	private static final int VELOCITY_ITERATIONS = 1;

	private static final int POSITION_ITERATIONS = 1;

	private static final long ITERATION_TIME = 16;

	private Thread thread;
	private World world;

	private boolean paused;

	public PhysicsService() {
		world = new World(GRAVITY);
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			if (!paused) {
				long startTime = System.currentTimeMillis();
				world.step(0.016666666f, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
				long stepTime = System.currentTimeMillis() - startTime;
				if(stepTime < ITERATION_TIME) {
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
	
	public void createBody(BodyDef bodyDef, Callback callback) {
		
	}
}
