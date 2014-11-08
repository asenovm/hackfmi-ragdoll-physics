package com.midtownmadness.bubblecombat.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midtownmadness.bubblecombar.listeners.GameRoomListener;
import com.midtownmadness.bubblecombat.R;
import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;

public class MenuGameView extends LinearLayout {

	private final TextView textView;

	private GameRoomListener listener;

	private MultiplayerGame model;

	public static class SimpleGameRoomListener implements GameRoomListener {
		@Override
		public void onGameSelected(MultiplayerGame model) {
			// blank
		}
	}

	private class TextViewClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (listener != null) {
				listener.onGameSelected(model);
			}
		}
	}

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
		this.model = model;
	}

	public void setListener(final GameRoomListener listener) {
		this.listener = listener;
		textView.setOnClickListener(new TextViewClickListener());
	}
}
