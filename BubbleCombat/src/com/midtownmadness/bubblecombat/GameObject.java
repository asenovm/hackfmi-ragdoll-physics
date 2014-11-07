package com.midtownmadness.bubblecombat;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import android.graphics.Canvas;

public class GameObject {
	protected static int staticId = 0;
	protected int id; 
	protected Vec2 position;
	protected Vec2 velocity;
	protected float mass;
	
	public GameObject()
	{
		id = staticId++;
	}
	
	public void render(Canvas canvas)
	{
		
	}
	
	public BodyDef buildBodyDef() {
		return null;
	}
	
	public FixtureDef buildFixtureDef() {
		return null;
	}
	
	public void setBody(Body body) {
		
	}
}
