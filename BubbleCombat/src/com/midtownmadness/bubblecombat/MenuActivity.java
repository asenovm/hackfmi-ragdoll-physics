package com.midtownmadness.bubblecombat;

import com.midtownmadness.bubblecombat.views.MenuView;

import android.app.Activity;
import android.os.Bundle;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MenuView(this));
	}

}
