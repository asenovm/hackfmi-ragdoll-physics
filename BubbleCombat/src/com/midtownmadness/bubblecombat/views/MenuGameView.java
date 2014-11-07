package com.midtownmadness.bubblecombat.views;

import com.midtownmadness.bubblecombar.model.GameModel;
import com.midtownmadness.bubblecombat.R;
import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuGameView extends LinearLayout {

	private final TextView textView;

	public MenuGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.menu_game_view, this);
		textView = (TextView) findViewById(R.id.game_view_text);
	}

	public MenuGameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MenuGameView(Context context) {
		this(context, null);
	}

	public void populateFromModel(final MultiplayerGame model) {
		textView.setText(model.getName());
	}

}
