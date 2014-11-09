package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.midtownmadness.bubblecombat.game.GameObject;

public class CentralHurdle extends GameObject {
	
	private static final float ANGULAR_VELOCITY = 7f;
	private static final float BLADE_LENGTH = 20f;
	private static final float BLADE_WIDTH = 5f;
	private float centerY;
	private float centerX;
	private float angle;
	private Bitmap bitmap;
	private Rect bitmapBounds;
	private Rect boundingBox;
	
	public CentralHurdle(float centerX, float centerY, float angle, Bitmap bitmap) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.angle = angle;
		this.bitmap = bitmap;
		bitmapBounds = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		boundingBox = new Rect(
				(int) (centerX - BLADE_WIDTH / 2),
				(int) (centerY - BLADE_LENGTH),
				(int) (centerX + BLADE_WIDTH / 2),
				(int) (centerY));
	}

	@Override
	public void render(Canvas canvas) {
		if(body != null) {
			canvas.save();
			canvas.translate(centerX, centerY);
			canvas.rotate((float) Math.toDegrees(body.getAngle()));
			canvas.translate(-centerX, -centerY);
			canvas.drawBitmap(bitmap, bitmapBounds, boundingBox, null);
			canvas.restore();
		}
	}

	@Override
	public BodyDef buildBodyDef() {
		BodyDef result = new BodyDef();
		result.type = BodyType.KINEMATIC;
		result.angularVelocity = ANGULAR_VELOCITY;
		result.position = new Vec2(centerX, centerY);
		result.angle = angle;
		return result;
	}

	@Override
	public FixtureDef buildFixtureDef() {
		FixtureDef result = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(BLADE_WIDTH / 2, BLADE_LENGTH / 2, new Vec2(BLADE_WIDTH / 2, BLADE_LENGTH / 2), 0);
		result.shape = shape;
		return result;
	}
}
