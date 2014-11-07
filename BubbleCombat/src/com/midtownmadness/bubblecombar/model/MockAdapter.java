package com.midtownmadness.bubblecombar.model;

import java.util.Arrays;

import android.content.Context;

import com.midtownmadness.bubblecombat.MockMultiplayerGame;
import com.midtownmadness.bubblecombat.R;

public class MockAdapter extends GamesAdapter {

	public MockAdapter(final Context context) {
		super(context, R.id.game_view_text, Arrays.asList(
				new MockMultiplayerGame("Game One"), new MockMultiplayerGame("Game Two"),
				new MockMultiplayerGame("Game Three")).toArray(new MockMultiplayerGame[0]));
	}
}
