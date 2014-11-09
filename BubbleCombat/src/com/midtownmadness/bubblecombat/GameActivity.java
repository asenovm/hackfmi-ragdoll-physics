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

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.widget.TextView;

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

	private long syncStamp;

	private boolean isGameEnded;

	private class GameResultRunnable implements Runnable {

		private final boolean isWinner;

		public GameResultRunnable(final boolean isWinner) {
			this.isWinner = isWinner;
		}

		@Override
		public void run() {
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					GameActivity.this);

			final Dialog dialog = builder
					.setTitle(R.string.game_over)
					.setMessage(
							isWinner ? R.string.game_win : R.string.game_lose)
					.setCancelable(false)
					.setPositiveButton(R.string.back_to_menu,
							new QuitOnClickListener()).create();

			dialog.show();
		}
	}

	private class QuitOnClickListener implements OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Intent intent = getIntent();
		syncStamp = intent.getLongExtra(EXTRA_SYNC_STAMP, -1);

		toast(String.format("sync stamp is %s", syncStamp));

		// Multiplayer reference example
		MultiplayManager manager = (MultiplayManager) getSystemService(MultiplayManager.SERVICE_NAME);
		if (manager == null) {
			throw new RuntimeException();
		}
		manager.addListener(this);

		List<Integer> players = manager.getPlayerIds();
		players.add(0, manager.getMyPlayerId());
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
		PlayerObject playerObject = level.getPlayerObject(playerId);
		playerObject.setHealth(e.health);
		Body playerBody = playerObject.getBody();
		physicsService.applyState(playerBody, e);
		if (e.health == 0) {
			endGame(playerId);
		}
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
				endGame(thisPlayer.getPlayerId());
			}
		}
	}

	private void endGame(final int loserId) {
		if (!isGameEnded) {
			isGameEnded = true;
			final int currentPlayerId = level.getThisPlayer().getPlayerId();
			runOnUiThread(new GameResultRunnable(currentPlayerId != loserId));
		}
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
