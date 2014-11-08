package com.midtownmadness.bubblecombat.physics;

import java.util.List;

import android.content.res.Resources;

import com.midtownmadness.bubblecombat.game.LevelObject;


public abstract class LevelBuilder {
	public abstract LevelObject build(List<Integer> players, PhysicsService physicsService, Resources resources);
}
