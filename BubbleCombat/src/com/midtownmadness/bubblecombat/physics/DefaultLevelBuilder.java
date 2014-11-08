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

	public Bitmap background;
	
	@Override
	public LevelObject build(PhysicsService physicsService, Resources resources) {
		LevelObject levelObject = new LevelObject();
		
		// Level size
		levelObject.size = new PointF(130, 100);
		
		// Walls
		List<GameWallObject> walls = Arrays.asList(
				new GameWallObject(new RectF(0, 0, GameWallObject.WALL_THICKNESS, levelObject.size.y)),
				new GameWallObject(new RectF(levelObject.size.x - GameWallObject.WALL_THICKNESS, 0, levelObject.size.x, levelObject.size.y)),
				new GameWallObject(new RectF(0, 0, levelObject.size.x, GameWallObject.WALL_THICKNESS)),
				new GameWallObject(new RectF(0, levelObject.size.y - GameWallObject.WALL_THICKNESS, levelObject.size.x, levelObject.size.y))
				);
		levelObject.objects = new ArrayList<GameObject>(walls);
		for(final GameWallObject wall : walls) {
			physicsService.createBody(new BodyCreationRequest(wall.buildBodyDef(), wall.buildFixtureDef(), new Callback<BodyCreationRequest>() {
				
				@Override
				public void call(BodyCreationRequest argument) {
					wall.setBody(argument.body);
					wall.getBody().setUserData(new BodyUserData(wall));
				}
			}));
		}
		
		// Player
		Vec2 position = new Vec2(50, 50);
		levelObject.setThisPlayer(buildPlayer(position, levelObject, physicsService));
		
		// Background
		levelObject.background = BitmapFactory.decodeResource(resources, R.drawable.background);
		
		return levelObject;
	}

	private PlayerObject buildPlayer(Vec2 position, LevelObject levelObject, PhysicsService physicsService) {
		final PlayerObject player = new PlayerObject(position);
		levelObject.objects.add(player);
		physicsService.createBody(new BodyCreationRequest(player.buildBodyDef(), player.buildFixtureDef(), new Callback<BodyCreationRequest>() {
			
			@Override
			public void call(BodyCreationRequest argument) {
				player.setBody(argument.body);
				player.getBody().setUserData(new BodyUserData(player));
			}
		}));
		return player;
	}
}
