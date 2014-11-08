package com.midtownmadness.bubblecombat;

import static android.widget.Toast.LENGTH_SHORT;
import android.app.Activity;
import android.widget.Toast;

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

	protected void toast(final String text) {
		Toast.makeText(this, text, LENGTH_SHORT).show();
	}

	protected void toast(final int resourceId) {
		Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show();
	}

}
