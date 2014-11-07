package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class DefaultLevelBuilder extends LevelBuilder {

	public static final Vec2 BOUNDING_BOX = new Vec2(100, 120);

	@Override
	public void build(PhysicsService physicsService) {
		physicsService.createBody(createWall(0, 0, 0),
				createWallFixture(BOUNDING_BOX.x), null);

		physicsService.createBody(createWall(0, BOUNDING_BOX.y, 0),
				createWallFixture(BOUNDING_BOX.x), null);

		physicsService.createBody(createWall(0, 0, -(float) (Math.PI / 2)),
				createWallFixture(BOUNDING_BOX.y), null);

		physicsService.createBody(createWall(BOUNDING_BOX.x, 0, -(float) (Math.PI / 2)),
				createWallFixture(BOUNDING_BOX.y), null);
	}

	private BodyDef createWall(float x, float y, float angle) {
		BodyDef def = new BodyDef();
		def.type = BodyType.STATIC;
		def.position = new Vec2(x, y);
		def.angle = angle;
		return def;
	}

	private FixtureDef createWallFixture(float length) {
		FixtureDef wallFixture;
		wallFixture = new FixtureDef();
		PolygonShape wallShape = new PolygonShape();
		wallShape.setAsBox(length, 1);
		wallFixture.shape = wallShape;
		return wallFixture;
	}
}
