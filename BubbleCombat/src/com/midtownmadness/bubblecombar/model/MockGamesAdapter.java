package com.midtownmadness.bubblecombar.model;

import android.content.Context;

import com.midtownmadness.bubblecombat.R;

public class MockGamesAdapter extends GamesAdapter {

	public MockGamesAdapter(final Context context) {
		super(context, R.id.game_view_text, new GameModel[] {
				new GameModel("Game One"), new GameModel("Game Two"),
				new GameModel("Game Three"), new GameModel("Game Four"),
				new GameModel("Game Five"), new GameModel("Game Six"),
				new GameModel("Game Seven"), new GameModel("Game Eight") });
	}
}
