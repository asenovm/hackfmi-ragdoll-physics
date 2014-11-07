package com.midtownmadness.bubblecombat;

import org.jbox2d.common.Vec2;

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
}
