package com.midtownmadness.bubblecombat.views;

import com.midtownmadness.bubblecombar.listeners.GameRoomListener;
import com.midtownmadness.bubblecombar.model.GameModel;
import com.midtownmadness.bubblecombat.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuGameView extends LinearLayout {

	private final TextView textView;

	private GameRoomListener listener;

	private GameModel model;

	public static class SimpleGameRoomListener implements GameRoomListener {
		@Override
		public void onGameEntered(GameModel model) {
			// blank
		}
	}

	private class TextViewClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			listener.onGameEntered(model);
		}
	}

	public MenuGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.menu_game_view, this);
		textView = (TextView) findViewById(R.id.game_view_text);
		listener = new SimpleGameRoomListener();
	}

	public MenuGameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MenuGameView(Context context) {
		this(context, null);
	}

	public void setListener(final GameRoomListener listener) {
		this.listener = listener;
	}

	public void populateFromModel(final GameModel model) {
		this.model = model;
		textView.setText(model.getGameName());
	}

}
