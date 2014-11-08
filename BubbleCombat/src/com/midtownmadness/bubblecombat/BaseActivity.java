package com.midtownmadness.bubblecombat;

import android.app.Activity;

public class BaseActivity extends Activity {
	@Override
	public Object getSystemService(String name) {
		Object normalSystemService = super.getSystemService(name);
		if (normalSystemService != null) {
			return normalSystemService;
		}

		Object ourSystemService = getApplicationContext()
				.getSystemService(name);
		return ourSystemService;
	}

	protected void onGameClose() {
		((GlobalContext) getApplicationContext()).onGameClose();
	}
}
