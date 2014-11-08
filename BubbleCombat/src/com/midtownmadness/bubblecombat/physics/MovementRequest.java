package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import com.midtownmadness.bubblecombat.multiplay.Callback;

public class MovementRequest extends PhysicsRequest {

	private Body body;
	private Vec2 movement;

	public MovementRequest(Body body, Vec2 movement,
			Callback<MovementRequest> callback) {
		super(callback);
		this.body = body;
		this.movement = movement;
	}

	@Override
	protected void apply(World world) {
		body.applyForceToCenter(movement.mul(1000));
	}
}
