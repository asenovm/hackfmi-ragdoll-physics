package com.midtownmadness.bubblecombat;

import android.app.Application;

import com.midtownmadness.bubblecombat.multiplay.MultiplayManager;

public class GlobalContext extends Application {
	private MultiplayManager multiplayManager;

	@Override
	public Object getSystemService(String name) {
		if (MultiplayManager.SERVICE_NAME.equals(name)) {
			if (multiplayManager == null) {
				multiplayManager = new MultiplayManager(this);
			}
			return multiplayManager;
		}
		return super.getSystemService(name);
	}

	public void onGameClose() {
		multiplayManager.close();
	}
}
