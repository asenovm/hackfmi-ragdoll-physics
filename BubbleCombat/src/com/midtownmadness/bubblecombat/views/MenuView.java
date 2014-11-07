package com.midtownmadness.bubblecombat.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.midtownmadness.bubblecombar.listeners.GameRoomListener;
import com.midtownmadness.bubblecombar.model.GameModel;
import com.midtownmadness.bubblecombar.model.GamesAdapter;
import com.midtownmadness.bubblecombar.model.MockGamesAdapter;
import com.midtownmadness.bubblecombat.R;

public class MenuView extends LinearLayout implements GameRoomListener {

	/**
	 * {@value}
	 */
	private static final String TAG = MenuView.class.getSimpleName();

	private class HostOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i(TAG, "host button is clicked");
		}
	}

	private class QuitOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i(TAG, "quit button is clicked");
		}
	}

	public MenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		final LayoutInflater inflater = LayoutInflater.from(context);

		inflater.inflate(R.layout.menu_layout, this);
		setBackgroundResource(R.color.light_gray);

		final View quitButton = findViewById(R.id.quit_button);
		quitButton.setOnClickListener(new QuitOnClickListener());

		final View hostButton = findViewById(R.id.host_button);
		hostButton.setOnClickListener(new HostOnClickListener());

		final ListView gamesListView = (ListView) findViewById(R.id.games_list);
		gamesListView.addHeaderView(inflater.inflate(R.layout.menu_title_view,
				gamesListView, false));

		final GamesAdapter mockAdapter = new MockGamesAdapter(context);
		mockAdapter.setGameRoomListener(this);

		gamesListView.setAdapter(mockAdapter);
	}

	public MenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MenuView(Context context) {
		this(context, null);
	}

	@Override
	public void onGameEntered(GameModel model) {
		Log.i(TAG, "on game entered is called with model " + model.toString());
	}
}
