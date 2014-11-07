package com.midtownmadness.bubblecombat;

import com.midtownmadness.bubblecombat.multiplay.MultiplayManager;

import android.app.Application;

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
}
