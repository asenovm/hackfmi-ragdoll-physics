/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.midtownmadness.bubblecombat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

@SuppressLint("WrongCall")
class GameView extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener {
	private SurfaceHolder holder;

	private DrawThread drawThread;

	private boolean surfaceCreated;

	private static final String TAG = GameView.class.getSimpleName();

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(this);

		holder = getHolder();
		holder.addCallback(this);

		drawThread = new DrawThread();

		setFocusable(true); // make sure we get key events
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.RED);
//		Log.d(TAG, "drawed");
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		surfaceCreated = true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	private class DrawThread extends Thread {
		private Handler handler;

		public DrawThread() {
			super("ceco");
			start();

		}

		@Override
		public void run() {
			if (handler == null) {
				Looper.prepare();
				handler = new Handler();
				handler.postDelayed(this, 16);
				Looper.loop();
			}
			if (surfaceCreated) {
				Canvas canvas = holder.lockCanvas();
				GameView.this.onDraw(canvas);
				holder.unlockCanvasAndPost(canvas);
			}
			handler.postDelayed(this, 16);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return false;
	}
}
