package com.midtownmadness.bubblecombat.game;

import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class PlayerObject extends GameObject {
	private static final int radius = 5;
	private static final Random rand = new Random();
	private static final float MAX_HEALTH = 100;
	protected Paint paint;
	protected Paint healthBarPaint;
	
	protected float health;
	private Vec2 initialPosition;
	private RectF boundingBox;
	
	public PlayerObject(Vec2 initialPosition)
	{
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(getRandomColor());
		this.initialPosition = initialPosition;
		
		healthBarPaint = new Paint();
		healthBarPaint.setStyle(Paint.Style.STROKE);
		healthBarPaint.setColor(Color.RED);
		healthBarPaint.setStrokeWidth(1f);
		
		health = MAX_HEALTH;
		boundingBox = new RectF();
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
		if (body != null)
		{
			Vec2 position = body.getPosition();
			canvas.drawCircle(position.x, position.y, radius, paint);
			
			boundingBox.set(position.x - radius, position.y - radius, position.x + radius, position.y + radius);
			canvas.drawArc(boundingBox, 0, (float) (2 * 180 * (health / MAX_HEALTH)), false, healthBarPaint);
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
		def.density = 0.001f;
		
		return def;
	}
	
	@Override
	public BodyDef buildBodyDef() {
		BodyDef def = new BodyDef();
		def.position = initialPosition;
		def.type = BodyType.DYNAMIC;
		def.bullet = true;
		return def;
	}

	public void takeDamage(float dmg) {
		health = Math.max(0f, health - dmg);
	}

	public float getHealth() {
		return health;
	}
}
