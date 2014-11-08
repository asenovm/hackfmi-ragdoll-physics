package com.midtownmadness.bubblecombat.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.midtownmadness.bubblecombar.model.GamesAdapter;
import com.midtownmadness.bubblecombat.R;

public class MenuView extends LinearLayout {

	/**
	 * {@value}
	 */
	@SuppressWarnings("unused")
	private static final String TAG = MenuView.class.getSimpleName();

	public MenuView(Context context, AttributeSet attrs, int defStyle,
			OnClickListener listener, GamesAdapter adapter) {
		super(context, attrs, defStyle);
		final LayoutInflater inflater = LayoutInflater.from(context);

		inflater.inflate(R.layout.menu_layout, this);
		setBackgroundResource(R.color.light_gray);

		final View quitButton = findViewById(R.id.quit_button);
		quitButton.setOnClickListener(listener);

		final View hostButton = findViewById(R.id.host_button);
		hostButton.setOnClickListener(listener);

		final View playButton = findViewById(R.id.play_button);
		playButton.setOnClickListener(listener);

		final ListView gamesListView = (ListView) findViewById(R.id.games_list);
		final View headerView = inflater.inflate(R.layout.menu_title_view,
				gamesListView, false);

		gamesListView.addHeaderView(headerView);
		gamesListView.setEmptyView(findViewById(R.id.menu_list_empty_view));

		gamesListView.setAdapter(adapter);

	}

	// public MenuView(Context context, AttributeSet attrs) {
	// this(context, attrs, 0);
	// }

	public MenuView(Context context, OnClickListener onClickListener,
			GamesAdapter adapter) {
		this(context, null, 0, onClickListener, adapter);
	}

}
