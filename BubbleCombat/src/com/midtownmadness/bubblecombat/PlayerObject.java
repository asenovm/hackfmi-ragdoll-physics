package com.midtownmadness.bubblecombat;

import java.util.Random;

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
		
		canvas.drawCircle(this.position.x, this.position.y, radius, paint);
	}
}
