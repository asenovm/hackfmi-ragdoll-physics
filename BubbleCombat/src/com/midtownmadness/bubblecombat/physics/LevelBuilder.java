package com.midtownmadness.bubblecombat.physics;

import com.midtownmadness.bubblecombat.game.LevelObject;


public abstract class LevelBuilder {
	public abstract LevelObject build(PhysicsService physicsService);
}
