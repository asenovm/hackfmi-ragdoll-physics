package com.midtownmadness.bubblecombat.physics;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import com.midtownmadness.bubblecombat.game.GameObject;
import com.midtownmadness.bubblecombat.multiplay.MultiplayEvent;

public class PhysicsService implements ContactListener {

	private static final Vec2 GRAVITY = new Vec2(0, 0);

	private static final int VELOCITY_ITERATIONS = 4;

	private static final int POSITION_ITERATIONS = 4;

	private World world;
	private BlockingQueue<PhysicsRequest> requestQueue = new ArrayBlockingQueue<PhysicsRequest>(
			128);

	private CollisionListener collisionListener;

	public void doPhysicsStep() {
		while (!requestQueue.isEmpty()) {
			PhysicsRequest request = requestQueue.poll();
			request.applyRequest(world);
		}
		world.step(0.016666666f / 3, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		world.step(0.016666666f / 3, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		world.step(0.016666666f / 3, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		world.clearForces();
	}

	public void startService() {
		world = new World(GRAVITY);
		world.setContinuousPhysics(true);
		world.setContactListener(this);
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

	@Override
	public void beginContact(Contact contact) {
		BodyUserData buda1 = (BodyUserData) contact.getFixtureA().getBody()
				.getUserData();
		BodyUserData buda2 = (BodyUserData) contact.getFixtureB().getBody()
				.getUserData();

		GameObject gameObj1 = buda1.gameObject;
		GameObject gameObj2 = buda2.gameObject;

		dispatchCollision(gameObj1, gameObj2, contact);
	}

	public void setCollisionListener(CollisionListener listener) {
		collisionListener = listener;
	}

	private void dispatchCollision(GameObject gameObj1, GameObject gameObj2,
			Contact contact) {
		if (collisionListener != null)
			collisionListener.collision(gameObj1, gameObj2, contact);
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// Nothing
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// Nothing
	}

	public void applyState(Body playerBody, MultiplayEvent e) {
		requestQueue.add(new MovementStateRequest(playerBody, new Vec2(e.dx,
				e.dy), new Vec2(e.x, e.y), new Vec2(e.vx, e.vy), null));
	}
}
