package com.midtownmadness.bubblecombat.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	private LevelObject level;
	private SurfaceHolder holder;
	public boolean stop = false;

	public DrawThread(LevelObject level, SurfaceHolder holder) {
		super();
		this.level = level;
		this.holder = holder;
	}

	@Override
	public void run() {
		Rect backgroundDest = new Rect();
		backgroundDest.bottom = (int) level.size.y;
		backgroundDest.right = (int) level.size.x;
		
		while (!stop) {
			Canvas canvas = holder.lockCanvas();
			if (canvas != null) {
				long startTime = System.currentTimeMillis();
				
				canvas.save();
				canvas.scale(level.scale.x, level.scale.y);
				canvas.drawColor(Color.BLACK);
				canvas.drawBitmap(level.background, null, backgroundDest, null);
				
				for (GameObject gobject : level.objects) {
					gobject.render(canvas);
				}
				
				canvas.restore();
				holder.unlockCanvasAndPost(canvas);
				
				long totalTime = System.currentTimeMillis() - startTime;
				if (totalTime < 16) {
					try {
						Thread.sleep(16 - totalTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}