package com.midtownmadness.bubblecombat.game;

import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PlayerObject extends GameObject {
	private static final int radius = 10;
	private static final Random rand = new Random();
	protected Paint paint;
	
	protected int health;
	
	public PlayerObject()
	{
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(getRandomColor());
	}
	
	private int getRandomColor()
	{
		// Will produce only bright / light colors:
		int r = rand.nextInt(128) + 128;
		int g = rand.nextInt(128) + 128;
		int b = rand.nextInt(128) + 128;
		
		return Color.rgb(r, g, b);
	}
	
	@Override
	public void render(Canvas canvas) {
		super.render(canvas);
		
		if (body != null)
		{
			Vec2 position = body.getPosition();
			canvas.drawCircle(position.x, position.y, radius, paint);
		}
	}
	
	@Override
	public FixtureDef buildFixtureDef() {
		FixtureDef def = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		def.shape = shape;
		def.friction = 0.2f;
		def.restitution = 0.7f;
		
		return def;
	}
	
	@Override
	public BodyDef buildBodyDef() {
		BodyDef def = new BodyDef();
		return def;
	}
}
