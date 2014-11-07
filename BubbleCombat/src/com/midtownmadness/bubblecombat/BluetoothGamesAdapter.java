package com.midtownmadness.bubblecombat;

import android.content.Context;

import com.midtownmadness.bubblecombar.model.GameModel;
import com.midtownmadness.bubblecombar.model.GamesAdapter;
import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;

public class BluetoothGamesAdapter extends GamesAdapter {

	public BluetoothGamesAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId, new MultiplayerGame[]{});
	}
}
