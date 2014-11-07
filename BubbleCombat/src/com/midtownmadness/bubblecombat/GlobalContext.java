package com.midtownmadness.bubblecombat;

import com.midtownmadness.bubblecombat.multiplay.MultiplayManager;
import com.midtownmadness.bubblecombat.physics.PhysicsService;

import android.app.Application;

public class GlobalContext extends Application {
	private MultiplayManager multiplayManager;
	private PhysicsService physicsService;

	@Override
	public Object getSystemService(String name) {
		if (MultiplayManager.SERVICE_NAME.equals(name)) {
			if (multiplayManager == null) {
				multiplayManager = new MultiplayManager(this);
			}
			return multiplayManager;
		} else if (PhysicsService.SERVICE_NAME.equals(name)) {
			if (physicsService == null) {
				physicsService = new PhysicsService();
			}
			return physicsService;
		}
		return super.getSystemService(name);
	}
}
