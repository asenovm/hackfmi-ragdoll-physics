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

import org.jbox2d.common.Vec2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.midtownmadness.bubblecombat.game.DrawThread;
import com.midtownmadness.bubblecombat.game.LevelObject;
import com.midtownmadness.bubblecombat.physics.PhysicsService;

@SuppressLint("WrongCall")
class GameView extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener {
	private SurfaceHolder holder;
	private DrawThread drawThread;
	private LevelObject level;

	private PointF startDragPosition = new PointF();
	private PointF endDragPosition = new PointF();
	private PhysicsService physics;

	private static final String TAG = GameView.class.getSimpleName();

	public GameView(Context context, LevelObject level, PhysicsService physics) {
		super(context);
		setOnTouchListener(this);

		holder = getHolder();
		holder.addCallback(this);
		this.level = level;

		setFocusable(true); // make sure we get key events
		
		this.physics = physics;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		physics.startService();
		drawThread = new DrawThread(level, holder, physics);
		drawThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.stop = true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Vec2 dragVector;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startDragPosition.set(event.getRawX(), event.getRawY());
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			endDragPosition.set(event.getRawX(), event.getRawY());

			dragVector = new Vec2((endDragPosition.x - startDragPosition.x)
					/ level.scale.x, (endDragPosition.y - startDragPosition.y)
					/ level.scale.y);
			physics.applyMovement(level.getThisPlayer().getBody(), dragVector);
		}
		return true;
	}
}
