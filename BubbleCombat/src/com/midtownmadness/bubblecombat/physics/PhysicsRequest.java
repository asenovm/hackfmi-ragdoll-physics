package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.dynamics.World;

public abstract class PhysicsRequest {
	public abstract void apply(World world);
}
