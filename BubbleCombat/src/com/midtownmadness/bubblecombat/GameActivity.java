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

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Display;

import com.midtownmadness.bubblecombat.game.LevelObject;
import com.midtownmadness.bubblecombat.multiplay.MultiplayManager;
import com.midtownmadness.bubblecombat.physics.DefaultLevelBuilder;
import com.midtownmadness.bubblecombat.physics.LevelBuilder;
import com.midtownmadness.bubblecombat.physics.PhysicsService;


public class GameActivity extends BaseActivity {
	private GameView gameView;
	private PhysicsService physicsService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Multiplayer reference example
		MultiplayManager manager = (MultiplayManager) getSystemService(MultiplayManager.SERVICE_NAME);
		if (manager == null){
			throw new RuntimeException();
		}
		
		LevelBuilder lbuilder = new DefaultLevelBuilder();
		physicsService = new PhysicsService();
		LevelObject level = lbuilder.build(physicsService, getResources());
		
		// get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point screenSize = new Point();
		display.getSize(screenSize);
		
		level.scale = new PointF();
		level.scale.x = level.scale.y = screenSize.y / level.size.y;
		
		gameView = new GameView(this, level, physicsService);
		setContentView(gameView);
	}

	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

}
