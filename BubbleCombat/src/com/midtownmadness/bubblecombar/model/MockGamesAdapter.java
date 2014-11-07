package com.midtownmadness.bubblecombar.model;

import java.util.Arrays;

import android.content.Context;

import com.midtownmadness.bubblecombat.R;

public class MockGamesAdapter extends GamesAdapter {

	public MockGamesAdapter(final Context context) {
		super(context, R.id.game_view_text, Arrays.asList(
				new GameModel("Game One"), new GameModel("Game Two"),
				new GameModel("Game Three")).toArray(new GameModel[0]));
	}
}
