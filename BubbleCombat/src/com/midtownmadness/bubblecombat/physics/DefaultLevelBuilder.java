package com.midtownmadness.bubblecombat.physics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbox2d.common.Vec2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;

import com.midtownmadness.bubblecombat.R;
import com.midtownmadness.bubblecombat.game.GameObject;
import com.midtownmadness.bubblecombat.game.GameWallObject;
import com.midtownmadness.bubblecombat.game.LevelObject;
import com.midtownmadness.bubblecombat.game.PlayerObject;
import com.midtownmadness.bubblecombat.multiplay.Callback;

public class DefaultLevelBuilder extends LevelBuilder {

	private static final int[] PLAYER_BITMAPS = new int[] {
			R.drawable.character1, R.drawable.character2, R.drawable.character3 };

	private static final int STARTING_X = 55;
	private static final float STARTING_Y = 50;
	public Bitmap background;

	@Override
	public LevelObject build(List<Integer> players,
			PhysicsService physicsService, Resources resources) {
		LevelObject levelObject = new LevelObject();

		// Level size
		levelObject.size = new PointF(167, 100);

		// Walls
		List<GameWallObject> walls = Arrays.asList(new GameWallObject(
				new RectF(0, 0, GameWallObject.WALL_THICKNESS,
						levelObject.size.y)), new GameWallObject(new RectF(
				levelObject.size.x - GameWallObject.WALL_THICKNESS, 0,
				levelObject.size.x, levelObject.size.y)), new GameWallObject(
				new RectF(0, 0, levelObject.size.x,
						GameWallObject.WALL_THICKNESS)), new GameWallObject(
				new RectF(0,
						levelObject.size.y - GameWallObject.WALL_THICKNESS,
						levelObject.size.x, levelObject.size.y)));
		levelObject.objects = new ArrayList<GameObject>(walls);
		for (final GameWallObject wall : walls) {
			physicsService.createBody(new BodyCreationRequest(wall
					.buildBodyDef(), wall.buildFixtureDef(),
					new Callback<BodyCreationRequest>() {

						@Override
						public void call(BodyCreationRequest argument) {
							wall.setBody(argument.body);
							wall.getBody().setUserData(new BodyUserData(wall));
						}
					}));
		}

		// Player
		int currentPlayerId = players.get(0);
		levelObject.setThisPlayer(buildPlayer(levelObject,
				physicsService, currentPlayerId, resources));
		for (int i = 1; i < players.size(); i++) {
			buildPlayer(levelObject, physicsService,
					players.get(i), resources);
		}
		
		// Level objects
		Bitmap swordBitmap = BitmapFactory.decodeResource(resources,
				R.drawable.sword);
		for (int i = 0; i < 4; i++) {
			final CentralHurdle centralHurdle = new CentralHurdle(
					levelObject.size.x / 2, levelObject.size.y / 2,
					(float) (Math.PI * ((float)i / 2)), swordBitmap);
			levelObject.objects.add(centralHurdle);
			physicsService.createBody(new BodyCreationRequest(centralHurdle.buildBodyDef(), centralHurdle.buildFixtureDef(), new Callback<BodyCreationRequest>() {
				
				@Override
				public void call(BodyCreationRequest argument) {
					centralHurdle.setBody(argument.body);
					argument.body.setUserData(new BodyUserData(centralHurdle));
				}
			}));
		}

		// Background
		levelObject.background = BitmapFactory.decodeResource(resources,
				R.drawable.background);

		return levelObject;
	}

	private PlayerObject buildPlayer(LevelObject levelObject,
			PhysicsService physicsService, int playerId, Resources resources) {
		
		Bitmap bitmap = BitmapFactory.decodeResource(resources,
				PLAYER_BITMAPS[playerId % PLAYER_BITMAPS.length]);
		
		final PlayerObject player = new PlayerObject(new Vec2((playerId + 1) * STARTING_X, STARTING_Y), bitmap, playerId);
		levelObject.objects.add(player);
		physicsService.createBody(new BodyCreationRequest(
				player.buildBodyDef(), player.buildFixtureDef(),
				new Callback<BodyCreationRequest>() {

					@Override
					public void call(BodyCreationRequest argument) {
						player.setBody(argument.body);
						player.getBody().setUserData(new BodyUserData(player));
					}
				}));
		return player;
	}
}
