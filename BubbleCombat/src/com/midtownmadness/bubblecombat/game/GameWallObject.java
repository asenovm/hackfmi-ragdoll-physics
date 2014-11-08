package com.midtownmadness.bubblecombat.game;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class GameWallObject extends GameObject {

	public static final int WALL_THICKNESS = 1;

	protected Paint paint;
	private RectF rect;

	public GameWallObject(RectF rect) {
		this.rect = rect;

		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
	}

	@Override
	public void render(Canvas canvas) {
		canvas.drawRect(rect, paint);
	}

	@Override
	public BodyDef buildBodyDef() {
		BodyDef def = new BodyDef();
		def.type = BodyType.STATIC;
		def.position = new Vec2(0, 0);
		def.angle = 0;
		return def;
	}

	@Override
	public FixtureDef buildFixtureDef() {
		FixtureDef wallFixture;
		wallFixture = new FixtureDef();
		PolygonShape wallShape = new PolygonShape();
		wallShape.set(new Vec2[] {
				new Vec2(rect.left, rect.top),
				new Vec2(rect.left, rect.bottom),
				new Vec2(rect.right, rect.bottom),
				new Vec2(rect.right, rect.top)
		}, 4);
		wallFixture.shape = wallShape;
		return wallFixture;
	}
}
