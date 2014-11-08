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

import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Display;

import com.midtownmadness.bubblecombat.game.GameObject;
import com.midtownmadness.bubblecombat.game.LevelObject;
import com.midtownmadness.bubblecombat.game.PlayerObject;
import com.midtownmadness.bubblecombat.multiplay.MultiplayEvent;
import com.midtownmadness.bubblecombat.multiplay.MultiplayEventListener;
import com.midtownmadness.bubblecombat.multiplay.MultiplayManager;
import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;

public class GameActivity extends BaseActivity implements
		MultiplayEventListener {
	private GameView gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Multiplayer reference example
		MultiplayManager multiplayManager = (MultiplayManager) getSystemService(MultiplayManager.SERVICE_NAME);
		multiplayManager.addListener(this);

		// LevelBuilder lbuilder = new DefaultLevelBuilder();
		// LevelObject level = lbuilder.build(null);

		LevelObject level = new LevelObject();
		level.objects = new ArrayList<GameObject>();
		level.objects.add(new PlayerObject());
		level.size = new PointF(130, 100);

		// get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point screenSize = new Point();
		display.getSize(screenSize);

		level.scale = new PointF();
		level.scale.x = level.scale.y = screenSize.y / level.size.y;

		level.background = BitmapFactory.decodeResource(getResources(),
				R.drawable.background);

		gameView = new GameView(this, level);
		setContentView(gameView);

		
//		load();
		int connectedPlayerId = multiplayManager.getPlayerId();
//		multiplayManager.sendGoMessage(connectedPlayerId);
		
	}

//	private Object load() {
//		try {
//			Thread.sleep(100);
//			return new Object();
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		super.onGameClose();
		finish();
	}

	@Override
	public void onMultiplayEvent(MultiplayEvent e) {

	}

	@Override
	public void onPlayerConnected(int playerId) {
		// XXX do nothing here
	}

	@Override
	public void onGameDiscovered(MultiplayerGame multiplayerGame) {
		// XXX do nothing
	}

	@Override
	public void onGameSynced(MultiplayerGame game) {

	}

}
