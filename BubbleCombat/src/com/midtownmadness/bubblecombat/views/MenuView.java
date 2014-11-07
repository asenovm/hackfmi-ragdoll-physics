package com.midtownmadness.bubblecombat.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.midtownmadness.bubblecombar.model.GameModel;
import com.midtownmadness.bubblecombar.model.GamesAdapter;
import com.midtownmadness.bubblecombat.R;

public class MenuView extends LinearLayout {

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
		LayoutInflater.from(context).inflate(R.layout.menu_layout, this);
		setBackgroundResource(R.color.light_gray);

		final View quitButton = findViewById(R.id.quit_button);
		quitButton.setOnClickListener(new QuitOnClickListener());

		final View hostButton = findViewById(R.id.host_button);
		hostButton.setOnClickListener(new HostOnClickListener());

		final ListView gamesListView = (ListView) findViewById(R.id.games_list);
		final GamesAdapter adapter = new GamesAdapter(context,
				R.id.game_view_text, new GameModel[] { new GameModel() });
		adapter.add(new GameModel());
		adapter.add(new GameModel());
		gamesListView.setAdapter(adapter);
	}

	public MenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MenuView(Context context) {
		this(context, null);
	}

}
