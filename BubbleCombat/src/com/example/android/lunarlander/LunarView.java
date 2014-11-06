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

package com.example.android.lunarlander;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
class LunarView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;

	private DrawThread drawThread;

	private boolean surfaceCreated;

	private static final String TAG = LunarView.class.getSimpleName();

	public LunarView(Context context, AttributeSet attrs) {
		super(context, attrs);

		holder = getHolder();
		holder.addCallback(this);

		drawThread = new DrawThread();

		setFocusable(true); // make sure we get key events
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.RED);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		surfaceCreated = true;
		Canvas canvas = holder.lockCanvas();
		onDraw(canvas);
		holder.unlockCanvasAndPost(canvas);
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
				LunarView.this.onDraw(canvas);
				holder.unlockCanvasAndPost(canvas);
			}
			handler.postDelayed(this, 16);
		}
	}
}
