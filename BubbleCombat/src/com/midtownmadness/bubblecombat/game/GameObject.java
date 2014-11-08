package com.midtownmadness.bubblecombat.game;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import android.graphics.Canvas;

public abstract class GameObject {
	protected static int staticId = 0;
	protected int id;
	protected Body body;

	public GameObject() {
		id = staticId++;
	}

	public abstract void render(Canvas canvas);

	public abstract BodyDef buildBodyDef();

	public abstract FixtureDef buildFixtureDef();

	public void setBody(Body body) {
		this.body = body;
	}
	
	public Body getBody() {
		return body;
	}
}
