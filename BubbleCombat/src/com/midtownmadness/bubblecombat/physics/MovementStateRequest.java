package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import com.midtownmadness.bubblecombat.multiplay.Callback;

public class MovementStateRequest extends PhysicsRequest {

	private Body body;
	private Vec2 movement;
	private Vec2 velocity;
	private Vec2 position;

	public MovementStateRequest(Body body, Vec2 movement,
			Vec2 position, Vec2 velocity,
			Callback<MovementStateRequest> callback) {
		super(callback);
		this.body = body;
		this.movement = movement;
		this.position = position;
		this.velocity = velocity;
	}

	@Override
	protected void apply(World world) {
		if(body != null) {
			body.setTransform(position, 0);
			body.setLinearVelocity(velocity);
			if(movement.length() != 0)
				body.applyForceToCenter(movement.mul(1000));
		}
	}
}
