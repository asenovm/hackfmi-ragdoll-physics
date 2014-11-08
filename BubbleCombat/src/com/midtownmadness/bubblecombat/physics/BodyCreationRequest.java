package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.midtownmadness.bubblecombat.multiplay.Callback;

public class BodyCreationRequest extends PhysicsRequest {

	public BodyCreationRequest(BodyDef bodyDef, FixtureDef fixtureDef,
			Callback<BodyCreationRequest> callback) {
		super(callback);
		this.bodyDef = bodyDef;
		this.fixtureDef = fixtureDef;
	}

	public BodyCreationRequest(BodyDef bodyDef, FixtureDef fixtureDef) {
		this(bodyDef, fixtureDef, null);
	}

	public BodyDef bodyDef;
	public FixtureDef fixtureDef;
	public Body body;

	@Override
	protected void apply(World world) {
		Body newBody = world.createBody(bodyDef);
		if(fixtureDef != null)
			newBody.createFixture(fixtureDef);
		this.body = newBody;
	}
}
