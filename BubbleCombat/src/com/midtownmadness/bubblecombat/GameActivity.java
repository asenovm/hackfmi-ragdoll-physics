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

import static com.midtownmadness.bubblecombat.Settings.EXTRA_SYNC_STAMP;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Display;

import com.midtownmadness.bubblecombat.game.GameObject;
import com.midtownmadness.bubblecombat.game.LevelObject;
import com.midtownmadness.bubblecombat.game.PlayerObject;
import com.midtownmadness.bubblecombat.multiplay.MultiplayEvent;
import com.midtownmadness.bubblecombat.multiplay.MultiplayEventListener;
import com.midtownmadness.bubblecombat.multiplay.MultiplayManager;
import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;
import com.midtownmadness.bubblecombat.physics.CollisionListener;
import com.midtownmadness.bubblecombat.physics.DefaultLevelBuilder;
import com.midtownmadness.bubblecombat.physics.LevelBuilder;
import com.midtownmadness.bubblecombat.physics.PhysicsService;

public class GameActivity extends BaseActivity implements
		MultiplayEventListener, CollisionListener {

	private static final float MAX_VELOCITY = 400f;
	private static final float MAX_DMG_ON_COLLISION = 5;

	private GameView gameView;
	private PhysicsService physicsService;
	private LevelObject level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Intent intent = getIntent();
		final long syncStamp = intent.getLongExtra(EXTRA_SYNC_STAMP, -1);

		toast(String.format("sync stamp is %s", syncStamp));

		// Multiplayer reference example
		MultiplayManager manager = (MultiplayManager) getSystemService(MultiplayManager.SERVICE_NAME);
		if (manager == null) {
			throw new RuntimeException();
		}
		manager.addListener(this);

		List<Integer> players = manager.getPlayerIds();
		LevelBuilder lbuilder = new DefaultLevelBuilder();
		physicsService = new PhysicsService();
		physicsService.setCollisionListener(this);
		level = lbuilder.build(players, physicsService, getResources());

		// get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point screenSize = new Point();
		display.getSize(screenSize);

		level.scale = new PointF();
		level.scale.x = level.scale.y = screenSize.y / level.size.y;

		gameView = new GameView(this, level, physicsService, manager);
		setContentView(gameView);

		manager.action();
	}

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
	public void onMultiplayEvent(final MultiplayEvent e, final int playerId) {
		Body playerBody = level.getPlayerObject(playerId).getBody();
		physicsService.applyMovement(playerBody, new Vec2(e.x, e.y));
	}

	@Override
	public void onPlayerConnected(int playerId) {

	}

	@Override
	public void onGameDiscovered(MultiplayerGame multiplayerGame) {
		// XXX do nothing
	}

	@Override
	public void onGameSynced(MultiplayerGame game) {

	}

	@Override
	public void onGameCommenced(final long syncStamp) {
		// TODO relevant?
	}

	@Override
	public void onError() {
		toast(R.string.error_text);
		finish();
	}

	@Override
	public void collision(GameObject gameObj1, GameObject gameObj2,
			Contact contact) {
		playCollisionSound();

		PlayerObject thisPlayer = level.getThisPlayer();
		if (gameObj1 == thisPlayer || gameObj2 == thisPlayer) {
			float velocity = thisPlayer.getBody().getLinearVelocity().length();
			float dmg = (velocity / MAX_VELOCITY) * MAX_DMG_ON_COLLISION;
			thisPlayer.takeDamage(dmg);
			if (thisPlayer.getHealth() == 0) {
				endGame();
			}
		}
	}

	private void endGame() {
		// TODO Auto-generated method stub

	}

	private void playCollisionSound() {
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.collision);
		mediaPlayer.start();
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}
}
