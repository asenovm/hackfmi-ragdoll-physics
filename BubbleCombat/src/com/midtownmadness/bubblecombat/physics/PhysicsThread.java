package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class PhysicsThread extends Thread {
	
	private static final Vec2 GRAVITY = new Vec2(0, 0);

	private World world;
	
	public PhysicsThread() {
		world = new World(GRAVITY);
	}

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			world.step();
		}
	}
}
